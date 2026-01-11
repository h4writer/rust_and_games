class Solution {
    fun repeatedNTimes(nums: IntArray): Int {
        assert(nums.size % 2 == 0);

        var set = hashSetOf<Int>();
        for (i in nums) {
            if (set.contains(i))
                return i;
            set.add(i);
        }
        return -1;
    }
}


val sol = Solution();
var result: Int;

result = sol.repeatedNTimes(intArrayOf(1, 2, 3, 3));
assert(result == 3)

result = sol.repeatedNTimes(intArrayOf(2, 1, 2, 5, 3, 2));
assert(result == 2)

result = sol.repeatedNTimes(intArrayOf(5, 1, 5, 2, 5, 3, 5, 4));
assert(result == 5)