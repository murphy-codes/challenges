// Source: https://leetcode.com/problems/trapping-rain-water/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-20
// At the time of submission:
//   Runtime 1 ms Beats 62.43%
//   Memory 46.49 MB Beats 49.41%

/****************************************
* 
* Given `n` non-negative integers representing an elevation map where the width of 
* _ each bar is `1`, compute how much water it can trap after raining.
* 
* Example 1:
*   [Image depicting a bar graph where the appropriate spots that could hold rainwater have been filled]
* Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
* Output: 6
* Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
* 
* Example 2:
* Input: height = [4,2,0,3,2,5]
* Output: 9
* 
* Constraints:
* • n == height.length
* • 1 <= n <= 2 * 10^4
* • 0 <= height[i] <= 10^5
* 
****************************************/

class Solution {
    // Use the two-pointer technique to calculate trapped water by moving inward from both ends
    // Track the max heights from the left and right to determine water levels at each position
    // Calculate water trapped by subtracting height from the respective max height
    // Time: O(n), Space: O(1)
    public int trap(int[] height) {
        if (height == null || height.length == 0) return 0;

        int left = 0, right = height.length - 1;
        int maxLeft = 0, maxRight = 0;
        int totalWater = 0;

        while (left <= right) {
            if (height[left] <= height[right]) {
                if (height[left] >= maxLeft) {
                    maxLeft = height[left];
                } else {
                    totalWater += maxLeft - height[left];
                }
                left++;
            } else {
                if (height[right] >= maxRight) {
                    maxRight = height[right];
                } else {
                    totalWater += maxRight - height[right];
                }
                right--;
            }
        }

        return totalWater;
    }
}
