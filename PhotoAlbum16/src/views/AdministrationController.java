package views;

import java.util.Collections;

import application.Main;
import application.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class AdministrationController {

	@FXML
	private ListView<User> usersListView;
	
	private ObservableList<User> obsList;
	
	public void start(Stage mainStage) throws Exception {      
	      
		obsList = FXCollections.observableList(Main.UserList);
		  
		usersListView.setItems(obsList);

	    usersListView.getSelectionModel().select(0);
	}

}
