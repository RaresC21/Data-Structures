#include <vector>
#include <math.h>
#include <iostream>

template<class T>
class RangeQuery {
public:
  RangeQuery(const std::vector<T>& A, T (*merge)(T, T), T zero) :
                                  merge(merge),
                                  ZERO(zero) {
    n = next_power(A.size());
    log_ = std::vector<int>(n + 1);
    log_table(n);

    this->A[0] = std::vector<T>(n, ZERO);
    for (int i = 0; i < A.size(); i++) this->A[0][i] = A[i];
    this->A[1] = this->A[0];
    std::reverse(this->A[1].begin(), this->A[1].end());

    column[0] = column[1] = std::vector<std::vector<T>>(n, std::vector<T>());
    paths[0] = paths[1] = std::vector<std::vector<T>>(
                                            n,
                                            std::vector<T>(log_[n] + 1, ZERO));
    path_length[0] = path_length[1] = std::vector<std::vector<int>>(
                                            n,
                                            std::vector<int>(log_[n] + 1, 0));
    create_tree();
    compute_paths();
  }

  T query(int L, int R);  // O(1)

private:
  int n;
  T ZERO;
  std::vector<T> A[2];
  std::vector<int> log_;
  std::vector<std::vector<T>> column[2];
  std::vector<std::vector<T>> paths[2];
  std::vector<std::vector<int>> path_length[2];
  T (*merge)(T, T);

  void create_tree(); // O(n)
  void compute_paths(); // O(n log n)

  void log_table(int max); // O(n)
  int next_power(int n); // O(log n)
};

template<class T>
T RangeQuery<T>::query(int L, int R) {
  int len = R - L + 1;
  int power = log_[len];
  T ans = ZERO;

  if (power >= column[0][L].size() - 1) {
    if (path_length[0][L][power] <= len) {
      ans = paths[0][L][power];
      L += path_length[0][L][power];
    } else {
      ans = paths[0][L][power - 1];
      L += path_length[0][L][power - 1];
    }
  }

  R = n - R - 1;
  L = n - L - 1;
  len = L - R + 1;

  if (len != 0) {
    power = log_[len];
    if (power >= column[1][R].size() - 1) {
      if (path_length[1][R][power] <= len) {
        ans = merge(ans, paths[1][R][power]);
      } else {
        ans = merge(ans, paths[1][R][power - 1]);
      }
    }
  }

  return ans;
}

template<class T>
void RangeQuery<T>::create_tree() {
  for (int t = 0; t <= 1; t++) {
    for (int len = 1; len <= n; len *= 2) {
      for (int c = 0; c < n; c += len) {
        if (len == 1) {
          column[t][c].push_back(A[t][c]);
        } else {
          T left_child = column[t][c].back();
          T right_child = column[t][c + len/2].back();
          column[t][c].push_back(merge(left_child, right_child));
        }
      }
    }
  }
}

template<class T>
void RangeQuery<T>::compute_paths() {
  for (int t = 0; t <= 1; t++) {
    for (int c = 0; c < n; c++) {
      T cur_val = ZERO;
      int cur_length = 0;
      int last_len = -1;

      int pos = c;
      do {
        cur_val = merge(cur_val, column[t][pos].back());

        int len = column[t][pos].size() - 1;
        int i = last_len + 1;
        cur_length += (1 << len);

        if (last_len != -1) {
          while (i != len) {
            paths[t][c][i] = paths[t][c][last_len];
            path_length[t][c][i] = path_length[t][c][last_len];
            i++;
          }
        }
        paths[t][c][len] = cur_val;
        path_length[t][c][len] = cur_length;
        last_len = len;

        pos += (1 << len);
      } while (pos < n);

      int i = last_len + 1;
      if (last_len != -1) {
        while (i < paths[t][c].size()) {
          paths[t][c][i] = paths[t][c][last_len];
          path_length[t][c][i] = path_length[t][c][last_len];
          i++;
        }
      }
    }
  }
}

template<class T>
void RangeQuery<T>::log_table(int max) {
  int cur = 2, val = 0;
  log_[1] = 0;
  for (int i = 2; i <= max; i++) {
    if (i == cur) {
      cur *= 2;
      val++;
    }
    log_[i] = val;
  }
}

template<class T>
int RangeQuery<T>::next_power(int n) {
  int nn = 1;
  while (nn < n) nn *= 2;
  return nn;
}
