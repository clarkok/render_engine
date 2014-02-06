package html.dom;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import utils.tree.TreeNode;
import html.HTMLToken;
import html.HTMLTokener;

public class DOMNode extends TreeNode {
    public enum DOMNodeType {
        DNT_UNDEFINED,
        DNT_TAG,
        DNT_CLOSE,
        DNT_TEXT,
        DNT_COMMENT,
        DNT_DEFINE;

        @Override
        public String toString() {
            switch(this) {
                case DNT_UNDEFINED:
                    return "DNT_UNDEFINED";
                case DNT_TAG:
                    return "DNT_TAG";
                case DNT_CLOSE:
                    return "DNT_CLOSE";
                case DNT_TEXT:
                    return "DNT_TEXT";
                case DNT_COMMENT:
                    return "DNT_COMMENT";
                case DNT_DEFINE:
                    return "DNT_DEFINE";
                default:
                    return "DNT_UNKNOWN";
            }
        }
    }

    public DOMNodeType type;
    public String tag_name;
    public String id;
    public String name;
    public String class_name;
    public HashMap<String, String> attributes;
    public String text;

    public DOMNode() {
        this.type = DOMNodeType.DNT_UNDEFINED;
        this.tag_name = new String();
        this.id = new String();
        this.name = new String();
        this.class_name = new String();
        this.attributes = new HashMap<String, String>();
        this.text = new String();
    }

    @Override
    public String toString() {
        if (this.type == DOMNodeType.DNT_TEXT ||
                this.type == DOMNodeType.DNT_DEFINE ||
                this.type == DOMNodeType.DNT_COMMENT) {
            return this.text;
        }
        else {
            String ret = new String("<");
            if (this.type == DOMNodeType.DNT_CLOSE) 
                ret += '/';
            ret += this.tag_name;

            Set<String> keys = this.attributes.keySet();
            for (String k : keys) {
                ret += ' ' + k + '=' + '"' + this.attributes.get(k) + '"';
            }

            ret += '>';

            return ret;
        }
    }
}
