// Source: https://leetcode.com/problems/xor-after-range-multiplication-queries-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-07
// At the time of submission:
//   Runtime 59 ms Beats 88.00%
//   Memory 47.76 MB Beats 78.67%

/****************************************
* 
* You are given an integer array `nums` of length `n` and a 2D integer array
* _ `queries` of size `q`, where `queries[i] = [l_i, r_i, k_i, v_i]`.
* For each query, you must apply the following operations in order:
* • Set `idx = l_i`.
* • While `idx <= r_i`:
* _ • Update: `nums[idx] = (nums[idx] * v_i) % (10^9 + 7)`
* _ • Set `idx += k_i`.
* Return the bitwise XOR of all elements in `nums` after processing all queries.
*
* Example 1:
* Input: nums = [1,1,1], queries = [[0,2,1,4]]
* Output: 4
* Explanation:
* A single query [0, 2, 1, 4] multiplies every element from index 0 through index 2 by 4.
* The array changes from [1, 1, 1] to [4, 4, 4].
* The XOR of all elements is 4 ^ 4 ^ 4 = 4.
*
* Example 2:
* Input: nums = [2,3,1,5,4], queries = [[1,4,2,3],[0,2,1,2]]
* Output: 31
* Explanation:
* • The first query [1, 4, 2, 3] multiplies the elements at indices 1 and 3 by 3, transforming the array to [2, 9, 1, 15, 4].
* • The second query [0, 2, 1, 2] multiplies the elements at indices 0, 1, and 2 by 2, resulting in [4, 18, 2, 15, 4].
* • Finally, the XOR of all elements is 4 ^ 18 ^ 2 ^ 15 ^ 4 = 31.​​​​​​​​​​​​​​
*
* Constraints:
* • `1 <= n == nums.length <= 10^3`
* • `1 <= nums[i] <= 10^9`
* • `1 <= q == queries.length <= 10^3`
* • `queries[i] = [l_i, r_i, k_i, v_i]`
* • `0 <= l_i <= r_i < n`
* • `1 <= k_i <= n`
* • `1 <= v_i <= 10^5`
* 
****************************************/

class Solution {
    // For each query, we directly simulate the updates by iterating from l to r
    // with step size k and applying the multiplication modulo 1e9+7. Given the
    // constraints (n, q ≤ 1000), this O(q * n) approach is efficient enough.
    // After processing all queries, we compute the XOR of the final array.
    // The solution uses in-place updates and constant extra space.

    private static final int MOD = 1_000_000_007;

    public int xorAfterQueries(int[] nums, int[][] queries) {

        for (int[] q : queries) {
            int l = q[0];
            int r = q[1];
            int k = q[2];
            int v = q[3];

            for (int i = l; i <= r; i += k) {
                long updated = (long) nums[i] * v;
                nums[i] = (int) (updated % MOD);
            }
        }

        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }

        return xor;
    }
}