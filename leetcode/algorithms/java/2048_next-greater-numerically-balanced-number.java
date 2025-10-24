// Source: https://leetcode.com/problems/next-greater-numerically-balanced-number/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-24
// At the time of submission:
//   Runtime 15 ms Beats 82.44%
//   Memory 44.76 MB Beats 23.66%

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
    // This solution precomputes and memoizes known "beautiful numbers" in a static list
    // for fast lookup of small inputs. If n is larger than the prefilled range, it 
    // iteratively checks each subsequent number until finding the next "balanced" one, 
    // where each digit d appears exactly d times. Time complexity is O(k * log n) for 
    // larger inputs (k digits checked), and space complexity is O(m) for memoized values.
    private static final ArrayList<Integer> memo = new ArrayList<>();
    static {
        memo.add(1);
        memo.add(22);
        memo.add(122);
        memo.add(212);
        memo.add(221);
        memo.add(333);
        memo.add(1333);
        memo.add(3133);
        memo.add(3313);
        memo.add(3331);
        memo.add(4444);
        memo.add(14444);
        memo.add(22333);
        memo.add(23233);
        memo.add(23323);
        memo.add(23332);
        memo.add(32233);
        memo.add(32323);
        memo.add(32332);
        memo.add(33223);
        memo.add(33232);
        memo.add(33322);
        memo.add(41444);
        memo.add(44144);
        memo.add(44414);
        memo.add(44441);
        memo.add(55555);
        memo.add(122333);
        memo.add(123233);
        memo.add(123323);
        memo.add(123332);
        memo.add(132233);
        memo.add(132323);
        memo.add(132332);
        memo.add(133223);
        memo.add(133232);
        memo.add(133322);
        memo.add(155555);
        memo.add(212333);
        memo.add(213233);
        memo.add(213323);
        memo.add(213332);
        memo.add(221333);
        memo.add(223133);
        memo.add(223313);
        memo.add(223331);
        memo.add(224444);
        memo.add(231233);
        memo.add(231323);
        memo.add(231332);
        memo.add(232133);
        memo.add(232313);
        memo.add(232331);
        memo.add(233123);
        memo.add(233132);
        memo.add(233213);
        memo.add(233231);
        memo.add(233312);
        memo.add(233321);
        memo.add(242444);
        memo.add(244244);
        memo.add(244424);
        memo.add(244442);
        memo.add(312233);
        memo.add(312323);
        memo.add(312332);
        memo.add(313223);
        memo.add(313232);
        memo.add(313322);
        memo.add(321233);
        memo.add(321323);
        memo.add(321332);
        memo.add(322133);
        memo.add(322313);
        memo.add(322331);
        memo.add(323123);
        memo.add(323132);
        memo.add(323213);
        memo.add(323231);
        memo.add(323312);
        memo.add(323321);
        memo.add(331223);
        memo.add(331232);
        memo.add(331322);
        memo.add(332123);
        memo.add(332132);
        memo.add(332213);
        memo.add(332231);
        memo.add(332312);
        memo.add(332321);
        memo.add(333122);
        memo.add(333212);
        memo.add(333221);
        memo.add(422444);
        memo.add(424244);
        memo.add(424424);
        memo.add(424442);
        memo.add(442244);
        memo.add(442424);
        memo.add(442442);
        memo.add(444224);
        memo.add(444242);
        memo.add(444422);
        memo.add(515555);
        memo.add(551555);
        memo.add(555155);
        memo.add(555515);
        memo.add(555551);
        memo.add(666666);
    }
    public int nextBeautifulNumber(int n) {
        if (n < memo.get(memo.size()-1)) 
            for (int bN : memo) 
                if (bN > n)
                    return bN;
        // START HERE
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