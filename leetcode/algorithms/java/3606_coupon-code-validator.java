// Source: https://leetcode.com/problems/coupon-code-validator/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-12
// At the time of submission:
//   Runtime 4 ms Beats 100.00%
//   Memory 47.54 MB Beats 42.27%

/****************************************
* 
* You are given three arrays of length `n` that describe the properties of `n`
* _ coupons: `code`, `businessLine`, and `isActive`. The `i^th` coupon has:
* _ • `code[i]`: a string representing the coupon identifier.
* _ • `businessLine[i]`: a string denoting the business category of the coupon.
* _ • `isActive[i]`: a boolean indicating whether the coupon is currently active.
* A coupon is considered valid if all of the following conditions hold:
* _ 1. `code[i]` is non-empty and consists only of alphanumeric characters
* __ (a-z, A-Z, 0-9) and underscores (`_`).
* _ 2. `businessLine[i]` is one of the following four categories:
* __ `"electronics"`, `"grocery"`, `"pharmacy"`, `"restaurant"`.
* _ 3. `isActive[i]` is true.
* Return an array of the codes of all valid coupons, sorted first by their
* _ businessLine in the order: `"electronics"`, `"grocery"`, `"pharmacy"`,
* _ `"restaurant"`, and then by code in lexicographical (ascending) order
* _ within each category.
*
* Example 1:
* Input: code = ["SAVE20","","PHARMA5","SAVE@20"], businessLine = ["restaurant","grocery","pharmacy","restaurant"], isActive = [true,true,true,true]
* Output: ["PHARMA5","SAVE20"]
* Explanation:
* First coupon is valid.
* Second coupon has empty code (invalid).
* Third coupon is valid.
* Fourth coupon has special character @ (invalid).
*
* Example 2:
* Input: code = ["GROCERY15","ELECTRONICS_50","DISCOUNT10"], businessLine = ["grocery","electronics","invalid"], isActive = [false,true,true]
* Output: ["ELECTRONICS_50"]
* Explanation:
* First coupon is inactive (invalid).
* Second coupon is valid.
* Third coupon has invalid business line (invalid).
*
* Constraints:
* • `n == code.length == businessLine.length == isActive.length`
* • `1 <= n <= 100`
* • `0 <= code[i].length, businessLine[i].length <= 100`
* • `code[i]` and `businessLine[i]` consist of printable ASCII characters.
* • `isActive[i]` is either `true` or `false`.
* 
****************************************/

class Solution {
    // Filter coupons by checking active status, non-empty code, and valid characters.
    // Valid coupons are placed directly into lists by business line, preserving the
    // required priority order without using a comparator. Each list is sorted
    // lexicographically and concatenated in order. Time complexity is O(n log n)
    // due to sorting, and space complexity is O(n) for storing valid coupon codes.
    public List<String> validateCoupons(
            String[] code, String[] businessLine, boolean[] isActive) {
        List<String> electronicsCodes = new ArrayList<>();
        List<String> groceryCodes = new ArrayList<>();
        List<String> pharmacyCodes = new ArrayList<>();
        List<String> restaurantCodes = new ArrayList<>();

        for (int i = 0; i < code.length; i++) {
            if (!isActive[i] || code[i].isEmpty()) continue; 
            boolean isValidCode = true;
            for (char ch : code[i].toCharArray()) {
                if (!Character.isLetterOrDigit(ch) && ch != '_') {
                    isValidCode = false;
                    break;
                }
            }
            if (!isValidCode) continue;

            // Bucket coupon code by business line
            if (businessLine[i].equals("electronics")) electronicsCodes.add(code[i]);
            else if (businessLine[i].equals("grocery")) groceryCodes.add(code[i]);
            else if (businessLine[i].equals("pharmacy")) pharmacyCodes.add(code[i]);
            else if (businessLine[i].equals("restaurant")) restaurantCodes.add(code[i]);
        }

        // Sort each category lexicographically
        Collections.sort(electronicsCodes);
        Collections.sort(groceryCodes);
        Collections.sort(pharmacyCodes);
        Collections.sort(restaurantCodes);

        // Combine results in required priority order
        List<String> result = new ArrayList<>();
        result.addAll(electronicsCodes);
        result.addAll(groceryCodes);
        result.addAll(pharmacyCodes);
        result.addAll(restaurantCodes);

        return result;
    }
}
