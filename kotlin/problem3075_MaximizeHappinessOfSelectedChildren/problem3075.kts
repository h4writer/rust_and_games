class Solution {
    fun maximumHappinessSum(happiness: IntArray, k: Int): Long {

        happiness.sort();

        var sum = 0L;
        for (i in 1..k) {
            val h =happiness[happiness.size-i] - i + 1;
            if (h <= 0)
                break;
            sum += h;
        }

        return sum;
    }
}

val sol = Solution();
var result: Long;

result = sol.maximumHappinessSum(intArrayOf(1,2,3), 2);
assert(result == 4L);