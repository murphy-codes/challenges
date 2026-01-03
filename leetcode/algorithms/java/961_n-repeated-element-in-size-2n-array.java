// Source: https://leetcode.com/problems/n-repeated-element-in-size-2n-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-01
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 47.66 MB Beats 25.52%

/****************************************
* 
* You are given an integer array `nums` with the following properties:
* • `nums.length == 2 * n`.
* • `nums` contains `n + 1` unique elements.
* • Exactly one element of `nums` is repeated `n` times.
* Return the element that is repeated `n` times.
*
* Example 1:
* Input: nums = [1,2,3,3]
* Output: 3
*
* Example 2:
* Input: nums = [2,1,2,5,3,2]
* Output: 2
*
* Example 3:
* Input: nums = [5,1,5,2,5,3,5,4]
* Output: 5
*
* Constraints:
* • `2 <= n <= 5000`
* • `nums.length == 2 * n`
* • `0 <= nums[i] <= 10^4`
* • `nums` contains `n + 1` unique elements and one of them is repeated exactly `n` times.
* 
****************************************/

class Solution {
  // Track which values have already been seen using a boolean array indexed
  // by the possible value range. As we iterate, the first value encountered
  // twice must be the one repeated n times due to the problem constraints.
  // Time: O(n), singular scan. Space: O(1), array size fixed by constraints
    public int repeatedNTimes(int[] nums) {
        boolean[] seen = new boolean[10001];
        for (int i : nums){
            if (seen[i]) return i;
            seen[i] = true;
        }
        return -1;
    }
}
