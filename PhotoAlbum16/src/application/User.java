package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import application.Role;

/**
 * @author matt kalita and Yigit Gungor 
 *
 */
public class User implements Serializable{
 private final Role user=Role.normal;
	private String username;
	private String password;
	private Role role;
	private ArrayList<Album> albums;
	
	
	/**
	 * User contructor 
	 * @param username
	 * @param password
	 * @param role
	 */
	public User(String username,String password,Role role){
		this.username = username;
		this.password = password;
		this.role = role;
		albums = new ArrayList<Album>();
	}
	
	/**
	 * two arg constructor 
	 * @param username
	 * @param Password
	 */
	public User(String username,String Password){
		this.username = username;
		this.password = Password;
		this.role = user;
		albums = new ArrayList<Album>();
		
		
	}
	
	/**
	 * tostring override 
	 */
	public String toString(){
		return "Username: " + username + "\n" + "Password: " + password;
	}
	
	
	/**
	 * method to return role of user 
	 * @return
	 */
	public Role getRole(){
		return this.role;
	}
	
	/**
	 * method to return user name 
	 * @return
	 */
	public String getUserName(){
		return this.username;
	}
	
	/**
	 * method to return password 
	 * @return
	 */
	public String getPassword(){
		return password;
	}
	
	
	/**
	 * method to return user dir 
	 * @return
	 */
	public String getUserDirectory(){
		System.out.println("user dir =  " + System.getProperty("user.dir") + File.separator +"src" + File.separator + "data" + File.separator+ this.username);
		return System.getProperty("user.dir") + File.separator +"src" + File.separator + "data" + File.separator+ this.username;
	}
	
	
	/**
	 * method that authenticates user 
	 * @param entered_username
	 * @param entered_password
	 * @return
	 */
	public Boolean Authenticate(String entered_username,String entered_password){

		System.out.println("authenticating this username: " + this.username + " this pass: " + this.password);
		if(this.username.equals(entered_username) && this.password.equals(entered_password)){
			System.out.println("correct credential");
			return true;
		}
		
		return false;		
	}
	
	
	
	
	//serilize data 
	/**
	 *  method used in writing object to drive 
	 */
	public void writeApp(){
		try  {
	        FileOutputStream fileOut = new FileOutputStream("data/"+File.separator + username + ".dat");
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(this);
	        out.close();
	        fileOut.close();
	    } catch(Exception e) {
	    	//e.printStackTrace();
	    	System.out.println("Invalid serialization.");
	    }
		
		
		
	}
	
	/**
	 * returns list of albums 
	 * @return
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
	}
}
