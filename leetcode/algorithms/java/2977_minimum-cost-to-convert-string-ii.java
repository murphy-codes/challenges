// Source: https://leetcode.com/problems/minimum-cost-to-convert-string-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-30
// At the time of submission:
//   Runtime 294 ms Beats 23.64%
//   Memory 49.78 MB Beats 7.27%

/****************************************
* 
* You are given two 0-indexed strings `source` and `target`, both of length `n`
* _ and consisting of lowercase English characters. You are also given two
* _ 0-indexed string arrays `original` and `changed`, and an integer array
* _ `cost`, where `cost[i]` represents the cost of converting the string
* _ `original[i]` to the string `changed[i]`.
* You start with the string `source`. In one operation, you can pick a substring
* _ `x` from the string, and change it to `y` at a cost of `z` if there exists
* _ any index `j` such that `cost[j] == z`, `original[j] == x`, and
* _ `changed[j] == y`. You are allowed to do any number of operations, but any
* _ pair of operations must satisfy either of these two conditions:
* • The substrings picked in the operations are `source[a..b]` and `source[c..d]`
* __ with either `b < c` or `d < a`. In other words, the indices picked in both
* __ operations are disjoint.
* • The substrings picked in the operations are `source[a..b]` and `source[c..d]`
* __ with `a == c` and `b == d`. In other words, the indices picked in both
* __ operations are identical.
* Return the minimum cost to convert the string `source` to the string `target`
* _ using any number of operations. If it is impossible to convert `source` to
* _ `target`, return `-1`.
* Note that there may exist indices `i`, `j` such that
* _ `original[j] == original[i]` and `changed[j] == changed[i]`.
*
* Example 1:
* Input: source = "abcd", target = "acbe", original = ["a","b","c","c","e","d"], changed = ["b","c","b","e","b","e"], cost = [2,5,5,1,2,20]
* Output: 28
* Explanation: To convert "abcd" to "acbe", do the following operations:
* - Change substring source[1..1] from "b" to "c" at a cost of 5.
* - Change substring source[2..2] from "c" to "e" at a cost of 1.
* - Change substring source[2..2] from "e" to "b" at a cost of 2.
* - Change substring source[3..3] from "d" to "e" at a cost of 20.
* The total cost incurred is 5 + 1 + 2 + 20 = 28.
* It can be shown that this is the minimum possible cost.
*
* Example 2:
* Input: source = "abcdefgh", target = "acdeeghh", original = ["bcd","fgh","thh"], changed = ["cde","thh","ghh"], cost = [1,3,5]
* Output: 9
* Explanation: To convert "abcdefgh" to "acdeeghh", do the following operations:
* - Change substring source[1..3] from "bcd" to "cde" at a cost of 1.
* - Change substring source[5..7] from "fgh" to "thh" at a cost of 3. We can do this operation because indices [5,7] are disjoint with indices picked in the first operation.
* - Change substring source[5..7] from "thh" to "ghh" at a cost of 5. We can do this operation because indices [5,7] are disjoint with indices picked in the first operation, and identical with indices picked in the second operation.
* The total cost incurred is 1 + 3 + 5 = 9.
* It can be shown that this is the minimum possible cost.
*
* Example 3:
* Input: source = "abcdefgh", target = "addddddd", original = ["bcd","defgh"], changed = ["ddd","ddddd"], cost = [100,1578]
* Output: -1
* Explanation: It is impossible to convert "abcdefgh" to "addddddd".
* If you select substring source[1..3] as the first operation to change "abcdefgh" to "adddefgh", you cannot select substring source[3..7] as the second operation because it has a common index, 3, with the first operation.
* If you select substring source[3..7] as the first operation to change "abcdefgh" to "abcddddd", you cannot select substring source[1..3] as the second operation because it has a common index, 3, with the first operation.
*
* Constraints:
* • `1 <= source.length == target.length <= 1000`
* • `source`, `target` consist only of lowercase English characters.
* • `1 <= cost.length == original.length == changed.length <= 100`
* • `1 <= original[i].length == changed[i].length <= source.length`
* • `original[i]`, `changed[i]` consist only of lowercase English characters.
* • `original[i] != changed[i]`
* • `1 <= cost[i] <= 10^6`
* 
****************************************/

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Solution {
    // Assign each unique substring an ID and build a conversion graph.
    // Use Floyd–Warshall to compute minimum cost between all string pairs.
    // Apply DP where dp[i] is the minimum cost to convert prefix [0..i).
    // Trie enables fast substring matching for valid conversions.
    // Time: O(m^3 + n^2), Space: O(m^2 + n)

    static class TrieNode {
        TrieNode[] next = new TrieNode[26];
        int id = -1;
    }

    private void insert(TrieNode root, String s, int id) {
        TrieNode cur = root;
        for (char c : s.toCharArray()) {
            int idx = c - 'a';
            if (cur.next[idx] == null) {
                cur.next[idx] = new TrieNode();
            }
            cur = cur.next[idx];
        }
        cur.id = id;
    }

    public long minimumCost(
        String source,
        String target,
        String[] original,
        String[] changed,
        int[] cost
    ) {
        int n = source.length();
        int m = original.length;

        // Step 1: assign IDs to unique strings
        Map<String, Integer> idMap = new HashMap<>();
        int id = 0;
        for (int i = 0; i < m; i++) {
            if (!idMap.containsKey(original[i])) {
                idMap.put(original[i], id++);
            }
            if (!idMap.containsKey(changed[i])) {
                idMap.put(changed[i], id++);
            }
        }

        int sz = id;
        long[][] dist = new long[sz][sz];
        for (int i = 0; i < sz; i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE / 4);
            dist[i][i] = 0;
        }

        // Step 2: build conversion graph
        for (int i = 0; i < m; i++) {
            int u = idMap.get(original[i]);
            int v = idMap.get(changed[i]);
            dist[u][v] = Math.min(dist[u][v], cost[i]);
        }

        // Step 3: Floyd–Warshall
        for (int k = 0; k < sz; k++) {
            for (int i = 0; i < sz; i++) {
                for (int j = 0; j < sz; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        // Step 4: build Tries
        TrieNode srcTrie = new TrieNode();
        TrieNode tgtTrie = new TrieNode();
        for (Map.Entry<String, Integer> e : idMap.entrySet()) {
            insert(srcTrie, e.getKey(), e.getValue());
            insert(tgtTrie, e.getKey(), e.getValue());
        }

        // Step 5: DP
        long[] dp = new long[n + 1];
        Arrays.fill(dp, Long.MAX_VALUE / 4);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            if (dp[i] == Long.MAX_VALUE / 4) continue;

            // No operation
            if (source.charAt(i) == target.charAt(i)) {
                dp[i + 1] = Math.min(dp[i + 1], dp[i]);
            }

            TrieNode sNode = srcTrie;
            TrieNode tNode = tgtTrie;

            for (int j = i; j < n; j++) {
                sNode = sNode == null ? null : sNode.next[source.charAt(j) - 'a'];
                tNode = tNode == null ? null : tNode.next[target.charAt(j) - 'a'];
                if (sNode == null || tNode == null) break;

                if (sNode.id != -1 && tNode.id != -1) {
                    long c = dist[sNode.id][tNode.id];
                    if (c < Long.MAX_VALUE / 4) {
                        dp[j + 1] = Math.min(dp[j + 1], dp[i] + c);
                    }
                }
            }
        }

        return dp[n] >= Long.MAX_VALUE / 4 ? -1 : dp[n];
    }
}
