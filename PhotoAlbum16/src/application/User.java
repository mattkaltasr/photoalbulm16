package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable{

	private String username;
	private String password;
	private Role role;
	
	public User(String username,String password,Role role){
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public String toString(){
		return "Username: " + username + "\n" + "Password: " + password;
	}
	
	public Role getRole(){
		return this.role;
	}
	public String getUserName(){
		return this.username;
	}
	public String getUserDirectory(){
		System.out.println("user dir =  " + System.getProperty("user.dir") + File.separator +"src" + File.separator + "data" + File.separator+ this.username);
		return System.getProperty("user.dir") + File.separator +"src" + File.separator + "data" + File.separator+ this.username;
	}
	
	public Boolean Authenticate(String entered_username,String entered_password){

		System.out.println("authenticating this username: " + this.username + " this pass: " + this.password);
		if(this.username.equals(entered_username) && this.password.equals(entered_password)){
			System.out.println("correct credential");
			return true;
		}
		
		return false;		
	}
	
	
	
	
	//serilize data 
	public void writeApp(){
		try {
	        FileOutputStream fileOut = new FileOutputStream("data/" + username + ".cvs");
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(this);
	        out.close();
	        fileOut.close();
	    } catch(Exception e) {
	    	System.out.println("not valid  serialization.");
	    }
		
	}
}
