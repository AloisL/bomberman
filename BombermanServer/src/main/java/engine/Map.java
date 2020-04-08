package engine;

import common.enums.AgentAction;
import common.enums.ColorAgent;
import common.infotypes.InfoAgent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Classe qui permet de charger une carte de Bomberman à partir d'un fichier de layout d'extension .lay
 *
 * @author Kevin Balavoine, Victor Lelu--Ribaimont
 */

public class Map implements Serializable {

    private static final long serialVersionUID = 1L;
    private String filename;
    private int size_x;
    private int size_y;
    private boolean walls[][];
    private boolean breakableWalls[][];
    private ArrayList<InfoAgent> infoAgents;

    public Map(String filename) {
        this.filename = filename;
        try {
            InputStream flux = getClass().getResourceAsStream(filename);
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader tampon = new BufferedReader(lecture);

            String ligne;

            int nbX = 0;
            int nbY = 0;

            while ((ligne = tampon.readLine()) != null) {
                ligne = ligne.trim();
                if (nbX == 0) {
                    nbX = ligne.length();
                } else if (nbX != ligne.length())
                    throw new Exception("Toutes les lignes doivent avoir la même longueur");
                nbY++;
            }
            tampon.close();

            size_x = nbX;
            size_y = nbY;

            walls = new boolean[size_x][size_y];
            breakableWalls = new boolean[size_x][size_y];

            flux = getClass().getResourceAsStream(filename);
            lecture = new InputStreamReader(flux);
            tampon = new BufferedReader(lecture);
            int y = 0;

            ColorAgent[] color = ColorAgent.values();
            int cpt_col = 0;

            infoAgents = new ArrayList<InfoAgent>();

            while ((ligne = tampon.readLine()) != null) {
                ligne = ligne.trim();

                for (int x = 0; x < ligne.length(); x++) {

                    if (ligne.charAt(x) == '%')
                        walls[x][y] = true;

                    else walls[x][y] = false;

                    if (ligne.charAt(x) == '$')
                        breakableWalls[x][y] = true;
                    else breakableWalls[x][y] = false;

                    if (ligne.charAt(x) == 'E' || ligne.charAt(x) == 'V' || ligne.charAt(x) == 'R') {
                        infoAgents.add(new InfoAgent(x, y, AgentAction.STOP, ligne.charAt(x), ColorAgent.DEFAULT,
                                false, false));
                    }

                    if (ligne.charAt(x) == 'B') {
                        ColorAgent col;
                        if (cpt_col < color.length) col = color[cpt_col];
                        else col = ColorAgent.DEFAULT;
                        infoAgents.add(new InfoAgent(x, y, AgentAction.STOP, ligne.charAt(x), col, false, false));
                        cpt_col++;
                    }

                }
                y++;
            }
            tampon.close();

            //On verifie que le labyrinthe est clos
            for (int x = 0; x < size_x; x++)
                if (!walls[x][0]) throw new Exception("Mauvais format du fichier: la carte doit etre close");
            for (int x = 0; x < size_x; x++)
                if (!walls[x][size_y - 1]) throw new Exception("Mauvais format du fichier: la carte doit etre close");
            for (y = 0; y < size_y; y++)
                if (!walls[0][y]) throw new Exception("Mauvais format du fichier: la carte doit etre close");
            for (y = 0; y < size_y; y++)
                if (!walls[size_x - 1][y]) throw new Exception("Mauvais format du fichier: la carte doit etre close");

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
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

}