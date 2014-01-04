#ifndef _DOM_H_
#define _DOM_H_

#include <string>
#include <map>
#include <vector>
#include <iterator>
#include "def.h"
#include "tree.h"

class DOMElement: public TreeNode {
    private:
        typedef vector<string>      DOMTokenList;
        typedef map<string, string> DOMAttribute;
    public:
                DOMElement(const string &tagname);
                ~DOMElement();
        string  *tagName;
        string  *id;
        DOMTokenList    *classList;
        DOMAttribute    *attributes;
};


#endif //_DOM_H_
