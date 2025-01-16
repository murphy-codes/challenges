// Source: https://leetcode.com/problems/bitwise-xor-of-all-pairings/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-01-15

/****************************************
* 
* You are given two 0-indexed arrays, `nums1` and `nums2`, consisting of non-negative integers. 
* There exists another array, `nums3`, which contains the bitwise XOR of all pairings of 
* integers between nums1 and nums2 (every integer in nums1 is paired with every integer 
* in nums2 exactly once).
* 
* Return the bitwise XOR of all integers in nums3.
* 
* Example 1:
* Input: nums1 = [2,1,3], nums2 = [10,2,5,0]
* Output: 13
* Explanation:
* A possible nums3 array is [8,0,7,2,11,3,4,1,9,1,6,3].
* The bitwise XOR of all these numbers is 13, so we return 13.
* 
* Example 2:
* Input: nums1 = [1,2], nums2 = [3,4]
* Output: 0
* Explanation:
* All possible pairs of bitwise XORs are nums1[0] ^ nums2[0], nums1[0] ^ nums2[1], nums1[1] ^ nums2[0],
* and nums1[1] ^ nums2[1].
* Thus, one possible nums3 array is [2,5,1,6].
* 2 ^ 5 ^ 1 ^ 6 = 0, so we return 0.
* 
* Constraints:
* • 1 <= nums1.length, nums2.length <= 10^5
* • 0 <= nums1[i], nums2[j] <= 10^9
* 
****************************************/

class Solution {
    // - Key observation: Pairwise XOR can be reduced by using properties of XOR.
    // - Each element in nums1 is XORed with all nums2 elements. If nums2.length is odd,
    //   nums1's XOR contributes to the result. Similarly for nums2 if nums1.length is odd.
    // - Compute the XOR of nums1 and nums2 once each, then conditionally combine.
    // Time: O(n + m), Space: O(1).
    public int xorAllNums(int[] nums1, int[] nums2) {
        int xor1 = 0, xor2 = 0;
        
        // Compute XOR of all elements in nums1 and nums2
        for (int num : nums1) xor1 ^= num;
        for (int num : nums2) xor2 ^= num;
        
        // Result depends on the lengths of nums1 and nums2
        int result = 0;
        if (nums2.length % 2 != 0) result ^= xor1; // nums1 contributes
        if (nums1.length % 2 != 0) result ^= xor2; // nums2 contributes
        
        return result;
    }
}
