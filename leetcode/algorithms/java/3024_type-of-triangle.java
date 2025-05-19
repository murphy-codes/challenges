// Source: https://leetcode.com/problems/type-of-triangle/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-18
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.78 MB Beats 95.86%

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
    // Check triangle inequality to determine if a valid triangle can be formed.
    // If valid, compare side lengths directly to classify the triangle type:
    // all equal (equilateral), two equal (isosceles), else diff' (scalene).
    // Time complexity: O(1), constant-time checks on 3 fixed elements.
    // Space complexity: O(1), no extra memory allocated beyond input array.
    public String triangleType(int[] nums) {
        if (nums[0]+nums[1]<=nums[2] || nums[0]+nums[2]<=nums[1] || nums[1]+nums[2]<=nums[0]) return "none";
        else {
            if(nums[0]==nums[1] && nums[1]==nums[2]) return "equilateral";
            else if(nums[0]==nums[1] || nums[1]==nums[2] || nums[2]==nums[0]) return "isosceles";
            else return "scalene";
        }
    }
}