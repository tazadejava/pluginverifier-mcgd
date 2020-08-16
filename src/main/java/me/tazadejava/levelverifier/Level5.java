package me.tazadejava.levelverifier;

import me.tazadejava.parsed.ParsedClass;
import me.tazadejava.parsed.ParsedMethod;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerShearEntityEvent;

import java.util.List;

public class Level5 extends Level {

    public Level5() {
        super();

        requireListenerClass = true;
    }

    @Override
    protected boolean isLegalStatement(String line) {
        return super.isLegalStatement(line);
    }

    @Override
    protected boolean satisfiesClassFiles(List<ParsedClass> parsedClasses) {
        if(listenerClasses != null && !listenerClasses.isEmpty()) {
            for (ParsedMethod method : listenerClasses.get(0).methods) {
                if (method.annotations.contains(EventHandler.class.getCanonicalName())) {
                    if (method.parameters.size() == 1 && !method.parameters.get(0).equals(PlayerShearEntityEvent.class.getCanonicalName())) {
                        errorMessage = "You can only register the PlayerShearEntityEvent for this challenge!";
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
