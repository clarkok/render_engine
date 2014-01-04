#include <iterator>
#include <iostream>
#include <fstream>
#include <string>
#include "tree.h"
#include "html_lex.h"
#include "html_parser.h"

using namespace std;

ifstream fin("/tmp/test.html");

HTMLLex lex(fin);
HTMLParser hp(lex);

int main () {
    DOMElement *d;

    while ((d = hp.parse())) {
        cout << *d->tagName;
        if ((*d->tagName) == "__TEXT__") {
            cout << (*d->attributes)["text"];
        }
        cout << endl;
    }

    return 0;
}
