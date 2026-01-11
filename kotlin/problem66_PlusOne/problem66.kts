class Solution {
    fun plusOne(digits: IntArray): IntArray {
        for (i in digits.indices.reversed()) {
            digits[i] += 1;
            if (digits[i] < 10)
                return digits;
            digits[i] = 0;
        }

        return intArrayOf(1)+digits;
    }
}

val sol = Solution();
var result:IntArray;

result = sol.plusOne(intArrayOf(1,2,3));
assert(result.contentEquals((intArrayOf(1,2,4))));

result = sol.plusOne(intArrayOf(0));
assert(result.contentEquals((intArrayOf(1))));

result = sol.plusOne(intArrayOf(9));
assert(result.contentEquals((intArrayOf(1,0))));

result = sol.plusOne(intArrayOf(9,9));
assert(result.contentEquals((intArrayOf(1,0,0))));
