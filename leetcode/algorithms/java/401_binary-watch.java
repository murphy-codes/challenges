// Source: https://leetcode.com/problems/binary-watch/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-16
// At the time of submission:
//   Runtime 14 ms Beats 8.18%
//   Memory 49.41 MB Beats 32.74%

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
    // Enumerate all possible hour (0–11) and minute (0–59) combinations.
    // For each time, compare the total number of set bits using bitCount.
    // Valid times are formatted and added to the result list.
    // Time complexity is O(1) due to a fixed search space, and space is O(1).
    public List<String> readBinaryWatch(int turnedOn) {
        List<String> result = new ArrayList<>();

        for (int hour = 0; hour < 12; hour++) {
            for (int minute = 0; minute < 60; minute++) {
                if (Integer.bitCount(hour) + Integer.bitCount(minute) == turnedOn) {
                    result.add(hour + ":" + String.format("%02d", minute));
                }
            }
        }

        return result;
    }
}
