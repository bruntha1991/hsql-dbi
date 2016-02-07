package org.hsqldb.tfidf;

/**
 * Created by prakhash on 06/02/16.
 */

import org.hsqldb.persist.ScaledRAFileHybrid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentParser {

    //This variable will hold all terms of each document in an array.
    private List<String[]> termsDocsArray = new ArrayList<String[]>();
    private List<String> allTerms = new ArrayList<String>(); //to hold all terms
    private List<Double[]> tfidfDocsVector = new ArrayList<Double[]>();

    /**
     * Method to read files and store in array.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void parseFiles(String[] queries) throws IOException {

        for (String f : queries) {
                String[] tokenizedTerms = f.split(" ");   //to get individual terms
                for (String term : tokenizedTerms) {
                    if (!allTerms.contains(term)) {  //avoid duplicate entry
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(tokenizedTerms);
        }
    }

    /**
     * Method to create termVector according to its tfidf score.
     */
    public void tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency

            for (int i=0;i<termsDocsArray.size();i++){
                String[] docTermsArray=termsDocsArray.get(i);
                Double[] tfidfvectors = new Double[allTerms.size()];
            int count = 0;

                for(int j=0;j<allTerms.size();j++){
                    String terms=allTerms.get(j);
                tf = new TfIdf().tfCalculator(docTermsArray, terms);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;
        }
    }

    /**
     * Method to calculate cosine similarity between all the documents.
     */
    public double getCosineSimilarity() {
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < tfidfDocsVector.size(); j++) {
                if(i!=j){
                    return new CosineSimilarity().cosineSimilarity(tfidfDocsVector.get(i), tfidfDocsVector.get(j));
                }
            }
        }
        return 0;
    }
}