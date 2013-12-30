#include "lex.h"

Token::Token():t_type(0), t_value(new Value(0, NULL)) {
}

Token::Token(u32 type, Value *value):t_type(type), t_value(value) {
}

BaseLex::BaseLex(istream &input):l_is(input) {
}

Token *BaseLex::nextToken() {
    return new Token();
}
