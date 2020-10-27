package tutorial.zeldaboy111.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

public class ScoreboardManager implements IScoreboardManager {
    private static ScoreboardManager clazz;
    private ScoreboardManager() { }
    public static IScoreboardManager getInstance() { return clazz == null ? (clazz = new ScoreboardManager()) : clazz; }

    private static final Scoreboard mainBoard = Bukkit.getServer().getScoreboardManager().getMainScoreboard();

    @Override
    public void insertPlayer(Player p) {
        new Board(p);
    }

    @Override
    public void removePlayer(Player p) {
        //verwijder scoreboard van speler
    }

    private Scoreboard getNewBoard() {
        return Bukkit.getServer().getScoreboardManager().getNewScoreboard();
    }
    private class Board {
        private Player p;
        private Scoreboard board;
        private Objective objective;
        private HashMap<Integer, Score> scores;

        private Board(Player p) {
            this.p = p;
            this.scores = new HashMap<>();
            this.board = getNewBoard();
            this.objective = board.registerNewObjective("TestBoard", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.getScore("1").setScore(0);

            objective.setDisplayName("Test Board");
            p.setScoreboard(board);
        }

        private void setScoreInBoard(int score, String text) {

        }

    }

}
