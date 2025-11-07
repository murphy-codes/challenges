// Source: https://leetcode.com/problems/maximize-the-minimum-powered-city/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-07
// At the time of submission:
//   Runtime 26 ms Beats 97.75%
//   Memory 89.21 MB Beats 5.62%

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

class Solution {
    // Uses prefix sums + binary search + rolling difference technique to find
    // the maximum achievable minimum power across cities. For each candidate
    // target, checks feasibility with ≤ k added stations using a lazy range
    // update array. Runs in O(n log M) time and O(n) space, where M ≈ 1e18.
    public long maxPower(int[] stations, int r, int k) {
        int n = stations.length;
        // Prefix sum array
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stations[i];
        }
        // Compute initial power of each city within distance r
        long[] power = new long[n];
        for (int i = 0; i < n; i++) {
            int left = Math.max(0, i - r);
            int right = Math.min(n - 1, i + r);
            power[i] = prefix[right + 1] - prefix[left];
        }
        // Binary search for the maximum achievable minimum power
        long low = 0, high = (long)1e18;
        long ans = 0;
        while (low <= high) {
            long mid = (low + high) / 2;
            if (canAchieve(mid, power, r, k)) {
                ans = mid;       // mid is achievable, try for more
                low = mid + 1;
            } else {
                high = mid - 1;  // mid too high, reduce search space
            }
        }

        return ans;
    }

    // Check if every city can reach at least 'target' power using ≤ k stations
    private boolean canAchieve(long target, long[] power, int r, long k) {
        int n = power.length;
        long[] added = new long[n];  // added[i] = stations added affecting city i
        long used = 0;               // total stations used so far
        long extra = 0;              // rolling sum of active added stations

        for (int i = 0; i < n; i++) {
            // Remove effects of stations whose range no longer includes i
            if (i - r - 1 >= 0) extra -= added[i - r - 1];
            // If power is below target, add needed stations optimally
            if (power[i] + extra < target) {
                long need = target - (power[i] + extra);
                if (need > k - used) return false;    // not enough stations left
                used += need;
                added[Math.min(n - 1, i + r)] = need; // schedule end of effect
                extra += need;                        // update current window
            }
        }
        return true;
    }
}
