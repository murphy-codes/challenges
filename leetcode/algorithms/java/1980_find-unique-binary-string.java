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

class Solution {
    // This solution leverages Cantor’s Diagonalization to construct a unique
    // binary string in O(n) time. By flipping the ith character of the ith
    // string in nums, we ensure the result differs from every string in nums
    // by at least one character. Since nums contains exactly n unique n-bit
    // strings, this approach guarantees a valid answer in O(n) space as well.
    public String findDifferentBinaryString(String[] nums) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append(nums[i].charAt(i) == '0' ? '1' : '0');
        }
        return sb.toString();
    }
}

