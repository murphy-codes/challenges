// Source: https://leetcode.com/problems/sort-colors/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-16
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.87 MB Beats 81.46%

/****************************************
* 
* Given an array `nums` with `n` objects colored red, white, or blue, sort them
* _ in-place so that objects of the same color are adjacent, with the colors in
* _ the order red, white, and blue.
* We will use the integers `0`, `1`, and `2` to represent the colors
* _ red, white, and blue, respectively.
* You must solve this problem without using the library's sort function.
*
* Example 1:
* Input: nums = [2,0,2,1,1,0]
* Output: [0,0,1,1,2,2]
*
* Example 2:
* Input: nums = [2,0,1]
* Output: [0,1,2]
*
* Constraints:
* • n == nums.length
* • 1 <= n <= 300
* • nums[i] is either 0, 1, or 2.
*
* Follow up: Could you come up with a one-pass algorithm using
* _ only constant extra space?
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Count the number of 0s, 1s, and 2s in a first pass (O(n))
    // Then overwrite the input array in order using these counts (O(n))
    // Time complexity: O(n); Space complexity: O(1) extra space
    // This is a two-pass, constant-space solution;
    public void sortColors(int[] nums) {
        int[] colors = new int[3];
        for (int c : nums) {
            colors[c]+=1;
        }
        int i = 0;
        while (i < colors[0]) {
            nums[i] = 0;
            i++;
        }
        while (i < (colors[0] + colors[1])) {
            nums[i] = 1;
            i++;
        }
        while (i < nums.length) {
            nums[i] = 2;
            i++;
        }
    }
}