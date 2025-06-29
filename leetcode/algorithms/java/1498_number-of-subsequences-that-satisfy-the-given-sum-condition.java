// Source: https://leetcode.com/problems/number-of-subsequences-that-satisfy-the-given-sum-condition/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-28
// At the time of submission:
//   Runtime 27 ms Beats 99.52%
//   Memory 54.84 MB Beats 97.20%

/****************************************
* 
* You are given an array of integers `nums` and an integer `target`.
*
* Return the number of non-empty subsequences of `nums` such that the sum of the
* _ minimum and maximum element on it is less or equal to `target`. Since the
* _ answer may be too large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: nums = [3,5,6,7], target = 9
* Output: 4
* Explanation: There are 4 subsequences that satisfy the condition.
* [3] -> Min value + max value <= target (3 + 3 <= 9)
* [3,5] -> (3 + 5 <= 9)
* [3,5,6] -> (3 + 6 <= 9)
* [3,6] -> (3 + 6 <= 9)
*
* Example 2:
* Input: nums = [3,3,6,8], target = 10
* Output: 6
* Explanation: There are 6 subsequences that satisfy the condition. (nums can have repeated numbers).
* [3] , [3] , [3,3], [3,6] , [3,6] , [3,3,6]
*
* Example 3:
* Input: nums = [2,3,3,4,6,7], target = 12
* Output: 61
* Explanation: There are 63 non-empty subsequences, two of them do not satisfy the condition ([6,7], [7]).
* Number of valid subsequences (63 - 2 = 61).
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • 1 <= nums[i] <= 10^6
* • 1 <= target <= 10^6
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Sort nums and use two pointers to find valid (min, max) subsequences.
    // For each valid pair, count 2^(right - left) subsequences between them.
    // Precompute powers of 2 modulo 10^9+7 to speed up counting.
    // Time: O(n log n) for sorting + O(n) for two-pointer scan.
    // Space: O(n) for power array.
    private static final int MOD = 1_000_000_007;

    public int numSubseq(int[] nums, int target) {
        Arrays.sort(nums); // Sort array to easily find valid (min, max) pairs
        int n = nums.length;

        // Precompute powers of 2 up to n
        int[] pow2 = new int[n];
        pow2[0] = 1;
        for (int i = 1; i < n; i++) {
            pow2[i] = (pow2[i - 1] * 2) % MOD;
        }

        int count = 0;
        int left = 0, right = n - 1;

        while (left <= right) {
            if (nums[left] + nums[right] <= target) {
                count = (count + pow2[right - left]) % MOD;
                left++;
            } else {
                right--;
            }
        }

        return count;
    }
}
