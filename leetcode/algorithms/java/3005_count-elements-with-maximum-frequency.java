// Source: https://leetcode.com/problems/count-elements-with-maximum-frequency/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-21
// At the time of submission:
//   Runtime 1 ms Beats 99.44%
//   Memory 42.30 MB Beats 56.53%

/****************************************
* 
* You are given an array `nums` consisting of positive integers.
* Return the total frequencies of elements in `nums`
* _ such that those elements all have the maximum frequency.
* The frequency of an element is the number of occurrences
* _ of that element in the array.
*
* Example 1:
* Input: nums = [1,2,2,3,1,4]
* Output: 4
* Explanation: The elements 1 and 2 have a frequency of 2 which is
* _ the maximum frequency in the array.
* So the number of elements in the array with maximum frequency is 4.
*
* Example 2:
* Input: nums = [1,2,3,4,5]
* Output: 5
* Explanation: All elements of the array have a frequency of 1 which is the maximum.
* So the number of elements in the array with maximum frequency is 5.
*
* Constraints:
* • 1 <= nums.length <= 100
* • 1 <= nums[i] <= 100
* 
****************************************/

class Solution {
    // Count frequencies of each number (1–100) using an array. Then find the
    // maximum frequency and how many elements share it. The answer is simply
    // (#elements with max frequency) * (that frequency). Time complexity is
    // O(n + k) where n = nums.length and k = 101. Space complexity is O(k).
    public int maxFrequencyElements(int[] nums) {
        int[] counts = new int[101];
        for (int i = 0; i < nums.length; i++) counts[nums[i]]++;
        int maxFreq = 0, repeats = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > maxFreq) {
                maxFreq = counts[i];
                repeats = 1;
            } else if (counts[i] == maxFreq) {
                repeats++;
            }
        }
        return repeats * maxFreq;
    }
}