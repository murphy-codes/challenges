// Source: https://leetcode.com/problems/number-of-zero-filled-subarrays/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-18
// At the time of submission:
//   Runtime 3 ms Beats 94.03%
//   Memory 61.91 MB Beats 34.02%

/****************************************
* 
* Given an integer array `nums`, return the number of subarrays filled with `0`.
* A subarray is a contiguous non-empty sequence of elements within an array.
*
* Example 1:
* Input: nums = [1,3,0,0,2,0,0,4]
* Output: 6
* Explanation:
* There are 4 occurrences of [0] as a subarray.
* There are 2 occurrences of [0,0] as a subarray.
* There is no occurrence of a subarray with a size more than 2 filled with 0. Therefore, we return 6.
*
* Example 2:
* Input: nums = [0,0,0,2,0,0]
* Output: 9
* Explanation:
* There are 5 occurrences of [0] as a subarray.
* There are 3 occurrences of [0,0] as a subarray.
* There is 1 occurrence of [0,0,0] as a subarray.
* There is no occurrence of a subarray with a size more than 3 filled with 0. Therefore, we return 9.
* Example 3:
*
* Input: nums = [2,10,2019]
* Output: 0
* Explanation: There is no subarray filled with 0. Therefore, we return 0.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • -10^9 <= nums[i] <= 10^9
* 
****************************************/

class Solution {
    // This solution counts zero-filled subarrays in O(n) time.  
    // For each element, track the current streak of consecutive zeros.  
    // Each zero contributes 'zeroCount' new subarrays ending at that index.  
    // Reset streak on non-zero elements. Space complexity is O(1).  
    // Time: O(n), Space: O(1).
    public long zeroFilledSubarray(int[] nums) {
        long result = 0;   // total zero-filled subarrays
        long zeroCount = 0; // current streak of consecutive zeros
        
        for (int num : nums) {
            if (num == 0) {
                zeroCount++;          // extend the current streak
                result += zeroCount;  // add all new subarrays ending here
            } else {
                zeroCount = 0;        // reset streak
            }
        }
        return result;
    }
}
