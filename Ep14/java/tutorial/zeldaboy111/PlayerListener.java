package tutorial.zeldaboy111;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import tutorial.zeldaboy111.YML.ymlHandler;

public class PlayerListener implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        e.setJoinMessage("§6" + p.getPlayerListName() + " §ehas joined!");
        ymlHandler.setLogin(p);
    }

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent e) {
        if(e.getClickedInventory() == null) return;
        String name = e.getView().getTitle();

        if(name.equals("Test Inventory")) CustomInventory.instance.click(e);
    }

}
