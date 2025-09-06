// Source: https://leetcode.com/problems/minimum-operations-to-make-array-elements-zero/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-06
// At the time of submission:
//   Runtime 14 ms Beats 91.67%
//   Memory 100.52 MB Beats 94.44%

/****************************************
* 
* You are given a 2D array `queries`, where `queries[i]` is of the form `[l, r]`.
* _ Each `queries[i]` defines an array of integers `nums` consisting of elements
* _ ranging from `l to r`, both inclusive.
* In one operation, you can:
* • Select two integers `a` and `b` from the array.
* • Replace them with `floor(a / 4)` and `floor(b / 4)`.
* Your task is to determine the minimum number of operations required to reduce
* _ all elements of the array to zero for each query. Return the sum of the
* _ results for all queries.
*
* Example 1:
* Input: queries = [[1,2],[2,4]]
* Output: 3
* Explanation:
* For queries[0]:
* The initial array is nums = [1, 2].
* In the first operation, select nums[0] and nums[1]. The array becomes [0, 0].
* The minimum number of operations required is 1.
* For queries[1]:
* The initial array is nums = [2, 3, 4].
* In the first operation, select nums[0] and nums[2]. The array becomes [0, 3, 1].
* In the second operation, select nums[1] and nums[2]. The array becomes [0, 0, 0].
* The minimum number of operations required is 2.
* The output is 1 + 2 = 3.
*
* Example 2:
* Input: queries = [[2,6]]
* Output: 4
* Explanation:
* For queries[0]:
* The initial array is nums = [2, 3, 4, 5, 6].
* In the first operation, select nums[0] and nums[3]. The array becomes [0, 3, 4, 1, 6].
* In the second operation, select nums[2] and nums[4]. The array becomes [0, 3, 1, 1, 1].
* In the third operation, select nums[1] and nums[2]. The array becomes [0, 0, 0, 1, 1].
* In the fourth operation, select nums[3] and nums[4]. The array becomes [0, 0, 0, 0, 0].
* The minimum number of operations required is 4.
* The output is 4.
*
* Constraints:
* • 1 <= queries.length <= 10^5
* • queries[i].length == 2
* • queries[i] == [l, r]
* • 1 <= l < r <= 10^9
* 
****************************************/

class Solution {
    // Precompute powers of 4 since any number x in [4^k, 4^(k+1)-1]
    // always requires exactly (k+1) divisions by 4 to reach zero.
    // For each query [l, r], count how many numbers fall into each
    // such range, multiply by (k+1), and sum. Each operation reduces
    // two numbers, so the answer is ceil(totalOps / 2).
    // Time complexity: O(Q * logN), where Q = queries.length,
    // N = max(r). Space complexity: O(logN).
    public long minOperations(int[][] queries) {
        // Precompute powers of 4 up to > 1e9
        long[] pow4 = new long[16]; // since 4^15 > 1e9
        pow4[0] = 1;
        for (int i = 1; i < pow4.length; i++) {
            pow4[i] = pow4[i - 1] * 4;
        }

        long total = 0;
        for (int[] q : queries) {
            int l = q[0], r = q[1];
            long ops = 0;

            // Traverse blocks of [4^k, 4^(k+1)-1]
            for (int k = 0; k < pow4.length - 1; k++) {
                long left = Math.max(l, pow4[k]);
                long right = Math.min(r, pow4[k + 1] - 1);

                if (left <= right) {
                    long count = right - left + 1;
                    ops += count * (k + 1); // each needs k+1 operations
                }
            }

            // Each operation reduces 2 numbers → ceil division
            total += (ops + 1) / 2;
        }
        return total;
    }
}
