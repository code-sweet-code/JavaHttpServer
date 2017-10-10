package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ShoppingListModel {
	private List<ShoppingList> list;
	public static String filepath = ".\\shopping.txt";
	
	public ShoppingListModel()
	{
		list = new LinkedList<ShoppingList>();
	}
	
	
	//place;item1:quantity:optional,item2:quantity:optional...\n
	public synchronized void loadFromFile(){
		try {
			File file = new File(filepath);
			file.createNewFile();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null && line.indexOf(";") != -1)
			{
				ShoppingList shList = new ShoppingList();
				String placeName = line.substring(0, line.indexOf(";"));
				Place place = new Place();
				place.setName(placeName);
				shList.setPlace(place);
				String itemLine = line.substring(line.indexOf(";")+1);
				String[] itemStr = itemLine.split(",");
				for(int i=0; i<itemStr.length; i++){
					String[] itemPair = itemStr[i].split(":");
					if(itemPair.length == 3)
					{
						Item item = new Item();
						item.setName(itemPair[0]);
						item.setQuantity(itemPair[1]);
						item.setOptional(itemPair[2]);
						shList.addItem(item);
					}
				}
				list.add(shList);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addShoppingList(ShoppingList sl){
		if(!list.contains(sl))
			list.add(sl);
	}
	
	public List<ShoppingList> getAllShoppingLists(){
		return list;
	}
	
	private int IndexOf(String place){
		int index = -1;
		for(int i=0; i<list.size(); i++){
			
			if(list.get(i).getPlace().getName().equals(place)){
				index = i;
			}
		}
		return index;
	}
	
	public ShoppingList getShoppingListByPlace(String place){
		ShoppingList targetList = new ShoppingList();
		int index;
		if((index = IndexOf(place)) != -1)
			targetList = list.get(index);
		
		return targetList;
	}
	
	public synchronized void saveToFile(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
			for(int i=0; i<list.size(); i++)
			{
				StringBuilder builder = new StringBuilder();
				ShoppingList sl = list.get(i);
				builder.append(sl.getPlace().getName());
				builder.append(";");
				List<Item> items = sl.getAllItems();
				for(int j=0; j<items.size(); j++)
				{
					builder.append(items.get(j).getName());
					builder.append(":");
					builder.append(items.get(j).getQuantity());
					builder.append(":");
					builder.append(items.get(j).getOptional());
					builder.append(",");
				}
				builder.append("\n");
				writer.write(builder.toString());
				writer.flush();
			}
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteListByPlace(String place) {
		int index = IndexOf(place);
		if(index != -1){
			list.remove(index);
		}
	}
	
	public void updateList(ShoppingList updateList) {
		int index = IndexOf(updateList.getPlace().getName());
		if(index != -1){
			list.remove(index);
			list.add(updateList);
		}
	}
	
	public void emptyLists(){
		for(int i=0; i<list.size(); i++)
		{
			list.remove(i);
		}
	}
}
