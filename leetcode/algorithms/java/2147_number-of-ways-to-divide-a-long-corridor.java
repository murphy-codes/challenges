// Source: https://leetcode.com/problems/number-of-ways-to-divide-a-long-corridor/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-14
// At the time of submission:
//   Runtime 13 ms Beats 100.00%
//   Memory 47.49 MB Beats 68.00%

/****************************************
* 
* Along a long library corridor, there is a line of seats and decorative plants.
* _ You are given a 0-indexed string `corridor` of length `n` consisting of
* _ letters `'S'` and `'P'` where each `'S'` represents a seat and each `'P'`
* _ represents a plant.
* One room divider has already been installed to the left of index 0, and
* _ another to the right of index `n - 1`. Additional room dividers can be
* _ installed. For each position between indices `i - 1` and `i`
* _ (`1 <= i <= n - 1`), at most one divider can be installed.
* Divide the corridor into non-overlapping sections, where each section has
* _ exactly two seats with any number of plants. There may be multiple ways to
* _ perform the division. Two ways are different if there is a position with a
* _ room divider installed in the first way but not in the second way.
* Return the number of ways to divide the corridor. Since the answer may be
* _ very large, return it modulo `10^9 + 7`. If there is no way, return 0.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/12/04/1.png]
* Input: corridor = "SSPPSPS"
* Output: 3
* Explanation: There are 3 different ways to divide the corridor.
* The black bars in the above image indicate the two room dividers already installed.
* Note that in each of the ways, each section has exactly two seats.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/12/04/2.png]
* Input: corridor = "PPSPSP"
* Output: 1
* Explanation: There is only 1 way to divide the corridor, by not installing any additional dividers.
* Installing any would create some section that does not have exactly two seats.
*
* Example 3:
* [Image: https://assets.leetcode.com/uploads/2021/12/12/3.png]
* Input: corridor = "S"
* Output: 0
* Explanation: There is no way to divide the corridor because there will always be a section that does not have exactly two seats.
*
* Constraints:
* • `n == corridor.length`
* • `1 <= n <= 10^5`
* • `corridor[i]` is either `'S'` or `'P'`.
* 
****************************************/

class Solution {
    // Traverse the corridor from right to left, pairing seats using a parity flag.
    // Each completed seat pair defines a segment boundary where exactly one divider
    // must be placed. The number of valid divider positions equals the distance
    // between paired seats, accounting for plants in between. Multiply all choices
    // to get the result. Time O(n), space O(1).
    public int numberOfWays(String corridor) {
        final int n = corridor.length();
        final byte[] chars = new byte[n];
        corridor.getBytes(0, n, chars, 0);

        long ways = 1;
        int seatParity = 0;          // toggles between first/second seat
        int lastSeatIndex = -1;      // index of previous seat seen

        for (int i = n - 1; i >= 0; i--) {
            if (chars[i] == 'P') continue;

            if (seatParity != 0) {
                // Found the first seat of a pair
                lastSeatIndex = i;
            } else if (lastSeatIndex > 0) {
                // Completed a pair; multiply divider placement options
                ways = (ways * (lastSeatIndex - i)) % 1_000_000_007L;
            }

            seatParity ^= 1; // toggle seat state
        }

        // Invalid if odd number of seats or no valid pairs found
        return (lastSeatIndex < 0 || seatParity != 0) ? 0 : (int) ways;
    }
}
