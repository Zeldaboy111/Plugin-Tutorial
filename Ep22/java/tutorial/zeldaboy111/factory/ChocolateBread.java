package tutorial.zeldaboy111.factory;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.SkullMeta;
import tutorial.zeldaboy111.Main;

import java.lang.reflect.Field;
import java.util.UUID;

public class ChocolateBread implements Food {

    ChocolateBread() { }

    private ItemStack stack;
    public void setup() {
        stack = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullmeta = (SkullMeta)stack.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2VhZmJkZWM4NjAxOGZjNWJiMWVjNDY1MWEwMzYwNWExMGZiNGVmNjI4NDFhYzY4NDg4MzBjNWJmZGMxY2UzNyJ9fX0"));

        Field profileField;
        try {
            profileField = skullmeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullmeta, profile);
        } catch(Exception e) {
            e.printStackTrace();
        }

        skullmeta.setDisplayName("§6Chocolate Bread");
        stack.setItemMeta(skullmeta);
        setupRecipe();
    }

    public void setupRecipe() {
        NamespacedKey key = new NamespacedKey(Main.plugin, "chocolate_bread_key");
        ShapedRecipe recipe = new ShapedRecipe(key, stack);
        recipe.shape("CCC", "CBC", "CCC");

        recipe.setIngredient('C', Material.COCOA_BEANS);
        recipe.setIngredient('B', Material.BREAD);

        Bukkit.getServer().addRecipe(recipe);
    }


}
