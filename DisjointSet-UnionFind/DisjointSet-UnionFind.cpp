#include <bits/stdc++.h>
using namespace std;

#define MAX 1001

class DSU {

public:

    int parent[MAX];
    int rank_[MAX];
    int set_size[MAX];

    int find_set (int a) {
        if (a != parent[a])
            parent[a] = find_set(parent[a]);
        return parent[a];
    }

    void make_set(int a) {
        parent[a] = a;
        set_size[a] = 1;
        rank_[a] = 0;
    }

    void merge_set (int a, int b) {
        int root_a = find_set (a);
        int root_b = find_set (b);

        if (root_a == root_b) return;

        if (rank_[root_a] > rank_[root_b]) {
            parent[root_b] = root_a;
            set_size[root_a] += set_size[root_b];
        } else {
            parent[root_a] = root_b;
            set_size[root_b] += set_size[root_a];
        }
        if (rank_[root_a] == rank_[root_b])
            rank_[root_b]++;
    }

    int get_set_size (int a) {
        int root = find_set(a);
        return set_size[root];
    }
};

// ------------ END OF DATA STRUCTURE -------------


// example problem
int main() {

    /*
    You are given number of nodes, and number of edges,
    of a undirected connected graph. After adding each edge,
    print the size of all the connected components (in increasing order)
    */

    DSU dsu;

    int nodes_n, edges_n; cin >> nodes_n >> edges_n;
    for (int i = 1; i <= nodes_n; i++)
        dsu.make_set(i);

    for (int i = 0; i < edges_n; i++) {
        int a, b; cin >> a >> b;

        dsu.merge_set (a, b);

        vector<bool> met(edges_n + 1);
        vector<int> ans;
        for (int i = 1; i <= nodes_n; i++) {
            int root = dsu.find_set(i);

            if (met[root]) continue;
            met[root] = true;

            ans.push_back(dsu.set_size[root]);
        }

        sort(ans.begin(), ans.end());

        for (int i = 0; i < ans.size(); i++) {
            cout << ans[i] << " ";
        }

        cout << "\n";
    }

    return 0;
}