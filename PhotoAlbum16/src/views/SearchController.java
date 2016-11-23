package views;

import java.io.IOException;
import java.util.List;

import application.Main;
import application.Photo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author matt kalita anf yigit gungor 
 *
 */
public class SearchController {
	@FXML
	private TilePane PhotosTilePane;
	@FXML
	private Button Back_Button;
	 
	/**
	 * start main stage 
	 * @param mainStage
	 * @throws Exception
	 */
	public void start(Stage mainStage) throws Exception {      
		
	}
	/**
	 * backbutton listner 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void backButtonClicked(ActionEvent event) throws IOException
	{
		 Stage stage;
	     Parent root;     
	     
	     stage=(Stage) Back_Button.getScene().getWindow();
	     root = FXMLLoader.load(getClass().getResource("AlbumsPage.fxml"));

	     Scene scene = new Scene(root);
	     stage.setScene(scene);
	     stage.show();			
		
	}
	
	/**
	 * initialize scene method 
	 */
	@FXML
	public void initialize(){
		List<Photo> photos = AlbumsPageController.searchResults;
					
		for(int i = 0; i<photos.size(); i++){

			Photo p = photos.get(i);
			ImageView imv = new ImageView();
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
		      		ImageView imv = ((ImageView) event.getSource());
        			
            		if(imv.getFitHeight() == 200){
            			imv.setFitHeight(400);
            			imv.setFitWidth(600);
            		}
            		else{
            		imv.setFitHeight(200);
        			imv.setFitWidth(300);
            		}
        			
					
				}
			});
			PhotosTilePane.getChildren().addAll(tileStack);
		}
	}

}
