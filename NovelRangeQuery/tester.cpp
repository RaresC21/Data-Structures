#include <iostream>
#include <chrono>
#include <assert.h>
#include <iomanip>
#include "NovelRangeQuery.cpp"

std::vector<int> generate_input(int N) {
  srand(time(NULL));
  
  std::vector<int> v(N);
  for (int i = 0; i < N; i++) {
    v[i] = rand();
  }

  return v;
}

std::pair<double, RangeQuery<int>*> time_build(const std::vector<int>& v, int (merge)(int, int)) {
  auto begin = std::chrono::steady_clock::now();
  auto range = new RangeQuery<int>(v, merge, 0);
  auto end = std::chrono::steady_clock::now();
  return std::make_pair(std::chrono::duration_cast<std::chrono::milliseconds>(end - begin).count(),
			range);
}

double time_query(RangeQuery<int>* dataStruct, int N) {
  auto begin = std::chrono::steady_clock::now();
  for (int i = 0; i < N; i++) {
    for (int k = i; k < N; k++) {
      dataStruct->query(i, k);
    }
  }
  auto end = std::chrono::steady_clock::now();
  return std::chrono::duration_cast<std::chrono::nanoseconds>(end - begin).count();
}

bool is_correct() {
  int N = 100;
  std::vector<int> v(N);
  for (int i = 0; i < N; i++) {
    v[i] = rand();
  }

  RangeQuery<int>* range = new RangeQuery<int>(v, [](int a, int b) { return std::max(a, b); }, 0);
  for (int i = 0; i < N; i++) {
    for (int k = i; k < N; k++) {
      int mine = range->query(i,k);
      int real = 0;
      for (int a = i; a <= k; a++) real = std::max(real, v[a]);
      if (mine != real) {
	return false;
      }
    }
  }
  
  return true;
}

int main() {
  is_correct();
  
  std::vector<std::pair<int, double>> build, query;

  for (int n = 10; n <= 100000; n *= 2) {
    std::vector<int> v(n);
    for (int i = 0; i < n; i++) v[i] = rand();
    auto b = time_build(v, [](int a, int b) { return std::max(a, b); });
    build.push_back(std::make_pair(n, b.first));
    query.push_back(std::make_pair(n, time_query(b.second, n)));
  }

  for (int i = 0; i < build.size(); i++) {
    int n = build[i].first;
    std::cout << std::fixed << std::setprecision(9);
    std::cout << "Array Size: " << n << "; "
	      << "Build Time: " << build[i].second << "ms; "
	      << "Average Query Time: " << query[i].second / (1LL*n*n) << "ns\n";
  }
  
  return 0;
}
