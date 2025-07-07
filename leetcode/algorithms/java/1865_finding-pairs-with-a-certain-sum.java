// Source: https://leetcode.com/problems/finding-pairs-with-a-certain-sum/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-06
// At the time of submission:
//   Runtime 126 ms Beats 97.20%
//   Memory 78.18 MB Beats 32.17%

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

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class FindSumPairs {
// This solution precomputes frequency maps for both nums1 and nums2.
// Since nums1 is small (≤ 1000), we cache its frequencies and sort
// its unique keys to optimize the count() operation. We update nums2
// and its frequency map on every add(). The count() operation iterates
// nums1 keys and checks the matching pair count in nums2.
// Time Complexity:
// - Constructor: O(n1 + n2 + n1*log(n1)) for maps + sorting
// - add(): O(1)
// - count(): O(n1) where n1 = nums1.length
// Space Complexity: O(n1 + n2) for frequency maps
    int[] nums1, nums2;

    // Frequency maps
    Map<Integer, Integer> freqNums1;
    Map<Integer, Integer> freqNums2;

    // Sorted unique keys of nums1
    List<Integer> sortedNums1Keys;

    public FindSumPairs(int[] nums1, int[] nums2) {
        this.nums1 = nums1;
        this.nums2 = nums2;

        freqNums1 = new HashMap<>();
        freqNums2 = new HashMap<>();

        // Build frequency map for nums1
        for (int num : nums1) {
            freqNums1.put(num, freqNums1.getOrDefault(num, 0) + 1);
        }

        // Store and sort unique keys of nums1 for optimized lookup
        sortedNums1Keys = new ArrayList<>(freqNums1.keySet());
        Collections.sort(sortedNums1Keys);

        // Build frequency map for nums2
        for (int num : nums2) {
            freqNums2.put(num, freqNums2.getOrDefault(num, 0) + 1);
        }
    }

    public void add(int index, int val) {
        int original = nums2[index];
        int updated = original + val;

        // Update frequency map: decrement old value
        int oldCount = freqNums2.get(original);
        if (oldCount == 1) {
            freqNums2.remove(original);
        } else {
            freqNums2.put(original, oldCount - 1);
        }

        // Increment frequency for new value
        freqNums2.put(updated, freqNums2.getOrDefault(updated, 0) + 1);

        // Update nums2 in-place
        nums2[index] = updated;
    }

    public int count(int targetSum) {
        int result = 0;

        for (int a : sortedNums1Keys) {
            if (a > targetSum) break; // Early exit optimization
            int b = targetSum - a;
            Integer freqB = freqNums2.get(b);

            if (freqB != null) {
                result += freqNums1.get(a) * freqB;
            }
        }

        return result;
    }
}
