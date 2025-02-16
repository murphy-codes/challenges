// Source: https://leetcode.com/problems/construct-the-lexicographically-largest-valid-sequence/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-15

/****************************************
* 
* Given an integer `n`, find a sequence that satisfies all of the following:
* • The integer `1` occurs once in the sequence.
* • Each integer between `2` and `n` occurs twice in the sequence.
* • For every integer `i` between `2` and `n`, the distance between the two occurrences of `i` is exactly `i`.
* The distance between two numbers on the sequence, `a[i]` and `a[j]`, is the absolute difference of their indices, `|j - i|`.
* Return the lexicographically largest sequence. It is guaranteed that under the given constraints, there is always a solution.
* A sequence `a` is lexicographically larger than a sequence `b` (of the same length) if in the first position where `a` and `b` differ, sequence a has a number greater than the corresponding number in `b`. For example, `[0,1,9,0]` is lexicographically larger than `[0,1,5,6]` because the first position they differ is at the third number, and `9` is greater than `5`.
* 
* Example 1:
* Input: n = 3
* Output: [3,1,2,3,2]
* Explanation: [2,3,2,1,3] is also a valid sequence, but [3,1,2,3,2] is the lexicographically largest valid sequence.
* 
* Example 2:
* Input: n = 5
* Output: [5,3,1,4,3,5,2,4,2]
* 
* Constraints:
* • 1 <= n <= 20
* 
****************************************/

class Solution {
    // Backtracking approach: place numbers from n to 1 in lexicographic order.
    // Ensure each i appears twice, with distance i, and backtrack on conflicts.
    // Time: O(2^n) worst case, but pruning makes it feasible for n ≤ 20.
    // Space: O(n) for result array and used number tracking.
    public int[] constructDistancedSequence(int n) {
        int size = 2 * n - 1;  // The total length of the sequence
        int[] result = new int[size];  // The sequence to be constructed
        boolean[] used = new boolean[n + 1];  // Track used numbers
        
        backtrack(result, used, 0, n);
        return result;
    }

    private boolean backtrack(int[] result, boolean[] used, int index, int n) {
        if (index == result.length) return true;  // Found a valid sequence
        
        if (result[index] != 0) return backtrack(result, used, index + 1, n);  // Skip filled indices
        
        for (int num = n; num >= 1; num--) {  // Try placing numbers from n to 1
            if (used[num]) continue;  // Skip already used numbers

            int secondIndex = (num > 1) ? index + num : index;
            if (secondIndex < result.length && result[secondIndex] == 0) {
                // Place the number at both valid positions
                result[index] = num;
                if (num > 1) result[secondIndex] = num;
                used[num] = true;

                if (backtrack(result, used, index + 1, n)) return true;  // Continue to the next index

                // Backtrack and undo the placement
                result[index] = 0;
                if (num > 1) result[secondIndex] = 0;
                used[num] = false;
            }
        }
        return false;  // No valid placement found
    }
}
