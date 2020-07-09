/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class DequeArr<Item> implements Iterable<Item> {
    private Item[] d;
    private int first;
    private int last;

    // construct an empty deque
    public DequeArr() {
        d = (Item[]) new Object[2];
        first = 0;
        last = 0;
    }

    /*private void checksize() {
        int size = size();
        if (first == 0 || last == d.length) { // deque at the boundary
            if (size >= d.length / 2) { // deque off balance
                resize(d.length * 2);
            }
            else {
                resize(d.length);
            }
        }
        else if (size > 1 && size == d.length / 4) { // deque too small
            resize(d.length / 2);
        }
    }

    private void resize(int cap) {
        Item[] copy = (Item[]) new Object[cap];
        int size = size();
        int newFirst = (cap - size) / 2;

        for (int i = 0; i < size; i++) {
            copy[newFirst + i] = d[first + i];
        }
        d = copy;
        first = newFirst;
        last = newFirst + size;
    }*/

    private void checksize() {
        int size = size();
        if (first == 0 && last == d.length) { // deque is full
            resize(d.length * 2, size);
        }
        else if (size > 0 && (first == 0 || last == d.length)) { // deque is off balance, recenter
            resize(d.length, size);
        }
        else if (size > 1 && size == d.length / 4) { // deque too small, downsize
            resize(d.length / 2, size);
        }
    }

    private void resize(int cap, int size) {
        Item[] copy = (Item[]) new Object[cap];
        int newFirst = (cap - size) / 2;

        for (int i = 0; i < size; i++) {
            copy[newFirst + i] = d[first + i];
        }

        d = copy;
        first = newFirst;
        last = newFirst + size;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return last - first;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkNull(item);
        checksize();
        if (isEmpty()) {
            d[first] = item;
            last++;
        }
        else {
            d[--first] = item;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (isEmpty()) {
            addFirst(item);
        }
        else {
            checkNull(item);
            checksize();
            d[last++] = item;
        }
    }

    private void checkNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkEmpty();
        Item result;
        if (size() == 1) {
            result = d[first];
            d[first] = null;
            last--;
        }
        else {
            result = d[first++];
            d[first - 1] = null;
        }
        checksize();
        return result;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkEmpty();
        Item result;
        if (size() == 1) {
            result = removeFirst();
        }
        else {
            result = d[--last];
            d[last] = null;
            checksize();
        }
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
        private int current = first;

        public boolean hasNext() {
            return current != last;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return d[current++];
        }

    }

    private void print() {
        for (Item i : d) {
            if (i == null) {
                StdOut.print("-");
            }
            else {
                StdOut.print(i);
            }
            StdOut.print(" ");
        }
        StdOut.print("f: " + first + " l: " + last);
        StdOut.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        DequeArr<Integer> deq = new DequeArr<>();
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
