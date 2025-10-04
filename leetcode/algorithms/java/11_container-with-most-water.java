// Source: https://leetcode.com/problems/water-bottles-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-01
// At the time of submission:
//   Runtime 5 ms Beats 77.27%
//   Memory 57.97 MB Beats 56.57%

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
    // Two-pointer approach: start with widest container (ends of array) and move
    // the pointer at the shorter line inward, since width shrinks and only a taller
    // line can yield a larger area. At each step compute area = (j - i) * min(h[i], h[j]).
    // Time complexity: O(n), since each pointer moves at most once per step.
    // Space complexity: O(1), as only a few variables are used.
    public int maxArea(int[] height) {
        int maxWater = 0, i = 0, j = height.length-1;
        while (i < j) {
            maxWater = Math.max(maxWater, (j-i)*Math.min(height[i], height[j]));
            if (height[i] < height[j]) { i++; }
            else { j--; }
        }
        return maxWater;
    }
}