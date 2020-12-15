package tutorial.zeldaboy111.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import tutorial.zeldaboy111.Main;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class ScoreboardManager implements IScoreboardManager {
    private static ScoreboardManager clazz;
    private ScoreboardManager() { }
    public static IScoreboardManager getInstance() { return clazz == null ? (clazz = new ScoreboardManager()) : clazz; }

    private static final Scoreboard mainBoard = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
    private static final Scoreboard emptyBoard = Bukkit.getScoreboardManager().getNewScoreboard();

    private static final HashMap<Integer, IScoreboardTeam> teamMap = new HashMap<>();
    private static final HashMap<UUID, Board> boardMap = new HashMap<>();


    @Override
    public void insertPlayer(Player p) {
        new Board(p);
    }

    @Override
    public void removePlayer(Player p) {
        p.setScoreboard(emptyBoard);
        boardMap.remove(p.getUniqueId());
    }

    @Override
    public void updateBoard(Player player) {
        if(!checkIfPlayerHasBoard(player)) new Board(player);
        else;
    }

    @Override
    public void updateBoard(IScoreboardTeam iTeam) {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) updateBoard(iTeam, p);
    }
    private void updateBoard(IScoreboardTeam team, Player player) {
        if(team == null || player == null || !player.isOnline()) return;
        if(!checkIfPlayerHasBoard(player)) insertPlayer(player);
        else getBoardFromPlayer(player).updateTeam(team);
    }
    private Boolean checkIfPlayerHasBoard(Player player) { return checkIfPlayerHasBoard(player.getUniqueId()); }
    private Boolean checkIfPlayerHasBoard(UUID uuid) { return getBoardFromPlayer(uuid) != null; }
    private Board getBoardFromPlayer(Player player) { return getBoardFromPlayer(player.getUniqueId()); }
    private Board getBoardFromPlayer(UUID uuid) { return boardMap.get(uuid); }

    @Override
    public IScoreboardTeam getTeamFromScoreboard(int weight) {
        return teamMap.get(weight);
    }

    private void setupTeam(String name, String prefix, String suffix) {
        IScoreboardTeam iTeam = ScoreboardTeam.getInstance().createTeam(name, prefix, suffix);
        teamMap.put(iTeam.getWeight(), iTeam);
    }

    private Scoreboard getNewBoard() {
        return Bukkit.getServer().getScoreboardManager().getNewScoreboard();
    }
    private class Board {
        private Player p;
        private Scoreboard board;
        private Objective objective;
        private HashMap<Integer, String> scores;

        private Board(Player p) {
            boardMap.put(p.getUniqueId(), this);
            this.p = p;
            this.scores = new HashMap<>();
            this.board = getNewBoard();
            this.objective = board.registerNewObjective("TestBoard", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName("Test Board");

            p.setScoreboard(board);
            final String defaultText = p.getDisplayName();
            final int maxLength = defaultText.length();
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
                int current = -1;
                public void run() {
                    if(current >= maxLength-1) current = -1;
                    else current++;
                    String text = "§6" + defaultText;
                    if(current > -1)
                        text = "§6" + defaultText.substring(0, current) + "§e" + defaultText.substring(current, current+1) +
                                "§6" + defaultText.substring(current+1);
                    setScoreInBoard(text, 0);
                }
            }, 0L, 5L);
        }

        private void setScoreInBoard(String text, int score) {
            if(scores.get(score) == null) scores.put(score, text);
            else resetAndSetScore(text, score);
            objective.getScore(text).setScore(score);
        }
        private void resetAndSetScore(String text, int score) {
            board.resetScores(scores.get(score));
            scores.replace(score, text);
        }
        public void updateTeam(IScoreboardTeam team) {
            if(team == null) for(IScoreboardTeam team1 : teamMap.values()) updateTeam1(team1);
            else updateTeam1(team);
        }
        private void updateTeam1(IScoreboardTeam team) {
            Team sbTeam = board.getTeam(team.getWeight() + "");
            if(sbTeam == null) sbTeam = createNewScoreboardTeam(team);
            else sbTeam = removePlayersFromTeam(sbTeam);
            for(Player p : team.getPlayers()) sbTeam.addPlayer(p);
        }

        private Team createNewScoreboardTeam(IScoreboardTeam team) {
            Team sbTeam = board.registerNewTeam(team.getWeight() + "");
            if(isStringValid(team.getPrefix())) sbTeam.setPrefix(team.getPrefix());
            else if(isStringValid(team.getSuffix())) sbTeam.setSuffix(team.getSuffix());
            else if(team.getColor() != null) sbTeam.setColor(team.getColor());
            return sbTeam;
        }
        private Boolean isStringValid(String input) {
            return input != null && !input.trim().equals("");
        }
        private Team removePlayersFromTeam(Team sbTeam) {
            Set<OfflinePlayer> buffer = sbTeam.getPlayers();
            for(OfflinePlayer p : buffer) sbTeam.removePlayer(p);
            return sbTeam;
        }

    }

}
