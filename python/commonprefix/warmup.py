
# Given two strings of equal length, write a function to return their XOR result.
# Follow up: Why is it important to use a constant-time
# comparison when checking if a provided password hash matches a stored one?


def xor(str1, str2):
    ret = ""
    for i in range(len(str1)):
        ret += chr(ord(str1[i]) ^ ord(str2[i]))
    return ret

if __name__ == "__main__":
    answer = xor("abc", "bbb");
