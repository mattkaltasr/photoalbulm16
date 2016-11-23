package views;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * @author matt kalita anf yigit gungor 
 *
 */
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
	@FXML
	private Button moveButton;
	@FXML
	private Button changeNameButton;
	@FXML
	private Button copyButton;
	public static boolean search;

	private String tag_display;
	
	/**
	 * starts main stage 
	 * @param mainStage
	 * @throws Exception
	 */
	public void start(Stage mainStage) throws Exception {      
		initialize();
	}
	/**
	 * initializes controller 
	 */
	@FXML
	public void initialize(){
		Main.photo = AlbumPhotosPageController.currentphoto;		
		if(Main.photo != null){
		photoImgView.setImage(Main.photo.getImage());
		nameLabel.setText(Main.photo.getName());
		tagsLabel.setText(Main.photo.getTagDisplay());
		}
	}
	/**
	 * change name button 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void changenameButton_Clicked(ActionEvent event) throws IOException{
		File pic = new File(Main.currentUser.getUserDirectory() + File.separator + AlbumsPageController.currentAlbum + File.separator + AlbumPhotosPageController.currentphoto.getName());
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Rename Photp");
		dialog.setHeaderText("What do you want to change the caption to?");
		dialog.setContentText("Name: ");
		Optional<String> result = dialog.showAndWait();

		String newcaption = pic.getName();
		if(result.isPresent()){
			newcaption = result.get();
			newcaption = newcaption + ".jpeg";
		}		
		
		pic.renameTo(new File(Main.currentUser.getUserDirectory() + File.separator + AlbumsPageController.currentAlbum + File.separator + newcaption));
		backButton_Clicked(event);
	}
	
	/**
	 * move button method 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void moveButton_Clicked(ActionEvent event) throws IOException {
		File pic = new File(Main.currentUser.getUserDirectory() + File.separator + AlbumsPageController.currentAlbum + File.separator + AlbumPhotosPageController.currentphoto.getName());
				
		File folder = new File(Main.currentUser.getUserDirectory());
		String[] albums= folder.list(new FilenameFilter(){
			@Override
				public boolean accept(File current, String name){
				return new File(current,name).isDirectory();
			}
			});
		
		List<String> dialogData = Arrays.asList(albums);		
		
		ChoiceDialog dialog = new ChoiceDialog(dialogData.get(0),dialogData);
		dialog.setTitle("Move Photo");
		dialog.setHeaderText("Where do you want to move this photo to?");
		dialog.setContentText("Album: ");
		Optional<String> result = dialog.showAndWait();

		String newalbum = AlbumsPageController.currentAlbum;
		if(result.isPresent()){
			newalbum = result.get();
		}	
		
		
		File newpic = new File(Main.currentUser.getUserDirectory() + File.separator + newalbum + File.separator + AlbumPhotosPageController.currentphoto.getName());
		
		Files.move(pic.toPath(), newpic.toPath(), StandardCopyOption.REPLACE_EXISTING);
		backButton_Clicked(event);
	}
	
	/**
	 * copy button watcher method 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void copyButton_Clicked(ActionEvent event) throws IOException {
		File pic = new File(Main.currentUser.getUserDirectory() + File.separator + AlbumsPageController.currentAlbum + File.separator + AlbumPhotosPageController.currentphoto.getName());
		
		File folder = new File(Main.currentUser.getUserDirectory());
		String[] albums= folder.list(new FilenameFilter(){
			@Override
				public boolean accept(File current, String name){
				return new File(current,name).isDirectory();
			}
			});
		
		List<String> dialogData = Arrays.asList(albums);		
		
		ChoiceDialog dialog = new ChoiceDialog(dialogData.get(0),dialogData);
		dialog.setTitle("Copy Photo");
		dialog.setHeaderText("Where do you want to copy this photo to?");
		dialog.setContentText("Album: ");
		Optional<String> result = dialog.showAndWait();

		String newalbum = AlbumsPageController.currentAlbum;
		if(result.isPresent()){
			newalbum = result.get();
		}	
		
		
		File newpic = new File(Main.currentUser.getUserDirectory() + File.separator + newalbum + File.separator + AlbumPhotosPageController.currentphoto.getName());
		
		Files.copy(pic.toPath(), newpic.toPath(), StandardCopyOption.REPLACE_EXISTING);
		backButton_Clicked(event);
	}
	
	/**
	 * back button listner method 
	 * @param event
	 * @throws IOException
	 */
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
	/**
	 * delete button method 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void deleteButton_Clicked(ActionEvent event) throws IOException {
		//DELETE
		File pic = new File(Main.currentUser.getUserDirectory() + File.separator + AlbumsPageController.currentAlbum + File.separator + AlbumPhotosPageController.currentphoto.getName());
		pic.delete();
		backButton_Clicked(event);
	}
	
	/**
	 * adds tag to photo 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void addTagButton_Clicked(ActionEvent event) throws IOException {
 		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("New Tag");
		dialog.setHeaderText("Add a new tag");
		dialog.setContentText("What is the key for this tag? ");
		Optional<String> result = dialog.showAndWait();

		String entered_key = null;
		if(result.isPresent()){
			entered_key = result.get();
		}
		
		TextInputDialog tag_dialog = new TextInputDialog();
		tag_dialog.setTitle("New Tag");
		tag_dialog.setHeaderText("Add a new tag");
		tag_dialog.setContentText("Enter the tag: ");
		Optional<String> result_tag = tag_dialog.showAndWait();
		

		String entered_tag = null;
		if(result_tag.isPresent()){
			entered_tag = result_tag.get();
		}
		
		if(entered_tag != null && entered_key != null && !Main.photo.getTagDisplay().contains("tag")){
			
			String key = entered_key;  //need two input these two values upon add button hit 
			String value = entered_tag;
			System.out.print("Key :"+key +" tag :"+value);
			if (key.trim().length() == 0 || value.trim().length() == 0) {
				Alert error = new Alert(AlertType.ERROR);
				error.setHeaderText("Error!");
				error.setContentText("Both key and value are required!");
				error.show();
				return;
			}
			ArrayList<String> vals;
			if (AlbumPhotosPageController.currentphoto.getPhotoTags().containsKey(key)) {
				vals = AlbumPhotosPageController.currentphoto.getPhotoTags().get(key);
			}
			else {
				vals = new ArrayList<String>();
			AlbumPhotosPageController.currentphoto.getPhotoTags().put(key, vals);
			}
			AlbumPhotosPageController.currentphoto.getPhotoTags().get(key).add(value);
			//tag_display = Main.photo.getTagDisplay(); //need to get tag display and update 
			//tags.setText("Tags - " + tag_display);
			// ADD THE TAG TO THE PHOTO P
			// UPDATE THE FILE/SERIALIZED OBJECT TOO
			//AlbumPhotosPageController.currentphoto=Main.photo;
			//Main.regular_user.writeApp();
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
	/**
	 * removes tag from photo 
	 * @param event
	 * @throws IOException
	 */
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
		
		if(tag != null && Main.photo.getTagDisplay().contains("tag")){
			// REMOVE THE TAG TO THE PHOTO P
			// UPDATE THE FILE/SERIALIZED OBJECT TOO
			// ALSO UPDATE "AlbumPhotosPageController.currentphoto"
			
			Photo photo = Main.photo;
			HashMap<String, ArrayList<String>> taglist = photo.getPhotoTags();
			if(tag.length() > 0){
				String key = tag.trim();
				while(taglist.remove(key) != null);
			} 
			//tag_display = Main.photo.getTagDisplay();  needs to refresh list ?
			//tags.setText("Tags - " + tag_display);
			//AlbumPhotosPageController.currentphoto=Main.photo;
			Main.regular_user.writeApp();
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
