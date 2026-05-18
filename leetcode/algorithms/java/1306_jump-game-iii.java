// Source: https://leetcode.com/problems/jump-game-iii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-05-17
// At the time of submission:
//   Runtime 1 ms Beats 99.73%
//   Memory 52.39 MB Beats 40.41%

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

class Solution {
    // DFS/BFS over indices; each index can jump left or right by arr[i].
    // Mark visited indices in-place to avoid cycles and revisits.
    // Return true immediately when a reachable index contains 0.
    // Time: O(n), each index visited once; Space: O(n) recursion stack.
    public boolean canReach(int[] arr, int start) {
        if (start < 0 || start >= arr.length || arr[start] < 0) {
            return false;
        }

        if (arr[start] == 0) {
            return true;
        }

        int jump = arr[start];
        arr[start] = -1; // Mark as visited

        return canReach(arr, start + jump)
            || canReach(arr, start - jump);
    }
}