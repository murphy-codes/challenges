// Source: https://leetcode.com/problems/count-negative-numbers-in-a-sorted-matrix/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-28
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 47.09 MB Beats 21.39%

/****************************************
* 
* Given a `m x n` matrix `grid` which is sorted in non-increasing order both
* _ row-wise and column-wise, return the number of negative numbers in `grid`.
*
* Example 1:
* Input: grid = [[4,3,2,-1],[3,2,1,-1],[1,1,-1,-2],[-1,-1,-2,-3]]
* Output: 8
* Explanation: There are 8 negatives number in the matrix.
*
* Example 2:
* Input: grid = [[3,2],[1,0]]
* Output: 0
*
* Constraints:
* • `m == grid.length`
* • `n == grid[i].length`
* • `1 <= m, n <= 100`
* • `-100 <= grid[i][j] <= 100`
*
* Follow up: Could you find an O(n + m) solution?
* 
****************************************/

class Solution {
    // Traverse rows while tracking the rightmost non-negative column seen so far.
    // Since rows and columns are sorted in non-increasing order, once we find a
    // non-negative in a row, everything to its right is negative and can be counted
    // at once. The pointer only moves left, giving an O(m + n) time solution.
    // Space usage is O(1) as we store only counters and a column pointer.
    public int countNegatives(int[][] grid) {
        int neg = 0;
        int m = grid.length, n = grid[0].length;
        int pointer = n-1;
        for (int i = 0; i < m; i++) {
            if (grid[i][0] < 0) {
                neg += (m-i) * n;
                break;
            }
            for (int j = pointer; j >= 0; j--) {
                if (grid[i][j] >= 0) {
                    pointer = j;
                    neg += n - pointer - 1;
                    break;
                }
            }
        }
        return neg;
    }
}