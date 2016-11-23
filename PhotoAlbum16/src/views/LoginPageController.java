package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import application.Main;
import application.User;
import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

/**
 * @author matt kalita and yigit gungor 
 *
 */
public class LoginPageController {
	@FXML
	private TextField user_tb;
	@FXML
	private PasswordField pass_tb;
	@FXML
	private Button login_btn;
	

	/**
	 * start method 
	 * @param mainStage
	 * @throws Exception
	 */
	public void start(Stage mainStage) throws Exception {      
	      
	   }

	
	// Event Listener on Button[#login_btn].onAction
	/**
	 * login click watcher 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void Login_Clicked(ActionEvent event) throws IOException {

		System.out.println("login clicked");

		System.out.println("user_tb " + user_tb.getText());
		System.out.println("pass_tb " + pass_tb.getText());

		if(user_tb.getText().equals("admin") && pass_tb.getText().equals("admin"))
		{
			 Stage stage; 
		     Parent root;     
		     
		     stage=(Stage) login_btn.getScene().getWindow();
		     root = FXMLLoader.load(getClass().getResource("Administration.fxml"));
		     
		     Scene scene = new Scene(root);
		     stage.setScene(scene);
		     stage.show();
		     return;
		}
		else{
			for(User u : Main.UserList)
			{
				System.out.println("itterating, current user:" + u.getUserName());
				if(u.Authenticate(user_tb.getText(), pass_tb.getText()))
				{
					// FIND FILE NAMED u.getUserName() in /data/
					// read the albums&photos from the directory of the user
					// Switch to Album View
	
					System.out.println("authenticated " + u.getUserName());
					Main.currentUser = u;
					 Stage stage; 
				     Parent root;     
				     
				     stage=(Stage) login_btn.getScene().getWindow();
				     root = FXMLLoader.load(getClass().getResource("AlbumsPage.fxml"));
	
				     Scene scene = new Scene(root);
				     stage.setScene(scene);
				     stage.show();
				     return;
				}
			}

		pass_tb.setText("");
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Invalid username or password.");
		alert.setContentText("The username or password you entered is incorrect, please try again.");
		alert.showAndWait();
		}
	}

}
