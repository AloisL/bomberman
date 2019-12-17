package bomberman.model.strategie;

import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;

import java.util.ArrayList;

public class Astart {

    private ArrayList<Noeud> openList;
    private ArrayList<Noeud> closeList;
    private boolean[][] mapMur;
    private Coordonne objectif;
    private AbstractAgent perso;


    public Astart(){}

    public Astart(BombermanGame bombermanGame,AbstractAgent agent,Coordonne objectif){

        mapMur=bombermanGame.getMap().get_walls();
        this.objectif=objectif;
        this.perso=agent;
        closeList=new ArrayList<>();
        openList=new ArrayList<>();


    }

    public Noeud creerOrigine(){
        Coordonne ori = new Coordonne(perso.getX(),perso.getY());
        Noeud n=new Noeud(ori,objectif,0,null);
        return n;
    }
    // return true si la difference(en valeur obsolue) de chemin entre coordonne X et plus grande(ou égale) que la difference de coordonné Y
  /*  public boolean difference_X_Y(Noeud node){
        int diffX = objectif.x - node.getCoordonne().x;
        int diffY = objectif.y - node.getCoordonne().y;
        diffX=Math.abs(diffX);
        diffY=Math.abs(diffY);
        return (diffX>=diffY);

    }

    public int differenceX(Noeud node){
        int diffX = objectif.x - node.getCoordonne().x;
        return diffX;

    }

    public int differenceY(Noeud node){
        int diffY = objectif.y - node.getCoordonne().y;
        return diffY;

    }
    /*
    si je met chaque voisin dans open list, je recupere le premier voisin avec l'heuristique la plus petite, si voisin dans close liste je laisse de coté ( suivre principe A* avec interpretation)
     */
    public void reTracerChemin(Noeud n){
        if (n.getOrigine()!=null) {
            openList.add(n.getOrigine());
            reTracerChemin(n.getOrigine());
        }
    }

    public Noeud compareVoisin(Noeud n){
        Noeud nCompare=null;
        if (n.voisin!=null) {
            for (Noeud v : n.voisin) {
                if (!existe(v)) {
                    if (nCompare == null) nCompare = v;

                    else nCompare=nCompare.compareHeuri(v);
                }
            }
        }
        return nCompare;
    }
    /*

    public Noeud creerChemin(Noeud n){
        Noeud nSuivant =compareVoisin(n);
        if (nSuivant!=null){
            closeList.add(n);
        }
        else {
            return creerChemin(nSuivant);
        }
         return chemin();
    }*/


    public Noeud chemin(Coordonne objectif,Noeud depart) {
        if (depart!=null) {
            if (!existe(depart)) {
                openList.add(depart);
                Noeud nTmp = depart;
                if (nTmp.getHcost() != 0) {
                    nTmp.creerVoisin(objectif, mapMur);
                    if (nTmp.voisin != null) {
                        if (nTmp.voisin.size() != 0) {
                            System.out.println("PAS VIDE");
                        } else System.out.println("PAS VIDE");
                    } else System.out.println("NULL");
                    Noeud nSuivant = compareVoisin(depart);
                    if (nSuivant != null) {
                        System.out.println("objectif => " + objectif.x + " : " + objectif.y);
                        System.out.println("depart => " + depart.getCoordonne().x + " : " + depart.getCoordonne().y);
                        System.out.println("depart heuri => "+depart.getHcost());
                        afficherCloseList();
                        return chemin(objectif, nSuivant);
                    } else {
                        System.out.println(depart.getCoordonne().x + " : " + depart.getCoordonne().y);
                        closeList.add(depiler(openList));
                    }
                }
                System.out.println("SORTI");
                if(openList.size()!=1) {
                    depiler(openList);
                    return depiler(openList);
                }
                else return depiler(openList);
            }
        }
        return null;
    }


    public void afficherCloseList(){
        if (closeList!=null){
            if (closeList.size()!=0){
                System.out.println("close liste => :");
                for (Noeud nc: closeList) {
                    System.out.println(nc.getCoordonne().x+" : " + nc.getCoordonne().y);
                }
            }
        }
    }
        /*
        while (nTmp.getCoordonne()!=objectif){
            nTmp.creerVoisin(objectif,mapMur);
            if (nTmp.voisin!=null){

            }
         */


/*
    public void cheminPlusCour(Noeud objectif, Noeud depart){
        closeList=new ArrayList<>();
        openList=new ArrayList<>();
        openList.add(depart);
        while (openList!=null){
            Noeud n = depiler(openList);
            if (n.isArrivee()){
                return reconstituer(n);
            }
            for(Noeud v: n.voisin){
                if (!existe(v)){

                }
            }
        }
    }

 */

    public boolean existe(Noeud n){
        if (closeList!=null) {
            for (Noeud nclose: closeList) {
                if(n.getCoordonne()==nclose.getCoordonne()) return true;
            }
        }
        if (openList!=null){
            for (Noeud nopen: openList){
                if(n.getCoordonne()==nopen.getCoordonne()){
                    if (n.getNbMouve()>nopen.getNbMouve()) return false;
                    else return true;
                }
            }
        }
        return false;
    }


    public Noeud depiler(ArrayList<Noeud> pile){
        if (pile.size()!=0) {
            if (pile.get(0)!=null) {
                Noeud n = pile.get(0);
                pile.remove(0);
                return n;
            }
        }
        return null;
    }

}
