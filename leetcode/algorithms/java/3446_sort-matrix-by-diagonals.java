// Source: https://leetcode.com/problems/sort-matrix-by-diagonals/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-27
// At the time of submission:
//   Runtime 3 ms Beats 94.50%
//   Memory 45.80 MB Beats 19.72%

/****************************************
* 
* You are given an `n x n` square matrix of integers `grid`.
* _ Return the matrix such that:
* • The diagonals in the bottom-left triangle (including the middle diagonal)
* __ are sorted in non-increasing order.
* • The diagonals in the top-right triangle are sorted in non-decreasing order.
*
* Example 1:
* Input: grid = [[1,7,3],[9,8,2],[4,5,6]]
* Output: [[8,2,3],[9,6,7],[4,5,1]]
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/12/29/4052example1drawio.png]
* The diagonals with a black arrow (bottom-left triangle) should be sorted in non-increasing order:
* • `[1, 8, 6]` becomes `[8, 6, 1]`.
* • `[9, 5]` and `[4]` remain unchanged.
* The diagonals with a blue arrow (top-right triangle) should be sorted in non-decreasing order:
* • `[7, 2]` becomes `[2, 7]`.
* • `[3]` remains unchanged.
*
* Example 2:
* Input: grid = [[0,1],[1,2]]
* Output: [[2,1],[1,0]]
* Explanation:
* [Image: https://assets.leetcode.com/uploads/2024/12/29/4052example2adrawio.png]
* The diagonals with a black arrow must be non-increasing, so `[0, 2]` is changed to `[2, 0]`. The other diagonals are already in the correct order.
*
* Example 3:
* Input: grid = [[1]]
* Output: [[1]]
* Explanation:
* Diagonals with exactly one element are already in order, so no changes are needed.
*
* Constraints:
* • grid.length == grid[i].length == n
* • 1 <= n <= 10
* • -10^5 <= grid[i][j] <= 10^5
* 
****************************************/

class Solution {
    // This solution sorts all diagonals of the matrix. Diagonals starting 
    // from the first column are sorted in non-increasing order, while those 
    // starting from the first row are sorted in non-decreasing order. Each 
    // diagonal is extracted into an array, sorted using Arrays.sort, and 
    // written back. Time complexity: O(m*n*log(min(m,n))). Space: O(min(m,n)).
    public int[][] sortMatrix(int[][] mat) {
        int numRows = mat.length;
        int numCols = mat[0].length;
        
        // Process diagonals starting from the first column (descending order)
        for (int row = 0; row < numRows; row++) {
            sortDiagonal(mat, row, 0, false);
        }

        // Process diagonals starting from the first row (ascending order)
        for (int col = 1; col < numCols; col++) {
            sortDiagonal(mat, 0, col, true);
        }

        return mat;
    }

    // Sort a diagonal starting at (row, col). 
    // 'increasing' determines if order is ascending (true) or descending (false).
    private void sortDiagonal(int[][] mat, int row, int col, boolean increasing) {
        int numRows = mat.length;
        int numCols = mat[0].length;
        
        int length = Math.min(numRows - row, numCols - col);
        int[] diagonal = new int[length];

        // Extract the diagonal into a temporary array
        for (int i = 0; i < length; i++) {
            diagonal[i] = mat[row + i][col + i];
        }

        // Sort the diagonal
        Arrays.sort(diagonal);

        // Reverse if non-increasing order is required
        if (!increasing) {
            reverse(diagonal);
        }

        // Write the sorted values back into the matrix
        for (int i = 0; i < length; i++) {
            mat[row + i][col + i] = diagonal[i];
        }
    }

    // Reverse array in-place
    private void reverse(int[] arr) {
        int i = 0, j = arr.length - 1;
        while (i < j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }
}
