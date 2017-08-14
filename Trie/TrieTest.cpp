#define CATCH_CONFIG_MAIN
#include <iostream>
#include "catch.hpp"

class Trie {
private:
    static const int ALPHABET_SIZE = 26;

    struct Node {
        bool end;
        struct Node* child[ALPHABET_SIZE];
    };

    Node* head;

    bool in_alphabet(const std::string& w) {
        for (char c : w)
            if (c < 'a' || c > 'z') return false;
        return true;
    }

public:

    Trie() {
        head = new Node();
        head->end = false;
    }

    void insert(const std::string& word) {
        if (!in_alphabet(word)) {
            return;
        }

        Node* cur = head;
        for (char c : word) {
            int letter = c - 'a';
            if (cur->child[letter] == NULL) {
                cur->child[letter] = new Node();
            }
            cur = cur->child[letter];
        }
        cur->end = true;
    }

    bool search(const std::string& word) {
        if (!in_alphabet(word)) {
            return false;
        }

        Node* cur = head;
        for (char c : word) {
            int letter = c - 'a';
            if (cur->child[letter] == NULL)
                return false;
            cur = cur->child[letter];
        }
        return cur->end;
    }
};

TEST_CASE("Trie") {
    Trie* trie = new Trie();

    REQUIRE(!trie->search(""));

    trie->insert("hello");
    trie->insert("hey");


    REQUIRE(!trie->search(""));
    REQUIRE(!trie->search("he"));
    REQUIRE(!trie->search("Hello"));
    REQUIRE(trie->search("hey"));
}