package server;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;


public class MainController {
	ServerSocket socket;
	Map<String, Accessable> funcMap;
	boolean isRunning;
	Application ui;

	public MainController() {
		funcMap = new HashMap<String, Accessable>();
		isRunning = false;
	}
	
	public void bindAccess(String funcName, Accessable obj){
		funcMap.put(funcName, obj);
	}
	
	public void setUICallback(Application cb){
		ui = cb;
	}
	
	public void startListen(int port) throws IOException {
		this.socket = new ServerSocket(port);
		isRunning = true;
		new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
				Connection connection;
				try {
					connection = new Connection(socket.accept());
					new Thread(new ConnectRunnable(connection, funcMap)).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
					
				}
			}
			
		}).start();
		
	}


	public boolean isRunning(){
		return isRunning;
	}
	public static void main(String[] args) {
		MainController mc = new MainController();
		mc.bindAccess("", new IndexAccess());
		mc.bindAccess("index", new IndexAccess());
		mc.bindAccess("edit", new EditPageAccess());
		mc.bindAccess("save", new SaveAccess());
		mc.bindAccess("delete", new DeleteAccess());
		try {
			mc.startListen(80);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connectionAccepted(Connection connection) {
		try {
			connection.loadData();
			String funcName = connection.getFunction();
			Accessable module = funcMap.get(funcName);
			if(module != null)
				module.serve(connection);
			else
				connection.responseHTML(404, new ErrorHtmlPage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				try {
					connection.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
	}

	public void closeSocket() {
		if(socket != null){
			try {
				socket.close();
				isRunning = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}



	

}
