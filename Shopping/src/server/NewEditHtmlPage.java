package server;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class NewEditHtmlPage extends HtmlPage {
	private StringBuilder content;
	private List<String> places;
	private List<String> items;

	public NewEditHtmlPage(List<String> places, List<String> items, boolean isNewPage) {
		content = new StringBuilder();
		super.generateFrame(content);
		String head = "<link rel='stylesheet' href='https://ajax.googleapis.com/ajax/libs/jquerymobile/1.4.5/jquery.mobile.min.css' />"
				+ "<script type='text/javascript'>$(document).bind('mobileinit', function() { $.mobile.ajaxEnabled = false; });</script>"
				+ "<script src='https://ajax.googleapis.com/ajax/libs/jquerymobile/1.4.5/jquery.mobile.min.js'></script>";
		content.insert(content.indexOf("</head>"), head);
		this.places = places;
		this.items = items;
		insertScript();
		insertForm(isNewPage);
		insertPupup();
		
	}
	
	private void insertPupup() {
		StringBuilder popup = new StringBuilder();
		popup.append("<div data-role='popup' id='additempop' >"
				+ "<a href='#' data-rel='back' class='ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right'></a>"
				+ "<div style='padding:10px'>"
				+ "<div>"
				+ "<select data-native-menu='false'  id='foodselector' data-mini='true'>"
				+ "<option value='0' data-defaults='true'>请选择:</option>");
		for(int i=0; i<items.size(); i++){
			popup.append("<option>"+items.get(i)+"</option>");
		}
		popup.append("</select>"
				+ "</div>"
				+ "<div ><input type='text' data-mini='false' id='foodinput'></div>"
				+ "<div class='ui-field-container'>"
				+ "<fieldset data-role='controlgroup' data-type='horizontal'>"
				+ "<a  class='ui-btn  ui-corner-all ' data-mini='true' id='minus'>-</a>"
				+ "<button class='ui-btn ui-shadow ' data-mini='true' id='count'>1</button>"
				+ "<a  class='ui-btn  ui-corner-all ' data-mini='true' id='add'>+</a>"
				+ "</fieldset>"
				+ "</div>"
				+ "<div>"
				+ "<select data-role='slider' id='optional'>"
				+ "<option value='0'>必选</option>"
				+ "<option value='1'>可选</option>"
				+ "</select>"
				+ "</div>"
				+ "<div><input type='button' value='确定' id='popsubmit'></div>"
				+ "</div>"
				+ "</div>");
		String formstr = "</form>";
		content.insert(content.indexOf(formstr)+formstr.length(), popup);
	}

	private void insertForm(boolean isNewPage) {
		StringBuilder formhtml = new StringBuilder();
		formhtml.append("<div style='text-align:center;border-bottom:3px solid lightgray;margin-bottom:20px'>"
				+ "<h4>康康的购物单</h4></div>"
				+ "<form ");
		if(isNewPage)
			formhtml.append("action='save?isnew=1'"); 
		else
			formhtml.append("action='save?isnew=0'"); 
		formhtml.append("  method='post'></form>");
		
		content.insert(content.indexOf("</body>"), formhtml.toString());
	}

	public void loadPage(String loadplace, List<List<String>> loaditems){
		StringBuilder storebuilder = new StringBuilder();
		StringBuilder tablebuilder = new StringBuilder();
		StringBuilder buttonbuilder = new StringBuilder();
		
		storebuilder.append("<div class='ui-grid-a'>"
				+ "<div class='ui-block-a'>"
				+ "<select data-native-menu='false' id='storeselector' data-mini='true'>"
				+ "<option>商店:</option>");
		for(int i=0; i<places.size(); i++){
			storebuilder.append("<option>"+places.get(i)+"</option>");
		}
		storebuilder.append("</select>"
				+ "</div>"
				+ "<div class='ui-block-b'><input type='text' name='shop' data-mini='false' id='storeinput' value='"+loadplace+"'></div>"
				+ "</div>");
		
		tablebuilder.append("<table style='width:100%' id='listtable'>");
		for(int i=0; i<loaditems.size(); i++){
			tablebuilder.append("<tr>"
					+ "<td num='"+i+"'>"+loaditems.get(i).get(0)+"<input type='hidden' name='item"+i+"' value='"+loaditems.get(i).get(0)+"' class='hitem'></td>"
					+ "<td>"+loaditems.get(i).get(1)+"<input type='hidden' name='quentity"+i+"' value='"+loaditems.get(i).get(1)+"' class='hquentity'></td>");
			if(loaditems.get(i).get(2).equals("1")){
				tablebuilder.append("<td>可选<input type='hidden' name='optional"+i+"' value='on' class='hoptional'></td>");
			}else{
				tablebuilder.append("<td><input type='hidden' name='optional"+i+"' value='off' class='hoptional'></td>");
			}
			tablebuilder.append("<td><a href='#additempop' data-rel='popup' data-position-to='window' class='ui-btn ui-icon-edit ui-btn-icon-notext ui-corner-all ui-btn-inline editbtn' data-transition='pop'></a></td>"
					+ "</tr>");
		}
		tablebuilder.append("</table>");
		
		buttonbuilder.append("<a href='#additempop' data-rel='popup' data-position-to='window' class='ui-btn ui-icon-plus ui-btn-icon-notext ui-corner-all ui-btn-inline' data-transition='pop' id='newbtn'></a>"
				+ "<div class='ui-grid-a'>"
				+ "<div class='ui-block-a'><input type='submit' class='ui-btn' value='save'></div>"
				+ "<div class='ui-block-b'><a href='index' class='ui-shadow ui-btn ui-corner-all'>cancel</a></div>"
				+ "</div>");
		
		content.insert(content.indexOf("</form>"), storebuilder.toString());
		content.insert(content.indexOf("</form>"), tablebuilder.toString());
		content.insert(content.indexOf("</form>"), buttonbuilder.toString());
	}
	
	private void insertScript() {
		String js = "<script>"
				+ "$(document).ready(function(){"
				+ "$('#storeselector').change(function(){"
				+ "$('#storeinput').val($('#storeselector option:selected').text());"
				+ "});"
				
				+ "$('#newbtn').click(function(){"
				+ "var totalnum = -1;"
				+ "if($('#listtable').find('tr').length > 0)"
				+ "totalnum = parseInt($('#listtable').find('tr').last().find('td').first().attr('num')); "
				+ "$('#additempop').attr('num', totalnum+1); "
				+ "$('#foodselector').val(0).selectmenu('refresh');"
				+ "$('#foodinput').val('');"
				+ "$('#count').text(1);"
				+ "$('#optional').val(0).slider('refresh');"
				+ "});"
				
				+ "$('.editbtn').click(function(){"
				+ "var num = $(this).parent().siblings().eq(0).attr('num');"
				+ "var name = $(this).parent().siblings().eq(0).text();"
				+ "var quantity = $(this).parent().siblings().eq(1).text();"
				+ "var optional = $(this).parent().siblings().eq(2).text();"
				+ "$('#additempop').attr('num', num);"
				+ "$('#foodselector').val(0).selectmenu('refresh');"
				+ "$('#foodinput').val(name);"
				+ "$('#count').text(quantity);"
				+ "if(optional != ''){"
				+ "$('#optional').val(1).slider('refresh');"
				+ "}else{"
				+ "$('#optional').val(0).slider('refresh');}"
				+ "});"
				
				+ "$('#foodselector').change(function(){"
				+ "$('#foodinput').val($('#foodselector option:selected').text());"
				+ "});"
				
				+ "$('#minus').click(function(){"
				+ "var count = parseInt($('#count').text());"
				+ "$('#count').text(count-1);"
				+ "});"
				
				+ "$('#add').click(function(){"
				+ "var count = parseInt($('#count').text());"
				+ "$('#count').text(count+1);"
				+ "});"
				
				+ "$('#popsubmit').click(function(){"
				+ "var isNew = true;"
				+ "var trTarget = null;"
				+ "var pupval =  parseInt($('#additempop').attr('num'));"
				+ "var trArr = $('#listtable').find('tr');"
				+ "for(var i=0; i<trArr.length; i++){"
				+ "var num = trArr.eq(i).children().first().attr('num');"
				+ "if(num == pupval) {"
				+ "isNew = false;"
				+ "trTarget = trArr.eq(i);"
				+ "break;"
				+ "}"
				+ "}"
				+ "if(isNew){"
				+ "var tr = document.createElement('tr');"
				+ "var td1 = document.createElement('td');"
				+ "td1.setAttribute('num', pupval);"
				+ "td1.innerHTML = $('#foodinput').val()+\"<input type='hidden' name='item\"+pupval+\"' value='\"+$('#foodinput').val()+\"' class='hitem'>\";"
				+ "var td2 = document.createElement('td');"
				+ "td2.innerHTML = $('#count').text()+\"<input type='hidden' name='quentity\"+pupval+\"' value='\"+$('#count').text()+\"' class='hquentity'>\";"
				+ "var td3 = document.createElement('td');"
				+ "var optional = $('#optional option:selected').val();"
				+ "if(optional == 1){"
				+ "td3.innerHTML = '可选'+\"<input type='hidden' name='optional\"+pupval+\"' value='on' class='hoptional'>\";"
				+ "}else{"
				+ "td3.innerHTML = \"<input type='hidden' name='optional\"+pupval+\"' value='off' class='hoptional'>\";"
				+ "}"
				+ "var td4 = document.createElement('td');"
				+ "td4.innerHTML = \"<a href='#additempop' data-rel='popup' data-position-to='window' class='ui-btn ui-icon-edit ui-btn-icon-notext ui-corner-all ui-btn-inline editbtn' data-transition='pop'></a>\";"
				+ "tr.appendChild(td1);"
				+ "tr.appendChild(td2);"
				+ "tr.appendChild(td3);"
				+ "tr.appendChild(td4);"
				+ "$('#listtable').append(tr);"
				+ "}"
				+ "else{"
				+ "var td1 = trTarget.children().eq(0);"
				+ "var td2 = trTarget.children().eq(1);"
				+ "var td3 = trTarget.children().eq(2);"
				+ "td1.text($('#foodinput').val());"
				+ "td1.append(\"<input type='hidden' name='item\"+pupval+\"' value='\"+$('#foodinput').val()+\"' class='hitem'>\");"
				+ "td2.text($('#count').text());"
				+ "td2.append(\"<input type='hidden' name='quentity\"+pupval+\"' value='\"+$('#count').text()+\"' class='hquentity'>\");"
				+ "var optional = $('#optional option:selected').val();"
				+ "if(optional == 1){"
				+ "td3.text('可选');"
				+ "td3.append(\"<input type='hidden' name='optional\"+pupval+\"' value='on' class='hoptional'>\");"
				+ "}else{"
				+ "td3.text('');"
				+ "td3.append(\"<input type='hidden' name='optional\"+pupval+\"' value='off' class='hoptional'>\");"
				+ "}"
				+ "}"
				+ "$('#additempop').popup('close');"
				+ "});"
				+ "});"
				+ "</script>";
		content.insert(content.indexOf("</head>"), js);
	}

	public void setPlaces(List<String> places){
		this.places = places;
	}
	
	public void setItems(List<String> items){
		this.items = items;
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
