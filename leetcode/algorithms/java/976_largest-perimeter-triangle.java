// Source: https://leetcode.com/problems/largest-perimeter-triangle/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-26
// At the time of submission:
//   Runtime 2 ms Beats 99.69%
//   Memory 44.99 MB Beats 84.13%

/****************************************
* 
* Given an integer array `nums`, return the largest perimeter of a triangle with
* _ a non-zero area, formed from three of these lengths. If it is impossible to
* _ form any triangle of a non-zero area, return `0`.
*
* Example 1:
* [Image: https://s3-lc-upload.s3.amazonaws.com/uploads/2018/04/04/1027.png]
* Input: nums = [2,1,2]
* Output: 5
* Explanation: You can form a triangle with three side lengths: 1, 2, and 2.
*
* Example 2:
* Input: nums = [1,2,1,10]
* Output: 0
* Explanation:
* You cannot use the side lengths 1, 1, and 2 to form a triangle.
* You cannot use the side lengths 1, 1, and 10 to form a triangle.
* You cannot use the side lengths 1, 2, and 10 to form a triangle.
* As we cannot use any three side lengths to form a triangle of non-zero area, we return 0.
*
* Constraints:
* • `3 <= nums.length <= 10^4`
* • `1 <= nums[i] <= 10^6`
* 
****************************************/

class Solution {
    // This solution uses a partial selection-sort strategy. Instead of fully sorting,
    // it repeatedly selects the max element from the unsorted prefix and swaps it into
    // place, ensuring the largest values are positioned for triangle checks. This can
    // save time since only top values matter. Worst-case Time: O(n^2); Space: O(1).

    public int largestPerimeter(int[] nums) {
        int n = nums.length;

        // Step 1: Bring the two largest elements to the last two positions
        selectAndSwapMax(nums, n - 1);
        selectAndSwapMax(nums, n - 2);

        // Step 2: Iterate backward to test possible triangles
        for (int i = n - 1; i >= 2; i--) {
            // Ensure the (i-2)-th element is the max among nums[0..i-2]
            selectAndSwapMax(nums, i - 2);

            // Check triangle inequality
            if (nums[i] < nums[i - 1] + nums[i - 2]) {
                return nums[i] + nums[i - 1] + nums[i - 2];
            }
        }

        // No valid triangle found
        return 0;
    }

    // Finds the maximum element in nums[0..endIdx] and swaps it into nums[endIdx]
    private void selectAndSwapMax(int[] nums, int endIdx) {
        int maxVal = nums[0];
        int maxIdx = 0;

        // Find maximum in the range
        for (int i = 1; i <= endIdx; i++) {
            if (nums[i] > maxVal) {
                maxVal = nums[i];
                maxIdx = i;
            }
        }

        // Swap into correct position
        int temp = nums[endIdx];
        nums[endIdx] = maxVal;
        nums[maxIdx] = temp;
    }
}
