package webscraper.Classes;

public class FilesComparedThread implements Runnable{

    private FilesParser threadFilesParserThread;

    public FilesComparedThread(FilesParser filesparser){
        threadFilesParserThread = filesparser;
    }

    public void run(){

        threadFilesParserThread.checkCompared();
    }
}
