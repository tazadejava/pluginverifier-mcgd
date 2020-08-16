package me.tazadejava.levelverifier;

import com.thoughtworks.qdox.model.JavaClass;
import me.tazadejava.main.PluginClass;
import me.tazadejava.parsed.ParsedClass;
import me.tazadejava.parsed.ParsedMethod;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class Level {

    protected ParsedClass pluginClass;
    protected List<ParsedClass> listenerClasses;

    protected String errorMessage;
    protected boolean hasWarnings;

    protected boolean requireListenerClass = true;

    public final boolean satisfiesConstraints(List<PluginClass> sourceFiles) {
        List<ParsedClass> parsedClasses = new ArrayList<>();
        for(PluginClass sourceFile : sourceFiles) {
            parsedClasses.add(new ParsedClass(sourceFile));

            if(!sourceFile.getSourceClass().getNestedClasses().isEmpty()) {
                for(JavaClass nestedClass : sourceFile.getSourceClass().getNestedClasses()) {
                    parsedClasses.add(new ParsedClass(sourceFile, nestedClass));
                }
            }
        }

        //special fields first
        if(!discoverSpecialClasses(parsedClasses)) {
            return false;
        }

        //check for invalid formatting, methods etc
        if(!containsNoIllegalPackages()) {
            return false;
        }
        //check for invalid formatting, methods etc
        if(!containsNoIllegalArguments(parsedClasses)) {
            return false;
        }
        if(!satisfiesClassFiles(parsedClasses)) {
            return false;
        }

        discoverWarnings();

        return true;
    }

    //Check if the main class is in a package declaration; if so, cannot exist!!!
    private boolean containsNoIllegalPackages() {
        if(!pluginClass.definedPackage.isEmpty()) {
            errorMessage = "For the plugin to compile, the class that extends JavaPlugin cannot be in a package. Please remove it from the package and try again.";
            return false;
        }

        return true;
    }

    //check for:
    /*
    Changing to op
    Changing a player's gamemode

     */
    private boolean containsNoIllegalArguments(List<ParsedClass> parsedClasses) {
        for(ParsedClass parsedClass : parsedClasses) {
            for(ParsedMethod method : parsedClass.methods) {
                for(String line : method.linesOfCode) {
                    if(!isLegalStatement(line)) {
                        return false;
                    }
                }

                //check for eventhandler elevations
                for(int i = 0; i < method.annotations.size(); i++) {
                    if(method.annotations.get(i).equals(EventHandler.class.getCanonicalName())) {
                        if (!method.annotationParameters.get(i).isEmpty()) {
                            errorMessage = "You cannot use any parameters for the EventHandler annotation (method " + method.methodName + ", class " + parsedClass.className + ")! Please remove any priorities, etc. and retry.";
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    protected boolean isLegalStatement(String line) {
        if(line.contains(".setOp(")) {
            errorMessage = "You cannot use the setOp method!";
            return false;
        }
        if(line.contains(".setGameMode(")) {
            errorMessage = "ERROR: You cannot use the setGameMode method!";
            return false;
        }

        return true;
    }

    //does not throw error, but WARNS the player for common debugging issues
    private void discoverWarnings() {
        //check for listener registration
        List<String> registeredListeners = new ArrayList<>();
        for(ParsedMethod method : pluginClass.methods) {
            for(String line : method.linesOfCode) {
                if(line.contains("getPluginManager().registerEvents(")) {
                    String className = line.split("getPluginManager\\(\\).registerEvents\\(")[1].split(",")[0].trim();
                    registeredListeners.add(className.substring(4, className.indexOf("(")));
                }
            }
        }

        for(ParsedClass listener : listenerClasses) {
            if(registeredListeners.contains(listener.className)) {
                registeredListeners.remove(listener.className);
            } else {
                System.out.println("WARNING: Did you forget to register the listener class " + listener.className + " in your JavaPlugin class?");
                hasWarnings = true;
            }
        }

        //check listener for missing eventhandlers
        for(ParsedClass listenerClass : listenerClasses) {
            for (ParsedMethod method : listenerClass.methods) {
                if(method.parameters.size() == 1 && method.returnType.equals("void") && method.parameters.get(0).startsWith("org.bukkit.event")) {
                    if(!method.annotations.contains(EventHandler.class.getCanonicalName())) {
                        System.out.println("WARNING: One of your events (" + method.methodName + ") may not be registered in the class " + listenerClass.className + "! Did you forget to annotate the method with @EventHandler?");
                        hasWarnings = true;
                    }
                }
            }
        }
    }

    private boolean discoverSpecialClasses(List<ParsedClass> parsedClasses) {
        pluginClass = null;
        listenerClasses = new ArrayList<>();

        for(ParsedClass parsedClass : parsedClasses) {
            if(parsedClass.superclassName.equals(JavaPlugin.class.getCanonicalName())) {
                if(pluginClass != null) {
                    errorMessage = "You can only have one JavaPlugin class.";
                    return false;
                }

                pluginClass = parsedClass;
            }

            if(parsedClass.implementations.contains(Listener.class.getCanonicalName())) {
                listenerClasses.add(parsedClass);
            }
        }

        if(pluginClass == null) {
            errorMessage = "Your JavaPlugin class is missing!";
            return false;
        }
        if(requireListenerClass && listenerClasses.isEmpty()) {
            errorMessage = "You are missing a listener class!";
            return false;
        }

        return true;
    }

    protected abstract boolean satisfiesClassFiles(List<ParsedClass> parsedClasses);

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean hasWarnings() {
        return hasWarnings;
    }
}
