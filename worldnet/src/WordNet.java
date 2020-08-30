import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;

public class WordNet {

    ArrayList<String[]> synset;
    Digraph dg;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null ) throw new IllegalArgumentException();

        In in = new In(synsets);
        this.synset = new ArrayList<String[]>();

        while ( ! in.isEmpty() ) {
            String[] line = in.readLine().split(",");
            synset.add(line[1].split(" "));
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

    private class nouns_iterator implements  Iterator<String> {
        private int current_synset;
        private int current_word;
        public nouns_iterator() {
            current_synset=0;
            current_word=0;
        }

        public boolean hasNext() {
            if( current_word < synset.get(current_synset).length ) {
                return true;
            }
            else
            {
                if( current_synset < synset.size() ) {
                    return true;
                }
                else
                {
                    return false;
                }
            }

        }

        public String next() {
            if( current_word < synset.get(current_synset).length ) {
                current_word++;
                return synset.get(current_synset)[current_word-1];
            }
            else
            {
                if( current_synset < synset.size() ) {
                    current_word=1;
                    current_synset++;
                    return synset.get(current_synset-1)[current_word-1];
                }
                else
                {
                    throw new IllegalStateException("Next called with no next.");
                }
            }

        }
    }


    private class nouns_iterable implements Iterable<String> {
        public Iterator<String> iterator() {
            return new nouns_iterator();
        }

    }
    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return new nouns_iterable();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null ) throw new IllegalArgumentException();
        return synset.contains(word);
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
            if (i> 10 ) break;

        }

    }
}