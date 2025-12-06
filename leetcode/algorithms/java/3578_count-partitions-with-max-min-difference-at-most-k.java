// Source: https://leetcode.com/problems/count-partitions-with-max-min-difference-at-most-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-05
// At the time of submission:
//   Runtime 38 ms Beats 95.06%
//   Memory 68.72 MB Beats 65.43%

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

// import java.util.ArrayDeque;
// import java.util.Deque;

class Solution {
    // Use two monotonic deques to maintain the min and max in a sliding window.
    // When max–min ≤ k, dp[j+1] equals the sum of all dp[i] for i in the window.
    // A running accumulator stores this sum and subtracts dp[left] when the
    // window becomes invalid, keeping all updates O(1). Overall time is O(n)
    // with O(n) space for dp and O(n) total deque operations.

    public int countPartitions(int[] nums, int k) {
        int n = nums.length;
        int MOD = 1_000_000_007;

        // dp[i] = number of ways to partition nums[0 .. i-1]
        int[] dp = new int[n + 1];
        dp[0] = 1;

        // acc = running sum of dp[i .. j], updated as window moves
        int acc = 1;

        Deque<Integer> minDeque = new ArrayDeque<>();
        Deque<Integer> maxDeque = new ArrayDeque<>();

        int left = 0;

        for (int right = 0; right < n; right++) {

            // Maintain decreasing maxDeque
            while (!maxDeque.isEmpty() && nums[right] > nums[maxDeque.peekLast()])
                maxDeque.pollLast();
            maxDeque.addLast(right);

            // Maintain increasing minDeque
            while (!minDeque.isEmpty() && nums[right] < nums[minDeque.peekLast()])
                minDeque.pollLast();
            minDeque.addLast(right);

            // Shrink window while max-min > k
            while (nums[maxDeque.peekFirst()] - nums[minDeque.peekFirst()] > k) {

                // Remove dp[left] from running sum
                acc = (acc - dp[left] + MOD) % MOD;
                left++;

                // Remove indices that fell out of window
                if (!minDeque.isEmpty() && minDeque.peekFirst() < left)
                    minDeque.pollFirst();
                if (!maxDeque.isEmpty() && maxDeque.peekFirst() < left)
                    maxDeque.pollFirst();
            }

            // dp[right+1] = total partitions ending at index right
            dp[right + 1] = acc;

            // Add dp[right+1] to accumulator for next iteration
            acc = (acc + dp[right + 1]) % MOD;
        }

        return dp[n];  // dp[n] = partitions using entire array
    }
}
