package html;

public class HTMLToken {
    public enum HTMLTokenType {
        HT_UNDEFINED,
        HT_NAME,
        HT_STRING,
        HT_COMMENT,
        HT_TEXT,
        HT_DEFINE,
        HT_LEFT_ANGLE_BRACKET,
        HT_RIGHT_ANGLE_BRACKET,
        HT_SLASH,
        HT_EQUAL,
        HT_EOF;

        @Override
        public String toString() {
            switch (this) {
                case HT_UNDEFINED:
                    return "HT_UNDEFINED";
                case HT_NAME:
                    return "HT_NAME";
                case HT_STRING:
                    return "HT_STRING";
                case HT_COMMENT:
                    return "HT_COMMENT";
                case HT_TEXT:
                    return "HT_TEXT";
                case HT_DEFINE:
                    return "HT_DEFINE";
                case HT_LEFT_ANGLE_BRACKET:
                    return "HT_LEFT_ANGLE_BRACKET";
                case HT_RIGHT_ANGLE_BRACKET:
                    return "HT_RIGHT_ANGLE_BRACKET";
                case HT_SLASH:
                    return "HT_SLASH";
                case HT_EQUAL:
                    return "HT_EQUAL";
                case HT_EOF:
                    return "HT_EOF";
                default:
                    return "HT_UNKNOW";
            }
        }
    }

    private HTMLTokenType type;
    private String value;

    public HTMLToken(HTMLTokenType t, String v) {
        this.type = t;
        this.value = new String(v);
    }

    public HTMLToken(HTMLTokenType t) {
        this.type = t;
        this.value = new String();
    }

    public HTMLTokenType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String v) {
        this.value = new String(v);
    }
}
