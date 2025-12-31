// Source: https://leetcode.com/problems/last-day-where-you-can-still-cross/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-30
// At the time of submission:
//   Runtime 11 ms Beats 100.00%
//   Memory 76.40 MB Beats 80.71%

/****************************************
* 
* There is a 1-based binary matrix where `0` represents land and `1` represents
* _ water. You are given integers `row` and `col` representing the number of
* _ rows and columns in the matrix, respectively.
* Initially on day `0`, the entire matrix is land. However, each day a new cell
* _ becomes flooded with water. You are given a 1-based 2D array `cells`, where
* _ `cells[i] = [r_i, c_i]` represents that on the `i^th` day, the cell on the
* _ `r_i^th` row and `c_i^th` column (1-based coordinates) will be covered with
* _ water (i.e., changed to `1`).
* You want to find the last day that it is possible to walk from the top to the
* _ bottom by only walking on land cells. You can start from any cell in the top
* _ row and end at any cell in the bottom row. You can only travel in the four
* _ cardinal directions (left, right, up, and down).
* Return the last day where it is possible to walk from the top to the bottom
* _ by only walking on land cells.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/07/27/1.png]
* Input: row = 2, col = 2, cells = [[1,1],[2,1],[1,2],[2,2]]
* Output: 2
* Explanation: The above image depicts how the matrix changes each day starting from day 0.
* The last day where it is possible to cross from top to bottom is on day 2.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/07/27/2.png]
* Input: row = 2, col = 2, cells = [[1,1],[1,2],[2,1],[2,2]]
* Output: 1
* Explanation: The above image depicts how the matrix changes each day starting from day 0.
* The last day where it is possible to cross from top to bottom is on day 1.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2021/07/27/3.png]
* Input: row = 3, col = 3, cells = [[1,2],[2,1],[3,3],[2,2],[1,1],[1,3],[2,3],[3,2],[3,1]]
* Output: 3
* Explanation: The above image depicts how the matrix changes each day starting from day 0.
* The last day where it is possible to cross from top to bottom is on day 3.
*
* Constraints:
* • `2 <= row, col <= 2 * 10^4`
* • `4 <= row * col <= 2 * 10^4`
* • `cells.length == row * col`
* • `1 <= r_i <= row`
* • `1 <= c_i <= col`
* • All the values of `cells` are unique.
* 
****************************************/

class Solution {
    // Use DSU on water cells to detect when flooding blocks all top-bottom paths.
    // Each day adds more water and unions adjacent water cells. Two virtual nodes
    // represent left and right boundaries; once they connect via water, land paths
    // from top to bottom no longer exist. Time: O(n * α(n)), Space: O(n).

    private int[] rank;
    private int[] parent;
    private int rowCount;
    private int colCount;

    // 8 directions: horizontal, vertical, and diagonal neighbors
    private static final int[][] DIRS = {
        {0,1},{0,-1},{1,0},{-1,0},{1,1},{-1,-1},{1,-1},{-1,1}
    };

    // Virtual DSU nodes representing leftmost and rightmost water columns
    private int leftBoundaryWaterId;
    private int rightBoundaryWaterId;

    public int latestDayToCross(int row, int col, int[][] cells) {
        this.rowCount = row;
        this.colCount = col;

        // +2 for virtual boundary nodes
        rank = new int[(row * col) + 2];
        parent = new int[(row * col) + 2];

        leftBoundaryWaterId = (row * col);
        rightBoundaryWaterId = (row * col) + 1;
        parent[leftBoundaryWaterId] = leftBoundaryWaterId;
        parent[rightBoundaryWaterId] = rightBoundaryWaterId;

        // Initialize each land cell to be its own parent
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                parent[toId(r, c)] = toId(r, c);
            }
        }

        boolean[][] isWater = new boolean[rowCount][colCount];

        // Flood cells day by day
        for (int day = 0; day < cells.length; day++) {
            int x = cells[day][0] - 1;
            int y = cells[day][1] - 1;

            isWater[x][y] = true;

            // Connect flooded cells in left/right boundary columns
            if (y == 0) {
                union(toId(x, y), leftBoundaryWaterId);
            } else if (y == colCount - 1) {
                union(toId(x, y), rightBoundaryWaterId);
            }

            // Connect adjacent flooded cells
            for (int[] d : DIRS) {
                int nx = x + d[0];
                int ny = y + d[1];
                if (inBounds(nx, ny) && isWater[nx][ny]) {
                    union(toId(x, y), toId(nx, ny));
                }
            }

            // Once boundaries connect via water, crossing becomes impossible
            if (find(leftBoundaryWaterId) == find(rightBoundaryWaterId)) {
                return day;  // this is the first day crossing fails
            }
        }

        return -1; // should never happen given constraints
    }

    private int toId(int r, int c) {
        return r * colCount + c;
    }

    private void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rank[rootA] == rank[rootB]) {
            rank[rootA]++;
            parent[rootB] = rootA;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootA] = rootB;
        }
    }

    private int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]]; // path compression
            x = parent[x];
        }
        return x;
    }

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < rowCount && c >= 0 && c < colCount;
    }
}
