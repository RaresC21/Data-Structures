import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Your implementation of a binary search tree.
 *
 * @author Rares Cristian
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        for (T elem : data) {
            add(elem);
        }
    }

    /**
     * Helper method for add(T data)
     *
     * @param cur the current node inspected.
     * @param data the data to be added
     */
    private void add(BSTNode<T> cur, T data) {
        if (cur.getData().equals(data)) {
            return;
        }
        if (cur.getData().compareTo(data) > 0) {
            if (cur.getLeft() == null) {
                cur.setLeft(new BSTNode<T>(data));
                size++;
                return;
            }
            add(cur.getLeft(), data);
        } else {
            if (cur.getRight() == null) {
                cur.setRight(new BSTNode<T>(data));
                size++;
                return;
            }
            add(cur.getRight(), data);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input is null. Can't insert");
        }
        if (size == 0) {
            root = new BSTNode<T>(data);
            size++;
            return;
        }
        add(root, data);
    }

    /**
     *
     * @param curNode The node queried.
     * @return the parent of the successor of curNode
     */
    private BSTNode<T> successor(BSTNode<T> curNode) {
        BSTNode<T> parent = curNode;
        curNode = curNode.getRight();
        while (curNode.getLeft() != null) {
            parent = curNode;
            curNode = curNode.getLeft();
        }
        return parent;
    }

    /**
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @param curNode the current node inspected
     * @param parent the parent of curNode
     * @return the data removed from the tree.  Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    private T remove(T data, BSTNode<T> curNode, BSTNode<T> parent) {
        if (curNode.getData().equals(data)) {
            if (curNode.getLeft() != null && curNode.getRight() != null) {
                BSTNode<T> successorParent = successor(curNode);
                BSTNode<T> suc;

                if (successorParent == curNode) {
                    suc = successorParent.getRight();
                } else {
                    suc = successorParent.getLeft();
                }

                remove(suc.getData(), suc, successorParent);

                if (parent.getLeft() == curNode) {
                    parent.setLeft(suc);
                } else {
                    parent.setRight(suc);
                }

                if (curNode.getRight() != null) {
                    suc.setRight(curNode.getRight());
                }
                if (curNode.getLeft() != null) {
                    suc.setLeft(curNode.getLeft());
                }

                return curNode.getData();
            } else if (parent.getLeft() == curNode) {
                --size;
                BSTNode<T> temp;
                if (curNode.getRight() != null) {
                    temp = curNode.getRight();
                } else {
                    temp = curNode.getLeft();
                }
                parent.setLeft(temp);
            } else if (parent.getRight() == curNode) {
                --size;
                BSTNode<T> temp;
                if (curNode.getRight() != null) {
                    temp = curNode.getRight();
                } else {
                    temp = curNode.getLeft();
                }
                parent.setRight(temp);
            }
            return curNode.getData();
        }

        if (curNode.getData().compareTo(data) > 0
            && curNode.getLeft() != null) {
            return remove(data, curNode.getLeft(), curNode);
        }
        if (curNode.getData().compareTo(data) < 0
            && curNode.getRight() != null) {
            return remove(data, curNode.getRight(), curNode);
        }
        throw new NoSuchElementException("This element is not in the BST");
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data is not valid input");
        }
        if (size == 0) {
            throw new NoSuchElementException("Empty tree, so no elements "
                + "to remove");
        }
        if (root.getData().equals(data)) {
            BSTNode<T> temp = new BSTNode<T>(null);
            temp.setLeft(root);
            T ans = remove(data, root, temp);
            root = temp.getLeft();
            return ans;
        }
        return remove(data, root, null);
    }

    /**
     * helper function for get(data)
     *
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @param cur current node being inspected
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    private T get(BSTNode<T> cur, T data) {
        if (cur.getData().equals(data)) {
            return cur.getData();
        }
        if (cur.getLeft() != null && cur.getData().compareTo(data) > 0) {
            return get(cur.getLeft(), data);
        } else if (cur.getRight() != null) {
            return get(cur.getRight(), data);
        }
        throw new NoSuchElementException("This data doesn't exist in the BST");
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for null data");
        }
        if (size == 0) {
            throw new NoSuchElementException("The tree is empty,"
                + " so cannot search for this data.");
        }
        return get(root, data);
    }

    /**
     * helper function for contains(data)
     *
     * @param data the data to search for in the tree.
     * @param cur the current node being inspected
     * @return whether or not the parameter is contained within the tree.
     */
    private boolean contains(BSTNode<T> cur, T data) {
        if (cur.getData().equals(data)) {
            return true;
        }
        if (cur.getLeft() != null && cur.getData().compareTo(data) > 0) {
            return contains(cur.getLeft(), data);
        } else if (cur.getRight() != null) {
            return contains(cur.getRight(), data);
        }
        return false;
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input is null. Can't insert");
        }
        if (size == 0) {
            return false;
        }
        return contains(root, data);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Helper function for preorder()
     *
     * @param cur current node being inspected
     * @param preList list containing preorder of nodes up until now.
     */
    private void preorder(BSTNode<T> cur, List<T> preList) {
        if (cur == null) {
            return;
        }
        preList.add(cur.getData());
        preorder(cur.getLeft(), preList);
        preorder(cur.getRight(), preList);
    }

    @Override
    public List<T> preorder() {
        List<T> ans = new ArrayList<T>();
        preorder(root, ans);
        return ans;
    }

    /**
     * Helper function for postorder
     *
     * @param cur current node being inspected
     * @param ans current list containing postorder list of nodes until now.
     */
    private void postorder(BSTNode<T> cur, List<T> ans) {
        if (cur == null) {
            return;
        }
        postorder(cur.getLeft(), ans);
        postorder(cur.getRight(), ans);
        ans.add(cur.getData());
    }

    @Override
    public List<T> postorder() {
        List<T> ans = new ArrayList<T>();
        postorder(root, ans);
        return ans;
    }

    /**
     * Helper function for inorder
     *
     * @param cur current node being inspected
     * @param ans the current list containing inorder list of nodes up to now.
     */
    private void inorder(BSTNode<T> cur, List<T> ans) {
        if (cur == null) {
            return;
        }
        inorder(cur.getLeft(), ans);
        ans.add(cur.getData());
        inorder(cur.getRight(), ans);
    }

    @Override
    public List<T> inorder() {
        List<T> ans = new ArrayList<T>();
        inorder(root, ans);
        return ans;
    }

    @Override
    public List<T> levelorder() {
        List<T> list = new ArrayList<T>();
        if (size == 0) {
            return list;
        }
        Queue<BSTNode<T>> q = new LinkedList<BSTNode<T>>();
        q.add(root);
        while (q.size() != 0) {
            BSTNode<T> cur = q.remove();

            if (cur.getLeft() != null) {
                q.add(cur.getLeft());
            }
            if (cur.getRight() != null) {
                q.add(cur.getRight());
            }

            list.add(cur.getData());

        }
        return list;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Helper function for height()
     *
     * @param cur the current node inspected
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int height(BSTNode<T> cur) {
        if (cur == null) {
            return 0;
        }
        return 1 + Math.max(height(cur.getLeft()), height(cur.getRight()));
    }

    @Override
    public int height() {
        return height(root) - 1;
    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}