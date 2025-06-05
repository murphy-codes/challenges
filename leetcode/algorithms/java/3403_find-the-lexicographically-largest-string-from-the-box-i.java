// Source: https://leetcode.com/problems/lexicographically-smallest-equivalent-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-05
// At the time of submission:
//   Runtime 2 ms Beats 96.73%
//   Memory 41.88 MB Beats 88.19%

/****************************************
* 
* You are given two strings of the same length `s1` and `s2` and a string `baseStr`.
* We say `s1[i]` and `s2[i]` are equivalent characters.
* For example, if `s1 = "abc"` and `s2 = "cde"`, then we have
* _ `'a' == 'c'`, `'b' == 'd'`, and `'c' == 'e'`.
* Equivalent characters follow the usual rules of any equivalence relation:
* • Reflexivity: `'a' == 'a'`.
* • Symmetry: `'a' == 'b'` implies `'b' == 'a'`.
* • Transitivity: `'a' == 'b'` and `'b' == 'c'` implies `'a' == 'c'`.
* For example, given the equivalency information from `s1 = "abc"` and `s2 = "cde"`,
* _ `"acd"` and `"aab"` are equivalent strings of `baseStr = "eed"`, and `"aab"`
* _ is the lexicographically smallest equivalent string of `baseStr`.
* Return the lexicographically smallest equivalent string of `baseStr` by using
* _ the equivalency information from `s1` and `s2`.
*
* Example 1:
* Input: s1 = "parker", s2 = "morris", baseStr = "parser"
* Output: "makkek"
* Explanation: Based on the equivalency information in s1 and s2, we can group their characters as [m,p], [a,o], [k,r,s], [e,i].
* The characters in each group are equivalent and sorted in lexicographical order.
* So the answer is "makkek".
*
* Example 2:
* Input: s1 = "hello", s2 = "world", baseStr = "hold"
* Output: "hdld"
* Explanation: Based on the equivalency information in s1 and s2, we can group their characters as [h,w], [d,e,o], [l,r].
* So only the second letter 'o' in baseStr is changed to 'd', the answer is "hdld".
*
* Example 3:
* Input: s1 = "leetcode", s2 = "programs", baseStr = "sourcecode"
* Output: "aauaaaaada"
* Explanation: We group the equivalent characters in s1 and s2 as [a,o,e,r,s,c], [l,p], [g,t] and [d,m], thus all letters in baseStr except 'u' and 'd' are transformed to 'a', the answer is "aauaaaaada".
*
* Constraints:
* • 1 <= s1.length, s2.length, baseStr <= 1000
* • s1.length == s2.length
* • `s1`, `s2`, and `baseStr` consist of lowercase English letters.
* 
****************************************/

class Solution {
    // We use Union-Find to track character equivalence groups.
    // Always union with the lexicographically smaller character as root.
    // For each character in baseStr, we find its smallest equivalent root.
    // Time: O(α(n)) per union/find → nearly O(1) per op; overall O(N).
    // Space: O(26) for DSU parent array, constant space.
    private int[] parent = new int[26];  // 26 letters in the alphabet

    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        // Initialize: every character is its own parent
        for (int i = 0; i < 26; i++) {
            parent[i] = i;
        }

        // Union characters from s1 and s2
        for (int i = 0; i < s1.length(); i++) {
            union(s1.charAt(i) - 'a', s2.charAt(i) - 'a');
        }

        // Transform baseStr
        StringBuilder sb = new StringBuilder();
        for (char c : baseStr.toCharArray()) {
            int root = find(c - 'a');
            sb.append((char)(root + 'a'));
        }

        return sb.toString();
    }

    // Find with path compression
    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // Union two sets, keeping the smaller lexicographical character as root
    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            if (rootX < rootY) {
                parent[rootY] = rootX;
            } else {
                parent[rootX] = rootY;
            }
        }
    }
}
