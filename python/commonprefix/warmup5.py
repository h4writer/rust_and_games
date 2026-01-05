
# Given a non-empty array of integers where every element appears twice except for one, find that single one.

def find_singleton(arr):
    from collections import defaultdict

    found = defaultdict(int)
    for i in arr:
        found[i] += 1
    for k in found:
        if found[k] == 1:
            return k
    return -1

def find_singleton(arr):
    sol = 0
    for i in arr:
        sol = sol ^ i
    return sol


print(find_singleton([1,2,3,2,3]))
print(find_singleton([2,3,2,3,1]))


