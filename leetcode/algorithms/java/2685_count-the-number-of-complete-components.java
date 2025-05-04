// Source: https://leetcode.com/problems/count-the-number-of-complete-components/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-21
// At the time of submission:
//   Runtime 4 ms Beats 98.91%
//   Memory 45.49 MB Beats 37.31%

/****************************************
* 
* You are given an integer `n`. There is an undirected graph with `n` vertices, numbered 
* _ from `0` to `n - 1`.
* You are given a 2D integer array `edges` where `edges[i] = [a_i, b_i]` denotes that there 
* _ exists an undirected edge connecting vertices `a_i` and `b_i`.
* Return the number of complete connected components of the graph.
* A connected component is a subgraph of a graph in which there exists a path between any two 
* _ vertices, and no vertex of the subgraph shares an edge with a vertex outside of the subgraph.
* A connected component is said to be complete if there exists an edge between every 
* _ pair of its vertices.
*
* Example 1:
* Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4]]
* Output: 3
* Explanation: From the picture above, one can see that all of the components of this graph are complete.
*
* Example 2:
* Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4],[3,5]]
* Output: 1
* Explanation: The component containing vertices 0, 1, and 2 is complete since there is an edge between every pair of two vertices. On the other hand, the component containing vertices 3, 4, and 5 is not complete since there is no edge between vertices 4 and 5. Thus, the number of complete components in this graph is 1.
*
* Constraints:
* • 1 <= n <= 50
* • 0 <= edges.length <= n * (n - 1) / 2
* • edges[i].length == 2
* • 0 <= ai, bi <= n - 1
* • ai != bi
* • There are no repeated edges.
* 
****************************************/

class Solution {
    // This solution uses Union-Find to group nodes into connected components.
    // Each component tracks its size and total edge count during union steps.
    // A component is complete if it has exactly size * (size - 1) / 2 edges.
    // Time Complexity: O(E * α(N)) for unions, O(N) for final check.
    // Space Complexity: O(N) for parent, size, and edge count arrays.

    private int[] parent;   // Disjoint set parents
    private int[] size;     // Size of each component
    private int[] edgeCount; // Number of edges in each component

    // Find the root of the component
    private int find(int node) {
        if (parent[node] != node) {
            parent[node] = find(parent[node]); // Path compression
        }
        return parent[node];
    }

    // Union two components and track edges
    private void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rootU == rootV) {
            edgeCount[rootU]++;  // Adding edge within same component
            return;
        }

        // Merge smaller component into larger one
        if (size[rootU] > size[rootV]) {
            parent[rootV] = rootU;
            size[rootU] += size[rootV];
            edgeCount[rootU] += edgeCount[rootV] + 1;
        } else {
            parent[rootU] = rootV;
            size[rootV] += size[rootU];
            edgeCount[rootV] += edgeCount[rootU] + 1;
        }
    }

    public int countCompleteComponents(int n, int[][] edges) {
        parent = new int[n];
        size = new int[n];
        edgeCount = new int[n];

        // Initialize each node as its own component
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        // Process each edge
        for (int[] edge : edges) {
            union(edge[0], edge[1]);
        }

        int completeComponents = 0;

        // A component is complete if it has exactly size*(size - 1)/2 edges
        for (int i = 0; i < n; i++) {
            if (parent[i] == i) {
                int expectedEdges = size[i] * (size[i] - 1) / 2;
                if (edgeCount[i] == expectedEdges) {
                    completeComponents++;
                }
            }
        }

        return completeComponents;
    }
}