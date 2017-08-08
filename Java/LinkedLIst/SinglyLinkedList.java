import java.util.NoSuchElementException;
/**
 * Your implementation of a SinglyLinkedList
 *
 * @author Rares Cristian
 * @version 1.0
 */

public class SinglyLinkedList<T> implements LinkedListInterface<T> {
    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            int s = 0;
            LinkedListNode<T> cur = head;
            while (s < index - 1) {
                cur = (cur.getNext());
                s++;
            }
            LinkedListNode<T> add = new LinkedListNode<T>(data, cur.getNext());
            cur.setNext(add);
            size++;
        }
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data passed in cannot be null");
        }
        if (head == null) {
            head = new LinkedListNode<T>(data);
            tail = head;
        } else {
            head = new LinkedListNode<T>(data, head);
        }
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data passed in cannot be null");
        }
        if (head == null) {
            head = new LinkedListNode<T>(data);
            tail = head;
        } else {
            LinkedListNode<T> cur = head;
            while (cur.getNext() != null) {
                cur = cur.getNext();
            }
            cur.setNext(new LinkedListNode<T>(data));
            tail = cur.getNext();
        }
        ++size;
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            int s = 0;
            LinkedListNode<T> cur = head;
            while (s < index - 1) {
                cur = (cur.getNext());
                ++s;
            }
            LinkedListNode<T> node = cur.getNext();
            cur.setNext(cur.getNext().getNext());
            size--;
            return node.getData();
        }
    }

    @Override
    public T removeFromFront() {
        LinkedListNode<T> node = head;
        if (head == null) {
            return null;
        }
        head = head.getNext();
        size--;
        if (size == 0) {
            tail = null;
        }
        return node.getData();
    }

    @Override
    public T removeFromBack() {
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            LinkedListNode<T> ans = head;
            head = null;
            tail = null;
            size--;
            return ans.getData();
        }
        LinkedListNode<T> cur = head;
        while (cur.getNext() != tail) {
            cur = cur.getNext();
        }
        LinkedListNode<T> node = tail;
        tail = cur;
        tail.setNext(null);
        size--;
        return node.getData();
    }

    @Override
    public T removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Error, this data is null");
        }
        if (size == 0) {
            throw new NoSuchElementException("Empty list, nothing to remove");
        }
        if (head.getData().equals(data)) {
            return removeFromFront();
        }

        LinkedListNode<T> cur = head;
        if (!head.getData().equals(data)) {
            while (cur.getNext() != null && !cur.getNext().getData().equals(data)) {
                cur = cur.getNext();
            }
        } else {
            T ans = head.getData();
            head = head.getNext();
            size--;
            return ans;
        }

        if (cur.getNext() == null || !cur.getNext().getData().equals(data)) {
            throw new NoSuchElementException("This element is not in list");
        }

        LinkedListNode<T> node = cur.getNext();

        if (cur.getNext() == tail) {
            tail = cur;
        }

        cur.setNext(cur.getNext().getNext());
        size--;
        return node.getData();
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index passed in less than 0");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("index passed in larger than size of list");
        }
        LinkedListNode<T> cur = head;
        int s = 0;
        while (s != index) {
            cur = cur.getNext();
            ++s;
        }
        return cur.getData();
    }

    @Override
    public Object[] toArray() {
        T[] arr = (T[]) new Object[size];

        int index = 0;
        LinkedListNode<T> cur = head;
        while (cur != null) {
            arr[index] = cur.getData();
            cur = cur.getNext();
            ++index;
        }

        return arr;
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
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    @Override
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}
