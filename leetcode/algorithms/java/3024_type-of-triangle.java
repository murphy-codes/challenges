// Source: https://leetcode.com/problems/type-of-triangle/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-18
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.15 MB Beats 43.93%

/****************************************
* 
* You are given a 0-indexed integer array nums of size 3 which can form the
* _ sides of a triangle.
* • A triangle is called equilateral if it has all sides of equal length.
* • A triangle is called isosceles if it has exactly two sides of equal length.
* • A triangle is called scalene if all its sides are of different lengths.
* Return a string representing the type of triangle that can be formed or
* _ "none" if it cannot form a triangle.
*
* Example 1:
* Input: nums = [3,3,3]
* Output: "equilateral"
* Explanation: Since all the sides are of equal length, therefore, it will form an equilateral triangle.
*
* Example 2:
* Input: nums = [3,4,5]
* Output: "scalene"
* Explanation:
* nums[0] + nums[1] = 3 + 4 = 7, which is greater than nums[2] = 5.
* nums[0] + nums[2] = 3 + 5 = 8, which is greater than nums[1] = 4.
* nums[1] + nums[2] = 4 + 5 = 9, which is greater than nums[0] = 3.
* Since the sum of the two sides is greater than the third side for all three cases, therefore, it can form a triangle.
* As all the sides are of different lengths, it will form a scalene triangle.
*
* Constraints:
* • nums.length == 3
* • 1 <= nums[i] <= 100
* 
****************************************/

class Solution {
    // First, check if the sides satisfy triangle inequality. If not, return "none".
    // Then compare sides directly: all equal means equilateral, two equal is isosceles,
    // and all distinct means scalene. No extra data structures used.
    // Time complexity: O(1), fixed comparisons on 3 elements.
    // Space complexity: O(1), no additional memory allocated.
    public String triangleType(int[] nums) {
        int a = nums[0], b = nums[1], c = nums[2];
        if (a + b <= c || a + c <= b || b + c <= a) return "none";
        if (a == b && b == c) return "equilateral";
        if (a == b || b == c || a == c) return "isosceles";
        return "scalene";
    }
}