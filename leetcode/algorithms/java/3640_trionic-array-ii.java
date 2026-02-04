// Source: https://leetcode.com/problems/trionic-array-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-03
// At the time of submission:
//   Runtime 3 ms Beats 94.87%
//   Memory 95.09 MB Beats 74.36%

/****************************************
* 
* You are given an integer array `nums` of length `n`.
* A trionic subarray is a contiguous subarray `nums[l...r]` (with
* _ `0 <= l < r < n`) for which there exist indices `l < p < q < r` such that:
* • `nums[l...p]` is strictly increasing,
* • `nums[p...q]` is strictly decreasing,
* • `nums[q...r]` is strictly increasing.
* Return the maximum sum of any trionic subarray in `nums`.
*
* Example 1:
* Input: nums = [0,-2,-1,-3,0,2,-1]
* Output: -4
* Explanation:
* Pick l = 1, p = 2, q = 3, r = 5:
* nums[l...p] = nums[1...2] = [-2, -1] is strictly increasing (-2 < -1).
* nums[p...q] = nums[2...3] = [-1, -3] is strictly decreasing (-1 > -3)
* nums[q...r] = nums[3...5] = [-3, 0, 2] is strictly increasing (-3 < 0 < 2).
* Sum = (-2) + (-1) + (-3) + 0 + 2 = -4.
*
* Example 2:
* Input: nums = [1,4,2,7]
* Output: 14
* Explanation:
* Pick l = 0, p = 1, q = 2, r = 3:
* nums[l...p] = nums[0...1] = [1, 4] is strictly increasing (1 < 4).
* nums[p...q] = nums[1...2] = [4, 2] is strictly decreasing (4 > 2).
* nums[q...r] = nums[2...3] = [2, 7] is strictly increasing (2 < 7).
* Sum = 1 + 4 + 2 + 7 = 14.
*
* Constraints:
* • `4 <= n = nums.length <= 10^5`
* • `-10^9 <= nums[i] <= 10^9`
* • It is guaranteed that at least one trionic subarray exists.
* 
****************************************/

class Solution {
    // We enumerate maximal trionic subarrays using a grouped linear scan.
    // For each increasing→decreasing→increasing structure, we build the
    // minimal valid core and extend its ends using Kadane-style max sums.
    // Each index is visited a constant number of times, ensuring O(n) time.
    // Time Complexity: O(n), Space Complexity: O(1)
    public long maxSumTrionic(int[] nums) {
        int n = nums.length;
        long answer = Long.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            int j = i + 1;
            long baseSum = 0;

            // First segment: strictly increasing
            while (j < n && nums[j - 1] < nums[j]) {
                j++;
            }
            int p = j - 1;
            if (p == i) continue; // first segment must have length >= 2

            // Second segment: strictly decreasing
            baseSum += nums[p] + nums[p - 1];
            while (j < n && nums[j - 1] > nums[j]) {
                baseSum += nums[j];
                j++;
            }
            int q = j - 1;

            // Validate second and third segment existence
            if (q == p || q == n - 1 || (j < n && nums[j] <= nums[q])) {
                i = q;
                continue;
            }

            // Third segment: strictly increasing (minimum inclusion)
            baseSum += nums[q + 1];

            // Extend third segment to the right (maximize sum)
            long maxExtra = 0, running = 0;
            for (int k = q + 2; k < n && nums[k] > nums[k - 1]; k++) {
                running += nums[k];
                maxExtra = Math.max(maxExtra, running);
            }
            baseSum += maxExtra;

            // Extend first segment to the left (maximize sum)
            maxExtra = 0;
            running = 0;
            for (int k = p - 2; k >= i; k--) {
                running += nums[k];
                maxExtra = Math.max(maxExtra, running);
            }
            baseSum += maxExtra;

            answer = Math.max(answer, baseSum);
            i = q - 1; // jump to next possible start
        }

        return answer;
    }
}
