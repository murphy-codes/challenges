// Source: https://leetcode.com/problems/pyramid-transition-matrix/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-29
// At the time of submission:
//   Runtime 77 ms Beats 82.80%
//   Memory 49.62 MB Beats 12.10%

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

// import java.util.Map;
// import java.util.HashMap;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.Set;
// import java.util.HashSet;

class Solution {
    // DFS builds the pyramid row-by-row: for each adjacent pair of chars in a row,
    // we lookup all valid upper blocks to form the next row. We recursively try each
    // possible next row until reaching length 1. Memoization stores rows that cannot
    // reach the top, preventing repeated work. Time is bounded due to short row size,
    // with effective complexity near O(branch^depth). Space is O(n) for maps & cache.

    private Map<String, List<Character>> next = new HashMap<>();
    private Set<String> invalidRows = new HashSet<>();

    public boolean pyramidTransition(String bottom, List<String> allowed) {
        // preprocess allowed patterns into adjacency map
        for (String s : allowed) {
            String base = s.substring(0, 2);
            next.computeIfAbsent(base, k -> new ArrayList<>())
                .add(s.charAt(2));
        }
        return canBuild(bottom);
    }

    private boolean canBuild(String row) {
        if (row.length() == 1) return true;
        if (invalidRows.contains(row)) return false;

        List<String> candidates = new ArrayList<>();
        generateNextRows(row, 0, new StringBuilder(), candidates);

        for (String nxt : candidates) {
            if (canBuild(nxt)) return true;
        }

        invalidRows.add(row);
        return false;
    }

    private void generateNextRows(String row, int idx, StringBuilder sb, 
                                  List<String> result) {
        if (idx == row.length() - 1) {
            result.add(sb.toString());
            return;
        }
        String base = row.substring(idx, idx + 2);
        if (!next.containsKey(base)) return;

        for (char c : next.get(base)) {
            sb.append(c);
            generateNextRows(row, idx + 1, sb, result);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
