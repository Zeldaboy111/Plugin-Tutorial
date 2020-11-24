package tutorial.zeldaboy111.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public interface IScoreboardTeam {

    IScoreboardTeam createTeam(String name, String prefix, String suffix);
    IScoreboardTeam createTeam(String name, String prefix, String suffix, int weight);

    void delete();

    void addPlayer(Player player);

    int getWeight();

    String getPrefix();
    String getSuffix();
    ChatColor getColor();

    ArrayList<Player> getPlayers();


}
