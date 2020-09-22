import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.HashMap;

public class BaseballElimination {
    private final int nt;
    private final String[] teams;
    private final HashMap<String, Integer> team_i;
    private final int[] w, l, r;
    private final int[][] g;

    public BaseballElimination(
            String filename)                    // create a baseball division from given filename in format specified below
    {
        In in = new In(filename);

        nt = in.readInt();

        teams = new String[nt];
        team_i = new HashMap<String, Integer>();
        w = new int[nt];
        l = new int[nt];
        r = new int[nt];
        g = new int[nt][nt];

        int i = 0;
        while (!in.isEmpty()) {
            teams[i] = in.readString();
            team_i.put(teams[i], i);
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < nt; j++) {
                g[i][j] = in.readInt();
            }
            i = i+1;
        }
    }

public              int numberOfTeams()                        // number of teams
{
    return nt;
}

public Iterable<String> teams()                                // all teams
{
    return Arrays.asList(teams);
}

public              int wins(String team)                      // number of wins for given team
{
    if (!team_i.containsKey(team)) throw new IllegalArgumentException();
    return w[team_i.get(team)];
}

public              int losses(String team)                    // number of losses for given team
{
    if (!team_i.containsKey(team)) throw new IllegalArgumentException();
    return l[team_i.get(team)];
}

public              int remaining(String team)                 // number of remaining games for given team
{
    if (!team_i.containsKey(team)) throw new IllegalArgumentException();
    return r[team_i.get(team)];
}

public              int against(String team1, String team2)    // number of remaining games between team1 and team2
{
    if (!team_i.containsKey(team1) || !team_i.containsKey(team2)) throw new IllegalArgumentException();
    return g[team_i.get(team1)][team_i.get(team2)];
}

/*
public          boolean isEliminated(String team)              // is given team eliminated?
public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated
 */

public static void main(String[] args) {
    BaseballElimination be = new BaseballElimination("teams4.txt");
    StdOut.println(be.numberOfTeams());
    StdOut.println(be.wins("Atlanta"));
    StdOut.println(be.losses("New_York"));
    StdOut.println(be.against("Philadlphia", "Montreal"));
}

}


