// Source: https://leetcode.com/problems/minimum-removals-to-balance-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-06
// At the time of submission:
//   Runtime 23 ms Beats 100.00%
//   Memory 86.13 MB Beats 61.42%

/****************************************
* 
* You are given an integer array `nums` and an integer `k`.
* An array is considered balanced if the value of its maximum element is at most
* _ `k` times the minimum element.
* You may remove any number of elements from `nums`​​​​​​​ without making it empty.
* Return the minimum number of elements to remove so that the remaining array is balanced.
* Note: An array of size 1 is considered balanced as its maximum and minimum
* _ are equal, and the condition always holds true.
*
* Example 1:
* Input: nums = [2,1,5], k = 2
* Output: 1
* Explanation:
* Remove nums[2] = 5 to get nums = [2, 1].
* Now max = 2, min = 1 and max <= min * k as 2 <= 1 * 2. Thus, the answer is 1.
*
* Example 2:
* Input: nums = [1,6,2,9], k = 3
* Output: 2
* Explanation:
* Remove nums[0] = 1 and nums[3] = 9 to get nums = [6, 2].
* Now max = 6, min = 2 and max <= min * k as 6 <= 2 * 3. Thus, the answer is 2.
*
* Example 3:
* Input: nums = [4,6], k = 2
* Output: 0
* Explanation:
* Since nums is already balanced as 6 <= 4 * 2, no elements need to be removed.
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `1 <= nums[i] <= 10^9`
* • `1 <= k <= 10^5`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Sort the array and use a sliding window where nums[left] is the minimum
    // and nums[right] is the maximum. Expand right and shrink left until
    // nums[right] <= k * nums[left] holds. Track the largest valid window.
    // Minimum removals equals total length minus this window size.
    // Time: O(n log n), Space: O(1) excluding sort
    public int minRemoval(int[] nums, int k) {
        Arrays.sort(nums);
        int i = 0, mx = 0, n = nums.length;

        for (int j = 0; j < n; j++) {
            while ((long) nums[j] > (long) nums[i] * k) {
                i++;
            }
            mx = Math.max(mx, j - i + 1);
        }
        return n - mx;
    }
}
