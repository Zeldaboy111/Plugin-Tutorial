package tutorial.zeldaboy111.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import tutorial.zeldaboy111.Main;

import java.util.HashMap;

public class ScoreboardManager implements IScoreboardManager {
    private static ScoreboardManager clazz;
    private ScoreboardManager() { }
    public static IScoreboardManager getInstance() { return clazz == null ? (clazz = new ScoreboardManager()) : clazz; }

    private static final Scoreboard mainBoard = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
    private static final Scoreboard emptyBoard = Bukkit.getScoreboardManager().getNewScoreboard();

    @Override
    public void insertPlayer(Player p) {
        new Board(p);
    }

    @Override
    public void removePlayer(Player p) {
        p.setScoreboard(emptyBoard);
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


    }

}
