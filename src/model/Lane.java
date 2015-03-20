package model;

/**
 * Created by Peter on 11/03/2015.
 */
public class Lane {

    private int id;
    private String laneName;
    private int maxPlayers;

    public Lane(int i, String l,int p) {
        System.out.println("Inside : LaneModel");
        this.id = i;
        this.laneName = l;
        this.maxPlayers = p;
    }

    public Lane(String l,int p) {
        this.laneName = l;
        this.maxPlayers = p;
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

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
