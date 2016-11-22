package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.io.*;

import java.time.ZoneId;
import java.util.*;

import application.Album;
import application.Main;
import application.Photo;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class AlbumsPageController {
	
	public static String currentAlbum;
	public static ArrayList searchResults;
	
	@FXML
	private TilePane TilePane;
	@FXML
	private Button logoutButton;
	
	public String[] albums;
	
	@FXML
	public void initialize(){

		currentAlbum = null;
		searchResults = null;
		//albums = null;
		System.out.println("Albums page started");
		File folder = new File(Main.currentUser.getUserDirectory());
		albums= folder.list(new FilenameFilter(){
			@Override
				public boolean accept(File current, String name){
				return new File(current,name).isDirectory();
			}
			});
		//System.out.println("Albums found: " + albums.toString());
		try{
		for(int i = 0; i<albums.length; i++){

			System.out.println("Adding to Tilepane");
			Rectangle r = new Rectangle(300,200);
			Label l = new Label(albums[i]);
			l.setTextFill(Color.WHITE);
			StackPane tileStack = new StackPane(r,l);
			System.out.println(tileStack.getChildren().toString());
			
			tileStack.setOnMouseClicked(new EventHandler<MouseEvent>(){
				Stage stage; 
			    Parent root;
				@Override
				public void handle(MouseEvent event) {
					stage=(Stage) tileStack.getScene().getWindow();
			    	StackPane albumTile = (StackPane) event.getSource();
			 		String albumName = ((Label) albumTile.getChildren().get(1)).getText();
			 		currentAlbum = albumName;
			    	try {
						root = FXMLLoader.load(getClass().getResource("AlbumPhotosPage.fxml"));
					     Scene scene = new Scene(root);
					     stage.setScene(scene);
					     stage.show();					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			     
			});
			TilePane.getChildren().addAll(tileStack);
		}

		}catch(Exception e){}
	}
	
	public void start(Stage mainStage) throws Exception {  
	      
	   }
	
	
	@FXML
	public void Search_Clicked(ActionEvent event) throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("New Search");
		dialog.setHeaderText("What do you want to search for?");
		Optional<String> result = dialog.showAndWait();

		String searchquery = null;
		if(result.isPresent()){
			searchquery = result.get();
		}
		
		if(searchquery != null){
			Main.searchReturn = new ArrayList<Photo>();
//need dialog for these terms
			LocalDate startDate = dialog.getStartDate();
			LocalDate endDate = dialog.getEndDate();
			String key = dialog.getKey().trim().toLowerCase();
			String value = dialog.getValue().trim().toLowerCase();
			if (startDate == null && endDate == null && key.length() == 0 && value.length() == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Something went wrong we couldn't find any photos related to " + searchquery);
				alert.showAndWait();
				return;
			}
				
			
				Album a = Main.album;
				ArrayList<Photo> photos = a.getPhotos();
				for(int i = 0; i < photos.size(); i++){
					File file = photos.get(i).getFile();
					Date d = new Date(file.lastModified());
					LocalDate date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if(startDate == null && endDate == null){
						if(key.length() > 0 && value.length() > 0){
							for (String k : photos.get(i).getPhotoTags().keySet()) {
								if (k.contains(key) && photos.get(i).getPhotoTags().get(k).contains(value)) {
									boolean duplicate = false;
									for(Photo p: Main.searchReturn){
										if(p.getImage().equals(photos.get(i).getImage())){
											duplicate = true;
										}
									}
									if(duplicate == false){
										Main.searchReturn.add(photos.get(i));
									}
								}
							}
						} else if (key.length() > 0 && value.length() == 0) {
							for (String k : photos.get(i).getPhotoTags().keySet()) {
								if (k.contains(key)) {
									boolean duplicate = false;
									for(Photo p: Main.searchReturn){
										if(p.getImage().equals(photos.get(i).getImage())){
											duplicate = true;
										}
									}
									if(duplicate == false){
										Main.searchReturn.add(photos.get(i));
									}
								}
							}
						} else if (value.length() > 0 && key.length() == 0) {
							for (String k : photos.get(i).getPhotoTags().keySet()) {
								if (photos.get(i).getPhotoTags().get(k).contains(value)) {
									boolean duplicate = false;
									for(Photo p: Main.searchReturn){
										if(p.getImage().equals(photos.get(i).getImage())){
											duplicate = true;
										}
									}
									if(duplicate == false){
										Main.searchReturn.add(photos.get(i));
									}
								}
							}
						} else {
							boolean duplicate = false;
							for(Photo p: Main.searchReturn){
								if(p.getImage().equals(photos.get(i).getImage())){
									duplicate = true;
								}
							}
							if(duplicate == false){
								Main.searchReturn.add(photos.get(i));
							}
						}
					} else if(startDate == null && endDate != null){
						if(date.compareTo(endDate) <= 0){
							if(key.length() > 0 && value.length() > 0){
								for (String k : photos.get(i).getPhotoTags().keySet()) {
									if (k.contains(key) && photos.get(i).getPhotoTags().get(k).contains(value)) {
										boolean duplicate = false;
										for(Photo p: Main.searchReturn){
											if(p.getImage().equals(photos.get(i).getImage())){
												duplicate = true;
											}
										}
										if(duplicate == false){
											Main.searchReturn.add(photos.get(i));
										}
									}
								}
							} else if (key.length() > 0 && value.length() == 0) {
								for (String k : photos.get(i).getPhotoTags().keySet()) {
									if (k.contains(key)) {
										boolean duplicate = false;
										for(Photo p: Main.searchReturn){
											if(p.getImage().equals(photos.get(i).getImage())){
												duplicate = true;
											}
										}
										if(duplicate == false){
											Main.searchReturn.add(photos.get(i));
										}
									}
								}
							} else if (value.length() > 0 && key.length() == 0) {
								for (String k : photos.get(i).getPhotoTags().keySet()) {
									if (photos.get(i).getPhotoTags().get(k).contains(value)) {
										boolean duplicate = false;
										for(Photo p: Main.searchReturn){
											if(p.getImage().equals(photos.get(i).getImage())){
												duplicate = true;
											}
										}
										if(duplicate == false){
											Main.searchReturn.add(photos.get(i));
										}
									}
								}
							} else {
								boolean duplicate = false;
								for(Photo p: Main.searchReturn){
									if(p.getImage().equals(photos.get(i).getImage())){
										duplicate = true;
									}
								}
								if(duplicate == false){
									Main.searchReturn.add(photos.get(i));
								}
							}
						}
					} else if (endDate == null && startDate != null){
						if(date.compareTo(startDate) >= 0){ 
							if(key.length() > 0 && value.length() > 0){
								for (String k : photos.get(i).getPhotoTags().keySet()) {
									if (k.contains(key) && photos.get(i).getPhotoTags().get(k).contains(value)) {
										boolean duplicate = false;
										for(Photo p: Main.searchReturn){
											if(p.getImage().equals(photos.get(i).getImage())){
												duplicate = true;
											}
										}
										if(duplicate == false){
											Main.searchReturn.add(photos.get(i));
										}
									}
								}
							} else if (key.length() > 0 && value.length() == 0) {
								for (String k : photos.get(i).getPhotoTags().keySet()) {
									if (k.contains(key)) {
										boolean duplicate = false;
										for(Photo p: Main.searchReturn){
											if(p.getImage().equals(photos.get(i).getImage())){
												duplicate = true;
											}
										}
										if(duplicate == false){
											Main.searchReturn.add(photos.get(i));
										}
									}
								}
							} else if (value.length() > 0 && key.length() == 0) {
								for (String k : photos.get(i).getPhotoTags().keySet()) {
									if (photos.get(i).getPhotoTags().get(k).contains(value)) {
										boolean duplicate = false;
										for(Photo p: Main.searchReturn){
											if(p.getImage().equals(photos.get(i).getImage())){
												duplicate = true;
											}
										}
										if(duplicate == false){
											Main.searchReturn.add(photos.get(i));
										}
									}
								}
							} else {
								boolean duplicate = false;
								for(Photo p: Main.searchReturn){
									if(p.getImage().equals(photos.get(i).getImage())){
										duplicate = true;
									}
								}
								if(duplicate == false){
									Main.searchReturn.add(photos.get(i));
								}
							}
						}
					} else {
						if(date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0){ 
							if(key.length() > 0 && value.length() > 0){
								for (String k : photos.get(i).getPhotoTags().keySet()) {
									if (k.contains(key) && photos.get(i).getPhotoTags().get(k).contains(value)) {
										boolean duplicate = false;
										for(Photo p: Main.searchReturn){
											if(p.getImage().equals(photos.get(i).getImage())){
												duplicate = true;
											}
										}
										if(duplicate == false){
											Main.searchReturn.add(photos.get(i));
										}
									}
								}
							} else if (key.length() > 0 && value.length() == 0) {
								for (String k : photos.get(i).getPhotoTags().keySet()) {
									if (k.contains(key)) {
										boolean duplicate = false;
										for(Photo p: Main.searchReturn){
											if(p.getImage().equals(photos.get(i).getImage())){
												duplicate = true;
											}
										}
										if(duplicate == false){
											Main.searchReturn.add(photos.get(i));
										}
									}
								}
							} else if (value.length() > 0 && key.length() == 0) {
								for (String k : photos.get(i).getPhotoTags().keySet()) {
									if (photos.get(i).getPhotoTags().get(k).contains(value)) {
										boolean duplicate = false;
										for(Photo p: Main.searchReturn){
											if(p.getImage().equals(photos.get(i).getImage())){
												duplicate = true;
											}
										}
										if(duplicate == false){
											Main.searchReturn.add(photos.get(i));
										}
									}
								}
							} else {
								boolean duplicate = false;
								for(Photo p: Main.searchReturn){
									if(p.getImage().equals(photos.get(i).getImage())){
										duplicate = true;
									}
								}
								if(duplicate == false){
									Main.searchReturn.add(photos.get(i));
								}
							}
						}
					}
				}

				 searchResults = Main.searchReturn;
				 Stage stage;
			     Parent root;     
			     
			     stage=(Stage) logoutButton.getScene().getWindow();
			     root = FXMLLoader.load(getClass().getResource("Search.fxml"));

			     Scene scene = new Scene(root);
			     stage.setScene(scene);
			     stage.show();
			}
		}
			
			
		
	
	
	
	@FXML
	public void logoutButton_Clicked(ActionEvent event) throws IOException {
		
		 Stage stage;
	     Parent root;     
	     
	     stage=(Stage) logoutButton.getScene().getWindow();
	     Main.currentUser.writeApp();
	     root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));

	     Scene scene = new Scene(root);
	     stage.setScene(scene);
	     stage.show();
		
	}

	@FXML
	public void AddAlbum_Clicked(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("New Album");
		dialog.setHeaderText("Create a new album");
		dialog.setContentText("Name: ");
		Optional<String> result = dialog.showAndWait();

		String albumname = null;
		if(result.isPresent()){
			albumname = result.get();
		}
		
		if(albums == null){
		File dir = new File(Main.currentUser.getUserDirectory() + File.separator + albumname);
		dir.mkdir();
		}
		else if(albumname != null && !Arrays.asList(albums).contains(albumname)){
			File dir = new File(Main.currentUser.getUserDirectory() + File.separator + albumname);
			dir.mkdir();	
		}
		else
		{
		 //ALBUM EXISTS	
		}
		
		TilePane.getChildren().clear();
		Main.currentUser.writeApp();
		initialize();
	}
	
	@FXML
	public void EditAlbum_Clicked(ActionEvent event) {
		// TODO Autogenerated
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Album Name");
		dialog.setHeaderText("Which album do you want to change?");
		dialog.setContentText("Name: ");
		Optional<String> result = dialog.showAndWait();

		String albumname = null;
		if(result.isPresent()){
			albumname = result.get();
		}
		if(!Arrays.asList(albums).contains(albumname)){
			//error
		}
		else{
			
			TextInputDialog newname_dialog = new TextInputDialog();
			newname_dialog.setTitle("Change Album Name");
			newname_dialog.setHeaderText("You are changing the album: " + albumname);
			newname_dialog.setContentText("New Name: ");
			Optional<String> newname_result = newname_dialog.showAndWait();

			String new_albumname = null;
			if(newname_result.isPresent()){
				new_albumname = newname_result.get();
			}
			
			if(new_albumname != null && !Arrays.asList(albums).contains(new_albumname)){
				RenameAlbum(Main.currentUser.getUserDirectory() + File.separator + albumname,Main.currentUser.getUserDirectory() + File.separator + new_albumname);
			}
			

			TilePane.getChildren().clear();
			Main.currentUser.writeApp();
			initialize();
		}
		
	}
	
	public void RenameAlbum(String oldpath, String newpath){

		File dir = new File(oldpath);
		File newName = new File(newpath);
		
		if ( dir.isDirectory() ) {
			dir.renameTo(newName);
		} else {
			dir.mkdir();
			dir.renameTo(newName);
		}
		Main.currentUser.writeApp();
	}
	
	public void DeleteAlbum(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	            	DeleteAlbum(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
		Main.currentUser.writeApp();
	    folder.delete();
	}

	@FXML
	public void DeleteAlbum_Clicked(ActionEvent event) {
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Delete Album");
		dialog.setHeaderText("Which album do you want to delete?");
		dialog.setContentText("Name: ");
		Optional<String> result = dialog.showAndWait();

		String albumname = null;
		if(result.isPresent()){
			albumname = result.get();
		}
		if(!Arrays.asList(albums).contains(albumname)){
			//error
		}
		else{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("You are deleting " + albumname);
			alert.setContentText("Are you ok with this?");

			Optional<ButtonType> confirmation_result = alert.showAndWait();
			if (confirmation_result.get() == ButtonType.OK){
				File dir = new File(Main.currentUser.getUserDirectory() + File.separator + albumname);
				DeleteAlbum(dir);}
			else {
			}

		}

		TilePane.getChildren().clear();
		Main.currentUser.writeApp();
		initialize();
	}


}



