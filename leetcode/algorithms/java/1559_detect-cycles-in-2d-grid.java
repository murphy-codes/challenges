// Source: https://leetcode.com/problems/detect-cycles-in-2d-grid/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-26
// At the time of submission:
//   Runtime 6 ms Beats 100.00%
//   Memory 123.99 MB Beats 91.78%

/****************************************
* 
* Given a 2D array of characters `grid` of size `m x n`, you need to find if
* _ there exists any cycle consisting of the same value in `grid`.
* A cycle is a path of length 4 or more in the grid that starts and ends at
* _ the same cell. From a given cell, you can move to one of the cells adjacent
* _ to it - in one of the four directions (up, down, left, or right), if it
* _ has the same value of the current cell.
* Also, you cannot move to the cell that you visited in your last move. For
* _ example, the cycle `(1, 1) -> (1, 2) -> (1, 1)` is invalid because from
* _ `(1, 2)` we visited `(1, 1)` which was the last visited cell.
* Return `true` if any cycle of the same value exists in grid, otherwise,
* _ return `false`.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2020/07/15/1.png]
* Input: grid = [["a","a","a","a"],["a","b","b","a"],["a","b","b","a"],["a","a","a","a"]]
* Output: true
* Explanation: There are two valid cycles shown in different colors in the image below:
* [Image: https://assets.leetcode.com/uploads/2020/07/15/11.png]
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2020/07/15/22.png]
* Input: grid = [["c","c","c","a"],["c","d","c","c"],["c","c","e","c"],["f","c","c","c"]]
* Output: true
* Explanation: There is only one valid cycle highlighted in the image below:
* [Image: https://assets.leetcode.com/uploads/2020/07/15/2.png]
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2020/07/15/3.png]
* Input: grid = [["a","b","b"],["b","z","b"],["b","b","a"]]
* Output: false
*
* Constraints:
* • `m == grid.length`
* • `n == grid[i].length`
* • `1 <= m, n <= 500`
* • `grid` consists only of lowercase English letters.
* 
****************************************/

class Solution {
    // Treat each cell as a graph node and connect adjacent cells
    // that contain the same character using Union-Find (DSU).
    // If two same-character neighbors are already in the same set,
    // connecting them again means a cycle exists.
    // Time: O(m * n * α(mn)), Space: O(m n)
    public boolean containsCycle(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        // parent[i] stores the representative (root) of node i
        int[] parent = new int[rows * cols];

        // Initially, every cell is its own parent
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int id = row * cols + col;
                parent[id] = id;
            }
        }

        // Only check right and down neighbors to avoid duplicate edges
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int currentId = row * cols + col;
                char currentChar = grid[row][col];

                // Check downward neighbor
                if (row + 1 < rows && grid[row + 1][col] == currentChar) {
                    int rootA = find(currentId, parent);
                    int rootB = find((row + 1) * cols + col, parent);

                    // Already connected → cycle found
                    if (rootA == rootB) {
                        return true;
                    }

                    // Union the two groups
                    parent[rootA] = rootB;
                }

                // Check right neighbor
                if (col + 1 < cols && grid[row][col + 1] == currentChar) {
                    int rootA = find(currentId, parent);
                    int rootB = find(row * cols + col + 1, parent);

                    // Already connected → cycle found
                    if (rootA == rootB) {
                        return true;
                    }

                    // Union the two groups
                    parent[rootA] = rootB;
                }
            }
        }

        return false;
    }

    private int find(int node, int[] parent) {
        if (node == parent[node]) {
            return node;
        }

        // Path compression
        parent[node] = find(parent[node], parent);
        return parent[node];
    }
}