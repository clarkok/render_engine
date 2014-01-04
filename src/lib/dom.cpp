#include <string>
#include <map>
#include <vector>
#include <iterator>
#include "def.h"
#include "tree.h"
#include "dom.h"

DOMElement::DOMElement(const string &tagname):
    tagName(new string(tagname)),
    id(new string("")),
    classList(new DOMTokenList),
    attributes(new DOMAttribute) {
}

DOMElement::~DOMElement() {
    delete tagName;
    delete id;
    delete classList;
    delete attributes;
}
