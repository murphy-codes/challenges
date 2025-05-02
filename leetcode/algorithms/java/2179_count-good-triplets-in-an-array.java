// Source: https://leetcode.com/problems/count-good-triplets-in-an-array/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-04-15
// At the time of submission:
//   Runtime 24 ms Beats 40.43%
//   Memory 57.03 MB Beats 87.23%

/****************************************
* 
* You are given two 0-indexed arrays `nums1` and `nums2` of length `n`,
* _ both of which are permutations of `[0, 1, ..., n - 1]`.
* A good triplet is a set of `3` distinct values which are present in
* _ increasing order by position both in `nums1` and `nums2`. In other words,
* _ if we consider `pos1_v` as the index of the value `v` in `nums1` and `pos2v`
* _ as the index of the value `v` in `nums2`, then a good triplet will be a set
* _ `(x, y, z)` where `0 <= x, y, z <= n - 1`, such that `pos1_x < pos1_y < pos1_z`
* _ and `pos2_x < pos2_y < pos2_z`.
* Return the total number of good triplets.
*
* Example 1:
* Input: nums1 = [2,0,1,3], nums2 = [0,1,2,3]
* Output: 1
* Explanation:
* There are 4 triplets (x,y,z) such that pos1x < pos1y < pos1z. They are (2,0,1), (2,0,3), (2,1,3), and (0,1,3).
* Out of those triplets, only the triplet (0,1,3) satisfies pos2x < pos2y < pos2z. Hence, there is only 1 good triplet.
*
* Example 2:
* Input: nums1 = [4,0,1,3,2], nums2 = [4,1,0,2,3]
* Output: 4
* Explanation: The 4 good triplets are (4,0,3), (4,0,2), (4,1,3), and (4,1,2).
*
* Constraints:
* • n == nums1.length == nums2.length
* • 3 <= n <= 10^5
* • 0 <= nums1[i], nums2[i] <= n - 1
* • `nums1` and `nums2` are permutations of `[0, 1, ..., n - 1]`.
* 
****************************************/

import java.util.HashMap;

class Solution {
    // Map nums2 values to their indices in nums1 for position comparison.
    // Count triplets using BIT where mapped[i] < mapped[j] < mapped[k].
    // For each middle index j, multiply count of smaller left and larger right.
    // Uses Fenwick Tree for O(log n) prefix sums, giving total O(n log n) time.
    // Time: O(n log n), Space: O(n)
    public long goodTriplets(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] indexInNums1 = new int[n];

        // Map each value to its index in nums1
        for (int i = 0; i < n; i++) {
            indexInNums1[nums1[i]] = i;
        }

        // Map nums2 into positions from nums1
        int[] mapped = new int[n];
        for (int i = 0; i < n; i++) {
            mapped[i] = indexInNums1[nums2[i]];
        }

        // BIT to count prefix values
        FenwickTree bitLeft = new FenwickTree(n);
        FenwickTree bitRight = new FenwickTree(n);

        // Build BIT for right side first
        for (int val : mapped) {
            bitRight.update(val, 1);
        }

        long result = 0;

        for (int i = 0; i < n; i++) {
            int val = mapped[i];
            bitRight.update(val, -1); // current index is no longer in the right

            long leftCount = bitLeft.query(val - 1);         // elements < val on left
            long rightCount = bitRight.queryRange(val + 1, n - 1); // elements > val on right

            result += leftCount * rightCount;

            bitLeft.update(val, 1); // add current val to the left
        }

        return result;
    }

    // Binary Indexed Tree (Fenwick Tree) to support prefix sums
    static class FenwickTree {
        int[] tree;
        int n;

        public FenwickTree(int size) {
            this.n = size + 2;
            this.tree = new int[n];
        }

        public void update(int index, int delta) {
            index++;
            while (index < n) {
                tree[index] += delta;
                index += index & -index;
            }
        }

        public int query(int index) {
            index++;
            int result = 0;
            while (index > 0) {
                result += tree[index];
                index -= index & -index;
            }
            return result;
        }

        public int queryRange(int left, int right) {
            return query(right) - query(left - 1);
        }
    }
}
