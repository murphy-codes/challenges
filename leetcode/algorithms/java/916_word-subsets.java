// Source: https://leetcode.com/problems/word-subsets/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-09

/****************************************
* 
* You are given two string arrays `words1` and `words2`.
* A string `b` is a subset of string `a` if every letter in `b` occurs in `a` including multiplicity.
* • For example, "wrr" is a subset of "warrior" but is not a subset of "world".
* A string `a` from `words1` is universal if for every string `b` in `words2`, `b` is a subset of `a`.
* Return an array of all the universal strings in `words1`. You may return the answer in any order.
* 
* Example 1:
* Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["e","o"]
* Output: ["facebook","google","leetcode"]
* 
* Example 2:
* Input: words1 = ["amazon","apple","facebook","google","leetcode"], words2 = ["l","e"]
* Output: ["apple","google","leetcode"]
* 
* Constraints:
* • 1 <= words1.length, words2.length <= 10^4
* • 1 <= words1[i].length, words2[i].length <= 10
* • words1[i] and words2[i] consist only of lowercase English letters.
* • All the strings of words1 are unique.
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {
    // We'll solve this using frequency arrays of size 26 to represent lowercase English letters.
    // First, we create a "max frequency map" from words2, representing the maximum count of each letter required.
    // Next, for each string in words1, we compute its frequency array and compare it against the max frequency map.
    // If a string satisfies all the requirements, it's added to the result list.
    // This approach runs in O(n1 * m + n2 * m) time, where n1 and n2 are the lengths of words1 and words2, 
    // and m is the average string length. Space complexity is O(26) or O(1), as the frequency arrays have a constant size.
    public List<String> wordSubsets(String[] words1, String[] words2) {
        // Step 1: Build maxFreq for words2
        int[] maxFreq = new int[26];
        for (String word : words2) {
            int[] freq = getFrequency(word);
            for (int i = 0; i < 26; i++) {
                maxFreq[i] = Math.max(maxFreq[i], freq[i]);
            }
        }

        // Step 2: Compare words1 against maxFreq
        List<String> result = new ArrayList<>();
        for (String word : words1) {
            int[] freq = getFrequency(word);
            if (isUniversal(freq, maxFreq)) {
                result.add(word);
            }
        }

        return result;
    }

    // Helper to calculate character frequencies
    private int[] getFrequency(String word) {
        int[] freq = new int[26];
        for (char c : word.toCharArray()) {
            freq[c - 'a']++;
        }
        return freq;
    }

    // Helper to check if freq1 satisfies freq2
    private boolean isUniversal(int[] freq1, int[] maxFreq) {
        for (int i = 0; i < 26; i++) {
            if (freq1[i] < maxFreq[i]) {
                return false;
            }
        }
        return true;
    }
}
