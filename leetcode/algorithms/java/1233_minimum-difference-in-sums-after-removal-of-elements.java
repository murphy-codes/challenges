// Source: https://leetcode.com/problems/remove-sub-folders-from-the-filesystem/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-07-18
// At the time of submission:
//   Runtime 39 ms Beats 87.11%
//   Memory 55.26 MB Beats 84.38%

/****************************************
* 
* Given a list of folders `folder`, return the folders after removing all
* _ sub-folders in those folders. You may return the answer in any order.
* If a `folder[i]` is located within another `folder[j]`, it is called a
* _ sub-folder of it. A sub-folder of `folder[j]` must start with `folder[j]`,
* _ followed by a `"/"`. For example, `"/a/b"` is a sub-folder of `"/a"`, but
* _ `"/b"` is not a sub-folder of `"/a/b/c"`.
* The format of a path is one or more concatenated strings of the form: `'/'`
* _ followed by one or more lowercase English letters.
* • For example, `"/leetcode"` and `"/leetcode/problems"` are valid paths while
* __ an empty string and `"/"` are not.
*
* Example 1:
* Input: folder = ["/a","/a/b","/c/d","/c/d/e","/c/f"]
* Output: ["/a","/c/d","/c/f"]
* Explanation: Folders "/a/b" is a subfolder of "/a" and "/c/d/e" is inside of folder "/c/d" in our filesystem.
*
* Example 2:
* Input: folder = ["/a","/a/b/c","/a/b/d"]
* Output: ["/a"]
* Explanation: Folders "/a/b/c" and "/a/b/d" will be removed because they are subfolders of "/a".
*
* Example 3:
* Input: folder = ["/a/b/c","/a/b/ca","/a/b/d"]
* Output: ["/a/b/c","/a/b/ca","/a/b/d"]
*
* Constraints:
* • 1 <= folder.length <= 4 * 104
* • 2 <= folder[i].length <= 100
* • folder[i] contains only lowercase letters and '/'.
* • folder[i] always starts with the character '/'.
* • Each folder name is unique.
* 
****************************************/

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

class Solution {
    // Sort the folders so parent folders come before their subfolders
    // Iterate over sorted folders, keeping track of the last root folder
    // If a folder is not a subfolder of the last root, add it to the result
    // A subfolder starts with the root folder plus a '/' character
    // Time: O(n log n + n * m), Space: O(n * m); n = num of folders, m = avg len
    public List<String> removeSubfolders(String[] folder) {
        Arrays.sort(folder); // Lexicographically sort the folder paths

        List<String> result = new ArrayList<>();
        String prev = ""; // Holds the last root folder added to result

        for (String path : folder) {
            // If current path is not a subfolder of the last added one
            if (prev.isEmpty() || !path.startsWith(prev + "/")) {
                result.add(path);
                prev = path;
            }
        }

        return result;
    }
}
