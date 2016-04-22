package jt.autismtracks;

/**
 * Created by chrx on 4/20/16.
 */
public class Rewards_class {
    private String title;
    private int points;
    private String icon_src;
    private long id;

    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public String getIcon_src() {
        return icon_src;
    }

    public  void setIcon_src(String klefstad) {
        this.icon_src = klefstad;
    }

    public void setId(long id) { this.id = id; }
    public long getId() {return id;}
}
