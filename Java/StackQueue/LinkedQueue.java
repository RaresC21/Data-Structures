import java.util.NoSuchElementException;

/**
 * Your implementation of a linked queue.
 *
 * @author Rares Cristian
 * @version 1.0
 */
public class LinkedQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    @Override
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Empty queue. Nothing to dequeue");
        }
        LinkedNode<T> ans = head;
        head.setNext(head.getNext());
        head = head.getNext();
        if (head == null) {
            tail = null;
        }
        size--;
        return ans.getData();
    }

    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("This data is null.");
        }
        if (tail == null) {
            tail = new LinkedNode<T>(data);
            head = new LinkedNode<T>(data);
        } else {
            tail.setNext(new LinkedNode<T>(data));
            tail = tail.getNext();
            if (head.getNext() == null) {
                head.setNext(tail);
            }
        }
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the head of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the tail node
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}
