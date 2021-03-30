package npc.zeldaboy111;

import com.mojang.authlib.GameProfile;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class ProfileClass {
    private static int lastProfileId = 0;
    private UUID npcUuid;
    private String uuidAsString, profileOwner, profileId;
    private GameProfile profile;

    public ProfileClass() {
        this.npcUuid = UUID.randomUUID();
        this.profileId = getNewProfileId();
        this.profile = new GameProfile(npcUuid, profileId);
    }
    private String getNewProfileId() {
        if((lastProfileId+"").length() > 31) return "";
        else return lastProfileId++ + "";
    }

    private void updateProfileTextures() {
        try {
            URL profileUrl = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuidAsString + "?unsigned=false");
            InputStreamReader read = getNewInputStreamReader(profile);
            JSONObject textureProperty = getProperties(getNewJsonElement(reader));
            this.profile.getProperties().put("textures", getNewProperty("textures", getPropertyAsString(textureProperty, "value"), getPropertyAsString(textureProperty, "signature")));
        } catch(IOException e) { e.printStackTrace(); }
    }

}
