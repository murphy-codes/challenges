// Source: https://leetcode.com/problems/the-k-th-lexicographical-string-of-all-happy-strings-of-length-n/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-18

/****************************************
* 
* A happy string is a string that:
* • consists only of letters of the set `['a', 'b', 'c']`.
* • `s[i] != s[i + 1]` for all values of `i` from `1` to `s.length - 1` (string is 1-indexed).
* 
* For example, strings "abc", "ac", "b" and "abcbabcbcb" are all happy strings and strings "aa", "baa" and "ababbc" are not happy strings.
* Given two integers `n` and `k`, consider a list of all happy strings of length `n` sorted in lexicographical order.
* Return the kth string of this list or return an empty string if there are less than `k` happy strings of length `n`.
* 
* Example 1:
* Input: n = 1, k = 3
* Output: "c"
* Explanation: The list ["a", "b", "c"] contains all happy strings of length 1. The third string is "c".
* 
* Example 2:
* Input: n = 1, k = 4
* Output: ""
* Explanation: There are only 3 happy strings of length 1.
* 
* Example 3:
* Input: n = 3, k = 9
* Output: "cab"
* Explanation: There are 12 different happy string of length 3 ["aba", "abc", "aca", "acb", "bab", "bac", "bca", "bcb", "cab", "cac", "cba", "cbc"]. You will find the 9th string = "cab"
* 
* Constraints:
* • 1 <= n <= 10
* • 1 <= k <= 100
* 
****************************************/

class Solution {
    // Generates and stores all happy strings in lexicographical order.
    // There are at most 2 * 3^(n-1) valid strings (since each position has 2 choices).
    // Time Complexity: O(3^n), Space Complexity: O(3^n) due to storing results.
    public String getHappyString(int n, int k) {
        List<String> happyStrings = new ArrayList<>();
        generateHappyStrings(n, "", happyStrings);
        return k <= happyStrings.size() ? happyStrings.get(k - 1) : "";
    }
    
    private void generateHappyStrings(int n, String current, List<String> result) {
        if (current.length() == n) {
            result.add(current);
            return;
        }
        
        for (char c : new char[]{'a', 'b', 'c'}) {
            if (current.isEmpty() || current.charAt(current.length() - 1) != c) {
                generateHappyStrings(n, current + c, result);
            }
        }
    }
}
