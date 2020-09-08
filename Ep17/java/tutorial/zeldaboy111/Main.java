package tutorial.zeldaboy111;

import com.sun.corba.se.impl.activation.CommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import tutorial.zeldaboy111.CustomCodingLanguage.FileHandler;
import tutorial.zeldaboy111.factory.Food;
import tutorial.zeldaboy111.factory.FoodFactory;
import tutorial.zeldaboy111.multiversion.Minecraft;
import tutorial.zeldaboy111.multiversion.minecraft_1_15_2_R1;

public class Main extends JavaPlugin {
    public static Minecraft minecraft;
    public static Plugin plugin;
    public static Main clazz;

    public void onEnable() {
        plugin = this;
        clazz = this;
        implementCommand("test");
        implementCommand("inventory");
        implementListener(new PlayerListener());

        /**
         * Change 'vehicle' to 'food' ;)
         * - Custom Recipes
         * - Custom Effects when eating it
         *
         */
        String[] foodTypes = new String[] {"Bread", "Honey Bread", "Oreo"};
        Food[] food = new Food[foodTypes.length];

        FoodFactory factory = new FoodFactory();
        String currentFood = null;
        
        Player p = Bukkit.getServer().getPlayer("Zeldaboy111");
        try {
            int id = 0;
            for(String f : foodTypes) {
                currentFood = f;
                Food f2 = factory.getFood(f);
                f2.setup();

                food[id] = f2;
                id++;
            }
        } catch(NullPointerException e) {
            System.out.println("FoodNotFoundException:" + currentFood);
        }

        minecraft = new minecraft_1_15_2_R1();
        FileHandler.instance.loadFiles();
    }

    public void onDisable() { }

    private void implementCommand(String cmd) {
        Bukkit.getServer().getPluginCommand(cmd).setExecutor(new Commands());
    }

    private void implementListener(Listener clazz) {
        Bukkit.getServer().getPluginManager().registerEvents(clazz, this);
    }

}
