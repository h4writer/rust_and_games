import hashlib

def hash_better(content):
    return hashlib.sha256(content.encode("utf-8")).hexdigest()

def printTree(tree, spacer = ""):
    if tree["left"]:
        printTree(tree["left"], spacer+"  ")
    print (spacer, "-", tree["hash"])
    if tree["right"]:
        printTree(tree["right"], spacer+"  ")

def generate(transactions):


    hashes = []
    for i in range(len(transactions)):
        hashes.append({
            "hash": hash_better(transactions[i]),
            "left": None,
            "right": None})
        print(transactions[i], hash_better(transactions[i]))

    while len(hashes) > 1:
        next_ = []
        for i in range(0, len(hashes)-1, 2):
            next_.append({
                "hash": hash_better(hashes[i]["hash"]+hashes[i+1]["hash"]),
                "left": hashes[i],
                "right": hashes[i+1]})
        if len(hashes) % 2 == 1:
            next_.append({
                "hash": hash_better(hashes[-1]["hash"]+hashes[-1]["hash"]),
                "left": hashes[-1],
                "right": hashes[-1]
            })

        hashes = next_
    
    printTree(hashes[0])
    return hashes[0]



if __name__ == '__main__':
    generate(["a","b","c","d", "e"])
