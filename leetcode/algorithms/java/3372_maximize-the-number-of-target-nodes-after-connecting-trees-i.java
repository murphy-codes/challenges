// Source: https://leetcode.com/problems/maximize-the-number-of-target-nodes-after-connecting-trees-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-27
// At the time of submission:
//   Runtime 48 ms Beats 100.00%
//   Memory 67.19 MB Beats 8.86%

/****************************************
* 
* There exist two undirected trees with `n` and `m` nodes, with distinct labels
* _ in ranges `[0, n - 1]` and `[0, m - 1]`, respectively.
* You are given two 2D integer arrays `edges1` and `edges2` of lengths `n - 1`
* _ and `m - 1`, respectively, where `edges1[i] = [a_i, b_i]` indicates that
* _ there is an edge between nodes `a_i` and `b_i` in the first tree and
* _ `edges2[i] = [u_i, v_i]` indicates that there is an edge between nodes
* _ `u_i` and `v_i` in the second tree. You are also given an integer `k`.
* Node `u` is target to node `v` if the number of edges on the path from `u`
* _ to `v` is less than or equal to `k`. Note that a node is always target
* _ to itself.
* Return an array of `n` integers `answer`, where `answer[i]` is the maximum
* _ possible number of nodes target to node `i` of the first tree if you have to
* _ connect one node from the first tree to another node in the second tree.
* Note that queries are independent from each other. That is, for every query
* _ you will remove the added edge before proceeding to the next query.
*
* Example 1:
* Input: edges1 = [[0,1],[0,2],[2,3],[2,4]], edges2 = [[0,1],[0,2],[0,3],[2,7],[1,4],[4,5],[4,6]], k = 2
* Output: [9,7,9,8,8]
* Explanation:
* • For `i = 0`, connect node 0 from the first tree to node 0 from the second tree.
* • For `i = 1`, connect node 1 from the first tree to node 0 from the second tree.
* • For `i = 2`, connect node 2 from the first tree to node 4 from the second tree.
* • For `i = 3`, connect node 3 from the first tree to node 4 from the second tree.
* • For `i = 4`, connect node 4 from the first tree to node 4 from the second tree.
* [Image: https://assets.leetcode.com/uploads/2024/09/24/3982-1.png]
*
* Example 2:
* Input: edges1 = [[0,1],[0,2],[0,3],[0,4]], edges2 = [[0,1],[1,2],[2,3]], k = 1
* Output: [6,3,3,3,3]
* Explanation:
* For every `i`, connect node `i` of the first tree with any node of the second tree.
* [Image: https://assets.leetcode.com/uploads/2024/09/24/3928-2.png]
*
* Constraints:
* • 2 <= n, m <= 1000
* • edges1.length == n - 1
* • edges2.length == m - 1
* • edges1[i].length == edges2[i].length == 2
* • edges1[i] = [a_i, b_i]
* • 0 <= a_i, b_i < n
* • edges2[i] = [u_i, v_i]
* • 0 <= u_i, v_i < m
* • The input is generated such that `edges1` and `edges2` represent valid trees.
* • 0 <= k <= 1000
* 
****************************************/

class Solution {
    // This solution computes the number of reachable nodes from each node in tree1,
    // considering all paths of length ≤ k, including those crossing into tree2 by 1 edge.
    // It uses DFS to populate forward path counts (`dp`) and then computes backward
    // path counts (`backtrack`) and total path counts (`count`) via topological order.
    // Time complexity: O(N * k + M * k), Space complexity: O(N * k + M * k), where
    // N and M are the number of nodes in tree1 and tree2, respectively.
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        final int N = edges1.length + 1;
        final int M = edges2.length + 1;

        if (k == 0) {
            int[] res = new int[N];
            Arrays.fill(res, 1);
            return res;
        }

        // Build adjacency lists for both trees
        List<Integer>[] tree1 = buildAdj(edges1, N);
        List<Integer>[] tree2 = buildAdj(edges2, M);

        // dp[v][d] = number of nodes at distance d in subtree rooted at v
        int[][] dp1 = new int[N][k + 1];
        int[][] dp2 = new int[M][k];

        int[] parent1 = new int[N];
        int[] parent2 = new int[M];

        Queue<Integer> bfsOrder1 = new LinkedList<>();
        Queue<Integer> bfsOrder2 = new LinkedList<>();

        // Run DFS to populate dp tables and capture parent relationships
        dfs(0, -1, tree1, parent1, dp1, bfsOrder1, k);
        dfs(0, -1, tree2, parent2, dp2, bfsOrder2, k - 1);

        // For backtracking paths (upward and lateral movement)
        int[][] backtrack1 = new int[N][k + 1];
        int[][] backtrack2 = new int[M][k];

        int[] count1 = new int[N]; // Final result array for tree1
        int[] count2 = new int[M]; // Used to compute max connectivity

        // Populate counts and backtrack arrays
        calcBacktrack(bfsOrder1, parent1, dp1, backtrack1, count1, k);
        calcBacktrack(bfsOrder2, parent2, dp2, backtrack2, count2, k - 1);

        // Find the max value in tree2 counts
        int maxConnectivity = 1;
        for (int count : count2)
            maxConnectivity = Math.max(maxConnectivity, count);

        // Add max from tree2 to each node in tree1
        for (int i = 0; i < N; i++)
            count1[i] += maxConnectivity;

        return count1;
    }

    private List<Integer>[] buildAdj(int[][] edges, int vertexCount) {
        List<Integer>[] graph = new List[vertexCount];
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            if (graph[u] == null) graph[u] = new LinkedList<>();
            if (graph[v] == null) graph[v] = new LinkedList<>();
            graph[u].add(v);
            graph[v].add(u);
        }
        return graph;
    }

    private void dfs(int node, int parent, List<Integer>[] tree, int[] parentArr, int[][] dp, Queue<Integer> order, int maxDist) {
        dp[node][0] = 1;
        order.offer(node);
        parentArr[node] = parent;

        if (tree[node] != null) {
            for (int child : tree[node]) {
                if (child == parent) continue;
                dfs(child, node, tree, parentArr, dp, order, maxDist);
            }
        }

        if (parent == -1) return;

        for (int d = 1; d <= maxDist; d++)
            dp[parent][d] += dp[node][d - 1];
    }

    private void calcBacktrack(Queue<Integer> order, int[] parent, int[][] dp, int[][] backtrack, int[] count, int maxDist) {
        while (!order.isEmpty()) {
            int node = order.poll();
            int par = parent[node];

            // Distance 0
            count[node] = dp[node][0];

            if (maxDist >= 1) {
                if (par != -1)
                    backtrack[node][1] = 1;
                count[node] += dp[node][1] + backtrack[node][1];
            }

            // For distances 2 to k
            for (int d = 2; d <= maxDist; d++) {
                if (par != -1) {
                    // Remove overlap from forward path through this child
                    backtrack[node][d] = dp[par][d - 1] - dp[node][d - 2] + backtrack[par][d - 1];
                }
                count[node] += dp[node][d] + backtrack[node][d];
            }
        }
    }
}
