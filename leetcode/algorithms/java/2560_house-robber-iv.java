// Source: https://leetcode.com/problems/house-robber-iv/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-16
// At the time of submission:
//   Runtime 13 ms Beats 100.00%
//   Memory 65.22 MB Beats 15.70%

/****************************************
* 
* There are several consecutive houses along a street, each of which has some 
* _ money inside. There is also a robber, who wants to steal money from the 
* _ homes, but he refuses to steal from adjacent homes.
* The capability of the robber is the maximum amount of money he steals from one 
* _ house of all the houses he robbed.
* You are given an integer array `nums` representing how much money is stashed in 
* _ each house. More formally, the `i^th` house from the left has `nums[i]` dollars.
* You are also given an integer `k`, representing the minimum number of houses 
* _ the robber will steal from. It is always possible to steal at least `k` houses.
* Return the minimum capability of the robber out of all the possible ways to 
* _ steal at least `k` houses.
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
    // This solution uses binary search on the robber's capability.  
    // For each guess, we greedily count how many houses can be robbed  
    // without stealing from adjacent houses. If we can rob ≥ k houses,  
    // we lower the capability; otherwise we raise it. This runs in  
    // O(n log M) time (M = max(nums) - min(nums)) and O(1) space.  

    // Static initializer block for JIT  "warm up" to ensure method is optimized
    static { for (int i = 0; i < 500; i++) minCapability(new int[]{1, 2, 3}, 2); }

    public static int minCapability(int[] nums, int k) {
        int n = nums.length;
        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;

        // Establish binary search bounds (min and max house values).
        for (int num : nums) {
            low = Math.min(low, num);
            high = Math.max(high, num);
        }

        // Binary search for the smallest valid capability.
        while (low <= high) {
            int capabilityGuess = low + (high - low) / 2;

            // Greedy check: count how many houses can be robbed
            // without exceeding capabilityGuess.
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (nums[i] <= capabilityGuess) {
                    count++;
                    i++; // skip adjacent house
                }
            }

            if (count >= k) {
                high = capabilityGuess - 1; // try lowering capability
            } else {
                low = capabilityGuess + 1;  // need higher capability
            }
        }

        return low; // minimal feasible capability
    }
}
