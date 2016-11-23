package views;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import application.Main;
import application.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * @author matt kalita and Yigit Gungor 
 *
 */
public class AdministrationController {
	@FXML
	private ListView<User> usersListView;
	@FXML
	private Button newUserButton;
	@FXML
	private AnchorPane newuserpane;
	@FXML
	private TextField usernameText;
	@FXML
	private TextField userpassText;
	@FXML
	private Button addButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button logOutButton;
	
	ObservableList<User> obsList;

/**
 * sets main stage 
 * @param mainStage
 * @throws Exception
 */
	public void start(Stage mainStage) throws Exception {      
	
	}
	/**
	 * initalize userlist 
	 */
	@FXML
	public void initialize(){
	      
			usersListView.setItems(null);
		
			obsList = FXCollections.observableList(Main.admin.getList());
			  
			usersListView.setItems(obsList);

		    usersListView.getSelectionModel().select(0);
	}
	
/**
 * creates new user on click 
 * @param event
 * @throws Exception
 */
	@FXML
	public void createnewuserButton_Clicked(ActionEvent event) throws Exception {
		newuserpane.setVisible(true);
	}
	
	
	/**
	 * add user on click 
	 * @param event
	 * @throws Exception
	 */
	@FXML
	public void addButton_Clicked(ActionEvent event) throws Exception {

		String newuser_name = usernameText.getText();
		String newuser_pass = userpassText.getText();
		for(User u : Main.UserList){
			if(u.getUserName().equals(newuser_name)){
				userpassText.setText("");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("User already exists.");
				alert.setContentText("This user already exists, try another username.");
				alert.showAndWait();
				return;
			}			
		}
		User newuser = new User(newuser_name,newuser_pass);
		Main.UserList.add(newuser);
		newuser.writeApp();
		
		String uri = newuser.getUserDirectory();
		File dir = new File(uri);
		  if(!dir.exists()){
		     dir.mkdir();
		  }
				
				  
		userpassText.setText("");
		usernameText.setText("");
		newuserpane.setVisible(true);
		refresh();
	}
/**
 * delete
 * button for user 
 * @param event
 * @throws Exception
 */
	@FXML
	public void deleteButton_Clicked(ActionEvent event) throws Exception {
		

		User u = usersListView.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("You are deleting user " + u.getUserName());
		alert.setContentText("Are you ok with this?");

		Optional<ButtonType> confirmation_result = alert.showAndWait();
		if (confirmation_result.get() == ButtonType.OK){

			
			//RIGHT NOW WON'T DELETE THE USER FILES SO IF THEY RECREATE THE ACCOUNT THEY CAN RETRIEVE THEIR DATA
			//THIS CAN BE CHANGED
			//ALBUM DELETING LINES ARE COMMENTED OUT CAN BE REUSED FOR USERS IF NEEDED
			
			//File dir = new File(Main.currentUser.getUserDirectory() + File.separator + albumname);
			//DeleteAlbum(dir);
			try {
				Files.delete(Paths.get("data/" + u.getUserName() + ".dat"));
				
				
			} catch (Exception e1) {
				System.out.println("Invalid serialized file to be deleted.");
			}
			Main.UserList.remove(u);
		}
		else {
		}
		
		refresh();
	}
	/**
	 * logout button action 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void logoutButton_Clicked(ActionEvent event) throws IOException {
		
		 Stage stage;
	     Parent root;     
	     
	     stage=(Stage) logOutButton.getScene().getWindow();
	     Main.currentUser.writeApp();
	     root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));

	     Scene scene = new Scene(root);
	     stage.setScene(scene);
	     stage.show();
		
	}
	

	/**
	 * refreshes list 
	 * @throws Exception
	 */
	//CALL AFTER ADD - DELETE
	public void refresh() throws Exception{
		initialize();
	}
}
