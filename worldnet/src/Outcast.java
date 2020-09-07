import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wn;
    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        wn = wordnet;
    }

    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
    {
        int[] d = new int[nouns.length];
        int max = 0;
        int max_id = 0;

        for (int i = 0; i < nouns.length; i++) {
            for (int j = 0; j < nouns.length; j++) {
                d[i] = d[i] + wn.distance(nouns[i],nouns[j]);
            }
            if (d[i] > max) {
                max = d[i];
                max_id = i;
            }
        }
        return nouns[max_id];
    }
    public static void main(String[] args)  // see test client below
    {
        WordNet wordnet = new WordNet("synsets.txt", "hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
            String outcasts_file = "outcast11.txt";
            In in = new In(outcasts_file);
            String[] nouns = in.readAllStrings();
            StdOut.println(outcasts_file + ": " + outcast.outcast(nouns));
    }
}
