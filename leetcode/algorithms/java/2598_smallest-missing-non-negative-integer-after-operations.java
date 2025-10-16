// Source: https://leetcode.com/problems/smallest-missing-non-negative-integer-after-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-16
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 58.52 MB Beats 51.78%

/****************************************
* 
* You are given a 0-indexed integer array `nums` and an integer `value`.
* In one operation, you can add or subtract `value` from any element of `nums`.
* • For example, if `nums = [1,2,3]` and `value = 2`, you can choose to subtract
* __ `value` from `nums[0]` to make `nums = [-1,2,3]`.
* The MEX (minimum excluded) of an array is the smallest missing non-negative
* _ integer in it.
* • For example, the MEX of `[-1,2,3]` is `0` while the MEX of `[1,0,3]` is `2`.
* Return the maximum MEX of `nums` after applying the mentioned operation
* _ any number of times.
*
* Example 1:
* Input: nums = [1,-10,7,13,6,8], value = 5
* Output: 4
* Explanation: One can achieve this result by applying the following operations:
* - Add value to nums[1] twice to make nums = [1,0,7,13,6,8]
* - Subtract value from nums[2] once to make nums = [1,0,2,13,6,8]
* - Subtract value from nums[3] twice to make nums = [1,0,2,3,6,8]
* The MEX of nums is 4. It can be shown that 4 is the maximum MEX we can achieve.
*
* Example 2:
* Input: nums = [1,-10,7,13,6,8], value = 7
* Output: 2
* Explanation: One can achieve this result by applying the following operation:
* - subtract value from nums[2] once to make nums = [1,-10,0,13,6,8]
* The MEX of nums is 2. It can be shown that 2 is the maximum MEX we can achieve.
*
* Constraints:
* • `1 <= nums.length, value <= 10^5`
* • `-109 <= nums[i] <= 10^9`
* 
****************************************/

class Solution {
    // This solution uses modular arithmetic to find the max possible MEX.
    // Each number can be changed to any integer with the same remainder mod `value`.
    // We count how many numbers fall into each remainder bucket, then find the one
    // with the smallest frequency. The result is derived as (minFreq * value + remainder).
    // Time Complexity: O(n + value), Space Complexity: O(value).
    public int findSmallestInteger(int[] nums, int value) {
        // Array to count frequency of each possible remainder (mod class)
        int[] remainderFreq = new int[value];
        
        // Step 1: Compute normalized remainder frequencies
        for (int num : nums) {
            int remainder = num % value;
            if (remainder < 0) remainder += value; // normalize negative mods
            remainderFreq[remainder]++;
        }

        // Step 2: Find the remainder bucket with the smallest frequency
        int minFreq = remainderFreq[0];
        int targetRemainder = 0;
        for (int i = 0; i < value; i++) {
            if (remainderFreq[i] < minFreq) {
                minFreq = remainderFreq[i];
                targetRemainder = i;
            }
        }

        // Step 3: Calculate the final result based on frequency pattern
        return value * minFreq + targetRemainder;
    }
}
