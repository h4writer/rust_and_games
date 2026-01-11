import java.security.MessageDigest


fun hash(content: String): String {
    val bytes = content.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("", { str, it -> str + "%02x".format(it) })
}

data class Node (val left: Node?, val right: Node?, val hash: String);
data class Root (val node: Node, val depth: Int);

fun createMerkle(data: Array<String>) : Root {

    val childs = arrayOfNulls<Node>(data.size);
    var i = 0;
    for (item in data) {
        childs[i++] = Node(null, null, hash(item));
    }

    var size = childs.size;
    var depth = 0;
    while (size > 1) {
        var i_after = 0;
        for (i_before in 0..<size step 2) {
            val left = childs[i_before];
            val right = if (i_before + 1 < size) childs[i_before+1] else childs[i_before];
            var content = left!!.hash + right!!.hash;
            childs[i_after++] = Node(left, right, hash(content));
        }
        size = i_after;
        depth++;
    }

    return Root(childs[0]!!, depth);
}

fun printMerkle(root: Root) {
    printMerkle(root.node, "-");
}


fun printMerkle(node: Node, before:String) {
    println(before+node.hash);
    if (node.left != null)
        printMerkle(node.left, before = " "+before);
    if (node.right != null)
        printMerkle(node.right, before = " "+before);
}

fun createMerkleProof(root: Root, id: Int, depth: Int) {
    // 5/2 = 2
    /*if (id < ) {
             1
         1       2
       1   2   3   X
      1 2 3 4 5 X X X
    }*/
}

val data = arrayOf("test","test2", "test3", "test4", "test5");
var root = createMerkle(data);

printMerkle(root);

//createMerkleProof(root, 2);
//val n = Node( null, null);
