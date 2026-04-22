// Source: https://leetcode.com/problems/words-within-two-edits-of-dictionary/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-21
// At the time of submission:
//   Runtime 2 ms Beats 97.79%
//   Memory 44.34 MB Beats 71.32%

/****************************************
* 
* You are given two string arrays, `queries` and `dictionary`. All words in
* _ each array comprise of lowercase English letters and have the same length.
* In one edit you can take a word from `queries`, and change any letter in it
* _ to any other letter. Find all words from `queries` that, after a maximum
* _ of two edits, equal some word from `dictionary`.
* Return a list of all words from `queries`, that match with some word from
* _ `dictionary` after a maximum of two edits. Return the words in the same
* _ order they appear in `queries`.
*
* Example 1:
* Input: queries = ["word","note","ants","wood"], dictionary = ["wood","joke","moat"]
* Output: ["word","note","wood"]
* Explanation:
* - Changing the 'r' in "word" to 'o' allows it to equal the dictionary word "wood".
* - Changing the 'n' to 'j' and the 't' to 'k' in "note" changes it to "joke".
* - It would take more than 2 edits for "ants" to equal a dictionary word.
* - "wood" can remain unchanged (0 edits) and match the corresponding dictionary word.
* Thus, we return ["word","note","wood"].
*
* Example 2:
* Input: queries = ["yes"], dictionary = ["not"]
* Output: []
* Explanation:
* Applying any two edits to "yes" cannot make it equal to "not". Thus, we return an empty array.
*
* Constraints:
* • `1 <= queries.length, dictionary.length <= 100`
* • `n == queries[i].length == dictionary[j].length`
* • `1 <= n <= 100`
* • All `queries[I]` and `dictionary[j]` are composed of lowercase English letters.
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {
    // For each query, compare it with every dictionary word and count
    // character differences. If any dictionary word differs by at most
    // two characters, include the query in the result. Early exit when
    // differences exceed two for efficiency. Time: O(q * d * n), Space: O(1)
    // (excluding output list).
    public List<String> twoEditWords(String[] queries, String[] dictionary) {
        List<String> result = new ArrayList<>();

        for (String q : queries) {
            for (String d : dictionary) {
                if (isWithinTwoEdits(q, d)) {
                    result.add(q);
                    break; // only need one match
                }
            }
        }

        return result;
    }

    private boolean isWithinTwoEdits(String a, String b) {
        int diff = 0;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                diff++;
                if (diff > 2) return false; // early exit
            }
        }

        return true;
    }
}