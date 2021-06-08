package npc.zeldaboy111;

import net.minecraft.server.v1_15_R1.*;
import npc.zeldaboy111.files.Files;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
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
        Boolean nameValid = true;
        if(name == null || name.trim().equals("")) name = " ";
        else if(name.length() > 16) nameValid = false;
        try {
            Field field = profile.getProfile().getClass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(profile.getProfile(), ChatColor.translateAlternateColorCodes('&', nameValid ? name : name.substring(0, 16)));
        } catch(Exception e) { e.printStackTrace(); }
        Files.NPC.save("npc."+id+".name", name);
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

    private void killNearbyMatchingEntities() {
        if(npcType == EntityType.PLAYER) return;
        for(Entity e : npcAsEntity.getNearbyEntities(0.5, 0.5, 0.5)) {
            if(e.getCustomName() != null && e.getCustomName().equals(this.name) && e.getType() == npcType && e.isInvulnerable() && e.isSilent()) {
                e.remove();
            }
        }
    }

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
    private void refreshNpc() {
        if(npcType == EntityType.PLAYER) refreshPlayerNpc();
        else if(npcAsEntity != null && !checkIfNpcIsDead()) npcAsEntity.teleport(this.location);
    }
    private void refreshPlayerNpc() {
        if(npcAsPlayer == null) return;
        hide();
        npcAsPlayer.setLocation(this.location.getX(), this.location.getY(), this.location.getZ(), 0f, 0f);
        show();
    }

    @Override
    public void refresh() {
        hide();
        if(Files.NPC.getLocation("npc."+id+".location") != null) location = Files.NPC.getLocation("npc."+id+".location");
        initVariables();
        createNewNpc();
    }
    @Override
    public void show() {
        for (Player p : location.getWorld().getPlayers()) show(p);
    }
    @Override
    public void show(Player player) {
        if(player.getWorld() != location.getWorld()) return;
        if(npcType == EntityType.PLAYER) showPlayerNpc(player);
        else showNpc(player);
    }
    private void showPlayerNpc(Player player) {
        final PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;
        playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npcAsPlayer));
        playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(npcAsPlayer));
        playerConnection.sendPacket(new PacketPlayOutEntityHeadRotation(npcAsPlayer, (byte)((0*256f)/360f)));
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                if(skinOwner != null && !skinOwner.trim().equals("")) sendNpcSkinPackets(playerConnection);
                playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npcAsPlayer));
            }
        }, 50);
    }
    private void sendNpcSkinPackets(PlayerConnection playerConnection) {
        DataWatcher watcher = npcAsPlayer.getDataWatcher();
        Byte bytes = 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40; // cape, jacket, left sleeve, right sleeve, left pants, right pants, hat
        watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a), bytes);
        playerConnection.sendPacket(new PacketPlayOutEntityMetadata(npcAsPlayer.getId(), watcher, true));
    }
    private void showNpc(Player player) {
        loadNewEntityNpc();
    }

    @Override
    public void hide() {
        for(Player player : location.getWorld().getPlayers()) hide(player);
    }
    @Override
    public void hide(Player player) {
        if(npcType == EntityType.PLAYER) hidePlayerNpc(player);
        else if(!checkIfNpcIsDead()) hideEntityNpc();
    }
    private void hidePlayerNpc(Player player) {

        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(npcAsPlayer.getId()));
    }
    private void hideEntityNpc() {
        npcAsEntity.remove();
        npcAsEntity.setHealth(0.0);
    }
    @Override
    public void tryExecuteNpcCommands(Player player) {
        if(player != null && player.isOnline() && !npcCommands.isEmpty()) executeCommands(player);
    }
    private void executeCommands(Player player) {
        for(String cmd : npcCommands) {
            if(cmd.startsWith("/")) cmd = cmd.replaceFirst("/", "");
            Boolean playerCmd = checkIfCommandIsPlayerCommand(cmd);
            if(playerCmd) {
                cmd = cmd.replaceFirst("player:", "");
                if(cmd.startsWith("/")) cmd = cmd.replaceFirst("/", "");
            }
            if(checkIfCommandIsValid(cmd.split(" ")[0])) executeCommand(player, cmd, playerCmd);
        }
    }
    private Boolean checkIfCommandIsPlayerCommand(String cmd) {
        return cmd.startsWith("player:");
    }
    private Boolean checkIfCommandIsValid(String cmd) {
        return cmd != null && !cmd.trim().equals("") && CommandMain.getInstance().getCommandFromMap(cmd) != null;
    }
    private void executeCommand(Player p, String command, Boolean isPlayerCommand) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
                String cmd = ChatColor.translateAlternateColorCodes('&', command).replace("%player%", p.getName());
                if(isPlayerCommand) p.performCommand(cmd);
                else Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);
            }
        }, 2);
    }


    @Override
    public int getEntityId() { return npcType == EntityType.PLAYER ? npcAsPlayer.getId() : npcAsEntity.getEntityId(); }


}
