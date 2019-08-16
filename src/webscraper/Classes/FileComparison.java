package webscraper.Classes;

import difflib.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileComparison {

    //https://stackoverflow.com/questions/33996988/java-compare-mark-and-interpret-html-texts-in-java


    private File original;
    private File revised;
    private String encoding;


    public FileComparison(File original, File revised, String encoding) {
        this.original = original;
        this.revised = revised;
        this.encoding = encoding;
    }

    public List<Chunk> getChangesFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.CHANGE);
    }

    public List<Chunk> getInsertsFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.INSERT);
    }

    public List<Chunk> getDeletesFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.DELETE);
    }

    private List<Chunk> getChunksByType(Delta.TYPE type) throws IOException {
        final List<Chunk> listOfChanges = new ArrayList<Chunk>();
        final List<Delta> deltas = getDeltas();
        for (Delta delta : deltas) {
            if (delta.getType() == type) {
                listOfChanges.add(delta.getRevised());
            }
        }
        return listOfChanges;
    }

    private List<Delta> getDeltas() throws IOException {

        final List<String> originalFileLines = fileToLines(original);
        final List<String> revisedFileLines = fileToLines(revised);

        final Patch patch = DiffUtils.diff(originalFileLines, revisedFileLines);

        return patch.getDeltas();
    }

    private List<String> fileToLines(File file) throws IOException {
        final List<String> lines = new ArrayList<String>();
        String line;
        final BufferedReader in = new BufferedReader(new FileReader(file));
        while ((line = in.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }


    public void createDiffFile(String pathname) {


        PrintWriter diffFile = null;
        //RandomAccessFile diffFile = null;
        RandomAccessFile oldFile = null;

        try {

            //diffFile = new RandomAccessFile(new File("./diffFile_" + System.currentTimeMillis()), "rw");

            //diffFile = new PrintWriter("/ScraperTool/" + companyName + "/" + websiteSection + "/" + orgID + "_" + companyName + "_" + websiteSection + "_" + language + "_DIFF.html", encoding);
            diffFile = new PrintWriter(pathname, encoding);
            oldFile = new RandomAccessFile(original, "r");

            final FileComparison comparator = new FileComparison(original, revised, encoding);

            final List<Chunk> changesFromOriginal = comparator.getChangesFromOriginal();

            final int changeNum = changesFromOriginal.size();

            //System.out.println("There are " + changeNum + " number of changes in " + revised);

            final List<Integer> changesIndex = new ArrayList<Integer>();

            for (Chunk change : changesFromOriginal) {

                changesIndex.add(change.getPosition());
            }

            String line = oldFile.readLine();
            int lineIndex = 0;

//            if (changeNum >= 0) {


            while (line != null) {

                if (changesIndex.contains(lineIndex)) {

                    String strikeLine = "<h2 style=\"background-color:red\">" + line + "</h2>" + "\n";
                    diffFile.print(strikeLine + "\n" + " <h1 style=\"background-color:green\">");

                    for (Object s : changesFromOriginal.get(changesIndex.indexOf(lineIndex)).getLines()) {
                        diffFile.println(s.toString());
                    }
                    diffFile.print("</h2>");

                } else {

                    diffFile.println(line);
                }

                line = oldFile.readLine();
                lineIndex++;
            }
//            }else
//                System.out.println("There are no changes!");


        } catch (IOException e) {

        } finally {
            try {
                if (diffFile != null) {
                    diffFile.close();
                }

                if (oldFile != null) {
                    oldFile.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


}





















