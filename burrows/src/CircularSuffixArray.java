import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;
import edu.princeton.cs.algs4.TrieST;

public class CircularSuffixArray {

    private int l;
    private String s;
    private int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        l=s.length();
        this.s=new String(s);
        index=new int[l];
        ics[] icsa=new ics[l];
        // StdOut.println("icsa before sorting :");
        for(Integer i=0; i<l; i++) {
            icsa[i]=new ics(s,i);
            // StdOut.println(icsa[i].icss());
        }

        Quick.sort(icsa);

        // StdOut.println("icsa after sorting :");
        for(Integer i=0; i<l; i++) {
            index[i]=icsa[i].ith;
            // StdOut.println(icsa[i].icss());
        }
    }

    // ith circular suffix
    private class ics implements Comparable<ics> {
        Integer ith;
        String s;

        public ics(String s,int ith) {
            this.s=s;
            this.ith=ith;
        }

        public String icss() {
            char[] sca=s.toCharArray();
            char[] icsa=new char[l];

            for(int j=0; j<l; j++) {
                icsa[j]=sca[(ith+j)%l];
            }
            return new String(icsa);
        }

        public int compareTo(ics icst){
            // StdOut.println("A: "+this.icss()+" B: "+icst.icss()+" :"+this.icss().compareTo(icst.icss()));
            return this.icss().compareTo(icst.icss());
        }
    }

    // also add this ics method in the parent class in order to be able to print the ith circular string
    private String ics_debug(Integer ii) {
        char[] sca=s.toCharArray();
        char[] icsa=new char[l];

        for(int j=0; j<l; j++) {
            icsa[j]=sca[(ii+j)%l];
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
        //CircularSuffixArray c=new CircularSuffixArray("ABABABABABAB");
        //CircularSuffixArray c=new CircularSuffixArray("ABAB");
        for(int i=0; i<c.length(); i++ ) {
            StdOut.println(c.ics_debug(c.index[i])+"\t"+c.index[i]);
        }

    }

}
