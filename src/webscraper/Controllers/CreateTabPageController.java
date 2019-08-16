package webscraper.Controllers;

import com.opencsv.CSVWriter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class CreateTabPageController {

    private String htmlStringType = "";

    @FXML
    private GridPane createTabController;

    @FXML
    private RadioButton id_button;

    @FXML
    private RadioButton class_button;

    @FXML
    private RadioButton tag_button;

    @FXML
    private TextField ticker;

    @FXML
    private TextField companyName;

    @FXML
    private TextField weblink;

    @FXML
    private TextField htmlString;

    @FXML
    private TextField websiteSection;

    @FXML
    private TextField language;

    @FXML
    private Button loadPage;

    @FXML
    private Button exportToCSV;

    @FXML
    private WebView pagePreview;


    @FXML
    public void initialize() {

        createEmptyFile();

        loadPage.setDisable(true);
        exportToCSV.setDisable(true);

    }


    @FXML
    public void onButtonClicked() throws IOException {

        String csv = "./CSV_Data.csv";

        String temp = ticker.getText();

        if(temp.contains(".")){
           temp = temp.replace('.','-');
        }

        ticker.setText(temp);

        String[] record = {ticker.getText(), companyName.getText(), weblink.getText(), htmlString.getText(), htmlStringType, websiteSection.getText(), language.getText()};

        CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
        writer.writeNext(record);
        writer.close();

    }

    @FXML
    public void handleKeyReleased() {

        String text01 = weblink.getText();
        String text02 = htmlString.getText();


        boolean disableButtons = false;

        if ((text01.isEmpty() || text01.trim().isEmpty()) || (text02.isEmpty() || text02.trim().isEmpty())) {
            disableButtons = true;
        }
        loadPage.setDisable(disableButtons);
        exportToCSV.setDisable(disableButtons);
    }

    @FXML
    public void showPage() throws IOException {

        Element eventsSectionId;
        Elements eventsSectionClass;
        Elements eventsSectionTag;

        WebEngine engine = pagePreview.getEngine();
        String address = "";

        Document doc = Jsoup.connect(weblink.getText()).timeout(50000).get();

        if (htmlStringType.equals("Id")) {
            eventsSectionId = doc.getElementById(htmlString.getText());
            doc.outputSettings(new Document.OutputSettings().prettyPrint(false));

            address = eventsSectionId.outerHtml();

        }else if(htmlStringType.equals("Class")){
            eventsSectionClass = doc.getElementsByClass(htmlString.getText());
            doc.outputSettings(new Document.OutputSettings().prettyPrint(false));

            address = eventsSectionClass.outerHtml();


        }else{

            eventsSectionTag = doc.getElementsByTag(htmlString.getText());
            doc.outputSettings(new Document.OutputSettings().prettyPrint(false));

            address = eventsSectionTag.outerHtml();

        }

        engine.loadContent(address, "text/html");


    }

    @FXML
    public void clearFields() {

        id_button.setSelected(false);
        class_button.setSelected(false);
        tag_button.setSelected(false);
        htmlStringType = "";



        htmlString.clear();
        weblink.clear();
        ticker.clear();
        companyName.clear();
        websiteSection.clear();
        language.clear();


        WebEngine engine = pagePreview.getEngine();
        engine.load("");

        handleKeyReleased();

    }

    @FXML
    private void setSectionType() {

        if (id_button.isSelected()) {
            htmlStringType = id_button.getText();
        }

        if (class_button.isSelected()) {
            htmlStringType = class_button.getText();
        }

        if (tag_button.isSelected()) {
            htmlStringType = tag_button.getText();
        }


    }

    public void createEmptyFile(){

        File file = new File("./CSV_Data.csv");

        String csv = "./CSV_Data.csv";


        if(!file.exists()){

            try{

                file.createNewFile();
                String[] record = {"ticker", "companyName", "weblink", "htmlString", "htmlStringType", "websiteSection", "language"};

                CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
                writer.writeNext(record);
                writer.close();

            }catch(IOException e) {
                System.out.println("A new file has been created");
            }


        }


    }
}






