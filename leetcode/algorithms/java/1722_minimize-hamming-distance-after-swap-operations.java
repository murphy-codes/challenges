// Source: https://leetcode.com/problems/minimize-hamming-distance-after-swap-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-20
// At the time of submission:
//   Runtime 48 ms Beats 66.67%
//   Memory 112.39 MB Beats 83.87%

/****************************************
* 
* You are given two integer arrays, `source` and `target`, both of length `n`.
* _ You are also given an array `allowedSwaps` where each
* _ `allowedSwaps[i] = [a_i, b_i]` indicates that you are allowed to swap the
* _ elements at index `a_i` and index `b_i` (0-indexed) of array `source`.
* _ Note that you can swap elements at a specific pair of indices multiple
* _ times and in any order.
* The Hamming distance of two arrays of the same length, source and target,
* _ is the number of positions where the elements are different. Formally,
* _ it is the number of indices `i` for
* _ `0 <= i <= n-1 where source[i] != target[i]` (0-indexed).
* Return the minimum Hamming distance of `source` and `target` after
* _ performing any amount of swap operations on array `source`.
*
* Example 1:
* Input: source = [1,2,3,4], target = [2,1,4,5], allowedSwaps = [[0,1],[2,3]]
* Output: 1
* Explanation: source can be transformed the following way:
* - Swap indices 0 and 1: source = [2,1,3,4]
* - Swap indices 2 and 3: source = [2,1,4,3]
* The Hamming distance of source and target is 1 as they differ in 1 position: index 3.
*
* Example 2:
* Input: source = [1,2,3,4], target = [1,3,2,4], allowedSwaps = []
* Output: 2
* Explanation: There are no allowed swaps.
* The Hamming distance of source and target is 2 as they differ in 2 positions: index 1 and index 2.
*
* Example 3:
* Input: source = [5,1,2,4,3], target = [1,5,4,2,3], allowedSwaps = [[0,4],[4,2],[1,3],[1,4]]
* Output: 0
*
* Constraints:
* • `n == source.length == target.length`
* • `1 <= n <= 10^5`
* • `1 <= source[i], target[i] <= 10^5`
* • `0 <= allowedSwaps.length <= 10^5`
* • `allowedSwaps[i].length == 2`
* • `0 <= a_i, b_i <= n - 1`
* • `a_i != b_i`
* 
****************************************/

import java.util.HashMap;
import java.util.Map;

class Solution {
    // Use Union-Find to group indices into connected components.
    // Within each component, elements can be freely rearranged.
    // Count frequencies of source values per component and try
    // to match target values, reducing counts when possible.
    // Unmatched elements contribute to Hamming distance.
    // Time: O(n α(n)), Space: O(n).
    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;

        UnionFind uf = new UnionFind(n);

        // Build connected components
        for (int[] swap : allowedSwaps) {
            uf.union(swap[0], swap[1]);
        }

        // Group indices by root
        Map<Integer, Map<Integer, Integer>> groups = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            groups.putIfAbsent(root, new HashMap<>());
            Map<Integer, Integer> freq = groups.get(root);

            freq.put(source[i], freq.getOrDefault(source[i], 0) + 1);
        }

        int mismatch = 0;

        // Try to match target values within each component
        for (int i = 0; i < n; i++) {
            int root = uf.find(i);
            Map<Integer, Integer> freq = groups.get(root);

            int val = target[i];
            if (freq.getOrDefault(val, 0) > 0) {
                freq.put(val, freq.get(val) - 1);
            } else {
                mismatch++;
            }
        }

        return mismatch;
    }

    // Union-Find with path compression
    static class UnionFind {
        int[] parent;

        public UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA != rootB) {
                parent[rootA] = rootB;
            }
        }
    }
}