
# Given a string, find the length of the longest substring without repeating characters.



def long_substr(data):

    # stupid
    length = 0
    for i in range(len(data)):
        found = {}
        for j in range(i, len(data)):
            if data[j] in found:
                if length < j-i:
                    length = j-i
                break
            found[data[j]] = True
    return longest

def long_substr_2(data):

    # better

    length = 0
    found = {}
    start = 0
    end = 0
    while end < len(data):
        if data[end] in found:
            next_ = found[data[end]]+1
            for k in list(found.keys()):
                if found[k] < next_: 
                    del found[k]
            start = next_
            continue

        if end - start + 1 > length:
            length = end - start + 1

        found[data[end]] = end
        end += 1

    return length

def long_substr_3(data):
    import defaultdict

    # even better
    # keep track of n° of doubles
    # and move start forward until
    # no doubles anymore

    length = 0
    found = defaultdict(0)
    start = 0
    end = 0
    double = 0
    while end < len(data):
        if data[end] in found:
            # TODO

        if end - start + 1 > length:
            length = end - start + 1

        found[data[end]] += 1
        end += 1

    return length


assert long_substr_2("aaa") == 1
assert long_substr_2("abc") == 3
assert long_substr_2("aba") == 2
assert long_substr_2("abaaba") == 2
assert long_substr_2("abcaba") == 3
assert long_substr_2("aabcaba") == 3
