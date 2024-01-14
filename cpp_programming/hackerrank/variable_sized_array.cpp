#include <algorithm>
#include <cmath>
#include <cstdio>
#include <iostream>
#include <vector>
using namespace std;

int main() {
  /* Enter your code here. Read input from STDIN. Print output to STDOUT */
  int n;
  int q;
  cin >> n;
  cin >> q;
  vector<int> arr[n];

  for (int i = 0; i < n; i++) {
    int k;
    cin >> k;
    vector<int> v;

    for (int j = 0; j < k; j++) {
      int x;
      cin >> x;
      v.push_back(x);
      /* code */
    }
    arr[i] = v;
  }

  for (int i = 0; i < q; i++) {
    int x, y;
    cin >> x >> y;
    cout << arr[x].at(y) << endl;
  }

  // cout << n << " " << q << endl;

  // for (int i = 0; i < arr->size(); i++) {
  //   vector<int> v = arr[i];
  //   for (int& x : v) {
  //     cout << x << " ";
  //   }
  //   cout << endl;
  // }

  return 0;
}