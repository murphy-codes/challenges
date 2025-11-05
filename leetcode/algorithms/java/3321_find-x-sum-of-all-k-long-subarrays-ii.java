// Source: https://leetcode.com/problems/find-x-sum-of-all-k-long-subarrays-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-04
// At the time of submission:
//   Runtime 442 ms Beats 87.50%
//   Memory 179.78 MB Beats 6.25%

/****************************************
* 
* You are given an array `nums` of `n` integers and two integers `k` and `x`.
* The x-sum of an array is calculated by the following procedure:
* • Count the occurrences of all elements in the array.
* • Keep only the occurrences of the top `x` most frequent elements. If two
* __ elements have the same number of occurrences, the element with the bigger
* __ value is considered more frequent.
* • Calculate the sum of the resulting array.
* Note that if an array has less than `x` distinct elements, its x-sum is the
* _ sum of the array.
* Return an integer array `answer` of length `n - k + 1` where `answer[i]`
* _ is the x-sum of the subarray `nums[i..i + k - 1]`.
*
* Example 1:
* Input: nums = [1,1,2,2,3,4,2,3], k = 6, x = 2
* Output: [6,10,12]
* Explanation:
* For subarray [1, 1, 2, 2, 3, 4], only elements 1 and 2 will be kept in the resulting array. Hence, answer[0] = 1 + 1 + 2 + 2.
* For subarray [1, 2, 2, 3, 4, 2], only elements 2 and 4 will be kept in the resulting array. Hence, answer[1] = 2 + 2 + 2 + 4. Note that 4 is kept in the array since it is bigger than 3 and 1 which occur the same number of times.
* For subarray [2, 2, 3, 4, 2, 3], only elements 2 and 3 are kept in the resulting array. Hence, answer[2] = 2 + 2 + 2 + 3 + 3.
*
* Example 2:
* Input: nums = [3,8,7,8,7,5], k = 2, x = 2
* Output: [11,15,15,15,12]
* Explanation:
* Since k == x, answer[i] is equal to the sum of the subarray nums[i..i + k - 1].
*
* Constraints:
* • `nums.length == n`
* • `1 <= n <= 10^5`
* • `1 <= nums[i] <= 10^9`
* • `1 <= x <= k <= nums.length`
* 
****************************************/

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

class Solution {
    // Uses a sliding window with frequency counting to track the top-x
    // most frequent (and largest on tie) elements across subarrays of size k.
    // Two ordered sets maintain top-x and remaining elements; balancing
    // occurs as elements enter or leave the window. Time O(n log x),
    // space O(n) for frequency maps and ordered sets.
    
    // Pair ordered by (freq, num) ascending so largest is last()
    private static class Pair implements Comparable<Pair> {
        final int freq;
        final int num;
        Pair(int f, int v) { freq = f; num = v; }
        public int compareTo(Pair o) {
            if (freq != o.freq) return Integer.compare(freq, o.freq);
            return Integer.compare(num, o.num);
        }
    }

    // Mutable fields used by helper methods
    private Map<Integer, Integer> freq;
    private NavigableSet<Pair> top;  // holds up to x best elements
    private NavigableSet<Pair> rest; // holds the rest
    private long topSum;             // sum of freq * num over elements in 'top'
    private int xTarget;

    public long[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        long[] ans = new long[n - k + 1];

        freq = new HashMap<>();
        top = new TreeSet<>();
        rest = new TreeSet<>();
        topSum = 0;
        xTarget = x;

        // initialize first window
        for (int i = 0; i < k; i++) {
            addNum(nums[i], +1);
        }
        balanceSets();
        ans[0] = topSum;

        for (int i = k; i < n; i++) {
            addNum(nums[i - k], -1); // remove outgoing
            addNum(nums[i], +1);     // add incoming
            balanceSets();
            ans[i - k + 1] = topSum;
        }
        return ans;
    }

    // Add or remove a value from freq and appropriate set
    private void addNum(int num, int delta) {
        int old = freq.getOrDefault(num, 0);
        if (old > 0) {
            // remove old Pair from whichever set contains it
            Pair oldPair = new Pair(old, num);
            if (top.remove(oldPair)) {
                topSum -= (long) old * num;
            } else {
                rest.remove(oldPair);
            }
        }

        int nw = old + delta;
        if (nw == 0) {
            freq.remove(num);
            return;
        }
        freq.put(num, nw);
        // new pair goes to rest initially; balancing will move if needed
        Pair newPair = new Pair(nw, num);
        rest.add(newPair);
    }

    // Ensure top has up to xTarget best elements (largest by freq then num).
    private void balanceSets() {
        // Move from rest -> top until top.size == xTarget or rest empty
        while (top.size() < xTarget && !rest.isEmpty()) {
            Pair p = rest.pollLast();
            top.add(p);
            topSum += (long) p.freq * p.num;
        }

        // If top too large, move smallest from top -> rest
        while (top.size() > xTarget) {
            Pair p = top.pollFirst();
            topSum -= (long) p.freq * p.num;
            rest.add(p);
        }

        // Now ensure every element in top is >= every element in rest (by compare)
        // If not, swap extremes until order holds
        while (!rest.isEmpty() && !top.isEmpty() && rest.last().compareTo(top.first()) > 0) {
            Pair smallTop = top.pollFirst();
            Pair bigRest = rest.pollLast();
            topSum -= (long) smallTop.freq * smallTop.num;
            topSum += (long) bigRest.freq * bigRest.num;
            top.add(bigRest);
            rest.add(smallTop);
        }
    }
}
