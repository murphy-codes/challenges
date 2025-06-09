// Source: https://leetcode.com/problems/k-th-smallest-in-lexicographical-order/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-08
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 40.74 MB Beats 19.07%

/****************************************
* 
* Given two integers `n` and `k`, return the `k^th` lexicographically
* _ smallest integer in the range `[1, n]`.
*
* Example 1:
* Input: n = 13, k = 2
* Output: 10
* Explanation: The lexicographical order is [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9], so the second smallest number is 10.
*
* Example 2:
* Input: n = 1, k = 1
* Output: 1
*
* Constraints:
* • 1 <= k <= n <= 10^9
* 
****************************************/

class Solution {
    // Traverse the implicit 10-ary prefix tree without enumerating every node.
    // At each prefix, count how many numbers lie in its subtree.
    //   • If the k-th number is beyond that subtree, skip it (move to sibling).
    //   • Otherwise, descend into the subtree (first child) until k == 0.
    // Time  : O(log n) — ≈ depth × 10 prefix counts (worst-case 400 for n≤1e9).
    // Memory: O(1) extra — only a few longs, independent of n.

    public int findKthNumber(int n, int k) {
        long prefix = 1;          // current node in the lexicographic tree
        k--;                      // we are already at rank-1 (the “1”)

        while (k > 0) {
            long steps = countSteps(n, prefix, prefix + 1);
            if (steps <= k) {     // k-th number is not in this subtree
                prefix++;         //   => go to next sibling
                k -= steps;
            } else {              // k-th number is inside this subtree
                prefix *= 10;     //   => go down one level (first child)
                k--;              // we move onto the prefix itself
            }
        }
        return (int) prefix;
    }

    /** Count how many numbers in [1,n] have prefix in [lo, hi). */
    private long countSteps(int n, long lo, long hi) {
        long steps = 0;
        while (lo <= n) {
            steps += Math.min(n + 1L, hi) - lo;
            lo *= 10;
            hi *= 10;
        }
        return steps;
    }
}
