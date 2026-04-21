// Source: https://leetcode.com/problems/two-furthest-houses-with-different-colors/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-19
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.25 MB Beats 64.71%

/****************************************
* 
* There are `n` houses evenly lined up on the street, and each house is
* _ beautifully painted. You are given a 0-indexed integer array `colors` of
* _ length `n`, where `colors[i]` represents the color of the `i^th` house.
* Return the maximum distance between two houses with different colors.
* The distance between the `i^th` and `j^th` houses is `abs(i - j)`, where
* _ `abs(x)` is the absolute value of `x`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/10/31/eg1.png]
* Input: colors = [1,1,1,6,1,1,1]
* Output: 3
* Explanation: In the above image, color 1 is blue, and color 6 is red.
* The furthest two houses with different colors are house 0 and house 3.
* House 0 has color 1, and house 3 has color 6. The distance between them is abs(0 - 3) = 3.
* Note that houses 3 and 6 can also produce the optimal answer.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/10/31/eg2.png]
* Input: colors = [1,8,3,8,3]
* Output: 4
* Explanation: In the above image, color 1 is blue, color 8 is yellow, and color 3 is green.
* The furthest two houses with different colors are house 0 and house 4.
* House 0 has color 1, and house 4 has color 3. The distance between them is abs(0 - 4) = 4.
*
* Example 3:
* Input: colors = [0,1]
* Output: 1
* Explanation: The furthest two houses with different colors are house 0 and house 1.
* House 0 has color 0, and house 1 has color 1. The distance between them is abs(0 - 1) = 1.
*
* Constraints:
* • `n == colors.length`
* • `2 <= n <= 100`
* • `0 <= colors[i] <= 100`
* • Test data are generated such that at least two houses have different colors.
* 
****************************************/

class Solution {
    // The max distance must involve either the first or last house.
    // Scan from the right to find the farthest house differing from colors[0].
    // Then scan from the left to find the farthest differing from colors[n-1].
    // Take the maximum of the two distances.
    // Time: O(n), Space: O(1).
    public int maxDistance(int[] colors) {
        int n = colors.length;

        int maxDist = 0;

        // Compare leftmost with all from right
        for (int j = n - 1; j >= 0; j--) {
            if (colors[j] != colors[0]) {
                maxDist = j;
                break;
            }
        }

        // Compare rightmost with all from left
        for (int i = 0; i < n; i++) {
            if (colors[i] != colors[n - 1]) {
                maxDist = Math.max(maxDist, n - 1 - i);
                break;
            }
        }

        return maxDist;
    }
}