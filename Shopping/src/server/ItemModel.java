package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ItemModel {
	private List<Item> items;
	public static String filepath = ".\\items.txt";
	
	public ItemModel()
	{
		items = new LinkedList<Item>();
		loadItems();
	}
	
	private synchronized void loadItems(){
		
		try {
			File file = new File(filepath);
			file.createNewFile();
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			String line = reader.readLine();
			String[] itemName;
			if(line != null){
				itemName = line.split(";");
			}else{
				itemName = new String[0];
			}
			for(int i=0; i<itemName.length; i++)
			{
				Item item = new Item();
				item.setName(itemName[i]);
				items.add(item);
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Item> getAllItems(){
		return items;
	}
	
	public boolean removeItem(String name){
		boolean removed = false;
		for(int i=0; i<items.size(); i++)
		{
			if(items.get(i).getName().equals(name))
			{
				items.remove(i);
				removed = true;
			}
		}
		
		return removed;
	}
	
	public void addItem(Item p){
		for(int i=0; i<items.size(); i++)
		{
			if(items.get(i).getName().equals(p.getName()))
			{
				return;
			}
			
		}
		items.add(p);	
	}

	
	public synchronized void saveToFile(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
			StringBuilder builder = new StringBuilder();
			for(int i=0; i<items.size(); i++)
			{
				builder.append(items.get(i).getName());
				builder.append(";");
			}
			writer.write(builder.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
