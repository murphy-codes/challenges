// Source: https://leetcode.com/problems/sort-vowels-in-a-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-10
// At the time of submission:
//   Runtime 5 ms Beats 99.89%
//   Memory 45.68 MB Beats 94.75%

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
    // This solution sorts only vowels by using ASCII frequency counts. It first
    // counts all characters, then replaces vowels in ascending ASCII order while
    // leaving consonants untouched. Early exit is used if no vowels exist.
    // Time complexity: O(n + A), where n = length of string, A = 128 ASCII size.
    // Space complexity: O(A), using fixed arrays for frequency and vowel checks.
    public String sortVowels(String s) {
        // Vowels ordered by ASCII value
        char[] vowels = {'A','E','I','O','U','a','e','i','o','u'};
        
        // Frequency count for all ASCII chars
        int[] charFreq = new int[128];
        char[] sChars = s.toCharArray();
        for (char ch : sChars) {
            charFreq[ch]++;
        }
        
        // Check if any vowel exists at all
        boolean hasVowel = false;
        for (char v : vowels) {
            hasVowel |= charFreq[v] > 0;
        }
        if (!hasVowel) return s;
        
        // Mark which ASCII chars are vowels present in string
        boolean[] isVowel = new boolean[128];
        for (char v : vowels) {
            if (charFreq[v] > 0) isVowel[v] = true;
        }
        
        // Rebuild string: replace vowels in sorted ASCII order
        int pos = 0;
        for (char v : vowels) {
            while (charFreq[v] > 0) {
                char ch = sChars[pos];
                charFreq[v] -= isVowel[ch] ? 1 : 0;
                sChars[pos] = isVowel[ch] ? v : ch;
                pos++;
            }
        }
        
        return new String(sChars);
    }
}
