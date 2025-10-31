// Source: https://leetcode.com/problems/the-two-sneaky-numbers-of-digitville/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-31
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 45.94 MB Beats 7.71%

/****************************************
* 
* In the town of Digitville, there was a list of numbers called `nums`
* _ containing integers from `0` to `n - 1`. Each number was supposed to appear
* _ exactly once in the list, however, two mischievous numbers sneaked in an
* _ additional time, making the list longer than usual.
* As the town detective, your task is to find these two sneaky numbers. Return
* _ an array of size two containing the two numbers (in any order), so peace
* _ can return to Digitville.
*
* Example 1:
* Input: nums = [0,1,1,0]
* Output: [0,1]
* Explanation:
* The numbers 0 and 1 each appear twice in the array.
*
* Example 2:
* Input: nums = [0,3,2,1,3,2]
* Output: [2,3]
* Explanation:
* The numbers 2 and 3 each appear twice in the array.
*
* Example 3:
* Input: nums = [7,1,5,4,3,4,6,0,9,5,8,2]
* Output: [4,5]
* Explanation:
* The numbers 4 and 5 each appear twice in the array.
*
* Constraints:
* • `2 <= n <= 100`
* • `nums.length == n + 2`
* • `0 <= nums[i] < n`
* • The input is generated such that `nums` contains exactly two repeated elements.
* 
****************************************/

class Solution {
    // Count occurrences of each number using an auxiliary array, then record
    // the two numbers that appear more than once. Since n ≤ 100, using a
    // small counting array is simple and efficient. Time Complexity: O(n),
    // Space Complexity: O(n), dominated by the count array of fixed size 101.
    public int[] getSneakyNumbers(int[] nums) {
        int[] sneaky = new int[2];
        int[] count = new int[101];
        for (int n : nums) count[n]++;
        for (int i = 0; i < 101; i++) 
            if (count[i]>1) sneaky[(sneaky[0] > 0) ? 1 : 0] = i;
        return sneaky;
    }
}