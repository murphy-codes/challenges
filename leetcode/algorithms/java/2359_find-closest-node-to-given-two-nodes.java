// Source: https://leetcode.com/problems/find-closest-node-to-given-two-nodes/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-30
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 58.96 MB Beats 76.31%

/****************************************
* 
* You are given a directed graph of `n` nodes numbered from `0` to `n - 1`, where
* _ each node has at most one outgoing edge.
* The graph is represented with a given 0-indexed array `edges` of size `n`,
* _ indicating that there is a directed edge from node `i` to node `edges[i]`.
* _ If there is no outgoing edge from `i`, then `edges[i] == -1`.
* You are also given two integers `node1` and `node2`.
* Return the index of the node that can be reached from both `node1` and `node2`,
* _ such that the maximum between the distance from `node1` to that node, and from
* _ `node2` to that node is minimized. If there are multiple answers, return the
* _ node with the smallest index, and if no possible answer exists, return `-1`.
* Note that `edges` may contain cycles.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2022/06/07/graph4drawio-2.png]
* Input: edges = [2,2,3,-1], node1 = 0, node2 = 1
* Output: 2
* Explanation: The distance from node 0 to node 2 is 1, and the distance from node 1 to node 2 is 1.
* The maximum of those two distances is 1. It can be proven that we cannot get a node with a smaller maximum distance than 1, so we return node 2.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2022/06/07/graph4drawio-4.png]
* Input: edges = [1,2,-1], node1 = 0, node2 = 2
* Output: 2
* Explanation: The distance from node 0 to node 2 is 2, and the distance from node 2 to itself is 0.
* The maximum of those two distances is 2. It can be proven that we cannot get a node with a smaller maximum distance than 2, so we return node 2.
*
* Constraints:
* • n == edges.length
* • 2 <= n <= 10^5
* • -1 <= edges[i] < n
* • edges[i] != i
* • 0 <= node1, node2 < n
* 
****************************************/

class Solution {
    // Traverse the graph from both node1 and node2 simultaneously, marking 
    // visited nodes. If either traversal reaches a node already visited by 
    // the other, it's a candidate for the closest meeting node. 
    // Return early once a valid node is found.   
    // Time complexity is O(n); space is O(n) for two visited arrays.
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int n = edges.length;
        boolean[] visitedFrom1 = new boolean[n];
        boolean[] visitedFrom2 = new boolean[n];
        int result = Integer.MAX_VALUE;

        // Traverse from both node1 and node2 in parallel
        while (node1 != -1 || node2 != -1) {

            // Step from node1
            if (node1 != -1) {
                // If node2 has already visited this node, it's a candidate
                if (visitedFrom2[node1]) result = Math.min(result, node1);
                // If we've already visited this node from node1, stop
                if (visitedFrom1[node1]) node1 = -1;
                else {
                    visitedFrom1[node1] = true;
                    node1 = edges[node1];
                }
            }

            // Step from node2
            if (node2 != -1) {
                // If node1 has already visited this node, it's a candidate
                if (visitedFrom1[node2]) result = Math.min(result, node2);
                // If we've already visited this node from node2, stop
                if (visitedFrom2[node2]) node2 = -1;
                else {
                    visitedFrom2[node2] = true;
                    node2 = edges[node2];
                }
            }

            // If we found a candidate node, return immediately
            if (result != Integer.MAX_VALUE) return result;
        }

        return -1; // No common node found
    }
}
