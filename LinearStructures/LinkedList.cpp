#include <bits/stdc++.h>

class LinkedList {

private:
    struct Node {
        int data;
        Node* next;
        Node(int data, Node* next):
            data(data),
            next(next) {}
    };

    Node* head;

public:
    LinkedList() :
        head(NULL) {}

    ~LinkedList() {
        Node* temp;
        for (; head; head = temp) {
            temp = head->next;
            delete head;
        }
    }

    void append(int item);
    void insert_at_head(int item);
    void delete_element(int item);
    void print() const;
};

void LinkedList::append(int item) {
    if (head == NULL) {
        head = new Node(item, NULL);
    } else {
        Node* last = head;
        while (last->next) {
            last = last->next;
        }
        last->next = new Node(item, NULL);
    }
}

void LinkedList::insert_at_head(int item) {
    head = new Node(item, head);
}

void LinkedList::delete_element(int item) {
    if (head == NULL) return;
    Node *cur = head, *prev = NULL;
    while (cur->next && cur->data != item) {
        prev = cur;
        cur = cur->next;
    }
    if (cur == NULL) return;
    if (cur == head) {
        head = head->next;
        return;
    }

    prev->next = cur->next;
    delete cur;
}

void LinkedList::print() const {
    if (head == NULL) return;

    Node* cur = head;
    while (cur->next) {
        std::cout << cur->data << " ";
        cur = cur->next;
    }
    std::cout << cur->data << "\n";
}

int main() {


    LinkedList linkedList;

    for (int i = 1; i <= 10; i++)
        linkedList.append(i);
    linkedList.print();

    linkedList.delete_element(1);
    linkedList.delete_element(5);
    linkedList.delete_element(10);

    linkedList.print();

    return 0;
}