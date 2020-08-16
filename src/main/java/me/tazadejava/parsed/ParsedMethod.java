package me.tazadejava.parsed;

import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParsedMethod {

    public ParsedClass parsedClass;

    public List<Map<String, Object>> annotationParameters;
    public List<String> annotations, parameters, linesOfCode;
    public String methodName, returnType;

    public ParsedMethod(ParsedClass parsedClass, JavaMethod method) {
        this.parsedClass = parsedClass;

        methodName = method.getName();
        returnType = method.getReturnType().getCanonicalName();

        annotations = new ArrayList<>();
        annotationParameters = new ArrayList<>();

        for(JavaAnnotation annotation : method.getAnnotations()) {
            annotationParameters.add(annotation.getNamedParameterMap());
            annotations.add(annotation.getType().getCanonicalName());
        }

        parameters = new ArrayList<>();

        for(JavaParameter parameter : method.getParameters()) {
            parameters.add(parameter.getType().getCanonicalName());
        }

        linesOfCode = new ArrayList<>();

        boolean inCommentBlock = false;
        for(String line : method.getSourceCode().split(";")) {
            line = line.trim().replaceAll("\t", "");
            if(!line.isEmpty() && !line.startsWith("//")) {
                linesOfCode.add(line);
            }
        }
    }
}
