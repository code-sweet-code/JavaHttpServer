package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditPageAccess implements Accessable {

	@Override
	public void serve(Connection connection) {
		String place = connection.getValue("place");
		try{
			 if(place.isEmpty()){
				connection.responseHTML(200, newShoppingList());
			}
			else{
				connection.responseHTML(200, loadShoppingList(place));
			}
		}catch(IOException e){
			e.printStackTrace();
		}

	}

	private HtmlPage loadShoppingList(String place) {
		PlaceModel pm = new PlaceModel();
		ItemModel im = new ItemModel();
		EditHtmlPage editPage = new EditHtmlPage(placeToList(pm.getAllPlaces()), itemToList(im.getAllItems()), false);
		ShoppingListModel shopModel = new ShoppingListModel();
		shopModel.loadFromFile();
		ShoppingList shopdata = shopModel.getShoppingListByPlace(place);
		ArrayList<List<String>> itemsarr = new ArrayList<List<String>>();
		List<Item> items = shopdata.getAllItems();
		for(int i=0; i<items.size(); i++){
			Item itm = items.get(i);
			ArrayList<String> itemarr = new ArrayList<String>();
			itemarr.add(itm.getName());
			itemarr.add(itm.getQuantity());
			itemarr.add(itm.getOptional());
			itemsarr.add(itemarr);
		}
		editPage.loadPage(place, itemsarr);
		return editPage;
	}

	private HtmlPage newShoppingList() {
		PlaceModel pm = new PlaceModel();
		ItemModel im = new ItemModel();
		EditHtmlPage editPage = new EditHtmlPage(placeToList(pm.getAllPlaces()), itemToList(im.getAllItems()), true);
		editPage.loadPage("", new ArrayList<List<String>>());
		return editPage;
	}

	private ArrayList<String> placeToList(List<Place> placeList){
		ArrayList<String> placeStringList  = new ArrayList<String>();
		for(int i=0; i<placeList.size(); i++)
		{
			placeStringList.add(placeList.get(i).getName());
		}
		return placeStringList;
	}
	
	private ArrayList<String> itemToList(List<Item> itemList){
		ArrayList<String> itemStringList  = new ArrayList<String>();
		for(int i=0; i<itemList.size(); i++)
		{
			itemStringList.add(itemList.get(i).getName());
		}
		return itemStringList;
	}
}
