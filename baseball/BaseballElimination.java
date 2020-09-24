import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BaseballElimination {
    private final int nt;
    private final String[] teams;
    private final HashMap<String, Integer> team_i;
    private final int[] w, l, r;
    private final int[][] g;
    private boolean[] isComputed;
    private boolean[] isEliminated;
    private Object[] certificateOfElimination;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename)
    {
        In in = new In(filename);

        nt = in.readInt();

        teams = new String[nt];
        team_i = new HashMap<String, Integer>();
        w = new int[nt];
        l = new int[nt];
        r = new int[nt];
        g = new int[nt][nt];
        isComputed = new boolean[nt];
        isEliminated = new boolean[nt];
        certificateOfElimination = new Object[nt];


        int i = 0;
        while (!in.isEmpty()) {
            teams[i] = in.readString();
            team_i.put(teams[i], i);
            isComputed[i] = false;
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for (int j = 0; j < nt; j++) {
                g[i][j] = in.readInt();
            }
            i = i+1;
        }




    }
    // number of teams
    public              int numberOfTeams()
    {
        return nt;
    }

    // all teams
    public Iterable<String> teams()
    {
        return Arrays.asList(teams);
    }

    // number of wins for given team
    public int wins(String team)
    {
        if (!team_i.containsKey(team)) throw new IllegalArgumentException();
        return w[team_i.get(team)];
    }

    // number of losses for given team
    public int losses(String team)
    {
        if (!team_i.containsKey(team)) throw new IllegalArgumentException();
        return l[team_i.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team)
    {
        if (!team_i.containsKey(team)) throw new IllegalArgumentException();
        return r[team_i.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2)
    {
        if (!team_i.containsKey(team1) || !team_i.containsKey(team2)) throw new IllegalArgumentException();
        return g[team_i.get(team1)][team_i.get(team2)];
    }

    private void compute(String team) {
        int ti = team_i.get(team);

        // trivial elimination ?
        for (int i = 0; i < nt; i++) {
            if (i != ti) {
                if ((w[ti] + r[ti]) < w[i]) {
                    isEliminated[ti] = true;
                    certificateOfElimination[ti] = new ArrayList<String>();
                    ((ArrayList<String>) certificateOfElimination[ti]).add(teams[i]);
                    isComputed[ti] = true;
                    // StdOut.println(teams[i]+" is trivially eliminated");
                    return;
                }
            }
        }

        // This is wrong because we don't want pairs of teams to appear twice
        // in different order. For example, we want only one team a/team b vertex
        // we don't want another team b / team a vertex
        // int n_games_vertices = (nt-1)*(nt-1) - (nt-1);

        // Compute the number of team a/team b vertices
        // That's probably the number of arrangements nt-1/2
        // But doesn't cost much to compute iteratively...
        int n_games_vertices=0;
        for (int i = 0; i < nt; i++) {
            for (int j = i +1; j < nt; j++) {
                if (i != ti && j != ti && i != j) {
                    n_games_vertices=n_games_vertices+1;
                }
            }
        }

        String[] vn = new String[2 + n_games_vertices + (nt-1)];
        vn[0]="s";
        vn[2 + n_games_vertices + (nt-1)-1]="t";

        // 2 because there is the source and target vertices, nt-1 is the number of teams vertices
        FlowNetwork G = new FlowNetwork(2 + n_games_vertices + (nt-1) );

        // construct an array giving the id of the vertex for team i and connect it to the target
        int[] team_vertex_id=new int[nt];
        for (int i = 0; i < nt; i++) {
            if (i < ti) {
                team_vertex_id[i]=1+n_games_vertices+i;
                FlowEdge e = new FlowEdge(team_vertex_id[i], G.V()-1, w[ti] + r[ti] - w[i]);
                G.addEdge(e);
                vn[team_vertex_id[i]]=teams[i];
            }
            else if (i > ti)
            {
                team_vertex_id[i]=1+n_games_vertices+i-1;
                FlowEdge e = new FlowEdge(team_vertex_id[i], G.V()-1, w[ti] + r[ti] - w[i]);
                G.addEdge(e);
                vn[team_vertex_id[i]]=teams[i];
            }
        }

        // connection the source vertex 0 to team i vs team j vertices ( excluding the team we're computing )
        int v = 1;
        for (int i = 0; i < nt; i++) {
            for (int j = i + 1; j < nt; j++) {
                if (i != ti && j != ti && i != j) {
                    // source to game vertices
                    FlowEdge e = new FlowEdge(0, v, g[i][j]);
                    G.addEdge(e);
                    // game to the teams vertices
                    e = new FlowEdge(v, team_vertex_id[i], Double.POSITIVE_INFINITY);
                    G.addEdge(e);
                    e = new FlowEdge(v, team_vertex_id[j], Double.POSITIVE_INFINITY);
                    G.addEdge(e);
                    vn[v]=teams[i]+"-"+teams[j];

                    v = v + 1;
                }
            }
        }

        /*
        for (int i = 0; i < nt; i++) {
            if ( i!=ti ) {
                StdOut.println(teams[i]+" edges");
                for (FlowEdge e : G.adj(team_vertex_id[i])) {
                    StdOut.println(vn[e.from()]+" to "+vn[e.to()]);
                    StdOut.println(e);
                }
            }
        }

         */


        FordFulkerson ff = new FordFulkerson(G, 0, G.V() - 1);
        for (FlowEdge e : G.adj(0)) {
            // StdOut.println(e);
            if( e.flow() < e.capacity() ) {
                isEliminated[ti] = true;
                isComputed[ti] = true;
                certificateOfElimination[ti] = new ArrayList<String>();
                for (int i = 0; i < nt; i++) {
                    if (i < ti) {
                        if (ff.inCut(team_vertex_id[i])) {
                            ((ArrayList<String>) certificateOfElimination[ti]).add(teams[i]);
                        }
                    }
                    else if (i > ti)
                    {
                        if (ff.inCut(team_vertex_id[i])) {
                            ((ArrayList<String>) certificateOfElimination[ti]).add(teams[i]);
                        }
                    }
                }
            }
        }
        /*
        StdOut.println("Edges of t");
        for (FlowEdge e : G.adj(G.V()-1)) {
            StdOut.println(e);
        }

         */
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if( ! team_i.containsKey(team) ) throw new IllegalArgumentException();
        if (!isComputed[team_i.get(team)]) {
            compute(team);
        }
        return isEliminated[team_i.get(team)];
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team)  {
        if( ! team_i.containsKey(team) ) throw new IllegalArgumentException();
        if (!isComputed[team_i.get(team)]) {
            compute(team);
        }
        return ((ArrayList<String>) certificateOfElimination[team_i.get(team)]);
    }


    public static void main(String[] args) {
        BaseballElimination be = new BaseballElimination("teams4.txt");
        StdOut.println(be.numberOfTeams());
        StdOut.println(be.wins("Atlanta"));
        StdOut.println(be.losses("New_York"));
        StdOut.println(be.against("Philadelphia", "Montreal"));

        BaseballElimination division = new BaseballElimination("teams5.txt");
        for (String team : division.teams()) {
            //String team="Boston";
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }

    }

}


