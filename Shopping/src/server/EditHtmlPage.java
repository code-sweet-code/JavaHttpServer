package server;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class EditHtmlPage extends HtmlPage {

	private StringBuilder content;
	private List<String> places;
	private List<String> items;

	public EditHtmlPage(List<String> places, List<String> items, boolean isNewPage) {
		content = new StringBuilder();
		super.generateFrame(content);
		String head = "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' integrity='sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u' crossorigin='anonymous'/>"
				+ "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js' integrity='sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa' crossorigin='anonymous'></script>";
		content.insert(content.indexOf("</head>"), head);
		insertScript();
		insertForm(isNewPage);
		this.places = places;
		this.items = items;
	}
	
	private void insertForm(boolean isNewPage) {
		StringBuilder formhtml = new StringBuilder();
		formhtml.append("<form class='form' name='shoppingform' ");
		if(isNewPage)
			formhtml.append("action='save?isnew=1'"); 
		else
			formhtml.append("action='save?isnew=0'"); 
		formhtml.append("  method='post'></form>");
		content.insert(content.indexOf("</body>"), formhtml);
	}

	private void insertScript() {
		String js = "<script>"
				+ "function shopselect(s){"
				+ "var shopbox = document.getElementById('shopinputbox');"
				+ "shopbox.value = s.firstChild.innerHTML;}"
				+ "function itemselect(s){"
				+ "var item = 'itembox'+s.value;"
				+ "var itembox = document.getElementById(item);"
				+ "itembox.value = s.firstChild.innerHTML;}"
				+ "function add(s){"
				+ "var quan = 'quantity'+s.value;"
				+ "var hquan = 'hquantity'+s.value;"
				+ "var quanbox = document.getElementById(quan);"
				+ "var hquanbox = document.getElementById(hquan);"
				+ "hquanbox.value = parseInt(hquanbox.value)+1;"
				+ "quanbox.innerHTML = parseInt(quanbox.innerHTML)+1;}"
				+ "function minus(s){"
				+ "var quan = 'quantity'+s.value;"
				+ "var hquan = 'hquantity'+s.value;"
				+ "var quanbox = document.getElementById(quan);"
				+ "var hquanbox = document.getElementById(hquan);"
				+ "hquanbox.value = parseInt(hquanbox.value)-1;"
				+ "quanbox.innerHTML = parseInt(quanbox.innerHTML)-1;}"
				+ "</script>";
		content.insert(content.indexOf("</head>"), js);
	}
	
	public void setPlaces(List<String> places){
		this.places = places;
	}
	
	public void setItems(List<String> items){
		this.items = items;
	}
	
	public void loadPage(String loadplace, List<List<String>> loaditems){
		StringBuilder placebuilder = new StringBuilder();
		StringBuilder itembuilder = new StringBuilder();
		StringBuilder buttonbuilder = new StringBuilder();
		placebuilder.append("<div class='form-group'>"
				+ "<div class='input-group'>"
				+ "<div class='input-group-btn'>"
				+ "<button type='button' ");
		if(!loadplace.isEmpty()) 
			placebuilder.append("disabled");
		placebuilder.append(" class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>商店 <span class='caret'></span></button>"
				+ "<ul class='dropdown-menu'>");
		for(int i=0; i<places.size(); i++){
			placebuilder.append("<li onclick='shopselect(this)'><a>"+places.get(i)+"</a></li>");
		}
				
		placebuilder.append("</ul></div><input type='text' class='form-control' name='shop' id='shopinputbox' ");
		if(!loadplace.isEmpty()) 
			placebuilder.append("value=\""+ loadplace +"\" ");
		placebuilder.append(" ></div></div>");
		
		for(int i=0; i<20; i++){
			itembuilder.append("<div class='form-group' value='"+i+"'>"
					+ "<div class='input-group'>"
					+ "<div class='input-group-btn'>"
					+ "<button type='button' class='btn btn-default dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'> <span class='caret'></span></button>"
					+ "<ul class='dropdown-menu' style='height:300px;overflow:scroll'>");
			for(int j=0; j<items.size(); j++){
				itembuilder.append("<li onclick='itemselect(this)' value='"+i+"'><a>"+items.get(j)+"</a></li>");
			}
			itembuilder.append("</ul>"
					+ "</div>"
					+ "<input type='text' class='form-control' name='item"+i+"' id='itembox"+i+"' ");
			if(i<loaditems.size() && loaditems.get(i).size() == 3)
				itembuilder.append(" value='"+loaditems.get(i).get(0)+"'");
			itembuilder.append(">");
			itembuilder.append("<div class='input-group-btn'>"
					+ "<button class='btn btn-default' type='button' onclick='minus(this)' value='"+i+"'>-</button>");
			//quantity
			itembuilder.append("<button class='btn btn-default' type='button' id='quantity"+i+"' >");
			if(i<loaditems.size() && loaditems.get(i).size() == 3)
			{
				itembuilder.append(loaditems.get(i).get(1)+"</button>");
				itembuilder.append("<input type='hidden' name='quentity"+i+"' id='hquantity"+i+"' value='"+loaditems.get(i).get(1)+"' >");
			}
			else
			{
				itembuilder.append("1</button>");
				itembuilder.append("<input type='hidden' name='quentity"+i+"' id='hquantity"+i+"' value='1' >");
			}
			
			
			itembuilder.append("<button class='btn btn-default' type='button' onclick='add(this)' value='"+i+"'>+</button>"
					+ "</div>"
					+ "<span class='input-group-addon'>"
					+ "<input type='checkbox' name='optional"+i+"' ");
			if(i<loaditems.size() && loaditems.get(i).size() == 3 && loaditems.get(i).get(2).equals("1"))
				itembuilder.append("checked");
			itembuilder.append(">可选</span></div></div>");
		}
		
		buttonbuilder.append("<div class='form-group'>"
				+ "<button type='submit' class='btn btn-primary' style='float:left'>确定</button>"
				+ "<a href='index' class='btn btn-default' style='float:right'>取消</a></div>");
		
		content.insert(content.indexOf("</form>"), placebuilder.toString());
		content.insert(content.indexOf("</form>"), itembuilder.toString());
		content.insert(content.indexOf("</form>"), buttonbuilder.toString());
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
