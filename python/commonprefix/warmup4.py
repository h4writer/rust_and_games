# How would you calculate (baseexponent)(modmodulus) if the exponent is a very large number (e.g., 2^1024)?

# base^exp mod m


# square and multiply
#
# exp to binary
# left to right
# square and maybe multiply
#  

# 3^5
# 101
# 1) (0^2) * 3 = 3
# 2) (3^2)     = 9
# 3) (9^2) * 3 = 243


# 3^45 mod 7
# 101101
#
# 1) (0^2) * 3 = 3 mod 7 = 3
# 2) (3^2)     = 9 mod 7 = 2
# 3) (2^2) * 3 = 12 mod 7 = 5
# 3) (5^2) * 3 = 75 mod 7 = 5
# 3) (5^2)     = 25 mod 7 = 4
# 3) (4^2) * 3 = 48 mod 7 = 6

