// Source: https://leetcode.com/problems/count-the-number-of-substrings-with-dominant-ones/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-15
// At the time of submission:
//   Runtime 323 ms Beats 75.00%
//   Memory 46.88 MB Beats 31.48%

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

import java.util.ArrayList;
import java.util.List;

class Solution {
    // This solution uses a sqrt optimization based on the fact that a valid
    // substring can contain at most sqrt(n) zeros. For each starting index, we
    // count substrings with no zeros, then consider up to sqrt(n) zero positions
    // as candidates for the rightmost zero. For each case we determine the
    // earliest ending index that satisfies ones >= zeros^2. Time: O(n*sqrt(n)).
    // Space: O(sqrt(n) + number_of_zeros).
    public int numberOfSubstrings(String s) {
        int n = s.length();
        char[] a = s.toCharArray();
        List<Integer> zeros = new ArrayList<>();

        // Collect all zero positions
        for (int i = 0; i < n; i++) {
            if (a[i] == '0') zeros.add(i);
        }

        int m = zeros.size();
        int limit = (int) Math.sqrt(n) + 2;
        long ans = 0;

        for (int l = 0; l < n; l++) {

            // Handle substrings with NO zeros
            int firstZero = firstZeroAtLeast(zeros, l);
            int rightBound = (firstZero == m ? n : zeros.get(firstZero));
            ans += (rightBound - l);

            // Handle substrings with k zeros
            int ones = 0;
            int prev = l;

            for (int k = 1; k <= limit; k++) {
                int idx = firstZero + k - 1;
                if (idx >= m) break;

                int z = zeros.get(idx);
                ones += (z - prev);    // count ones between these zeros
                prev = z + 1;

                int requiredOnes = k * k;
                int need = requiredOnes - ones; // how many more ones needed

                int minR = z + need;  
                if (minR < z) minR = z;

                int nextZero = (idx + 1 < m ? zeros.get(idx + 1) : n);
                if (minR < nextZero) {
                    ans += (nextZero - minR);
                }
            }
        }

        return (int) ans;
    }

    // binary search: first zero at index >= x
    private int firstZeroAtLeast(List<Integer> zeros, int x) {
        int lo = 0, hi = zeros.size();
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (zeros.get(mid) >= x) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }
}
