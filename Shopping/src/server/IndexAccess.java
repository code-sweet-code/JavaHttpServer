package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IndexAccess implements Accessable {

	@Override
	public void serve(Connection connection) {
		String isout = connection.getValue("isout");
		try {
			connection.responseHTML(200, loadOldIndex(isout));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private HtmlPage loadOldIndex(String isout){
		ShoppingListModel slLoder = new ShoppingListModel();
		slLoder.loadFromFile();
		List<ShoppingList> sls = slLoder.getAllShoppingLists();
		IndexHtmlPage indexP;
		if(isout.isEmpty()){
			indexP = new IndexHtmlPage();
		}else{
			indexP = new ProIndexHtmlPage();
		}
		for(int i=0; i<sls.size(); i++)
		{
			ShoppingList sl = sls.get(i);
			String placeName = sl.getPlace().getName();
			List<Item> items = sl.getAllItems();
			ArrayList<ArrayList<String>> itemArr = new ArrayList<ArrayList<String>>();
			for(int j=0; j<items.size(); j++)
			{
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(items.get(j).getName());
				arr.add(items.get(j).getQuantity());
				arr.add(items.get(j).getOptional());
				itemArr.add(arr);
			}
			indexP.addItemsToPlace(itemArr, placeName);
		}
		
		return indexP;
	}
	
}
