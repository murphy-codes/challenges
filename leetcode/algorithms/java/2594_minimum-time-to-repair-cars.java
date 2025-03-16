// Source: https://leetcode.com/problems/minimum-time-to-repair-cars/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-16

/****************************************
* 
* You are given an integer array `ranks` representing the ranks of some mechanics. `ranks_i` is the rank of the `i^th` mechanic. A mechanic with a rank `r` can repair `n` cars in `r * n2` minutes.
* You are also given an integer `cars` representing the total number of cars waiting in the garage to be repaired.
* Return the minimum time taken to repair all the cars.
* Note: All the mechanics can repair the cars simultaneously.
*
* Example 1:
* Input: ranks = [4,2,3,1], cars = 10
* Output: 16
* Explanation:
* - The first mechanic will repair two cars. The time required is 4 * 2 * 2 = 16 minutes.
* - The second mechanic will repair two cars. The time required is 2 * 2 * 2 = 8 minutes.
* - The third mechanic will repair two cars. The time required is 3 * 2 * 2 = 12 minutes.
* - The fourth mechanic will repair four cars. The time required is 1 * 4 * 4 = 16 minutes.
* It can be proved that the cars cannot be repaired in less than 16 minutes.​​​​​
*
* Example 2:
* Input: ranks = [5,1,8], cars = 6
* Output: 16
* Explanation:
* - The first mechanic will repair one car. The time required is 5 * 1 * 1 = 5 minutes.
* - The second mechanic will repair four cars. The time required is 1 * 4 * 4 = 16 minutes.
* - The third mechanic will repair one car. The time required is 8 * 1 * 1 = 8 minutes.
* It can be proved that the cars cannot be repaired in less than 16 minutes.​​​​​
*
* Constraints:
* • 1 <= ranks.length <= 10^5
* • 1 <= ranks[i] <= 100
* • 1 <= cars <= 10^6
* 
****************************************/

class Solution {
    // Uses binary search on time to find the minimum possible repair time.
    // For a given time limit, checks if all cars can be repaired using mechanics.
    // Each mechanic repairs sqrt(timeLimit / rank) cars within the given time.
    // Time Complexity: O(n * log(m * max_rank)), where n = mechanics, m = cars.
    // Space Complexity: O(1), as only a few extra variables are used.
    public long repairCars(int[] ranks, int cars) {
        // Set the search range: min time is 1, max time is the worst mechanic doing all repairs.
        long left = 1;
        long right = (long) ranks[0] * (long) cars * (long) cars; // Worst case: slowest mechanic does all cars

        while (left < right) {
            long mid = left + (right - left) / 2;
            if (canRepairAllCars(ranks, cars, mid)) {
                right = mid; // Try to minimize time
            } else {
                left = mid + 1; // Need more time
            }
        }

        return left; // The minimal time to repair all cars
    }

    private boolean canRepairAllCars(int[] ranks, int cars, long timeLimit) {
        long carsRepaired = 0;
        for (int rank : ranks) {
            // Each mechanic can repair sqrt(timeLimit / rank) cars in the given time
            carsRepaired += (long) Math.sqrt(timeLimit / rank);
            if (carsRepaired >= cars) return true; // No need to check further
        }
        return false;
    }
}
