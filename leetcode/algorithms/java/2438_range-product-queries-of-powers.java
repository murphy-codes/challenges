// Source: https://leetcode.com/problems/power-of-two/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-08
// At the time of submission:
//   Runtime 3 ms Beats 100.00%
//   Memory 89.55 MB Beats 44.09%

/****************************************
* 
* Given an integer `n`, return `true` if it is a power of two. Otherwise, return `false`.
* An integer `n` is a power of two, if there exists an integer `x` such that `n == 2^x`.
*
* Example 1:
* Input: n = 1
* Output: true
* Explanation: 2^0 = 1
*
* Example 2:
* Input: n = 16
* Output: true
* Explanation: 2^4 = 16
*
* Example 3:
* Input: n = 3
* Output: false
*
* Constraints:
* • -2^31 <= n <= 2^31 - 1
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
