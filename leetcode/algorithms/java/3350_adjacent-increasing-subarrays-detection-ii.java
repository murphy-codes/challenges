// Source: https://leetcode.com/problems/adjacent-increasing-subarrays-detection-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-14
// At the time of submission:
//   Runtime 7 ms Beats 100.00%
//   Memory 91.79 MB Beats 7.45%

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

class Solution {
    // Scans nums to find lengths of all strictly increasing segments.
    // For each segment, compute the best possible k using either:
    //   - currLen / 2 if two subarrays fit within one segment, or
    //   - min(currLen, prevLen) if spanning two adjacent segments.
    // Time: O(n), Space: O(1). Single linear pass over nums.
    public int maxIncreasingSubarrays(List<Integer> numsList) {
        Integer[] nums = numsList.toArray(Integer[]::new);
        int n = nums.length;
        int i = 0, maxK = 0;
        int prevLen = 0;

        while (i < n) {
            int start = i;

            // Move i to the end of the current strictly increasing run
            while (i + 1 < n && nums[i] < nums[i + 1]) {
                i++;
            }

            int currLen = i - start + 1;

            // Update maxK based on possible adjacent increasing subarrays
            maxK = Math.max(maxK, Math.max(currLen / 2, Math.min(currLen, prevLen)));

            // Prepare for next segment
            prevLen = currLen;
            i++;
        }

        return maxK;
    }
}
