// Source: https://leetcode.com/problems/equal-sum-grid-partition-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-25
// At the time of submission:
//   Runtime 44 ms Beats 94.12%
//   Memory 176.29 MB Beats 100.00%

/****************************************
* 
* You are given an `m x n` matrix `grid` of positive integers. Your task is to
* _ determine if it is possible to make either one horizontal or one vertical
* _ cut on the grid such that:
* • Each of the two resulting sections formed by the cut is non-empty.
* • The sum of elements in both sections is equal, or can be made equal by
* __ discounting at most one single cell in total (from either section).
* • If a cell is discounted, the rest of the section must remain connected.
* Return `true` if such a partition exists; otherwise return `false`.
* Note: A section is connected if every cell in it can be reached from any other
* _ cell by moving up, down, left, or right through other cells in the section.
*
* Example 1:
* Input: grid = [[1,4],[2,3]]
* Output: true
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2025/03/30/lc.jpeg]
* A horizontal cut after the first row gives sums 1 + 4 = 5 and 2 + 3 = 5, which are equal. Thus, the answer is true.
*
* Example 2:
* Input: grid = [[1,2],[3,4]]
* Output: true
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2025/04/01/chatgpt-image-apr-1-2025-at-05_28_12-pm.png]
* A vertical cut after the first column gives sums 1 + 3 = 4 and 2 + 4 = 6.
* By discounting 2 from the right section (6 - 2 = 4), both sections have equal sums and remain connected. Thus, the answer is true.
*
* Example 3:
* Input: grid = [[1,2,4],[2,3,5]]
* Output: false
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2025/04/01/chatgpt-image-apr-2-2025-at-02_50_29-am.png]
* A horizontal cut after the first row gives 1 + 2 + 4 = 7 and 2 + 3 + 5 = 10.
* By discounting 3 from the bottom section (10 - 3 = 7), both sections have equal sums, but they do not remain connected as it splits the bottom section into two parts ([2] and [5]). Thus, the answer is false.
*
* Example 4:
* Input: grid = [[4,1,8],[3,2,6]]
* Output: false
* Explanation:
* No valid cut exists, so the answer is false.
*
* Constraints:
* • `1 <= m == grid.length <= 10^5`
* • `1 <= n == grid[i].length <= 10^5`
* • `2 <= m * n <= 10^5`
* • `1 <= grid[i][j] <= 10^5`
* 
****************************************/

class Solution {
    // Precompute row and column prefix sums for fast partition queries.
    // Try all horizontal and vertical cuts and compare partition sums.
    // If unequal, compute diff and check if it exists in larger side.
    // Use frequency arrays for O(1) lookup and better performance.
    // Handle thin partitions via endpoint checks for connectivity.
    // Time: O(m * n); Space: O(m + n + maxVal).
    public boolean canPartitionGrid(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        long totalSum = 0;

        // Row sums and total
        long[] rowSum = new long[rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rowSum[i] += grid[i][j];
                totalSum += grid[i][j];
            }
        }

        // Prefix sums over rows
        long[] rowPrefix = new long[rows + 1];
        for (int i = 1; i <= rows; i++) {
            rowPrefix[i] = rowPrefix[i - 1] + rowSum[i - 1];
        }

        // Column sums
        long[] colSum = new long[cols];
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                colSum[j] += grid[i][j];
            }
        }

        // Prefix sums over columns
        long[] colPrefix = new long[cols + 1];
        for (int j = 1; j <= cols; j++) {
            colPrefix[j] = colPrefix[j - 1] + colSum[j - 1];
        }

        // Check exact equal partition (no removal)
        for (int k = 1; k < rows; k++) {
            long s1 = rowPrefix[k];
            if (s1 == totalSum - s1) return true;
        }
        for (int k = 1; k < cols; k++) {
            long s1 = colPrefix[k];
            if (s1 == totalSum - s1) return true;
        }

        final int MAXV = 100000;

        // Horizontal: top partition larger
        if (rows >= 2) {
            int[] freq = new int[MAXV + 1];

            for (int k = 1; k < rows; k++) {

                // Add row k-1 into top partition
                for (int j = 0; j < cols; j++) {
                    int val = grid[k - 1][j];
                    if (val <= MAXV) freq[val]++;
                }

                long top = rowPrefix[k];
                long bottom = totalSum - top;
                if (top <= bottom) continue;

                long diff = top - bottom;
                if (diff > MAXV) continue;

                int D = (int) diff;

                int h = k, w = cols;

                boolean can = false;

                if (h >= 2 && w >= 2) {
                    if (freq[D] > 0) can = true;
                } else {
                    int r1 = 0, r2 = k - 1;
                    int c1 = 0, c2 = cols - 1;
                    if (grid[r1][c1] == D || grid[r1][c2] == D ||
                        grid[r2][c1] == D || grid[r2][c2] == D) {
                        can = true;
                    }
                }

                if (can) return true;
            }
        }

        // Horizontal: bottom partition larger
        if (rows >= 2) {
            int[] freq = new int[MAXV + 1];

            for (int bottomHeight = 1; bottomHeight < rows; bottomHeight++) {

                int rowToAdd = rows - bottomHeight;

                // Add row into bottom partition
                for (int j = 0; j < cols; j++) {
                    int val = grid[rowToAdd][j];
                    if (val <= MAXV) freq[val]++;
                }

                int topRows = rows - bottomHeight;
                long top = rowPrefix[topRows];
                long bottom = totalSum - top;

                if (bottom <= top) continue;

                long diff = bottom - top;
                if (diff > MAXV) continue;

                int D = (int) diff;

                int h = bottomHeight;
                int w = cols;

                int r1 = rows - bottomHeight;
                int r2 = rows - 1;
                int c1 = 0;
                int c2 = cols - 1;

                boolean can = false;

                if (h >= 2 && w >= 2) {
                    if (freq[D] > 0) can = true;
                } else {
                    if (grid[r1][c1] == D || grid[r1][c2] == D ||
                        grid[r2][c1] == D || grid[r2][c2] == D) {
                        can = true;
                    }
                }

                if (can) return true;
            }
        }

        // Vertical: left partition larger
        if (cols >= 2) {
            int[] freq = new int[MAXV + 1];

            for (int k = 1; k < cols; k++) {

                int colToAdd = k - 1;

                // Add column into left partition
                for (int i = 0; i < rows; i++) {
                    int val = grid[i][colToAdd];
                    if (val <= MAXV) freq[val]++;
                }

                long left = colPrefix[k];
                long right = totalSum - left;

                if (left <= right) continue;

                long diff = left - right;
                if (diff > MAXV) continue;

                int D = (int) diff;

                int h = rows;
                int w = k;

                int r1 = 0;
                int r2 = rows - 1;
                int c1 = 0;
                int c2 = k - 1;

                boolean can = false;

                if (h >= 2 && w >= 2) {
                    if (freq[D] > 0) can = true;
                } else {
                    if (grid[r1][c1] == D || grid[r1][c2] == D ||
                        grid[r2][c1] == D || grid[r2][c2] == D) {
                        can = true;
                    }
                }

                if (can) return true;
            }
        }
        // Vertical: right partition larger
        if (cols >= 2) {
            int[] freq = new int[MAXV + 1];

            for (int rightWidth = 1; rightWidth < cols; rightWidth++) {

                int colToAdd = cols - rightWidth;

                // Add column into right partition
                for (int i = 0; i < rows; i++) {
                    int val = grid[i][colToAdd];
                    if (val <= MAXV) freq[val]++;
                }

                int leftCols = cols - rightWidth;
                long left = colPrefix[leftCols];
                long right = totalSum - left;

                if (right <= left) continue;

                long diff = right - left;
                if (diff > MAXV) continue;

                int D = (int) diff;

                int h = rows;
                int w = rightWidth;

                int r1 = 0;
                int r2 = rows - 1;
                int c1 = cols - rightWidth;
                int c2 = cols - 1;

                boolean can = false;

                if (h >= 2 && w >= 2) {
                    if (freq[D] > 0) can = true;
                } else {
                    if (grid[r1][c1] == D || grid[r1][c2] == D ||
                        grid[r2][c1] == D || grid[r2][c2] == D) {
                        can = true;
                    }
                }

                if (can) return true;
            }
        }

        return false;
    }
}