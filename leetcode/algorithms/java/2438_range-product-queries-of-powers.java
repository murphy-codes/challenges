// Source: https://leetcode.com/problems/range-product-queries-of-powers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-11
// At the time of submission:
//   Runtime 3 ms Beats 100.00%
//   Memory 89.55 MB Beats 44.09%

/****************************************
* 
* Given a positive integer `n`, there exists a 0-indexed array called `powers`,
* _ composed of the minimum number of powers of `2` that sum to `n`. The array is
* _ sorted in non-decreasing order, and there is only one way to form the array.
* You are also given a 0-indexed 2D integer array `queries`, where
* _ `queries[i] = [left_i, right_i]`. Each `queries[i]` represents a query where
* _ you have to find the product of all `powers[j]` with `left_i <= j <= right_i`.
* Return an array `answers`, equal in length to `queries`, where `answers[i]` is
* _ the answer to the `i^th` query. Since the answer to the `i^th` query may be
* _ too large, each `answers[i]` should be returned modulo `10^9 + 7`.
*
* Example 1:
* Input: n = 15, queries = [[0,1],[2,2],[0,3]]
* Output: [2,4,64]
* Explanation:
* For n = 15, powers = [1,2,4,8]. It can be shown that powers cannot be a smaller size.
* Answer to 1st query: powers[0] * powers[1] = 1 * 2 = 2.
* Answer to 2nd query: powers[2] = 4.
* Answer to 3rd query: powers[0] * powers[1] * powers[2] * powers[3] = 1 * 2 * 4 * 8 = 64.
* Each answer modulo 109 + 7 yields the same answer, so [2,4,64] is returned.
*
* Example 2:
* Input: n = 2, queries = [[0,0]]
* Output: [2]
* Explanation:
* For n = 2, powers = [2].
* The answer to the only query is powers[0] = 2. The answer modulo 109 + 7 is the same, so [2] is returned.
*
* Constraints:
* • 1 <= n <= 10^9
* • 1 <= queries.length <= 10^5
* • 0 <= start_ <= end_i < powers.length
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {
    // This solution builds the powers[] array from the binary representation of n,
    // storing powers of 2 in descending order. Then, it precomputes the product of
    // every possible subarray of powers[] in a 2D table, allowing each query to be
    // answered in O(1) time. This trades memory for speed, using O(k^2) space where
    // k = number of set bits in n. Time complexity: O(k^2 + q), Space complexity: O(k^2).
    public int[] productQueries(int n, int[][] queries) {
        final int MOD = (int) 1e9 + 7;
        
        // Find the highest power of 2 <= n
        int largestPower = 1;
        while (largestPower <= n) largestPower <<= 1;
        largestPower >>= 1;

        // Build the list of powers of 2 that sum to n (in descending order)
        List<Integer> powers = new ArrayList<>();
        int remaining = n;
        while (remaining > 0) {
            if (largestPower <= remaining) {
                powers.add(largestPower);
                remaining -= largestPower;
            }
            largestPower >>= 1;
        }

        int size = powers.size();
        
        // Precompute all range products into a 2D array
        int[][] rangeProducts = new int[size][size];
        for (int start = 0; start < size; start++) {
            rangeProducts[start][start] = powers.get(size - 1 - start);
            for (int end = start + 1; end < size; end++) {
                rangeProducts[start][end] = (int) (
                    (1L * rangeProducts[start][end - 1] * powers.get(size - 1 - end)) % MOD
                );
            }
        }

        // Answer queries in O(1) using the precomputed range products
        int[] results = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            results[i] = rangeProducts[queries[i][0]][queries[i][1]];
        }

        return results;
    }
}
