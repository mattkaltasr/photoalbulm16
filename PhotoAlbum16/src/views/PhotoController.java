package views;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import application.Main;
import application.Photo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
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
	
	Photo p = null;
	
	public void start(Stage mainStage) throws Exception {      
		initialize();
	}
	
	@FXML
	public void initialize(){
		p = AlbumPhotosPageController.currentphoto;
		if(p != null){
		photoImgView.setImage(p.getImage());
		nameLabel.setText(p.getName());
		tagsLabel.setText(p.getTagDisplay());
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
	
	@FXML
	public void addTagButton_Clicked(ActionEvent event) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("New Tag");
		dialog.setHeaderText("Add a new tag");
		dialog.setContentText("Tag: ");
		Optional<String> result = dialog.showAndWait();

		String tag = null;
		if(result.isPresent()){
			tag = result.get();
		}
		
		if(tag != null && !p.getTagDisplay().contains("tag")){
			// ADD THE TAG TO THE PHOTO P
			// UPDATE THE FILE/SERIALIZED OBJECT TOO
			// ALSO UPDATE "AlbumPhotosPageController.currentphoto"
			initialize();
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Something is wrong.");
			alert.setContentText("This tag either exists or we can't find the photo you want to edit.");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void removeTagButton_Clicked(ActionEvent event) throws IOException {
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Remove Tag");
		dialog.setHeaderText("Remove a tag");
		dialog.setContentText("Which tag do you want to remove?: ");
		Optional<String> result = dialog.showAndWait();

		String tag = null;
		if(result.isPresent()){
			tag = result.get();
		}
		
		if(tag != null && p.getTagDisplay().contains("tag")){
			// REMOVE THE TAG TO THE PHOTO P
			// UPDATE THE FILE/SERIALIZED OBJECT TOO
			// ALSO UPDATE "AlbumPhotosPageController.currentphoto"
			initialize();
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Something is wrong.");
			alert.setContentText("This tag either doesn't exist or we can't find the photo you want to edit.");
			alert.showAndWait();
		}
		
	}
	
}
