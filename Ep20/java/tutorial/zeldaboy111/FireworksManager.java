package tutorial.zeldaboy111;

import com.sun.javaws.exceptions.InvalidArgumentException;
import net.minecraft.server.v1_15_R1.EntityFireworks;
import net.minecraft.server.v1_15_R1.ItemStack;
import net.minecraft.server.v1_15_R1.Items;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
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
    public FireworksManager(Location location, int lifeTimeInTicks) {
        this.location = location;
        this.lifeTime = lifeTimeInTicks;
        initFireworks();
    }
    public FireworksManager(Location location, int lifeTimeInTicks, int fireworkEffectId) {
        this.location = location;
        this.lifeTime = lifeTimeInTicks;
        initFireworks(fireworkEffectId);
    }
    private void initFireworks() { initFireworks(0); }
    private void initFireworks(int fireworkEffectId) {
        try {
            fireworks = new ItemStack(Items.FIREWORK_ROCKET, 1);
            FireworkMeta meta = (FireworkMeta) CraftItemStack.asCraftMirror(fireworks).getItemMeta();
            meta.clearEffects();
            meta.addEffect(getFireworkEffect(fireworkEffectId));
            meta.setPower(1);
            CraftItemStack.setItemMeta(fireworks, meta);
        } catch(IllegalArgumentException e) { e.printStackTrace(); }
    }

    private EntityFireworks spawnNewFireworkEntity() {
        EntityFireworks entity = new EntityFireworks(((CraftWorld) location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ(), fireworks) {
            public void m() {
                this.world.broadcastEntityEffect(this, (byte)17);
                die();
            }
        };
        if(lifeTime < 21) return entity;
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInt("LifeTime", lifeTime);
        entity.a(compound);
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

    private FireworkEffect getFireworkEffect(int effectId) throws IllegalArgumentException {
        if(effectId == 0) return FireworkEffect.builder().flicker(false).trail(false).withColor(Color.RED).with(FireworkEffect.Type.BALL).build();
        else if(effectId == 1) return FireworkEffect.builder().flicker(false).trail(false).withColor(Color.fromRGB(0, 255, 0)).with(FireworkEffect.Type.CREEPER).build();
        else throw new IllegalArgumentException("effectId");
    }

}
