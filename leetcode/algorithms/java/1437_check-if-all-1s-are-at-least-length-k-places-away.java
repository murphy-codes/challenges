// Source: https://leetcode.com/problems/check-if-all-1s-are-at-least-length-k-places-away/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-15
// At the time of submission:
//   Runtime 1 ms Beats 99.71%
//   Memory 65.64 MB Beats 5.71%

/****************************************
* 
* Given an binary array `nums` and an integer `k`, return `true` if all `1`'s
* _ are at least `k` places away from each other, otherwise return `false`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/04/15/sample_1_1791.png]
* Input: nums = [1,0,0,0,1,0,0,1], k = 2
* Output: true
* Explanation: Each of the 1s are at least 2 places away from each other.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2020/04/15/sample_2_1791.png]
* Input: nums = [1,0,0,1,0,1], k = 2
* Output: false
* Explanation: The second 1 and third 1 are only one apart from each other.
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `0 <= k <= nums.length`
* • `nums[i]` is `0` or `1`
* 
****************************************/

class Solution {
    // Scan and track index of previous 1. If any are found to be within less than
    // k distance from the next 1, return false. Otherwise return true.
    // Time complexity is O(n), and space is O(1)
    public boolean kLengthApart(int[] nums, int k) {
        int last = -1;
        for (int i = 0; i < nums.length; i++){
            if (nums[i]==1){
                if (last != -1 && i-last-1 < k) return false;
                last = i;
            }
        }
        return true;
    }
}