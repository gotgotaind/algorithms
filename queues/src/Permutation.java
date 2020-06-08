import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq=new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            rq.enqueue(value);
        }

        Iterator<String> it=rq.iterator();
        for(int i=0;i<k;i++) {
            StdOut.println(it.next());
        }

    }
}