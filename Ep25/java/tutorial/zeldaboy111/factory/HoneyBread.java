package tutorial.zeldaboy111.factory;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;
import tutorial.zeldaboy111.Main;

import java.lang.reflect.Field;
import java.util.UUID;

public class HoneyBread implements Food {

    HoneyBread() { }

    private ItemStack stack;
    public void setup() {
        stack = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullmeta = (SkullMeta)stack.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjg5MjJjODQyM2QyODExODQwZjU2Yjc1YzQ1M2FlM2UyYmIzN2ZhMWQ3ZDIzNDhhNWVmOWI0NDU2YzM1ODIxYyJ9fX0"));

        Field profileField;
        try {
            profileField = skullmeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullmeta, profile);
        } catch(Exception e) {
            e.printStackTrace();
        }

        skullmeta.setDisplayName("§6Honey Bread");
        stack.setItemMeta(skullmeta);

        setupRecipe();
    }

    public void setupRecipe() {
        NamespacedKey key = new NamespacedKey(Main.plugin, "honey_bread_key");
        ShapelessRecipe recipe = new ShapelessRecipe(key, stack);
        recipe.addIngredient(Material.BREAD);
        recipe.addIngredient(Material.HONEY_BOTTLE);
        Bukkit.getServer().addRecipe(recipe);
    }

}
