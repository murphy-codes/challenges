// Source: https://leetcode.com/problems/longest-common-suffix-queries/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-27
// At the time of submission:
//   Runtime 46 ms Beats 96.37%
//   Memory 284.49 MB Beats 15.12%

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
    // Reverse words so suffix matching becomes Trie prefix traversal.
    // Each Trie node stores the shortest matching container word index,
    // with insertion order naturally resolving equal-length ties.
    // Time: O(containerChars + queryChars), Space: O(containerChars)
    
    class TrieNode {
        TrieNode[] children = new TrieNode[26];

        // Best candidate for this suffix path
        int shortestLen = Integer.MAX_VALUE;
        int bestIndex = 0;
    }

    TrieNode root = new TrieNode();

    void insert(String word, int index) {

        TrieNode node = root;
        int length = word.length();

        // Update empty suffix candidate
        if (length < node.shortestLen) {
            node.shortestLen = length;
            node.bestIndex = index;
        }

        // Insert reversed word into Trie
        for (int i = length - 1; i >= 0; --i) {

            int charIndex = word.charAt(i) - 'a';

            if (node.children[charIndex] == null) {
                node.children[charIndex] = new TrieNode();
            }

            node = node.children[charIndex];

            // Keep shortest word for this suffix
            if (length < node.shortestLen) {
                node.shortestLen = length;
                node.bestIndex = index;
            }
        }
    }

    int search(String query) {

        TrieNode node = root;

        // Default answer = shortest overall container word
        int answer = node.bestIndex;

        // Traverse reversed query
        for (int i = query.length() - 1; i >= 0; --i) {

            int charIndex = query.charAt(i) - 'a';

            if (node.children[charIndex] == null) {
                break;
            }

            node = node.children[charIndex];

            // Longest suffix match so far
            answer = node.bestIndex;
        }

        return answer;
    }

    public int[] stringIndices(String[] wordsContainer,
                               String[] wordsQuery) {

        for (int i = 0; i < wordsContainer.length; ++i) {
            insert(wordsContainer[i], i);
        }

        int[] result = new int[wordsQuery.length];

        for (int i = 0; i < wordsQuery.length; ++i) {
            result[i] = search(wordsQuery[i]);
        }

        return result;
    }
}