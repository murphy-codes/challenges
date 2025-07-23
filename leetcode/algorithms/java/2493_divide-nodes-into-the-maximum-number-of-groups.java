// Source: https://leetcode.com/problems/divide-nodes-into-the-maximum-number-of-groups/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-29
// At the time of submission:
//   Runtime 24 ms Beats 99.07%
//   Memory 46.14 MB Beats 97.22%

/****************************************
* 
* You are given a positive integer `n` representing the number of nodes in an 
* _ undirected graph. The nodes are labeled from `1` to `n.
* You are also given a 2D integer array `edges`, where `edges[i] = [a_i, b_i]` 
* _ indicates that there is a bidirectional edge between nodes `a_i` and `b_i`. 
* _ Notice that the given graph may be disconnected.
* Divide the nodes of the graph into `m` groups (1-indexed) such that:
* _ • Each node in the graph belongs to exactly one group.
* _ • For every pair of nodes in the graph that are connected by an edge 
* __ `[a_i, b_i]`, if `a_i` belongs to the group with index `x`, and `b_i` 
* __ belongs to the group with index `y`, then `|y - x| = 1`.
* Return the maximum number of groups (i.e., maximum `m`) into which you can 
* _ divide the nodes. Return `-1` if it is impossible to group the nodes with 
* _ the given conditions.
* 
* Example 1:
*   [Image: https://assets.leetcode.com/uploads/2022/10/13/example1.png]
* Input: n = 6, edges = [[1,2],[1,4],[1,5],[2,6],[2,3],[4,6]]
* Output: 4
* Explanation: As shown in the image we:
* - Add node 5 to the first group.
* - Add node 1 to the second group.
* - Add nodes 2 and 4 to the third group.
* - Add nodes 3 and 6 to the fourth group.
* We can see that every edge is satisfied.
* It can be shown that that if we create a fifth group and move any node from the 
* _ third or fourth group to it, at least on of the edges will not be satisfied.
* 
* Example 2:
* Input: n = 3, edges = [[1,2],[2,3],[3,1]]
* Output: -1
* Explanation: If we add node 1 to the first group, node 2 to the second group, 
* _ and node 3 to the third group to satisfy the first two edges, we can see that 
* _ the third edge will not be satisfied.
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

class Solution {
    // For each component, this code finds a starting node and performs BFS
    // to assign "layers" to each node while ensuring the graph is bipartite.
    // It tracks the maximum depth (number of layers) reached in each component,
    // summing those to return the total number of "magnificent sets".
    // Time: O(n + m * d), Space: O(n + m) where m = edges, d = max node degree.
    public int magnificentSets(int n, int[][] edges) {
        List<Integer>[] adj = new ArrayList[n + 1];
        int[] degree = new int[n + 1];
        
        // Build adjacency list and track degree of each node
        for (int i = 0; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
            degree[edge[0]]++;
            degree[edge[1]]++;
        }

        int[] comp = new int[n + 1]; // Component ID for each node
        int component = 1;
        int[][] res = new int[n + 1][2]; // res[c][0]=max depth, res[c][1]=min degree in component

        for (int i = 1; i <= n; i++) {
            // Skip if already visited and not the lowest-degree node in component
            if (comp[i] != 0 && res[comp[i]][1] < degree[i]) continue;

            // Assign new component if unvisited
            if (comp[i] == 0) comp[i] = component++;

            res[comp[i]][1] = degree[i];

            int[] groups = new int[n + 1]; // Layer assignments
            Queue<Integer> q = new LinkedList<>();
            groups[i] = 1; // Start from layer 1
            q.offer(i);
            int maxLayer = 0;

            // BFS to assign groups
            while (!q.isEmpty()) {
                int size = q.size();
                for (int j = 0; j < size; j++) {
                    int node = q.poll();
                    comp[node] = comp[i];
                    maxLayer = Math.max(maxLayer, groups[node]);

                    for (int neighbor : adj[node]) {
                        if (groups[neighbor] == 0) {
                            groups[neighbor] = groups[node] + 1;
                            q.offer(neighbor);
                        } else if (Math.abs(groups[neighbor] - groups[node]) != 1) {
                            return -1; // Not bipartite
                        }
                    }
                }
            }
            res[comp[i]][0] = Math.max(res[comp[i]][0], maxLayer);
        }

        int totalGroups = 0;
        for (int[] r : res) {
            totalGroups += r[0];
        }
        return totalGroups;
    }
}
