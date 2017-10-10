package server;

import java.io.IOException;
import java.util.List;

public class SaveAccess implements Accessable {

	private ShoppingList shoppingList;
	
	public SaveAccess()
	{
		shoppingList = new ShoppingList();
	}
	
	@Override
	public void serve(Connection connection) {
		String isNewPage = connection.getValue("isnew");
		extractParams(connection);
		if(isNewPage.isEmpty() || isNewPage.equals("0")){
			updateShoppingList();
		}else{
			saveNewShoppingList();
		}
		try {
			connection.redirect("index");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateShoppingList() {
		ShoppingListModel shoplistM = new ShoppingListModel();
		shoplistM.loadFromFile();
		shoplistM.updateList(shoppingList);
		shoplistM.saveToFile();
		updateNewTags();
	}

	private void updateNewTags() {
		
		PlaceModel placeM = new PlaceModel();
		placeM.addPlace(shoppingList.getPlace());
		placeM.saveToFile();
		
		ItemModel itemM = new ItemModel();
		List<Item> items = shoppingList.getAllItems();
		for(int i=0; i<items.size(); i++)
		{
			itemM.addItem(items.get(i));
		}
		itemM.saveToFile();
		
	}

	private void saveNewShoppingList() {
		ShoppingListModel shoplistM = new ShoppingListModel();
		shoplistM.loadFromFile();
		shoplistM.addShoppingList(shoppingList);
		shoplistM.saveToFile();
		updateNewTags();
	}

	private void extractParams(Connection connection) {
		shoppingList = new ShoppingList();
		Place place = new Place();
		place.setName(connection.getValue("shop"));
		shoppingList.setPlace(place);
		for(int i=0; ;i++){
			String itemName = connection.getValue("item"+i);
			String itemQuan = connection.getValue("quentity"+i);
			String itemOpt = connection.getValue("optional"+i);
			if(itemName.isEmpty()){
				if(i > 20)	
					break;
				else	
					continue;
			}
			Item item = new Item();
			item.setName(itemName);
			item.setQuantity(itemQuan);
			if(itemOpt.equals("on"))
				item.setOptional("1");
			else
				item.setOptional("0");
			shoppingList.addItem(item);
		}
	}

}
