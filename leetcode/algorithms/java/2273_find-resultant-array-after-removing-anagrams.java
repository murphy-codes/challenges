// Source: https://leetcode.com/problems/find-resultant-array-after-removing-anagrams/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-12
// At the time of submission:
//   Runtime 2 ms Beats 93.48%
//   Memory 44.96 MB Beats 33.97%

/****************************************
* 
* You are given a 0-indexed string array `words`, where `words[i]` consists of
* _ lowercase English letters.
* In one operation, select any index `i` such that `0 < i < words.length` and
* _ `words[i - 1]` and `words[i]` are anagrams, and delete `words[i]` from words.
* _ Keep performing this operation as long as you can select an index that
* _ satisfies the conditions.
* Return `words` after performing all operations. It can be shown that selecting
* _ the indices for each operation in any arbitrary order will lead to the
* _ same result.
* An Anagram is a word or phrase formed by rearranging the letters of a different
* _ word or phrase using all the original letters exactly once. For example,
* _ `"dacb"` is an anagram of `"abdc"`.
*
* Example 1:
* Input: words = ["abba","baba","bbaa","cd","cd"]
* Output: ["abba","cd"]
* Explanation:
* One of the ways we can obtain the resultant array is by using the following operations:
* - Since words[2] = "bbaa" and words[1] = "baba" are anagrams, we choose index 2 and delete words[2].
* Now words = ["abba","baba","cd","cd"].
* - Since words[1] = "baba" and words[0] = "abba" are anagrams, we choose index 1 and delete words[1].
* Now words = ["abba","cd","cd"].
* - Since words[2] = "cd" and words[1] = "cd" are anagrams, we choose index 2 and delete words[2].
* Now words = ["abba","cd"].
* We can no longer perform any operations, so ["abba","cd"] is the final answer.
*
* Example 2:
* Input: words = ["a","b","c","d","e"]
* Output: ["a","b","c","d","e"]
* Explanation:
* No two adjacent strings in words are anagrams of each other, so no operations are performed.
*
* Constraints:
* • `1 <= words.length <= 100`
* • `1 <= words[i].length <= 10`
* • `words[i]` consists of lowercase English letters.
* 
****************************************/

class Solution {
    // Iterate through the words list, keeping only those that are not anagrams
    // of the last kept word. Uses a frequency count (int[26]) to detect anagrams
    // efficiently in O(k) time per comparison, where k is word length. (max 10)
    // Overall complexity: O(n * k) time and O(1) extra space, since k ≤ 10 and
    // the counting array is reused for each comparison.
    public List<String> removeAnagrams(String[] words) {
        List<String> res = new ArrayList<>();
        res.add(words[0]);
        int[] count = new int[26];
        for (char c : words[0].toCharArray()) count[c - 'a']++;

        for (int i = 1; i < words.length; i++) {
            if (!areAnagrams(count, words[i])) {
                res.add(words[i]);
                count = new int[26];
                for (char c : words[i].toCharArray()) count[c - 'a']++;
            }
        }
        return res;
    }
    private boolean areAnagrams(int[] count, String word) {
        int[] temp = count.clone(); // make a copy to preserve the original
        for (char c : word.toCharArray()) temp[c - 'a']--;
        for (int n : temp) if (n != 0) return false;
        return true;
    }
}