package views;

import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import application.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class AdministrationController {
	@FXML
	private ListView<User> usersListView;
	@FXML
	private Button addButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button logOutButton;
	
	ObservableList<User> obsList;


	public void start(Stage mainStage) throws Exception {      
	
	}
	
	@FXML
	public void initialize(){
	      
			obsList = FXCollections.observableList(Main.UserList);
			  
			usersListView.setItems(obsList);

		    usersListView.getSelectionModel().select(0);
	}
	
	@FXML
	public void addButton_Clicked(ActionEvent event) {
		
	}

	@FXML
	public void deleteButton_Clicked(ActionEvent event) {
		
	}
	
	@FXML
	public void logoutButton_Clicked(ActionEvent event) throws IOException {
		
		 Stage stage;
	     Parent root;     
	     
	     stage=(Stage) logOutButton.getScene().getWindow();
	     root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));

	     Scene scene = new Scene(root);
	     stage.setScene(scene);
	     stage.show();
		
	}
	

	
	//CALL AFTER ADD - DELETE
	public void refresh() throws Exception{
		Main.UserList = new ArrayList<User>();
		Main.readDataFrom(System.getProperty("user.dir") + "/src/data/users.csv");
		initialize();
	}
}
