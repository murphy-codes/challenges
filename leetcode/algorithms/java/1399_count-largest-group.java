// Source: https://leetcode.com/count-largest-group/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-22
// At the time of submission:
//   Runtime 1 ms Beats 100.00%
//   Memory 40.88 MB Beats 64.52%

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
    // Time Complexity: O(log(n) * D^2), where D=36 (the max digit sum of 9999).
    // Space Complexity: O(D^2) for memoizing digit sum combinations.
    // Uses DP to avoid brute-force sum calculation for each number up to n.
    // Efficient for n up to 10^4, leveraging combinatorics and prefix sums.

    // ref[d][s] will eventually hold the number of ways to get a digit sum 's' with 'd' digits
    int[][] digitSumWays = { {1}, new int[10], new int[19], new int[28] };

    public int countLargestGroup(int n) {
        // Holds the number of digits per position (units, tens, hundreds, thousands)
        int[] digitCounts = {1, 0, 0, 0};

        // Array to count how many numbers have a certain digit sum
        int[] sumFrequencies = new int[37];

        // Number of digits and prefix sum of all digits
        int numDigits = 0;
        int totalDigitSum = 0;

        // Preprocess digits of n (clamp to 9999 max) into digitCounts array
        for (int number = Math.min(n, 9999); number > 0; number /= 10) {
            int digit = number % 10;
            totalDigitSum += digitCounts[numDigits] += digit;
            numDigits++;
        }

        // Build combinations of digit sums dynamically
        for (int digitPos = 0; digitPos < numDigits; digitPos++) {
            int digit = digitCounts[digitPos];
            int prevSize = digitSumWays[digitPos].length;

            // Determine whether to compute digitSumWays[d+1] for the next digit position
            boolean shouldComputeNext = digitPos <= 2 && digitSumWays[digitPos + 1][digitSumWays[digitPos + 1].length - 1] == 0;
            int nextSize = shouldComputeNext ? digitSumWays[digitPos + 1].length : 0;

            // Calculate limit for sum index range in this digit position
            int sumLimit = Math.max(digitPos * 9 + digit, nextSize);

            // We subtract current digit because we’ll be shifting from psum to psum + i
            totalDigitSum -= digit;

            int rollingSum = 0;
            for (int sum = 0; sum < sumLimit; sum++) {
                // Compute rolling sum of possible combinations to reach 'sum' using current digit
                rollingSum += (sum < prevSize ? digitSumWays[digitPos][sum] : 0)
                            - (sum >= digit && sum - digit < prevSize ? digitSumWays[digitPos][sum - digit] : 0);

                // Update the count of numbers that can form this digit sum
                sumFrequencies[totalDigitSum + sum] += rollingSum;

                // Update next level of digitSumWays if needed
                if (shouldComputeNext) {
                    digitSumWays[digitPos + 1][sum] =
                        (sum > 0 && sum - 1 < nextSize ? digitSumWays[digitPos + 1][sum - 1] : 0)
                      + (sum < prevSize ? digitSumWays[digitPos][sum] : 0)
                      - (sum >= 10 && sum - 10 < prevSize ? digitSumWays[digitPos][sum - 10] : 0);
                }
            }
        }

        // Ignore sumFrequencies[0] since it's a dummy
        sumFrequencies[0] = 0;

        // Find the max group size and count how many groups have that size
        int maxGroupSize = 0, groupCount = 0;
        for (int frequency : sumFrequencies) {
            if (frequency > maxGroupSize) {
                maxGroupSize = frequency;
                groupCount = 1;
            } else if (frequency == maxGroupSize) {
                groupCount++;
            }
        }

        return groupCount;
    }
}
