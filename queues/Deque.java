/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Item[] d;
    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    private class Node {
        Item val;
        Node prev;
        Node next;

        // construct a node with val i
        public Node(Item i) {
            val = i;
        }
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
        checkNull(item);
        Node newFirst = new Node(item);
        newFirst.next = first;
        if (isEmpty()) {
            last = newFirst;
        }
        else {
            first.prev = newFirst;
        }
        first = newFirst;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        checkNull(item);
        Node newLast = new Node(item);
        newLast.prev = last;
        if (isEmpty()) {
            first = newLast;
        }
        else {
            last.next = newLast;
        }
        last = newLast;
        size++;
    }

    private void checkNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkEmpty();
        Item result = first.val;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        size--;
        return result;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkEmpty();
        Item result = last.val;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        size--;
        return result;
    }

    private void checkEmpty() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DeqIterator();
    }

    private class DeqIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item result = current.val;
            current = current.next;
            return result;
        }

    }

    private void print() {
        Node curr = first;
        while (curr != null) {
            StdOut.print(curr.val + " ");
            curr = curr.next;
        }
        StdOut.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deq = new Deque<>();
        //deq.addLast(1);
        //deq.addLast(2);
        //StdOut.print(deq.removeFirst());

        //deq.addFirst(1);
        //deq.addFirst(2);
        //deq.removeLast();
        //deq.addFirst(3);

        deq.addLast(3);
        deq.print();
        deq.addLast(4);
        deq.addLast(4);
        deq.print();
        deq.removeFirst();
        deq.print();
        deq.addLast(3);
        deq.removeLast();


        deq.print();

        deq.print();
        // error case: - - - - 3 3 - - f: 4 l: 6

        for (int i = 0; i < 10000; i++) {
            int r = StdRandom.uniform(6);
            if (r == 0) {
                deq.size();
            }
            else if (r == 1 && !deq.isEmpty()) {
                deq.removeLast();
            }
            else if (r == 2) {
                deq.isEmpty();
            }
            else if (r == 3) {
                deq.addFirst(r);
            }
            else if (r == 4) {
                deq.addLast(r);
            }
            else if (r == 5 && !deq.isEmpty()) {
                deq.removeFirst();
            }
            deq.print();
        }

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                deq.addFirst(i);
            }
            else {
                deq.addLast(i);
            }
        }
        for (Integer j : deq) {
            StdOut.println(j.toString());
        }
        while (!deq.isEmpty()) {
            deq.removeLast();
            deq.print();
        }

        for (int i = 0; i < 20; i++) deq.addFirst(i);
        for (int i = 0; i < 20; i++) deq.removeLast();

        deq.addFirst(1);
        deq.print();
        deq.addFirst(2);
        deq.print();
    }

}
