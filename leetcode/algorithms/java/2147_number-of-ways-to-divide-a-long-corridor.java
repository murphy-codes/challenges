// Source: https://leetcode.com/problems/number-of-ways-to-divide-a-long-corridor/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-14
// At the time of submission:
//   Runtime 22 ms Beats 63.20%
//   Memory 47.76 MB Beats 54.40%

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
    // Pair seats from left to right so each section contains exactly two seats.
    // Between adjacent seat pairs, exactly one divider must be placed; if there
    // are k plants between them, there are k+1 valid divider positions. The total
    // number of ways is the product of all such choices. Time complexity is O(n),
    // and space complexity is O(1).

    private static final int MOD = 1_000_000_007;

    public int numberOfWays(String corridor) {
        long ways = 1;
        int seatCount = 0;
        int plantsBetween = 0;

        for (char c : corridor.toCharArray()) {
            if (c == 'S') {
                seatCount++;
                // Every time we finish a pair and encounter a new seat,
                // multiply the number of divider choices.
                if (seatCount > 2 && seatCount % 2 == 1) {
                    ways = (ways * (plantsBetween + 1)) % MOD;
                    plantsBetween = 0;
                }
            } else if (seatCount >= 2 && seatCount % 2 == 0) {
                // Count plants only after completing a seat pair
                plantsBetween++;
            }
        }

        // Total seats must be even and non-zero
        return seatCount >= 2 && seatCount % 2 == 0 ? (int) ways : 0;
    }
}
