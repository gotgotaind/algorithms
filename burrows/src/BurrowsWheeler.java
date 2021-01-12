import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

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