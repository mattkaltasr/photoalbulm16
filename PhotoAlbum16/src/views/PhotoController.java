package views;

import java.io.File;
import java.io.IOException;

import application.Main;
import application.Photo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PhotoController {
	@FXML
	private ImageView photoImgView;
	@FXML
	private Button backButton;
	@FXML
	private Label nameLabel;
	@FXML
	private Label tagsLabel;
	@FXML
	private Button deleteButton;
	
	public void start(Stage mainStage) throws Exception {      
		initialize();
	}
	
	@FXML
	public void initialize(){
		Photo p = AlbumPhotosPageController.currentphoto;
		if(p != null){
		photoImgView.setImage(p.getImage());
		nameLabel.setText(p.getName());
		//GET TAGS FROM HASHMAP
		tagsLabel.setText("");
		}
	}
	
	
	@FXML
	public void backButton_Clicked(ActionEvent event) throws IOException {
		
		 Stage stage;
	     Parent root;     
	     
	     stage=(Stage) backButton.getScene().getWindow();
	     root = FXMLLoader.load(getClass().getResource("AlbumPhotosPage.fxml"));

	     Scene scene = new Scene(root);
	     stage.setScene(scene);
	     stage.show();
		
	}
	
	@FXML
	public void deleteButton_Clicked(ActionEvent event) throws IOException {
		//DELETE
		File pic = new File(Main.currentUser.getUserDirectory() + File.separator + AlbumsPageController.currentAlbum + File.separator + AlbumPhotosPageController.currentphoto.getName());
		pic.delete();
		backButton_Clicked(event);
	}
	
	
	
	
}
