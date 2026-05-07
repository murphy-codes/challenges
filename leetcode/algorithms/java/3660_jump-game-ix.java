// Source: https://leetcode.com/problems/jump-game-ix/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-06
// At the time of submission:
//   Runtime 5 ms Beats 87.10%
//   Memory 197.64 MB Beats 85.48%

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
    // Split array into components where left max <= right min.
    // Indices inside a component can reach the component maximum.
    // Use prefix max and suffix min to greedily form components.
    // Time: O(n), Space: O(n)
    public int[] maxValue(int[] nums) {
        int n = nums.length;

        int[] suffixMin = new int[n];
        suffixMin[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(nums[i], suffixMin[i + 1]);
        }

        int[] ans = new int[n];

        int start = 0;
        int currentMax = nums[0];

        for (int i = 0; i < n; i++) {
            currentMax = Math.max(currentMax, nums[i]);

            // End current component
            if (i == n - 1 || currentMax <= suffixMin[i + 1]) {

                for (int j = start; j <= i; j++) {
                    ans[j] = currentMax;
                }

                start = i + 1;

                if (start < n) {
                    currentMax = nums[start];
                }
            }
        }

        return ans;
    }
}