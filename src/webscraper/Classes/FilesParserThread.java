package webscraper.Classes;

import java.io.IOException;

public class FilesParserThread implements Runnable {

    private FilesParser threadFilesParserThread;

    public FilesParserThread(FilesParser files){
        threadFilesParserThread = files;
    }

    @Override
    public void run(){

        try{
            threadFilesParserThread.parseAndSaveHtml();
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
