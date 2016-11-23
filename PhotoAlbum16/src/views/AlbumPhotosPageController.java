package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;

import java.awt.Color;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.Main;
import application.Photo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

/**
 * @author matt kalita and yigit gungor 
 *
 */
public class AlbumPhotosPageController {
	@FXML
	private Button Back_Button;
	@FXML
	private TilePane PhotosTilePane;
	@FXML
	private ImageView big_imageview;
	@FXML
	private Button back_button;
	@FXML
	private Button forw_button;

	//AlbumsPageController.currentAlbum

	public static Photo currentphoto;
	private int slide_index;

	private List<String> photos = new ArrayList<String>();
	private List<Photo> albumphotos = new ArrayList<Photo>();
	
	
	/**
	 * initialize method 
	 * @throws IOException
	 */
	@FXML
	public void initialize() throws IOException{
		currentphoto = null;
		photos = new ArrayList<String>();
		albumphotos = new ArrayList<Photo>();
		File file = new File(Main.currentUser.getUserDirectory()+File.separator+AlbumsPageController.currentAlbum);
		File[] files = file.listFiles();

		for(int i = 0; i< files.length; i++){
			photos.add(files[i].getAbsolutePath());
		}		

		if(!photos.isEmpty()){
			slide_index = 0;
			big_imageview.setImage(new Photo(photos.get(0)).getImage());
		}
						
		System.out.println("Photos found: " + photos.toString());
		for(int i = 0; i<photos.size(); i++){

			ImageView imv = new ImageView();
			Photo p = new Photo(photos.get(i));
			albumphotos.add(p);
			imv.setImage(p.getImage());
			imv.setFitHeight(200);
			imv.setFitWidth(300);
			
			String caption = p.getName();
			caption = caption.replaceAll(".jpeg", "");
			caption = caption.replaceAll(".jpg", "");
			Text label = new Text(caption);
			label.setFill(javafx.scene.paint.Color.WHITE);
			
			StackPane tileStack = new StackPane();
			tileStack.setPrefSize(300, 200);
			tileStack.setStyle("-fx-background-color: gray");
			tileStack.setAlignment(label,Pos.BOTTOM_CENTER);
			tileStack.getChildren().addAll(imv,label);
			
			imv.setOnMouseClicked(new EventHandler<MouseEvent>(){


   			 Stage stage;
   		     Parent root;
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					  
					
            		for(Photo p : albumphotos){
            			if(p.getImage().equals(((ImageView) event.getSource()).getImage())){
            				currentphoto = p;
            				Main.photo=currentphoto;
            				break;
            			}
            		}
            		stage=(Stage) tileStack.getScene().getWindow();
            		if(currentphoto != null){            		   
            		   try{
            		    root = FXMLLoader.load(getClass().getResource("Photo.fxml"));

            		     Scene scene = new Scene(root);
            		     stage.setScene(scene);
            		     stage.show();
            		   }catch(IOException e){
            			   e.printStackTrace();
            		   }
            		}
            		else{
            			Alert alert = new Alert(AlertType.INFORMATION);
                		alert.setTitle("Error");
                		alert.setHeaderText("Couldn't Find The Image");
                		alert.setContentText("File: " + ((ImageView) event.getSource()).getImage().toString());
                		alert.showAndWait();
            		}
				}
			});
			PhotosTilePane.getChildren().addAll(tileStack);
		}
		
		
		
	}
	/**
	 * forward button listner 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void ForwImgButton_Clicked(ActionEvent event) throws IOException {
			
		
		if(photos.size() > slide_index+1){
				slide_index++;
		}
		else if(!photos.get(0).isEmpty()){
				slide_index = 0;
		}
		big_imageview.setImage(new Photo(photos.get(slide_index)).getImage());
	}
	/**
	 * back button listener
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void BackImgButton_Clicked(ActionEvent event) throws IOException {
		

		if(slide_index-1 < 0){
				slide_index = photos.size() - 1;
		}
		else if(!photos.get(slide_index-1).isEmpty()){
				slide_index--;
		}
		big_imageview.setImage(new Photo(photos.get(slide_index)).getImage());
	}
	
	// Event Listener on Button[#Back_Button].onAction
	/**
	 * back to main listner 
	 * @param event
	 * @throws IOException
	 */
	@FXML	
	public void BackButton_Clicked(ActionEvent event) throws IOException {

		 Stage stage;
	     Parent root;     
	     
	     stage=(Stage) Back_Button.getScene().getWindow();
	     root = FXMLLoader.load(getClass().getResource("AlbumsPage.fxml"));

	     Scene scene = new Scene(root);
	     stage.setScene(scene);
	     stage.show();		
	}
	
	
	
	/**
	 * add photo listner 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void AddPhotos_Clicked(ActionEvent event) throws IOException
	{

		Stage stage = (Stage) PhotosTilePane.getScene().getWindow();
		 configureFileChooser(fileChooser);
		                    File file = fileChooser.showOpenDialog(stage);
		                    if (file != null) {
		                    	TextInputDialog dialog = new TextInputDialog();
		                		dialog.setTitle("Caption this photo");
		                		dialog.setHeaderText("Add a new caption");
		                		dialog.setContentText("What is the name/caption for this photo? (Leave blank if you want to keep the file name)");
		                		Optional<String> result = dialog.showAndWait();

		                		String caption = null;
		                		if(result.isPresent()){
		                			caption = result.get();
		                		}
		                		if(caption != null && !caption.isEmpty()){
		                			caption = caption + ".jpeg";
		                		}else{
		                			caption = file.getName();
		                		}
		                    	// copy(from,to,options)
		                    	Files.copy(file.toPath(), Paths.get(Main.currentUser.getUserDirectory()+File.separator+AlbumsPageController.currentAlbum+File.separator+caption), java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
		                    												 java.nio.file.LinkOption.NOFOLLOW_LINKS,
		                    												 java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		                    	
		                    	Alert alert = new Alert(AlertType.INFORMATION);
		                		alert.setTitle("File Info");
		                		alert.setHeaderText("Information Dialog");
		                		alert.setContentText("File: " + file.getName() + " is coppied.");
		                		alert.showAndWait();		                		
		                    }
		 PhotosTilePane.getChildren().clear();
		 Main.currentUser.writeApp();
		 initialize();
		 
	}
	

    final FileChooser fileChooser = new FileChooser();
/**
 * uses files for photos 
 * @param fileChooser
 */
	private static void configureFileChooser(
	        final FileChooser fileChooser) {      
	            fileChooser.setTitle("View Pictures");
	            fileChooser.setInitialDirectory(
	                new File(System.getProperty("user.home"))
	            );                 
	            fileChooser.getExtensionFilters().addAll(
	                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
	                new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
	            );
	    }


	/**
	 * edit photo listner 
	 * @param event
	 */
	@FXML
	public void EditPhotos_Clicked(ActionEvent event)
	{
		
	}
}
