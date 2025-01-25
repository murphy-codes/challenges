// Source: https://leetcode.com/problems/make-lexicographically-smallest-array-by-swapping-elements/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-24

/****************************************
* 
* You are given a 0-indexed array of positive integers `nums` and a positive integer `limit`.
* 
* In one operation, you can choose any two indices `i` and `j` and swap `nums[i]` and `nums[j]` if `|nums[i] - nums[j]| <= limit`.
* 
* Return the lexicographically smallest array that can be obtained by performing the operation any number of times.
* 
* An array `a` is lexicographically smaller than an array `b` if in the first position where `a` and `b` differ, array `a` has an element that is less than the corresponding element in `b`. For example, the array `[2,10,3]` is lexicographically smaller than the array `[10,2,3]` because they differ at index `0` and `2 < 10`.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    // Approach: Sorting + Grouping
    // Elements can be swapped if their absolute difference ≤ limit, forming transitive groups. 
    // 1. Sort a copy of the array and group elements based on the limit.
    // 2. Sort each group and map elements in the original array to the smallest available in their group.
    // Time Complexity: O(n log n) (sorting + traversal)
    // Space Complexity: O(n) (group mappings and storage)
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        // Step 1: Sort a copy of the original array to identify groups
        int n = nums.length;
        int[] numsSorted = nums.clone();
        Arrays.sort(numsSorted);

        // Step 2: Create mappings for groups and elements
        Map<Integer, Integer> numToGroup = new HashMap<>(); // Map number to group ID
        Map<Integer, List<Integer>> groupToList = new HashMap<>(); // Map group ID to list of elements
        int currGroup = 0;
        groupToList.put(currGroup, new ArrayList<>());
        groupToList.get(currGroup).add(numsSorted[0]);
        numToGroup.put(numsSorted[0], currGroup);

        // Step 3: Identify groups based on `limit`
        for (int i = 1; i < n; i++) {
            if (numsSorted[i] - numsSorted[i - 1] > limit) {
                // Start a new group
                currGroup++;
                groupToList.put(currGroup, new ArrayList<>());
            }
            groupToList.get(currGroup).add(numsSorted[i]);
            numToGroup.put(numsSorted[i], currGroup);
        }

        // Step 4: Sort each group (already sorted in numsSorted, but still store)
        for (int group : groupToList.keySet()) {
            groupToList.put(group, groupToList.get(group));
        }

        // Step 5: Build the result array using sorted groups
        Map<Integer, Integer> groupIndex = new HashMap<>(); // Track indices for each group
        for (int group : groupToList.keySet()) {
            groupIndex.put(group, 0);
        }

        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            int group = numToGroup.get(nums[i]);
            List<Integer> sortedGroup = groupToList.get(group);
            result[i] = sortedGroup.get(groupIndex.get(group));
            groupIndex.put(group, groupIndex.get(group) + 1);
        }

        return result;
    }

    public static void main(String[] args) {
        Solution solver = new Solution();
        int[] nums = {20, 40, 60, 80, 100, 34, 54, 74, 94, 114};
        int limit = 10;
        int[] result = solver.lexicographicallySmallestArray(nums, limit);
        System.out.println(Arrays.toString(result));
    }
}
