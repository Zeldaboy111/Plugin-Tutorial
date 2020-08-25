package tutorial.zeldaboy111;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class CustomInventory {

    public static final CustomInventory instance = new CustomInventory();
    private final ItemStack stack = createStack(Material.COBBLESTONE, "§7Cobblestone", new String[] {"§1", "§8Click to get message"});

    public void open(Player p) {
        Inventory i = Bukkit.getServer().createInventory(null, 9, "Test Inventory");
        i.setItem(0, stack);

        p.openInventory(i);
    }

    public void click(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        e.setCancelled(true);

        int slot = e.getSlot();
        ItemStack stack = e.getClickedInventory().getItem(slot);
        if(stack == null || stack.getType() == Material.AIR) return;

        p.closeInventory();
        if(slot == 0 && stack == this.stack) {
            p.sendMessage("You clicked Cobblestone");
        }
    }

    private ItemStack createStack(Material mat, String name, String[] lore) {
        ItemStack stack = new ItemStack(mat);
        ItemMeta meta = stack.getItemMeta();
        if(name != null) meta.setDisplayName(name);
        if(lore != null) {
            ArrayList<String> newLore = new ArrayList<String>();
            for(String s : lore) { newLore.add(s); }

            meta.setLore(newLore);
        }

        stack.setItemMeta(meta);
        return stack;
    }

}
