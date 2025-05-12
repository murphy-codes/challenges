// Source: https://leetcode.com/problems/finding-3-digit-even-numbers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-11
// At the time of submission:
//   Runtime 4 ms Beats 96.05%
//   Memory 45.18 MB Beats 45.64%

/****************************************
* 
* You are given an integer array `digits`, where each element is a digit.
* _ The array may contain duplicates.
* You need to find all the unique integers that follow the given requirements:
* • The integer consists of the concatenation of three elements from digits in
* _ any arbitrary order.
* • The integer does not have leading zeros.
* • The integer is even.
* For example, if the given `digits` were `[1, 2, 3]`, integers `132` and
* _ `312` follow the requirements.
* Return a sorted array of the unique integers.
*
* Example 1:
* Input: digits = [2,1,3,0]
* Output: [102,120,130,132,210,230,302,310,312,320]
* Explanation: All the possible integers that follow the requirements are in the output array.
* Notice that there are no odd integers or integers with leading zeros.
*
* Example 2:
* Input: digits = [2,2,8,8,2]
* Output: [222,228,282,288,822,828,882]
* Explanation: The same digit can be used as many times as it appears in digits.
* In this example, the digit 8 is used twice each time in 288, 828, and 882.
*
* Example 3:
* Input: digits = [3,7,5]
* Output: []
* Explanation: No even integers can be formed using the given digits.
*
* Constraints:
* • 3 <= digits.length <= 100
* • 0 <= digits[i] <= 9
* 
****************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    // Count occurrences of each digit in the input array.
    // Iterate through all 3-digit even numbers from 100 to 998.
    // For each number, check if its digits exist in the original input.
    // Use an array to compare digit frequencies for validity.
    // Time: O(450 * 10) = O(1); Space: O(1) extra (ignores output size).
    public int[] findEvenNumbers(int[] digits) {
        int[] digitCount = new int[10];
        for (int d : digits) digitCount[d]++;

        List<Integer> result = new ArrayList<>();

        for (int num = 100; num < 1000; num += 2) {
            int d1 = num / 100;
            int d2 = (num / 10) % 10;
            int d3 = num % 10;

            // Count digits used in this number
            int[] tempCount = new int[10];
            tempCount[d1]++;
            tempCount[d2]++;
            tempCount[d3]++;

            // Validate against available digits
            boolean isValid = true;
            for (int i = 0; i < 10; i++) {
                if (tempCount[i] > digitCount[i]) {
                    isValid = false;
                    break;
                }
            }

            if (isValid) result.add(num);
        }

        // Convert list to array
        int[] output = new int[result.size()];
        for (int i = 0; i < result.size(); i++) output[i] = result.get(i);
        return output;
    }
}