// Source: https://leetcode.com/problems/unique-length-3-palindromic-subsequences/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-03

/****************************************
* 
* You are given a 0-indexed integer array nums of length n.
* nums contains a valid split at index i if the following are true:
* • The sum of the first i + 1 elements is greater than or equal to the sum of the last n - i - 1 elements.
* • There is at least one element to the right of i. That is, 0 <= i < n - 1.
* Return the number of valid splits in nums.
* 
* Example 1:
* Input: nums = [10,4,-8,7]
* Output: 2
* Explanation: 
* There are three ways of splitting nums into two non-empty parts:
* - Split nums at index 0. Then, the first part is [10], and its sum is 10. The second part is [4,-8,7], and its sum is 3. Since 10 >= 3, i = 0 is a valid split.
* - Split nums at index 1. Then, the first part is [10,4], and its sum is 14. The second part is [-8,7], and its sum is -1. Since 14 >= -1, i = 1 is a valid split.
* - Split nums at index 2. Then, the first part is [10,4,-8], and its sum is 6. The second part is [7], and its sum is 7. Since 6 < 7, i = 2 is not a valid split.
* Thus, the number of valid splits in nums is 2.
* 
* Example 2:
* Input: nums = [2,3,1,0]
* Output: 2
* Explanation: 
* There are two valid splits in nums:
* - Split nums at index 1. Then, the first part is [2,3], and its sum is 5. The second part is [1,0], and its sum is 1. Since 5 >= 1, i = 1 is a valid split. 
* - Split nums at index 2. Then, the first part is [2,3,1], and its sum is 6. The second part is [0], and its sum is 0. Since 6 >= 0, i = 2 is a valid split.
* 
* Constraints:
* • 2 <= nums.length <= 10^5
* • -10^5 <= nums[i] <= 10^5
* 
****************************************/

import java.util.HashSet;
import java.util.Set;

class Solution {
    // Solution: Two-pass approach with O(n) complexity. (O(26n), simplified)
    // 1. Treat each character as the middle of a potential palindrome.
    // 2. Use arrays to track seen characters on the left and count characters on the right.
    // 3. For each middle, check all possible outer characters ('a' to 'z').
    // 4. Add valid palindromes to a HashSet to avoid duplicates.
    // 5. Return the size of the set as the result.
    public int countPalindromicSubsequence(String s) {
        int n = s.length();
        Set<String> uniquePalindromes = new HashSet<>();

        // Array to store right set of characters for each position
        int[] rightCount = new int[26];
        for (char c : s.toCharArray()) {
            rightCount[c - 'a']++;
        }

        boolean[] leftSeen = new boolean[26]; // Set of characters seen on the left

        // Traverse the string
        for (int i = 0; i < n; i++) {
            char middle = s.charAt(i);
            rightCount[middle - 'a']--; // Remove the current character from the right

            // Check all possible outer characters
            for (char outer = 'a'; outer <= 'z'; outer++) {
                if (leftSeen[outer - 'a'] && rightCount[outer - 'a'] > 0) {
                    // Form a valid palindrome and add to the set
                    uniquePalindromes.add("" + outer + middle + outer);
                }
            }

            leftSeen[middle - 'a'] = true; // Add current character to the left set
        }

        return uniquePalindromes.size();
    }
}
