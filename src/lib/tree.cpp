#include <vector>
#include <iterator>
#include "def.h"
#include "tree.h"

TreeNode::TreeNode():tn_children(new TreeNodeList()), tn_parent(this) {
}

TreeNode::~TreeNode() {
    for (TreeNodeList::iterator i = tn_children->begin(); i != tn_children->end(); i++) {
        delete *i;
    }
    delete tn_children;
}

void TreeNode::attachChild(TreeNodePointer child) {
    tn_children->push_back(child);
    child->setParent(this);
}

void TreeNode::detachChild(TreeNodePointer child) {
    for (iterator i = tn_children->begin(); i != tn_children->end(); i++) {
        if (child == (*i)) {
            tn_children->erase(i);
            child->setParent(child);
            return;
        }
    }
}

void TreeNode::setParent(TreeNodePointer parent) {
    tn_parent = parent;
}

TreeNode::TreeNodePointer TreeNode::getChild(u32 index) {
    return ((*tn_children)[index]);
}

TreeNode::TreeNodePointer TreeNode::getParent() const {
    return tn_parent;
}

TreeNode::iterator TreeNode::childrenBegin() {
    return tn_children->begin();
}

TreeNode::iterator TreeNode::childrenEnd() {
    return tn_children->end();
}
