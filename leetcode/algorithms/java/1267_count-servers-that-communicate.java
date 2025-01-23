// Source: https://leetcode.com/problems/map-of-highest-peak/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-22

/****************************************
* 
* You are given a map of a server center, represented as a `m * n` integer matrix grid, where 1 means that on that cell there is a server and 0 means that it is no server. Two servers are said to communicate if they are on the same row or on the same column.
* 
* Return the number of servers that communicate with any other server.
* 
* Example 1:
*   [Image depicting a 2x2 grid, with two red computers at opposite corners indicating they can't communicate]
* Input: grid = [[1,0],[0,1]]
* Output: 0
* Explanation: No servers can communicate with others.
* 
* Example 2:
*   [Image depicting a 2x2 grid, with three blue computers indicating they can all communicate]
* Input: grid = [[1,0],[1,1]]
* Output: 3
* Explanation: All three servers can communicate with at least one other server.
* 
* Example 3:
*   [Image depicting a 4x4 grid, with 4 blue computers & 1 red, indicating which can communicate]
* Input: grid = [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]
* Output: 4
* Explanation: The two servers in the first row can communicate with each other. The two servers in the third column can communicate with each other. The server at right bottom corner can't communicate with any other server.
* 
* Constraints:
* • m == grid.length
* • n == grid[i].length
* • 1 <= m <= 250
* • 1 <= n <= 250
* • grid[i][j] == 0 or 1
* 
****************************************/

class Solution {
    // Count servers in each row and column using two passes over the grid
    // First pass: Populate row and column counts for all servers
    // Second pass: Identify servers that communicate (rowCount > 1 or colCount > 1)
    // Efficient two-pass approach ensures no double-counting
    // Time: O(m * n), Space: O(m + n)
    public int countServers(int[][] grid) {
        int m = grid.length;    // Number of rows
        int n = grid[0].length; // Number of columns
        
        // Arrays to count the number of servers in each row and column
        int[] rowCount = new int[m];
        int[] colCount = new int[n];
        
        // First pass: Count servers in each row and column
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    rowCount[i]++;
                    colCount[j]++;
                }
            }
        }
        
        // Second pass: Count communicable servers
        int totalServers = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && (rowCount[i] > 1 || colCount[j] > 1)) {
                    totalServers++;
                }
            }
        }
        
        return totalServers;
    }
}
