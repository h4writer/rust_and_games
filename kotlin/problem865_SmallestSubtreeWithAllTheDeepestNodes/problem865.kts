import java.util.LinkedList
import kotlin.math.*

/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * */

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

class Result(var node: TreeNode, var depth: Int)

class Solution {
    fun subtreeWithAllDeepest(root: Array<Int?>): TreeNode? {
        return subtreeWithAllDeepest(createTree(root));
    }

    fun dfs(res: Result, node: TreeNode, depth: Int = 0) {
        var lCurrDepth: Int = 0;
        var rCurrDepth: Int = 0;
        var lCurrNode: TreeNode = node;
        var rCurrNode: TreeNode = node;

        if (node.left == null && node.right == null) {
            res.depth = depth + 1;
            res.node = node;
            return;
        }

        if (node.left != null) {
            dfs(res, node.left!!, depth + 1);
            lCurrDepth = res.depth;
            lCurrNode = res.node;
        }

        if (node.right != null) {
            dfs(res, node.right!!, depth + 1);
            rCurrDepth = res.depth;
            rCurrNode = res.node;
        }

        if (lCurrDepth == rCurrDepth) {
            res.depth = lCurrDepth;
            res.node = node;
            return;
        }
        if (lCurrDepth > rCurrDepth) {
            res.depth = lCurrDepth;
            res.node = lCurrNode;
            return;
        }

        if (rCurrDepth > lCurrDepth) {
            res.depth = rCurrDepth;
            res.node = rCurrNode;
            return;
        }

        assert(false);
    }

    fun subtreeWithAllDeepest(root: TreeNode?): TreeNode? {
        if (root == null) return null;

        val result = Result(root, 0);
        dfs(result, root);
        return result.node;
    }
}

fun createTree(tree: Array<Int?>): TreeNode? {
    if (tree.size == 0)
        return null;

    val root = TreeNode(tree[0]!!);

    var worklist = LinkedList<TreeNode>();
    worklist.add(root);
    var i = 1;
    while (i < tree.size) {
        var left = tree[i++];
        var right = tree[i++];
        val node = worklist.remove();

        if (left != null) {
            val left = TreeNode(left);
            node.left = left;
            worklist.add(left);
        }

        if (right != null) {
            val right = TreeNode(right);
            node.right = right;
            worklist.add(right);
        }
    }

    return root;
}

fun printTree(tree: TreeNode?, prefix: String = "") {
    if (tree == null)
        return;

    printTree(tree.left, prefix + "   ");
    println("${prefix} - ${tree.`val`}");
    printTree(tree.right, prefix + "   ");
}

fun equal(tree1: TreeNode?, tree2: TreeNode?): Boolean {
    if (tree1 == null || tree2 == null)
        return tree1 == tree2;

    return tree1!!.`val` == tree2!!.`val`
            && equal(tree1.left, tree2.left)
            && equal(tree1.right, tree2.right);
}

fun equal(tree1: TreeNode?, tree2: Array<Int?>): Boolean {
    return equal(tree1, createTree(tree2))
}

var sol = Solution();
var result: TreeNode?;

result = sol.subtreeWithAllDeepest(arrayOf(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4));
assert(equal(result, arrayOf(2, 7, 4)));

result = sol.subtreeWithAllDeepest(arrayOf(1));
assert(equal(result, arrayOf(1)));

result = sol.subtreeWithAllDeepest(arrayOf(0, 1, 3, null, 2));
assert(equal(result, arrayOf(2)));
