import java.util.Iterator;

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
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if ( size > 0 ) {
            oldfirst.previous = first;
        }
        else
        {
            last=first;
        }
        size=size+1;
    }

    // add the item to the back
    public void addLast(Item item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.previous = oldlast;
        if ( size > 0 ) {
            oldlast.next = last;
        }
        else {
            first = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        Item item = first.item;
        first = first.next;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        Item item = last.item;
        last = last.previous;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return iterator();
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
