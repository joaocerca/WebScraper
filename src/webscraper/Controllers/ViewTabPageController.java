package webscraper.Controllers;

import com.sun.javafx.scene.control.skin.TreeViewSkin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import sun.reflect.generics.tree.Tree;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ViewTabPageController implements Initializable {

    private static final String ROOT_FOLDER = "./ScraperLinks";

    @FXML
    private GridPane runTabController;

    @FXML
    private Button refreshList;

    @FXML
    private TreeView<Path> fileTreeView;
    private TreeItem<Path> rootItem;

    @FXML
    private WebView pagePreview;

//https://stackoverflow.com/questions/34534775/configuring-a-treeview-which-scans-local-fie-system-to-only-include-folders-whic

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rootItem = new TreeItem<Path>(Paths.get(ROOT_FOLDER));
        rootItem.setExpanded(true);

        try {
            createTree(rootItem);
        } catch (IOException e) {
            System.out.println(e);
        }

        fileTreeView.setRoot(rootItem);

    }

//    creates the file tree

    @FXML
    public void createTree(TreeItem<Path> rootItem) throws IOException {

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootItem.getValue())) {
            for (Path path : directoryStream) {
                TreeItem<Path> newItem = new TreeItem<Path>(path);
                newItem.setExpanded(true);
                rootItem.getChildren().add(newItem);

                if (Files.isDirectory(path)) {
                    createTree(newItem);
                }
            }

        }


    }


    @FXML
    public void previewPage(MouseEvent event) throws IOException {

        TreeItem<Path> item = fileTreeView.getSelectionModel().getSelectedItem();

        String path = System.getProperty("user.dir").replace("\\", "/");
        String address = item.getValue().toString().substring(1).replace("\\", "/");

        String fullPath = path + address;

        try {

            WebEngine engine = pagePreview.getEngine();
            if (event.getClickCount() == 2) {

                engine.load("file:///" + fullPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    public void refreshTree() throws IOException{
       rootItem.getChildren().clear();
       createTree(rootItem);
    }

//    @FXML   //code to add the dialog pane//
//    public void newFileChooser() {
//
//        FileChooser fileChooser = new FileChooser();
//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
//        fileChooser.getExtensionFilters().add(extFilter);
//        fileChooser.setTitle("Load File");
//        fileChooser.showOpenDialog(botao.getScene().getWindow());
//
//
//    }

}
