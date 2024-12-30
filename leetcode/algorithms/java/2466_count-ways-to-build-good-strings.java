// Source: https://leetcode.com/problems/count-ways-to-build-good-strings/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2024-12-29

/****************************************
* 
* Given the integers zero, one, low, and high, we can construct a string by starting with an empty string, and then at each step perform either of the following:
* 
* Append the character '0' zero times.
* Append the character '1' one times.
* 
* This can be performed any number of times.
* 
* A good string is a string constructed by the above process having a length between low and high (inclusive).
* 
* Return the number of different good strings that can be constructed satisfying these properties. Since the answer can be large, return it modulo 109 + 7.
* 
* Example 1:
* Input: low = 3, high = 3, zero = 1, one = 1
* Output: 8
* Explanation: 
* One possible valid good string is "011". 
* It can be constructed as follows: "" -> "0" -> "01" -> "011". 
* All binary strings from "000" to "111" are good strings in this example.
* 
* Example 2:
* Input: low = 2, high = 3, zero = 1, one = 2
* Output: 5
* Explanation: The good strings are "00", "11", "000", "110", and "011".
* 
* Constraints:
* 
* 1 <= low <= high <= 10^5
* 1 <= zero, one <= low
* 
****************************************/

class Solution {
    // We define a constant MOD to handle the large numbers by taking results modulo 10^9 + 7
    // This helps us prevent overflow and ensures our answer fits within standard integer limits.
    // dp[i] represents the number of ways to build a good string of length i.
    // We initialize dp[0] = 1 because there is one way to build an empty string (by doing nothing).
    // We then iterate from 1 to 'high', and for each length i, we check two possibilities:
    // - Append a '0' to a valid string of length i - zero. This means we add dp[i - zero] to dp[i].
    // - Append a '1' to a valid string of length i - one. This means we add dp[i - one] to dp[i].
    // After iterating through all lengths, the sum of dp values from dp[low] to dp[high] gives
    // the total number of valid strings that can be constructed within the length range [low, high].
    // We use modular arithmetic to avoid overflow issues, applying MOD to the sum at every step.
    public int countGoodStrings(int low, int high, int zero, int one) {
        int MOD = 1_000_000_007;
        int[] dp = new int[high + 1];
        
        // Base case: one way to construct an empty string
        dp[0] = 1;
        
        // Fill dp array
        for (int i = 1; i <= high; i++) {
            if (i >= zero) {
                dp[i] = (dp[i] + dp[i - zero]) % MOD;
            }
            if (i >= one) {
                dp[i] = (dp[i] + dp[i - one]) % MOD;
            }
        }
        
        // Sum up all valid lengths between low and high
        int result = 0;
        for (int i = low; i <= high; i++) {
            result = (result + dp[i]) % MOD;
        }
        
        return result;
    }
}
