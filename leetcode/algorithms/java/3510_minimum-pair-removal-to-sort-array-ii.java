// Source: https://leetcode.com/problems/minimum-pair-removal-to-sort-array-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-01-23
// At the time of submission:
//   Runtime 583 ms Beats 98.79%
//   Memory 195.04 MB Beats 21.86%

/****************************************
* 
* Given an array `nums`, you can perform the following operation any number of times:
* • Select the adjacent pair with the minimum sum in `nums`. If multiple such
* __ pairs exist, choose the leftmost one.
* • Replace the pair with their sum.
* Return the minimum number of operations needed to make the array non-decreasing.
* An array is said to be non-decreasing if each element is greater than or equal
* _ to its previous element (if it exists).
*
* Example 1:
* Input: nums = [5,2,3,1]
* Output: 2
* Explanation:
* The pair `(3,1)` has the minimum sum of 4. After replacement, `nums = [5,2,4]`.
* The pair `(2,4)` has the minimum sum of 6. After replacement, `nums = [5,6]`.
* The array `nums` became non-decreasing in two operations.
*
* Example 2:
* Input: nums = [1,2,2]
* Output: 0
* Explanation:
* The array `nums` is already sorted.
*
* Constraints:
* • `1 <= nums.length <= 10^5`
* • `-10^9 <= nums[i] <= 10^9`
* 
****************************************/

import java.util.TreeSet;

class Solution {
    // Simulate forced merges using a linked list and ordered pair sums.
    // Track adjacent inversions to know when array becomes non-decreasing.
    // TreeSet maintains the leftmost minimum-sum adjacent pair at all times.
    // Each merge updates only affected neighbors and inversion count.
    // Time: O(n log n), Space: O(n)
    public int minimumPairRemoval(int[] nums) {
        int n = nums.length;

        // Use long to avoid overflow during merges
        long[] values = new long[n];
        for (int i = 0; i < n; i++) values[i] = nums[i];

        // Doubly linked list via indices
        int[] next = new int[n];
        int[] prev = new int[n];
        for (int i = 0; i < n; i++) {
            next[i] = i + 1;
            prev[i] = i - 1;
        }

        // Stores {pairSum, leftIndex}, ordered by sum then index
        TreeSet<long[]> pairs = new TreeSet<>((a, b) -> {
            if (a[0] != b[0]) return Long.compare(a[0], b[0]);
            return Long.compare(a[1], b[1]);
        });

        // Count adjacent inversions
        int inversions = 0;
        for (int i = 0; i < n - 1; i++) {
            if (values[i] > values[i + 1]) inversions++;
            pairs.add(new long[]{values[i] + values[i + 1], i});
        }

        int operations = 0;

        while (inversions > 0) {
            long[] minPair = pairs.pollFirst();
            int left = (int) minPair[1];
            int right = next[left];

            int leftNeighbor = prev[left];
            int rightNeighbor = next[right];

            // Remove internal inversion
            if (values[left] > values[right]) inversions--;

            long merged = values[left] + values[right];

            // Left neighbor impact
            if (leftNeighbor >= 0) {
                boolean wasBad = values[leftNeighbor] > values[left];
                boolean isBad = values[leftNeighbor] > merged;

                if (wasBad && !isBad) inversions--;
                else if (!wasBad && isBad) inversions++;

                pairs.remove(new long[]{values[leftNeighbor] + values[left], leftNeighbor});
                pairs.add(new long[]{values[leftNeighbor] + merged, leftNeighbor});
            }

            // Right neighbor impact
            if (rightNeighbor < n) {
                boolean wasBad = values[right] > values[rightNeighbor];
                boolean isBad = merged > values[rightNeighbor];

                if (wasBad && !isBad) inversions--;
                else if (!wasBad && isBad) inversions++;

                pairs.remove(new long[]{values[right] + values[rightNeighbor], right});
                pairs.add(new long[]{merged + values[rightNeighbor], left});

                prev[rightNeighbor] = left;
            }

            // Finalize merge
            next[left] = rightNeighbor;
            values[left] = merged;
            operations++;
        }

        return operations;
    }
}
