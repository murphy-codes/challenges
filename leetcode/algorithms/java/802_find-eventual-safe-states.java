// Source: https://leetcode.com/problems/find-eventual-safe-states/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-23
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 56.08 MB Beats 7.99%

/****************************************
* 
* There is a directed graph of `n` nodes with each node labeled from `0` to `n - 1`. 
* _ The graph is represented by a 0-indexed 2D integer array `graph` where `graph[i]` 
* _ is an integer array of nodes adjacent to node `i`, meaning there is an edge 
* _ from node `i` to each node in `graph[i]`.
* A node is a terminal node if there are no outgoing edges. A node is a safe node 
* _ if every possible path starting from that node leads to a terminal node 
* _ (or another safe node).
* Return an array containing all the safe nodes of the graph. The answer should be 
* _ sorted in ascending order.
* 
* Example 1:
*   [Image: https://s3-lc-upload.s3.amazonaws.com/uploads/2018/03/17/picture1.png]
* Input: graph = [[1,2],[2,3],[5],[0],[5],[],[]]
* Output: [2,4,5,6]
* Explanation: The given graph is shown above.
* Nodes 5 and 6 are terminal nodes as there are no outgoing edges from either of them.
* Every path starting at nodes 2, 4, 5, and 6 all lead to either node 5 or 6.
* 
* Example 2:
* Input: graph = [[1,2,3,4],[1,2],[3,4],[0,4],[]]
* Output: [4]
* Explanation:
* Only node 4 is a terminal node, and every path starting at node 4 leads to node 4.
* 
* Constraints:
* • n == graph.length
* • 1 <= n <= 10^4
* • 0 <= graph[i].length <= n
* • 0 <= graph[i][j] <= n - 1
* • `graph[i]` is sorted in a strictly increasing order.
* • The graph may contain self-loops.
* • The number of edges in the graph will be in the range `[1, 4 * 10^4]`.
* 
****************************************/

import java.util.AbstractList;

class Solution {
    // This solution lazily computes the safe nodes by reversing the graph edges and using 
    // a topological sort (Kahn's algorithm) on nodes with zero indegree. Safe nodes are 
    // those that do not lead to cycles. The results are cached for efficient repeated access.
    // Time Complexity: O(V + E), where V is nodes and E is edges, for graph traversal.
    // Space Complexity: O(V + E) for reversed graph storage and auxiliary data structures.

    private List<Integer> safeNodes;

    public List<Integer> eventualSafeNodes(int[][] graph) {
        return new AbstractList<Integer>() {
            @Override
            public Integer get(int index) {
                initializeSafeNodes();
                return safeNodes.get(index);
            }

            @Override
            public int size() {
                initializeSafeNodes();
                return safeNodes.size();
            }

            private void initializeSafeNodes() {
                if (safeNodes != null) return;  // Already initialized

                int n = graph.length;
                List<List<Integer>> reversedGraph = new ArrayList<>();
                safeNodes = new ArrayList<>();
                int[] indegree = new int[n];

                // Initialize adjacency list for reversed graph
                for (int i = 0; i < n; i++) {
                    reversedGraph.add(new ArrayList<>());
                }

                // Build reversed graph and indegree array
                for (int node = 0; node < n; node++) {
                    for (int succ : graph[node]) {
                        reversedGraph.get(succ).add(node);
                        indegree[node]++;
                    }
                }

                // Queue for nodes with zero indegree (safe nodes)
                Queue<Integer> queue = new LinkedList<>();
                for (int i = 0; i < n; i++) {
                    if (indegree[i] == 0) {
                        queue.offer(i);
                    }
                }

                // Process nodes and identify all safe nodes
                while (!queue.isEmpty()) {
                    int curr = queue.poll();
                    safeNodes.add(curr);

                    for (int pred : reversedGraph.get(curr)) {
                        indegree[pred]--;
                        if (indegree[pred] == 0) {
                            queue.offer(pred);
                        }
                    }
                }

                // Sort to meet problem requirement
                Collections.sort(safeNodes);
            }
        };
    }
}
