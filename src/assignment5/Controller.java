package assignment5;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.AnimationTimer;

public class Controller implements Initializable {
	
	private ObservableList<CheckMenuItem> list = FXCollections.observableArrayList();
	public static ObservableList<CheckMenuItem> bugs = FXCollections.observableArrayList();
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
	@FXML
	private GridPane Grid;
	@FXML
	private TextArea TA;
	public static AnimationTimer GridDisplay;
	 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		SpeedSlider.setMax(1000);
		SpeedSlider.setMin(1);
		Grid.setCache(true);
		Grid.setCacheShape(true);
		Grid.setCacheHint(CacheHint.SPEED);
		GridDisplay = new AnimationTimer(){
			private long prev = 0;
			@Override
			public void handle(long now) {
				long slideval;
				try{
					slideval = Integer.parseInt(SpeedTF.getText());
					if(slideval <= 0)
						slideval = (long)SpeedSlider.getValue();
					else
						SpeedSlider.setValue((double)slideval);
				}
				catch(Exception e){
					slideval = (long)SpeedSlider.getValue();
				}
				long delay = 1_000_000_000/slideval;
				// TODO Auto-generated method stub
				if(now - prev >= delay){
					Critter.worldTimeStep();
					Critter.displayWorld(Grid);
					prev = now;
				}
			}
		};
		ArrayList<String> critters = getBugs();
		Critter.clearWorld();
		for(String bug : critters){
			list.add(new CheckMenuItem(bug));
			MakeCritterCB.getItems().add(bug);
			System.out.println(bug);
		}
		RunStatsMenuButton.getItems().addAll(list);
		MakeCritterButton.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				try{
					int count = 0;
					if(MakeCritterTF.getText().equals("")){
						Critter.makeCritter(MakeCritterCB.getValue().toString());
					}
					else{
						count = Integer.parseInt(MakeCritterTF.getText());
						for(int k = 0; k < count; k++){
							Critter.makeCritter(MakeCritterCB.getValue().toString());
						}
					}
				}catch(Exception e){
					
				}
			}
        });
		
        RunStatsButton.setOnAction((event) -> {
        	bugs.clear();
            for(MenuItem item : RunStatsMenuButton.getItems()) {
                CheckMenuItem checkMenuItem = (CheckMenuItem) item;
                if(checkMenuItem.isSelected()) {
                	bugs.add(checkMenuItem);
                }
            }
        });
        
        TimeStepButton.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				try{
					int count = 0;
					if(TimeStepTF.getText().equals("")){
						Critter.worldTimeStep();
					}
					else{
						count = Integer.parseInt(TimeStepTF.getText());
						for(int k = 0; k < count; k++){
							Critter.worldTimeStep();
						}
					}
					Critter.displayWorld(Grid);
				}catch(Exception e){
					
				}				
			}
        });
        
        SeedButton.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				try{
					if(!SeedTF.getText().equals("")){
						int seed = Integer.parseInt(SeedTF.getText());
						Critter.setSeed(seed);
					}
				}catch(Exception e){
					
				}
			}
        });
        
        RunButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				TimeStepButton.setDisable(true);
				RunStatsButton.setDisable(true);
				SeedButton.setDisable(true);
				MakeCritterButton.setDisable(true);
				RunStatsMenuButton.setDisable(true);
				RunButton.setDisable(true);
				PauseButton.setDisable(false);
				MakeCritterTF.setDisable(true);
				SeedTF.setDisable(true);
				TimeStepTF.setDisable(true);
				MakeCritterCB.setDisable(true);
				GridDisplay.start();
			}
        });
        
        PauseButton.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				TimeStepButton.setDisable(false);
				RunStatsButton.setDisable(false);
				SeedButton.setDisable(false);
				MakeCritterButton.setDisable(false);
				RunStatsMenuButton.setDisable(false);
				RunButton.setDisable(false);
				PauseButton.setDisable(true);
				MakeCritterTF.setDisable(false);
				SeedTF.setDisable(false);
				TimeStepTF.setDisable(false);
				MakeCritterCB.setDisable(false);
				GridDisplay.stop();
			}
        });
        
        QuitButton.setOnAction(new EventHandler <ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);				
			}
        });
        Grid.setGridLinesVisible(true);
        int numCols = Params.world_width;
        int numRows = Params.world_height;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            colConst.setHalignment(HPos.CENTER);
            Grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            rowConst.setValignment(VPos.CENTER);
            Grid.getRowConstraints().add(rowConst);         
        }
        Critter.displayWorld(Grid);
        
        TA.setCache(true);
        TA.setCacheShape(true);
        TA.setCacheHint(CacheHint.SPEED);
        AnimationTimer timer = new AnimationTimer(){
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
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
        };
        timer.start();
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
