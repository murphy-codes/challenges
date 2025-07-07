// Source: https://leetcode.com/problems/finding-pairs-with-a-certain-sum/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-06
// At the time of submission:
//   Runtime 138 ms Beats 86.36%
//   Memory 73.49 MB Beats 84.96%

/****************************************
* 
* You are given two integer arrays `nums1` and `nums2`. You are tasked to
* _ implement a data structure that supports queries of two types:
* 1. Add a positive integer to an element of a given index in the array `nums2`.
* 2. Count the number of pairs `(i, j)` such that `nums1[i] + nums2[j]` equals a
* __ given value (`0 <= i < nums1.length` and `0 <= j < nums2.length`).
* Implement the `FindSumPairs` class:
* • `FindSumPairs(int[] nums1, int[] nums2)` Initializes the `FindSumPairs`
* __ object with two integer arrays `nums1` and `nums2`.
* • `void add(int index, int val)` Adds `val` to `nums2[index]`, i.e.,
* __ apply `nums2[index] += val`.
* • `int count(int tot)` Returns the number of pairs `(i, j)` such that
* __ `nums1[i] + nums2[j] == tot`.
*
* Example 1:
* Input
* ["FindSumPairs", "count", "add", "count", "count", "add", "add", "count"]
* [[[1, 1, 2, 2, 2, 3], [1, 4, 5, 2, 5, 4]], [7], [3, 2], [8], [4], [0, 1], [1, 1], [7]]
* Output
* [null, 8, null, 2, 1, null, null, 11]
* Explanation
* FindSumPairs findSumPairs = new FindSumPairs([1, 1, 2, 2, 2, 3], [1, 4, 5, 2, 5, 4]);
* findSumPairs.count(7);  // return 8; pairs (2,2), (3,2), (4,2), (2,4), (3,4), (4,4) make 2 + 5 and pairs (5,1), (5,5) make 3 + 4
* findSumPairs.add(3, 2); // now nums2 = [1,4,5,4,5,4]
* findSumPairs.count(8);  // return 2; pairs (5,2), (5,4) make 3 + 5
* findSumPairs.count(4);  // return 1; pair (5,0) makes 3 + 1
* findSumPairs.add(0, 1); // now nums2 = [2,4,5,4,5,4]
* findSumPairs.add(1, 1); // now nums2 = [2,5,5,4,5,4]
* findSumPairs.count(7);  // return 11; pairs (2,1), (2,2), (2,4), (3,1), (3,2), (3,4), (4,1), (4,2), (4,4) make 2 + 5 and pairs (5,3), (5,5) make 3 + 4
*
* Constraints:
* • 1 <= nums1.length <= 1000
* • 1 <= nums2.length <= 10^5
* • 1 <= nums1[i] <= 10^9
* • 1 <= nums2[i] <= 10^5
* • 0 <= index < nums2.length
* • 1 <= val <= 10^5
* • 1 <= tot <= 10^9
* • At most `1000` calls are made to `add` and `count` each.
* 
****************************************/

import java.util.HashMap;
import java.util.Map;

class FindSumPairs {
    // Build a frequency map for nums2 to allow fast lookup of complements.
    // When add() is called, update both nums2 and the frequency map.
    // In count(), for each num1 in nums1, check how many times (tot - num1)
    // appears in nums2 using the map. This is efficient due to nums1 being small.
    // Time Complexity: O(1) for add(), O(n1) for count() where n1 = nums1.length
    // Space Complexity: O(n2) where n2 = nums2.length, due to the frequency map.
    private int[] nums1;
    private int[] nums2;
    private Map<Integer, Integer> freq2;

    public FindSumPairs(int[] nums1, int[] nums2) {
        this.nums1 = nums1;
        this.nums2 = nums2;
        this.freq2 = new HashMap<>();

        // Build frequency map of nums2
        for (int num : nums2) {
            freq2.put(num, freq2.getOrDefault(num, 0) + 1);
        }
    }

    public void add(int index, int val) {
        int original = nums2[index];
        int updated = original + val;

        // Update frequency map
        freq2.put(original, freq2.get(original) - 1);
        if (freq2.get(original) == 0) {
            freq2.remove(original);
        }

        freq2.put(updated, freq2.getOrDefault(updated, 0) + 1);

        // Update nums2 array
        nums2[index] = updated;
    }

    public int count(int tot) {
        int count = 0;
        for (int num1 : nums1) {
            int complement = tot - num1;
            count += freq2.getOrDefault(complement, 0);
        }
        return count;
    }
}
