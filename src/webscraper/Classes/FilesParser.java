package webscraper.Classes;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


class FilesParser {

    private boolean fileExists = false;

   // private String type;
    private String weblink;
    private String htmlString;
    private String htmlStringType;
    private String companyName;
    private String ticker;
    private String websiteSection; //events or news
    private String language;
    private String encoding;


    public File fileName;
    public File fileName_new;
    private File [] listFiles;

    Element eventsSectionId;
    Elements eventsSectionClassAndTag;

    private Document doc;

    public FileComparison compareFiles;

    String tempPath;
    public File pathName;
    public boolean compared = false;


    public FilesParser(String ticker, String companyName, String weblink, String htmlString, String htmlStringType, String websiteSection, String language) {

        this.ticker = ticker;
        this.companyName = companyName;
        this.weblink = weblink;
        this.htmlString = htmlString;
        this.htmlStringType = htmlStringType;
        this.websiteSection = websiteSection;
        this.language = language;

    }

    public synchronized void parseAndSaveHtml() throws IOException {

        //extracts the content of the webpage selected area, normally inside Events and Presentations page


        tempPath = "./ScraperLinks/" + companyName + "/" + websiteSection + "/";
        pathName = new File(tempPath);

        doc = Jsoup.connect(weblink).timeout(50000).get();

        setEncoding();

        if (htmlStringType.equals("id") || htmlStringType.equals("Id")) {

            checkFolderAndFile(); //checks if the files are already there, as well as if the directory exists

            eventsSectionId = doc.getElementById(htmlString);
            doc.outputSettings(new Document.OutputSettings().prettyPrint(false));
            if (!fileExists) {
                fileName = new File(pathName, ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_.html");
                FileUtils.writeStringToFile(fileName, eventsSectionId.outerHtml(), encoding);
            } else if (fileExists && !compared) {
                fileName = new File(pathName, ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_.html");
                fileName_new = new File(pathName, ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_NEW.html");
                FileUtils.writeStringToFile(fileName_new, eventsSectionId.outerHtml(), encoding);
                compareFiles(fileName, fileName_new);
            }
        } else {

            checkFolderAndFile(); //checks if the files are already there, as well as if the directory exists
            if (htmlStringType.equals("class") || htmlStringType.equals("Class")) {
                eventsSectionClassAndTag = doc.getElementsByClass(htmlString);
            } else {
                eventsSectionClassAndTag = doc.getElementsByTag(htmlString);
            }

            doc.outputSettings(new Document.OutputSettings().prettyPrint(false));

            //setFile();
            if (!fileExists) {
                fileName = new File(pathName, ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_.html");
                FileUtils.writeStringToFile(fileName, eventsSectionClassAndTag.outerHtml(), encoding);
            } else if (fileExists && !compared) {
                fileName = new File(pathName, ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_.html");
                fileName_new = new File(pathName, ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_NEW.html");
                FileUtils.writeStringToFile(fileName_new, eventsSectionClassAndTag.outerHtml(), encoding);
                compareFiles(fileName, fileName_new);
            }
        }

    }


    private synchronized void checkFolderAndFile() throws IOException {

        listFiles = pathName.listFiles();

        File tempfile01 = null;
        File tempfile02 = null;
        String tempname01 = ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_.html";
        String tempname02 = ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_NEW.html";

        if (pathName.isDirectory()) {


            if (listFiles.length > 0) {
                for (File file : listFiles) {

                    if (file.getName().equals(tempname01)) {
                        tempfile01 = file;
                        fileExists = true;
                    } else if (file.getName().equals(tempname02)) {
                        tempfile02 = file;

                    }
                }


            } else
                fileExists = false;


        } else
            System.out.println(companyName + " directory created");

    }

    private synchronized void compareFiles(File file01, File file02) throws NullPointerException {


        String nameOfFileDiff = "./ScraperLinks/" + companyName + "/" + websiteSection + "/" + ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_DIFF.html";
        String temp = ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_DIFF.html";

        if ((file01 != null) && (file02 != null)) {

            compareFiles = new FileComparison(file01, file02, encoding);
            compareFiles.createDiffFile(nameOfFileDiff);
            System.out.println("The comparison file " + temp + " has been created");
            compared = true;

        }

    }


    private void setEncoding() {

        switch (language) {
            case "EN":
            case "PT":
            case "ES":
                encoding = "ISO-8859-15";
                break;
            case "HU":
            case "PL":
                encoding = "ISO-8859-2";
                break;
            case "DE":
                encoding = "ISO-8859-1";
                break;
            case "RU":
                encoding = "ISO-8859-5";
                break;
            case "GR":
                encoding = "ISO-8859-7";
                break;
            case "Arabic":
                encoding = "ISO-8859-6";
                break;
            default:
                encoding = "UTF-8";
                break;
        }

    }


    public synchronized void listAllFiles() throws NullPointerException, IOException{


        tempPath = "./ScraperLinks/" + companyName + "/" + websiteSection + "/";
        String tempPath2 = "./ScraperLinks/" + companyName + "/" + websiteSection + "/OldFilesComparison/";
        pathName = new File(tempPath);
        File pathName2 = new File(tempPath2);
        boolean deleted = false;

        File[] listOfFiles = pathName.listFiles();


        String date_ = getDate();

        File tempfile01 = new File(tempPath, ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_.html");
        String x = ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_.html";
        Path temppath01 = Paths.get(x);
        File tempfile02 = new File(tempPath, ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_NEW.html");
        File tempfile03 = new File(tempPath, ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_DIFF.html");
        File tempfile04 = new File(tempPath, ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_DIFF_" + date_ + ".html");


            if (pathName.isDirectory()) {

                if (listOfFiles.length > 0) {

                    for (File file : listOfFiles) {

                        if (tempfile01.exists() && !deleted /*&& file.getName().equals(tempfile01.getName())*/) {
                            tempfile01.delete();
                            System.out.println("the file " + x + " has been deleted!");
                            deleted = true;

                        } else if (tempfile02.exists() /*&& file.getName().equals(tempfile02.getName())*/) {

                            tempfile02.renameTo(tempfile01);

                        } else if (tempfile03.exists() /*&& file.getName().equals(tempfile03.getName())*/) {

                            tempfile03.renameTo(tempfile04);
                            FileUtils.forceMkdir(pathName2);
                            FileUtils.moveToDirectory(tempfile04, pathName2, true);

                        }

                        compared = false;

                    }
                } else {
                    System.out.println("Empty folder");
                }

            }
    }

    public void checkCompared() {


        tempPath = "./ScraperLinks/" + companyName + "/" + websiteSection + "/";
        File pathName = new File(tempPath);
        File tempfile = new File(tempPath, ticker + "_" + companyName + "_" + websiteSection + "_" + language + "_DIFF.html");


        File[] listFiles = pathName.listFiles();

        if (pathName.isDirectory()) {

            if (tempfile.exists()) {
                compared = true;
            } else
                compared = false;
        }
    }

    public String getDate() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy_HH-mm-ss");
        String strDate = formatter.format(date);
        return strDate;
    }
}


