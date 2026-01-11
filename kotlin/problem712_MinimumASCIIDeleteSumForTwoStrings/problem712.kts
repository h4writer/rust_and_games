class Solution {

    fun iterative_del(s1: String, s2: String): Int {

        var before: IntArray = IntArray(s2.length + 1) { 0 }
        var current: IntArray = IntArray(s2.length + 1) { 0 }

        for (j in s2.indices) {
            before[j+1] = s2[j].code + before[j];
        }

        //println("${before.toList()}");

        // SEA - EAT
        //     |  -     E    EA    EAT
        //   --|----------------------
        // -   |        E    EA    EAT
        // E   |  E     /     A     AT
        // EA  |  EA    A     /      T
        // SEA |  SEA  SA     S     ST

        for (i in s1.indices) {
            current[0] = before[0] + s1[i].code;

            for (j in s2.indices) {
                // FOOT / BARR
                // - FOO+[T] / BAR
                // - FOO / BAR+[R]
                // - FOO+[T] / BAR+[R]
                current[j + 1] = minOf(
                    s2[j].code + current[j],
                    s1[i].code + before[j + 1],
                    (if (s1[i] == s2[j]) 0 else s1[i].code + s2[j].code) + before[j]
                );
            }
            //println("${current.toList()}");

            val tmp = current;
            current = before;
            before = tmp;
        }

        return before.last();
    }

    fun minimumDeleteSum(s1: String, s2: String): Int {
        println("${s1} - ${s2}");
        assert(iterative_del(s1, s2) == iterative_del(s2, s1));
        val res = iterative_del(s1, s2);
        println("${res}");
        return res;
    }
}

val sol = Solution();
var result: Int;

result = sol.minimumDeleteSum("delete", "l");
assert(result == 519);

result = sol.minimumDeleteSum("sea", "eat");
assert(result == 231);

result = sol.minimumDeleteSum("delete", "leet");
assert(result == 403);

result = sol.minimumDeleteSum("ea", "ae");
assert(result == 'a'.code * 2);

result = sol.minimumDeleteSum("ccaccjp", "fwosarcwge");
assert(result == 1399);

result = sol.minimumDeleteSum("ccaccjp", "fwosarcwge");
assert(result == 1399);