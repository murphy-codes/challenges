// Source: https://leetcode.com/problems/smallest-missing-non-negative-integer-after-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-16
// At the time of submission:
//   Runtime 7 ms Beats 68.53%
//   Memory 58.56 MB Beats 51.78%

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
    // Use modular arithmetic: each num can become any int ≡ num % value.
    // Count freq of each residue (0..value-1). Then test x = 0,1,2,... :
    // if freq[x % value] > 0, use one; else x is the smallest missing int.
    // Time: O(n + MEX) ≈ O(n), Space: O(value) for frequency array.
    public int findSmallestInteger(int[] nums, int value) {
        int[] freq = new int[value];
        
        // Count frequency of normalized remainders
        for (int num : nums) {
            int r = ((num % value) + value) % value; // ensure positive remainder
            freq[r]++;
        }

        // Incrementally find smallest missing non-negative integer
        for (int x = 0; ; x++) {
            int r = x % value;
            if (freq[r] == 0) {
                return x; // MEX found
            }
            freq[r]--; // use one instance of this residue
        }
    }
}
