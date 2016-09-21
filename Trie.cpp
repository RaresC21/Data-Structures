/*
Allows for lookup of a particular string out of a large text for example. 
Runs in time proportional to the length of the string being looked up.
*/

class Trie {

	// Trie - Data structure of type Tree
#define ALPHAHBET_SIZE 26
public:

	struct node {
		bool is_end;
		struct node* child[ALPHAHBET_SIZE];
	}*front, *behind;

	void init(node* n) {
		n = new node();
		n->is_end = false;
	}

	void insert(string word, node* n) {
		node* current = n;
		for (unsigned int i = 0; i < word.length(); i++) {
			int letter = (int)word[i] - (int) 'a';
			if (current->child[letter] == NULL)
				current->child[letter] = new node();
			current = current->child[letter];
		}
		current->is_end = true;
	}

	bool search(string word, node* n) {
		node * current = n;
		for (unsigned int i = 0; i < word.length(); i++) {
			int letter = (int)word[i] - (int) 'a';
			if (current->child[letter] == NULL)
				return false;
			current = current->child[letter];
		}
		return current->is_end;
	}

	int main() {
		insert("hello", front);
		insert("hi", front);
		insert("what", front);
		if (search("hi", front))
			cout << "TRUE" << "\n";
		return 0;
	}
};
