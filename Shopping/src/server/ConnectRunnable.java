package server;

import java.io.IOException;
import java.util.Map;

public class ConnectRunnable implements Runnable {

	private Connection conn;
	Map<String, Accessable> funcMap;
	
	public ConnectRunnable(Connection newConn, Map<String, Accessable> funcMap){
		this.conn = newConn;
		this.funcMap = funcMap;
	}
	
	@Override
	public void run() {
		try {
			conn.loadData();
			String funcName = conn.getFunction();
			Accessable module = funcMap.get(funcName);
			if(module != null)
				module.serve(conn);
			else
				conn.responseHTML(404, new ErrorHtmlPage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				try {
					conn.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

	}

	
}
