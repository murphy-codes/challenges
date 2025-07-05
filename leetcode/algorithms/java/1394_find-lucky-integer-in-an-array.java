// Source: https://leetcode.com/problems/find-lucky-integer-in-an-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-05
// At the time of submission:
//   Runtime 4 ms Beats 73.84%
//   Memory 43.34 MB Beats 58.02%

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

import java.util.HashMap;
class Solution {
    // Count the frequency of each number using a HashMap.  
    // Then, scan for values where the number equals its frequency.  
    // Track and return the largest such value (lucky number).  
    // Time complexity: O(n), where n is the length of arr.  
    // Space complexity: O(n) for storing unique elements in the map.
    public int findLucky(int[] arr) {
        int lucky = -1;
        HashMap<Integer, Integer> luckyNumbers = new HashMap<Integer, Integer>();
        for (int i=0; i < arr.length; ++i) {
            luckyNumbers.merge(arr[i], 1, (a, b) -> a + b);
        }
        for (int num : luckyNumbers.keySet()) {
            if(num==luckyNumbers.get(num) && num > lucky) { lucky = num; }
        }
        return lucky;
    }
}