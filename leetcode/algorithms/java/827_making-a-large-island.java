// Source: https://leetcode.com/problems/making-a-large-island/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-31

/****************************************
* 
* You are given a positive integer n representing the number of nodes in an undirected graph. The nodes are labeled from `1` to `n.
* 
* You are also given a 2D integer array `edges`, where `edges[i] = [a_i, b_i]` indicates that there is a bidirectional edge between nodes `a_i` and `b_i`. Notice that the given graph may be disconnected.
* 
* Divide the nodes of the graph into `m` groups (1-indexed) such that:
* 
* • Each node in the graph belongs to exactly one group.
* • For every pair of nodes in the graph that are connected by an edge `[a_i, b_i]`, if `a_i` belongs to the group with index `x`, and `b_i` belongs to the group with index `y`, then `|y - x| = 1`.
* 
* Return the maximum number of groups (i.e., maximum `m`) into which you can divide the nodes. Return `-1` if it is impossible to group the nodes with the given conditions.
* 
* Example 1:
*   [Image depicting 4 groups, with G1 having 5, which connects to 1 in G2, which then connects to both 2 & 4 in G3. Finally, 2 in G3 connects to both 3 & 6 in G4, but 4 in G3 only connects to 6 in G4 (thus 6 in G4 connects to both elements in G3). https://assets.leetcode.com/uploads/2022/10/13/example1.png]
* Input: n = 6, edges = [[1,2],[1,4],[1,5],[2,6],[2,3],[4,6]]
* Output: 4
* Explanation: As shown in the image we:
* - Add node 5 to the first group.
* - Add node 1 to the second group.
* - Add nodes 2 and 4 to the third group.
* - Add nodes 3 and 6 to the fourth group.
* We can see that every edge is satisfied.
* It can be shown that that if we create a fifth group and move any node from the third or fourth group to it, at least on of the edges will not be satisfied.
* 
* Example 2:
* Input: n = 3, edges = [[1,2],[2,3],[3,1]]
* Output: -1
* Explanation: If we add node 1 to the first group, node 2 to the second group, and node 3 to the third group to satisfy the first two edges, we can see that the third edge will not be satisfied.
* It can be shown that no grouping is possible.
* 
* Constraints:
* • 1 <= n <= 500
* • 1 <= edges.length <= 10^4
* • edges[i].length == 2
* • 1 <= a_i, b_i <= n
* • a_i != b_i
* • There is at most one edge between any pair of vertices.
* 
****************************************/

import java.util.HashMap;
import java.util.HashSet;

class Solution {
    // We use DFS to find and label all islands, storing their sizes in a hashmap.  
    // Then, we iterate over all 0s, checking adjacent unique islands to find the  
    // largest possible island size by flipping a single 0. If no 0s exist, we  
    // return the largest existing island. This runs in O(n²) time and space.  
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        HashMap<Integer, Integer> islandSize = new HashMap<>();
        int islandId = 2, maxIsland = 0;

        // Step 1: Identify islands and store their sizes
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    int size = dfs(grid, r, c, islandId);
                    islandSize.put(islandId++, size);
                    maxIsland = Math.max(maxIsland, size);
                }
            }
        }

        // Step 2: Try flipping each 0 and calculate the largest possible island
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 0) {
                    HashSet<Integer> seen = new HashSet<>();
                    int possibleSize = 1; // Count the flipped cell

                    // Check all 4 directions
                    for (int[] d : directions) {
                        int nr = r + d[0], nc = c + d[1];
                        if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] > 1) {
                            int id = grid[nr][nc];
                            if (seen.add(id)) { // Prevent double-counting
                                possibleSize += islandSize.get(id);
                            }
                        }
                    }
                    maxIsland = Math.max(maxIsland, possibleSize);
                }
            }
        }

        return maxIsland;
    }

    // Directions for up, down, left, right
    private static final int[][] directions = {{0,1},{1,0},{0,-1},{-1,0}};

    // DFS to mark an island and count its size
    private int dfs(int[][] grid, int r, int c, int id) {
        int n = grid.length, size = 1;
        grid[r][c] = id; // Mark with island ID

        for (int[] d : directions) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] == 1) {
                size += dfs(grid, nr, nc, id);
            }
        }
        return size;
    }
}
