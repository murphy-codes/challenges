// Source: https://leetcode.com/problems/next-greater-numerically-balanced-number/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-24
// At the time of submission:
//   Runtime 26 ms Beats 80.92%
//   Memory 44.96 MB Beats 12.98%

/****************************************
* 
* An integer `x` is numerically balanced if for every digit `d` in the
* _ number `x`, there are exactly `d` occurrences of that digit in `x`.
* Given an integer `n`, return the smallest numerically balanced number
* _ strictly greater than `n`.
*
* Example 1:
* Input: n = 1
* Output: 22
* Explanation:
* 22 is numerically balanced since:
* - The digit 2 occurs 2 times.
* It is also the smallest numerically balanced number strictly greater than 1.
*
* Example 2:
* Input: n = 1000
* Output: 1333
* Explanation:
* 1333 is numerically balanced since:
* - The digit 1 occurs 1 time.
* - The digit 3 occurs 3 times.
* It is also the smallest numerically balanced number strictly greater than 1000.
* Note that 1022 cannot be the answer because 0 appeared more than 0 times.
*
* Example 3:
* Input: n = 3000
* Output: 3133
* Explanation:
* 3133 is numerically balanced since:
* - The digit 1 occurs 1 time.
* - The digit 3 occurs 3 times.
* It is also the smallest numerically balanced number strictly greater than 3000.
*
* Constraints:
* • `0 <= n <= 10^6`
* 
****************************************/

import java.util.ArrayList;

class Solution {
    // This solution keeps a static memo list of known "beautiful numbers" for reuse.
    // If n is smaller than the largest memoized value, it returns the next larger one.
    // Otherwise, it searches incrementally for the next "balanced" number, where each
    // digit d appears exactly d times, adding new results to the memo as discovered.
    // Time complexity is O(k * log n) per search; space complexity is O(m) for memo.

    private static final ArrayList<Integer> memo = new ArrayList<>();

    static {
        memo.add(1);
        memo.add(22);
        memo.add(122);
        memo.add(212);
        memo.add(221);
        memo.add(333);
        // …
        // memo.add(666666);
    }

    public int nextBeautifulNumber(int n) {
        if (n < memo.get(memo.size()-1)) 
            for (int bN : memo) 
                if (bN > n)
                    return bN;
        for (int x = n + 1; ; x++) {
            if (isBalanced(x)) {
                memo.add(x);
                return x;
            }
        }
    }

    private boolean isBalanced(int x) {
        int[] count = new int[10];
        while (x > 0) {
            count[x % 10]++;
            x /= 10;
        }
        for (int d = 0; d < 10; d++) {
            if (count[d] != 0 && count[d] != d) return false;
        }
        return true;
    }
}