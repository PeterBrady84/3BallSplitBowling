package model;

/**
 * Created by Peter on 11/03/2015.
 */
public class Lane {

    private int id;
    private String laneName;
    private final static int maxPlayers = 6;


    public Lane (int i, String l) {
        //System.out.println("Inside : LaneModel");
        this.id = i;
        this.laneName = l;
    }

    public Lane (String l) {
        //System.out.println("Inside : LaneModel");
        this.laneName = l;
    }

    public Lane(int id) {
        this.id = id;
        laneName = "Lane "+id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }


}
