#include <bits/stdc++.h>
using namespace std;

#define MOD 1000000007
#define MAX 100001

int maxim;
int N;
vector<int> BIT0(MAX), BIT1(MAX);
int arr[MAX];
int dp[MAX][2];

void update(int x, int val, vector<int> &bits) {
    for(; x <= maxim; x += x & -x)
        bits[x] = (bits[x] + val) % MOD;
}

int query(int x, vector<int> &bits) {
    int sum = 0;
    for(; x > 0; x -= x & -x)
        sum = (sum + bits[x]) % MOD;
    return sum;
}

int main() {

    cin >> N;
    for (int i = 1; i <= N; i++) {
        cin >> arr[i];
        maxim = max(maxim, arr[i]);
    }

    int ans = 0;
    for (int i = 1; i <= N; i++) {
        update(arr[i], 1, BIT1);
        update(arr[i], 1, BIT0);

        dp[i][0] = (query(maxim, BIT1) - query(arr[i], BIT1)) % MOD;
        dp[i][1] = query(arr[i] - 1, BIT0) % MOD;

        update(arr[i], dp[i][0], BIT0);
        update(arr[i], dp[i][1], BIT1);

        dp[i][0] = (dp[i][0] + MOD) % MOD;
        dp[i][1] = (dp[i][1] + MOD) % MOD;

        ans = (ans + dp[i][0]) % MOD;
        ans = (ans + dp[i][1]) % MOD;
    }

    ans = (ans + MOD) % MOD;
    cout << ans << "\n";

    return 0;
}