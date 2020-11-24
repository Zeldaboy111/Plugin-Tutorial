package tutorial.zeldaboy111.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Set;

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
        this.teamWeight = -1;
        init(name, prefix, suffix);
    }
    private ScoreboardTeam(String name, String prefix, String suffix, int weight) {
        this.teamWeight = weight;
        init(name, prefix, suffix);
    }
    private void init(String name, String prefix, String suffix) {
        this.teamName = name;
        this.prefix = prefix == null ? "" : ChatColor.translateAlternateColorCodes('&', prefix);
        this.suffix = suffix == null ? "" : ChatColor.translateAlternateColorCodes('&', suffix);
        tryInitTeamColor();
        validateTeamWeight();
        setupTeam();
    }
    private void tryInitTeamColor() {
        if(prefix == null || prefix.length() < 2) return;
        this.color = getTeamColorFromString(prefix.substring(0, 2));
    }
    private ChatColor getTeamColorFromString(String prefixColor) {
        if(!prefixColor.startsWith("§")) return DEFAULT_COLOR;
        prefixColor = prefixColor.substring(1, 2);
        for(ChatColor color : ChatColor.class.getEnumConstants()) {
            if((color.getChar() + "").equals(prefixColor)) return color;
        }
        return DEFAULT_COLOR;
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
        Team team;
        if((team = mainBoard.getTeam(teamWeight + "")) != null) team.unregister();
        removePlayersFromTeam();
        manager.updateBoard(this);
    }
    private void removePlayersFromTeam() {
        if(scoreboardTeam.getPlayers().isEmpty()) return;
        Set<OfflinePlayer> players = scoreboardTeam.getPlayers();
        for(OfflinePlayer p : players) {
            scoreboardTeam.removePlayer(p);
        }
    }

    @Override
    public void addPlayer(Player player) {
        if(!scoreboardTeam.getPlayers().isEmpty() && scoreboardTeam.getPlayers().contains(player)) return;
        scoreboardTeam.addPlayer(player);
        manager.updateBoard(this);
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

    private void validateTeamWeight() {
        Boolean automatic = teamWeight < 0 || teamWeight == lastAutomaticWeight;
        if(automatic) { teamWeight = lastAutomaticWeight; lastAutomaticWeight++; }
        while(manager.getTeamFromScoreboard(teamWeight) != null) {
            if(automatic && teamWeight >= WEIGHT_MAX_AUTOMATIC) {
                teamWeight = -1;
                throw new IllegalArgumentException("WeightExceedsAutomaticWeightLimit:" + WEIGHT_MAX_AUTOMATIC);
            }
            teamWeight++;
            if(teamWeight >= WEIGHT_MAX) {
                teamWeight = -1;
                throw new IllegalArgumentException("WeightExceedsLimit:" + WEIGHT_MAX);
            }
        }
        teamWeight = WEIGHT_MAX-teamWeight;
    }

    private void setupTeam() {
        createTeam();
        if(scoreboardTeam == null) return;
        scoreboardTeam.setPrefix(getPrefix());
        scoreboardTeam.setSuffix(getSuffix());
        scoreboardTeam.setColor(getColor());
    }
    private void createTeam() {
        if((scoreboardTeam = mainBoard.getTeam(teamWeight + "")) != null) scoreboardTeam.unregister();
        scoreboardTeam = mainBoard.registerNewTeam(teamWeight + "");
    }

}
