// Source: https://leetcode.com/problems/redundant-connection/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-28
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.05 MB Beats 85.77%

/****************************************
* 
* In this problem, a tree is an undirected graph that is connected and has no cycles.
* You are given a graph that started as a tree with n nodes labeled from `1` to `n`, 
* _ with one additional edge added. The added edge has two different vertices chosen 
* _ from `1` to `n`, and was not an edge that already existed. The graph is 
* _ represented as an array `edges` of length `n` where `edges[i] = [ai, bi]` 
* _ indicates that there is an edge between nodes `a_i` and `b_i` in the graph.
* Return an edge that can be removed so that the resulting graph is a tree of `n` 
* _ nodes. If there are multiple answers, return the answer that occurs last in the input.
* 
* Example 1:
*   [Image: https://assets.leetcode.com/uploads/2021/05/02/reduntant1-1-graph.jpg]
* Input: edges = [[1,2],[1,3],[2,3]]
* Output: [2,3]
* 
* Example 2:
*   [Image: https://assets.leetcode.com/uploads/2021/05/02/reduntant1-2-graph.jpg]
* Input: edges = [[1,2],[2,3],[3,4],[1,4],[1,5]]
* Output: [1,4]
* 
* Constraints:
* • n == edges.length
* • 3 <= n <= 1000
* • edges[i].length == 2
* • 1 <= a_i < b_i <= edges.length
* • a_i != b_i
* • There are no repeated edges.
* • The given graph is connected.
* 
****************************************/

class Solution {
    // This solution uses Union-Find (Disjoint Set Union) with path compression  
    // and union by rank to detect cycles in the given graph. If an edge connects  
    // two nodes that already share the same root, it is redundant. The find()  
    // function ensures nearly constant time complexity using path compression.  
    // Time: O(N * α(N)), where α(N) is the inverse Ackermann function (~constant).  
    // Space: O(N) for the parent and rank arrays.  
    private int[] parent;
    private int[] rank;

    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        parent = new int[n + 1]; // Parent array
        rank = new int[n + 1];   // Rank array

        // Initialize each node as its own parent
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        // Process each edge and return the redundant one
        for (int[] edge : edges) {
            if (!union(edge[0], edge[1])) {
                return edge; // This edge creates a cycle
            }
        }
        
        return new int[0]; // This should never be reached
    }

    // Find function with path compression
    private int find(int node) {
        if (parent[node] != node) {
            parent[node] = find(parent[node]); // Path compression
        }
        return parent[node];
    }

    // Union function with union by rank
    private boolean union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);
        if (rootU == rootV) {
            return false; // They are already connected; this edge is redundant
        }
        if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }
        return true;
    }
}
