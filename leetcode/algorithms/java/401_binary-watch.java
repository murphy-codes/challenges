// Source: https://leetcode.com/problems/binary-watch/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-16
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 43.51 MB Beats 91.66%

/****************************************
* 
* Reverse bits of a given 32 bits signed integer.
*
* Example 1:
* Input: n = 43261596
* Output: 964176192
* Explanation:
* Integer        Binary
* 43261596        00000010100101000001111010011100
* 964176192        00111001011110000010100101000000
*
* Example 2:
* Input: n = 2147483644
* Output: 1073741822
* Explanation:
* Integer        Binary
* 2147483644        01111111111111111111111111111100
* 1073741822        00111111111111111111111111111110
*
* Constraints:
* • `0 <= n <= 2^31 - 2`
* • `n` is even.
* 
****************************************/

import java.util.List;
import java.util.ArrayList;

class Solution {
    // This solution uses backtracking to build valid hours and minutes from LEDs.
    // It tracks numeric values, remaining bits, and LEDs turned on to prune early.
    // Invalid branches (hour > 11 or minute > 59) are skipped immediately.
    // Time complexity is O(1) due to a fixed LED count, with O(1) space usage.

    public List<String> readBinaryWatch(int turnedOn) {
        List<String> results = new ArrayList<>();

        if (turnedOn > 8) {
            return results;
        }

        buildHours(results, new StringBuilder(), 0, 4, turnedOn, 0);
        return results;
    }

    void buildHours(List<String> results, StringBuilder current,
                    int hourValue, int bitsLeft,
                    int targetOn, int ledsOn) {

        if (bitsLeft == 0) {
            if (hourValue > 11) return;

            if (hourValue >= 10) {
                current.append(hourValue);
            } else {
                current.append((char) (hourValue + '0'));
            }

            current.append(':');
            buildMinutes(results, current, 0, 6, targetOn, ledsOn, current.length());
            current.setLength(0);
            return;
        }

        int withBit = hourValue + (1 << (bitsLeft - 1));

        if (ledsOn < targetOn) {
            buildHours(results, current, withBit,
                       bitsLeft - 1, targetOn, ledsOn + 1);
        }

        buildHours(results, current, hourValue,
                   bitsLeft - 1, targetOn, ledsOn);
    }

    void buildMinutes(List<String> results, StringBuilder current,
                      int minuteValue, int bitsLeft,
                      int targetOn, int ledsOn, int resetLen) {

        if (bitsLeft == 0) {
            if (ledsOn != targetOn || minuteValue > 59) return;

            if (minuteValue >= 10) {
                current.append((char) (minuteValue / 10 + '0'));
                current.append((char) (minuteValue % 10 + '0'));
            } else {
                current.append('0');
                current.append((char) (minuteValue + '0'));
            }

            results.add(current.toString());
            current.setLength(resetLen);
            return;
        }

        int withBit = minuteValue + (1 << (bitsLeft - 1));

        if (ledsOn < targetOn) {
            buildMinutes(results, current, withBit,
                         bitsLeft - 1, targetOn, ledsOn + 1, resetLen);
        }

        buildMinutes(results, current, minuteValue,
                     bitsLeft - 1, targetOn, ledsOn, resetLen);
    }
}
