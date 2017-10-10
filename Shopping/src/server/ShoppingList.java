package server;


import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
	private Place store;
	private List<Item> items;
	
	
	public ShoppingList(){
		items = new ArrayList<Item>();
	}
	
	public void setPlace(Place p){
		this.store = p;
	}
	
	public Place getPlace(){
		return this.store;
	}
	
	public void addItem(Item i){
		if(!items.contains(i))
			this.items.add(i);
	}
	
	public List<Item> getAllItems(){
		return items;
	}
	
	public boolean removeItem(String s){
		boolean removed = false;
		for(int i=0; i<items.size(); i++)
		{
			if(items.get(i).getName().equals(s))
			{
				items.remove(i);
				removed = true;
			}
		}
		
		return removed;
	}
	
	
}
