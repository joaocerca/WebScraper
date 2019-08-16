package webscraper;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import webscraper.Controllers.RunTabPageController;
import webscraper.Controllers.CreateTabPageController;
import webscraper.Controllers.ViewTabPageController;

public class MainController  {

    @FXML
    private TabPane tabPane;


    //inject tab content
    @FXML
    private Tab createAgents; //from mainController.fxml: <Tab fx:id="createAgents" ...>
    @FXML
    private Tab viewAgents; //from mainController.fxml: <Tab fx:id="runAgents" ...>
    @FXML
    private Tab runAgents;


    //inject tab controller

    @FXML
    private CreateTabPageController createTabPageController; //mainController.fxml_include_fx:id="createTabPage" + "Controller"
    @FXML
    private ViewTabPageController viewTabPageController; //mainController.fxml_include_fx:id="runTabPage" + "Controller"
    @FXML
    private RunTabPageController runTabPageController;




}