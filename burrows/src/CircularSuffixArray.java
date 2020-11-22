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


        TrieSET ts=new TrieSET();
        TrieST<Integer> tst = new TrieST<Integer>();

        for(int i=0; i<l; i++) {
            String ics=this.ics(i);
            ts.add(ics);
            tst.put(ics,i);
        }

        int i=0;
        for(String ss:ts) {
            index[i]=tst.get(ss);
            i++;
        }

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
        CircularSuffixArray c=new CircularSuffixArray("ABRACADABRA!");
        for(int i=0; i<c.length(); i++ ) {
            StdOut.println(c.ics(i)+"\t"+c.index[i]);
        }

    }

}
