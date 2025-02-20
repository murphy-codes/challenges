// Source: https://leetcode.com/problems/find-unique-binary-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-19

/****************************************
* 
* Given an array of strings `nums` containing `n` unique binary strings each of length `n`, return a binary string of length `n` that does not appear in `nums`. If there are multiple answers, you may return any of them.
* 
* Example 1:
* Input: nums = ["01","10"]
* Output: "11"
* Explanation: "11" does not appear in nums. "00" would also be correct.
* 
* Example 2:
* Input: nums = ["00","01"]
* Output: "11"
* Explanation: "11" does not appear in nums. "10" would also be correct.
* 
* Example 3:
* Input: nums = ["111","011","001"]
* Output: "101"
* Explanation: "101" does not appear in nums. "000", "010", "100", and "110" would also be correct.
* 
* Constraints:
* • n == nums.length
* • 1 <= n <= 16
* • nums[i].length == n
* • `nums[i]` is either `'0'` or `'1'`.
* • All the strings of `nums` are unique.
* 
****************************************/

import java.util.HashSet;
import java.util.Set;

class Solution {
    // Uses HashSet for O(1) lookups and iterates over at most n+1 numbers.
    // Time Complexity: O(n), Space Complexity: O(n) (for HashSet storage).
    public String findDifferentBinaryString(String[] nums) {
        int n = nums.length;
        Set<String> set = new HashSet<>();
        for (String num : nums) {
            set.add(num);
        }
        
        for (int i = 0; i < (1 << n); i++) { // Iterate through all possible n-bit binary strings
            String candidate = String.format("%" + n + "s", Integer.toBinaryString(i)).replace(' ', '0');
            if (!set.contains(candidate)) {
                return candidate;
            }
        }
        return ""; // This should never be reached due to constraints
    }
}
