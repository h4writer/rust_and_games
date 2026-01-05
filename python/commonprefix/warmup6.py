
# Given two strings, write a function to determine if they are anagrams.

def is_anagram(str1, str2):
    from collections import defaultdict

    sol = defaultdict(int)
    for i in str1:
        sol[i] += 1
    for i in str2:
        sol[i] -= 1
        if sol[i] == 0:
            del sol[i]

    return len(sol) == 0

print(is_anagram("a","a"))
print(is_anagram("a","b"))
print(is_anagram("aa","bb"))
print(is_anagram("abc","cba"))
