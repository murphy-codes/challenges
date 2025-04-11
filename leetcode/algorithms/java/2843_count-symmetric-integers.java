// Source: https://leetcode.com/problems/count-symmetric-integers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-11
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 41.67 MB Beats 87.73%

/****************************************
* 
* You are given two positive integers `low` and `high`.
* An integer `x` consisting of `2 * n` digits is symmetric if the sum of
* _ the first n digits of `x` is equal to the sum of the last n digits of `x`.
* _ Numbers with an odd number of digits are never symmetric.
* Return the number of symmetric integers in the range `[low, high]`.
*
* Example 1:
* Input: low = 1, high = 100
* Output: 9
* Explanation: There are 9 symmetric integers between 1 and 100: 11, 22, 33, 44, 55, 66, 77, 88, and 99.
*
* Example 2:
* Input: low = 1200, high = 1230
* Output: 4
* Explanation: There are 4 symmetric integers between 1200 and 1230: 1203, 1212, 1221, and 1230.
*
* Constraints:
* • 1 <= low <= high <= 10^4
* 
****************************************/

class Solution {
    // This solution precomputes and stores symmetric integer counts up to 10,000  
    // in a static array, allowing O(1) retrieval for each query. The first call  
    // to countSymmetricIntegers() triggers a one-time O(10^4) preprocessing step,  
    // after which each test case runs in O(1) time. Space complexity is O(10^4).  
    // This approach avoids recomputation, making it highly efficient for multiple queries.
    private static final short[] symmetricCount = new short[10_001];

    public int countSymmetricIntegers(int low, int high) {
        // Initialize symmetric counts on first function call
        if (symmetricCount[11] == 0) {
            precomputeSymmetricCounts();
        }
        return symmetricCount[high] - symmetricCount[low - 1];
    }

    // Precomputes the cumulative count of symmetric integers up to each index.
    // This runs only once per submission, persisting values for all test cases.
    private void precomputeSymmetricCounts() {
        // Fill symmetric counts for numbers from 11 to 99
        for (int num = 11; num <= 99; num++) {
            symmetricCount[num] = (short) (num / 11);
        }

        // Copy count from 99 for numbers 100 to 999 (no symmetric numbers in this range)
        short prevCount = symmetricCount[99];
        for (int num = 100; num <= 999; num++) {
            symmetricCount[num] = prevCount;
        }

        // Compute symmetric counts for numbers from 1000 to 9999
        prevCount = symmetricCount[999];
        int index = 1000;

        for (int high10 = 1; high10 <= 9; high10++) {
            for (int high1 = 0; high1 <= 9; high1++) {
                int highSum = high10 + high1;

                for (int low10 = 0; low10 <= 9; low10++) {
                    for (int low1 = 0; low1 <= 9; low1++) {
                        symmetricCount[index++] = (short) ((highSum == low10 + low1) ? ++prevCount : prevCount);
                    }
                }
            }
        }

        // Copy count from 9999 to 10_000
        symmetricCount[10_000] = symmetricCount[9999];
    }
}