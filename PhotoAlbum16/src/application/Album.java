package application;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
	
	private String name;
	private ArrayList<Photo> photos;
	
	public Album() {
		
	}
	
	
	public Album(String s) {
		name = s;
		photos = new ArrayList<Photo>();
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public ArrayList<Photo> getPhotos() {
		return photos;
	}
	
	
	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}
	
	
	public void changeName(String n) {
		name = n;
	}
}
