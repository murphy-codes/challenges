// Source: https://leetcode.com/problems/three-consecutive-odds/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-11
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.78 MB Beats 13.34%

/****************************************
* 
* Given an integer array `arr`, return `true` if there are three consecutive odd
* _ numbers in the array. Otherwise, return `false`.
*
* Example 1:
* Input: arr = [2,6,4,1]
* Output: false
* Explanation: There are no three consecutive odds.
*
* Example 2:
* Input: arr = [1,2,34,3,4,5,7,23,12]
* Output: true
* Explanation: [5,7,23] are three consecutive odds.
*
* Constraints:
* • 1 <= arr.length <= 1000
* • 1 <= arr[i] <= 1000
* 
****************************************/

class Solution {
    // Iterate through the array and count consecutive odd numbers.
    // Reset count to 0 when an even number is found.
    // Return true as soon as 3 consecutive odds are detected.
    // Time complexity: O(n), single pass through the array.
    // Space complexity: O(1), using a constant counter variable.
    public boolean threeConsecutiveOdds(int[] arr) {
        int count = 0;
        for (int num : arr) {
            if (num % 2 == 0) count = 0; // Reset counter at every even number
            else count++; // Increment counter at every odd number
            if (count == 3) return true; // Check counter after every odd number
        }
        return false;
    }
}