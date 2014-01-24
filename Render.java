import html.HTMLToken;
import html.HTMLTokener;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;

public class Render {
    public static void main(String[] args) {
        try {
            HTMLTokener tokener = new HTMLTokener(
                    new FileReader(new File("/tmp/dashboard")));
            HTMLToken t = null;
            while (true) {
                t = tokener.nextToken();
                if (t.getType() == HTMLToken.HTMLTokenType.HT_EOF) 
                    break;
                System.out.print(t.getType().toString());
                System.out.print('\t');
                System.out.println(t.getValue());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

    }
}
