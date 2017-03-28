package assignment5;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application{
	Controller C = new Controller();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("scenebuilder_shit.fxml"));
		Parent root = loader.load();
		C = loader.getController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Critters Controller");
        primaryStage.show();
        Stage secondStage = new Stage();
        VBox box = new VBox();
        TextArea TA = new TextArea();
        Timeline Updater = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {  
            @Override  
            public void handle(ActionEvent event) { 
              TA.clear();
              for(MenuItem item : Controller.bugs){
            	  CheckMenuItem checkMenuItem = (CheckMenuItem) item;
                  if(checkMenuItem.isSelected()) {
                	  List<Critter> critters = new ArrayList<>();
                	  String name = checkMenuItem.getText();
                	  String text;
                	  try{
  	            		critters = Critter.getInstances(name);
	  	            	}
	  	            	catch(Exception e){
	  	            	}
	  	            	Class<?> myCritter = null;
	  	        		try {
	  	        			myCritter = Class.forName("assignment5." + name); 	// Class object of specified name
	  	        		} catch (ClassNotFoundException e) {
	  	        		}
	  	        		try{
	  	        			Method method = myCritter.getMethod("runStats", List.class);
	  	        			TA.appendText((String) method.invoke(null, critters));
	  	        		}
	  	        		catch(Exception e){
	  	        			TA.appendText(Critter.runStats(critters));
	  	        		}
                  }
              }
            }  
        }));  
        Updater.setCycleCount(Timeline.INDEFINITE);  
        Updater.play();  
        box.getChildren().addAll(TA);
        Scene scene2 = new Scene(box, 500, 250);
        secondStage.setScene(scene2);
        secondStage.show();
	}
}
