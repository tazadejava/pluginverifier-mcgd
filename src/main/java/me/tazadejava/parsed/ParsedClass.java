package me.tazadejava.parsed;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaType;
import me.tazadejava.main.PluginClass;

import java.util.ArrayList;
import java.util.List;

public class ParsedClass {

    public PluginClass pluginClass;

    public String className, definedPackage;
    public String superclassName;
    public List<String> implementations;

    public List<ParsedMethod> methods;
    
    private ParsedClass(JavaClass javaClass) {
        className = javaClass.getName();
        superclassName = javaClass.getSuperClass() == null ? "" : javaClass.getSuperClass().getCanonicalName();
        implementations = new ArrayList<>();

        definedPackage = javaClass.getPackageName();

        for(JavaType implementation : javaClass.getImplements()) {
            implementations.add(implementation.getCanonicalName());
        }

        methods = new ArrayList<>();

        for(JavaMethod method : javaClass.getMethods()) {
            methods.add(new ParsedMethod(this, method));
        }
    }

    public ParsedClass(PluginClass pluginClass) {
        this(pluginClass.getSourceClass());
        this.pluginClass = pluginClass;
    }

    public ParsedClass(PluginClass pluginClass, JavaClass subclass) {
        this(subclass);
        this.pluginClass = pluginClass;
    }
}
