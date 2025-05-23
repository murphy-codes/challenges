// Source: https://leetcode.com/problems/build-array-from-permutation/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-05-05
// At the time of submission:
//   Runtime 0 ms Beats 100.00%
//   Memory 45.15 MB Beats 91.98%

/****************************************
* 
* Given a zero-based permutation `nums` (0-indexed), build an array
* _ `ans` of the same length where `ans[i] = nums[nums[i]]` for each
* _ `0 <= i < nums.length` and return it.
* A zero-based permutation `nums` is an array of distinct integers
* _ from `0` to `nums.length - 1` (inclusive).
*
* Example 1:
* Input: nums = [0,2,1,5,3,4]
* Output: [0,1,2,4,5,3]
* Explanation: The array ans is built as follows:
* ans = [nums[nums[0]], nums[nums[1]], nums[nums[2]], nums[nums[3]], nums[nums[4]], nums[nums[5]]]
* _    = [nums[0], nums[2], nums[1], nums[5], nums[3], nums[4]]
* _    = [0,1,2,4,5,3]
*
* Example 2:
* Input: nums = [5,0,1,2,3,4]
* Output: [4,5,0,1,2,3]
* Explanation: The array ans is built as follows:
* ans = [nums[nums[0]], nums[nums[1]], nums[nums[2]], nums[nums[3]], nums[nums[4]], nums[nums[5]]]
* _    = [nums[5], nums[0], nums[1], nums[2], nums[3], nums[4]]
* _    = [4,5,0,1,2,3]
*
* Constraints:
* • 1 <= nums.length <= 1000
* • 0 <= nums[i] < nums.length
* • The elements in `nums` are distinct.
*
* Follow-up: Can you solve it without using an extra space (i.e., O(1) memory)?
* 
****************************************/

class Solution {
    // This approach uses recursion to safely transform nums[i] = nums[nums[i]]
    // by delaying the write operation until after the recursive call. This
    // guarantees that all values nums[*] are accessed while still unmodified.
    // It avoids allocating a new array (in-place transformation), but recursion
    // adds O(n) call stack space.
    public int[] buildArray(int[] nums) {
        transformInPlace(nums,0);
        return nums;
    }
    // Recursively build the result array in-place using postorder traversal
    void transformInPlace(int[] nums, int index){
        if(index<nums.length){
            int currentValue = nums[index]; // Store the current value before the array gets modified
            int result = nums[currentValue]; // Lookup nums[nums[i]], before recursion mutates
            transformInPlace(nums,index+1); // Recursively transform the next index first
            nums[index]=result;
        }
    }
}