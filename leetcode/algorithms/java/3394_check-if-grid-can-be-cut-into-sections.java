// Source: https://leetcode.com/problems/check-if-grid-can-be-cut-into-sections/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-25
// At the time of submission:
//   Runtime 85 ms Beats 92.11%
//   Memory 118.03 MB Beats 75.00%

/****************************************
* 
* You are given an integer `n` representing the dimensions of an `n x n` grid, with the origin at
* the bottom-left corner of the grid. You are also given a 2D array of coordinates `rectangles`,
* where `rectangles[i]` is in the form `[start_x, start_y, end_x, end_y]`, representing a rectangle
* on the grid. Each rectangle is defined as follows:
* • (start_x, start_y): The bottom-left corner of the rectangle.
* • (end_x, end_y): The top-right corner of the rectangle.
* Note that the rectangles do not overlap. Your task is to determine if it is possible to make
* either two horizontal or two vertical cuts on the grid such that:
* • Each of the three resulting sections formed by the cuts contains at least one rectangle.
* • Every rectangle belongs to exactly one section.
* Return `true` if such cuts can be made; otherwise, return `false`.
*
* Example 1:
* Input: n = 5, rectangles = [[1,0,5,2],[0,2,2,4],[3,2,5,3],[0,4,4,5]]
* Output: true
* Explanation:
* [image: https://assets.leetcode.com/uploads/2024/10/23/tt1drawio.png]
* The grid is shown in the diagram. We can make horizontal cuts at y = 2 and y = 4. Hence, output is true.
*
* Example 2:
* Input: n = 4, rectangles = [[0,0,1,1],[2,0,3,4],[0,2,2,3],[3,0,4,3]]
* Output: true
* Explanation:
* [image: https://assets.leetcode.com/uploads/2024/10/23/tc2drawio.png]
* We can make vertical cuts at x = 2 and x = 3. Hence, output is true.
*
* Example 3:
* Input: n = 4, rectangles = [[0,2,2,4],[1,0,3,2],[2,2,3,4],[3,0,4,2],[3,2,4,4]]
* Output: false
* Explanation:
* We cannot make two horizontal or two vertical cuts that satisfy the conditions. Hence, output is false.
*
* Constraints:
* • 3 <= n <= 10^9
* • 3 <= rectangles.length <= 10^5
* • 0 <= rectangles[i][0] < rectangles[i][2] <= n
* • 0 <= rectangles[i][1] < rectangles[i][3] <= n
* • No two rectangles overlap.
* 
****************************************/

import java.util.Arrays;

class Solution {
    // This solution checks if we can make two valid cuts (horizontal or vertical)
    // to divide the set of rectangles into three sections. It sorts the rectangles
    // by their starting coordinate, tracks the furthest end seen, and counts gaps.
    // Time complexity: O(nlogn) due to sorting, followed by O(n) for scanning,
    // resulting in O(nlogn). Space complexity: O(logn) for sorting (Java's QuickSort).
    public boolean checkValidCuts(int n, int[][] rectangles) {
        return checkCuts(rectangles, 0) || checkCuts(rectangles, 1);
    }

    private boolean checkCuts(int[][] rectangles, int dim) {
        int gapCount = 0;
        
        // Sort rectangles by their starting coordinate in the given dimension
        Arrays.sort(rectangles, (a, b) -> Integer.compare(a[dim], b[dim]));

        // Track the furthest ending coordinate seen so far
        int furthestEnd = rectangles[0][dim + 2];

        for (int i = 1; i < rectangles.length; i++) {
            int[] rect = rectangles[i];

            // If a gap exists where a cut can be made
            if (furthestEnd <= rect[dim]) {
                gapCount++;
            }

            // Extend the furthest end seen so far
            furthestEnd = Math.max(furthestEnd, rect[dim + 2]);
        }

        return gapCount >= 2;  // At least 2 gaps needed to divide into 3 sections
    }
}
