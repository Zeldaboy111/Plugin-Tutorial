package tutorial.zeldaboy111.multiversion;

import org.bukkit.entity.Player;

public interface Minecraft {

    /**
     * Sends a tablist to the player
     * @param player
     * @param header
     * @param footer
     */
    void sendTablist(Player player, String header, String footer);



}
