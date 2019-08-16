package webscraper.Controllers;

import javafx.scene.control.Alert;
import webscraper.Classes.GetData;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;

public class RunTabPageController {

    @FXML
    private Button runAgentsButton;


//    @FXML
//    public void initialize() {
//
//        runAgentsButton.setEnabled(false);
//
//    }

    @FXML
    private void runData() {

        try{

            GetData getData = new GetData();
            getData.getData();



        }catch(IOException e){

//            System.out.println("The file is empty!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("The file has no agents. Please create some agents");

            alert.showAndWait();

        }

    }

}
