package jjc.code;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

/**
 * 用于持有源代码
 * @author jjc
 */
public class StringSource extends SimpleJavaFileObject {
    private String code;
    /**
     * Construct a SimpleJavaFileObject of the given kind and with the
     * given URI.
     *
     * @param name  the name for this file name
     * @param code the content of this source file
     */
    public StringSource(String name, String code) {
        super(URI.create("string:///" + name.replace('.', '/') + ".java"), Kind.SOURCE);
        this.code = code;
    }

    public CharSequence getCharContent(boolean ignoreEncoding){
        return code;
    }
}
