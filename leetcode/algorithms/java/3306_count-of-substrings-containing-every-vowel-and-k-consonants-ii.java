// Source: https://leetcode.com/problems/count-of-substrings-containing-every-vowel-and-k-consonants-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-09
// At the time of submission:
//   Runtime 275 ms Beats 35.64%
//   Memory 46.32 MB Beats 61.51%

/****************************************
* 
* You are given a string `word` and a non-negative integer `k`.
*
* Return the total number of substrings of `word` that contain every vowel (`'a'`, `'e'`, `'i'`, `'o'`, and `'u'`) at least once and exactly `k` consonants.
*
* Example 1:
* Input: word = "aeioqq", k = 1
* Output: 0
* Explanation:
* There is no substring with every vowel.
*
* Example 2:
* Input: word = "aeiou", k = 0
* Output: 1
* Explanation:
* The only substring with every vowel and zero consonants is word[0..4], which is "aeiou".
*
* Example 3:
* Input: word = "ieaouqqieaouqq", k = 1
* Output: 3
* Explanation:
* The substrings with every vowel and one consonant are:
* • word[0..5], which is "ieaouq".
* • word[6..11], which is "qieaou".
* • word[7..12], which is "ieaouq".
*
* Constraints:
* • 5 <= word.length <= 2 * 10^5
* • `word` consists only of lowercase English letters.
* • 0 <= k <= word.length - 5
* 
****************************************/

class Solution {
    // This solution uses a sliding window approach to count substrings with exactly
    // k consonants and all 5 vowels. The window expands by adding characters, and
    // shrinks when the number of consonants exceeds k. We count valid substrings when
    // the window contains all 5 vowels and the consonant count is valid. The time
    // complexity is O(N) due to the linear scan of the string, and space complexity
    // is O(1) since we only use fixed-size data structures (vowelCount and consonantCount).
    public long countOfSubstrings(String word, int k) {
        return atLeastK(word, k) - atLeastK(word, k + 1);
    }

    private long atLeastK(String word, int k) {
        long numValidSubstrings = 0;
        int start = 0;
        int end = 0;
        // Keep track of counts of vowels and consonants
        HashMap<Character, Integer> vowelCount = new HashMap<>();
        int consonantCount = 0;

        // Start sliding window
        while (end < word.length()) {
            // Insert new letter
            char newLetter = word.charAt(end);

            // Update counts
            if (isVowel(newLetter)) {
                vowelCount.put(newLetter, vowelCount.getOrDefault(newLetter, 0) + 1);
            } else {
                consonantCount++;
            }

            // Shrink window while we have a valid substring
            while (vowelCount.size() == 5 && consonantCount >= k) {
                numValidSubstrings += word.length() - end;
                char startLetter = word.charAt(start);
                if (isVowel(startLetter)) {
                    vowelCount.put(startLetter, vowelCount.get(startLetter) - 1);
                    if (vowelCount.get(startLetter) == 0) {
                        vowelCount.remove(startLetter);
                    }
                } else {
                    consonantCount--;
                }
                start++;
            }

            end++;
        }

        return numValidSubstrings;
    }

    private boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
}
