package me.tazadejava.levelverifier;

import me.tazadejava.parsed.ParsedClass;

import java.util.List;

public class Level6 extends Level {

    public Level6() {
        super();

        requireListenerClass = false;
    }

    @Override
    protected boolean isLegalStatement(String line) {
        if(line.contains(".setFlying(")) {
            errorMessage = "You can't use the setFlying method for this level!";
            return false;
        }
        if(line.contains(".setDifficulty(")) {
            errorMessage = "You can't change the world difficulty for this level!";
            return false;
        }
        if(line.toLowerCase().trim().contains("\"difficulty peaceful\"")) {
            errorMessage = "You can't change the world difficulty for this level!";
            return false;
        }
        if(line.toLowerCase().trim().contains("\"difficulty easy\"")) {
            errorMessage = "You can't change the world difficulty for this level!";
            return false;
        }
        if(line.contains(".setHealth(")) {
            errorMessage = "You cannot change the health of an entity for this level!";
            return false;
        }
        if(line.contains(".setMaxHealth(")) {
            errorMessage = "You cannot change the health of an entity for this level!";
            return false;
        }
        if(line.replace(" ", "").contains(".getAttribute(Attribute.GENERIC_MAX_HEALTH).set")) {
            errorMessage = "You cannot change the health of an entity for this level!";
            return false;
        }
        if(line.contains(".remove(")) {
            errorMessage = "You cannot simply delete the entities for this level! That's too easy...";
            return false;
        }

        return super.isLegalStatement(line);
    }

    @Override
    protected boolean satisfiesClassFiles(List<ParsedClass> parsedClasses) {
        return true;
    }
}
