import edu.princeton.cs.algs4.Heap;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.LSD;
import edu.princeton.cs.algs4.MSD;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.Quick3way;
import edu.princeton.cs.algs4.Selection;
import edu.princeton.cs.algs4.Shell;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

public class SortCompare
{
    public static double time(String alg, Comparable[] a)
    {
        Stopwatch timer = new Stopwatch();
        if (alg.equals("Insertion")) Insertion.sort(a);
        if (alg.equals("Selection")) Selection.sort(a);
        if (alg.equals("Shell")) Shell.sort(a);
        if (alg.equals("Merge")) Merge.sort(a);
        if (alg.equals("Quick")) Quick.sort(a);
        if (alg.equals("Quick3way")) Quick3way.sort(a);
        if (alg.equals("Heap")) Heap.sort(a);
        return timer.elapsedTime();
    }

    public static double timeLSD(String alg, int[] a)
    {
        Stopwatch timer = new Stopwatch();
        if (alg.equals("LSD")) {
            StdOut.printf("Doing LSD \n");
            LSD.sort(a);
        }
        if (alg.equals("MSD"))
        {
            StdOut.printf("Doing MSD \n");
            MSD.sort(a);
        }
        return timer.elapsedTime();
    }

    public static double timeRandomInput(String alg, int N, int T)
    { // Use alg to sort T random arrays of length N.
        double total = 0.0;
        int n = 214;  // number of possible values max int = 2147483647
        //Double[] a = new Double[N];

        for (int t = 0; t < T; t++) { // Perform one experiment (generate and sort an array).
            if (alg == "LSD" || alg == "MSD") {
                int[] a = new int[N];
                for (int i = 0; i < N; i++) {
                    a[i] = StdRandom.uniform(n);
                }
                total += timeLSD(alg, a);
                if ( ! isSorted(a) ) throw new RuntimeException();
            } else {
                Integer[] a = new Integer[N];
                for (int i = 0; i < N; i++) {
                    a[i] = StdRandom.uniform(n);
                }
                total += time(alg, a);
                if ( ! isSorted(a) ) throw new RuntimeException();
            }
        }
        return total;
    }

    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i <= a.length - 1; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i <= a.length - 1; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    // is v < w ?
    private static boolean less(int v, int w) {
        return (v-w) < 0;
    }
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static long getGarbageCollectionTime() {
        long collectionTime = 0;
        for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            collectionTime += garbageCollectorMXBean.getCollectionTime();
        }
        return collectionTime;
    }

    public static void main(String[] args)
    {
        String alg1 = "Quick3way";
        String alg2 = "LSD";
        int N = Integer.parseInt("100000000");
        int T = Integer.parseInt("1");
        double t1 = timeRandomInput(alg1, N, T); // total for alg1
        double t2 = timeRandomInput(alg2, N, T); // total for alg2
        StdOut.printf(alg1+" took "+t1+"\n");
        StdOut.printf(alg2+" took "+t2+"\n");
        StdOut.printf("For %d random Doubles\n %s is", N, alg1);
        StdOut.printf(" %.1f times faster than %s\n", t2/t1, alg2);
        System.out.println("collectionTime = " + getGarbageCollectionTime());
    }
}