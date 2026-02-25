// Source: https://leetcode.com/problems/sort-integers-by-the-number-of-1-bits/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-24
// At the time of submission:
//   Runtime 7 ms Beats 78.21%
//   Memory 46.80 MB Beats 52.92%

/****************************************
* 
* You are given an integer array `arr`. Sort the integers in the array in
* _ ascending order by the number of `1`'s in their binary representation and
* _ in case of two or more integers have the same number of `1`'s you have to
* _ sort them in ascending order.
* Return the array after sorting it.
*
* Example 1:
* Input: arr = [0,1,2,3,4,5,6,7,8]
* Output: [0,1,2,4,8,3,5,6,7]
* Explantion: [0] is the only integer with 0 bits.
* [1,2,4,8] all have 1 bit.
* [3,5,6] have 2 bits.
* [7] has 3 bits.
* The sorted array by bits is [0,1,2,4,8,3,5,6,7]
*
* Example 2:
* Input: arr = [1024,512,256,128,64,32,16,8,4,2,1]
* Output: [1,2,4,8,16,32,64,128,256,512,1024]
* Explantion: All integers have 1 bit in the binary representation, you should just sort them in ascending order.
*
* Constraints:
* • `1 <= arr.length <= 500`
* • `0 <= arr[i] <= 10^4`
* 
****************************************/

import java.util.Arrays;
import java.util.Comparator;

class Solution {
    // Sort numbers by count of 1-bits using a custom comparator.
    // Integer.bitCount provides fast bit counting per value.
    // Primary sort key is bit count; secondary key is numeric value.
    // Sorting dominates runtime: O(n log n).
    // Space complexity is O(n) due to boxing the array.
    public int[] sortByBits(int[] arr) {
        // Box int[] to Integer[] so we can use a custom comparator
        Integer[] boxed = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            boxed[i] = arr[i];
        }

        Arrays.sort(boxed, (a, b) -> {
            int bitDiff = Integer.bitCount(a) - Integer.bitCount(b);
            return bitDiff != 0 ? bitDiff : a - b;
        });

        // Unbox back to int[]
        for (int i = 0; i < arr.length; i++) {
            arr[i] = boxed[i];
        }

        return arr;
    }
}