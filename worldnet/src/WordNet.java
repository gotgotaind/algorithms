import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class WordNet {

    ArrayList<String> synset;
    Digraph dg;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null ) throw new IllegalArgumentException();

        In in = new In(synsets);
        this.synset = new ArrayList<String>();

        while ( ! in.isEmpty() ) {
            String[] line = in.readLine().split(",");
            synset.add(line[1]);
        }

        dg=new Digraph(synset.size());

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
        return synset;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null ) throw new IllegalArgumentException();
        return true;
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

        /*
        StdOut.println("1000 word is "+wn.synset.get(1000));


        for( String s: wn.nouns() ) {
            StdOut.println(s);
        }
        */

        // StdOut.println(wn.dg.toString());

    }
}