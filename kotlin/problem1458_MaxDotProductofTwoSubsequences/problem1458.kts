class Solution {

    fun maxDotProduct(nums1: IntArray, nums2: IntArray): Int {
        val result = maxDotProduct_iterative(nums1.asList(), nums2.asList());
        println(result);
        return result;
    }

    fun maxDotProduct_iterative(a: List<Int>, b: List<Int>): Int {
        var nextline = IntArray(b.size + 1) { Int.MIN_VALUE };
        var current = IntArray(b.size + 1) { Int.MIN_VALUE };
        for (i in a.indices.reversed()) {
            for (j in b.indices.reversed()) {
                current[j] = maxOf(
                    a[i] * b[j] + maxOf(0, nextline[j + 1]),
                    nextline[j],
                    current[j + 1]
                );
            }

            println("${current.asList()}");
            run { val temp = nextline; nextline = current; current = temp } // swap current/nextline.
        }

        return nextline[0];
    }


    fun maxDotProduct_recursive(a: List<Int>, b: List<Int>): Int {
        // [a rest_a] . [b rest_b] =
        // maxOf (
        //     a*b + max(0, [rest_a].[rest_b]),
        //     [a rest_a].[rest_b],
        //     [rest_a].[b rest_b]
        // )

        if (a.size == 1) {
            return if (a[0] >= 0) a[0] * b.max(); else a[0] * b.min();
        } else if (b.size == 1) {
            return if (b[0] >= 0) b[0] * a.max(); else b[0] * a.min();
        }

        val res = maxOf(
            a[0] * b[0] + maxOf(0, maxDotProduct_recursive(a.subList(1, a.size), b.subList(1, b.size))),
            maxDotProduct_recursive(a, b.subList(1, b.size)),
            maxDotProduct_recursive(a.subList(1, a.size), b)
        );
        println("${a}.${b} = ${res}");
        return res;
    }
}

var sol = Solution();
var result = 0;


result = sol.maxDotProduct(intArrayOf(2, 1), intArrayOf(3, 0, -6));
assert(result == 6);

result = sol.maxDotProduct(intArrayOf(2, 1, -2, 5), intArrayOf(3, 0, -6));
assert(result == 18);

result = sol.maxDotProduct(intArrayOf(10, -2, -2, 15), intArrayOf(1, 1, 1));
assert(result == 25);

result = sol.maxDotProduct(intArrayOf(1), intArrayOf(10));
assert(result == 10);

result = sol.maxDotProduct(intArrayOf(-5, -1, -2), intArrayOf(3, 3, 5, 5));
assert(result == -3);