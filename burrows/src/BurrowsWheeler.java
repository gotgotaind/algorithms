import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.LSD;
import edu.princeton.cs.algs4.MSD;
import edu.princeton.cs.algs4.Quick3way;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BurrowsWheeler {

    private final static int R = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        StringBuilder sb=new StringBuilder();
        //BinaryStdOut.write(42);
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            sb.append(c);
            //BinaryStdOut.write(c);
        }
        String s=new String(sb);
        CircularSuffixArray c=new CircularSuffixArray(s);
        for(int i=0; i<s.length(); i++) {
            if( c.index(i)==0 ) {
                BinaryStdOut.write(i);
                break;
            }
        }
        int l=s.length();
        for(int i=0; i<s.length(); i++) {
            int sai=c.index(i);
            char last_char_of_ith_circular_rotation=s.charAt(((l-1)+sai)%l);
            BinaryStdOut.write(last_char_of_ith_circular_rotation);
        }
        BinaryStdOut.flush();

    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first=BinaryStdIn.readInt();

        String st = BinaryStdIn.readString();
        char[] t = st.toCharArray();

        int l=t.length;

        // compute frequency counts
        int[] count = new int[R+1];
        char[] ts = new char[l];
        int next[]=new int[l];

        for (int i = 0; i < l; i++)
            count[t[i] + 1]++;

        // compute cumulates
        for (int r = 0; r < R; r++)
            count[r+1] += count[r];

        // move data
        for (int i = 0; i < t.length; i++) {
            int j = count[t[i]]++;
            ts[j] = t[i];
            next[j] = i;
        }

        // reconstruct the original string provided first and next[]
        //BinaryStdOut.write(first);
        int nexti=first;
        for(int i=1; i<l; i++) {
            char nextc=ts[nexti];
            BinaryStdOut.write(nextc);
            nexti=next[nexti];
        }
        BinaryStdOut.write(ts[nexti]);
        BinaryStdOut.flush();

    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        //BinaryStdOut.write(42);
        if (args[0].equals("-")) {
            transform();
        } else if (args[0].equals("+")) {
            inverseTransform();
        } else {
            throw new IllegalArgumentException();
        }
    }
}