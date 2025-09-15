// Source: https://leetcode.com/problems/vowel-spellchecker/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-14
// At the time of submission:
//   Runtime 19 ms Beats 92.82%
//   Memory 48.50 MB Beats 56.46%

/****************************************
* 
* Given a `wordlist`, we want to implement a spellchecker that converts a
* _ query word into a correct word.
* For a given `query` word, the spell checker handles
* _ two categories of spelling mistakes:
* • Capitalization: If the query matches a word in the wordlist (case-insensitive),
* _ then the query word is returned with the same case as the case in the wordlist.
* _ • Example: wordlist = ["yellow"], query = "YellOw": correct = "yellow"
* _ • Example: wordlist = ["Yellow"], query = "yellow": correct = "Yellow"
* _ • Example: wordlist = ["yellow"], query = "yellow": correct = "yellow"
* • Vowel Errors: If after replacing the vowels ('a', 'e', 'i', 'o', 'u') of the
* _ query word with any vowel individually, it matches a word in the wordlist
* _ (case-insensitive), then the query word is returned with the same case as the
* _ match in the wordlist.
* _ • Example: wordlist = ["YellOw"], query = "yollow": correct = "YellOw"
* _ • Example: wordlist = ["YellOw"], query = "yeellow": correct = "" (no match)
* _ • Example: wordlist = ["YellOw"], query = "yllw": correct = "" (no match)
* In addition, the spell checker operates under the following precedence rules:
* • When the query exactly matches a word in the wordlist (case-sensitive), you
* __ should return the same word back.
* • When the query matches a word up to capitalization, you should return the
* __ first such match in the wordlist.
* • When the query matches a word up to vowel errors, you should return the
* __ first such match in the wordlist.
* • If the query has no matches in the wordlist, you should return the empty string.
* Given some queries, return a list of words answer, where answer[i] is the
* _ correct word for query = queries[i].
*
* Example 1:
* Input: wordlist = ["KiTe","kite","hare","Hare"], queries = ["kite","Kite","KiTe","Hare","HARE","Hear","hear","keti","keet","keto"]
* Output: ["kite","KiTe","KiTe","Hare","hare","","","KiTe","","KiTe"]
*
* Example 2:
* Input: wordlist = ["yellow"], queries = ["YellOw"]
* Output: ["yellow"]
*
* Constraints:
* • 1 <= wordlist.length, queries.length <= 5000
* • 1 <= wordlist[i].length, queries[i].length <= 7
* • `wordlist[i]` and `queries[i]` consist only of only English letters.
* 
****************************************/

class Solution {
    // Build three maps: exact words, lowercase words, and vowel-normalized words.
    // Vowel normalization replaces every vowel with 'a' to unify all variations.
    // Iterate wordlist backward so earlier words are preserved as first matches.
    // For each query, check exact > lowercase > vowel-normalized precedence.
    // Time complexity: O((N + Q) * L), where L ≤ 7. Space: O(N * L).
    public String[] spellchecker(String[] wordlist, String[] queries) {
        int m = wordlist.length, n = queries.length;
        String[] result = new String[n];

        // Maps for exact, case-insensitive, and vowel-insensitive lookups
        Map<String, Integer> exactMatch = new HashMap<>();
        Map<String, Integer> caseInsensitive = new HashMap<>();
        Map<String, Integer> vowelInsensitive = new HashMap<>();

        // Build maps from wordlist (iterate backwards so first occurrence is kept)
        for (int wordIndex = m - 1; wordIndex >= 0; wordIndex--) {
            String word = wordlist[wordIndex];
            exactMatch.put(word, wordIndex);

            String lower = word.toLowerCase();
            caseInsensitive.put(lower, wordIndex);

            char[] normalized = lower.toCharArray();
            for (int charIndex = 0; charIndex < normalized.length; charIndex++) {
                if (normalized[charIndex] == 'e' || normalized[charIndex] == 'i' ||
                    normalized[charIndex] == 'o' || normalized[charIndex] == 'u') {
                    normalized[charIndex] = 'a';
                }
            }
            vowelInsensitive.put(new String(normalized), wordIndex);
        }

        // Process each query
        for (int queryIndex = 0; queryIndex < n; queryIndex++) {
            String query = queries[queryIndex];

            if (exactMatch.containsKey(query)) {
                result[queryIndex] = query;
                continue;
            }

            String lower = query.toLowerCase();
            if (caseInsensitive.containsKey(lower)) {
                result[queryIndex] = wordlist[caseInsensitive.get(lower)];
                continue;
            }

            char[] normalized = lower.toCharArray();
            for (int charIndex = 0; charIndex < normalized.length; charIndex++) {
                if (normalized[charIndex] == 'e' || normalized[charIndex] == 'i' ||
                    normalized[charIndex] == 'o' || normalized[charIndex] == 'u') {
                    normalized[charIndex] = 'a';
                }
            }
            String normalizedQuery = new String(normalized);
            if (vowelInsensitive.containsKey(normalizedQuery)) {
                result[queryIndex] = wordlist[vowelInsensitive.get(normalizedQuery)];
                continue;
            }

            result[queryIndex] = "";
        }

        return result;
    }
}
