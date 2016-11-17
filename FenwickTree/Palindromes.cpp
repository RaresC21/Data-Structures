/*
from hackerearth
two operations:
    1 L x - update Lth character of string to x
    2 L R - find if all the character of string from index L to R can be
            rearranged such that they can form a palindrome.
            If they can print "yes", else print "no".
*/

#include <bits/stdc++.h>
using namespace std;

#define MAX 1000
int N, Q;
int arr[MAX];
int BITree[26][MAX];

int getSum(int let, int index) {
    int sum = 0;
    index = index + 1;
    while (index > 0) {
        sum += BITree[let][index];
        index -= index & (-index);
    }
    return sum;
}

void updateBIT(int let, int index, int val) {
    index = index + 1;
    while (index <= N) {
       BITree[let][index] += val;
       index += index & (-index);
    }
}

int main() {


    cin >> N >> Q;
    string s; cin >> s;
    vector<int> str;
    for (int i = 0; i < N; i++) {
        str.push_back(s[i] - 'a');
        updateBIT(s[i] - 'a', i, 1);
    }

    for (int i = 0; i < Q; i++) {
        int type; cin >> type;
        if (type == 1) {
            int indx; char letter; cin >> indx >> letter;

            int afterLetter = letter - 'a';
            int beforeLetter = str[indx - 1];
            str[indx - 1] = afterLetter;

            updateBIT(afterLetter, indx - 1, 1);
            updateBIT(beforeLetter, indx - 1, -1);
        } else {
            int l, r; cin >> l >> r;
            l--; r--;
            int odd = 0;
            for (int i = 0; i < 26; i++) {
                int cur = getSum(i, r) - getSum(i, l - 1);
                if (cur % 2 != 0) odd++;
            }
            if (odd <= 1) {
                cout << "yes\n";
            } else {
                cout << "no\n";
            }
        }
    }

    return 0;
}