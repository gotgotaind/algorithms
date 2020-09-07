import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

// import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
// import java.util.Iterator;

public class WordNet {

    private final HashMap<String, Bag<Integer>> nouns_synsets;

    private final ArrayList<String> synsets;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null ) throw new IllegalArgumentException();

        In in = new In(synsets);
        this.nouns_synsets = new HashMap<String, Bag<Integer>>();
        this.synsets=new ArrayList<String>();

        int line_nb=0;
        while ( ! in.isEmpty() ) {
            String[] line = in.readLine().split(",");
            this.synsets.add(line_nb,line[1]);
            for( String s:line[1].split(" ")) {
                // initialize new nouns_synsets key if it does not exist
                if ( ! nouns_synsets.containsKey(s)) {
                    nouns_synsets.put(s,new Bag<Integer>());
                }
                nouns_synsets.get(s).add(line_nb);
            }
            line_nb++;
        }

        Digraph dg=new Digraph(line_nb);

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

        /*
        DirectedCycle dc=new DirectedCycle(dg);
        if( dc.hasCycle() ) {
            throw new IllegalArgumentException("Not a DAG");
        }
        */

        Topological topo=new Topological(dg);
        if ( ! topo.hasOrder() ) {
            throw new IllegalArgumentException("Not a DAG");
        }

        /*
        int first=topo.order().iterator().next();
        if ( dg.outdegree(first) > 1 ) {
            throw new IllegalArgumentException("Not a rooted DAG");
        }
        */

        int root = -1;
        for (int v = 0; v < dg.V(); v++) {
            if (dg.outdegree(v) == 0) {
                if (root != -1) {
                    throw new IllegalArgumentException("Not a rooted DAG");
                }
                root=v;
            }
        }

        sap=new SAP(dg);
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
        if (nounA == null || nounB == null ) throw new IllegalArgumentException();
        return sap.length(nouns_synsets.get(nounA),nouns_synsets.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null ) throw new IllegalArgumentException();
        return synsets.get(sap.ancestor(nouns_synsets.get(nounA),nouns_synsets.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn=new WordNet("synsets3.txt","hypernyms3InvalidTwoRoots.txt");
        //WordNet wn=new WordNet("C:\\data\\projects\\algorithm\\wordnet\\synsets15.txt","C:\\data\\projects\\algorithm\\wordnet\\hypernyms15Path.txt");


        //String a=new String("baboo");
        String a="baboo";
        StdOut.println("Is '"+a+"' a noun? : "+wn.isNoun(a));

        a="zoophilia zoophilism";
        StdOut.println("Is '"+a+"' a noun? : "+wn.isNoun(a));
        /*
        StdOut.println("1000 word is "+wn.synset.get(1000));


        for( String s: wn.nouns() ) {
            StdOut.println(s);
        }
        */

        /*
        // StdOut.println(wn.dg.toString());
        int i=0;
        for(String w:wn.nouns()) {
            StdOut.println(w);
            i++;
            //if (i> 30 ) break;

        }

        */
        StdOut.println(wn.sap("horse","zebra"));


    }
}