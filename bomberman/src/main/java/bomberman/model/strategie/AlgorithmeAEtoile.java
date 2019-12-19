package bomberman.model.strategie;

import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;

import java.util.ArrayList;

public class AlgorithmeAEtoile {

    private ArrayList<Noeud> openList;
    private ArrayList<Noeud> closeList;
    private boolean[][] mapMur;
    private Coordonnee objectif;
    private AbstractAgent perso;

    public AlgorithmeAEtoile() {
    }

    public AlgorithmeAEtoile(BombermanGame bombermanGame, AbstractAgent agent, Coordonnee objectif) {
        mapMur = bombermanGame.getMap().get_walls();
        this.objectif = objectif;
        perso = agent;
        closeList = new ArrayList<>();
        openList = new ArrayList<>();
    }

    public Noeud creerOrigine() {
        Coordonnee ori = new Coordonnee(perso.getX(), perso.getY());
        Noeud n = new Noeud(ori, objectif, 0, null);
        return n;
    }

    public void reTracerChemin(Noeud n) {
        if (n.getOrigine() != null) {
            openList.add(n.getOrigine());
            reTracerChemin(n.getOrigine());
        }
    }

    public Noeud compareVoisin(Noeud n) {
        Noeud nCompare = null;
        if (n.voisin != null) {
            for (Noeud v : n.voisin) {
                if (!existe(v)) {
                    if (nCompare == null) nCompare = v;

                    else nCompare = nCompare.compareHeuri(v);
                }
            }
        }
        return nCompare;
    }

    public Noeud chemin(Coordonnee objectif, Noeud depart) {
        if (depart != null) {
            if (!existe(depart)) {
                openList.add(depart);
                Noeud nTmp = depart;
                if (nTmp.getHcost() != 0) {
                    nTmp.creerVoisin(objectif, mapMur);
                   /* if (nTmp.voisin != null) {
                        if (nTmp.voisin.size() != 0) {
                            System.out.println("PAS VIDE");
                        } else System.out.println("PAS VIDE");
                    } else System.out.println("NULL");
                    */
                    Noeud nSuivant = compareVoisin(depart);
                    if (nSuivant != null) { /*
                        System.out.println("objectif => " + objectif.x + " : " + objectif.y);
                        System.out.println("depart => " + depart.getCoordonnee().x + " : " + depart.getCoordonnee().y);
                        System.out.println("depart heuri => "+depart.getHcost());
                        afficherCloseList();*/
                        return chemin(objectif, nSuivant);
                    } else {
                        System.out.println(depart.getCoordonnee().x + " : " + depart.getCoordonnee().y);
                        closeList.add(depiler(openList));
                    }
                }
                //System.out.println("SORTI");
                if (openList.size() != 1) {
                    depiler(openList);
                    return depiler(openList);
                } else return depiler(openList);
            }
        }
        return null;
    }


    public boolean existe(Noeud n) {
        if (closeList != null) {
            for (Noeud nclose : closeList) {
                if (n.getCoordonnee() == nclose.getCoordonnee()) return true;
            }
        }
        if (openList != null) {
            for (Noeud nopen : openList) {
                if (n.getCoordonnee() == nopen.getCoordonnee()) {
                    if (n.getNbMouve() > nopen.getNbMouve()) return false;
                    else return true;
                }
            }
        }
        return false;
    }


    public Noeud depiler(ArrayList<Noeud> pile) {
        if (pile.size() != 0) {
            if (pile.get(0) != null) {
                Noeud n = pile.get(0);
                pile.remove(0);
                return n;
            }
        }
        return null;
    }

}
