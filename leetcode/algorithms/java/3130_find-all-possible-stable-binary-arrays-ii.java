// Source: https://leetcode.com/problems/find-all-possible-stable-binary-arrays-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-09
// At the time of submission:
//   Runtime 38 ms Beats 100.00%
//   Memory 53.82 MB Beats 89.66%

/****************************************
* 
* You are given 3 positive integers `zero`, `one`, and `limit`.
* A binary array `arr` is called stable if:
* • The number of occurrences of 0 in `arr` is exactly `zero`.
* • The number of occurrences of 1 in `arr` is exactly `one`.
* • Each subarray of arr with a size greater than `limit` must contain both 0 and 1.
* Return the total number of stable binary arrays.
* Since the answer may be very large, return it modulo `10^9 + 7`.
*
* Example 1:
* Input: zero = 1, one = 1, limit = 2
* Output: 2
* Explanation:
* The two possible stable binary arrays are [1,0] and [0,1].
*
* Example 2:
* Input: zero = 1, one = 2, limit = 1
* Output: 1
* Explanation:
* The only possible stable binary array is [1,0,1].
*
* Example 3:
* Input: zero = 3, one = 3, limit = 2
* Output: 14
* Explanation:
* All the possible stable binary arrays are [0,0,1,0,1,1], [0,0,1,1,0,1], [0,1,0,0,1,1], [0,1,0,1,0,1], [0,1,0,1,1,0], [0,1,1,0,0,1], [0,1,1,0,1,0], [1,0,0,1,0,1], [1,0,0,1,1,0], [1,0,1,0,0,1], [1,0,1,0,1,0], [1,0,1,1,0,0], [1,1,0,0,1,0], and [1,1,0,1,0,0].
*
* Constraints:
* • `1 <= zero, one, limit <= 1000`
* 
****************************************/

class Solution {
    // Instead of building arrays element-by-element, we treat them as alternating
    // groups of zeros and ones where each group length is between 1 and limit.
    // We first compute the number of ways to split a total length into k groups
    // with bounded size using prefix-sum DP. Then we combine zero-group and
    // one-group counts to count valid alternating sequences.
    // Time: O(max(zero, one)^2), Space: O(max(zero, one)^2).

	private static final int MOD = (int)1e9 + 7;
	private static final long MOD_LONG = MOD;

	private static int add(int a, int b) {
		return (a + b) % MOD;
	}

	private static int subtract(int a, int b) {
		return (a + MOD - b) % MOD;
	}

	private static int multiply(int a, int b) {
		return (int)((long)a * b % MOD_LONG);
	}

	public static int numberOfStableArrays(int zero, int one, int limit) {

		int maxCount = Math.max(zero, one);

		// ways[g][s] = ways to split total sum s into g groups
		// where each group size is between 1 and limit
		int[][] ways = new int[maxCount + 1][maxCount + 1];

		int[] prefix = new int[maxCount + 1];

		int[] prevRow = ways[0];
		prevRow[0] = 1;

		// Build DP table for bounded compositions
		for (int groups = 1; groups <= maxCount; groups++) {

			int maxSum = Math.min(groups * limit, maxCount);

			int running = 0;

			for (int s = 0; s <= maxSum; s++) {
				prefix[s] = running;
				running = add(running, prevRow[s]);
			}

			prevRow = ways[groups];

			for (int s = groups; s <= maxSum; s++) {

				prevRow[s] =
						s < limit
						? prefix[s]
						: subtract(prefix[s], prefix[s - limit]);
			}
		}

		int result = maxCount <= limit ? 2 : 0;

		int zeroWays = ways[1][zero];
		int oneWays  = ways[1][one];

		for (int groups = 2; groups <= maxCount; groups++) {

			int prevZero = zeroWays;
			int prevOne  = oneWays;

			zeroWays = ways[groups][zero];
			oneWays  = ways[groups][one];

			result = add(result, multiply(zeroWays, prevOne));
			result = add(result, multiply(oneWays, prevZero));

			int both = multiply(zeroWays, oneWays);
			result = add(result, add(both, both));
		}

		return result;
	}
}