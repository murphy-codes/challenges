// Source: https://leetcode.com/problems/find-eventual-safe-states/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-23
// At the time of submission:
//   Runtime 26 ms Beats 24.92%
//   Memory 55.06 MB Beats 96.93%

/****************************************
* 
* 12345678901234567890123456789012345678901234567890123456789012345678901234567890
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

class Solution {
    // Reverse the graph so edges point to predecessors
    // Use Kahn's algorithm (topological sorting) to identify nodes with indegree 0
    // Nodes with indegree 0 are safe, as they do not lead to cycles
    // Gradually reduce indegree of neighbors and collect all eventual safe nodes
    // Sort the result and return it
    // Time: O(V + E), Space: O(V + E)
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        List<List<Integer>> reverseGraph = new ArrayList<>();
        int[] indegree = new int[n];
        
        // Build the reverse graph and calculate indegree
        for (int i = 0; i < n; i++) {
            reverseGraph.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            for (int neighbor : graph[i]) {
                reverseGraph.get(neighbor).add(i);
                indegree[i]++;
            }
        }
        
        // Use a queue to perform topological sorting
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        // Process nodes and collect safe nodes
        List<Integer> safeNodes = new ArrayList<>();
        while (!queue.isEmpty()) {
            int node = queue.poll();
            safeNodes.add(node);
            for (int neighbor : reverseGraph.get(node)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        // Sort the safe nodes as required
        Collections.sort(safeNodes);
        return safeNodes;
    }
}
