// Source: https://leetcode.com/problems/longest-common-suffix-queries/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-27
// At the time of submission:
//   Runtime 79 ms Beats 35.57%
//   Memory 284.1 MB Beats 45.03%

/****************************************
* 
* You are given two arrays of strings `wordsContainer` and `wordsQuery`.
* For each `wordsQuery[i]`, you need to find a string from `wordsContainer`
* _ that has the longest common suffix with `wordsQuery[i]`. If there are two
* _ or more strings in `wordsContainer` that share the longest common suffix,
* _ find the string that is the smallest in length. If there are two or more
* _ such strings that have the same smallest length, find the one that
* _ occurred earlier in `wordsContainer`.
* Return an array of integers `ans`, where `ans[I]` is the index of the string
* _ in `wordsContainer` that has the longest common suffix with `wordsQuery[i]`.
*
* Example 1:
* Input: wordsContainer = ["abcd","bcd","xbcd"], wordsQuery = ["cd","bcd","xyz"]
* Output: [1,1,1]
* Explanation:
* Let's look at each wordsQuery[i] separately:
* • For wordsQuery[0] = "cd", strings from wordsContainer that share the longest common suffix "cd" are at indices 0, 1, and 2. Among these, the answer is the string at index 1 because it has the shortest length of 3.
* • For wordsQuery[1] = "bcd", strings from wordsContainer that share the longest common suffix "bcd" are at indices 0, 1, and 2. Among these, the answer is the string at index 1 because it has the shortest length of 3.
* • For wordsQuery[2] = "xyz", there is no string from wordsContainer that shares a common suffix. Hence the longest common suffix is "", that is shared with strings at index 0, 1, and 2. Among these, the answer is the string at index 1 because it has the shortest length of 3.
* Example 2:
* Input: wordsContainer = ["abcdefgh","poiuygh","ghghgh"], wordsQuery = ["gh","acbfgh","acbfegh"]
* Output: [2,0,2]
* Explanation:
* Let's look at each wordsQuery[i] separately:
* • For wordsQuery[0] = "gh", strings from wordsContainer that share the longest common suffix "gh" are at indices 0, 1, and 2. Among these, the answer is the string at index 2 because it has the shortest length of 6.
* • For wordsQuery[1] = "acbfgh", only the string at index 0 shares the longest common suffix "fgh". Hence it is the answer, even though the string at index 2 is shorter.
* • For wordsQuery[2] = "acbfegh", strings from wordsContainer that share the longest common suffix "gh" are at indices 0, 1, and 2. Among these, the answer is the string at index 2 because it has the shortest length of 6.
*
* Constraints:
* • `1 <= wordsContainer.length, wordsQuery.length <= 10^4`
* • `1 <= wordsContainer[i].length <= 5 * 10^3`
* • `1 <= wordsQuery[i].length <= 5 * 10^3`
* • `wordsContainer[i]` consists only of lowercase English letters.
* • `wordsQuery[i]` consists only of lowercase English letters.
* • Sum of `wordsContainer[i].length` is at most `5 * 10^5`.
* • Sum of `wordsQuery[i].length` is at most `5 * 10^5`.
* 
****************************************/

class Solution {
    // Reverse words so suffix matching becomes prefix matching in a Trie.
    // Each Trie node stores the best container index for that suffix path,
    // preferring shorter words and earlier indices on ties.
    // Time: O(containerChars + queryChars), Space: O(containerChars)

    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];

        // Best candidate index for this suffix/prefix
        int bestIndex = -1;
    }

    public int[] stringIndices(String[] wordsContainer,
                               String[] wordsQuery) {

        TrieNode root = new TrieNode();

        // Build reversed trie
        for (int i = 0; i < wordsContainer.length; i++) {
            insert(root, wordsContainer, i);
        }

        int[] ans = new int[wordsQuery.length];

        for (int i = 0; i < wordsQuery.length; i++) {
            ans[i] = search(root, wordsQuery[i]);
        }

        return ans;
    }

    private void insert(TrieNode root,
                        String[] wordsContainer,
                        int index) {

        String word = wordsContainer[index];
        TrieNode node = root;

        // Update root candidate
        updateBest(node, wordsContainer, index);

        // Insert reversed word
        for (int i = word.length() - 1; i >= 0; i--) {
            int c = word.charAt(i) - 'a';

            if (node.children[c] == null) {
                node.children[c] = new TrieNode();
            }

            node = node.children[c];

            // Update best candidate at this node
            updateBest(node, wordsContainer, index);
        }
    }

    private int search(TrieNode root, String query) {
        TrieNode node = root;

        // Traverse reversed query
        for (int i = query.length() - 1; i >= 0; i--) {
            int c = query.charAt(i) - 'a';

            if (node.children[c] == null) {
                break;
            }

            node = node.children[c];
        }

        return node.bestIndex;
    }

    private void updateBest(TrieNode node,
                            String[] wordsContainer,
                            int candidateIndex) {

        if (node.bestIndex == -1) {
            node.bestIndex = candidateIndex;
            return;
        }

        int currentLen = wordsContainer[node.bestIndex].length();
        int candidateLen = wordsContainer[candidateIndex].length();

        // Prefer:
        // 1. shorter length
        // 2. earlier index if tied
        if (candidateLen < currentLen ||
            (candidateLen == currentLen &&
             candidateIndex < node.bestIndex)) {

            node.bestIndex = candidateIndex;
        }
    }
}