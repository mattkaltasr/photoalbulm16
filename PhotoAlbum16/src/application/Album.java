package application;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author matt kalita and yigit gungor 
 *
 */
public class Album implements Serializable {
	
	private String name;
	public ArrayList<Photo> photos;
	/**
	 * default constructor 
	 */
	public Album() {
		
	}
	
	/**
	 * name arg constructor 
	 * @param s
	 */
	public Album(String s) {
		name = s;
		photos = new ArrayList<Photo>();
	}
	
	/**
	 * returns name of album 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * returns array list of photos
	 * @return
	 */
	public ArrayList<Photo> getPhotos() {
		return photos;
	}
	
	/**
	 * void takes a array of photos and adds to object 
	 * @param photos
	 */
	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}
	
	/**
	 * changes albums name 
	 * @param n
	 */
	public void changeName(String n) {
		name = n;
	}
}
