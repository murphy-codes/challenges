// Source: https://leetcode.com/problems/jump-game-ix/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-06
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 203.22 MB Beats 9.68%

/****************************************
* 
* You are given an integer array `nums`.
* From any index `i`, you can jump to another index `j` under
* _ the following rules:
* • Jump to index `j` where `j > i` is allowed only if `nums[j] < nums[i]`.
* • Jump to index `j` where `j < i` is allowed only if `nums[j] > nums[i]`.
* For each index `i`, find the maximum value in `nums` that can be reached
* _ by following any sequence of valid jumps starting at `i`.
* Return an array `ans` where `ans[i]` is the maximum value reachable
* _ starting from index `i`.
*
* Example 1:
* Input: nums = [2,1,3]
* Output: [2,2,3]
* Explanation:
* For `i = 0`: No jump increases the value.
* For `i = 1`: Jump to `j = 0` as `nums[j] = 2` is greater than `nums[I]`.
* For `i = 2`: Since `nums[2] = 3` is the maximum value in `nums`, no jump increases the value.
* Thus, `ans = [2, 2, 3]`.
*
* Example 2:
* Input: nums = [2,3,1]
* Output: [3,3,3]
* Explanation:
* For `i = 0`: Jump forward to `j = 2` as `nums[j] = 1` is less than `nums[i] = 2`, then from `i = 2` jump to `j = 1` as `nums[j] = 3` is greater than `nums[2]`.
* For `i = 1`: Since `nums[1] = 3` is the maximum value in `nums`, no jump increases the value.
* For `i = 2`: Jump to `j = 1` as `nums[j] = 3` is greater than `nums[2] = 1`.
* Thus, `ans = [3, 3, 3]`.
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `1 <= nums[i] <= 10^9`
* 
****************************************/

class Solution {
    // Build prefix maximums, then scan backward with suffix minimums.
    // If right side contains a smaller value, regions are connected.
    // Propagate the reachable maximum right-to-left across components.
    // Time: O(n), Space: O(n)
    public int[] maxValue(int[] nums) {

        int n = nums.length;

        // Tracks smallest value seen on the right side
        int suffixMin = Integer.MAX_VALUE;

        // prefixMax[i] = max value in nums[0..i]
        int[] prefixMax = new int[n];

        prefixMax[0] = nums[0];

        for (int i = 1; i < n; i++) {
            prefixMax[i] = Math.max(prefixMax[i - 1], nums[i]);
        }

        // Merge connected regions from right to left
        for (int i = n - 2; i >= 0; i--) {

            suffixMin = Math.min(suffixMin, nums[i + 1]);

            // Connection exists across boundary
            if (suffixMin < prefixMax[i]) {
                prefixMax[i] = prefixMax[i + 1];
            }
        }

        return prefixMax;
    }
}