// Source: https://leetcode.com/problems/compare-version-numbers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-22
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 41.75 MB Beats 32.01%

/****************************************
* 
* Given two version strings, `version1` and `version2`, compare them. A version
* _ string consists of revisions separated by dots '.'. The value of the
* _ revision is its integer conversion ignoring leading zeros.
* To compare version strings, compare their revision values in left-to-right
* _ order. If one of the version strings has fewer revisions, treat the missing
* _ revision values as `0`.
* Return the following:
* • If `version1 < version2`, return -1.
* • If `version1 > version2`, return 1.
* • Otherwise, return 0.
*
* Example 1:
* Input: version1 = "1.2", version2 = "1.10"
* Output: -1
* Explanation:
* version1's second revision is "2" and version2's second revision is "10": 2 < 10, so version1 < version2.
*
* Example 2:
* Input: version1 = "1.01", version2 = "1.001"
* Output: 0
* Explanation:
* Ignoring leading zeroes, both "01" and "001" represent the same integer "1".
*
* Example 3:
* Input: version1 = "1.0", version2 = "1.0.0.0"
* Output: 0
* Explanation:
* version1 has less revisions, which means every missing revision are treated as "0".
*
* Constraints:
* • `1 <= version1.length, version2.length <= 500`
* • `version1` and `version2` only contain digits and `'.'`.
* • `version1` and `version2` are valid version numbers.
* • All the given revisions in `version1` and `version2` can be stored in a 32-bit integer.
* 
****************************************/

class Solution {
    // This solution parses version numbers in-place without splitting,
    // comparing each revision as integers until a difference is found.
    // Time complexity: O(n + m), where n and m are the lengths of the
    // version strings (each scanned once). Space complexity: O(1).
    public int compareVersion(String version1, String version2) {
        int idx1 = 0, idx2 = 0;
        int len1 = version1.length(), len2 = version2.length();

        // Process both version strings until the end
        while (idx1 < len1 || idx2 < len2) {
            long rev1 = 0, rev2 = 0; // revision numbers for this segment

            // Parse next revision number from version1
            while (idx1 < len1 && version1.charAt(idx1) != '.') {
                rev1 = rev1 * 10 + (version1.charAt(idx1) - '0');
                idx1++;
            }

            // Parse next revision number from version2
            while (idx2 < len2 && version2.charAt(idx2) != '.') {
                rev2 = rev2 * 10 + (version2.charAt(idx2) - '0');
                idx2++;
            }

            // Compare revision numbers
            if (rev1 > rev2) return 1;
            if (rev1 < rev2) return -1;

            // Skip the '.' delimiter if present
            if (idx1 < len1 && version1.charAt(idx1) == '.') idx1++;
            if (idx2 < len2 && version2.charAt(idx2) == '.') idx2++;
        }

        return 0;
    }
}
