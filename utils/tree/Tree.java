package utils.tree;

import utils.tree.TreeNode;

public class Tree {
    private TreeNode root;

    public Tree() {
        root = null;
    }

    public Tree(TreeNode r) {
        root = r;
    }

    public TreeNode getRoot() {
        return this.root;
    }

    public TreeNode setRoot(TreeNode r) {
        TreeNode old = this.root;
        this.root = r;
        return old;
    }

}
