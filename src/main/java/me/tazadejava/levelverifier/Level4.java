package me.tazadejava.levelverifier;

import me.tazadejava.parsed.ParsedClass;

import java.util.List;

public class Level4 extends Level {

    public Level4() {
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
        if(line.contains("System.out.print")) {
            errorMessage = "You cannot use system printouts for this level! Try to use another way to output to the player (ex: boss bar, scoreboard, title, subtitle, action bar...)!";
            return false;
        }
        if(line.contains("Bukkit.broadcast")) {
            errorMessage = "You cannot use broadcasts for this level! Try to use another way to output to the player (ex: boss bar, scoreboard, title, subtitle, action bar...)!";
            return false;
        }
        if(line.contains(".sendMessage(")) {
            errorMessage = "You cannot send messages to the player for this level! Try to use another way to output to the player (ex: boss bar, scoreboard, title, subtitle, action bar...)!";
            return false;
        }

        return super.isLegalStatement(line);
    }

    @Override
    protected boolean satisfiesClassFiles(List<ParsedClass> parsedClasses) {
        return true;
    }
}
