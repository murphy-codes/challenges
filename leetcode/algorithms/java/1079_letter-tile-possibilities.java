// Source: https://leetcode.com/problems/letter-tile-possibilities/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-17

/****************************************
* 
* You have `n` `tiles`, where each tile has one letter `tiles[i]` printed on it.
* Return the number of possible non-empty sequences of letters you can make using the letters printed on those `tiles`.
* 
* Example 1:
* Input: tiles = "AAB"
* Output: 8
* Explanation: The possible sequences are "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA".
* 
* Example 2:
* Input: tiles = "AAABBC"
* Output: 188
* 
* Example 3:
* Input: tiles = "V"
* Output: 1
* 
* Constraints:
* • 1 <= tiles.length <= 7
* • `tiles` consists of uppercase English letters.
* 
****************************************/

import java.util.HashMap;
import java.util.Map;

class Solution {
    // This solution uses backtracking with a frequency map to generate all unique
    // non-empty sequences. At each step, we pick an available letter, reduce its
    // count, and recursively explore further. The time complexity is O(n!), as we
    // generate permutations with pruning. The space complexity is O(n) due to the
    // recursion depth and frequency map storage.
    public int numTilePossibilities(String tiles) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : tiles.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        return backtrack(freq);
    }

    private int backtrack(Map<Character, Integer> freq) {
        int count = 0;
        for (char c : freq.keySet()) {
            if (freq.get(c) == 0) continue;

            // Choose this character
            freq.put(c, freq.get(c) - 1);
            count += 1 + backtrack(freq); // Count current and recursive sequences
            freq.put(c, freq.get(c) + 1); // Undo the choice (backtrack)
        }
        return count;
    }
}
