package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Connection {
	private Socket socket;
	private String function;
	private Map<String, String> query;
	
	public Connection(Socket socket) {
		this.socket = socket;
		this.query = new HashMap<String, String>();
	}

	
	public synchronized void loadData() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		StringBuilder requestBuilder = new StringBuilder();
		char[] readdata = new char[1000];
		int readlen = 0;
		while((readlen = reader.read(readdata)) != -1){
			requestBuilder.append(readdata, 0, readlen);
			if(requestBuilder.indexOf("\r\n\r\n") != -1) break;
		}
		int headEndIndex = requestBuilder.indexOf("\r\n\r\n");
		if(headEndIndex > 0){
			String header = requestBuilder.toString().substring(0, headEndIndex);
			disassembleHeader(header);
		}
		if(query.containsKey("Content-Length")){
			int contentLenth = Integer.valueOf(query.get("Content-Length"));
			String readedContent = requestBuilder.toString().substring(headEndIndex+4);
			int leftLen = contentLenth - readedContent.length();
			while(leftLen > 0 && (readlen = reader.read(readdata)) != -1){
				requestBuilder.append(readdata, 0, readlen);
				leftLen = leftLen - readlen;
			}
			readedContent = requestBuilder.toString().substring(headEndIndex+4);
			pickQuery(readedContent);
		}
		
	}


	private void disassembleHeader(String header){
		String[] lines = header.split("\r\n");
		for(int i=0; i<lines.length; i++){
			if(i == 0) 
			{
				String link = getRequestLink(lines[0]);
				pickFunction(link);
				pickQuery(link);
				continue;
			}
			pickHeaderAttr(lines[i]);
		}
	}
	
	private void pickHeaderAttr(String line){
		String[] attr = line.split(": ");
		if(attr.length == 2){
			query.put(attr[0], attr[1]);
		}
	}
	
	private void pickFunction(String link) {
		String path;
		int questionindex = link.indexOf("?");
		if(questionindex != -1) 
			path = link.substring(0, questionindex);
		else
			path = link;
		this.function = path.substring(path.lastIndexOf("/")+1);
	}


	private void pickQuery(String link) {
		int questionindex = link.indexOf("?");
		String queryStr = link.substring(questionindex+1);
		String[] queries = queryStr.split("&");
		
		for(int i=0; i<queries.length; i++)
		{
			String[] kv = queries[i].split("=");
			if(kv.length > 1)
				query.put(kv[0], kv[1]);
		}
	}


	private String getRequestLink(String firstline) {
		String path = null;
		String[] sections = firstline.split(" ");
		if(sections.length > 2)
		{
			path = sections[1];
		}
		
		return path;
	}
	


	public String getFunction() {
		
		return function;
	}
	
	public String getValue(String param) {
		String value = query.get(param);
		if(value == null)
			value = "";
		try {
			value = URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return value;
		}
		return value;
	}

	public synchronized void responseHTML(int code, HtmlPage htmlPage) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		long contentLength = htmlPage.getContentLength();
		String httpheader = getResponseHeader(code, contentLength);
		String response = httpheader + htmlPage.getContent();
		writer.write(response);
		writer.flush();
		writer.close();
	}

	public void close() throws IOException{
		this.socket.close();
	}

	private String getResponseHeader(int code, long length) {
		StringBuilder headerbuilder = new StringBuilder();
		headerbuilder.append("HTTP/1.1 ");
		switch(code){
		case 200: headerbuilder.append("200 OK\r\n"); break;
		case 404: headerbuilder.append("404 Not Found\r\n");break;
		}
		headerbuilder.append("Content-Type: text/html\r\n");
		headerbuilder.append("Content-Length: "+length+"\r\n");
		headerbuilder.append("\r\n");
		return headerbuilder.toString();
	}
	
	public synchronized void redirect(String page) throws IOException{
		StringBuilder redirectHeader = new StringBuilder();
		redirectHeader.append("HTTP/1.1 303 See Other\r\n");
		redirectHeader.append("Location: "+page+"\r\n");
		redirectHeader.append("\r\n");
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		writer.write(redirectHeader.toString());
		writer.flush();
		writer.close();
	}

}
