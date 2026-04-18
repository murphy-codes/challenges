// Source: https://leetcode.com/problems/minimum-absolute-distance-between-mirror-pairs/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-04-17
// At the time of submission:
//   Runtime 37 ms Beats 100.00%
//   Memory 102.49 MB Beats 21.61%

/****************************************
* 
* You are given an integer array `nums`.
* A mirror pair is a pair of indices `(i, j)` such that:
* • `0 <= i < j < nums.length`, and
* • `reverse(nums[i]) == nums[j]`, where `reverse(x)` denotes the integer
* __ formed by reversing the digits of `x`. Leading zeros are omitted after
* __ reversing, for example `reverse(120) = 21`.
* Return the minimum absolute distance between the indices of any mirror pair.
* _ The absolute distance between indices `i` and `j` is `abs(i - j)`.
* If no mirror pair exists, return `-1`.
*
* Example 1:
* Input: nums = [12,21,45,33,54]
* Output: 1
* Explanation:
* The mirror pairs are:
* • (0, 1) since `reverse(nums[0]) = reverse(12) = 21 = nums[1]`, giving an absolute distance `abs(0 - 1) = 1`.
* • (2, 4) since `reverse(nums[2]) = reverse(45) = 54 = nums[4]`, giving an absolute distance `abs(2 - 4) = 2`.
* The minimum absolute distance among all pairs is 1.
*
* Example 2:
* Input: nums = [120,21]
* Output: 1
* Explanation:
* There is only one mirror pair (0, 1) since `reverse(nums[0]) = reverse(120) = 21 = nums[1]`.
* The minimum absolute distance is 1.
*
* Example 3:
* Input: nums = [21,120]
* Output: -1
* Explanation:
* There are no mirror pairs in the array.
*
* Constraints:
* • `1 <= nums.length <= 10^5
* • `1 <= nums[i] <= 10^9
* 
****************************************/

/*
 We are looking for nums[i] == reverse(nums[j]) 
 and also allows j - i Minimum

 hashmap What is stored inside?
 key   = reverse(nums[i])
 value = i the maximum subscript of
 It means: 
  At what position does the reverse of a number to the left appear?
*/ 

class Solution {
    public int minMirrorPairDistance(int[] nums) {
        int n = nums.length; 
        int ans = n; 

        // lastIndex：key = reverse(A left-side element nums[i])，value = The subscript *i* (taking the latest/maximum subscript)
        // Pre-allocate capacity `n` with a load factor of 1 to minimize resizing and rehashing (a performance optimization, not strictly required).
        Map<Integer, Integer> lastIndex = new HashMap<>(n,1.0f);

        for(int j =0; j < n ; j++){
            // Query: Is there any index `i` to the left such that `reverse(nums[i]) == nums[j]`?
            // Since we stored `reverse(nums[i])` as the key in `lastIndex`, we can simply use `x` to perform the lookup.
            int x = nums[j];
            Integer i = lastIndex.get(x); 

            if(i!= null){  // If a mirror pair is found
                int dist = j-i;  // Calculate distance
                ans = Math.min(ans, dist);  // Update the answer with a smaller distance.
            }


             // ====== Below: Update the hash table (contributing the current element to "future right endpoints") ======

            int t = x;                            // Store a copy of `x` in `t` (since the variable will be repeatedly modified by dividing by 10 later on).
            int rev = 0;                          // rev is used to calculate reverse(x).



             // Calculate reverse(t): for example t=123 -> rev=321
            while (t > 0) {                       // As long as t still contains digits
                int digit = t % 10;               // Extract the last digit of t.
                rev = rev * 10 + digit;           // Append `digit` to the end of `rev`.
                t = t / 10;                       // Remove the last digit of t.
            }


             // Store `reverse(nums[j])` as the key, and record the current index `j`.
             // This overwrites any previous value: ensuring that the stored value is the "largest/most recent index," thereby making it possible for `j - i` to be smaller.
            lastIndex.put(rev, j);                // lastIndex[rev] = j

        }

        // If `ans` remains `n`, it indicates that it was never updated (meaning no mirror pairs were found); return -1.
        // Otherwise, return the minimum distance `ans`.
        if (ans < n) {                            // Mirror pair found
            return ans;                           // Returns the minimum mirror distance.
        } else {                                  // No mirror pair found
            return -1;                            // Return -1 as per the problem statement.
        }
        
    }
}