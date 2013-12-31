#include <iostream>
#include <fstream>
#include <string>
#include "def.h"
#include "html_lex.h"
#include "css_lex.h"

using namespace std;

ifstream fin("/tmp/tmp.css");

CSSLex lex(fin);

int main () {
    Token *t;
    while ((t = lex.nextToken()), t->t_type != CT_EOF) {
        cout << "Type: " << t->t_type << "\t\t" << *((string*)t->t_value->get()) << endl;
        delete t;
    }
    return 0;
}
