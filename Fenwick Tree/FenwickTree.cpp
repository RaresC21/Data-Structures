#include <bits/stdc++.h>
using namespace std;

int N;
int BIT[1000], a[1000];

void update(int x, int val) {
    for(; x <= N; x += x & -x)
        BIT[x] += val;
}

int query(int x) {
    int sum = 0;
    for(; x > 0; x -= x & -x)
        sum += BIT[x];
    return sum;
}

int main() {
    cin >> N;
    int i;
    for(i = 1; i <= N; i++) {
        cin >> a[i];
        update(i, a[i]);
    }
    cout << "sum of first 10 elements is " << query(10) << "\n";
    cout << "sum of all elements in range [2, 7] is " << query(7) - query(2 - 1) << "\n";

    return 0;
}