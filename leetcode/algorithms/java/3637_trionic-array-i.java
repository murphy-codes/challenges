// Source: https://leetcode.com/problems/trionic-array-i/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-02
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 44.60 MB Beats 81.51%

/****************************************
* 
* You are given an integer array `nums` of length `n`.
* An array is trionic if there exist indices `0 < p < q < n − 1` such that:
* • `nums[0...p]` is strictly increasing,
* • `nums[p...q]` is strictly decreasing,
* • `nums[q...n − 1]` is strictly increasing.
* Return `true` if `nums` is trionic, otherwise return `false`.
*
* Example 1:
* Input: nums = [1,3,5,4,2,6]
* Output: true
* Explanation:
* Pick p = 2, q = 4:
* • nums[0...2] = [1, 3, 5] is strictly increasing (1 < 3 < 5).
* • nums[2...4] = [5, 4, 2] is strictly decreasing (5 > 4 > 2).
* • nums[4...5] = [2, 6] is strictly increasing (2 < 6).
*
* Example 2:
* Input: nums = [2,1,3]
* Output: false
* Explanation:
* There is no way to pick p and q to form the required three segments.
*
* Constraints:
* • `3 <= n <= 100`
* • `-1000 <= nums[i] <= 1000`
* 
****************************************/

class Solution {
    // Scan the array to find a strictly increasing prefix, followed by a
    // strictly decreasing middle, and finally a strictly increasing suffix.
    // If any segment is missing or violates strict ordering, return false.
    // This greedy split is valid because a trionic array has a unique shape.
    // Time: O(n), Space: O(1).
    public boolean isTrionic(int[] nums) {
        int n = nums.length;
        
        // Find p: end of the first strictly increasing segment
        int p = 0;
        while (p < n - 1 && nums[p] < nums[p + 1]) {
            p++;
        }
        
        // Must have at least one increase
        if (p == 0) {
            return false;
        }
        
        // Find q: end of the strictly decreasing segment
        int q = p;
        while (q < n - 1 && nums[q] > nums[q + 1]) {
            q++;
        }
        
        // Must have at least one decrease
        if (q == p) {
            return false;
        }
        
        // Must leave room for a final increasing segment
        if (q == n - 1) {
            return false;
        }
        
        // Verify the remaining elements are strictly increasing
        for (int i = q; i < n - 1; i++) {
            if (nums[i] >= nums[i + 1]) {
                return false;
            }
        }
        
        return true;
    }
}