// Source: https://leetcode.com/problems/longest-balanced-subarray-ii/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-02-10
// At the time of submission:
//   Runtime 12 ms Beats 98.53%
//   Memory 129.52 MB Beats 17.65%

/****************************************
* 
* You are given an integer array `nums`.
* A subarray is called balanced if the number of distinct even numbers in the
* _ subarray is equal to the number of distinct odd numbers.
* Return the length of the longest balanced subarray.
*
* Example 1:
* Input: nums = [2,5,4,3]
* Output: 4
* Explanation:
* The longest balanced subarray is `[2, 5, 4, 3]`.
* It has 2 distinct even numbers `[2, 4]` and 2 distinct odd numbers `[5, 3]`.
* _ Thus, the answer is 4.
*
* Example 2:
* Input: nums = [3,2,2,5,4]
* Output: 5
* Explanation:
* The longest balanced subarray is `[3, 2, 2, 5, 4]`.
* It has 2 distinct even numbers `[2, 4]` and 2 distinct odd numbers `[3, 5]`.
* _ Thus, the answer is 5.
*
* Example 3:
* Input: nums = [1,2,3,2]
* Output: 3
* Explanation:
* The longest balanced subarray is `[2, 3, 2]`.
* It has 1 distinct even number `[2]` and 1 distinct odd number `[3]`.
* _ Thus, the answer is 3.
*
* Constraints:
* • `1 <= nums.length <= 15^5`
* • `1 <= nums[i] <= 10^5`
* 
****************************************/

// import java.util.HashMap;
// import java.util.Map;
// import java.util.Optional;
// import java.util.concurrent.CompletableFuture;
// import java.util.function.BiFunction;
// import java.util.stream.IntStream;

class Solution {
    // This solution encodes a segment tree with lazy propagation entirely inside
    // lambda expressions and arrays to minimize overhead. It tracks a prefix
    // balance where new distinct evens add +1 and odds add -1, and finds the
    // longest subarray whose balance returns to zero. Heavy inlining and functional
    // abuse reduce constant factors. Time: O(n log n), Space: O(n).
    public int longestBalanced(int[] nums) {
        return CompletableFuture.supplyAsync(() -> Optional.of(new Object[]{new int[3][4 * nums.length], new HashMap<Integer, Integer>(), null}) .map(s -> Optional.of((BiFunction<Object, int[], Integer>) (f, a) -> a[0] == 0 ? (((int[][])s[0])[2][a[1]] == 0 ? 0 : (((int[][])s[0])[0][a[1]] += ((int[][])s[0])[2][a[1]]) * 0 + (((int[][])s[0])[1][a[1]] += ((int[][])s[0])[2][a[1]]) * 0 + (a[2] == a[3] ? 0 : (((int[][])s[0])[2][2 * a[1]] += ((int[][])s[0])[2][a[1]]) * 0 + (((int[][])s[0])[2][2 * a[1] + 1] += ((int[][])s[0])[2][a[1]]) * 0) + (((int[][])s[0])[2][a[1]] = 0) * 0) : a[0] == 1 ? ((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{0, a[1], a[2], a[3]}) * 0 + (a[2] > a[5] || a[3] < a[4] ? 0 : a[4] <= a[2] && a[3] <= a[5] ? (((int[][])s[0])[2][a[1]] += a[6]) * 0 + ((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{0, a[1], a[2], a[3]}) : ((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{1, 2 * a[1], a[2], (a[2] + a[3]) / 2, a[4], a[5], a[6]}) * 0 + ((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{1, 2 * a[1] + 1, (a[2] + a[3]) / 2 + 1, a[3], a[4], a[5], a[6]}) * 0 + (((int[][])s[0])[0][a[1]] = Math.min(((int[][])s[0])[0][2 * a[1]], ((int[][])s[0])[0][2 * a[1] + 1])) * 0 + (((int[][])s[0])[1][a[1]] = Math.max(((int[][])s[0])[1][2 * a[1]], ((int[][])s[0])[1][2 * a[1] + 1])) * 0) : ((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{0, a[1], a[2], a[3]}) * 0 + (((int[][])s[0])[0][a[1]] > 0 || ((int[][])s[0])[1][a[1]] < 0 ? -1 : a[2] == a[3] ? (((int[][])s[0])[0][a[1]] == 0 ? a[2] : -1) : Optional.of(((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{2, 2 * a[1], a[2], (a[2] + a[3]) / 2})).map(r -> r != -1 ? r : ((BiFunction<Object, int[], Integer>)f).apply(f, new int[]{2, 2 * a[1] + 1, (a[2] + a[3]) / 2 + 1, a[3]})).get())) .map(func -> ((s[2] = func) == null ? s : s)) .map(state -> IntStream.range(0, nums.length).boxed().reduce(0, (max, r) -> Optional.of(nums[r] % 2 == 0 ? 1 : -1).map(val -> (state[1] != null && ((Map)state[1]).containsKey(nums[r]) ? ((BiFunction<Object, int[], Integer>)state[2]).apply(state[2], new int[]{1, 1, 0, nums.length - 1, 0, (int)((Map)state[1]).get(nums[r]), -val}) : 0) * 0 + ((BiFunction<Object, int[], Integer>)state[2]).apply(state[2], new int[]{1, 1, 0, nums.length - 1, 0, r, val}) * 0 + (((Map)state[1]).put(nums[r], r) == null ? 0 : 0) * 0 + Optional.of(((BiFunction<Object, int[], Integer>)state[2]).apply(state[2], new int[]{2, 1, 0, nums.length - 1})).filter(l -> l != -1 && l <= r).map(l -> Math.max(max, r - l + 1)).orElse(max)).get(), (a, b) -> b)) .get()).orElse(0)).join();
    }
}



// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.HashSet;
// import java.util.Map;
// import java.util.Set;

// class Solution {
//     // We convert the problem into a prefix balance array where each new distinct
//     // even adds +1 and each new distinct odd adds -1. A subarray is balanced
//     // when the balance difference is zero. A segment tree with lazy propagation
//     // supports range updates as elements expire and queries for the furthest
//     // index with balance zero, yielding an O(n log n) solution.
//     // Time: O(n log n), Space: O(n)

//     // Segment tree supporting range add + querying last index with value == 0
//     private static class SegmentTree {
//         int[] minVal;
//         int[] maxVal;
//         int[] lazy;
//         int size;

//         SegmentTree(int size) {
//             this.size = size;
//             minVal = new int[4 * size];
//             maxVal = new int[4 * size];
//             lazy   = new int[4 * size];
//         }

//         void build(int[] data, int node, int left, int right) {
//             if (left == right) {
//                 minVal[node] = maxVal[node] = data[left];
//                 return;
//             }
//             int mid = (left + right) / 2;
//             build(data, node * 2, left, mid);
//             build(data, node * 2 + 1, mid + 1, right);
//             minVal[node] = Math.min(minVal[node * 2], minVal[node * 2 + 1]);
//             maxVal[node] = Math.max(maxVal[node * 2], maxVal[node * 2 + 1]);
//         }

//         void push(int node) {
//             if (lazy[node] != 0) {
//                 int delta = lazy[node];
//                 apply(node * 2, delta);
//                 apply(node * 2 + 1, delta);
//                 lazy[node] = 0;
//             }
//         }

//         void apply(int node, int delta) {
//             minVal[node] += delta;
//             maxVal[node] += delta;
//             lazy[node] += delta;
//         }

//         void update(int node, int left, int right, int ql, int qr, int delta) {
//             if (ql > right || qr < left) return;
//             if (ql <= left && right <= qr) {
//                 apply(node, delta);
//                 return;
//             }
//             push(node);
//             int mid = (left + right) / 2;
//             update(node * 2, left, mid, ql, qr, delta);
//             update(node * 2 + 1, mid + 1, right, ql, qr, delta);
//             minVal[node] = Math.min(minVal[node * 2], minVal[node * 2 + 1]);
//             maxVal[node] = Math.max(maxVal[node * 2], maxVal[node * 2 + 1]);
//         }

//         // Find the largest index >= minIndex where value == 0
//         int findLastZero(int node, int left, int right, int minIndex) {
//             if (right < minIndex) return -1;
//             if (minVal[node] > 0 || maxVal[node] < 0) return -1;

//             if (left == right) return left;

//             push(node);
//             int mid = (left + right) / 2;

//             int res = findLastZero(node * 2 + 1, mid + 1, right, minIndex);
//             if (res != -1) return res;

//             return findLastZero(node * 2, left, mid, minIndex);
//         }
//     }

//     public int longestBalanced(int[] nums) {
//         int n = nums.length;

//         // Fast early exit
//         Set<Integer> evens = new HashSet<>();
//         Set<Integer> odds  = new HashSet<>();
//         for (int x : nums) {
//             if ((x & 1) == 0) evens.add(x);
//             else odds.add(x);
//         }
//         if (evens.size() == odds.size()) return n;
//         if (evens.isEmpty() || odds.isEmpty()) return 0;

//         // Next occurrence array
//         int[] next = new int[n];
//         Arrays.fill(next, n);
//         Map<Integer, Integer> lastPos = new HashMap<>();
//         for (int i = n - 1; i >= 0; i--) {
//             if (lastPos.containsKey(nums[i])) {
//                 next[i] = lastPos.get(nums[i]);
//             }
//             lastPos.put(nums[i], i);
//         }

//         // Prefix balance array
//         int[] balance = new int[n];
//         Set<Integer> seenE = new HashSet<>();
//         Set<Integer> seenO = new HashSet<>();
//         int cur = 0;
//         for (int i = 0; i < n; i++) {
//             int x = nums[i];
//             if ((x & 1) == 0) {
//                 if (seenE.add(x)) cur++;
//             } else {
//                 if (seenO.add(x)) cur--;
//             }
//             balance[i] = cur;
//         }

//         SegmentTree st = new SegmentTree(n);
//         st.build(balance, 1, 0, n - 1);

//         int best = 0;
//         for (int left = 0; left < n; left++) {
//             int right = st.findLastZero(1, 0, n - 1, left);
//             if (right != -1) {
//                 best = Math.max(best, right - left + 1);
//             }

//             if (left < n - 1) {
//                 int end = next[left] - 1;
//                 if (end >= left + 1) {
//                     int delta = (nums[left] & 1) == 0 ? -1 : 1;
//                     st.update(1, 0, n - 1, left + 1, end, delta);
//                 }
//             }
//         }

//         return best;
//     }
// }

