package res;

import res.infotype.InfoAgent;
import res.infotype.InfoBomb;
import res.infotype.InfoItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe permettant de stocker les informations courante sur l'état du jeu
 * Objet mis à jour à chaque passage dans la méthode run du Controller via appels REST
 */

public class Map implements Serializable {

    private static final long serialVersionUID = 1L;
    private String filename;
    private int size_x;
    private int size_y;
    private boolean walls[][];
    private boolean breakableWalls[][];
    private ArrayList<InfoAgent> infoAgents;
    private ArrayList<InfoBomb> infoBombs;
    private ArrayList<InfoItem> infoItems;

    public Map(String filename, int size_x, int size_y, boolean walls[][], boolean breakableWalls[][],
               ArrayList<InfoAgent> infoAgents, ArrayList<InfoBomb> infoBombs, ArrayList<InfoItem> infoItems) {
        this.filename = filename;
        this.size_x = size_x;
        this.size_y = size_y;
        this.walls = walls;
        this.breakableWalls = breakableWalls;
        this.infoAgents = infoAgents;
        this.infoBombs = infoBombs;
        this.infoItems = infoItems;

    }

    public int getSizeX() {
        return (size_x);
    }

    public int getSizeY() {
        return (size_y);
    }

    public String getFilename() {
        return filename;
    }

    public boolean[][] getBreakableWalls() {
        return breakableWalls;
    }

    public boolean[][] get_walls() {
        return walls;
    }

    public ArrayList<InfoAgent> getInfoAgents() {
        return infoAgents;
    }

    public ArrayList<InfoBomb> getInfoBombs() {
        return infoBombs;
    }

    public ArrayList<InfoItem> getInfoItems() {
        return infoItems;
    }

}