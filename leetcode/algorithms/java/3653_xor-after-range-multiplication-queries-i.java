// Source: https://leetcode.com/problems/xor-after-range-multiplication-queries-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-07
// At the time of submission:
//   Runtime 57 ms Beats 97.33%
//   Memory 47.77 MB Beats 78.67%

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
    // We simulate each query directly by iterating from l to r with step k,
    // multiplying the selected elements modulo 1e9+7. A long array is used
    // to prevent overflow during intermediate calculations. Given constraints
    // (n, q ≤ 1000), this O(q * n) approach is efficient. Finally, we compute
    // the XOR of all elements in the updated array.
    long MOD = (long)10e8+7;
    public int xorAfterQueries(int[] nums, int[][] q) {
        int n = nums.length;
        long arr[] =new long[n]; // long array to prevent overflow during mult
        for(int i=0; i<n ;i++){ // Copy nums into long array
            arr[i] = (long)nums[i];
        }
        for(int i=0;i<q.length;i++){
            for(int j=q[i][0];j<=q[i][1];j+=q[i][2]){
                arr[j]=(arr[j]*(long)q[i][3])%MOD;
            }
        }
        // Comp XOR of final vals
        long ans = 0;
        for(int i=0;i<n;i++){
            ans = ans^arr[i];
        }
        return (int)ans;
    }
}