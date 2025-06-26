// Source: https://leetcode.com/problems/divide-array-into-arrays-with-max-difference/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-17
// At the time of submission:
//   Runtime 7 ms Beats 99.96%
//   Memory 56.34 MB Beats 98.43%

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

class Solution {
    // Use counting sort to arrange nums in ascending order.
    // Then, group every 3 consecutive values and check if max - min ≤ k.
    // Return an empty array if any triplet violates the constraint.
    // Time: O(n + m), where m is the max value in nums.
    // Space: O(m) for the frequency array.
    public int[][] divideArray(int[] nums, int k) {
        // Find the max value to size the counting sort array
        int maxValue = 0;
        for (int num : nums) {
            if (num > maxValue) maxValue = num;
        }

        // Count occurrences of each number
        int[] freq = new int[maxValue + 1];
        for (int num : nums) {
            freq[num]++;
        }

        // Rebuild nums[] in sorted order using frequency array
        int index = 0;
        for (int num = 0; num < freq.length; num++) {
            for (int count = 0; count < freq[num]; count++) {
                nums[index++] = num;
            }
        }

        // Validate each group of 3 for the max difference constraint
        for (int i = 0; i < nums.length; i += 3) {
            if (nums[i + 2] - nums[i] > k) {
                return new int[0][];
            }
        }

        // Build result using valid triplets
        int[][] result = new int[nums.length / 3][];
        index = 0;
        for (int i = 0; i < result.length; i++) {
            result[i] = new int[] { nums[index++], nums[index++], nums[index++] };
        }

        return result;
    }
}
