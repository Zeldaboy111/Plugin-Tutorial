package npc.zeldaboy111;

import npc.zeldaboy111.files.Files;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public class Npc implements INpc {

    private String id, name, skinOwner;
    private Location location;
    private EntityType npcType;
    private LivingEntity npcAsEntity;
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
        
    }
    private void loadNewEntityNpc() {

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


}
