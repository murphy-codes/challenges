// Source: https://leetcode.com/problems/lexicographical-numbers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-08
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 48.32 MB Beats 69.42%

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
    // Perform DFS from 1–9 to simulate lexicographic traversal of numbers.
    // Each number appends a digit (0–9) recursively, avoiding values > n.
    // Mimics a pre-order traversal of a 10-ary tree rooted at 1–9.
    // Time: O(n), each number from 1 to n is added exactly once.
    // Space: O(log n) recursion stack depth (not O(1) due to recursion).
    
    public List<Integer> lexicalOrder(int n) {
        List<Integer> result = new ArrayList<>();
        for (int digit = 1; digit <= 9; digit++) {
            dfs(digit, n, result);
        }
        return result;
    }

    private void dfs(int current, int limit, List<Integer> result) {
        if (current > limit) return;

        result.add(current);

        for (int nextDigit = 0; nextDigit <= 9; nextDigit++) {
            int next = current * 10 + nextDigit;
            if (next > limit) break;
            dfs(next, limit, result);
        }
    }
}
