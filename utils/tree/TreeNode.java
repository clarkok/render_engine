package utils.tree;

import java.util.List;
import java.util.ArrayList;

public class TreeNode {
    private TreeNode parent;
    private List<TreeNode> children;

    public TreeNode () {
        parent = null;
        children = new ArrayList<TreeNode>();
    }

    /**
     * Get parent of the node
     * @return the parent
     */
    public TreeNode getParent() {
        return this.parent;
    }

    /**
     * Set parent
     * @param p the parent
     * @return the old parent
     */
    public TreeNode setParent(TreeNode p) {
        TreeNode old = this.parent;
        this.parent = p;
        return old;
    }

    /**
     * Append this node to another
     * @param p the new parent
     * @return the new parent
     */
    public TreeNode appendTo(TreeNode p) {
        p.appendChild(this);
        return p;
    }

    /**
     * Append a child to this node
     * @param c the new child
     * @return the new child
     */
    public TreeNode appendChild(TreeNode c) {
        this.children.add(c);
        c.setParent(this);
        return c;
    }

    /**
     * Get the list of children
     * @return the list
     */
    public List<TreeNode> getChildren() {
        return this.children;
    }

    /**
     * Get the child at certain index
     * @param ind the index
     * @return the child
     */
    public TreeNode getChild(int ind) {
        return this.children.get(ind);
    }

    /**
     * Get the index of a certain child
     * @param c the child
     * @return the index, -1 if not found
     */
    public int indexOfChild(TreeNode c) {
        return this.children.indexOf(c);
    }

    /**
     * Get the next sibling of the node
     * @return the next sibling, null if not found
     */
    public TreeNode nextSibling() {
        if (this.parent == null) {
            return null;
        }
        int ind = this.parent.indexOfChild(this);
        if (ind+1 == this.parent.getChildren().size()) {
            return null;
        }
        return this.parent.getChild(ind+1);
    }

    /**
     * Get the previous sibling of the node
     * @return the previous sibling, null if not found
     */
    public TreeNode previousSibling() {
        if (this.parent == null) {
            return null;
        }
        int ind = this.parent.indexOfChild(this);
        if (ind == 0) {
            return null;
        }
        return this.parent.getChild(ind-1);
    }
}
