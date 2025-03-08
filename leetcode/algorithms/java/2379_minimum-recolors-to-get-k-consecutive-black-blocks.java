// Source: https://leetcode.com/problems/minimum-recolors-to-get-k-consecutive-black-blocks/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-07

/****************************************
* 
* You are given a 0-indexed string `blocks` of length `n`, where `blocks[i]` is either `'W'` or `'B'`, representing the color of the `i^th` block. The characters `'W'` and `'B'` denote the colors white and black, respectively.
* You are also given an integer `k`, which is the desired number of consecutive black blocks.
* In one operation, you can recolor a white block such that it becomes a black block.
* Return the minimum number of operations needed such that there is at least one occurrence of k consecutive black blocks.
*
* Example 1:
* Input: blocks = "WBBWWBBWBW", k = 7
* Output: 3
* Explanation:
* One way to achieve 7 consecutive black blocks is to recolor the 0th, 3rd, and 4th blocks
* so that blocks = "BBBBBBBWBW".
* It can be shown that there is no way to achieve 7 consecutive black blocks in less than 3 operations.
* Therefore, we return 3.
*
* Example 2:
* Input: blocks = "WBWBBBW", k = 2
* Output: 0
* Explanation:
* No changes need to be made, since 2 consecutive black blocks already exist.
* Therefore, we return 0.
*
* Constraints:
* • n == blocks.length
* • 1 <= n <= 100
* • blocks[i] is either 'W' or 'B'.
* • 1 <= k <= n
* 
****************************************/

class Solution {
    // Uses a sliding window approach to find the minimum number of recolors
    // needed to get k consecutive black ('B') blocks. First, it counts 'W'
    // in the initial window, then slides the window while updating the count.
    // Time Complexity: O(n) since each character is processed once.
    // Space Complexity: O(1) as only a few integer variables are used.
    public int minimumRecolors(String blocks, int k) {
        int n = blocks.length();
        int minRecolors = Integer.MAX_VALUE;
        int whiteCount = 0;
        
        // Count 'W' in the initial window of size k
        for (int i = 0; i < k; i++) {
            if (blocks.charAt(i) == 'W') {
                whiteCount++;
            }
        }
        minRecolors = whiteCount;
        
        // Slide the window across the string
        for (int i = k; i < n; i++) {
            // Remove the leftmost element of the previous window
            if (blocks.charAt(i - k) == 'W') {
                whiteCount--;
            }
            // Add the new element to the window
            if (blocks.charAt(i) == 'W') {
                whiteCount++;
            }
            // Update the minimum recolors required
            minRecolors = Math.min(minRecolors, whiteCount);
        }
        
        return minRecolors;
    }
}
