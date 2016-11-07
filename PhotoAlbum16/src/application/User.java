package application;

import java.io.File;

public class User {

	private String username;
	private String password;
	private Role role;
	
	public User(String username,String password,Role role){
		this.username = username;
		this.password = password;
		this.role = role;
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
}
