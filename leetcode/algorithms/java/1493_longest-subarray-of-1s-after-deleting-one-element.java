// Source: https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-24
// At the time of submission:
//   Runtime 3 ms Beats 77.98%
//   Memory 56.46 MB Beats 84.64%

/****************************************
* 
* Given a binary array `nums`, you should delete one element from it.
* Return the size of the longest non-empty subarray containing only `1`'s in the
* _ resulting array. Return `0` if there is no such subarray.
*
* Example 1:
* Input: nums = [1,1,0,1]
* Output: 3
* Explanation: After deleting the number in position 2, [1,1,1] contains 3 numbers with value of 1's.
*
* Example 2:
* Input: nums = [0,1,1,1,0,1,1,0,1]
* Output: 5
* Explanation: After deleting the number in position 4, [0,1,1,1,1,1,0,1] longest subarray with value of 1's is [1,1,1,1,1].
*
* Example 3:
* Input: nums = [1,1,1]
* Output: 2
* Explanation: You must delete one element.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • `nums[i]` is either `0` or `1`.
* 
****************************************/

class Solution {
    // Track consecutive 1s before and after a zero using prev and curr counters.
    // Whenever a zero is hit, shift curr to prev, and reset curr. Combine prev+curr
    // to find the longest subarray after deleting one zero. If no zero exists, 
    // delete one element anyway, so the answer is total length minus one.
    // Time Complexity: O(n) since we scan nums once. Space Complexity: O(1).
    public int longestSubarray(int[] nums) {
        int prev = 0, curr = 0, longest = 0, zeroes = 0;
        boolean detected = false;
        for (int i : nums) {
            if (i == 0) {
                detected = true;
                zeroes += 1;
                if (zeroes == 1) {
                    prev = curr;
                    curr = 0;
                } else {
                    prev = 0;
                }
            } else {
                curr += 1;
                zeroes = 0;
                longest = Math.max(prev + curr,longest);
            }
        }
        if (detected) return longest; 
        else return Math.max(longest-1,0);
    }
}