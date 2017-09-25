#include "bits/stdc++.h"
using namespace std;
#define FOR(i,m,n) for(int i = (m); i < (n); i++)

const int MAX = 100005;
int two[MAX];
int maxim[MAX][30];
vector<int> a;

void sparse_table() {
    two[1] = 0;
    FOR(i,2,2*N) {
        two[i] = two[i / 2] + 1;
    }
    FOR(i,0,2*N) {
        maxim[i][0] = a[i];
    }
    FOR(i,1,24) {
        for (int j = 0; j + (1 << i) < 2*N; j++) {
            maxim[j][i] = max(maxim[j][i - 1], maxim[j + (1 << (i - 1))][i - 1]);
        }
    }
}

int query(int a, int b) {
    int power_ = two[b - a + 1];
    return max(maxim[a][power_], maxim[b - (1 << power_) + 1][power_]);
}

int main() {
    // initialize vector a
    sparse_table();
    return 0;
}