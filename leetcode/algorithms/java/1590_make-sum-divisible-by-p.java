// Source: https://leetcode.com/problems/make-sum-divisible-by-p/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-29
// At the time of submission:
//   Runtime 23 ms Beats 99.12%
//   Memory 95.58 MB Beats 59.78%

/****************************************
* 
* Given an array of positive integers `nums`, remove the smallest subarray
* _ (possibly empty) such that the sum of the remaining elements is divisible
* _ by `p`. It is not allowed to remove the whole array.
* Return the length of the smallest subarray that you need to remove, or `-1`
* _ if it's impossible.
* A subarray is defined as a contiguous block of elements in the array.
*
* Example 1:
* Input: nums = [3,1,4,2], p = 6
* Output: 1
* Explanation: The sum of the elements in nums is 10, which is not divisible by 6. We can remove the subarray [4], and the sum of the remaining elements is 6, which is divisible by 6.
*
* Example 2:
* Input: nums = [6,3,5,2], p = 9
* Output: 2
* Explanation: We cannot remove a single element to get a sum divisible by 9. The best way is to remove the subarray [5,2], leaving us with [6,3] with sum 9.
*
* Example 3:
* Input: nums = [1,2,3], p = 3
* Output: 0
* Explanation: Here the sum is 6. which is already divisible by 3. Thus we do not need to remove anything.
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `1 <= nums[i] <= 10^9`
* • `1 <= p <= 10^9`
* 
****************************************/

import java.util.HashMap;

class Solution {
    // Compute total sum modulo p as the target remainder. If target is 0, return 0.
    // Track prefix sums modulo p in a hashmap mapping mod value -> last index.
    // For each index, calculate which previous prefix would form a removable subarray
    // with sum modulo p equal to target. Update minimum length accordingly.
    // Time: O(n) for a single pass, Space: O(n) for hashmap storing prefix mod indices.
    public int minSubarray(int[] nums, int p) {
        long totalSum = 0;
        for (int num : nums) totalSum += num;

        long target = totalSum % p;
        if (target == 0) return 0; // already divisible

        HashMap<Integer, Integer> modIndex = new HashMap<>();
        modIndex.put(0, -1); // base case for prefix sum modulo

        long prefixSum = 0;
        int minLength = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            // early exit if a single element matches target
            if (nums[i] % p == target) return 1;

            prefixSum += nums[i];
            int prefixMod = (int) (prefixSum % p);
            int targetMod = (int) ((prefixSum - target + p) % p);

            if (modIndex.containsKey(targetMod)) {
                minLength = Math.min(minLength, i - modIndex.get(targetMod));
            }

            modIndex.put(prefixMod, i);
        }

        return minLength >= nums.length ? -1 : minLength;
    }
}
