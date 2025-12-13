// Source: https://leetcode.com/problems/coupon-code-validator/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-12-12
// At the time of submission:
//   Runtime 19 ms Beats 42.73%
//   Memory 47.76 MB Beats 32.27%

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

// import java.util.List;
// import java.util.ArrayList;

class Solution {
    // Filter coupons by checking active status, valid business line, and ensuring
    // the code is non-empty and contains only alphanumeric characters or underscores.
    // Store valid coupons as (businessLine, code) pairs, then sort them using a fixed
    // priority order for businessLine and lexicographical order for codes within each
    // category. Finally, extract the sorted codes. Time complexity is O(n log n) due
    // to sorting, and space complexity is O(n) for storing valid coupons.

    private static final Map<String, Integer> PRIORITY = Map.of(
        "electronics", 0, "grocery", 1, "pharmacy", 2, "restaurant", 3
    );

    public List<String> validateCoupons(String[] code, String[] businessLine, boolean[] isActive) {
        List<String> valid = new ArrayList<>();
        List<Coupon> coupons = new ArrayList<>();
        
        for (int i = 0; i < code.length; i++) {
            if(isActive[i] && PRIORITY.containsKey(businessLine[i])) {
                if (code[i] != null && code[i].matches("^[A-Za-z0-9_]+$")) {
                    coupons.add(new Coupon(businessLine[i], code[i]));
                }
            }
        }

        coupons.sort((a, b) -> {
            int p1 = PRIORITY.get(a.businessLine);
            int p2 = PRIORITY.get(b.businessLine);
            if (p1 != p2) return p1 - p2;
            return a.code.compareTo(b.code);
        });

        for (Coupon c : coupons) valid.add(c.code);
        return valid;
    }
}

class Coupon {
    String businessLine;
    String code;
    Coupon(String businessLine, String code) {
        this.businessLine = businessLine;
        this.code = code;
    }
}