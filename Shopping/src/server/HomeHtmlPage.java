package server;

import java.io.UnsupportedEncodingException;

public class HomeHtmlPage extends HtmlPage {

	private StringBuilder content;
	
	public HomeHtmlPage() {
		content = new StringBuilder();
		super.generateFrame(content);
		String buttons = "<div style='margin:50px'>"
				+ "<a href='/index?ver=new' type='button' class='btn btn-info btn-block'>新购物单</a></div>"
				+ "<div style='margin:50px'>"
				+ "<a href='/index?ver=old' type='button' class='btn btn-info btn-block'>查看上次购物单</a></div>";
		content.insert(content.indexOf("</body>"), buttons);
	}
	
	@Override
	protected int getContentLength() {
		try {
			return this.content.toString().getBytes("utf-8").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	protected String getContent() {
		return this.content.toString();
	}

}
