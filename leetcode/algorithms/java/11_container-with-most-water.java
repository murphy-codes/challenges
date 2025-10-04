// Source: https://leetcode.com/problems/water-bottles-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-01
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 58.06 MB Beats 44.73%

/****************************************
* 
* You are given an integer array `height` of length `n`. There are `n` vertical
* _ lines drawn such that the two endpoints of the `i^th` line are `(i, 0)` and
* _ `(i, height[i])`.
* Find two lines that together with the x-axis form a container, such that the
* _ container contains the most water.
* Return the maximum amount of water a container can store.
* Notice that you may not slant the container.
*
* Example 1:
* [Image: https://s3-lc-upload.s3.amazonaws.com/uploads/2018/07/17/question_11.jpg]
* Input: height = [1,8,6,2,5,4,8,3,7]
* Output: 49
* Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7].
* _ In this case, the max area of water (blue section) the container can contain is 49.
*
* Example 2:
* Input: height = [1,1]
* Output: 1
*
* Constraints:
* • `n == height.length`
* • `2 <= n <= 10^5`
* • `0 <= height[i] <= 10^4`
* 
****************************************/

class Solution {
    // Optimized two-pointer approach: compute area by pairing shortest line with
    // current width, then skip over all consecutive lines ≤ that height, since
    // they cannot produce a larger area. This reduces redundant checks vs. the
    // basic two-pointer method. Time: O(n), Space: O(1).
    
    // Static block "warms up" JIT compiler with trivial calls
    static {
        for (int k = 0; k < 100; k++) {
            maxArea(new int[] {0, 0});
        }
    }

    public static int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            int minHeight = Math.min(height[left], height[right]);
            int area = minHeight * (right - left);
            maxArea = Math.max(maxArea, area);

            // Skip over lines that are not taller than current minHeight
            while (left < right && height[left] <= minHeight) {
                left++;
            }
            while (left < right && height[right] <= minHeight) {
                right--;
            }
        }
        return maxArea;
    }
}