// Source: https://leetcode.com/problems/minimize-hamming-distance-after-swap-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-20
// At the time of submission:
//   Runtime 43 ms Beats 84.95%
//   Memory 98.90 MB Beats 100.00%

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

import java.util.Arrays;
import java.util.HashMap;

class Solution {
    // Use a Union-Find-like structure to group swappable indices.
    // For each component, track frequency deltas: +1 for source,
    // -1 for target. The sum of absolute imbalances gives twice
    // the number of mismatches, so divide by 2. Handle isolated
    // indices separately. Time: O(n α(n)), Space: O(n).
    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;

        int[] parent = new int[n];
        Arrays.fill(parent, 100001); // sentinel for "uninitialized"

        // Build union structure
        for (int[] swap : allowedSwaps) {
            int rootA = findRoot(parent, swap[0]);
            int rootB = findRoot(parent, swap[1]);

            int newRoot = Math.min(rootA, rootB);

            parent[rootA] = newRoot;
            parent[rootB] = newRoot;

            // small optimization: flatten immediate nodes
            parent[swap[0]] = newRoot;
            parent[swap[1]] = newRoot;
        }

        HashMap<Integer, HashMap<Integer, Integer>> componentMap = new HashMap<>();

        int mismatchNoSwap = 0;

        // Build frequency deltas per component
        for (int i = 0; i < n; i++) {
            // No swaps allowed for this index
            if (parent[i] == 100001) {
                if (source[i] != target[i]) {
                    mismatchNoSwap++;
                }
                continue;
            }

            int root = findRoot(parent, i);

            componentMap.putIfAbsent(root, new HashMap<>());
            HashMap<Integer, Integer> freq = componentMap.get(root);

            // Add source, subtract target
            freq.put(source[i], freq.getOrDefault(source[i], 0) + 1);
            freq.put(target[i], freq.getOrDefault(target[i], 0) - 1);
        }

        int imbalance = 0;

        // Sum absolute imbalances
        for (HashMap<Integer, Integer> freq : componentMap.values()) {
            for (int count : freq.values()) {
                imbalance += Math.abs(count);
            }
        }

        // Each mismatch counted twice → divide by 2
        return imbalance / 2 + mismatchNoSwap;
    }

    private int findRoot(int[] parent, int index) {
        if (parent[index] == 100001) {
            parent[index] = index;
            return index;
        }
        if (parent[index] == index) {
            return index;
        }
        return findRoot(parent, parent[index]);
    }
}