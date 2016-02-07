package org.hsqldb.tfidf;

/**
 * Created by prakhash on 06/02/16.
 */
import java.io.FileNotFoundException;
import java.io.IOException;


public class TfIdfMain {

    /**
     * Main method
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String args[]) throws FileNotFoundException, IOException
    {
        DocumentParser dp = new DocumentParser();
        dp.parseFiles("D:\\FolderToCalculateCosineSimilarityOf"); // give the location of source file
        dp.tfIdfCalculator(); //calculates tfidf
        dp.getCosineSimilarity(); //calculates cosine similarity
    }
}