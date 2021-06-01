package npc.zeldaboy111.handlers;

import npc.zeldaboy111.INpc;
import npc.zeldaboy111.NpcUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerListener implements Listener {
    private static final HashMap<UUID, Long> playerDelays = new HashMap<>();


    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        NpcUtils.getInstance().showNpcs(e.getPlayer());
    }
    @EventHandler
    public void playerChangedWorldEvent(PlayerChangedWorldEvent e) {
        NpcUtils.getInstance().hideNpcs(e.getPlayer());
    }
    @EventHandler
    public void playerEntityInteract(PlayerInteractAtEntityEvent e) {
        if(e.getPlayer() == null || e.getRightClicked() == null) return;
        INpc npc;
        if((npc = NpcUtils.getInstance().getNpcFromEntityId(e.getRightClicked().getEntityId())) != null) e.setCancelled(tryExecuteNpcCommands(e.getPlayer(), npc));
    }
    private Boolean tryExecuteNpcCommands(Player p, INpc npc) {
        if(!lastNpcInteractDelayIs(p.getUniqueId(), 500)) return false;
        npc.tryExecuteNpcCommands(p);
        return true;
    }
    private Boolean lastNpcInteractDelayIs(UUID uuid, long minimumDelay) {
        if(playerDelays.containsKey(uuid)) {
            if(System.currentTimeMillis()-playerDelays.get(uuid) >= minimumDelay) {
                playerDelays.replace(uuid, System.currentTimeMillis());
                return true;
            }
            return false;
        }
        playerDelays.put(uuid, System.currentTimeMillis());
        return true;
    }


}
