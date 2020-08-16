package me.tazadejava.main;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

import java.io.File;
import java.io.IOException;

public class PluginClass {

    private String packageName;
    private File classFile;

    private JavaSource source;
    private JavaClass sourceClass;

    public PluginClass(String packageName, File classFile) {
        this.packageName = packageName;
        this.classFile = classFile;
    }

    public JavaSource getSource() {
        return source;
    }

    public JavaClass getSourceClass() {
        return sourceClass;
    }

    public void parse() {
        try {
            JavaProjectBuilder builder = new JavaProjectBuilder();

            source = builder.addSource(classFile);
            sourceClass = source.getClasses().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
