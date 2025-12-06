// Source: https://leetcode.com/problems/count-partitions-with-max-min-difference-at-most-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-05
// At the time of submission:
//   Runtime 44 ms Beats 34.57%
//   Memory 86.91 MB Beats 6.17%

/****************************************
* 
* You are given an integer array `nums` and an integer `k`. Your task is to
* _ partition `nums` into one or more non-empty contiguous segments such that
* _ in each segment, the difference between its maximum and minimum elements
* _ is at most `k`.
* Return the total number of ways to partition `nums` under this condition.
* Since the answer may be too large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: nums = [9,4,1,3,7], k = 4
* Output: 6
* Explanation:
* There are 6 valid partitions where the difference between the maximum and minimum elements in each segment is at most k = 4:
* [[9], [4], [1], [3], [7]]
* [[9], [4], [1], [3, 7]]
* [[9], [4], [1, 3], [7]]
* [[9], [4, 1], [3], [7]]
* [[9], [4, 1], [3, 7]]
* [[9], [4, 1, 3], [7]]
*
* Example 2:
* Input: nums = [3,3,4], k = 0
* Output: 2
* Explanation:
* There are 2 valid partitions that satisfy the given conditions:
* [[3], [3], [4]]
* [[3, 3], [4]]
*
* Constraints:
* • `2 <= nums.length <= 5 * 10^4`
* • `1 <= nums[i] <= 10^9`
* • `0 <= k <= 10^9`
* 
****************************************/

import java.util.ArrayDeque;

class Solution {
    // We use a sliding window with monotonic min/max deques to track when a
    // segment has max-min ≤ k. For each index i, dp[i] counts all partitions
    // whose last segment starts between window limits. Prefix sums allow O(1)
    // range DP queries. The window only moves forward, giving O(n) time and
    // O(n) space for dp/prefix plus O(n) total deque operations.
    public int countPartitions(int[] nums, int k) {
        final int MOD = 1_000_000_007;
        int n = nums.length;

        long[] dp = new long[n];
        long[] prefix = new long[n + 1]; 
        // prefix[i] = (dp[0] + ... + dp[i-1])

        ArrayDeque<Integer> minD = new ArrayDeque<>();
        ArrayDeque<Integer> maxD = new ArrayDeque<>();

        int start = 0;

        for (int i = 0; i < n; i++) {

            // Insert nums[i] into min deque
            while (!minD.isEmpty() && nums[minD.peekLast()] > nums[i]) {
                minD.pollLast();
            }
            minD.addLast(i);

            // Insert into max deque
            while (!maxD.isEmpty() && nums[maxD.peekLast()] < nums[i]) {
                maxD.pollLast();
            }
            maxD.addLast(i);

            // Shrink window until valid
            while (!minD.isEmpty() && !maxD.isEmpty() &&
                   (nums[maxD.peekFirst()] - nums[minD.peekFirst()] > k)) {

                if (minD.peekFirst() == start) minD.pollFirst();
                if (maxD.peekFirst() == start) maxD.pollFirst();
                start++;
            }

            // dp[i] = number of valid partitions ending at i
            if (start == 0) {
                dp[i] = prefix[i] + 1;   // entire prefix counts + single segment
            } else {
                dp[i] = (prefix[i] - prefix[start - 1] + MOD) % MOD;
            }

            prefix[i + 1] = (prefix[i] + dp[i]) % MOD;
        }

        return (int)(dp[n - 1] % MOD);
    }
}
