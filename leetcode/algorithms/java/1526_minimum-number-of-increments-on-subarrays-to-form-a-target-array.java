// Source: https://leetcode.com/problems/minimum-number-of-increments-on-subarrays-to-form-a-target-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-30
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 56.60 MB Beats 88.45%

/****************************************
* 
* You are given an integer array `target`. You have an integer array `initial`
* _ of the same size as `target` with all elements initially zeros.
* In one operation you can choose any subarray from `initial` and
* _ increment each value by one.
* Return the minimum number of operations to form a `target` array from `initial`.
* The test cases are generated so that the answer fits in a 32-bit integer.
*
* Example 1:
* Input: target = [1,2,3,2,1]
* Output: 3
* Explanation: We need at least 3 operations to form the target array from the initial array.
* [0,0,0,0,0] increment 1 from index 0 to 4 (inclusive).
* [1,1,1,1,1] increment 1 from index 1 to 3 (inclusive).
* [1,2,2,2,1] increment 1 at index 2.
* [1,2,3,2,1] target array is formed.
*
* Example 2:
* Input: target = [3,1,1,2]
* Output: 4
* Explanation: [0,0,0,0] -> [1,1,1,1] -> [1,1,1,2] -> [2,1,1,2] -> [3,1,1,2]
*
* Example 3:
* Input: target = [3,1,5,4,2]
* Output: 7
* Explanation: [0,0,0,0,0] -> [1,1,1,1,1] -> [2,1,1,1,1] -> [3,1,1,1,1] -> [3,1,2,2,2] -> [3,1,3,3,2] -> [3,1,4,4,2] -> [3,1,5,4,2].
*
* Constraints:
* • `1 <= target.length <= 10^5`
* • `1 <= target[i] <= 10^5`
* 
****************************************/

class Solution {
    // Compute the min operations by summing all positive increases between
    // consecutive elements. Each increase indicates new increments needed
    // beyond the previous peak height. The first element always contributes
    // its own value. Time Complexity: O(n), Space Complexity: O(1).
    public int minNumberOperations(int[] target) {
        int operations = target[0];  // first element requires this many increments
        for (int i = 1; i < target.length; i++) {
            if (target[i] > target[i - 1]) {
                operations += target[i] - target[i - 1];  // only count upward jumps
            }
        }
        return operations;
    }
}
