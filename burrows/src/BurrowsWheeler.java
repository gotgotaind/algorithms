import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.LSD;
import edu.princeton.cs.algs4.MSD;
import edu.princeton.cs.algs4.Quick3way;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BurrowsWheeler {

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
        ArrayList<Character> t=new ArrayList<Character>();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            t.add(c);
            //BinaryStdOut.write(c);
        }
        int l=t.size();

        // copying t to an array of strings whose first chat is t[i] and the
        // next eight ones, are i encoded as a hexadecimal string
        // to be able to sort it using LSD which guarentees NW running time
        // which should be N*9 because W=1+8
        String[] ff=new String[l];
        for(int i=0; i<l; i++) {
            String iAsHexString = String.format("%08X", i);
            ff[i]=t.get(i).toString()+iAsHexString;
        }
        LSD.sort(ff,9);

        // now we have to "decode" ff
        // fc if the array of first chars of the circular array
        char[] fc=new char[l];
        // fci is the array of the (int) index in the sorted circular array
        int[] fci=new int[l];
        for(int i=0; i<l; i++) {
            char[] ffc=ff[i].toCharArray();
            fc[i]=ffc[0];
            char[] fci_char_array=new char[10];
            // must begin with 0x to mean hexadecimal
            fci_char_array[0]='0';
            fci_char_array[1]='x';
            for(int j=0; j<8; j++) {
                fci_char_array[j+2]=ffc[j+1];
            }
            String fci_string=new String(fci_char_array);
            fci[i]=Integer.decode(fci_string);
        }

        // build the next[] array
        int next[]=new int[l];
        for(int i=0; i<l; i++) {
            next[i]=fci[i];
            // StdOut.println(next[i]);
        }

        // reconstruct the original string provided first and next[]
        BinaryStdOut.write(first);
        int nexti=next[0];
        for(int i=1; i<l; i++) {
            char nextc=fc[nexti];
            BinaryStdOut.write(nextc);
            nexti=next[nexti];
        }
        BinaryStdOut.write(fc[nexti]);
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