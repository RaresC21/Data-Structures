/*
Segment Tree. 
Build. Query. Update.

Given an array of integers, answers queries to find sum of entries in a given range in O(LogN) time. 
Builds the tree in O(NLogN) time. Also allows for updates. i.e. adding a particular value to a given entry. 
This is also done in O(LongN) time.

Lazy Implementation of Segment Tree.
Allows for updating an entire range of values, not a single element. 
This only makes the minimum amount of operations needed, and traverses down the tree only when we have a query 
explicitly demanding us to do so.
*/


#define MAX 100 // This can change depending on input size

class SegTree {

private:

	std::vector <int>& A;
	int tree[2 * MAX], lazy[2 * MAX];

public:

	SegTree(std::vector<int>& a) : A(a) {
		memset(tree, 0, sizeof(tree));
		memset(lazy, 0, sizeof(lazy));
	}

	void build(int node, int start, int end);
	void update(int node, int start, int end, int indx, int val);
	int query(int node, int start, int end, int l, int r);

	void lazy_updateRange(int node, int start, int end, int l, int r, int val);
	int lazy_queryRange(int node, int start, int end, int l, int r);
};

void SegTree::build(int node, int start, int end)
{
	if (start == end) { // leaf node
		tree[node] = A[start];
		return;
	}

	int mid = (start + end) / 2;
	build(2 * node, start, mid);
	build(2 * node + 1, mid + 1, end);

	tree[node] = tree[2 * node] + tree[2 * node + 1];
}

void SegTree::update(int node, int start, int end, int indx, int val)
{
	if (start == end) {
		A[indx] += val;
		tree[node] += val;
		return;
	}

	int mid = (start + end) / 2;
	if (start <= mid && indx <= mid)
		update(2 * node, start, mid, indx, val);
	else
		update(2 * node + 1, mid + 1, end, indx, val);

	tree[node] = tree[node * 2] + tree[node * 2 + 1];
}

int SegTree::query(int node, int start, int end, int l, int r)
{
	if (r < start || l > end) return 0;

	if (l <= start && r >= end) // completely within range.
		return tree[node];

	//partially in range, partially out.
	int mid = (start + end) / 2;
	int p1 = query(node * 2, start, mid, l, r);
	int p2 = query(node * 2 + 1, mid + 1, end, l, r);

	return p1 + p2;
}

void SegTree::lazy_updateRange(int node, int start, int end, int l, int r, int val)
{
	if (lazy[node] != 0) {
		tree[node] += (end - start + 1) * lazy[node];
		if (start != end) {
			lazy[node * 2] += lazy[node];
			lazy[node * 2 + 1] += lazy[node];
		}
		lazy[node] = 0;
	}

	if (start > end || start > r || end < l)
		return;

	if (start >= l && end <= r)
	{
		tree[node] += (end - start + 1) * val;
		if (start != end) {
			lazy[node * 2] += val;
			lazy[node * 2 + 1] += val;
		}
		return;
	}

	int mid = (start + end) / 2;
	lazy_updateRange(node * 2, start, mid, l, r, val);
	lazy_updateRange(node * 2 + 1, mid + 1, end, l, r, val);

	tree[node] = tree[node * 2] + tree[node * 2 + 1];
}

int SegTree::lazy_queryRange(int node, int start, int end, int l, int r)
{
	if (start > end || start > r || end < l)
		return 0;

	if (lazy[node] != 0) {
		// this node needs to be updated.

		tree[node] += (end - start + 1) * lazy[node];
		if (start != end) {
			lazy[node * 2] += lazy[node];
			lazy[node * 2 + 1] += lazy[node];
		}
		lazy[node] = 0;
	}

	if (start >= l && end <= r)
		return tree[node];

	int mid = (start + end) / 2;
	int p1 = lazy_queryRange(2 * node, start, mid, l, r);
	int p2 = lazy_queryRange(2 * node + 1, mid + 1, end, l, r);

	return p1 + p2;
}