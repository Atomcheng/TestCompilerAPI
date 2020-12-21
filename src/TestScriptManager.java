import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;

public class TestScriptManager {
    public static void main(String[] args) throws ScriptException, FileNotFoundException {
        ScriptEngineManager manager = new ScriptEngineManager();
        //获取Nashorn引擎
        ScriptEngine nashorn = manager.getEngineByExtension("js");
        //使用默认的输入输出流，执行脚本
        nashorn.eval("print(\"我是一名优秀程序员\")");
        nashorn.eval(new InputStreamReader(new FileInputStream("Resources/Test.js")));

        //重定向输出流
        nashorn.getContext().setWriter(new OutputStreamWriter(new FileOutputStream("./Resources/out.txt")));
        nashorn.eval("print(\"我是一名优秀程序员\")");

    }
}
