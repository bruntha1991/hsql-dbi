package org.hsqldb.fullTextIndex;

import org.hsqldb.Row;

import java.util.ArrayList;

/**
 * Created by Ruba on 2/6/2016.
 */
public class NodeAVL
{
    NodeAVL left, right;
    String word;
    int height;
    ArrayList<Data> datas=new ArrayList<Data>();
    int collectionFrequency = 0;

    public NodeAVL()
    {
        left = null;
        right = null;
        word = null;
        height = 0;
        datas = null;
    }

    public NodeAVL(String word,int index,int occurence)
    {
        left = null;
        right = null;
        this.word = word;
        height = 0;
        datas.add(new Data(index,occurence));
        collectionFrequency+= occurence;
    }

    public void insert(int index,int occurence){
        datas.add(new Data(index,occurence));
        collectionFrequency+= occurence;
    }

    public ArrayList<Data> getData(){
        return datas;
    }

    public int getCollectionFrequency(){
        return collectionFrequency;
    }
}

