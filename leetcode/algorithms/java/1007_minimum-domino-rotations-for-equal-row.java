// Source: https://leetcode.com/problems/minimum-domino-rotations-for-equal-row/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-01
// At the time of submission:
//   Runtime 4 ms Beats 84.96%
//   Memory 50.96 MB Beats 11.87%

/****************************************
* 
* In a row of dominoes, `tops[i]` and `bottoms[i]` represent the top and
* _ bottom halves of the `i^th` domino. (A domino is a tile with two numbers
* _ from 1 to 6 - one on each half of the tile.)
* We may rotate the `i^th` domino, so that `tops[i]` and `bottoms[i]` swap values.
* Return the minimum number of rotations so that all the values in `tops` are
* _ the same, or all the values in `bottoms` are the same.
* If it cannot be done, return `-1`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/05/14/domino.png]
* Input: tops = [2,1,2,4,2,2], bottoms = [5,2,6,2,3,2]
* Output: 2
* Explanation:
* The first figure represents the dominoes as given by tops and bottoms: before we do any rotations.
* If we rotate the second and fourth dominoes, we can make every value in the top row equal to 2, as indicated by the second figure.
*
* Example 2:
* Input: tops = [3,5,1,2,3], bottoms = [3,6,3,3,4]
* Output: -1
* Explanation:
* In this case, it is not possible to rotate the dominoes to make one row of values equal.
*
* Constraints:
* • 2 <= tops.length <= 2 * 10^4
* • bottoms.length == tops.length
* • 1 <= tops[i], bottoms[i] <= 6
* 
****************************************/

class Solution {
    // Time: O(n), where n is the number of dominoes. 
    // We do up to two full passes over the arrays to check valid targets. 
    // Space: O(1), using constant space only for counters.
    public int minDominoRotations(int[] tops, int[] bottoms) {
        int rotations = check(tops[0], tops, bottoms);
        if (rotations != -1 || tops[0] == bottoms[0]) {
            return rotations;
        }
        return check(bottoms[0], tops, bottoms);
    }

    private int check(int target, int[] tops, int[] bottoms) {
        int topRotations = 0;
        int bottomRotations = 0;

        for (int i = 0; i < tops.length; i++) {
            if (tops[i] != target && bottoms[i] != target) {
                return -1; // Can't make this value uniform
            } else if (tops[i] != target) {
                topRotations++; // Need to rotate bottom up
            } else if (bottoms[i] != target) {
                bottomRotations++; // Need to rotate top down
            }
        }

        return Math.min(topRotations, bottomRotations);
    }
}