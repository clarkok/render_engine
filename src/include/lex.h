#ifndef _LEX_H_
#define _LEX_H_

#include <istream>
#include <stack>
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
                Token   *nextToken();
                void    pushBack(Token *t);
    protected:
        virtual     Token   *parseToken();
        istream     &l_is;
    private:
        stack<Token*>   push_back_stack;
};

#endif // _LEX_H_
