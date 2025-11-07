// Source: https://leetcode.com/problems/maximize-the-minimum-powered-city/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-07
// At the time of submission:
//   Runtime 29 ms Beats 92.13%
//   Memory 89.48 MB Beats 5.62%

/****************************************
* 
* You are given a 0-indexed integer array `stations` of length `n`, where
* _ `stations[i]` represents the number of power stations in the `i^th` city.
* Each power station can provide power to every city in a fixed range. In other
* _ words, if the range is denoted by `r`, then a power station at city `i` can
* _ provide power to all cities `j` such that `|i - j| <= r` and
* _ `0 <= i, j <= n - 1`.
* Note that `|x|` denotes absolute value. For example, `|7 - 5| = 2` and
* _ `|3 - 10| = 7`.
* The power of a city is the total number of power stations it is being provided
* _ power from.
* The government has sanctioned building `k` more power stations, each of which
* _ can be built in any city, and have the same range as the pre-existing ones.
* Given the two integers `r` and `k`, return the maximum possible minimum
* _ power of a city, if the additional power stations are built optimally.
* Note that you can build the `k` power stations in multiple cities.
*
* Example 1:
* Input: stations = [1,2,4,5,0], r = 1, k = 2
* Output: 5
* Explanation:
* One of the optimal ways is to install both the power stations at city 1.
* So stations will become [1,4,4,5,0].
* - City 0 is provided by 1 + 4 = 5 power stations.
* - City 1 is provided by 1 + 4 + 4 = 9 power stations.
* - City 2 is provided by 4 + 4 + 5 = 13 power stations.
* - City 3 is provided by 5 + 4 = 9 power stations.
* - City 4 is provided by 5 + 0 = 5 power stations.
* So the minimum power of a city is 5.
* Since it is not possible to obtain a larger power, we return 5.
*
* Example 2:
* Input: stations = [4,4,4,4], r = 0, k = 3
* Output: 4
* Explanation:
* It can be proved that we cannot make the minimum power of a city greater than 4.
*
* Constraints:
* • `n == stations.length`
* • `1 <= n <= 10^5`
* • `0 <= stations[i] <= 10^5`
* • `0 <= r <= n - 1`
* • `0 <= k <= 10^9`
* 
****************************************/

import java.util.Arrays;

class Solution {
    // Binary search + line sweep solution.
    // Each city’s initial power is computed via prefix sums, then we binary
    // search the maximum minimum achievable power. For each guess, we greedily
    // simulate adding stations to maintain ≥ target power using a sliding window.
    // Time: O(n log M), Space: O(n), where M is the maximum possible power value.
    public long maxPower(int[] stations, int r, int k) {
        int n = stations.length;
        long[] power = new long[n];
        long[] prefix = new long[n + 1];

        // Precompute prefix sums for efficient range power computation
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stations[i];
        }

        // Compute initial power for each city using the range [i - r, i + r]
        for (int i = 0; i < n; i++) {
            int left = Math.max(0, i - r);
            int right = Math.min(n - 1, i + r);
            power[i] = prefix[right + 1] - prefix[left];
        }

        long low = 0, high = (long) 1e18, ans = 0;

        // Binary search to maximize the minimum power
        while (low <= high) {
            long mid = (low + high) / 2;
            if (canAchieve(mid, power, r, k, n)) {
                ans = mid;
                low = mid + 1; // try for higher
            } else {
                high = mid - 1; // too high, lower it
            }
        }

        return ans;
    }

    private boolean canAchieve(long target, long[] power, int r, long k, int n) {
        long[] added = new long[n];
        long used = 0, currAdd = 0;

        for (int i = 0; i < n; i++) {
            // Remove influence of a station that’s now out of range
            if (i - r - 1 >= 0) currAdd -= added[i - r - 1];

            long currPower = power[i] + currAdd;

            // If this city’s power < target, we must add stations
            if (currPower < target) {
                long need = target - currPower;
                used += need;
                if (used > k) return false; // not feasible

                currAdd += need; // new stations affect forward range
                int pos = (int) Math.min(n - 1, i + r);
                added[pos] += need;
            }
        }

        return true;
    }
}
