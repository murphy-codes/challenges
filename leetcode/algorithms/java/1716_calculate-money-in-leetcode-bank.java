// Source: https://leetcode.com/problems/calculate-money-in-leetcode-bank/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-25
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 40.84 MB Beats 15.30%

/****************************************
* 
* Hercy wants to save money for his first car.
* _ He puts money in the Leetcode bank every day.
* He starts by putting in `$1` on Monday, the first day. Every day from Tuesday
* _ to Sunday, he will put in `$1` more than the day before. On every subsequent
* _ Monday, he will put in `$1` more than the previous Monday.
* Given `n`, return the total amount of money he will have in the Leetcode
* _ bank at the end of the `n^th` day.
*
* Example 1:
* Input: n = 4
* Output: 10
* Explanation: After the 4th day, the total is 1 + 2 + 3 + 4 = 10.
*
* Example 2:
* Input: n = 10
* Output: 37
* Explanation: After the 10th day, the total is (1 + 2 + 3 + 4 + 5 + 6 + 7) + (2 + 3 + 4) = 37. Notice that on the 2nd Monday, Hercy only puts in $2.
*
* Example 3:
* Input: n = 20
* Output: 96
* Explanation: After the 20th day, the total is (1 + 2 + 3 + 4 + 5 + 6 + 7) + (2 + 3 + 4 + 5 + 6 + 7 + 8) + (3 + 4 + 5 + 6 + 7 + 8) = 96.
*
* Constraints:
* • `1 <= n <= 1000` 
* 
****************************************/

class Solution {
    // Each full week contributes 28 + 7*(week index) total dollars.
    // We compute total for full weeks using a summation formula,
    // then iterate through remaining days (<=7) to add final partial week.
    // Time Complexity: O(1) since loop runs ≤ 7 times; Space: O(1).
    public int totalMoney(int n) {
        int d = n % 7, w = n / 7;
        int total = (w * 28) + 7*((w*(w-1))/2);
        for (int i = 1; i <= d; i++) total += w+i;
        return total;
    }
}