package org.hsqldb.tfidf;

/**
 * Created by prakhash on 06/02/16.
 */
import java.io.FileNotFoundException;
import java.io.IOException;


public class TfIdfMain {

    String ourTerm;


    public TfIdfMain() {
    }

    public TfIdfMain(String ourTerm) {
          this.ourTerm=ourTerm;
    }

    public void setOurTerm(String ourTerm) {
        this.ourTerm = ourTerm;
    }

    public static void main(String args[]) throws FileNotFoundException, IOException
    {
        TfIdfMain main=new TfIdfMain();
        main.setOurTerm("Prakhash rfgfg bgf");

        String [] array={"my name is Prakhash","is Prakhash"};

        for (int i=0;i<array.length;i++){
            main.compare(array[i]);
        }

    }

    public void compare(String testTerm) throws IOException
    {
        DocumentParser dp = new DocumentParser();
        String[] array={ourTerm,testTerm};
        dp.parseFiles(array); // give the location of source file
        dp.tfIdfCalculator(); //calculates tfidf
        System.out.println(dp.getCosineSimilarity()); //calculates cosine similarity
    }
}