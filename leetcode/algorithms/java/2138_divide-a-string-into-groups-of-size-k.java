// Source: https://leetcode.com/problems/divide-a-string-into-groups-of-size-k/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-22
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 42.24 MB Beats 84.09%

/****************************************
* 
* A string `s` can be partitioned into groups of size `k` using the
* _ following procedure:
* • The first group consists of the first `k` characters of the string, the second
* __ group consists of the next `k` characters of the string, and so on. Each
* __ element can be a part of exactly one group.
* • For the last group, if the string does not have `k` characters remaining, a
* _ character fill is used to complete the group.
* Note that the partition is done so that after removing the `fill` character
* _ from the last group (if it exists) and concatenating all the groups in order,
* _ the resultant string should be `s`.
* Given the string `s`, the size of each group `k` and the character `fill`,
* _ return a string array denoting the composition of every group `s` has been
* _ divided into, using the above procedure.
*
* Example 1:
* Input: s = "abcdefghi", k = 3, fill = "x"
* Output: ["abc","def","ghi"]
* Explanation:
* The first 3 characters "abc" form the first group.
* The next 3 characters "def" form the second group.
* The last 3 characters "ghi" form the third group.
* Since all groups can be completely filled by characters from the string, we do not need to use fill.
* Thus, the groups formed are "abc", "def", and "ghi".
*
* Example 2:
* Input: s = "abcdefghij", k = 3, fill = "x"
* Output: ["abc","def","ghi","jxx"]
* Explanation:
* Similar to the previous example, we are forming the first three groups "abc", "def", and "ghi".
* For the last group, we can only use the character 'j' from the string. To complete this group, we add 'x' twice.
* Thus, the 4 groups formed are "abc", "def", "ghi", and "jxx".
*
* Constraints:
* • 1 <= s.length <= 100
* • `s` consists of lowercase English letters only.
* • 1 <= k <= 100
* • `fill` is a lowercase English letter.
* 
****************************************/

class Solution {
    // Divide the string into n groups of size k. If the last group is short,
    // pad it with the given fill character. Substring operations handle each
    // group efficiently, and a StringBuilder pads the last group if needed.
    // Time complexity: O(n), where n = s.length()
    // Space complexity: O(n), due to the result array and possible padding.
    public String[] divideString(String s, int k, char fill) {
        int n = (s.length() + k - 1) / k;
        String[] groups = new String[n];
        for (int i = 0; i < n-1; i++) {
            groups[i] = s.substring(k*i, k*(i+1));
        }
        if (n == s.length() / k) {
            groups[n-1] = s.substring(k*(n-1), k*n);
        } else {
            StringBuilder sb = new StringBuilder();
            int remaining  = (n * k) - s.length();
            sb.append(s.substring(k*(n-1), (k*n)-remaining));
            for (int i = 0; i < remaining; i++) {
                sb.append(fill);
            }
            groups[n-1] = sb.toString();
        }
        return groups;
    }
}