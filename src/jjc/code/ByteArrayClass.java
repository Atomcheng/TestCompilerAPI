package jjc.code;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * 用于持有源文件编译后的字节码。
 * @author jjc
 */
public class ByteArrayClass extends SimpleJavaFileObject {
    private ByteArrayOutputStream out;
    public ByteArrayClass(String name){
        super(URI.create("byts:///" + name.replace('.', '/')+ ".class"), Kind.CLASS);
    }

    public byte[] getCode(){
        return out.toByteArray();
    }

    public OutputStream openOutputStream(){
        out = new ByteArrayOutputStream();
        return out;
    }
}
