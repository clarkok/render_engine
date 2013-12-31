#include <iterator>
#include <iostream>
#include <fstream>
#include <string>
#include "tree.h"

using namespace std;

class TestNode: public TreeNode {
    public:
        TestNode(const string &_s):s(new string(_s)) {};
        string  *s;
};

void output(TestNode &t, string ind) {
    cout << ind << *(t.s) << endl;
    for (TestNode::iterator i = t.childrenBegin(); i != t.childrenEnd(); i++) {
        output(*((TestNode*)(*i)), ind + "  ");
    }
}

int main () {
    TestNode *root = new TestNode("Root");

    TestNode *tmp = new TestNode("Node 1");
    root->attachChild(tmp);

    tmp = new TestNode("Node 2");
    root->attachChild(tmp);

    tmp = new TestNode("Node 2 1");
    root->getChild(1)->attachChild(tmp);

    output(*root, "");


    return 0;
}
