package assignment5;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
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
						System.out.print("y");
					}
					else{
						count = Integer.parseInt(TimeStepTF.getText());
						for(int k = 0; k < count; k++){
							Critter.worldTimeStep();
							System.out.print("y");
						}
					}
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
            Grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            Grid.getRowConstraints().add(rowConst);         
        }
        Critter.displayWorld(Grid);
	}
	
	private StackPane createCell(BooleanProperty cellSwitch) {

        StackPane cell = new StackPane();

        cell.setOnMouseClicked(e -> cellSwitch.set(! cellSwitch.get() ));

        Circle circle = new Circle(10, Color.CORNFLOWERBLUE);

        circle.visibleProperty().bind(cellSwitch);

        cell.getChildren().add(circle);
        cell.getStyleClass().add("cell");
        return cell;
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
