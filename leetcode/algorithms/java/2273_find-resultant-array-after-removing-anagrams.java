// Source: https://leetcode.com/problems/find-resultant-array-after-removing-anagrams/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-12
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 44.82 MB Beats 48.48%

/****************************************
* 
* You are given a 0-indexed string array `words`, where `words[i]` consists of
* _ lowercase English letters.
* In one operation, select any index `i` such that `0 < i < words.length` and
* _ `words[i - 1]` and `words[i]` are anagrams, and delete `words[i]` from words.
* _ Keep performing this operation as long as you can select an index that
* _ satisfies the conditions.
* Return `words` after performing all operations. It can be shown that selecting
* _ the indices for each operation in any arbitrary order will lead to the
* _ same result.
* An Anagram is a word or phrase formed by rearranging the letters of a different
* _ word or phrase using all the original letters exactly once. For example,
* _ `"dacb"` is an anagram of `"abdc"`.
*
* Example 1:
* Input: words = ["abba","baba","bbaa","cd","cd"]
* Output: ["abba","cd"]
* Explanation:
* One of the ways we can obtain the resultant array is by using the following operations:
* - Since words[2] = "bbaa" and words[1] = "baba" are anagrams, we choose index 2 and delete words[2].
* Now words = ["abba","baba","cd","cd"].
* - Since words[1] = "baba" and words[0] = "abba" are anagrams, we choose index 1 and delete words[1].
* Now words = ["abba","cd","cd"].
* - Since words[2] = "cd" and words[1] = "cd" are anagrams, we choose index 2 and delete words[2].
* Now words = ["abba","cd"].
* We can no longer perform any operations, so ["abba","cd"] is the final answer.
*
* Example 2:
* Input: words = ["a","b","c","d","e"]
* Output: ["a","b","c","d","e"]
* Explanation:
* No two adjacent strings in words are anagrams of each other, so no operations are performed.
*
* Constraints:
* • `1 <= words.length <= 100`
* • `1 <= words[i].length <= 10`
* • `words[i]` consists of lowercase English letters.
* 
****************************************/

import java.util.AbstractList;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

class Solution {
    // Builds a lazy-evaluated list that removes consecutive anagrams.
    // Uses sorted-character "signatures" to compare adjacent words quickly.
    // Defers computation until list access via AbstractList methods.
    // Time: O(N × L log L) for sorting each word; Space: O(N × L) for results.
    // Efficient and clean, though uses an unconventional AbstractList approach.

    List<String> result;

    public List<String> removeAnagrams(String[] words) {
        return new AbstractList<String>() {
            @Override
            public int size() {
                initialize();
                return result.size();
            }

            @Override
            public String get(int index) {
                initialize();
                return result.get(index);
            }

            // Lazily initializes result list on first access
            protected void initialize() {
                if (result != null) return;

                result = new ArrayList<>();
                result.add(words[0]);

                String prevSignature = canonicalForm(words[0]);
                for (int i = 1; i < words.length; i++) {
                    String currentSignature = canonicalForm(words[i]);
                    if (!currentSignature.equals(prevSignature)) {
                        result.add(words[i]);
                        prevSignature = currentSignature;
                    }
                }
            }
        };
    }

    // Returns canonical sorted form of word (for anagram comparison)
    private static String canonicalForm(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
