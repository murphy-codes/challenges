// Source: https://leetcode.com/problems/remove-all-occurrences-of-a-substring/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-11

/****************************************
* 
* Given two strings `s` and `part`, perform the following operation on `s` until all occurrences of the substring `part` are removed:
* • Find the leftmost occurrence of the substring `part` and remove it from `s`.
* Return s after removing all occurrences of `part`.
* A substring is a contiguous sequence of characters in a string.
* 
* Example 1:
* Input: s = "daabcbaabcbc", part = "abc"
* Output: "dab"
* Explanation: The following operations are done:
* - s = "daabcbaabcbc", remove "abc" starting at index 2, so s = "dabaabcbc".
* - s = "dabaabcbc", remove "abc" starting at index 4, so s = "dababc".
* - s = "dababc", remove "abc" starting at index 3, so s = "dab".
* Now s has no occurrences of "abc".
* 
* Example 2:
* Input: s = "axxxxyyyyb", part = "xy"
* Output: "ab"
* Explanation: The following operations are done:
* - s = "axxxxyyyyb", remove "xy" starting at index 4 so s = "axxxyyyb".
* - s = "axxxyyyb", remove "xy" starting at index 3 so s = "axxyyb".
* - s = "axxyyb", remove "xy" starting at index 2 so s = "axyb".
* - s = "axyb", remove "xy" starting at index 1 so s = "ab".
* Now s has no occurrences of "xy".
* 
* Constraints:
* • 1 <= s.length <= 1000
* • 1 <= part.length <= 1000
* • `s​​​​​`​ and `part` consists of lowercase English letters.
* 
****************************************/

class Solution {
    // We use a StringBuilder as a stack to build the result efficiently.
    // For each character in 's', we append it and check if the last 'part.length()'
    // characters match 'part'. If they do, we remove them immediately.
    // This ensures each character is processed at most twice, giving O(n) time.
    // Space complexity is O(n) due to the StringBuilder storage.
    public String removeOccurrences(String s, String part) {
        StringBuilder sb = new StringBuilder();
        int partLength = part.length();

        for (char c : s.toCharArray()) {
            sb.append(c);
            // Check if the last characters in sb form 'part'
            if (sb.length() >= partLength && sb.substring(sb.length() - partLength).equals(part)) {
                sb.setLength(sb.length() - partLength); // Remove 'part'
            }
        }

        return sb.toString();
    }
}
