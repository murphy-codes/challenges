// Source: https://leetcode.com/problems/divide-nodes-into-the-maximum-number-of-groups/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-29

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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    // This solution finds the max number of groups that can be formed in a graph.  
    // It first checks if each component is bipartite using BFS. If not, return -1.  
    // For each bipartite component, it finds the max BFS depth from the best node.  
    // Time complexity: O(n + m) due to BFS traversal and adjacency list operations.  
    // Space complexity: O(n + m) for graph storage, color tracking, and BFS queues.  
    public int magnificentSets(int n, int[][] edges) {
        // Step 1: Build adjacency list
        List<Integer>[] graph = new ArrayList[n + 1]; // 1-based index
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        // Step 2: Check for bipartiteness and process components
        int[] color = new int[n + 1]; // 0 = unvisited, 1 & -1 = colors
        Arrays.fill(color, 0);
        int totalMaxGroups = 0;

        for (int i = 1; i <= n; i++) {
            if (color[i] == 0) { // Unvisited component
                List<Integer> component = new ArrayList<>();
                if (!isBipartite(graph, i, color, component)) {
                    return -1; // Odd cycle found, impossible to group
                }
                // Step 3: Get max BFS depth for this component
                totalMaxGroups += getMaxDepth(graph, component);
            }
        }

        return totalMaxGroups;
    }

    // BFS-based bipartiteness check & component collection
    private boolean isBipartite(List<Integer>[] graph, int start, int[] color, List<Integer> component) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        color[start] = 1; // Start coloring with 1
        component.add(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph[node]) {
                if (color[neighbor] == 0) { // Unvisited, assign opposite color
                    color[neighbor] = -color[node];
                    queue.offer(neighbor);
                    component.add(neighbor);
                } else if (color[neighbor] == color[node]) { 
                    return false; // Odd-length cycle detected
                }
            }
        }
        return true;
    }

    // BFS to find the longest path from any node in a component
    private int getMaxDepth(List<Integer>[] graph, List<Integer> component) {
        int maxDepth = 0;
        for (int node : component) {
            maxDepth = Math.max(maxDepth, bfsDepth(graph, node));
        }
        return maxDepth;
    }

    // Perform BFS to determine the longest path from a starting node
    private int bfsDepth(List<Integer>[] graph, int start) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);
        int depth = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            depth++; // Each level represents a group
            for (int i = 0; i < size; i++) {
                int node = queue.poll();
                for (int neighbor : graph[node]) {
                    if (!visited.contains(neighbor)) {
                        queue.offer(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
        }

        return depth;
    }
}
