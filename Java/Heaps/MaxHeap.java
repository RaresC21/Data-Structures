import java.util.NoSuchElementException;

/**
 * Your implementation of a max heap.
 *
 * @author Rares Cristian
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial length of {@code INITIAL_CAPACITY} for the
     * backing array.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[HeapInterface.INITIAL_CAPACITY];
    }

    /**
     * Swap elements at index a and b in backing array
     *
     * @param a an index to swap
     * @param b other to index to swap with
    */
    private void swap(int a, int b) {
        T temp = backingArray[b];
        backingArray[b] = backingArray[a];
        backingArray[a] = temp;
    }

    /**
     * Helper function for add(T item)
     *
     * @param position the current index of added item
    */
    private void heapifyUp(int position) {
        if (position == 1) {
            return;
        }

        if (backingArray[position / 2].compareTo(backingArray[position]) < 0) {
            swap(position, position / 2);
            heapifyUp(position / 2);
        }
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null. Can't add it.");
        }

        if (size + 1 == backingArray.length) {
            T[] temp = (T[]) new Comparable[backingArray.length * 2 + 1];
            for (int i = 0; i <= size; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }

        backingArray[size + 1] = item;
        heapifyUp(size + 1);
        size++;
    }

    /**
     * Helper function for remove()
     *
     * @param pos current index of element we need to correctly place
    */
    private void heapifyDown(int pos) {

        boolean lessRightChild = false;
        if (2 * pos + 1 <= size && backingArray[pos]
            .compareTo(backingArray[2 * pos + 1]) < 0) {
            lessRightChild = true;
        }

        boolean lessLeftChild = false;
        if (2 * pos <= size && backingArray[pos]
            .compareTo(backingArray[2 * pos]) < 0) {
            lessLeftChild = true;
        }

        if (lessLeftChild && lessRightChild) {
            if (backingArray[2 * pos]
                .compareTo(backingArray[2 * pos + 1]) > 0) {
                swap(2 * pos, pos);
                heapifyDown(2 * pos);
            } else {
                swap(2 * pos + 1, pos);
                heapifyDown(2 * pos + 1);
            }
        } else if (lessLeftChild) {
            swap(2 * pos, pos);
            heapifyDown(2 * pos);
        } else if (lessRightChild) {
            swap(2 * pos + 1, pos);
            heapifyDown(2 * pos + 1);
        }
    }

    @Override
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Empty heap. Can't remove.");
        }

        T ans = backingArray[1];
        backingArray[1] = backingArray[size];
        heapifyDown(1);
        backingArray[size] = null;
        size--;
        return ans;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[HeapInterface.INITIAL_CAPACITY];
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT CHANGE THIS METHOD!
        return backingArray;
    }
}