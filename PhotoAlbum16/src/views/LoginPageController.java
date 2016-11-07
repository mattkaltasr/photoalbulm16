package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import application.Main;
import application.User;
import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

public class LoginPageController {
	@FXML
	private TextField user_tb;
	@FXML
	private PasswordField pass_tb;
	@FXML
	private Button login_btn;
	@FXML
	private Button albumtest_btn;

	
	public void start(Stage mainStage) throws Exception {      
	      
	   }

	
	// Event Listener on Button[#login_btn].onAction
	@FXML
	public void Login_Clicked(ActionEvent event) throws IOException {

		System.out.println("login clicked");
		for(User u : Main.UserList)
		{



			System.out.println("itterating " + u.getUserName());
			System.out.println("user_tb " + user_tb.getText());
			System.out.println("pass_tb " + pass_tb.getText());
			if(u.Authenticate(user_tb.getText(), pass_tb.getText()))
			{
				// FIND FILE NAMED u.getUserName() in /data/
				// read the albums&photos from the directory of the user
				// Switch to Album View

				System.out.println("authenticated " + u.getUserName());
				Main.currentUser = u;
				 Stage stage; 
			     Parent root;     
			     
			     stage=(Stage) albumtest_btn.getScene().getWindow();
			     root = FXMLLoader.load(getClass().getResource("AlbumsPage.fxml"));

			     Scene scene = new Scene(root);
			     stage.setScene(scene);
			     stage.show();
				break;
			}
			else
			{
				//ERROR
			}
		}
		if(user_tb.getText() == "admin" && pass_tb.getText() == "pass")
		{
			// SWITCH TO ADMIN VIEW
			// CREATE NEW FOLDERS UNDER /data/ FOR EACH USER
			
		}
	}
	// Event Listener on Button[#albumtest_btn].onAction
	@FXML
	public void Test_Clicked(ActionEvent event) throws IOException {
		 Stage stage; 
	     Parent root;     
	     
	     stage=(Stage) albumtest_btn.getScene().getWindow();
	     root = FXMLLoader.load(getClass().getResource("AlbumsPage.fxml"));

	     Scene scene = new Scene(root);
	     stage.setScene(scene);
	     stage.show();
		
	}
}
