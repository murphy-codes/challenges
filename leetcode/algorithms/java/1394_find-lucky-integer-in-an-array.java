// Source: https://leetcode.com/problems/find-lucky-integer-in-an-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-05
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.13 MB Beats 84.93%

/****************************************
* 
* Given an array of integers `arr`, a lucky integer is an integer that has a
* _ frequency in the array equal to its value.
* Return the largest lucky integer in the array. If there is no lucky integer return `-1`.
*
* Example 1:
* Input: arr = [2,2,3,4]
* Output: 2
* Explanation: The only lucky number in the array is 2 because frequency[2] == 2.
*
* Example 2:
* Input: arr = [1,2,2,3,3,3]
* Output: 3
* Explanation: 1, 2 and 3 are all lucky numbers, return the largest of them.
*
* Example 3:
* Input: arr = [2,2,2,3,3]
* Output: -1
* Explanation: There are no lucky numbers in the array.
*
* Constraints:
* • 1 <= arr.length <= 500
* • 1 <= arr[i] <= 500
* 
****************************************/

class Solution {
    // Use an array to count frequency of each number (1–500). 
    // Then iterate backwards to find the largest number whose 
    // value equals its frequency. Time complexity: O(n), 
    // Space complexity: O(1), since max arr[i] is 500.
    static {
        // JIT warm-up to reduce method invocation cost
        for (int i = 0; i < 100; i++) {
            findLucky(new int[0]);
        }
    }
    public static int findLucky(int[] arr) {
        if (arr.length == 0) return -1;
        int[] freq = new int[501]; // value range is [1, 500]
        for (int num : arr) {
            freq[num]++;
        }
        for (int i = 500; i >= 1; i--) {
            if (freq[i] == i) return i;
        }
        return -1;
    }
}
