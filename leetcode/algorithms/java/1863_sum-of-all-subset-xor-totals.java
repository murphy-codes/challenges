// Source: https://leetcode.com/problems/sum-of-all-subset-xor-totals/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-06
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 40.79 MB Beats 88.69%

/****************************************
* 
* The XOR total of an array is defined as the bitwise `XOR` of
* _ all its elements, or `0` if the array is empty.
* • For example, the XOR total of the array `[2,5,6]` is `2 XOR 5 XOR 6 = 1`.
* Given an array `nums`, return the sum of all XOR totals for every subset of `nums`.
* Note: Subsets with the same elements should be counted multiple times.
* An array `a` is a subset of an array `b` if `a` can be obtained from b
* _ by deleting some (possibly zero) elements of `b`.
*
* Example 1:
* Input: nums = [1,3]
* Output: 6
* Explanation: The 4 subsets of [1,3] are:
* - The empty subset has an XOR total of 0.
* - [1] has an XOR total of 1.
* - [3] has an XOR total of 3.
* - [1,3] has an XOR total of 1 XOR 3 = 2.
* 0 + 1 + 3 + 2 = 6
*
* Example 2:
* Input: nums = [5,1,6]
* Output: 28
* Explanation: The 8 subsets of [5,1,6] are:
* - The empty subset has an XOR total of 0.
* - [5] has an XOR total of 5.
* - [1] has an XOR total of 1.
* - [6] has an XOR total of 6.
* - [5,1] has an XOR total of 5 XOR 1 = 4.
* - [5,6] has an XOR total of 5 XOR 6 = 3.
* - [1,6] has an XOR total of 1 XOR 6 = 7.
* - [5,1,6] has an XOR total of 5 XOR 1 XOR 6 = 2.
* 0 + 5 + 1 + 6 + 4 + 3 + 7 + 2 = 28
*
* Example 3:
* Input: nums = [3,4,5,6,7,8]
* Output: 480
* Explanation: The sum of all XOR totals for every subset is 480.
*
* Constraints:
* • 1 <= nums.length <= 12
* • 1 <= nums[i] <= 20
* 
****************************************/

class Solution {
    // This solution uses backtracking to generate all subsets and compute their XOR sum.
    // At each step, we either include or exclude an element, forming 2^n subsets.
    // The base case returns the XOR total of a subset, and recursion accumulates results.
    // Since each element is visited once per subset, time complexity is O(2^n).
    // Space complexity is O(n) due to recursion depth (max depth = nums.length).
    public int subsetXORSum(int[] nums) {
        return backtrack(nums, 0, 0);
    }

    private int backtrack(int[] nums, int index, int currXOR) {
        // Base case: if we've considered all elements
        if (index == nums.length) return currXOR;
        
        // Recursive case: Include or exclude the current element
        int include = backtrack(nums, index + 1, currXOR ^ nums[index]);
        int exclude = backtrack(nums, index + 1, currXOR);
        
        return include + exclude;
    }
}
