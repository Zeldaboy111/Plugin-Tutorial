package tutorial.zeldaboy111.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class ScoreboardTeam implements IScoreboardTeam {
    private static ScoreboardTeam clazz;
    private ScoreboardTeam() { }
    public static IScoreboardTeam getInstance() { return clazz == null ? (clazz = new ScoreboardTeam()) : clazz; }

    private static final Scoreboard mainBoard = Bukkit.getScoreboardManager().getMainScoreboard();
    private static final IScoreboardManager manager = ScoreboardManager.getInstance();

    private static final ChatColor DEFAULT_COLOR = ChatColor.GRAY;
    private static final int WEIGHT_MAX = 100;
    private static final int WEIGHT_MAX_AUTOMATIC = 79;
    private static int lastAutomaticWeight = 0;

    private int teamWeight;
    private Team scoreboardTeam;
    private String prefix, suffix, teamName;
    private ChatColor color;


    private ScoreboardTeam(String name, String prefix, String suffix) {

    }
    private ScoreboardTeam(String name, String prefix, String suffix, int weight) {

    }
    private void init(String name, String prefix, String suffix) {
        
    }

    @Override
    public IScoreboardTeam createTeam(String name, String prefix, String suffix) {
        return new ScoreboardTeam(name, prefix, suffix);
    }
    @Override
    public IScoreboardTeam createTeam(String name, String prefix, String suffix, int weight) {
        return new ScoreboardTeam(name, prefix, suffix, weight);
    }

    @Override
    public void delete() {

    }

    @Override
    public void addPlayer(Player player) {

    }

    @Override
    public int getWeight() {
        return teamWeight;
    }

    @Override
    public String getPrefix() {
        return prefix == null ? "" : prefix;
    }

    @Override
    public String getSuffix() {
        return suffix == null ? "" : suffix;
    }

    @Override
    public ChatColor getColor() {
        return color == null ? DEFAULT_COLOR : color;
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return null;
    }
}
