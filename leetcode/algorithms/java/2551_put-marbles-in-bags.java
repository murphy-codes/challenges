// Source: https://leetcode.com/problems/put-marbles-in-bags/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-30
// At the time of submission:
//   Runtime 36 ms Beats 35.82%
//   Memory 62.88 MB Beats 61.19%

/****************************************
* 
* You have `k` bags. You are given a 0-indexed integer array `weights` where
* — `weights[i]` is the weight of the `i^th` marble. You are also given the integer `k`.
* Divide the marbles into the `k` bags according to the following rules:
* • No bag is empty.
* • If the `i^th` marble and `j^th` marble are in a bag, then all marbles with an
* — index between the `i^th` and `j^th` indices should also be in that same bag.
* • If a bag consists of all the marbles with an index from `i` to `j` inclusively,
* — then the cost of the bag is `weights[i]` + `weights[j]`.
* The score after distributing the marbles is the sum of the costs of all the `k` bags.
*
* Return the difference between the maximum and minimum scores among marble distributions.
*
* Example 1:
* Input: weights = [1,3,5,1], k = 2

* Output: 4
* Explanation:
* The distribution [1],[3,5,1] results in the minimal score of (1+1) + (3+1) = 6.
* The distribution [1,3],[5,1], results in the maximal score of (1+3) + (5+1) = 10.
* Thus, we return their difference 10 - 6 = 4.
*
* Example 2:
* Input: weights = [1, 3], k = 2
* Output: 0
* Explanation: The only distribution possible is [1],[3].
* Since both the maximal and minimal score are the same, we return 0.
*
* Constraints:
* • 1 <= k <= weights.length <= 10^5
* • 1 <= weights[i] <= 10^9
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Compute the cost differences between consecutive marbles.
    // Sort the pairwise sums and choose k-1 smallest for min score.
    // Choose k-1 largest for max score, then return max-min difference.
    // Sorting takes O(n log n), while summation takes O(k), so overall O(n log n).
    // Space complexity is O(n) due to the pairSum array.
    public long putMarbles(int[] weights, int k) {
        if (k == 1) return 0; // If k == 1, there's only one way to split.

        int n = weights.length;
        long[] pairSum = new long[n - 1];

        // Compute pair sums
        for (int i = 0; i < n - 1; i++) {
            pairSum[i] = weights[i] + weights[i + 1];
        }

        // Sort the pair sums
        Arrays.sort(pairSum);

        long minSum = 0, maxSum = 0;
        // Pick the smallest k-1 elements for min score
        for (int i = 0; i < k - 1; i++) {
            minSum += pairSum[i];
        }
        // Pick the largest k-1 elements for max score
        for (int i = 0; i < k - 1; i++) {
            maxSum += pairSum[n - 2 - i];
        }

        return maxSum - minSum;
    }
}
