package assignment5;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Controller implements Initializable {
	
	private ObservableList<CheckMenuItem> list = FXCollections.observableArrayList();
	@FXML
	private Button TimeStepButton, RunStatsButton, SeedButton, MakeCritterButton, RunButton, PauseButton, QuitButton;
	@FXML
	private MenuButton RunStatsMenuButton;
	@FXML
	private TextField SpeedTF, MakeCritterTF, SeedTF, TimeStepTF;
	@FXML
	private Slider SpeedSlider;
	@FXML
	private ChoiceBox MakeCritterCB;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		ArrayList<String> critters = getBugs();
		for(String bug : critters){
			list.add(new CheckMenuItem(bug));
			System.out.println(bug);
		}
		RunStatsMenuButton.getItems().addAll(list);
		MakeCritterCB.getItems().addAll(list);
		MakeCritterButton.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
        });
		
        RunStatsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(MenuItem item : RunStatsMenuButton.getItems()) {
                    CheckMenuItem checkMenuItem = (CheckMenuItem) item;
                    if(checkMenuItem.isSelected()) {
                        //Add in functionality here (i.e. run each critter's RunStats method)
                    	
                    }
                }
            }
        });
        
        TimeStepButton.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
        });
        
        SeedButton.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
        });
        
        RunButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Button pressed!");				
			}
        	
        });
        
        PauseButton.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
        });
        
        QuitButton.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);				
			}
        });
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
