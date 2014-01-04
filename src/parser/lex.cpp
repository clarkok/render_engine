#include "lex.h"

Token::Token():t_type(0), t_value(new Value(0, NULL)) {
}

Token::~Token() {
    if (t_value) {
        delete t_value;
    }
}

Token::Token(u32 type, Value *value):t_type(type), t_value(value) {
}

BaseLex::BaseLex(istream &input):l_is(input) {
}

Token *BaseLex::parseToken() {
    return new Token;
}

Token *BaseLex::nextToken() {
    if (push_back_stack.empty())
        return parseToken();
    else {
        Token *t = push_back_stack.top();
        push_back_stack.pop();
        return t;
    }
}

void BaseLex::pushBack(Token *t) {
    push_back_stack.push(t);
}

