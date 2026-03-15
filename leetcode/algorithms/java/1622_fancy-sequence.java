// Source: https://leetcode.com/problems/fancy-sequence/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2026-03-15
// At the time of submission:
//   Runtime 46 ms Beats 84.48%
//   Memory 124.42 MB Beats 65.52%

/****************************************
* 
* Write an API that generates fancy sequences using the `append`, `addAll`, and
* _ `multAll` operations.
* Implement the `Fancy` class:
* • `Fancy()` Initializes the object with an empty sequence.
* • `void append(val)` Appends an integer `val` to the end of the sequence.
* • `void addAll(inc)` Increments all existing values in the sequence by an integer `inc`.
* • `void multAll(m)` Multiplies all existing values in the sequence by an integer `m`.
* • `int getIndex(idx)` Gets the current value at index `idx` (0-indexed) of
* __ the sequence modulo `10^9 + 7`. If the index is greater or equal than the
* __ length of the sequence, return -1.
*
* Example 1:
* Input
* ["Fancy", "append", "addAll", "append", "multAll", "getIndex", "addAll", "append", "multAll", "getIndex", "getIndex", "getIndex"]
* [[], [2], [3], [7], [2], [0], [3], [10], [2], [0], [1], [2]]
* Output
* [null, null, null, null, null, 10, null, null, null, 26, 34, 20]
* Explanation
* Fancy fancy = new Fancy();
* fancy.append(2);   // fancy sequence: [2]
* fancy.addAll(3);   // fancy sequence: [2+3] -> [5]
* fancy.append(7);   // fancy sequence: [5, 7]
* fancy.multAll(2);  // fancy sequence: [5*2, 7*2] -> [10, 14]
* fancy.getIndex(0); // return 10
* fancy.addAll(3);   // fancy sequence: [10+3, 14+3] -> [13, 17]
* fancy.append(10);  // fancy sequence: [13, 17, 10]
* fancy.multAll(2);  // fancy sequence: [13*2, 17*2, 10*2] -> [26, 34, 20]
* fancy.getIndex(0); // return 26
* fancy.getIndex(1); // return 34
* fancy.getIndex(2); // return 20
*
* Constraints:
* • `1 <= val, inc, m <= 100`
* • `0 <= idx <= 10^5`
* • `At most `10^5` calls total will be made to `append`, `addAll`, `multAll`, and `getIndex`.
* 
****************************************/

class Fancy {
    // Maintain a global linear transform: value = stored * mul + add (mod M).
    // append() stores numbers in normalized form by reversing the transform
    // using a modular inverse so later operations apply correctly.
    // addAll() and multAll() update the global transform instead of each value.
    // getIndex() reconstructs the value using the stored base value.
    // Time: O(1) amortized per operation (inverse via fast power). Space: O(n).

    // All results must be modulo this value
    private static final long MOD = 1_000_000_007;

    // Stores normalized values of appended numbers
    private List<Long> values;

    // Global transformation parameters
    private long mul;
    private long add;

    public Fancy() {
        values = new ArrayList<>();
        mul = 1;
        add = 0;
    }

    public void append(int val) {

        // Normalize the value by reversing the current transformation
        long normalized =
                ((val - add + MOD) % MOD) *
                modInverse(mul) % MOD;

        values.add(normalized);
    }

    public void addAll(int inc) {
        add = (add + inc) % MOD;
    }

    public void multAll(int m) {
        mul = (mul * m) % MOD;
        add = (add * m) % MOD;
    }

    public int getIndex(int idx) {

        if (idx >= values.size())
            return -1;

        long base = values.get(idx);

        long result = (base * mul % MOD + add) % MOD;

        return (int) result;
    }

    // Modular exponentiation
    private long modPow(long base, long exp) {

        long result = 1;

        while (exp > 0) {

            if ((exp & 1) == 1)
                result = result * base % MOD;

            base = base * base % MOD;
            exp >>= 1;
        }

        return result;
    }

    // Fermat modular inverse
    private long modInverse(long x) {
        return modPow(x, MOD - 2);
    }
}