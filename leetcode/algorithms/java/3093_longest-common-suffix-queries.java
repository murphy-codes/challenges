// Source: https://leetcode.com/problems/longest-common-suffix-queries/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-27
// At the time of submission:
//   Runtime 40 ms Beats 99.99%
//   Memory 275.25 MB Beats 98.98%

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
    // Build a reversed Trie up to max query length; nodes store best indices.
    // Query by walking the longest matching suffix path in the Trie.
    // Best index = shortest word sharing that suffix, then earliest index.
    // Time: O(C + Q), Space: O(C) (C=container chars, Q=query chars)
    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        int maxQueryLength = findMaxQueryLength(wordsQuery);
        TrieNode root = buildTrie(wordsContainer, maxQueryLength);

        int[] result = new int[wordsQuery.length];

        for (int queryIndex = 0; queryIndex < wordsQuery.length; queryIndex++) {
            TrieNode current = root;
            String query = wordsQuery[queryIndex];

            // Walk the Trie using the reversed query.
            for (int charPos = query.length() - 1; charPos >= 0; charPos--) {
                int childIndex = query.charAt(charPos) - 'a';

                TrieNode next = current.children[childIndex];
                if (next == null) {
                    break;
                }

                current = next;
            }

            result[queryIndex] = current.shortestWordIndex;
        }

        return result;
    }

    private int findMaxQueryLength(String[] wordsQuery) {
        int maxLength = 0;

        for (String query : wordsQuery) {
            if (query.length() > maxLength) {
                maxLength = query.length();
            }
        }

        return maxLength;
    }

    private TrieNode buildTrie(String[] wordsContainer, int maxQueryLength) {
        TrieNode root = new TrieNode();

        for (int wordIndex = 0; wordIndex < wordsContainer.length; wordIndex++) {

            // Root represents the empty suffix ("").
            // Keep the globally shortest word here.
            if (wordsContainer[wordIndex].length()
                    < wordsContainer[root.shortestWordIndex].length()) {
                root.shortestWordIndex = wordIndex;
            }

            TrieNode current = root;

            // Insert the word in reverse order.
            // No need to go deeper than the longest query length.
            for (int charPos = wordsContainer[wordIndex].length() - 1, depth = 0;
                 charPos >= 0 && depth < maxQueryLength;
                 charPos--, depth++) {

                int childIndex =
                    wordsContainer[wordIndex].charAt(charPos) - 'a';

                TrieNode next = current.children[childIndex];

                if (next == null) {
                    next = new TrieNode(wordIndex);
                    current.children[childIndex] = next;
                } else if (
                    wordsContainer[wordIndex].length()
                        < wordsContainer[next.shortestWordIndex].length()
                ) {
                    next.shortestWordIndex = wordIndex;
                }

                current = next;
            }
        }

        return root;
    }

    private static class TrieNode {
        int shortestWordIndex = 0;
        TrieNode[] children = new TrieNode[26];

        TrieNode() {
        }

        TrieNode(int shortestWordIndex) {
            this.shortestWordIndex = shortestWordIndex;
        }
    }
}