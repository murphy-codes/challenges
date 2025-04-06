// Source: https://leetcode.com/problems/largest-divisible-subset/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-06
// At the time of submission:
//   Runtime 13 ms Beats 99.64%
//   Memory 43.21 MB Beats 43.16%

/****************************************
* 
* Given a set of distinct positive integers `nums`, return the largest subset `answer` 
* _ such that every pair `(answer[i], answer[j])` of elements in this subset satisfies:
* • `answer[i] % answer[j] == 0`, or
* • `answer[j] % answer[i] == 0`
* If there are multiple solutions, return any of them.
*
* Example 1:
* Input: nums = [1,2,3]
* Output: [1,2]
* Explanation: [1,3] is also accepted.
*
* Example 2:
* Input: nums = [1,2,4,8]
* Output: [1,2,4,8]
*
* Constraints:
* • 1 <= nums.length <= 1000
* • 1 <= nums[i] <= 2 * 10^9
* • All the integers in `nums` are unique.
* 
****************************************/

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class Solution {
    // This solution sorts the array and uses dynamic programming to track the
    // longest divisible subset at each index. A backtracking array helps reconstruct
    // the subset. Sorting ensures divisibility only needs to be checked one way.
    // Time complexity is O(n²) due to the nested loop, and space is O(n) for DP storage.
    // This approach efficiently finds the largest subset while maintaining order.
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums); // Step 1: Sort the array

        int[] dp = new int[n];      // dp[i] = length of largest subset ending at i
        int[] prev = new int[n];    // prev[i] = previous index in subset
        Arrays.fill(dp, 1);
        Arrays.fill(prev, -1);

        int maxSize = 0, maxIndex = -1;

        // Step 2: Build DP table
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            if (dp[i] > maxSize) {
                maxSize = dp[i];
                maxIndex = i;
            }
        }

        // Step 3: Reconstruct the largest divisible subset
        List<Integer> result = new ArrayList<>();
        while (maxIndex != -1) {
            result.add(nums[maxIndex]);
            maxIndex = prev[maxIndex];
        }

        return result;
    }
}
