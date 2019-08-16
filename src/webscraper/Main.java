package webscraper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {

    public static int countStateButton = 0;

    @Override
    public void start(Stage primaryStage) throws IOException{

        Scene scene = new Scene(new StackPane());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/webscraper/mainController.fxml"));
        scene.setRoot(loader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("WebScraper Tool");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}