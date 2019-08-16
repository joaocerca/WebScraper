package webscraper.Classes;

import java.io.IOException;

public class FilesExecuteThread extends Thread {

    private FilesParser threadFiles;

    public FilesExecuteThread(FilesParser threads){
        threadFiles = threads;
    }

    @Override
    public void run() {
        threadFiles.checkCompared();

        try{

            threadFiles.parseAndSaveHtml();

            if (threadFiles.compared) {
                threadFiles.listAllFiles();
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}

