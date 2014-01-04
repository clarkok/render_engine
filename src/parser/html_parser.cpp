#include <string>
#include <map>
#include "def.h"
#include "tree.h"
#include "html_parser.h"

HTMLTagDescriptor::HTMLTagDescriptor(u32 type, DOMElement *element):
    htg_type(type),
    htg_element(element) {
}

HTMLParser::HTMLParser(BaseLex &input): h_lex(input) {
}

HTMLParser::~HTMLParser() {
}

HTMLTagDescriptor *HTMLParser::parseElement() {
    Token   *t = h_lex.nextToken();
    DOMElement *d = NULL;

    while (t->t_type == HT_COMMENT_BEGIN) {
        while (t->t_type != HT_COMMENT_END) {
            if (t->t_type == HT_EOF)
                return new HTMLTagDescriptor(HTG_EOF);
            delete t;
            t = h_lex.nextToken();
        }
        delete t;
        t = h_lex.nextToken();
    }

    switch (t->t_type) {
        case HT_DECLARATION:
        case HT_ANGLE_BRACKET_BEGIN:
            {
                u32 tagType = (t->t_type == HT_DECLARATION) ? HTG_DESC : HTG_NORMAL ;
                delete t;
                t = h_lex.nextToken();
                d = new DOMElement(V2S(t->t_value));
                if (t->t_type == HT_SLASH) {
                    delete t;
                    t = h_lex.nextToken();
                    d = new DOMElement(V2S(t->t_value));
                    delete t;
                    if (t->t_type == HT_ID) {
                        return new HTMLTagDescriptor(HTG_CLOSE, d);
                    }
                    else {
                        return new HTMLTagDescriptor(HTG_UNDEFINED, d);
                    }
                }
                if (t->t_type != HT_ID) {
                    delete t;
                    return new HTMLTagDescriptor(HTG_UNDEFINED, d);
                }
                delete t;
                while ((t = h_lex.nextToken()), (t->t_type != HT_ANGLE_BRACKET_END)) {
                    switch (t->t_type) {
                        case HT_EOF:
                            delete t;
                            return new HTMLTagDescriptor(HTG_EOF, d);
                            break;
                        case HT_ID:
                            {
                                string key(V2S(t->t_value));
                                delete t;
                                if ((t = h_lex.nextToken()), t->t_type != HT_EQUAL_SIGN) {
                                    (*d->attributes)[key] = "";
                                    h_lex.pushBack(t);
                                    t = NULL;
                                    break;
                                }
                                delete t;
                                t = h_lex.nextToken();
                                switch (t->t_type){
                                    case HT_STRING:
                                        {
                                            string value(V2S(t->t_value));
                                            value = value.substr(1, value.length() - 2);
                                            (*d->attributes)[key] = value;
                                        }
                                        break;
                                    case HT_ID:
                                        {
                                            string value(V2S(t->t_value));
                                            (*d->attributes)[key] = value;
                                        }
                                        break;
                                    default:
                                        {
                                            h_lex.pushBack(t);
                                            t = NULL;
                                            (*d->attributes)[key] = "";
                                        }
                                        break;
                                }
                            }
                            break;
                        case HT_SLASH:
                            delete t;
                            return new HTMLTagDescriptor(HTG_SELF_CLOSED, d);
                            break;
                        default:
                            delete t;
                            return new HTMLTagDescriptor(HTG_UNDEFINED, d);
                    }
                    delete t;
                }
                if (t->t_type == HT_ANGLE_BRACKET_END)
                    h_lex.pushBack(t);
                else {
                    delete t;
                }
                return new HTMLTagDescriptor(tagType, d);
            }
            break;
        case HT_ANGLE_BRACKET_END:
            {
                string value = V2S(t->t_value);
                d = new DOMElement("__TEXT__");
                (*d->attributes)["text"] = value.substr(1, value.length() - 1);
                return new HTMLTagDescriptor(HTG_TEXT, d);
            }
            break;
        case HT_EOF:
            {
                delete t;
                return new HTMLTagDescriptor(HTG_EOF, d);
            }
            break;
        default:
            {
                d = new DOMElement(V2S(t->t_value));
                return new HTMLTagDescriptor(HTG_EOF, d);
            }
            break;
    }
    
    return new HTMLTagDescriptor;
}

DOMElement *HTMLParser::parse() {
    HTMLTagDescriptor *htg = parseElement();
    DOMElement *d = htg->htg_element;
    delete htg;
    return d;
}
