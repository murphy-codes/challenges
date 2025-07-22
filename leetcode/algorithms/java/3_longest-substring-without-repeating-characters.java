// Source: https://leetcode.com/problems/longest-substring-without-repeating-characters/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2024-12-27
// At the time of submission:
//   Runtime 6 ms Beats 72.64%
//   Memory 44.92 MB Beats 31.87%

/****************************************
* 
* Given a string s, find the length of the longest substring without repeating characters.
* 
* Example 1:
* Input: s = "abcabcbb"
* Output: 3
* Explanation: The answer is "abc", with the length of 3.
* 
* Example 2:
* Input: s = "bbbbb"
* Output: 1
* Explanation: The answer is "b", with the length of 1.
* 
* Example 3:
* Input: s = "pwwkew"
* Output: 3
* Explanation: The answer is "wke", with the length of 3.
* Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
*  
* Constraints:
* • 0 <= s.length <= 5 * 104
* • s consists of English letters, digits, symbols and spaces.
* 
****************************************/

class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n=s.length(), ans=0;
        Map<Character, Integer> charCnt=new HashMap<>();
        int l=0;
        for(int r=0;r<n;r++) {
            char c=s.charAt(r);
            while(l<=r && charCnt.containsKey(c)) {
                charCnt.put(s.charAt(l), charCnt.get(s.charAt(l)) - 1);
                if(charCnt.get(s.charAt(l)) == 0)
                    charCnt.remove(s.charAt(l));
                l++;
            }
            charCnt.put(c, charCnt.getOrDefault(c, 0) + 1);

            ans=Math.max(ans, r-l+1);
        }
        return ans;
    }
}