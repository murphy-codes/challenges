// Source: https://leetcode.com/problems/closest-prime-numbers-in-range/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-07

/****************************************
* 
* Given two positive integers `left` and `right`, find the two integers `num1` and `num2` such that:
* • left <= num1 < num2 <= right.
* • Both `num1` and `num2` are prime numbers.
* • `num2 - num1` is the minimum amongst all other pairs satisfying the above conditions.
* Return the positive integer array `ans = [num1, num2]`. If there are multiple pairs satisfying these conditions, return the one with the smallest `num1` value. If no such numbers exist, return `[-1, -1]`.
*
* Example 1:
* Input: left = 10, right = 19
* Output: [11,13]
* Explanation: The prime numbers between 10 and 19 are 11, 13, 17, and 19.
* The closest gap between any pair is 2, which can be achieved by [11,13] or [17,19].
* Since 11 is smaller than 17, we return the first pair.
*
* Example 2:
* Input: left = 4, right = 6
* Output: [-1,-1]
* Explanation: There exists only one prime number in the given range, so the conditions cannot be satisfied.
*
* Constraints:
* • 1 <= left <= right <= 10^6
* 
****************************************/

import java.util.ArrayList;
import java.util.Arrays;

class Solution {
    // Uses the Sieve of Eratosthenes to precompute primes up to `right` in O(n log log n).
    // Extracts primes in range [left, right] and finds the closest prime pair in O(n).
    // The first pair with the smallest `num1` is returned in case of ties.
    // Time complexity: O(n log log n), Space complexity: O(n) for prime array.
    public int[] closestPrimes(int left, int right) {
        // Step 1: Compute prime numbers up to `right` using Sieve of Eratosthenes
        boolean[] isPrime = sieve(right);
        
        // Step 2: Collect primes in range [left, right]
        List<Integer> primes = new ArrayList<>();
        for (int i = Math.max(2, left); i <= right; i++) {
            if (isPrime[i]) primes.add(i);
        }
        
        // Step 3: Find the closest pair
        if (primes.size() < 2) return new int[]{-1, -1};
        
        int minDiff = Integer.MAX_VALUE;
        int num1 = -1, num2 = -1;
        
        for (int i = 1; i < primes.size(); i++) {
            int diff = primes.get(i) - primes.get(i - 1);
            if (diff < minDiff) {
                minDiff = diff;
                num1 = primes.get(i - 1);
                num2 = primes.get(i);
            }
        }
        
        return new int[]{num1, num2};
    }
    
    // Helper function: Sieve of Eratosthenes to find primes up to `n`
    private boolean[] sieve(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false; // 0 and 1 are not prime
        
        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        
        return isPrime;
    }
}
