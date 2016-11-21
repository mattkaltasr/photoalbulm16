package application;

import java.util.ArrayList;
import application.Role;

public class Administration extends User {
	
	private final static Role Admin=Role.admin;
	ArrayList<User> materUserList;
	
	
	
	
	
	public Administration(String user,String pass){
		super(user,pass,Admin);
		
		materUserList=new ArrayList<User>();
	}

	
	
	
	
	public ArrayList<User> getList(){
		
		return materUserList;
	}
}
