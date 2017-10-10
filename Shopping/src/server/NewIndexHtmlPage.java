package server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NewIndexHtmlPage extends HtmlPage {

	private StringBuilder content;
	public NewIndexHtmlPage(){
		content = new StringBuilder();
		super.generateFrame(content);
		String newcontent = "<div style='text-align:center;border-bottom:3px solid lightgray;margin-bottom:20px'>"
				+ "<h4>康康的购物单</h4></div>"
				+ "<div data-role='collapsibleset' data-theme='a' data-content-theme='a' data-collapsed-icon='carat-d' data-expanded-icon='carat-u' data-iconpos='right' id='collapsebox'>"
				+ "</div>"
				+ "<a href='edit' class='ui-btn ui-icon-plus ui-btn-icon-notext ui-corner-all' style='float:right'></a>";
		content.insert(content.indexOf("</body>"), newcontent);
		String head = "<link rel='stylesheet' href='https://ajax.googleapis.com/ajax/libs/jquerymobile/1.4.5/jquery.mobile.min.css' />"
				+ "<script type='text/javascript'>$(document).bind('mobileinit', function() { $.mobile.ajaxEnabled = false; });</script>"
				+ "<script src='https://ajax.googleapis.com/ajax/libs/jquerymobile/1.4.5/jquery.mobile.min.js'></script>";
		content.insert(content.indexOf("</head>"), head);
	}
	
	public void addItemsToPlace(ArrayList<ArrayList<String>> itemArr, String placeName){
		StringBuilder html = new StringBuilder();
		html.append("<div data-role='collapsible' >"
				+ "<h4>"+ placeName +"</h4>"
				+ "<table style='width:100%'>");
		for(int i=0; i<itemArr.size(); i++)
		{
			html.append("<tr><td>"+itemArr.get(i).get(0)+"</td><td>"+itemArr.get(i).get(1)+"</td><td>");
			if(itemArr.get(i).get(2).equals("1"))
				html.append("可选");
			html.append("</td></tr>");
		}
		try {
			html.append("</table>"
					+ "<div class='ui-grid-a '>"
					+ "<div class='ui-block-a'><a href='delete?place="+URLEncoder.encode(placeName, "UTF-8")+"' class='ui-btn ui-icon-delete ui-btn-icon-notext ui-corner-all'></a></div>"
					+ "<div class='ui-block-b'><a href='edit?place="+URLEncoder.encode(placeName, "UTF-8")+"' class='ui-btn ui-icon-edit ui-btn-icon-notext ui-corner-all' style='margin-left:80%'></a></div>"
					+ "</div>"
					+ "</div>");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			String searchStr = "id='collapsebox'>";
			content.insert(content.indexOf(searchStr)+searchStr.length(), html.toString());
		}
		
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

}
