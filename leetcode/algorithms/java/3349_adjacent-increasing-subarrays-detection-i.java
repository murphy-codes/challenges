// Source: https://leetcode.com/problems/adjacent-increasing-subarrays-detection-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-13
// At the time of submission:
//   Runtime 2 ms Beats 47.79%
//   Memory 44.38 MB Beats 80.88%

/****************************************
* 
* Given an array `nums` of `n` integers and an integer `k`, determine whether
* _ there exist two adjacent subarrays of length `k` such that both subarrays
* _ are strictly increasing. Specifically, check if there are two subarrays
* _ starting at indices `a` and `b` (`a < b`), where:
*
* • Both subarrays `nums[a..a + k - 1]` and `nums[b..b + k - 1]` are
* __ strictly increasing.
* • The subarrays must be adjacent, meaning `b = a + k`.
* Return `true` if it is possible to find two such subarrays, and `false` otherwise.
*
* Example 1:
* Input: nums = [2,5,7,8,9,2,3,4,3,1], k = 3
* Output: true
* Explanation:
* The subarray starting at index 2 is [7, 8, 9], which is strictly increasing.
* The subarray starting at index 5 is [2, 3, 4], which is also strictly increasing.
* These two subarrays are adjacent, so the result is true.
*
* Example 2:
* Input: nums = [1,2,3,4,4,4,4,5,6,7], k = 5
* Output: false
*
* Constraints:
* • `2 <= nums.length <= 100`
* • `1 < 2 * k <= nums.length`
* • `-1000 <= nums[i] <= 1000`
* 
****************************************/

class Solution {
    // Build an array tracking each index's current increasing run length.
    // Two adjacent subarrays of size k exist if both runs reach length k
    // at their respective endpoints k apart. Check each possible start.
    // Time: O(n) since each element is visited once. Space: O(n) for inc[].
    public boolean hasIncreasingSubarrays(List<Integer> nums, int k) {
        int n = nums.size();
        int[] inc = new int[n];
        inc[0] = 1;
        for (int i = 1; i < n; i++)
            inc[i] = (nums.get(i) > nums.get(i - 1)) ? inc[i - 1] + 1 : 1;
        for (int a = 0; a + 2 * k <= n; a++) {
            if (inc[a + k - 1] >= k && inc[a + 2 * k - 1] >= k)
                return true;
        }
        return false;
    }
}