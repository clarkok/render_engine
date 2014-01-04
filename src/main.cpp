#include <iterator>
#include <iostream>
#include <fstream>
#include <string>
#include "tree.h"
#include "html_lex.h"

using namespace std;

ifstream fin("/tmp/test.html");

HTMLLex lex(fin);

int main () {
    Token *t;

    while ((t = lex.nextToken()), t->t_type != HT_EOF) {
        cout << t->t_type << '\t' << *(string*)t->t_value->get() << endl;
        delete t;
    }

    return 0;
}
