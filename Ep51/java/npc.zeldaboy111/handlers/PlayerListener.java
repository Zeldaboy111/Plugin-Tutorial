package npc.zeldaboy111.handlers;

import npc.zeldaboy111.Main;
import npc.zeldaboy111.NpcUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        NpcUtils.getInstance().showNpcs(e.getPlayer());
    }
    @EventHandler
    public void playerChangedWorldEvent(PlayerChangedWorldEvent e) {
        NpcUtils.getInstance().hideNpcs(e.getPlayer());
    }

}
