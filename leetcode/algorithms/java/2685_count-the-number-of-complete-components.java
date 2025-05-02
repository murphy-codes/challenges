// Source: https://leetcode.com/problems/count-the-number-of-complete-components/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-21

/****************************************
* 
* You are given an integer `n`. There is an undirected graph with `n` vertices, numbered from `0` to `n - 1`.
* You are given a 2D integer array `edges` where `edges[i] = [a_i, b_i]` denotes that there exists an
* undirected edge connecting vertices `a_i` and `b_i`.
* Return the number of complete connected components of the graph.
* A connected component is a subgraph of a graph in which there exists a path between any two vertices,
* and no vertex of the subgraph shares an edge with a vertex outside of the subgraph.
* A connected component is said to be complete if there exists an edge between every pair of its vertices.
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

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Solution {
    // This solution finds all connected components using DFS, then checks
    // if each component is complete. A complete component with k nodes
    // must have exactly k * (k - 1) / 2 edges. We store the graph as an
    // adjacency list and use a visited array to avoid redundant traversal.
    // Time Complexity: O(V + E), where V is nodes and E is edges.
    // Space Complexity: O(V + E), for adjacency list and visited set.
    public int countCompleteComponents(int n, int[][] edges) {
        // Step 1: Build adjacency list
        List<Set<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new HashSet<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        // Step 2: Find all connected components using DFS
        boolean[] visited = new boolean[n];
        int completeComponents = 0;

        for (int node = 0; node < n; node++) {
            if (!visited[node]) {
                Set<Integer> component = new HashSet<>();
                dfs(node, graph, visited, component);

                // Step 3: Check if the component is complete
                if (isComplete(component, graph)) {
                    completeComponents++;
                }
            }
        }

        return completeComponents;
    }

    private void dfs(int node, List<Set<Integer>> graph, boolean[] visited, Set<Integer> component) {
        visited[node] = true;
        component.add(node);
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited, component);
            }
        }
    }

    private boolean isComplete(Set<Integer> component, List<Set<Integer>> graph) {
        int size = component.size();
        for (int node : component) {
            if (graph.get(node).size() != size - 1) {
                return false;
            }
        }
        return true;
    }
}
