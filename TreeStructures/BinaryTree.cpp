
struct Node {
    int key_value;
    Node *left;
    Node *right;
    Node (int key_value, Node *left, Node *right) :
        key_value(key_value),
        left(left),
        right(right) {}
};

class BTree {

private:

    void insert(int key, Node *leaf);
    void destroy_tree(Node *leaf);
    Node *search(int key, Node *leaf);

public:
    BTree();
    ~BTree();

    void insert(int key);
    Node *search(int key);
    void destroy_tree();

    Node *root;
};

BTree::BTree() {
    root = nullptr;
}

BTree::~BTree() {
    destroy_tree();
}

void BTree::destroy_tree(Node *leaf) {
    if (leaf != nullptr) {
        destroy_tree(leaf->left);
        destroy_tree(leaf->right);
        delete leaf;
    }
}

void BTree::insert(int key, Node *leaf) {
    if (key < leaf->key_value) {
        if (leaf->left != nullptr) {
            insert(key, leaf->left);
        } else {
            leaf->left = new Node(key, nullptr, nullptr);
        }
    } else if (key >= leaf->key_value) {
        if (leaf->right != nullptr) {
            insert(key, leaf->right);
        } else {
            leaf->right = new Node(key, nullptr, nullptr);
        }
    }
}

Node *BTree::search(int key, Node *leaf) {
    if (leaf != nullptr) {
        if (key == leaf->key_value)
            return leaf;
        if (key < leaf->key_value)
            return search(key, leaf->left);
        else
            return search(key, leaf->right);
    } else {
        return nullptr;
    }
}

void BTree::insert(int key) {
    if (root != nullptr) {
        insert(key, root);
    } else {
        root = new Node(key, nullptr, nullptr);
    }
}

Node *BTree::search(int key) {
    return search(key, root);
}

void BTree::destroy_tree() {
    destroy_tree(root);
}