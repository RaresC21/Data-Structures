#include <iostream>

class Stack {

private:
    struct Node {
        int data;
        Node* next;

        Node (int data, Node *next) :
            data(data),
            next(next);
    };

    Node* head;

public:

    Stack() :
        head(NULL) {}

    ~Stack() {
        Node* temp;

    }

    void push(int item);
    int pop();
    bool is_empty();
};

void Stack::push(int item) {
    head = new Node(item, head);
}

int pop() {
    int ans = head.data;
    head = head->next;
    return ans;
}

bool Stack::is_empty() {
    return head == NULL;
}