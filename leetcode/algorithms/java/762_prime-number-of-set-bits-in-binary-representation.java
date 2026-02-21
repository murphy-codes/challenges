// Source: https://leetcode.com/problems/prime-number-of-set-bits-in-binary-representation/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-21
// At the time of submission:
//   Runtime 2 ms Beats 98.83%
//   Memory 42.55 MB Beats 27.37%

/****************************************
* 
* Given two integers `left` and `right`, return the count of numbers in the
* _inclusive range `[left, right]` having a prime number of set bits in their
* _binary representation.
* Recall that the number of set bits an integer has is the number of `1`'s
* _present when written in binary.
* • For example, `21` written in binary is `10101`, which has `3` set bits.
*
* Example 1:
* Input: left = 6, right = 10
* Output: 4
* Explanation:
* 6  -> 110 (2 set bits, 2 is prime)
* 7  -> 111 (3 set bits, 3 is prime)
* 8  -> 1000 (1 set bit, 1 is not prime)
* 9  -> 1001 (2 set bits, 2 is prime)
* 10 -> 1010 (2 set bits, 2 is prime)
* 4 numbers have a prime number of set bits.
*
* Example 2:
* Input: left = 10, right = 15
* Output: 5
* Explanation:
* 10 -> 1010 (2 set bits, 2 is prime)
* 11 -> 1011 (3 set bits, 3 is prime)
* 12 -> 1100 (2 set bits, 2 is prime)
* 13 -> 1101 (3 set bits, 3 is prime)
* 14 -> 1110 (3 set bits, 3 is prime)
* 15 -> 1111 (4 set bits, 4 is not prime)
* 5 numbers have a prime number of set bits.
*
* Constraints:
* • `1 <= left <= right <= 10^6`
* • `0 <= right - left <= 10^4`
* 
****************************************/

class Solution {
    // Iterate through the range & count set bits using Integer.bitCount()
    // Since n <= 10^6, the maximum possible set bits is 20.
    // We precompute which counts are prime for O(1) lookup.
    // Time complexity is O(N), space complexity is O(1).
    public int countPrimeSetBits(int left, int right) {
        // Prime counts possible for numbers <= 10^6 (max 20 bits)
        boolean[] isPrime = new boolean[21];
        isPrime[2] = isPrime[3] = isPrime[5] = isPrime[7] = true;
        isPrime[11] = isPrime[13] = isPrime[17] = isPrime[19] = true;

        int count = 0;

        for (int n = left; n <= right; n++) {
            int setBits = Integer.bitCount(n);
            if (isPrime[setBits]) {
                count++;
            }
        }

        return count;
    }
}
