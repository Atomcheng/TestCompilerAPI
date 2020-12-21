package jjc.main;
import jjc.code.ByteArrayClass;
import jjc.code.ByteArrayClassLoader;
import jjc.code.StringSource;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TestCompiler {
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        //导入java源文件为字符串。
        List<StringSource> sources = new LinkedList<>();
        Scanner in = new Scanner(new FileInputStream("./src/jjc/code/CodeTest.txt"), "UTF-8");
        StringBuilder aStringBuilder = new StringBuilder();
        while (in.hasNextLine())
            aStringBuilder.append(in.nextLine());
        String code = aStringBuilder.toString();
        System.out.println(code);   //输出java源文件代码

        //通过StringSource对象持有源代码
        sources.add(new StringSource("jjc.code.CodeTest", code));

        //发起编译任务，对字符串类型的code进行编译。
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();   //获取系统Java编译器。
        //OutputStream outClass = new FileOutputStream("./compilerOut/CodeTest.class");
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, null,
                null, null, sources);   //将编译的类文件输出到默认的工作目录
        task.call();    //对源文件进行编译

        //配置文件管理器，并将编译后的类文件直接加载至JVM。
        List<ByteArrayClass> classes = new LinkedList<>();
        StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, null, null);
        JavaFileManager fileManager = new ForwardingJavaFileManager<JavaFileManager>(stdFileManager){
            public JavaFileObject getJavaFileForOutput(Location location, String className,
                                                       JavaFileObject.Kind kind, FileObject sibling) throws IOException {
                if(kind == JavaFileObject.Kind.CLASS){
                    ByteArrayClass outFile = new ByteArrayClass(className);
                    classes.add(outFile);
                    return outFile;
                }
                else {
                    return super.getJavaFileForOutput(location, className, kind, sibling);
                }
            }
        };
        JavaCompiler.CompilationTask task1 = compiler.getTask(null, fileManager, null,
                null, null, sources);
        task1.call();   //对源文件进行编译
        ByteArrayClassLoader loader = new ByteArrayClassLoader(classes);
        Class<?> cl = Class.forName("jjc.code.CodeTest", true, loader);     //加载编译后的源件

        //使用反射将CodeTest里面s的字段读取出来，进行显示。
        Constructor constructor = cl.getConstructor();
        Object obj = constructor.newInstance();
        Field f = cl.getField("s");
        System.out.println(f.get(obj));
    }
}
