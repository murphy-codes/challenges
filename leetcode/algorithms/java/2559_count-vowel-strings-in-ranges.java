// Source: https://leetcode.com/problems/count-vowel-strings-in-ranges/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-01

/****************************************
* 
* You are given a 0-indexed array of strings words and a 2D array of integers queries.
* 
* Each query queries[i] = [l_i, r_i] asks us to find the number of strings present in the range l_i to r_i (both inclusive) of words that start and end with a vowel.
* Return an array ans of size queries.length, where ans[i] is the answer to the i^th query.
* 
* Note that the vowel letters are 'a', 'e', 'i', 'o', and 'u'.
* 
* Example 1:
* Input: words = ["aba","bcb","ece","aa","e"], queries = [[0,2],[1,4],[1,1]]
* Output: [2,3,0]
* Explanation: The strings starting and ending with a vowel are "aba", "ece", "aa" and "e".
* The answer to the query [0,2] is 2 (strings "aba" and "ece").
* to query [1,4] is 3 (strings "ece", "aa", "e").
* to query [1,1] is 0.
* We return [2,3,0].
* 
* Example 2:
* Input: words = ["a","e","i"], queries = [[0,2],[0,1],[2,2]]
* Output: [3,2,1]
* Explanation: Every string satisfies the conditions, so we return [3,2,1].
* 
* Constraints:
* • 1 <= words.length <= 10^5
* • 1 <= words[i].length <= 40
* • words[i] consists only of lowercase English letters.
* • sum(words[i].length) <= 3 * 10^5
* • 1 <= queries.length <= 10^5
* • 0 <= l_i <= r_i < words.length
* 
****************************************/

class Solution {
    // This solution uses a prefix sum array to efficiently count valid strings.
    // First, we precompute a prefix array where each entry represents the count 
    // of strings starting and ending with vowels up to that index.
    // Then, for each query, we use the prefix array to quickly calculate the 
    // count of valid strings in the specified range in O(1) time.
    // This approach is O(n + q), where n is the length of words and q is the number of queries.
    public int[] vowelStrings(String[] words, int[][] queries) {
        int n = words.length;
        int[] prefix = new int[n + 1];

        // Compute prefix sums
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + (isVowelString(words[i]) ? 1 : 0);
        }

        int[] result = new int[queries.length];

        // Answer queries using prefix sums
        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0], r = queries[i][1];
            result[i] = prefix[r + 1] - prefix[l];
        }

        return result;
    }

    // Helper function to check if a string starts and ends with a vowel
    private boolean isVowelString(String s) {
        char first = s.charAt(0), last = s.charAt(s.length() - 1);
        return "aeiou".indexOf(first) != -1 && "aeiou".indexOf(last) != -1;
    }
}
