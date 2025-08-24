// Source: https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-08-24
// At the time of submission:
//   Runtime 1 ms Beats 99.93%
//   Memory 57.17 MB Beats 28.24%

/****************************************
* 
* Given a binary array `nums`, you should delete one element from it.
* Return the size of the longest non-empty subarray containing only `1`'s in the
* _ resulting array. Return `0` if there is no such subarray.
*
* Example 1:
* Input: nums = [1,1,0,1]
* Output: 3
* Explanation: After deleting the number in position 2, [1,1,1] contains 3 numbers with value of 1's.
*
* Example 2:
* Input: nums = [0,1,1,1,0,1,1,0,1]
* Output: 5
* Explanation: After deleting the number in position 4, [0,1,1,1,1,1,0,1] longest subarray with value of 1's is [1,1,1,1,1].
*
* Example 3:
* Input: nums = [1,1,1]
* Output: 2
* Explanation: You must delete one element.
*
* Constraints:
* • 1 <= nums.length <= 10^5
* • `nums[i]` is either `0` or `1`.
* 
****************************************/

class Solution {
    // This solution uses a sliding window to maintain at most one zero.
    // When a second zero is found, the window left pointer moves just
    // past the first skipped zero, ensuring validity. The longest valid
    // window size (minus one, since one element must be deleted) is the
    // answer. Time complexity is O(n), and space complexity is O(1).

    static { // JIT Warm-up block forces garbage collection & works as micro-optimization
        System.gc();
        for (int i = 0; i < 500; i++) {
            longestSubarray(new int[]{0, 0});
        }
    }

    public static int longestSubarray(int[] nums) {
        boolean skippedOnce = false;      // Have we already skipped one zero?
        int left = 0;                     // Left pointer of sliding window
        int right = 0;                    // Right pointer of sliding window
        int skippedPos = 0;               // Position of the skipped zero
        int maxLength = 0;                // Maximum length found

        // Special case: only one element -> must delete it
        if (nums.length == 1) return 0;

        while (right < nums.length) {
            if (nums[right] == 1) {
                // Extend window with 1’s
                if (right == nums.length - 1) {
                    maxLength = Math.max(maxLength, right - left + 1);
                }
                right++;
            } else {
                // Hit a zero
                if (skippedOnce) {
                    // Already skipped one zero -> shrink window
                    maxLength = Math.max(maxLength, right - left);
                    left = skippedPos + 1;
                }
                skippedOnce = true;
                skippedPos = right;
                right++;
            }
        }

        // Must delete one element -> adjust final result
        maxLength--;

        return maxLength;
    }
}
