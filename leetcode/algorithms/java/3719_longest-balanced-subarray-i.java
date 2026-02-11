// Source: https://leetcode.com/problems/longest-balanced-subarray-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-10
// At the time of submission:
//   Runtime 253 ms Beats 92.28%
//   Memory 47.14 MB Beats 45.77%

/****************************************
* 
* You are given an integer array `nums`.
* A subarray is called balanced if the number of distinct even numbers in the
* _ subarray is equal to the number of distinct odd numbers.
* Return the length of the longest balanced subarray.
*
* Example 1:
* Input: nums = [2,5,4,3]
* Output: 4
* Explanation:
* The longest balanced subarray is `[2, 5, 4, 3]`.
* It has 2 distinct even numbers `[2, 4]` and 2 distinct odd numbers `[5, 3]`.
* _ Thus, the answer is 4.
*
* Example 2:
* Input: nums = [3,2,2,5,4]
* Output: 5
* Explanation:
* The longest balanced subarray is `[3, 2, 2, 5, 4]`.
* It has 2 distinct even numbers `[2, 4]` and 2 distinct odd numbers `[3, 5]`.
* _ Thus, the answer is 5.
*
* Example 3:
* Input: nums = [1,2,3,2]
* Output: 3
* Explanation:
* The longest balanced subarray is `[2, 3, 2]`.
* It has 1 distinct even number `[2]` and 1 distinct odd number `[3]`.
* _ Thus, the answer is 3.
*
* Constraints:
* • `1 <= nums.length <= 1500`
* • `1 <= nums[i] <= 10^5`
* 
****************************************/

import java.util.HashSet;
import java.util.Set;

class Solution {
    // Try every subarray by fixing a left index and expanding the right index.
    // Track distinct even and odd values using two HashSets for each subarray.
    // Update the answer whenever the counts of distinct evens and odds match.
    // Time complexity is O(n^2) due to all subarrays being examined.
    // Space complexity is O(n) in the worst case for the distinct sets.
    public int longestBalanced(int[] nums) {
        int res = 0, r = 0, n = nums.length;
        Set<Integer> evenS = new HashSet<>();
        Set<Integer> oddS = new HashSet<>();
        for (int l = 0; l < n; l++) {
            evenS.clear();
            oddS.clear();
            r = l;
            while (r < n) {
                if (nums[r] % 2 == 0) evenS.add(nums[r]);
                else oddS.add(nums[r]);
                if (evenS.size() == oddS.size() && r - l + 1 > res) res = r - l + 1;
                r++;
            }
        }
        return res;
    }
}