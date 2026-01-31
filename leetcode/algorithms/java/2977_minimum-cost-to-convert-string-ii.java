// Source: https://leetcode.com/problems/minimum-cost-to-convert-string-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-30
// At the time of submission:
//   Runtime 107 ms Beats 87.27%
//   Memory 48.26 MB Beats 38.18%

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

class Solution {
    // Uses a Trie to map each unique string to an integer ID.
    // Floyd–Warshall precomputes minimum costs between all string pairs.
    // Dynamic programming scans the source string and tries all valid
    // substring transformations using Trie traversal for pruning.
    // Time: O(K^3 + N^2), Space: O(K^2 + N), where K = unique strings.

    private int nextId = 0;

    public long minimumCost(
        String source,
        String target,
        String[] original,
        String[] changed,
        int[] cost
    ) {
        TrieNode root = new TrieNode();

        // Insert all unique strings into the Trie
        for (String s : original) insert(s, root);
        for (String s : changed) insert(s, root);

        // dist[i][j] = minimum cost to convert string i -> string j
        int[][] dist = new int[nextId][nextId];
        for (int i = 0; i < nextId; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[i][i] = 0;
        }

        // Initialize direct transformation costs
        for (int i = 0; i < cost.length; i++) {
            int from = getIndex(original[i], root);
            int to = getIndex(changed[i], root);
            dist[from][to] = Math.min(dist[from][to], cost[i]);
        }

        // Floyd–Warshall to compute all-pairs shortest paths
        for (int mid = 0; mid < nextId; mid++) {
            for (int from = 0; from < nextId; from++) {
                if (dist[from][mid] == Integer.MAX_VALUE) continue;
                for (int to = 0; to < nextId; to++) {
                    if (dist[mid][to] == Integer.MAX_VALUE) continue;
                    dist[from][to] = Math.min(
                        dist[from][to],
                        dist[from][mid] + dist[mid][to]
                    );
                }
            }
        }

        char[] src = source.toCharArray();
        char[] tgt = target.toCharArray();
        int n = src.length;

        // dp[i] = min cost to convert source[0..i)
        long[] dp = new long[n + 1];
        Arrays.fill(dp, Long.MAX_VALUE);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            if (dp[i] == Long.MAX_VALUE) continue;

            TrieNode nodeSrc = root;
            TrieNode nodeTgt = root;

            // If characters already match, move forward at zero cost
            if (src[i] == tgt[i]) {
                dp[i + 1] = Math.min(dp[i + 1], dp[i]);
            }

            // Try extending substrings starting at i
            for (int j = i; j < n; j++) {
                nodeSrc = nodeSrc.next[src[j] - 'a'];
                nodeTgt = nodeTgt.next[tgt[j] - 'a'];

                if (nodeSrc == null || nodeTgt == null) break;

                if (nodeSrc.index != -1 &&
                    nodeTgt.index != -1 &&
                    dist[nodeSrc.index][nodeTgt.index] != Integer.MAX_VALUE) {

                    dp[j + 1] = Math.min(
                        dp[j + 1],
                        dp[i] + dist[nodeSrc.index][nodeTgt.index]
                    );
                }
            }
        }

        return dp[n] == Long.MAX_VALUE ? -1 : dp[n];
    }

    private void insert(String s, TrieNode node) {
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            if (node.next[c] == null) node.next[c] = new TrieNode();
            node = node.next[c];
        }
        if (node.index == -1) node.index = nextId++;
    }

    private int getIndex(String s, TrieNode node) {
        for (int i = 0; i < s.length(); i++) {
            node = node.next[s.charAt(i) - 'a'];
        }
        return node.index;
    }
}

class TrieNode {
    TrieNode[] next = new TrieNode[26];
    int index = -1;
}
