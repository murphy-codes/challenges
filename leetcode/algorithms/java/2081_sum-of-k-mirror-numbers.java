// Source: https://leetcode.com/problems/sum-of-k-mirror-numbers/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-06-22
// At the time of submission:
//   Runtime 963 ms Beats 17.86%
//   Memory 44.52 MB Beats 51.79%

/****************************************
* 
* A k-mirror number is a positive integer without leading zeros that reads the
* _ same both forward and backward in base-10 as well as in base-k.
* • For example, `9` is a 2-mirror number. The representation of `9` in base-10
* __ and base-2 are `9` and `1001` respectively, which read the same both forward
* __ and backward.
* • On the contrary, `4` is not a 2-mirror number. The representation of `4` in
* __ base-2 is `100`, which does not read the same both forward and backward.
* Given the base `k` and the number `n`, return the sum of the
* _ `n` smallest k-mirror numbers.
*
* Example 1:
* Input: k = 2, n = 5
* Output: 25
* Explanation:
* The 5 smallest 2-mirror numbers and their representations in base-2 are listed as follows:
*   base-10    base-2
*     1          1
*     3          11
*     5          101
*     7          111
*     9          1001
* Their sum = 1 + 3 + 5 + 7 + 9 = 25.
*
* Example 2:
* Input: k = 3, n = 7
* Output: 499
* Explanation:
* The 7 smallest 3-mirror numbers are and their representations in base-3 are listed as follows:
*   base-10    base-3
*     1          1
*     2          2
*     4          11
*     8          22
*     121        11111
*     151        12121
*     212        21212
* Their sum = 1 + 2 + 4 + 8 + 121 + 151 + 212 = 499.
*
* Example 3:
* Input: k = 7, n = 17
* Output: 20379000
* Explanation: The 17 smallest 7-mirror numbers are:
* 1, 2, 3, 4, 5, 6, 8, 121, 171, 242, 292, 16561, 65656, 2137312, 4602064, 6597956, 6958596
*
* Constraints:
* • 2 <= k <= 9
* • 1 <= n <= 30
* 
****************************************/

class Solution {
    // Generate palindromes in increasing order and check if they are k-mirror.
    // A k-mirror number is palindromic in both base-10 and base-k. We build
    // odd- and even-length base-10 palindromes separately to maintain order.
    // For each palindrome, convert to base-k and check if it's also a palindrome.
    // Time: O(n * log^2 x), where x is the largest k-mirror number found.
    // Space: O(log x) for string conversions; overall space is O(1) extra.
    public long kMirror(int k, int n) {
        long sum = 0;
        int count = 0;
        int length = 1;

        while (count < n) {
            // First, generate all odd-length palindromes
            for (long half = (long) Math.pow(10, length - 1); half < Math.pow(10, length); half++) {
                String s = Long.toString(half);
                StringBuilder rev = new StringBuilder(s).reverse();
                long pal = Long.parseLong(s + rev.substring(1));
                if (isKMirror(pal, k)) {
                    sum += pal;
                    count++;
                    if (count == n) return sum;
                }
            }

            // Then, generate even-length palindromes
            for (long half = (long) Math.pow(10, length - 1); half < Math.pow(10, length); half++) {
                String s = Long.toString(half);
                StringBuilder rev = new StringBuilder(s).reverse();
                long pal = Long.parseLong(s + rev);
                if (isKMirror(pal, k)) {
                    sum += pal;
                    count++;
                    if (count == n) return sum;
                }
            }

            length++;
        }


        return sum;
    }

    private boolean isKMirror(long num, int k) {
        StringBuilder baseK = new StringBuilder();
        while (num > 0) {
            baseK.append(num % k);
            num /= k;
        }
        String s = baseK.toString();
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i++) != s.charAt(j--)) return false;
        }
        return true;
    }
}
