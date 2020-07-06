/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] d;
    private int first;
    private int last;
    private boolean shuffled;

    // construct an empty randomized queue
    public RandomizedQueue() {
        d = (Item[]) new Object[1];
        first = 0;
        last = 0;
    }

    private void checksize() {
        int size = size();
        if (last == d.length) { // deque at the boundary
            if (size >= d.length / 2) { // deque off balance
                resize(d.length * 2);
            }
            else {
                resize(d.length);
            }
        }
        else if (size > 0 && size == d.length / 4) { // deque too small
            resize(d.length / 2);
        }
    }

    private void resize(int cap) {
        Item[] copy = (Item[]) new Object[cap];
        int size = size();

        for (int i = 0; i < size; i++) {
            copy[i] = d[first + i];
        }
        d = copy;
        first = 0;
        last = size;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return last - first;
    }

    // add the item
    public void enqueue(Item item) {
        checkNull(item);
        checksize();
        shuffled = false;
        d[last++] = item;
    }

    private void checkNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    // remove and return a random item
    public Item dequeue() {
        checkEmpty();
        if (!shuffled) {
            StdRandom.shuffle(d, first, last);
            shuffled = true;
        }
        Item result = d[--last];
        d[last] = null;
        checksize();
        return result;
    }

    private void checkEmpty() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkEmpty();
        return d[StdRandom.uniform(first, last)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandIterator();
    }

    private class RandIterator implements Iterator<Item> {
        private int current;
        private Item[] i;

        public RandIterator() {
            this.current = first;
            this.i = d.clone();
            StdRandom.shuffle(i, first, last);
        }

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
            return i[current++];
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
        StdOut.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rand = new RandomizedQueue<>();
        for (int i = 0; i < 60; i++) {
            rand.enqueue(i);
            rand.print();
        }
        while (!rand.isEmpty()) {
            rand.dequeue();
            rand.print();
        }
    }
}
