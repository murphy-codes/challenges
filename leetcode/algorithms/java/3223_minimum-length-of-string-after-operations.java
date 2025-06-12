// Source: https://leetcode.com/problems/minimum-length-of-string-after-operations/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-12
// At the time of submission:
//   Runtime 84 ms Beats 33.12%
//   Memory 51.12 MB Beats 8.93%

/****************************************
* 
* You are given a string `s`.
* You can perform the following process on `s` any number of times:
* • Choose an index `I` in the string such that there is at least one character to the left of index `I` that is equal to `s[i]`, and at least one character to the right that is also equal to `s[i]`.
* • Delete the closest character to the left of index i that is equal to `s[i]`.
* • Delete the closest character to the right of index i that is equal to `s[i]`.
* Return the minimum length of the final string s that you can achieve.
* 
* Example 1:
* Input: s = "abaacbcbb"
* Output: 5
* Explanation:
* We do the following operations:
* Choose index 2, then remove the characters at indices 0 and 3. The resulting string is s = "bacbcbb".
* Choose index 3, then remove the characters at indices 0 and 5. The resulting string is s = "acbcb".
* 
* Example 2:
* Input: s = "aa"
* Output: 2
* Explanation:
* We cannot perform any operations, so we return the length of the original string.
* 
* Constraints:
* • 1 <= s.length <= 2 * 10^5
* • `s` consists only of lowercase English letters.
* 
****************************************/

import java.util.HashMap;

class Solution {
    // Count character frequencies in the string.
    // For each character, remove extra occurrences based on even/odd frequencies.
    // Subtract deletions from the original length to get the result.
    // Time complexity: O(n), Space complexity: O(k).
    public int minimumLength(String s) {
        // Initialize a frequency map to count the occurrences of each character
        HashMap<Character, Integer> charFrequencyMap = new HashMap<>();
        
        // Fill the frequency map
        for (char c : s.toCharArray()) {
            charFrequencyMap.put(c, charFrequencyMap.getOrDefault(c, 0) + 1);
        }

        // Initialize deleteCount to keep track of how many characters we need to remove
        int deleteCount = 0;

        // Iterate through the frequency map to calculate deletions based on odd/even counts
        for (int frequency : charFrequencyMap.values()) {
            if (frequency % 2 == 0) {
                // If frequency is even, we delete all but two characters
                deleteCount += frequency - 2;
            } else {
                // If frequency is odd, we delete all but one character
                deleteCount += frequency - 1;
            }
        }

        // The result is the length of the string minus the deletions
        return s.length() - deleteCount;
    }
}
