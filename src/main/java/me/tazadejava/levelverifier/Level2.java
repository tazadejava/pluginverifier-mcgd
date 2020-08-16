package me.tazadejava.levelverifier;

import me.tazadejava.parsed.ParsedClass;
import me.tazadejava.parsed.ParsedMethod;
import org.bukkit.event.EventHandler;

import java.util.List;

public class Level2 extends Level {

    public Level2() {
        super();

        requireListenerClass = false;
    }

    @Override
    protected boolean isLegalStatement(String line) {
        if(line.contains(".setFlying(")) {
            errorMessage = "You can't use the setFlying method for this level!";
            return false;
        }

        return super.isLegalStatement(line);
    }

    @Override
    protected boolean satisfiesClassFiles(List<ParsedClass> parsedClasses) {
        if(parsedClasses.size() != 3) {
            errorMessage = "You can only use two java files.";
            return false;
        }

        int eventCount = 0;
        if(listenerClasses != null && !listenerClasses.isEmpty()) {
            for (ParsedMethod method : listenerClasses.get(0).methods) {
                if (method.annotations.contains(EventHandler.class.getCanonicalName())) {
                    eventCount++;
                }
            }
        }

        if(eventCount > 0) {
            errorMessage = "You cannot register any events for this challenge! Use commands instead.";
            return false;
        }

        return true;
    }
}
