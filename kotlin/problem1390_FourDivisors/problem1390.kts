import kotlin.math.sqrt

class Solution {

    fun createZeef(primes: ArrayList<Int>, is_prime: BooleanArray, max: Int) {
        for (i in 2..<max) {
            if (!is_prime[i])
                continue;

            primes.add(i);
            var mul = 2;
            while (i * mul < max) {
                is_prime[i * mul] = false;
                mul++;
            }
        }
    }

    fun sumFourDivisors(nums: IntArray): Int {
        val max = sqrt(nums.max().toDouble()).toInt() + 1;

        val primes = ArrayList<Int>();
        val is_prime = BooleanArray(max) { true };
        createZeef(primes, is_prime, max);

        // 1*prime*prime*value
        var sum = 0;
        for (value in nums) {
            for (prime in primes) {
                if (prime >= value)
                    break;

                if (value % prime != 0) {
                    continue;
                }

                // found prime1

                // Test for:
                // possible 1: 1, prime1, prime2, value
                // possible 2: 1, prime1, prime1*prime1, prime1*prime1*prime1

                val other = value / prime;
                if (other == prime || other * prime != value)
                    break;

                if (other >= is_prime.size || is_prime[other]) {
                    println("found 1, ${prime}, ${other}, ${value}");
                    sum += 1 + other + prime + value;
                    break;
                }

                if (prime * prime == other) {
                    println("found 2, ${prime}, ${other}, ${value}");
                    sum += 1 + other + prime + value;
                    break;
                }
                break;
            }
        }

        println("sum: " + sum);
        return sum;
    }
}

val sol = Solution();
var result: Int;

result = sol.sumFourDivisors(intArrayOf(21, 4, 7));
assert(result == 32);

result = sol.sumFourDivisors(intArrayOf(1, 2, 3, 4, 5));
assert(result == 0);

result = sol.sumFourDivisors(intArrayOf(97, 4, 7));
assert(result == 0);

result = sol.sumFourDivisors(intArrayOf(97 * 2, 4, 7));
assert(result == 1 + 2 + 97 + 97 * 2);

result = sol.sumFourDivisors(intArrayOf(8));
assert(result == 1 + 2 + 4 + 8);