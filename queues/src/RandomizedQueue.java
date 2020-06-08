import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int head = 0;
    private int tail = 0;
    private int capa=2;
    
    // construct an empty randomized queue
    public RandomizedQueue()
    {
        s = (Item[]) new Object[capa];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {  return (tail-head)==0; }

    // return the number of items on the randomized queue
    public int size() { return (tail-head); }

    // add the item
    public void enqueue(Item item) {
        if ( item == null ) {
            throw new IllegalArgumentException("Trying to enqueue null item");
        }
        // extend s if max capacity is reached
        if( tail >= capa ) {
            Item[] t=(Item[]) new Object[capa*2];
            for( int i=0 ; i<capa; i++  ) {
                t[i]=s[i];
            }
            capa=capa*2;
            s=t;
        }

        s[tail] = item;
        tail=tail+1;
    }

    // remove and return a random item
    public Item dequeue() {

        if ( isEmpty() ) {
            throw new java.util.NoSuchElementException("Trying to dequeue while RandomizedQueue is empty");
        }

        //Returns a random real number uniformly in [a, b).
        int e=StdRandom.uniform(head,tail);
        Item item=s[e];
        s[e]=s[tail-1];
        tail=tail-1;

        // shrink s if half full
        if( tail < ( capa / 2 ) ) {
            Item[] t=(Item[]) new Object[capa/2];
            for( int i=0 ; i<tail; i++  ) {
                t[i]=s[i];
            }
            capa=capa/2;
            s=t;
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if ( isEmpty() ) {
            throw new java.util.NoSuchElementException("Trying to sample while RandomizedQueue is empty");
        }
        return s[StdRandom.uniform(head,tail)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = head;
        private int[] map=new int[tail];

        public RandomizedQueueIterator() {
            for(int i=0;i<tail;i++) {
                map[i]=i;
            }
            StdRandom.shuffle(map);
        }

        public boolean hasNext() {
            return current < tail;
        }

        public void remove() {
            /* not supported */
            throw new UnsupportedOperationException("remove() is not support in DequeuIterator");
        }

        ;

        public Item next() {

            if (current >= tail) {
                throw new java.util.NoSuchElementException("Trying to get next from iterator after end");
            }

            Item item = s[map[current]];

            current = current + 1;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq=new RandomizedQueue<Integer>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);

        for (int i : rq)
            StdOut.println(i);
        for (int i : rq)
            StdOut.println(i);

        Integer item=rq.dequeue();
        StdOut.println("item = " + item +" capa = " + rq.capa);
        item=rq.dequeue();
        StdOut.println("item = " + item +" capa = " + rq.capa);
        item=rq.dequeue();
        StdOut.println("item = " + item +" capa = " + rq.capa);
        item=rq.dequeue();
        StdOut.println("item = " + item +" capa = " + rq.capa);
        item=rq.dequeue();
        StdOut.println("item = " + item +" capa = " + rq.capa);
        item=rq.dequeue();
        StdOut.println("item = " + item +" capa = " + rq.capa);

    }
}
