package Napredno_Programiranje.SecondPartialExercises.kol_29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Partial exam II 2016/2017
 */
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here

class FootballTable{
    private Map<String,Team> teamMap;

    public FootballTable() {
        teamMap = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals){
        Game game = new Game(homeTeam,awayTeam,homeGoals,awayGoals);
        teamMap.putIfAbsent(homeTeam,new Team(homeTeam));
        teamMap.putIfAbsent(awayTeam,new Team(awayTeam));
        teamMap.get(homeTeam).updateStats(game);
        teamMap.get(awayTeam).updateStats(game);
    }
    public void printTable(){
        AtomicInteger counter = new AtomicInteger(1);
        teamMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Team.COMPARATOR))
                .forEach(entry->{
                    System.out.println(String.format("%2d. %s", counter.getAndIncrement(),entry.getValue()));
                });
    }
}
class Game{
    String homeTeam;
    String awayTeam;
    int homeGoals;
    int awayGoals;

    public Game(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

}
class Team{
    private final String name;
    private int playedGames;
    private int wins;
    private int loses;
    private int draws;
    private int goalsScored;
    private int goalsReceived;
    public static final Comparator<Team>COMPARATOR=
            Comparator.comparing(Team::totalPoints)
                    .thenComparing(Team::goalDifference).reversed()
                    .thenComparing(Team::getName);

    public Team(String name) {
        this.name = name;
        playedGames = wins = loses = draws =  goalsReceived = goalsScored = 0;
    }
    public int totalPoints() {
        return wins*3 + draws;
    }
    public void updateStats(Game game){
        if (name.equals(game.homeTeam)){
            if (game.homeGoals > game.awayGoals)wins++;
            else if (game.homeGoals < game.awayGoals)loses++;
            else draws++;
            goalsScored+=game.homeGoals;
            goalsReceived+=game.awayGoals;
        }else {
            if (game.homeGoals < game.awayGoals)wins++;
            else if (game.homeGoals > game.awayGoals)loses++;
            else draws++;
            goalsScored+=game.awayGoals;
            goalsReceived+=game.homeGoals;
        }
        playedGames++;
    }
    public int goalDifference(){return goalsScored-goalsReceived;}

    @Override
    public String toString() {
        return String.format("%-18s%2d    %-5d%-5d%-4d%2d",
                name,playedGames,wins,draws,loses,totalPoints());
    }

    public String getName() {
        return name;
    }
}