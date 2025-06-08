// Source: https://leetcode.com/problems/lexicographical-numbers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-08
// At the time of submission:
//   Runtime 5 ms Beats 56.66%
//   Memory 48.86 MB Beats 26.64%

/****************************************
* 
* Given an integer `n`, return all the numbers in the range `[1, n]` sorted in
* _ lexicographical order.
* You must write an algorithm that runs in `O(n)` time and uses `O(1)` extra space.
*
* Example 1:
* Input: n = 13
* Output: [1,10,11,12,13,2,3,4,5,6,7,8,9]
*
* Example 2:
* Input: n = 2
* Output: [1,2]
*
* Constraints:
* • 1 <= n <= 5 * 10^4
* 
****************************************/

import java.util.List;
import java.util.ArrayList;

class Solution {
    // Traverse numbers in lexicographic order using DFS-like iteration.
    // Each number is visited once, building the next number by descending 
    // into its 10x child (e.g. 1 → 10), or backtracking and incrementing 
    // to the next sibling (e.g. 19 → 2). Skips invalid numbers above n.
    // Time: O(n) as each number from 1 to n is processed once.
    // Space: O(1) extra space (excluding output list).
    public List<Integer> lexicalOrder(int n) {
        List<Integer> result = new ArrayList<>(n);
        int curr = 1;

        for (int i = 0; i < n; i++) {
            result.add(curr);
            if (curr * 10 <= n) {
                curr *= 10; // go down a level
            } else {
                while (curr % 10 == 9 || curr + 1 > n) {
                    curr /= 10; // backtrack to valid parent
                }
                curr++; // go to next sibling
            }
        }

        return result;
    }
}
