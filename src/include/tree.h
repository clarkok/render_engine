#ifndef _TREE_H_
#define _TREE_H_

#include <iterator>
#include <vector>
#include "def.h"

class TreeNode {
    private:
        typedef TreeNode* TreeNodePointer;
        typedef vector<TreeNodePointer> TreeNodeList;
    public:
        typedef TreeNodeList::iterator iterator;
                TreeNode();
                ~TreeNode();
        void    attachChild(TreeNodePointer child);
        void    detachChild(TreeNodePointer child);
        void    setParent(TreeNodePointer parent);
        TreeNodePointer     getParent() const;
        TreeNodePointer     getChild(u32 index);
        iterator            childrenBegin();
        iterator            childrenEnd();
    private:
        TreeNodeList    *tn_children;
        TreeNodePointer tn_parent;
};

#endif //_TREE_H_
