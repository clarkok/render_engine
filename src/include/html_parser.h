#ifndef _HTML_PRASER_H_
#define _HTML_PRASER_H_

#include <string>
#include <map>
#include "def.h"
#include "lex.h"
#include "tree.h"
#include "dom.h"
#include "html_lex.h"
#include "html_parser.h"

typedef enum HTMLTagType {
    HTG_UNDEFINED = 0,
    HTG_DESC,
    HTG_COMMENT,
    HTG_SELF_CLOSED,
    HTG_NORMAL,
    HTG_CLOSE,
    HTG_TEXT,
    HTG_EOF
} HTMLTagType;

class HTMLTagDescriptor {
    public:
        HTMLTagDescriptor(u32 type=HTG_UNDEFINED, DOMElement *element=NULL);
        u32         htg_type;
        DOMElement  *htg_element;
};

class HTMLParser {
    private:
        BaseLex             &h_lex;
        HTMLTagDescriptor   *parseElement();
    public:
                    HTMLParser(BaseLex &input);
                    ~HTMLParser();
        DOMElement  *parse();
};

#endif //_HTML_PRASER_H_
