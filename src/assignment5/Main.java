package assignment5;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application{
	
	private ObservableList<CheckMenuItem> list = FXCollections.observableArrayList();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		SplitPane page = (SplitPane) FXMLLoader.load(Main.class.getResource("scenebuilder_shit.fxml"));
		
        Scene scene = new Scene(page);
		MenuButton menuButton = (MenuButton) scene.lookup("#RunStatsMenuButton");
		ArrayList<String> critters = getBugs();
		for(String bug : critters){
			list.add(new CheckMenuItem(bug));
		}
		menuButton.getItems().addAll(list);
//		Button runButton = (Button) scene.lookup("#RunStatsButton");
//        runButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                for(MenuItem item : menuButton.getItems()) {
//                    CheckMenuItem checkMenuItem = (CheckMenuItem) item;
//                    if(checkMenuItem.isSelected()) {
//                        System.out.println("Selected item :" + checkMenuItem.getText());
//                    }
//                }
//            }
//        });
        
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	/**
	 * Helper function for getting Critter names
	 * @return List of folders to check (hardcoded to working dir, bin, and src)
	 */
	private ArrayList<String> getFolders(){
		ArrayList<String> dirs = new ArrayList<String>();
		String working_dir = System.getProperty("user.dir");
		dirs.add(working_dir);
		dirs.add(working_dir + File.separator + "src" + File.separator + "assignment5");
		dirs.add(working_dir + File.separator + "bin" + File.separator + "assignment5");
		return dirs;
	}
	
	/**
	 * Gets names of Critters in working directory, src, and bin.
	 * @return List of valid Critters
	 */
	private ArrayList<String> getBugs(){
		ArrayList<String> bugs_list = new ArrayList<String>();
		
		//gets list of folders in the order of: working_dir, src, bin
		ArrayList<String> folders = getFolders();
		for(String dir: folders){
			File folder = new File(dir);
			for(File file: folder.listFiles()){
				if(file.isFile()){
					String name = file.getName();
					if(name.contains(".class"))
						continue;
					name = name.replaceFirst("[.][^.]+$", "");
					if(name != "Critter"){
						try{
							Critter.makeCritter(name);
						}
						catch(Exception e){
							continue;
						}
						bugs_list.add(name);
					}
				}
			}
		}
		return bugs_list;
	}
}
