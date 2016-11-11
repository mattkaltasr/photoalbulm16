package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	
	@FXML
	private TilePane TilePane;
	
	public String[] albums;
	
	@FXML
	public void initialize(){

		currentAlbum = null;
		albums = null;
		System.out.println("Albums page started");
		File folder = new File(Main.currentUser.getUserDirectory());
		albums= folder.list(new FilenameFilter(){
			@Override
				public boolean accept(File current, String name){
				return new File(current,name).isDirectory();
			}
			});
		System.out.println("Albums found: " + albums.toString());
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
	}
	
	public void start(Stage mainStage) throws Exception {  
	      
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
		
		if(albumname != null && !Arrays.asList(albums).contains(albumname)){
		File dir = new File(Main.currentUser.getUserDirectory() + File.separator + albumname);
		dir.mkdir();
		}
		else
		{
		 //ALBUM EXISTS	
		}
		
		TilePane.getChildren().clear();
		initialize();
	}
	
	@FXML
	public void EditAlbum_Clicked(ActionEvent event) {
		// TODO Autogenerated
	}
		
}
