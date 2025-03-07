// Source: https://leetcode.com/problems/closest-prime-numbers-in-range/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-03-07

/****************************************
* 
* 
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
