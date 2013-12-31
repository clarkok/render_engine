#ifndef _LEX_H_
#define _LEX_H_

#include <istream>
#include "def.h"

using namespace std;

// Token
class Token {
    public:
        Token();
        ~Token();
        Token(u32 type, Value *value);
        u32     t_type;
        Value   *t_value;
};

// Base Class of Lex
class BaseLex {
    public:
                BaseLex(istream &input);
        virtual Token   *nextToken();
    protected:
        istream     &l_is;
};

#endif // _LEX_H_
