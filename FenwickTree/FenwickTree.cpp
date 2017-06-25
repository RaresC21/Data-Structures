#include <bits/stdc++.h>
using namespace std;

class BitTree {
public:

    int N;
    int BIT[1000];

    BitTree(int N) : N(N) {
        memset(BIT, 0, sizeof(BIT));
    }

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
};

int main() {

    BitTree *bit = new BitTree(10000);

    return 0;
}