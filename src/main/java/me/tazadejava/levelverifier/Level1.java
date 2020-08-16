package me.tazadejava.levelverifier;

import me.tazadejava.parsed.ParsedClass;

import java.util.List;

public class Level1 extends Level {

    @Override
    protected boolean isLegalStatement(String line) {
        if(line.contains(".setFlying(")) {
            errorMessage = "You can't use the setFlying method for this level... That's too easy!";
            return false;
        }

        return super.isLegalStatement(line);
    }

    @Override
    protected boolean satisfiesClassFiles(List<ParsedClass> parsedClasses) {
        if(parsedClasses.size() != 2) {
            errorMessage = "You can only use two java files.";
            return false;
        }

        return true;
    }
}
