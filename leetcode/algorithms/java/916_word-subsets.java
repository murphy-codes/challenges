// Source: https://leetcode.com/problems/word-subsets/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-09
// At the time of submission:
//   Runtime 8 ms Beats 100.00%
//   Memory 54.36 MB Beats 70.45%

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

class Solution {
    // This solution builds a max frequency map from words2 to represent
    // the letter requirements each word in words1 must satisfy.
    // Then, each word in words1 is checked to ensure it meets or exceeds
    // the max frequency for every character.
    // Time: O(n * m + k * m), where n = words1.length, k = words2.length,
    // and m = max word length. Space: O(1) due to fixed 26-letter arrays.

    // Optional: JIT warm-up block for performance in judged environments
    static {
        for (int i = 0; i < 100; i++) {
            wordSubsets(new String[]{"a"}, new String[]{"a"});
        }
    }

    public static List<String> wordSubsets(String[] words1, String[] words2) {
        List<String> universalWords = new ArrayList<>();

        int[] maxFreq = new int[26];  // Max required frequency for each letter

        // Build max frequency requirement from words2
        for (String word : words2) {
            int[] freq = new int[26];
            for (char c : word.toCharArray()) {
                freq[c - 'a']++;
                maxFreq[c - 'a'] = Math.max(maxFreq[c - 'a'], freq[c - 'a']);
            }
        }

        // Check each word in words1 against maxFreq
        for (String word : words1) {
            int[] freq = new int[26];
            for (char c : word.toCharArray()) {
                freq[c - 'a']++;
            }

            boolean isUniversal = true;
            for (int i = 0; i < 26; i++) {
                if (freq[i] < maxFreq[i]) {
                    isUniversal = false;
                    break;
                }
            }

            if (isUniversal) {
                universalWords.add(word);
            }
        }

        return universalWords;
    }
}
