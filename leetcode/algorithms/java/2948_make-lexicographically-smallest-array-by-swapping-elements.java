// Source: https://leetcode.com/problems/make-lexicographically-smallest-array-by-swapping-elements/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-09-23
// At the time of submission:
//   Runtime 40 ms Beats 100.00%
//   Memory 56.16 MB Beats 99.30%

/****************************************
* 
* You are given a 0-indexed array of positive integers `nums` and a positive
* _ integer `limit`.
* In one operation, you can choose any two indices `i` and `j` and swap `nums[i]`
* _ and `nums[j]` if `nums[i] - nums[j]| <= limit`.
* Return the lexicographically smallest array that can be obtained by performing
* _ the operation any number of times.
* An array `a` is lexicographically smaller than an array `b` if in the first
* _ position where `a` and `b` differ, array `a` has an element that is less than
* _ the corresponding element in `b`. For example, the array `[2,10,3]` is
* _ lexicographically smaller than the array `[10,2,3]` because they differ at
* _ index `0` and `2 < 10`.
* 
* Example 1:
* Input: nums = [1,5,3,9,8], limit = 2
* Output: [1,3,5,8,9]
* Explanation: Apply the operation 2 times:
* - Swap nums[1] with nums[2]. The array becomes [1,3,5,9,8]
* - Swap nums[3] with nums[4]. The array becomes [1,3,5,8,9]
* We cannot obtain a lexicographically smaller array by applying any more operations.
* Note that it may be possible to get the same result by doing different operations.
* 
* Example 2:
* Input: nums = [1,7,6,18,2,1], limit = 3
* Output: [1,6,7,18,1,2]
* Explanation: Apply the operation 3 times:
* - Swap nums[1] with nums[2]. The array becomes [1,6,7,18,2,1]
* - Swap nums[0] with nums[4]. The array becomes [2,6,7,18,1,1]
* - Swap nums[0] with nums[5]. The array becomes [1,6,7,18,1,2]
* We cannot obtain a lexicographically smaller array by applying any more operations.
* 
* Example 3:
* Input: nums = [1,7,28,19,10], limit = 3
* Output: [1,7,28,19,10]
* Explanation: [1,7,28,19,10] is the lexicographically smallest array we can obtain because we cannot apply the operation on any two indices.
* 
* Constraints:
* • 1 <= nums.length <= 10^5
* • 1 <= nums[i] <= 10^9
* • 1 <= limit <= 10^9
* 
****************************************/

class Solution {
    // Approach: Sort nums, then split into groups where consecutive values
    // differ by more than limit. Within each group, elements can be freely
    // swapped. Use binary search to find the correct group for each original
    // element, and assign the next available smallest value. Time: O(n log n)
    // for sorting + searches, Space: O(n) for arrays.
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;

        // Step 1: Sort a copy of nums to help form groups
        int[] sorted = Arrays.copyOf(nums, n);
        Arrays.sort(sorted);

        // Step 2: Record the start indices of groups (breaks where diff > limit)
        List<Integer> groupStarts = new ArrayList<>();
        groupStarts.add(0);
        for (int i = 1; i < n; i++) {
            if (sorted[i] - sorted[i - 1] > limit) {
                groupStarts.add(i);
            }
        }

        // Convert list of group starts into array form
        int[] groupIndices = toIntArray(groupStarts);

        // For each group, store its representative starting value
        int[] groupValues = groupStartValues(groupIndices, sorted);

        int[] result = new int[n];

        // Step 3: Build the result array
        for (int i = 0; i < n; i++) {
            int originalVal = nums[i];

            // Find which group this value belongs to (binary search by value)
            int groupIdx = Arrays.binarySearch(groupValues, originalVal);
            if (groupIdx < 0) {
                groupIdx = -(groupIdx + 1) - 1;
            }

            // Place the next available sorted value from this group
            result[i] = sorted[groupIndices[groupIdx]];
            groupIndices[groupIdx]++; // consume that group element
        }

        return result;
    }

    // Convert a List<Integer> to int[]
    private int[] toIntArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    // Extract representative values of each group from sorted array
    private int[] groupStartValues(int[] groupIndices, int[] sorted) {
        int[] values = new int[groupIndices.length];
        for (int i = 0; i < groupIndices.length; i++) {
            values[i] = sorted[groupIndices[i]];
        }
        return values;
    }
}
