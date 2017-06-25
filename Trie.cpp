class Trie {

private:

#define ALPHAHBET_SIZE 27

    struct node {
        bool is_end;
        struct node* child[ALPHAHBET_SIZE];
    }*front;

    bool search(string word, node* n) {
        node * current = n;
        for (unsigned int i = 0; i < word.length(); i++) {
            if (word[i] == '*') word[i] = (char)((int)'z' + 1);
            int letter = (int)word[i] - (int) 'a';
            if (current->child[letter] == NULL)
                return false;
            current = current->child[letter];
        }
        return current->is_end;
    }

    void insert(string word, node* n) {
        node* current = n;
        for (unsigned int i = 0; i < word.length(); i++) {
            if (word[i] == '*') word[i] = (char)((int)'z' + 1);
            int letter = (int)word[i] - (int) 'a';
            if (current->child[letter] == NULL)
                current->child[letter] = new node();
            current = current->child[letter];
        }
        current->is_end = true;
    }



public:

    Trie() {
        front = new node();
        front->is_end = false;
    }

    void insert(string word) {
        insert(word, front);
    }

    bool search(string word) {
        return search(word, front);
    }
};

int main() {

	Trie *t = new Trie();

	return 0;
}