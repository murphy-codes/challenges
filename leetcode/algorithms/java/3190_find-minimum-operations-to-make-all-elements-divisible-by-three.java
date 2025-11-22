// Source: https://leetcode.com/problems/find-minimum-operations-to-make-all-elements-divisible-by-three/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-21
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.61 MB Beats 13.37%

/****************************************
* 
* You are given an integer array `nums`. In one operation, you can add or
* _ subtract 1 from any element of `nums`.
* Return the minimum number of operations to make all elements of `nums`
* _ divisible by 3.
*
* Example 1:
* Input: nums = [1,2,3,4]
* Output: 3
* Explanation:
* All array elements can be made divisible by 3 using 3 operations:
* Subtract 1 from 1.
* Add 1 to 2.
* Subtract 1 from 4.
*
* Example 2:
* Input: nums = [3,6,9]
* Output: 0
*
* Constraints:
* • `1 <= nums.length <= 50`
* • `1 <= nums[i] <= 50`
* 
****************************************/

class Solution {
    // Iterate through nums and count elements that are not 
    // already divisible by three. Time: O(n), Space: O(1).
    public int minimumOperations(int[] nums) {
        int ops = 0;
        for (int n : nums) if (n%3!=0) ops++;
        return ops;
    }
}