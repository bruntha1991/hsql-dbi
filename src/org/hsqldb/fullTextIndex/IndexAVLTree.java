package org.hsqldb.fullTextIndex;

import org.hsqldb.Row;

import java.util.ArrayList;

/**
 * Created by Ruba on 2/6/2016.
 */
public class IndexAVLTree
{
    private NodeAVL root;
    private NodeAVL lastIdentified;

    public IndexAVLTree()
    {
        root = null;
    }
    /* Function to check if tree is empty */
    public boolean isEmpty()
    {
        return root == null;
    }
    /* Make the tree logically empty */
    public void makeEmpty()
    {
        root = null;
    }
    /* Function to insert data */
    public void insert(String word,int index,int occurence)
    {
        root = insert(word, root, index, occurence);
    }
    /* Function to get height of node */
    private int height(NodeAVL t )
    {
        return t == null ? -1 : t.height;
    }
    /* Function to max of left/right node */
    private int max(int lhs, int rhs)
    {
        return lhs > rhs ? lhs : rhs;
    }
    /* Function to insert data recursively */
    private NodeAVL insert(String x, NodeAVL t,int index,int occurence)
    {
        if (t == null)
            t = new NodeAVL(x,index,occurence);
        else if (x.compareTo( t.word) < 0)
        {
            t.left = insert( x, t.left, index,occurence );
            if( height( t.left ) - height( t.right ) == 2 )
                if( x.compareTo( t.left.word ) < 0)
                    t = rotateWithLeftChild( t );
                else
                    t = doubleWithLeftChild( t );
        }
        else if( x.compareTo( t.word ) > 0)
        {
            t.right = insert( x, t.right, index,occurence);
            if( height( t.right ) - height( t.left ) == 2 )
                if( x.compareTo( t.right.word) > 0)
                    t = rotateWithRightChild( t );
                else
                    t = doubleWithRightChild( t );
        }
        else
            t.insert(index, occurence);
        t.height = max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }
    /* Rotate binary tree node with left child */
    private NodeAVL rotateWithLeftChild(NodeAVL k2)
    {
        NodeAVL k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    /* Rotate binary tree node with right child */
    private NodeAVL rotateWithRightChild(NodeAVL k1)
    {
        NodeAVL k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = max( height( k2.right ), k1.height ) + 1;
        return k2;
    }
    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child */
    private NodeAVL doubleWithLeftChild(NodeAVL k3)
    {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild(k3);
    }
    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child */
    private NodeAVL doubleWithRightChild(NodeAVL k1)
    {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }
    /* Functions to count number of nodes */
    public int countNodes()
    {
        return countNodes(root);
    }
    private int countNodes(NodeAVL r)
    {
        if (r == null)
            return 0;
        else
        {
            int l = 1;
            l += countNodes(r.left);
            l += countNodes(r.right);
            return l;
        }
    }
    /* Functions to search for an element */
    public ArrayList<Data> search(String word)
    {
        return search(root, word);
    }
    private ArrayList<Data> search(NodeAVL r, String val)
    {
        ArrayList<Data> results = null;
        while (r != null)
        {
            String rval = r.word;
            if (val.compareTo( rval) < 0)
                r = r.left;
            else if (val.compareTo( rval) > 0)
                r = r.right;
            else
            {
                results = r.getData();
                lastIdentified = r;
                break;
            }
        }
        return results;
    }

    public int getCollectionFrequency(String word){
        if(lastIdentified!= null && lastIdentified.word.equals(word))
            return lastIdentified.collectionFrequency;
        else
            return 0;
    }
}
