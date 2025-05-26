// Source: https://leetcode.com/problems/largest-color-value-in-a-directed-graph/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-26
// At the time of submission:
//   Runtime 52 ms Beats 97.68%
//   Memory 119.58 MB Beats 51.07%

/****************************************
* 
* There is a directed graph of `n` colored nodes and `m` edges. The nodes are
* _ numbered from `0` to `n - 1`.
* You are given a string `colors` where `colors[i]` is a lowercase English letter
* _ representing the color of the `i^th` node in this graph (0-indexed). You are
* _ also given a 2D array `edges` where `edges[j] = [a_j, b_j]` indicates that
* _ there is a directed edge from node `a_j` to node `b_j`.
* A valid path in the graph is a sequence of nodes
* _ `x_1 -> x_2 -> x_3 -> ... -> x_k` such that there is a directed edge from
* _ `x_i` to `x_i+1` for every `1 <= i < k`. The color value of the path is the
* _ number of nodes that are colored the most frequently occurring color along
* _ that path.
* Return the largest color value of any valid path in the given graph, or `-1` if
* _ the graph contains a cycle.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/04/21/leet1.png]
* Input: colors = "abaca", edges = [[0,1],[0,2],[2,3],[3,4]]
* Output: 3
* Explanation: The path 0 -> 2 -> 3 -> 4 contains 3 nodes that are colored "a" (red in the above image).
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/04/21/leet2.png]
* Input: colors = "a", edges = [[0,0]]
* Output: -1
* Explanation: There is a cycle from 0 to 0.
*
* Constraints:
* • n == colors.length
* • m == edges.length
* • 1 <= n <= 10^5
* • 0 <= m <= 10^5
* • `colors` consists of lowercase English letters.
* • 0 <= a_j, b_j < n
* 
****************************************/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    // Time Complexity: O(n + m), where n = number of nodes, m = number of edges
    // - Each node and edge is visited once during topological sort and DP updates.
    // Space Complexity: O(n * 26 + m)
    // - O(n * 26) for the color count DP table.
    // - O(m) for the adjacency list to store the graph structure.
    public int largestPathValue(String colors, int[][] edges) {
        int n = colors.length();
        int[][] dp = new int[n][26]; // dp[i][c] = max count of color c at node i
        int[] indegree = new int[n];
        ArrayList<Integer>[] graph = new ArrayList[n];
        
        // Initialize adjacency list
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        // Build graph and indegree array
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            indegree[edge[1]]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        
        // Initialize queue with all zero-indegree nodes
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int visited = 0;
        int maxColorValue = 0;
        
        while (!queue.isEmpty()) {
            int node = queue.poll();
            visited++;
            int colorIdx = colors.charAt(node) - 'a';
            
            // Increment count of the node's color
            dp[node][colorIdx]++;
            maxColorValue = Math.max(maxColorValue, dp[node][colorIdx]);
            
            for (int neighbor : graph[node]) {
                // Update neighbor's color counts using current node's counts
                for (int c = 0; c < 26; c++) {
                    dp[neighbor][c] = Math.max(dp[neighbor][c], dp[node][c]);
                }
                // Decrease indegree and enqueue if it becomes 0
                if (--indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        // If not all nodes were visited, there's a cycle
        return visited == n ? maxColorValue : -1;
    }
}
