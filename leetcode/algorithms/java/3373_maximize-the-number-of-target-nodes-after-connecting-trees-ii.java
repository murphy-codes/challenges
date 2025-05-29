// Source: https://leetcode.com/problems/maximize-the-number-of-target-nodes-after-connecting-trees-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-27
// At the time of submission:
//   Runtime 127 ms Beats 66.67%
//   Memory 119.82 MB Beats 74.36%

/****************************************
* 
* There exist two undirected trees with `n` and `m` nodes, labeled from
* _ `[0, n - 1]` and `[0, m - 1]`, respectively.
* You are given two 2D integer arrays `edges1` and `edges2` of lengths `n - 1`
* _ and `m - 1`, respectively, where `edges1[i] = [a_i, b_i]` indicates that
* _ there is an edge between nodes `a_i` and `b_i` in the first tree and
* _ `edges2[i] = [u_i, v_i]` indicates that there is an edge between nodes
* _ `u_i` and `v_i` in the second tree.
* Node `u` is target to node `v` if the number of edges on the path from
* _ `u` to `v` is even. Note that a node is always target to itself.
* Return an array of `n` integers `answer`, where `answer[i]` is the maximum
* _ possible number of nodes that are target to node `i` of the first tree if you
* _ had to connect one node from the first tree to another node in the second tree.
* Note that queries are independent from each other. That is, for every query you
* _ will remove the added edge before proceeding to the next query.
*
* Example 1:
* Input: edges1 = [[0,1],[0,2],[2,3],[2,4]], edges2 = [[0,1],[0,2],[0,3],[2,7],[1,4],[4,5],[4,6]]
* Output: [8,7,7,8,8]
* Explanation:
* • For `i = 0`, connect node 0 from the first tree to node 0 from the second tree.
* • For `i = 1`, connect node 1 from the first tree to node 4 from the second tree.
* • For `i = 2`, connect node 2 from the first tree to node 7 from the second tree.
* • For `i = 3`, connect node 3 from the first tree to node 0 from the second tree.
* • For `i = 4`, connect node 4 from the first tree to node 4 from the second tree.
* [Image: https://assets.leetcode.com/uploads/2024/09/24/3982-1.png]
*
* Example 2:
* Input: edges1 = [[0,1],[0,2],[0,3],[0,4]], edges2 = [[0,1],[1,2],[2,3]]
* Output: [3,6,6,6,6]
* Explanation:
* For every `i`, connect node `i` of the first tree with any node of the second tree.
* [Image: https://assets.leetcode.com/uploads/2024/09/24/3928-2.png]
*
* Constraints:
* • 2 <= n, m <= 10^5
* • edges1.length == n - 1
* • edges2.length == m - 1
* • edges1[i].length == edges2[i].length == 2
* • edges1[i] = [a_i, b_i]
* • 0 <= a_i, b_i < n
* • edges2[i] = [u_i, v_i]
* • 0 <= u_i, v_i < m
* • The input is generated such that `edges1` and `edges2` represent valid trees.
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {
    // We use DFS to precompute depth parities for both trees, allowing us
    // to count how many nodes are at even/odd distances from each node.
    // For each node in Tree1, we try connecting to even or odd nodes in Tree2
    // and compute which connection yields more target nodes.
    // Time: O(n + m), Space: O(n + m), where n = Tree1 nodes, m = Tree2 nodes.
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2) {
        int n = edges1.length + 1;
        int m = edges2.length + 1;

        List<List<Integer>> tree1 = new ArrayList<>();
        List<List<Integer>> tree2 = new ArrayList<>();

        for (int i = 0; i < n; i++) tree1.add(new ArrayList<>());
        for (int i = 0; i < m; i++) tree2.add(new ArrayList<>());

        for (int[] e : edges1) {
            tree1.get(e[0]).add(e[1]);
            tree1.get(e[1]).add(e[0]);
        }
        for (int[] e : edges2) {
            tree2.get(e[0]).add(e[1]);
            tree2.get(e[1]).add(e[0]);
        }

        // Depths for Tree1
        int[] depth1 = new int[n];
        dfs(tree1, 0, -1, 0, depth1);

        // Depths for Tree2
        int[] depth2 = new int[m];
        dfs(tree2, 0, -1, 0, depth2);

        int evenT1 = 0, oddT1 = 0;
        for (int d : depth1) {
            if (d % 2 == 0) evenT1++;
            else oddT1++;
        }

        int evenT2 = 0, oddT2 = 0;
        for (int d : depth2) {
            if (d % 2 == 0) evenT2++;
            else oddT2++;
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            boolean evenDepth1 = depth1[i] % 2 == 0;
            int fromT1 = evenDepth1 ? evenT1 : oddT1;

            // Two cases: connect to even-depth or odd-depth node in T2
            // Depending on parity, target nodes from T2 change
            int connectEven = (depth1[i] + 1) % 2 == 0 ? evenT2 : oddT2;
            int connectOdd  = (depth1[i] + 1) % 2 == 0 ? oddT2 : evenT2;

            res[i] = fromT1 + Math.max(connectEven, connectOdd);
        }

        return res;
    }

    private void dfs(List<List<Integer>> tree, int node, int parent, int depth, int[] depthArr) {
        depthArr[node] = depth;
        for (int nei : tree.get(node)) {
            if (nei != parent) {
                dfs(tree, nei, node, depth + 1, depthArr);
            }
        }
    }
}