package npc.zeldaboy111;

import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.PlayerInteractManager;
import net.minecraft.server.v1_15_R1.WorldServer;
import npc.zeldaboy111.files.Files;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftSalmon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Npc implements INpc {

    private String id, name, skinOwner;
    private Location location;
    private EntityType npcType;
    private LivingEntity npcAsEntity;
    private EntityPlayer npcAsPlayer;
    private ProfileClass profile;
    private ArrayList<String> npcCommands; //TODO Setup the pipeline etc. for this

    public Npc(String id, Location location) {
        this.id = id;
        setLocation(location);
        initVariables();
        createNewNpc();
    }
    private void initVariables() {
        if(checkIfNpcHasData()) initVariablesFromExisting();
        else initNewVariables();
    }
    private Boolean checkIfNpcHasData() {
        return Files.NPC.getString("npc." + id) != null;
    }
    private void initVariablesFromExisting() {
        name = Files.NPC.getString("npc." + id + ".name");
        skinOwner = Files.NPC.getString("npc."+id+".skinowner");
        npcCommands = Files.NPC.getStringArray("npc."+id+".commands", false);
        if(name == null || name.equals("")) name = " ";
        if(skinOwner == null || skinOwner.equals("")) skinOwner = " ";
    }
    private void initNewVariables() {
        name = " ";
        skinOwner = " ";
        npcCommands = new ArrayList<>();
    }

    private void createNewNpc() {
        profile = new ProfileClass();
        if(checkIfNpcHasData()) initializeNpcType();
        else npcType = EntityType.PLAYER;
        loadNewNpc();
        killNearbyMatchingEntities();
    }
    private void initializeNpcType() {
        String typeAsString = Files.NPC.getString("npc."+id+".type");
        npcType = null;
        if(typeAsString != null && !typeAsString.trim().equals("")) npcType = EntityType.valueOf(typeAsString);
        if(npcType == null) npcType = EntityType.PLAYER;
    }

    private void loadNewNpc() {
        if(npcType == EntityType.PLAYER) loadNewPlayerNpc();
        else loadNewEntityNpc();
        save();
    }
    private void loadNewPlayerNpc() {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld)location.getWorld()).getHandle();
        npcAsPlayer = new EntityPlayer(server, world, profile.getProfile(), new PlayerInteractManager(world));
        npcAsPlayer.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        npcAsPlayer.setCustomNameVisible(true);
        setNpcName();
        setNpcSkin();
        hide();
        show();
    }
    private void setNpcName() {

    }
    private void setNpcSkin() {
        if(skinOwner == null || skinOwner.trim().equals("")) return;
        profile.updateSkinOwner(skinOwner);
    }

    private void loadNewEntityNpc() {
        if(!checkIfNpcIsDead()) return;
        npcAsEntity = getNewEntityNpc();
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                if(!checkIfNpcIsDead()) npcAsEntity.setInvulnerable(true);
            }
        }, 2);
    }
    private LivingEntity getNewEntityNpc() {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, npcType);
        entity.setSilent(true);
        entity.setAI(false);
        entity.setGravity(false);
        entity.setCustomNameVisible(true);
        entity.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
        return entity;
    }
    private Boolean checkIfNpcIsDead() { return npcAsEntity == null || npcAsEntity.isDead(); }

    private void save() {
        Files.NPC.save("npc."+id+".name", name);
        Files.NPC.save("npc."+id+".type", npcType.toString().toLowerCase());
        Files.NPC.save("npc."+id+".skinowner", skinOwner);
        Files.NPC.save("npc."+id+".commands", npcCommands);
        Files.NPC.save("npc."+id+".location", location);
    }

    @Override
    public void setLocation(Location location) {
        if(location == null) return;
        this.location = location;
        this.location.setYaw(0f);
        this.location.setPitch(0f);

        refreshNpc();
    }


}
