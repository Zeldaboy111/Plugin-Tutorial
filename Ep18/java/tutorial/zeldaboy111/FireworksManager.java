package tutorial.zeldaboy111;

import net.minecraft.server.v1_15_R1.EntityFireworks;
import net.minecraft.server.v1_15_R1.ItemStack;
import net.minecraft.server.v1_15_R1.Items;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworksManager {
    private int lifeTime;
    private Location location;
    private ItemStack fireworks;

    public FireworksManager() { initFireworks(); }
    public FireworksManager(Player p) {
        this.location = p.getLocation();
        initFireworks();
    }
    public FireworksManager(Location location) {
        this.location = location;
        initFireworks();
    }
    private void initFireworks() {
        fireworks = new ItemStack(Items.FIREWORK_ROCKET, 1);
        FireworkMeta meta = (FireworkMeta) CraftItemStack.asCraftMirror(fireworks).getItemMeta();
        meta.clearEffects();
        // Add effects
        // Flight duration
        meta.setPower(1);
        CraftItemStack.setItemMeta(fireworks, meta);
    }

    private EntityFireworks spawnNewFireworkEntity() {
        EntityFireworks entity = new EntityFireworks(((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ(), fireworks) {
            public void m() {
                this.world.broadcastEntityEffect(this, (byte)17);
                die();
            }
        };

        return entity;
    }

    public void spawn() {
        if(this.location == null) throw new NullPointerException("location");
        else spawn(this.location);
    }
    public void spawn(Location loc) {
        EntityFireworks entity = spawnNewFireworkEntity();
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
    }
}
