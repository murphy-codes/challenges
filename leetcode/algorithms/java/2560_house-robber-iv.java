// Source: https://leetcode.com/problems/house-robber-iv/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-16

/****************************************
* 
* There are several consecutive houses along a street, each of which has some money inside. There is also a robber, who wants to steal money from the homes, but he refuses to steal from adjacent homes.
* The capability of the robber is the maximum amount of money he steals from one house of all the houses he robbed.
* You are given an integer array `nums` representing how much money is stashed in each house. More formally, the `i^th` house from the left has `nums[i]` dollars.
* You are also given an integer `k`, representing the minimum number of houses the robber will steal from. It is always possible to steal at least `k` houses.
* Return the minimum capability of the robber out of all the possible ways to steal at least `k` houses.
*
* Example 1:
* Input: nums = [2,3,5,9], k = 2
* Output: 5
* Explanation:
* There are three ways to rob at least 2 houses:
* - Rob the houses at indices 0 and 2. Capability is max(nums[0], nums[2]) = 5.
* - Rob the houses at indices 0 and 3. Capability is max(nums[0], nums[3]) = 9.
* - Rob the houses at indices 1 and 3. Capability is max(nums[1], nums[3]) = 9.
* Therefore, we return min(5, 9, 9) = 5.
*
* Example 2:
* Input: nums = [2,7,9,3,1], k = 2
* Output: 2
* Explanation: There are 7 ways to rob the houses. The way which leads to minimum capability is to rob the house at index 0 and 4. Return max(nums[0], nums[4]) = 2.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • 1 <= nums[i] <= 10^9
* • 1 <= k <= (nums.length + 1)/2
* 
****************************************/

class Solution {
    // Time Complexity: O(n log M)
    // - Binary search runs in O(log M), where M is the range of values (10^9).
    // - Each check (greedy selection) runs in O(n).
    // - Overall, O(n log M), which is optimal for n up to 10^5.
    // Space Complexity: O(1)
    // - Only a few extra variables are used.
    public int minCapability(int[] nums, int k) {
        int left = Integer.MAX_VALUE, right = Integer.MIN_VALUE;

        // Find the min and max value in nums
        for (int num : nums) {
            left = Math.min(left, num);
            right = Math.max(right, num);
        }

        // Binary search for the minimum capability
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canRobAtLeastK(nums, k, mid)) {
                right = mid; // Try to lower capability
            } else {
                left = mid + 1; // Increase capability
            }
        }

        return left; // The minimum valid capability found
    }

    // Helper function: Can we rob at least k houses with max value ≤ capability?
    private boolean canRobAtLeastK(int[] nums, int k, int capability) {
        int count = 0;
        int i = 0;

        while (i < nums.length) {
            if (nums[i] <= capability) { // Rob this house
                count++;
                if (count >= k) return true; // We successfully robbed k houses
                i++; // Skip next house (since adjacent houses can't be robbed)
            }
            i++; // Move to the next house
        }

        return false;
    }
}
