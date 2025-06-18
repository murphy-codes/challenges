// Source: https://leetcode.com/problems/divide-array-into-arrays-with-max-difference/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-17
// At the time of submission:
//   Runtime 23 ms Beats 81.15%
//   Memory 62.08 MB Beats 9.42%

/****************************************
* 
* You are given a positive integer `k` and
* _ an integer array `nums` of size `n` where `n` is a multiple of 3.
* Divide the array `nums` into `n / 3` arrays of size 3
* _ satisfying the following condition:
* • The difference between any two elements in one array is <= `k`.
* Return a 2D array containing the arrays. If it is impossible to satisfy the
* _ conditions, return an empty array. And if there are multiple answers,
* _ return any of them.
*
* Example 1:
* Input: nums = [1,3,4,8,7,9,3,5,1], k = 2
* Output: [[1,1,3],[3,4,5],[7,8,9]]
* Explanation:
* The difference between any two elements in each array is less than or equal to 2.
*
* Example 2:
* Input: nums = [2,4,2,2,5,2], k = 2
* Output: []
* Explanation:
* Different ways to divide nums into 2 arrays of size 3 are:
* [[2,2,2],[2,4,5]] (and its permutations)
* [[2,2,4],[2,2,5]] (and its permutations)
* Because there are four 2s there will be an array with the elements 2 and 5 no matter how we divide it. since 5 - 2 = 3 > k, the condition is not satisfied and so there is no valid division.
*
* Example 3:
* Input: nums = [4,2,9,8,2,12,7,12,10,5,8,5,5,7,9,2,5,11], k = 14
* Output: [[2,2,12],[4,8,5],[5,9,7],[7,8,5],[5,9,10],[11,12,2]]
* Explanation:
* The difference between any two elements in each array is less than or equal to 14.
*
* Constraints:
* • n == nums.length
* • 1 <= n <= 10^5
* • n is a multiple of 3
* • 1 <= nums[i] <= 10^5
* • 1 <= k <= 10^5
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Sort the array to group similar values together.
    // Then, iterate in steps of 3 and form triplets.
    // For each triplet, check if max - min ≤ k.
    // If not, return empty; otherwise, store it.
    // Time: O(n log n) due to sorting. Space: O(n) for result.
    public int[][] divideArray(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        int[][] result = new int[n / 3][3];

        for (int i = 0; i < n; i += 3) {
            int a = nums[i], b = nums[i + 1], c = nums[i + 2];
            if (c - a > k) {
                return new int[0][0]; // not a valid triplet
            }
            result[i / 3] = new int[] {a, b, c};
        }
        return result;
    }
}
