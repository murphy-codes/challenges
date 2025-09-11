// Source: https://leetcode.com/problems/sort-vowels-in-a-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-10
// At the time of submission:
//   Runtime 21 ms Beats 86.82%
//   Memory 46.10 MB Beats 78.03%

/****************************************
* 
* Given a 0-indexed string `s`, permute `s` to get a new string `t` such that:
* • All consonants remain in their original places. More formally, if there is
* __ an index `i` with `0 <= i < s.length` such that `s[i]` is a consonant,
* __ then `t[i] = s[i]`.
* • The vowels must be sorted in the nondecreasing order of their ASCII values.
* __ More formally, for pairs of indices `i`, `j` with `0 <= i < j < s.length`
* __ such that `s[i]` and `s[j]` are vowels, then `t[i]` must not have a higher
* __ ASCII value than `t[j]`.
* Return the resulting string.
* The vowels are `'a'`, `'e'`, `'i'`, `'o'`, and `'u'`, and they can appear in
* _ lowercase or uppercase. Consonants comprise all letters that are not vowels.
*
* Example 1:
* Input: s = "lEetcOde"
* Output: "lEOtcede"
* Explanation: 'E', 'O', and 'e' are the vowels in s; 'l', 't', 'c', and 'd'
* _ are all consonants. The vowels are sorted according to their ASCII values,
* _ and the consonants remain in the same places.
*
* Example 2:
* Input: s = "lYmpH"
* Output: "lYmpH"
* Explanation: There are no vowels in s (all characters in s are consonants),
* _ so we return "lYmpH".
*
* Constraints:
* • 1 <= s.length <= 10^5
* • `s` consists only of letters of the English alphabet in uppercase and lowercase.
* 
****************************************/

class Solution {
    // This solution counts all vowels (10 possible chars) and stores their
    // positions. It then reconstructs the string by placing sorted vowels
    // (by ASCII order) back into their recorded indices while leaving
    // consonants untouched. Time complexity is O(n) for scanning the string
    // and rebuilding, and space complexity is O(n) for storing indices.

    private static final Map<Character, Integer> VOWELS_TO_INT;
    private static final Map<Integer, Character> INT_TO_VOWELS;
    static {
        VOWELS_TO_INT = new HashMap<>();
        INT_TO_VOWELS = new HashMap<>();
        char[] chars = {'A','E','I','O','U','a','e','i','o','u'};
        for (int i = 0; i < chars.length; i++) {
            VOWELS_TO_INT.put(chars[i], i);
            INT_TO_VOWELS.put(i, chars[i]);
        }
    }
    
    public String sortVowels(String s) {
        int[] vowelCount = new int[10];
        List<Integer> indexes = new ArrayList<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (VOWELS_TO_INT.containsKey(chars[i])) {
                vowelCount[VOWELS_TO_INT.get(chars[i])]++;
                indexes.add(i);
            }
        }
        if (indexes.size()==0) return s;
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        int j = 0;
        for (int i = 0; i < indexes.size(); i++) {
            if (vowelCount[j]>0) {
                sb.setCharAt(indexes.get(i), INT_TO_VOWELS.get(j));
                vowelCount[j]--;
            } else {
                j++; // move to next vowel
                i--; // do not count this iteration
            }
        }
        return sb.toString();
    }
}