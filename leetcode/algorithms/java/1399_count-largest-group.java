// Source: https://leetcode.com/count-largest-group/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-22
// At the time of submission:
//   Runtime 4 ms Beats 77.06%
//   Memory 40.68 MB Beats 72.76%

/****************************************
* 
* You are given an integer `n`.
* Each number from `1` to `n` is grouped according to the sum of its digits.
* Return the number of groups that have the largest size.
*
* Example 1:
* Input: n = 13
* Output: 4
* Explanation: There are 9 groups in total, they are grouped according sum of its digits of numbers from 1 to 13:
* [1,10], [2,11], [3,12], [4,13], [5], [6], [7], [8], [9].
* There are 4 groups with largest size.
*
* Example 2:
* Input: n = 2
* Output: 2
* Explanation: There are 2 groups [1], [2] of size 1.
*
* Constraints:
* • 1 <= n <= 10^4
* 
****************************************/

class Solution {
    // Groups numbers 1 to n by the sum of their digits, then finds how many
    // groups have the largest size. Uses an array for digit sums (max 36).
    // Time: O(n) for computing digit sums and tracking frequencies.
    // Space: O(1), since array size is fixed and independent of input n.
    // Fast and elegant due to bounded digit sums and direct array access.
    public int countLargestGroup(int n) {
        int[] digitSums = new int[37]; // max sum of digits for n = 9999 is 36

        for (int i = 1; i <= n; i++) {
            int sum = digitSum(i);
            digitSums[sum]++;
        }

        int maxGroupSize = 0;
        int count = 0;

        for (int size : digitSums) {
            if (size > maxGroupSize) {
                maxGroupSize = size;
                count = 1;
            } else if (size == maxGroupSize) {
                count++;
            }
        }

        return count;
    }

    private int digitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
