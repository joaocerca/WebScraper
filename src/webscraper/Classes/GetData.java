package webscraper.Classes;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import webscraper.Main;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;



public class GetData{



    private static final String CSV_PATH = "./CSV_data.csv";


    public void getData () throws IOException{


        Main.countStateButton = 1;



        FilesParser files = null;

        try (Reader reader = Files.newBufferedReader(Paths.get(CSV_PATH));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim());
        ) {
            for (CSVRecord csvRecord : csvParser) {

                if(csvRecord.size() != 0){

                    files = new FilesParser(csvRecord.get("ticker"),
                            csvRecord.get("companyName"),
                            csvRecord.get("weblink"),
                            csvRecord.get("htmlString"),
                            csvRecord.get("htmlStringType"),
                            csvRecord.get("websiteSection"),
                            csvRecord.get("language"));

                    FilesExecuteThread threads = new FilesExecuteThread(files);
                    threads.start();

                }

                else{
                    System.out.println("There are no records on this file");

                }



            }

        }


    }


}
