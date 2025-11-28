// Source: https://leetcode.com/problems/maximum-number-of-k-divisible-components/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-27
// At the time of submission:
//   Runtime 25 ms Beats 78.49%
//   Memory 89.12 MB Beats 35.48%

/****************************************
* 
* There is an undirected tree with `n` nodes labeled from `0` to `n - 1`. You
* _ are given the integer `n` and a 2D integer array `edges` of length `n - 1`,
* _ where `edges[i] = [a_i, b_i]` indicates that there is an edge between nodes
* _ `a_i` and `b_i` in the tree.
* You are also given a 0-indexed integer array `values` of length `n`, where
* _ `values[i]` is the value associated with the `i^th` node, and an integer `k`.
* A valid split of the tree is obtained by removing any set of edges, possibly
* _ empty, from the tree such that the resulting components all have values that
* _ are divisible by `k`, where the value of a connected component is the sum
* _ of the values of its nodes.
* Return the maximum number of components in any valid split.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2023/08/07/example12-cropped2svg.jpg]
* Input: n = 5, edges = [[0,2],[1,2],[1,3],[2,4]], values = [1,8,1,4,4], k = 6
* Output: 2
* Explanation: We remove the edge connecting node 1 with 2. The resulting split is valid because:
* - The value of the component containing nodes 1 and 3 is values[1] + values[3] = 12.
* - The value of the component containing nodes 0, 2, and 4 is values[0] + values[2] + values[4] = 6.
* It can be shown that no other valid split has more than 2 connected components.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2023/08/07/example21svg-1.jpg]
* Input: n = 7, edges = [[0,1],[0,2],[1,3],[1,4],[2,5],[2,6]], values = [3,0,6,1,5,2,1], k = 3
* Output: 3
* Explanation: We remove the edge connecting node 0 with 2, and the edge connecting node 0 with 1. The resulting split is valid because:
* - The value of the component containing node 0 is values[0] = 3.
* - The value of the component containing nodes 2, 5, and 6 is values[2] + values[5] + values[6] = 9.
* - The value of the component containing nodes 1, 3, and 4 is values[1] + values[3] + values[4] = 6.
* It can be shown that no other valid split has more than 3 connected components.
*
* Constraints:
* • `1 <= n <= 3 * 10^4`
* • `edges.length == n - 1`
* • `edges[i].length == 2`
* • `0 <= a_i, b_i < n`
* • `values.length == n`
* • `0 <= values[i] <= 10^9`
* • `1 <= k <= 10^9`
* • Sum of `values` is divisible by `k`.
* • The input is generated such that `edges` represents a valid tree.
* 
****************************************/

class Solution {
    // DFS computes each subtree's sum modulo k in-place by accumulating child
    // remainders directly into the parent's value. When a node's subtree sum
    // is divisible by k, it forms its own component and is counted. Each node
    // is visited once, giving O(n) time and O(n) space for the adjacency list.

    int ans;  // counts number of subtree components divisible by k

    public int maxKDivisibleComponents(int n, int[][] edges,
                                       int[] values, int k) {

        // Build adjacency list
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            graph[u].add(v);
            graph[v].add(u);
        }

        ans = 0;
        dfs(values, k, graph, 0, -1);
        return ans;
    }

    private void dfs(int[] values, int k,
                     List<Integer>[] graph, int node, int parent) {

        // Visit all children except parent
        for (int child : graph[node]) {
            if (child != parent) {

                dfs(values, k, graph, child, node);

                // Add child's subtree remainder into this node
                values[node] += values[child];
                values[node] %= k;
            }
        }

        // Check if this node's full subtree sum is divisible by k
        values[node] %= k;
        if (values[node] == 0) {
            ans++;
        }
    }
}
