package application;
	
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Main extends Application {
	public static Administration admin = new Administration("admin","password");
	public static ArrayList<User> UserList;
	public static User currentUser;
	public static ArrayList<Photo> searchReturn;
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/views/LoginPage.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Photo Album");
			primaryStage.show();
			searchReturn= new ArrayList<Photo>();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		UserList = new ArrayList<User>();
		readDataFrom(System.getProperty("user.dir") + "/src/data/users.csv");
		launch(args);
	}
	
	
	public static void readDataFrom(String uri) throws Exception{
		
		BufferedReader br;
		try{
		br = new BufferedReader(new FileReader(uri));
		}
		catch(FileNotFoundException err){
			File file = new File(uri);
			  if(!file.exists()){
			     file.getParentFile().mkdirs();
			     file.createNewFile();
			  }
			
			br = new BufferedReader(new FileReader(uri));		
		}

	    String line = null;	    
	    while ((line = br.readLine()) != null) {

	    	if(line != ""){

	      String[] values = line.split(",");

	      if(values[2] == "admin")
	    	  UserList.add(new User(values[0],values[1],Role.admin));
	      else
	    	  UserList.add(new User(values[0],values[1],Role.normal));

	    	}
	   	}
	    br.close();
	    
		
	}
	
	
	// gets files from file 
	
	public User getUserFile(String fileName){
		User temp=null;
		try{
			FileInputStream input = new FileInputStream("data/"+fileName);
			ObjectInputStream inTemp=new ObjectInputStream(input);
			temp=(User)inTemp.readObject();
			inTemp.close();
			input.close();
		}catch(Exception e ){
			System.out.println(" not a valid deserialization!");
		}
		return temp;
	}
	
	
	
	//gets list for running 
	//of program 
	
	public void getUserList(){
		
		File dir = new File ("data");
		File[]  directoryListing= dir.listFiles();
		
		if (directoryListing!=null){
			for (File temp : directoryListing){
				String name=temp.getName();
				if(name.toLowerCase().contains(".csv")){
					User tempUser=getUserFile(name);
					admin.getList().add(tempUser);
					
				}
			}
		}
		
	}
	
}
