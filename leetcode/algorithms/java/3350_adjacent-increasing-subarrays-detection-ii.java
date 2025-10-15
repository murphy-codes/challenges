// Source: https://leetcode.com/problems/adjacent-increasing-subarrays-detection-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-14
// At the time of submission:
//   Runtime 18 ms Beats 54.26%
//   Memory 77.73 MB Beats 75.53%

/****************************************
* 
* Given an array `nums` of `n` integers, your task is to find the maximum value
* _ of `k` for which there exist two adjacent subarrays of length `k` each, such
* _ that both subarrays are strictly increasing. Specifically, check if there are
* _ two subarrays of length `k` starting at indices `a` and `b` (`a < b`), where:
* • Both subarrays `nums[a..a + k - 1]` and `nums[b..b + k - 1]` are strictly
* __ increasing.
* • The subarrays must be adjacent, meaning `b = a + k`.
* Return the maximum possible value of `k`.
* A subarray is a contiguous non-empty sequence of elements within an array.
*
* Example 1:
* Input: nums = [2,5,7,8,9,2,3,4,3,1]
* Output: 3
* Explanation:
* The subarray starting at index 2 is [7, 8, 9], which is strictly increasing.
* The subarray starting at index 5 is [2, 3, 4], which is also strictly increasing.
* These two subarrays are adjacent, and 3 is the maximum possible value of k for which two such adjacent strictly increasing subarrays exist.
*
* Example 2:
* Input: nums = [1,2,3,4,4,4,4,5,6,7]
* Output: 2
* Explanation:
* The subarray starting at index 0 is [1, 2], which is strictly increasing.
* The subarray starting at index 2 is [3, 4], which is also strictly increasing.
* These two subarrays are adjacent, and 2 is the maximum possible value of k for which two such adjacent strictly increasing subarrays exist.
*
* Constraints:
* • `2 <= nums.length <= 2 * 10^5`
* • `-10^9 <= nums[i] <= 10^9`
* 
****************************************/

import java.util.List;

class Solution {
    // Time: O(n) — single pass to build runs, two short passes over runs.
    // Space: O(r) where r = number of runs (≤ n). Could be considered O(n) worst-case.
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int n = nums.size();
        if (n < 2) return 0;

        // Build list of increasing run lengths
        int maxK = 0;
        int run = 1;
        java.util.ArrayList<Integer> runs = new java.util.ArrayList<>();

        for (int i = 1; i < n; i++) {
            if (nums.get(i) > nums.get(i - 1)) {
                run++;
            } else {
                runs.add(run);
                run = 1;
            }
        }
        runs.add(run);

        // Case A: both subarrays lie inside a single run -> floor(run/2)
        for (int r : runs) {
            maxK = Math.max(maxK, r / 2);
        }

        // Case B: one subarray from run i, the adjacent one from run i+1
        for (int i = 0; i + 1 < runs.size(); i++) {
            maxK = Math.max(maxK, Math.min(runs.get(i), runs.get(i + 1)));
        }

        return maxK;
    }
}
