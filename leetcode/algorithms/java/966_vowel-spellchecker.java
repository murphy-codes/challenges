// Source: https://leetcode.com/problems/vowel-spellchecker/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-14
// At the time of submission:
//   Runtime 21 ms Beats 78.95%
//   Memory 47.46 MB Beats 81.82%

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

import java.util.HashSet;
import java.util.HashMap;

class Solution {
    // Build three structures: a set for exact matches, a map for lowercase
    // matches, and a map for vowel-normalized matches (first occurrence kept).
    // For each query, check exact > lowercase > vowel-normalized precedence.
    // Normalization replaces vowels with '*' to unify vowel errors. 
    // Time complexity: O(N * L + Q * L), where L ≤ 7. Space: O(N * L).
    public String[] spellchecker(String[] wordlist, String[] queries) {
        HashSet<String> exact = new HashSet<>();
        HashMap<String, String> caseInsensitive = new HashMap<>();
        HashMap<String, String> vowelInsensitive = new HashMap<>();

        for (String word : wordlist) {
            exact.add(word);
            String lower = word.toLowerCase();
            caseInsensitive.putIfAbsent(lower, word);
            vowelInsensitive.putIfAbsent(normalize(lower), word);
        }

        String[] result = new String[queries.length];
        for (int i = 0; i < queries.length; i++) {
            String query = queries[i];
            if (exact.contains(query)) {
                result[i] = query;
            } else {
                String lower = query.toLowerCase();
                if (caseInsensitive.containsKey(lower)) {
                    result[i] = caseInsensitive.get(lower);
                } else {
                    String norm = normalize(lower);
                    result[i] = vowelInsensitive.getOrDefault(norm, "");
                }
            }
        }
        return result;
    }

    private String normalize(String word) {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            if (isVowel(c)) {
                sb.append('*');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}
