#ifndef _HTML_LEX_H_
#define _HTML_LEX_H_

#include "def.h"
#include "lex.h"

typedef enum HTMLTokenType {
    HT_UNDEFINED = 0,
    HT_DECLARATION,             // <!
    HT_COMMENT_BEGIN,           // <!--
    HT_ANGLE_BRACKET_BEGIN,     // <
    HT_SLASH,                   // /
    HT_COMMENT_END,             // -->
    HT_ANGLE_BRACKET_END,       // >and text until HT_ANGLE_BRACKET_BEGIN or EOF
    HT_EQUAL_SIGN,              // =
    HT_STRING,                  // "strings"
    HT_ID,                      // tagName, attrName etc.
    HT_EOF
} TokenType;

class HTMLLex: protected BaseLex {
    public:
                HTMLLex(istream &input);
        Token   *nextToken();
};

#endif //_HTML_LEX_H
