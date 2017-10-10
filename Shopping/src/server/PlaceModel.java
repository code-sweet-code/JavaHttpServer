package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PlaceModel {
	private List<Place> places;
	public static String filepath = ".\\places.txt";
	
	public PlaceModel()
	{
		places = new LinkedList<Place>();
		loadPlaces();
	}
	
	private synchronized void loadPlaces(){
		
		try {
			File file = new File(filepath);
			file.createNewFile();
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			String line = reader.readLine();
			String[] placeName;
			if(line != null){
				placeName = line.split(";");
			}else{
				placeName = new String[0];
			}
				for(int i=0; i<placeName.length; i++)
				{
					Place place = new Place();
					place.setName(placeName[i]);
					places.add(place);
				}
			
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Place> getAllPlaces(){
		return places;
	}
	
	public boolean removePlace(String name){
		boolean removed = false;
		for(int i=0; i<places.size(); i++)
		{
			if(places.get(i).getName().equals(name))
			{
				places.remove(i);
				removed = true;
			}
		}
		
		return removed;
	}
	
	public void addPlace(Place p){
		for(int i=0; i<places.size(); i++)
		{
			if(places.get(i).getName().equals(p.getName()))
			{
				return;
			}
			
		}
		places.add(p);	
	}
	
	public synchronized void saveToFile(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
			StringBuilder builder = new StringBuilder();
			for(int i=0; i<places.size(); i++)
			{
				builder.append(places.get(i).getName());
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
