package me.tazadejava.levelverifier;

import me.tazadejava.parsed.ParsedClass;

import java.util.List;

public class Level3 extends Level {

    public Level3() {
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
        if(line.contains(".addPotionEffect(")) {
            errorMessage = "You can't add potion effects for this level!";
            return false;
        }
        if(line.contains(".addPotionEffects(")) {
            errorMessage = "You can't add potion effects for this level!";
            return false;
        }
        if(line.contains(".dropItem(") || line.contains(".dropItemNaturally(")) {
            errorMessage = "You can't drop items for this level!";
            return false;
        }
        if(line.contains(".setItemOnCursor(") || line.contains(".setItemInHand(") || line.contains(".getOpenInventory(")) {
            errorMessage = "You can't give the player items for this level!";
            return false;
        }
        if((line.contains(".getOpenInventory("))) {
            errorMessage = "You can only add to chest inventories for this level!";
            return false;
        }
        if(line.contains(".getInventory()")) {
            boolean allowed = false;
            if(line.contains("().setHelmet(") || line.contains("().setChestplate(") || line.contains("().setLeggings(") || line.contains("().setBoots(")) {
                allowed = true;
            } else if(line.contains("().getHelmet()") || line.contains("().getChestplate()") || line.contains("().getLeggings()") || line.contains("().getBoots()")) {
                allowed = true;
            }
            
            if(!allowed) {
                errorMessage = "You can only add to chest inventories for this level!";
                return false;
            }
        }

        return super.isLegalStatement(line);
    }

    @Override
    protected boolean satisfiesClassFiles(List<ParsedClass> parsedClasses) {
        if(parsedClasses.size() != 3) {
            errorMessage = "You can only use three java files.";
            return false;
        }

        return true;
    }
}
