import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;


public class Deque<Item> implements Iterable<Item> {

    private Node first,last = null;
    private static int size = 0;

    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if ( item == null ) {
            throw new IllegalArgumentException("Trying to addFirst null item");
        }

        Node N = new Node();
        N.item=item;

        if ( isEmpty() ) {
            first = N;
            last = N;
        }
        else
        {
            N.previous=first;
            first.next=N;
            first=N;
        }
        size=size+1;
    }

    // add the item to the back
    public void addLast(Item item) {

        if ( item == null ) {
            throw new IllegalArgumentException("Trying to addLast null item");
        }

        Node N = new Node();
        N.item=item;

        if ( isEmpty() ) {
            first = N;
            last = N;
        }
        else
        {
            N.next=last;
            last.previous=N;
            last=N;
        }
        size=size+1;
    }

    // remove and return the item from the front
    public Item removeFirst() {

        if ( isEmpty() ) {
            throw new java.util.NoSuchElementException("Trying to removeFirst while deque is empty");
        }

        Item item = first.item;

        if ( size == 1 ) {
            first=null;
        }
        else
        {
            Node oldfirst=first;
            first = oldfirst.next;
            first.previous = null;
            oldfirst=null;
        }

        size=size-1;

        return item;

    }

    // remove and return the item from the back
    public Item removeLast() {
        if ( isEmpty() ) {
            throw new java.util.NoSuchElementException("Trying to removeFirst while deque is empty");
        }

        Item item = last.item;

        if ( size == 1 ) {
            last=null;
        }
        else {
            Node oldlast = last;
            last=oldlast.previous;
            last.next = null;
            oldlast=null;
        }

        size=size-1;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return iterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        dq.addLast(5);
        dq.addLast(7);
        Integer item=dq.removeFirst();
        StdOut.println("item = " + item);
        item=dq.removeFirst();
        StdOut.println("item = " + item);
    }

}
