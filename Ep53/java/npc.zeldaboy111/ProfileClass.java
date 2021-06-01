package npc.zeldaboy111;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
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
            InputStreamReader reader = getNewInputStreamReader(profileUrl);
            JsonObject textureProperty = getProperties(getNewJsonElement(reader));
            this.profile.getProperties().put("textures", getNewProperty("textures", getPropertyAsString(textureProperty, "value"), getPropertyAsString(textureProperty, "signature")));
        } catch(IOException e) { e.printStackTrace(); }
    }
    
    private InputStreamReader getNewInputStreamReader(URL url) throws IOException {
        return new InputStreamReader(url.openStream());
    }
    private JsonObject getProperties(JsonElement element) {
        return element.getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
    }
    private JsonElement getNewJsonElement(InputStreamReader reader) {
        return new JsonParser().parse(reader);
    }
    private Property getNewProperty(String name, String propertyAsString, String signature) {
        return new Property(name, propertyAsString, signature);
    }
    private String getPropertyAsString(JsonObject container, String property) {
        return container.get(property).getAsString();
    }

    public Boolean updateSkinOwner(String skinOwnerName) {
        this.profileOwner = skinOwnerName;
        this.uuidAsString = getUuidFromProfileOwner();
        if(uuidAsString == null) return false;
        updateProfileTextures();
        return true;
    }
    private String getUuidFromProfileOwner() {
        try {
            URL profileUrl = getProfileUrl();
            InputStreamReader reader = getNewInputStreamReader(profileUrl);
            JsonElement element = getNewJsonElement(reader);
            if(!isElementJsonObject(element)) return null;
            return element.getAsJsonObject().get("id").getAsString();
        } catch(IOException e) { e.printStackTrace(); }
        return null;
    }
    private URL getProfileUrl() throws IOException {
        return new URL("https://api.mojang.com/users/profile/minecraft/"+profileOwner);
    }
    private Boolean isElementJsonObject(JsonElement element) { return element instanceof JsonObject; }

    public GameProfile getProfile() { return profile; }
}
