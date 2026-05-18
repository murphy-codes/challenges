// Source: https://leetcode.com/problems/jump-game-iii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-17
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 48.64 MB Beats 73.18%

/****************************************
* 
* Given an array of non-negative integers `arr`, you are initially positioned
* _ at `start` index of the array. When you are at index `i`, you can jump to
* _ `i + arr[i]` or `i - arr[i]`, check if you can reach any index with value 0.
* Notice that you can not jump outside of the array at any time.
*
* Example 1:
* Input: arr = [4,2,3,0,3,1,2], start = 5
* Output: true
* Explanation:
* All possible ways to reach at index 3 with value 0 are:
* index 5 -> index 4 -> index 1 -> index 3
* index 5 -> index 6 -> index 4 -> index 1 -> index 3
*
* Example 2:
* Input: arr = [4,2,3,0,3,1,2], start = 0
* Output: true
* Explanation:
* One possible way to reach at index 3 with value 0 is:
* index 0 -> index 4 -> index 1 -> index 3
*
* Example 3:
* Input: arr = [3,0,2,1,2], start = 2
* Output: false
* Explanation: There is no way to reach at index 1 with value 0.
*
* Constraints:
* • `1 <= arr.length <= 5 * 10^4`
* • `0 <= arr[i] < arr.length`
* • `0 <= start < arr.length`
* 
****************************************/

import java.util.ArrayList;
import java.util.List;

class Solution {

    private final List<Integer> visited = new ArrayList<>();
    
    // DFS with visited tracking to avoid infinite cycles.
    // Try both reachable indices: i + arr[i] and i - arr[i].
    // Stop when reaching value 0 or an invalid/revisited index.
    // Time: O(n²) worst-case due to List.contains(); Space: O(n).
    public boolean canReach(int[] arr, int start) {

        // Out of bounds or exhausted all indices
        if (start < 0 || start >= arr.length
                || visited.size() == arr.length) {
            return false;
        }

        // Found reachable zero
        if (arr[start] == 0) {
            return true;
        }

        // Skip already visited indices
        if (!visited.contains(start)) {

            visited.add(start);

            // Micro-optimization for direct reachability
            if (arr[start] == 1 && arr[arr.length - 1] == 0) {
                return true;
            }

            return canReach(arr, start + arr[start])
                || canReach(arr, start - arr[start]);
        }

        return false;
    }
}

// class Solution {
//     // DFS/BFS over indices; each index can jump left or right by arr[i].
//     // Mark visited indices in-place to avoid cycles and revisits.
//     // Return true immediately when a reachable index contains 0.
//     // Time: O(n), each index visited once; Space: O(n) recursion stack.
//     public boolean canReach(int[] arr, int start) {
//         if (start < 0 || start >= arr.length || arr[start] < 0) {
//             return false;
//         }

//         if (arr[start] == 0) {
//             return true;
//         }

//         int jump = arr[start];
//         arr[start] = -1; // Mark as visited

//         return canReach(arr, start + jump)
//             || canReach(arr, start - jump);
//     }
// }