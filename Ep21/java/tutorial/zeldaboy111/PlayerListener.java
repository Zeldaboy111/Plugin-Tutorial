package tutorial.zeldaboy111;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import tutorial.zeldaboy111.Inventory.CustomInventory;
import tutorial.zeldaboy111.YML.ymlHandler;

public class PlayerListener implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        e.setJoinMessage("§6" + p.getPlayerListName() + " §ehas joined!");
        ymlHandler.setLogin(p);
    }
    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent e) {
        if(e.getMessage() == null || e.getMessage().equals("")) return;
        String message = e.getMessage();
        e.setMessage(new String(Crypto.encrypt(message)));

    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e) {
        if(e.getClickedInventory() == null) return;
        String name = e.getView().getTitle();

        if(name.equals("Test Inventory")) CustomInventory.instance.click(e);
    }

    @EventHandler
    public void moveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();

        //p.playSound(loc, Sound.BLOCK_BAMBOO_HIT, 14, 0);
        p.spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 5, 0, 0, 0, new Particle.DustOptions(Color.RED, 1));
    }


}
