// Source: https://leetcode.com/problems/lexicographically-smallest-equivalent-string/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-05
// At the time of submission:
//   Runtime 2 ms Beats 97.03%
//   Memory 41.88 MB Beats 89.95%

/****************************************
* 
* You are given two strings of the same length `s1` and `s2` and a string `baseStr`.
* We say `s1[i]` and `s2[i]` are equivalent characters.
* For example, if `s1 = "abc"` and `s2 = "cde"`, then we have
* _ `'a' == 'c'`, `'b' == 'd'`, and `'c' == 'e'`.
* Equivalent characters follow the usual rules of any equivalence relation:
* • Reflexivity: `'a' == 'a'`.
* • Symmetry: `'a' == 'b'` implies `'b' == 'a'`.
* • Transitivity: `'a' == 'b'` and `'b' == 'c'` implies `'a' == 'c'`.
* For example, given the equivalency information from `s1 = "abc"` and `s2 = "cde"`,
* _ `"acd"` and `"aab"` are equivalent strings of `baseStr = "eed"`, and `"aab"`
* _ is the lexicographically smallest equivalent string of `baseStr`.
* Return the lexicographically smallest equivalent string of `baseStr` by using
* _ the equivalency information from `s1` and `s2`.
*
* Example 1:
* Input: s1 = "parker", s2 = "morris", baseStr = "parser"
* Output: "makkek"
* Explanation: Based on the equivalency information in s1 and s2, we can group their characters as [m,p], [a,o], [k,r,s], [e,i].
* The characters in each group are equivalent and sorted in lexicographical order.
* So the answer is "makkek".
*
* Example 2:
* Input: s1 = "hello", s2 = "world", baseStr = "hold"
* Output: "hdld"
* Explanation: Based on the equivalency information in s1 and s2, we can group their characters as [h,w], [d,e,o], [l,r].
* So only the second letter 'o' in baseStr is changed to 'd', the answer is "hdld".
*
* Example 3:
* Input: s1 = "leetcode", s2 = "programs", baseStr = "sourcecode"
* Output: "aauaaaaada"
* Explanation: We group the equivalent characters in s1 and s2 as [a,o,e,r,s,c], [l,p], [g,t] and [d,m], thus all letters in baseStr except 'u' and 'd' are transformed to 'a', the answer is "aauaaaaada".
*
* Constraints:
* • 1 <= s1.length, s2.length, baseStr <= 1000
* • s1.length == s2.length
* • `s1`, `s2`, and `baseStr` consist of lowercase English letters.
* 
****************************************/

class Solution {
    static int parent[];
    public static int find(int val)
    {
        if(parent[val]!=val)
        {
            parent[val]=find(parent[val]);
            
        }
        return parent[val];
    }
    public static void union(int a,int b)
    {
        int leta=find(a);
        int letb=find(b);

        if(leta<letb)
        {
            parent[letb]=leta;
        }
        else
        {
            parent[leta]=letb;
        }
    }
    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        int n=baseStr.length();
        parent=new int[26];
        for(int i=0;i<26;i++)
        {
            parent[i]=i;
        }

        for(int i=0;i<s1.length();i++)
        {
            union(s1.charAt(i)-'a',s2.charAt(i)-'a');
        }

        StringBuilder res = new StringBuilder();
        for(int i=0;i<n;i++)
        {
            char chh=baseStr.charAt(i);
            res.append((char)(find(chh-'a')+'a'));
        }
        return res.toString();
    }
}