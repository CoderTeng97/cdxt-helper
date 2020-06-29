package com.cdxt.common.utils;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaUtil {

    public static void compile() throws IOException {
        String filePath = "F:\\svn\\1274755482485125120\\";//java文件夹路径
        String classPath = "F:\\svn\\1274755482485125120_class";//class文件存放路径
        File classFile = new File(classPath);
        if(!classFile.exists()){
            classFile.mkdirs();
        }
        //把文件夹下所有java文件完整路径存到files数组中，不用考虑引用关系//在ZGS.java中引用了LIST和INFO作为引用数据对象
        String[] files = {filePath+"XmlUtil.java"};
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null,null,null);
        Iterable<? extends JavaFileObject> javaFileObjects =
                manager.getJavaFileObjectsFromStrings(Arrays.asList(files));
        //options就是指定编译输入目录，与我们命令行写javac -d C://是一样的
        List<String> options = new ArrayList<String>();
        options.add("-d");
        options.add(classPath);
        JavaCompiler.CompilationTask task = compiler.getTask(null,manager,null,options,null,javaFileObjects);
        task.call();
        manager.close();
    }

    public static void main(String[] args) {
        try {
            compile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}