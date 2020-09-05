import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class WordNet {

    HashMap<String, Bag<Integer>> nouns_synsets;
    Digraph dg;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null ) throw new IllegalArgumentException();

        In in = new In(synsets);
        this.nouns_synsets = new HashMap<String, Bag<Integer>>();

        int line_nb=0;
        while ( ! in.isEmpty() ) {
            String[] line = in.readLine().split(",");
            for( String s:line[1].split(" ")) {
                // initialize new nouns_synsets key if it does not exist
                if ( ! nouns_synsets.containsKey(s)) {
                    nouns_synsets.put(s,new Bag<Integer>());
                }
                nouns_synsets.get(s).add(line_nb);
            }
            line_nb++;
        }

        dg=new Digraph(line_nb);

        in = new In(hypernyms);
        while ( ! in.isEmpty() ) {
            String[] line = in.readLine().split(",");
            int ssid=-1;
            for(int i=0;i<line.length;i++ ) {
                if(i==0) {
                    ssid=Integer.parseInt(line[i]);
                }
                else
                {
                    if( ssid == -1 ) throw new IllegalStateException("ssid is not supposed to be -1");
                    dg.addEdge(ssid,Integer.parseInt(line[i]));
                }

            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns_synsets.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null ) throw new IllegalArgumentException();
        return nouns_synsets.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounA == null ) throw new IllegalArgumentException();
        return 0;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounA == null ) throw new IllegalArgumentException();
        return new String();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn=new WordNet("synsets.txt","hypernyms.txt");
        //WordNet wn=new WordNet("C:\\data\\projects\\algorithm\\wordnet\\synsets15.txt","C:\\data\\projects\\algorithm\\wordnet\\hypernyms15Path.txt");


        String a=new String("babo");
        StdOut.println("Is '"+a+"' a noun? : "+wn.isNoun(a));

        a=new String("zoophilia zoophilism");
        StdOut.println("Is '"+a+"' a noun? : "+wn.isNoun(a));
        /*
        StdOut.println("1000 word is "+wn.synset.get(1000));


        for( String s: wn.nouns() ) {
            StdOut.println(s);
        }
        */

        // StdOut.println(wn.dg.toString());
        int i=0;
        for(String w:wn.nouns()) {
            StdOut.println(w);
            i++;
            //if (i> 30 ) break;

        }

    }
}