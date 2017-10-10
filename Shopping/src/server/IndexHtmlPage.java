package server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class IndexHtmlPage extends HtmlPage {
	
	private StringBuilder content;

	public IndexHtmlPage() {
		content = new StringBuilder();
		super.generateFrame(content);
		String newbutton = "<div style='margin-top:10px'>"
				+ "<a href='edit' type='button' class='btn btn-success btn-block'>新地点</a>"
				+ "</div>";
		content.insert(content.indexOf("</body>"), newbutton);
		String head = "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' integrity='sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u' crossorigin='anonymous'/>"
				+ "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js' integrity='sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa' crossorigin='anonymous'></script>";
		content.insert(content.indexOf("</head>"), head);
	}
	
	@Override
	public int getContentLength() {
		try {
			return this.content.toString().getBytes("utf-8").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String getContent() {
		return this.content.toString();
	}

	public void addItemsToPlace(ArrayList<ArrayList<String>> itemArr, String placeName){
		StringBuilder html = new StringBuilder();
		html.append("<div>"
				+ "<table class='table'>"
				+ "<thead><tr><th colspan='3'>"+placeName+"</th></tr></thead>"
				+ "<tbody>");
		for(int i=0; i<itemArr.size(); i++)
		{
			html.append("<tr><td style='width:50%'>"+itemArr.get(i).get(0)+"</td><td style='width:20%'>"+itemArr.get(i).get(1)+"</td><td style='width:30%'>");
			if(itemArr.get(i).get(2).equals("1"))
				html.append("可选");
			html.append("</td></tr>");
		}
		try {
			html.append("</tbody>"
					+ "</table>"
					+ "<a href='edit?place="+URLEncoder.encode(placeName, "UTF-8")+"' type='button' class='btn btn-primary' style='float:left'>修改</a>"
					+ "<a href='delete?place="+URLEncoder.encode(placeName, "UTF-8")+"' type='button' class='btn btn-danger' style='float:right'>删除</a>"
					+ "</div>");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			content.insert(content.indexOf("</body>"), html.toString());
		}
		content.insert(content.indexOf("</body>"), html.toString());
	}
}
