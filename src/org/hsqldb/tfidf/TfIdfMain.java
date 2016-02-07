package org.hsqldb.tfidf;

/**
 * Created by prakhash on 06/02/16.
 */
import org.hsqldb.Session;
import org.hsqldb.fullTextIndex.Data;
import org.hsqldb.lib.Set;
import org.hsqldb.result.Result;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class TfIdfMain {

    String ourTerm;


    public TfIdfMain() {
    }

    public static void main(String args[]) throws IOException
    {
        TfIdfMain main=new TfIdfMain();

    }

    public String[][] ProcessQuery(String query) throws IOException
    {
        String queryWords[] = query.split(" ");

        java.util.Set<Integer> rowIndex = new HashSet<Integer>();

        for(int i=0; i<queryWords.length;i++) {
            ArrayList<Data> datas = Session.indexTree.search(queryWords[i]);

            for (int j = 0; j < datas.size(); j++) {
                rowIndex.add(datas.get(j).index);
            }
        }

        HashMap<Integer,Double> scores = new HashMap<Integer,Double>();
        for(Integer integer : rowIndex){
            double score = compare(Session.table.get(integer).getData()[Session.colIndex].toString(),query);
            scores.put(integer,score);
        }

        LinkedHashMap linkedHashMap=sortHashMapByValuesD(scores);


        String[][] results = new String[linkedHashMap.size()][Session.table.get(0).getData().length];

        for(int i=0; i< results.length; i++){
            results[i] = Arrays.copyOf(Session.table.get(i).getData(), Session.table.get(i).getData().length, String[].class);
        }

        return results;

    }

    public LinkedHashMap sortHashMapByValuesD(HashMap<Integer,Double> passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap sortedMap = new LinkedHashMap();

        Iterator valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                String comp1 = passedMap.get(key).toString();
                String comp2 = val.toString();

                if (comp1.equals(comp2)){
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put(key,val);
                    break;
                }

            }

        }
        return sortedMap;
    }

    public static double compare(String testTerm, String ourTerm) throws IOException
    {
        DocumentParser dp = new DocumentParser();
        String[] array={ourTerm,testTerm};
        dp.parseFiles(array); // give the location of source file
        dp.tfIdfCalculator(); //calculates tfidf
        return dp.getCosineSimilarity(); //calculates cosine similarity
    }
}