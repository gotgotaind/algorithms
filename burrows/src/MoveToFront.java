import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdOut;

public class MoveToFront {



    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {

        final char[] ci;
        final int R=256;

        ci=new char[R];
        for(int i = 0; i < R ; i++ ) {
            ci[i]=(char) i;
        }

        // read one 8-bit char at a time
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            if( c == ci[0]) {
                BinaryStdOut.write(0,8);
            }
            else {
                int i = 0;
                char t0 = c;
                char t1 = ci[i];

                while (t1 != c) {
                    t1 = ci[i];
                    ci[i] = t0;
                    t0 = t1;
                    i = i + 1;
                }

                BinaryStdOut.write(i-1, 8);
            }
        }
        BinaryStdOut.flush();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {

        final char[] ci;
        final int R=256;

        ci=new char[R];
        for(int i = 0; i < R ; i++ ) {
            ci[i]=(char) i;
        }


        // read one 8-bit char at a time
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(ci[c]);

            int i = 0;
            char t0 = ci[c];
            char t1 = ci[i];

            while (t1 != ci[c]) {
                t1 = ci[i];
                ci[i] = t0;
                t0 = t1;
                i = i + 1;
            }


        }
        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if( args[0].equals("-") ) {
            encode();
        }
        else if ( args[0].equals("+") ) {
            decode();
        }
        else {
            throw new IllegalArgumentException();
        }
    }
}