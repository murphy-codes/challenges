// Source: https://leetcode.com/problems/tuple-with-same-product/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-02-06

/****************************************
* 
* Given an array `nums` of distinct positive integers, return the number of tuples `(a, b, c, d)` such that `a * b = c * d` where `a`, `b`, `c`, and `d` are elements of `nums`, and `a != b != c != d`.
* 
* Example 1:
* Input: nums = [2,3,4,6]
* Output: 8
* Explanation: There are 8 valid tuples:
* (2,6,3,4) , (2,6,4,3) , (6,2,3,4) , (6,2,4,3)
* (3,4,2,6) , (4,3,2,6) , (3,4,6,2) , (4,3,6,2)
* 
* Example 2:
* Input: nums = [1,2,4,5,10]
* Output: 16
* Explanation: There are 16 valid tuples:
* (1,10,2,5) , (1,10,5,2) , (10,1,2,5) , (10,1,5,2)
* (2,5,1,10) , (2,5,10,1) , (5,2,1,10) , (5,2,10,1)
* (2,10,4,5) , (2,10,5,4) , (10,2,4,5) , (10,2,5,4)
* (4,5,2,10) , (4,5,10,2) , (5,4,2,10) , (5,4,10,2)
* 
* Constraints:
* • 1 <= nums.length <= 1000
* • 1 <= nums[i] <= 10^4
* • All elements in `nums` are distinct.
* 
****************************************/

import java.util.HashMap;

class Solution {
    // This solution counts the occurrences of each product (a * b) using a HashMap.
    // If a product appears k times, there are kC2 valid tuple pairs. Since each
    // tuple (a, b, c, d) can be arranged in 8 ways, we multiply the final count
    // by 8. The time complexity is O(n²) due to the nested loop, and space is O(n²)
    // in the worst case (if all products are unique and stored in the map).
    public int tupleSameProduct(int[] nums) {
        HashMap<Integer, Integer> productCount = new HashMap<>();
        int count = 0;

        // Iterate through all pairs (a, b) in nums
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int product = nums[i] * nums[j];

                // If product already exists in map, add the valid tuple count
                count += productCount.getOrDefault(product, 0);

                // Update product count
                productCount.put(product, productCount.getOrDefault(product, 0) + 1);
            }
        }

        // Each valid (a, b, c, d) tuple has 8 permutations
        return count * 8;
    }
}

