#ifndef _CSS_LEX_H_
#define _CSS_LEX_H_

#include "def.h"
#include "lex.h"

typedef enum CSSTokenType {
    CT_UNDEFINED = 0,
    CT_AT_SIGN,                 // @
    CT_LINE_COMMENT,            // // and comment until EOL
    CT_COMMENT_BEGIN,           // /*
    CT_COMMENT_END,             // */
    CT_BRACE_BEGIN,             // {
    CT_BRACE_END,               // }
    CT_COLON,                   // :
    CT_SEMICOLON,               // ;
    CT_BRACKETS,                // (and context inside)
    CT_STRING,                  // "and context inside" or 'and context inside'
    CT_COMMA,                   // ,
    CT_COLOR,                   // #CCC or #CCCCCC
    CT_ID,                      // a short text
    CT_EOF
} CSSTokenType;

class CSSLex: protected BaseLex {
    public:
                CSSLex(istream &input);
        Token   *nextToken();
};

#endif //_CSS_LEX_H_
