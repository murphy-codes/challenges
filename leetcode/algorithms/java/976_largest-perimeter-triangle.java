// Source: https://leetcode.com/problems/largest-perimeter-triangle/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-26
// At the time of submission:
//   Runtime 7 ms Beats 99.49%
//   Memory 45.34 MB Beats 27.16%

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

// import java.util.Arrays;

class Solution {
    public int largestPerimeter(int[] nums) {
        // Step 1: Sort array to simplify triangle inequality checks
        Arrays.sort(nums);
        int n = nums.length;

        // Step 2: Traverse from largest side downwards
        for (int i = n - 1; i >= 2; i--) {
            int a = nums[i - 2];
            int b = nums[i - 1];
            int c = nums[i];

            // Step 3: Check triangle inequality
            if (a + b > c) {
                return a + b + c; // Found largest perimeter
            }
        }

        // Step 4: No valid triangle found
        return 0;
    }
}