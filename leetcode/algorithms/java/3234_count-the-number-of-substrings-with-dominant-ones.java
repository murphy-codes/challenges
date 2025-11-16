// Source: https://leetcode.com/problems/count-the-number-of-substrings-with-dominant-ones/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-15
// At the time of submission:
//   Runtime 57 ms Beats 91.67%
//   Memory 47.04 MB Beats 26.85%

/****************************************
* 
* You are given a binary string `s`.
* Return the number of substrings with dominant ones.
* A string has dominant ones if the number of ones in the string is greater
* _ than or equal to the square of the number of zeros in the string.
*
* Example 1:
* Input: s = "00011"
* Output: 5
* Explanation:
* The substrings with dominant ones are shown in the table below.
* i	j	s[i..j]	# Zeros	# Ones
* 3	3	1	0	1
* 4	4	1	0	1
* 2	3	01	1	1
* 3	4	11	0	2
* 2	4	011	1	2
*
* Example 2:
* Input: s = "101101"
* Output: 16
* Explanation:
* The substrings with non-dominant ones are shown in the table below.
* Since there are 21 substrings total and 5 of them have non-dominant ones, it follows that there are 16 substrings with dominant ones.
* i	j	s[i..j]	# Zeros	# Ones
* 1	1	0	1	0
* 4	4	0	1	0
* 1	4	0110	2	2
* 0	4	10110	2	3
* 1	5	01101	2	3
*
* Constraints:
* • `1 <= s.length <= 4 * 10^4`
* • `s` consists only of characters `'0'` and `'1'`.
* 
****************************************/

class Solution {
    // This solution enumerates all substrings ending at each index using a
    // prefix-based scan over zero positions. A substring with dominant ones
    // contains at most sqrt(n) zeros, so for each endpoint we walk backward
    // only through this limited set. Counts of valid starts are computed in
    // constant time using zero gaps and needed ones. Time: O(n*sqrt(n)).
    // Space: O(n) to store zero positions.
  public int numberOfSubstrings(String s) {
    int n = s.length();

    // zerosIdx[i] = the index (in s) of the i-th zero; zerosIdx[0] = -1 sentinel
    int[] zerosIdx = new int[n + 1];
    zerosIdx[0] = -1;

    int zeroCount = 1;          // next slot to write zero index
    int totalOnes = 0;          // total number of ones seen so far
    int result = 0;

    for (int right = 0; right < n; right++) {

      // Case 1: s[right] == '0' → record zero position
      if (s.charAt(right) == '0') {
        zerosIdx[zeroCount++] = right;
      }

      // Case 2: s[right] == '1' → all substrings ending here with no zeros are valid
      else {
        result += right - zerosIdx[zeroCount - 1];
        totalOnes++;
      }

      // Now consider substrings ending at 'right' with k zeros (k >= 1)
      // Walk backward through previous zeros until k^2 > totalOnes
      for (int zp = zeroCount - 1;
           zp > 0 && (zeroCount - zp) * (zeroCount - zp) <= totalOnes;
           zp--) {

        int zerosInSub = zeroCount - zp;                // number of zeros in substring
        int onesInSub = (right - zerosIdx[zp] + 1)      // substring length
                        - zerosInSub;                    // minus zeros
        int neededOnes = zerosInSub * zerosInSub;        // ones >= zeros^2
        int onesDeficit = neededOnes - onesInSub;        // how many ones missing

        // Extendable region: how many possible left positions exist before this zero
        int extendableStarts = zerosIdx[zp] - zerosIdx[zp - 1];

        // Valid starts = extendable - those too close to the right (lack ones)
        result += Math.max(extendableStarts - Math.max(onesDeficit, 0), 0);
      }
    }

    return result;
  }
}
