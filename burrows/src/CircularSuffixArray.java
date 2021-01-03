import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;
import edu.princeton.cs.algs4.TrieST;

public class CircularSuffixArray {

    int l;
    String s;
    int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        l=s.length();
        this.s=s;
        index=new int[l];
        char[] ca=s.toCharArray();


    }

    public String ics(int i) {
        char[] sca=s.toCharArray();
        char[] icsa=new char[l];

        for(int j=0; j<l; j++) {
            icsa[j]=sca[(i+j)%l];
        }
        return new String(icsa);
    }

    // length of s
    public int length() {
        return l;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        // CircularSuffixArray c=new CircularSuffixArray("ABRACADABRA!");
        CircularSuffixArray c=new CircularSuffixArray("ABABABABABAB");
        for(int i=0; i<c.length(); i++ ) {
            StdOut.println(c.ics(i)+"\t"+c.index[i]);
        }

    }

}
