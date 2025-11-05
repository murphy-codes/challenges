// Source: https://leetcode.com/problems/find-x-sum-of-all-k-long-subarrays-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-11-04
// At the time of submission:
//   Runtime 399 ms Beats 96.88%
//   Memory 191.45 MB Beats 6.25%

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

class Solution {
    // This solution maintains two balanced TreeSets: topSet (top x freq,num pairs)
    // and restSet (the remaining). Each slide updates element frequencies, rebalances
    // between sets, and keeps a running sum of freq*num for the top x elements.
    // All operations (add/remove) take O(log D), where D is the distinct count.
    // Overall complexity: O(n log D) time and O(D) space for each window pass.

    private int x; // number of top (freq,num) pairs to include in sum
    private long sum = 0L; // running sum of top-x (freq * num)
    private Map<Integer, Integer> freq; // frequency map per number

    // TreeSets ordered by (freq ascending, num ascending)
    // topSet = top x elements; restSet = remaining elements
    private final TreeSet<int[]> topSet = new TreeSet<>(
        (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]
    );
    private final TreeSet<int[]> restSet = new TreeSet<>(
        (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]
    );

    public long[] findXSum(int[] nums, int k, int x) {
        int n = nums.length;
        this.x = x;
        freq = new HashMap<>(n);
        long[] ans = new long[n - k + 1];

        for (int i = 0; i < n; i++) {
            // Add incoming number
            int count = freq.merge(nums[i], 1, Integer::sum);
            remove(count - 1, nums[i]); // remove old freq pair
            add(count, nums[i]);        // add new freq pair

            // Once window is full, record result and slide
            if (i + 1 >= k) {
                ans[i - k + 1] = sum;
                count = freq.merge(nums[i - k + 1], -1, Integer::sum);
                remove(count + 1, nums[i - k + 1]); // remove outgoing old freq
                add(count, nums[i - k + 1]);        // add updated freq (may be 0)
            }
        }
        return ans;
    }

    // Add or rebalance an element with given count and value
    private void add(int count, int num) {
        if (count == 0) return;
        int[] val = new int[]{count, num};

        // If topSet not yet filled, add directly
        if (topSet.size() < x) {
            topSet.add(val);
            sum += (long) count * num;
            return;
        }

        // Otherwise, compare with smallest in topSet
        int[] smallestTop = topSet.first();
        if (smallestTop[0] > count || (smallestTop[0] == count && smallestTop[1] >= num)) {
            // new element not good enough, goes to restSet
            restSet.add(val);
            return;
        }

        // new element belongs in topSet; swap out smallestTop
        sum += (long) count * num - (long) smallestTop[0] * smallestTop[1];
        restSet.add(topSet.pollFirst());
        topSet.add(val);
    }

    // Remove or rebalance an element with given count and value
    private void remove(int count, int num) {
        if (count == 0) return;
        int[] val = new int[]{count, num};

        // If present in restSet, just remove it
        if (restSet.contains(val)) {
            restSet.remove(val);
            return;
        }

        // Otherwise, it was in topSet
        topSet.remove(val);
        sum -= (long) count * num;

        // Refill topSet if possible
        if (restSet.isEmpty()) return;
        int[] biggestRest = restSet.pollLast();
        sum += (long) biggestRest[0] * biggestRest[1];
        topSet.add(biggestRest);
    }
}
