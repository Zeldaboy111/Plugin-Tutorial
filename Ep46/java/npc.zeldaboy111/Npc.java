package npc.zeldaboy111;

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

    @Override
    public void setLocation(Location location) {
        if(location == null) return;
        this.location = location;
        this.location.setYaw(0f);
        this.location.setPitch(0f);

        refreshNpc();
    }


}
