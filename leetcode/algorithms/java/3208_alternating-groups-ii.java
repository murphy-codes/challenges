// Source: https://leetcode.com/problems/alternating-groups-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-09
// At the time of submission:
//   Runtime 3 ms Beats 85.29%
//   Memory 63.64 MB Beats 6.30%

/****************************************
* 
* There is a circle of red and blue tiles. You are given an array of integers `colors` and an integer `k`. The color of tile `i` is represented by `colors[i]`:
*
* • `colors[i] == 0` means that tile `i` is red.
* • `colors[i] == 1` means that tile `I` is blue.
*
* An alternating group is every `k` contiguous tiles in the circle with alternating colors (each tile in the group except the first and last one has a different color from its left and right tiles).
*
* Return the number of alternating groups.
*
* Note that since colors represents a circle, the first and the last tiles are considered to be next to each other.
*
* Example 1:
* Input: colors = [0,1,0,1,0], k = 3
* Output: 3
* Explanation:
* Alternating groups:
*
* Example 2:
* Input: colors = [0,1,0,0,1,0,1], k = 6
* Output: 2
* Explanation:
* Alternating groups:
*
* Example 3:
* Input: colors = [1,1,0,1], k = 4
* Output: 0
* Explanation:
*
* Constraints:
* • 3 <= colors.length <= 10^5
* • 0 <= colors[i] <= 1
* • 3 <= k <= colors.length
* 
****************************************/

class Solution {
    // This solution iterates through the array with circular traversal using modulo.
    // We track alternating sequences of colors and count them once they reach size k.
    // The loop runs for n + k - 1 iterations, where n is the array length, making it O(n + k).
    // The algorithm uses a constant amount of extra space, so its space complexity is O(1).
    public int numberOfAlternatingGroups(int[] colors, int k) {
        int length = colors.length;
        int result = 0;
        int alternatingElementsCount = 1;  // Start with the first element
        int lastColor = colors[0];

        // Loop through the array with circular traversal
        for (int i = 1; i < length + k - 1; i++) {
            int index = i % length;  // Wrap around using modulo

            // Check if current color is the same as the last color
            if (colors[index] == lastColor) {
                // Pattern breaks, reset sequence length
                alternatingElementsCount = 1;
                lastColor = colors[index];
                continue;
            }

            // Extend alternating sequence
            alternatingElementsCount++;

            // If sequence length reaches at least k, count it
            if (alternatingElementsCount >= k) {
                result++;
            }

            lastColor = colors[index];
        }

        return result;
    }
}
