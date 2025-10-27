// Source: https://leetcode.com/problems/number-of-laser-beams-in-a-bank/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-26
// At the time of submission:
//   Runtime 6 ms Beats 100.00%
//   Memory 45.23 MB Beats 45.58%

/****************************************
* 
* Anti-theft security devices are activated inside a bank. You are given a
* _ 0-indexed binary string array `bank` representing the floor plan of the bank,
* _ which is an `m x n` 2D matrix. `bank[i]` represents the `i^th` row,
* _ consisting of `'0'`s and `'1'`s. `'0'` means the cell is empty, while `'1'`
* _ means the cell has a security device.
* There is one laser beam between any two security devices if both conditions are met:
* • The two devices are located on two different rows: `r1` and `r2`, where `r1 < r2`.
* • For each row `i` where `r_1 < i < r_2`, there are no security devices in the `i^th` row.
* Laser beams are independent, i.e., one beam does not interfere nor join with another.
* Return the total number of laser beams in the bank.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/12/24/laser1.jpg]
* Input: bank = ["011001","000000","010100","001000"]
* Output: 8
* Explanation: Between each of the following device pairs, there is one beam. In total, there are 8 beams:
* * bank[0][1] -- bank[2][1]
* * bank[0][1] -- bank[2][3]
* * bank[0][2] -- bank[2][1]
* * bank[0][2] -- bank[2][3]
* * bank[0][5] -- bank[2][1]
* * bank[0][5] -- bank[2][3]
* * bank[2][1] -- bank[3][2]
* * bank[2][3] -- bank[3][2]
* Note that there is no beam between any device on the 0th row with any on the 3rd row.
* This is because the 2nd row contains security devices, which breaks the second condition.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/12/24/laser2.jpg]
* Input: bank = ["000","111","000"]
* Output: 0
* Explanation: There does not exist two devices located on two different rows.
*
* Constraints:
* • `m == bank.length`
* • `n == bank[i].length`
* • `1 <= m, n <= 500`
* • `bank[i][j]` is either `'0'` or `'1'`.
* 
****************************************/

class Solution {
    // Counts total laser beams by multiplying device counts of consecutive
    // non-empty rows. Skips rows with no devices. Uses a helper to count '1's
    // per row efficiently. Runs in O(m·n) time since each char is checked once,
    // and uses O(1) space with only a few integer variables for tracking state.
    public int numberOfBeams(String[] bank) {
        int totalBeams = 0;
        int prevDevices = countDevices(bank[0]);

        for (int i = 1; i < bank.length; i++) {
            int currDevices = countDevices(bank[i]);
            if (currDevices == 0) continue;
            // Add beams between previous and current non-empty rows
            totalBeams += prevDevices * currDevices;
            prevDevices = currDevices; // update for next iteration
        }

        return totalBeams;
    }

    // Helper to count number of '1' devices in a row
    int countDevices(String row) {
        int count = 0;
        for (int i = 0; i < row.length(); i++)
            count += (row.charAt(i) - '0');
        return count;
    }
}
