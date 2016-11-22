package application;

import java.util.ArrayList;
import application.Role;

/**
 * @author matt kalita and Yigit Gungor 
 *
 */
public class Administration extends User {
	
	private final static Role Admin=Role.admin;
	ArrayList<User> materUserList;
	
	
	
	
	/**
	 * constructor 
	 * @param user
	 * @param pass
	 */
	public Administration(String user,String pass){
		super(user,pass,Admin);
		
		materUserList=new ArrayList<User>();
	}

	
	
	
	/**
	 * method to return the array list of users 
	 * @return
	 */
	public ArrayList<User> getList(){
		
		return materUserList;
	}
}
