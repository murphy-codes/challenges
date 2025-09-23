// Source: https://leetcode.com/problems/compare-version-numbers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-21
// At the time of submission:
//   Runtime 1 ms Beats 76.03%
//   Memory 41.48 MB Beats 83.19%

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
    // Split both version strings by '.' into revision arrays. Compare revisions
    // one by one as integers, padding with 0 if one version is shorter. Return
    // -1, 1, or 0 depending on the first unequal pair. If all are equal, return 0.
    // Time complexity: O(n + m) where n, m are lengths of version1 and version2.
    // Space complexity: O(n + m) for storing the split revision arrays.
    public int compareVersion(String version1, String version2) {
        String[] reV1 = version1.split("\\.");
        String[] reV2 = version2.split("\\.");
        for (int i = 0; i < Math.max(reV1.length, reV2.length); i++) {
            int v1 = i < reV1.length ? Integer.parseInt(reV1[i]) : 0;
            int v2 = i < reV2.length ? Integer.parseInt(reV2[i]) : 0;
            if (v1 < v2) return -1;
            if (v1 > v2) return 1;
        }
        return 0;
    }
}
