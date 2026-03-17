// Source: https://leetcode.com/problems/maximize-spanning-tree-stability-with-upgrades/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-13
// At the time of submission:
//   Runtime 24 ms Beats 99.90%
//   Memory 244.88 MB Beats 81.59%

/****************************************
* 
* You are given an integer `n`, representing `n` nodes numbered from 0 to
* _ `n - 1` and a list of `edges`, where `edges[i] = [u_i, v_i, s_i, must_i]`:
* • `u_i` and `v_i` indicates an undirected edge between nodes `u_i` and `v_i`.
* • `s_i` is the strength of the edge.
* • `must_i` is an integer (0 or 1). If `must_i == 1`, the edge must be
* __ included in the spanning tree. These edges cannot be upgraded.
* You are also given an integer `k`, the maximum number of upgrades you can
* _ perform. Each upgrade doubles the strength of an edge, and each eligible
* _ edge (with `must_i == 0`) can be upgraded at most once.
* The stability of a spanning tree is defined as the minimum strength score
* _ among all edges included in it.
* Return the maximum possible stability of any valid spanning tree. If it is
* _ impossible to connect all nodes, return `-1`.
* Note: A spanning tree of a graph with `n` nodes is a subset of the edges
* _ that connects all nodes together (i.e. the graph is connected) without
* _ forming any cycles, and uses exactly `n - 1` edges.
*
* Example 1:
* Input: n = 3, edges = [[0,1,2,1],[1,2,3,0]], k = 1
* Output: 2
* Explanation:
* Edge [0,1] with strength = 2 must be included in the spanning tree.
* Edge [1,2] is optional and can be upgraded from 3 to 6 using one upgrade.
* The resulting spanning tree includes these two edges with strengths 2 and 6.
* The minimum strength in the spanning tree is 2, which is the maximum possible stability.
*
* Example 2:
* Input: n = 3, edges = [[0,1,4,0],[1,2,3,0],[0,2,1,0]], k = 2
* Output: 6
* Explanation:
* Since all edges are optional and up to k = 2 upgrades are allowed.
* Upgrade edges [0,1] from 4 to 8 and [1,2] from 3 to 6.
* The resulting spanning tree includes these two edges with strengths 8 and 6.
* The minimum strength in the tree is 6, which is the maximum possible stability.
*
* Example 3:
* Input: n = 3, edges = [[0,1,1,1],[1,2,1,1],[2,0,1,1]], k = 0
* Output: -1
* Explanation:
* All edges are mandatory and form a cycle, which violates the spanning tree property of acyclicity. Thus, the answer is -1.
*
* Constraints:
* • `2 <= n <= 10^5`
* • `1 <= edges.length <= 10^5`
* • `edges[i] = [u_i, v_i, s_i, must_i]`
* • `0 <= u_i, v_i < n`
* • `u_i != v_i`
* • `1 <= s_i <= 10^5`
* • `must_i` is either `0` or `1`.
* • `0 <= k <= n`
* • There are no duplicate edges.
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Binary search the maximum possible stability X. Mandatory edges are
    // pre-unioned into a base DSU; if they form a cycle the answer is -1.
    // Optional edges are packed into longs and sorted by strength for very
    // fast primitive processing. For each candidate X we greedily add free
    // edges (s ≥ X) and then upgradeable edges (2s ≥ X) using DSU until a
    // spanning tree is formed. Time: O(E log E + E log S), Space: O(E + N).

    // Bit packing constants
    // Each node index fits in 17 bits (n ≤ 100000)
    private static final int NODE_BITS = 17;
    private static final int NODE_MASK = (1 << NODE_BITS) - 1;

    public int maxStability(int n, int[][] edges, int k) {

        // DSU containing only mandatory edges
        int[] baseParent = new int[n];
        for (int i = 0; i < n; i++) baseParent[i] = i;

        int mandatoryEdgesUsed = 0;
        int minMandatoryStrength = Integer.MAX_VALUE;
        boolean hasMandatory = false;

        int optionalEdgeCount = 0;

        // Pass 1: process mandatory edges and count optional edges
        for (int[] edge : edges) {

            int u = edge[0];
            int v = edge[1];
            int strength = edge[2];
            int must = edge[3];

            if (must == 1) {

                hasMandatory = true;
                minMandatoryStrength = Math.min(minMandatoryStrength, strength);

                int rootU = find(baseParent, u);
                int rootV = find(baseParent, v);

                if (rootU != rootV) {
                    baseParent[rootU] = rootV;
                    mandatoryEdgesUsed++;
                } else {
                    // Mandatory edges create a cycle → impossible
                    return -1;
                }

            } else {
                optionalEdgeCount++;
            }
        }

        // Pack optional edges into long[]
        // Format: [strength | u | v]
        long[] optionalEdges = new long[optionalEdgeCount];
        int idx = 0;

        for (int[] edge : edges) {
            if (edge[3] == 0) {
                optionalEdges[idx++] =
                        ((long) edge[2] << (NODE_BITS * 2))
                                | ((long) edge[0] << NODE_BITS)
                                | edge[1];
            }
        }

        Arrays.sort(optionalEdges);

        int low = 1;
        int high = hasMandatory ? minMandatoryStrength : 200000;