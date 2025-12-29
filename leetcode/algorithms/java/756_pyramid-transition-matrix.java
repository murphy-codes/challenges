// Source: https://leetcode.com/problems/pyramid-transition-matrix/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-29
// At the time of submission:
//   Runtime 2 ms Beats 100.00%
//   Memory 43.97 MB Beats 85.99%

/****************************************
* 
* You are stacking blocks to form a pyramid. Each block has a color, which is
* _ represented by a single letter. Each row of blocks contains one less block
* _ than the row beneath it and is centered on top.
* To make the pyramid aesthetically pleasing, there are only specific triangular
* _ patterns that are allowed. A triangular pattern consists of a single block
* _ stacked on top of two blocks. The patterns are given as a list of three-letter
* _ strings `allowed`, where the first two characters of a pattern represent
* _ the left and right bottom blocks respectively, and the third character is
* _ the top block.
* • For example, `"ABC"` represents a triangular pattern with a `'C'` block
* __ stacked on top of an `'A'` (left) and `'B'` (right) block. Note that this
* __ is different from `"BAC"` where `'B'` is on the left bottom and `'A'` is
* __ on the right bottom.
* You start with a bottom row of blocks `bottom`, given as a single string,
* _ that you must use as the base of the pyramid.
* Given `bottom` and `allowed`, return true if you can build the pyramid all
* _ the way to the top such that every triangular pattern in the pyramid is
* _ in `allowed`, or `false` otherwise.
*
* Example 1:
* [Image: https://assets.leetcode.com/uploads/2021/08/26/pyramid1-grid.jpg]
* Input: bottom = "BCD", allowed = ["BCC","CDE","CEA","FFF"]
* Output: true
* Explanation: The allowed triangular patterns are shown on the right.
* Starting from the bottom (level 3), we can build "CE" on level 2 and then build "A" on level 1.
* There are three triangular patterns in the pyramid, which are "BCC", "CDE", and "CEA". All are allowed.
*
* Example 2:
* [Image: https://assets.leetcode.com/uploads/2021/08/26/pyramid2-grid.jpg]
* Input: bottom = "AAAA", allowed = ["AAB","AAC","BCD","BBE","DEF"]
* Output: false
* Explanation: The allowed triangular patterns are shown on the right.
* Starting from the bottom (level 4), there are multiple ways to build level 3, but trying all the possibilities, you will get always stuck before building level 1.
*
* Constraints:
* • `2 <= bottom.length <= 6`
* • `0 <= allowed.length <= 216`
* • `allowed[i].length == 3`
* • The letters in all input strings are from the set `{'A', 'B', 'C', 'D', 'E', 'F'}`.
* • All the values of `allowed` are unique.
* 
****************************************/

import java.util.List;

class Solution {
    // Converts pyramid transitions into a compact numeric DFS with memoization.
    // Allowed triples stored in fixed 3D arrays allow constant-time lookups.
    // Each row is encoded as a base-6 integer so partial rows can be memoized.
    // DFS builds upper layers while pruning invalid partial rows early.
    // Runtime worst-case exponential but tight pruning and caching keep it fast.

    public boolean pyramidTransition(String bottom, List<String> allowed) {
        return new Solver(allowed, bottom.length()).canDo(bottom);
    }

    static final class Solver {
        // allowed[a][b] -> list of possible toppers (as ints 0-5)
        final int[][][] allowed = new int[6][6][];
        // cache[length][encodedRow] = 0 (unseen), 1 (true), or 2 (false)
        final int[][] cache;

        public Solver(List<String> allowedList, int bottomLength) {
            final int[][] count = new int[6][6];

            // Count how many topper entries each pair will have
            for (String pattern : allowedList) {
                int left = pattern.charAt(0) - 'A';
                int right = pattern.charAt(1) - 'A';
                count[left][right]++;
            }

            // Allocate the correct-sized arrays per (left, right) pair
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    allowed[i][j] = new int[count[i][j]];
                }
            }

            // Cache size grows with length; precompute size per layer
            this.cache = new int[bottomLength][];
            int size = 36; // 6 * 6
            for (int length = 3; length < bottomLength; length++) {
                size *= 6;
                cache[length] = new int[size];
            }

            // Fill allowed array with toppers
            for (String pattern : allowedList) {
                int left = pattern.charAt(0) - 'A';
                int right = pattern.charAt(1) - 'A';
                int top = pattern.charAt(2) - 'A';
                allowed[left][right][--count[left][right]] = top;
            }
        }

        boolean canDo(String bottomRow) {
            // Convert row string to 0-5 indices for efficiency
            final int[] row = new int[bottomRow.length()];
            for (int i = 0; i < row.length; i++) {
                row[i] = bottomRow.charAt(i) - 'A';
            }

            // Length 2 only needs to check if a topper exists
            return row.length == 2
                ? allowed[row[0]][row[1]].length > 0
                : compute(row, row.length);
        }

        boolean canDo(int[] row, int length) {
            // Base case for length 2
            if (length == 2) {
                return allowed[row[0]][row[1]].length > 0;
            }

            // Check cache
            final int encoded = encode(row, length);
            final int cached = cache[length][encoded];
            if (cached != 0) {
                return cached == 1;
            }

            // Otherwise compute & store result
            final boolean result = compute(row, length);
            cache[length][encoded] = result ? 1 : 2;
            return result;
        }

        // Encode row as base-6 integer for dense memo indexing
        private int encode(int[] row, int len) {
            int encoded = row[0];
            for (int i = 1; i < len; i++) {
                encoded = encoded * 6 + row[i];
            }
            return encoded;
        }

        // Quick prune: ensure each adjacent pair can produce a topper
        boolean compute(int[] row, int length) {
            for (int i = 2; i < length; i++) {
                if (allowed[row[i - 1]][row[i]].length == 0) {
                    return false;
                }
            }
            return compute(new int[length], 0, row, 0, length);
        }

        // DFS to build next row; prefixRow accumulates toppers
        boolean compute(int[] prefixRow, int prefixLen,
                        int[] suffixRow, int suffixIdx, int suffixLen) {
            // Prune: if current prefix can't be completed to a valid pyramid
            if (prefixLen > 1 && !canDo(prefixRow, prefixLen)) {
                return false;
            }

            int nextIdx = suffixIdx + 1;

            // Try all toppers for this adjacent pair
            if (nextIdx < suffixLen) {
                for (int top : allowed[suffixRow[suffixIdx]][suffixRow[nextIdx]]) {
                    prefixRow[prefixLen] = top;
                    if (compute(prefixRow, prefixLen + 1, suffixRow, nextIdx, suffixLen)) {
                        return true;
                    }
                }
            } else {
                // End of level → validate completed row
                return canDo(prefixRow, prefixLen);
            }
            return false;
        }
    }
}
