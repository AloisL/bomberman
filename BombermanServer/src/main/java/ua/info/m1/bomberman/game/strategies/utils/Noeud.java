package ua.info.m1.bomberman.game.strategies.utils;

import java.util.ArrayList;

public class Noeud {

    public ArrayList<Noeud> voisin;
    private Coordonnee coordonnee;
    private int nbMouve;
    private int Hcost;
    private Noeud origine;


    public Noeud() {
    }

    public Noeud(Coordonnee perso, Coordonnee objectif) {
        creerHeuri(perso, objectif);
    }

    public Noeud(Coordonnee coordo, int nbDeplacement, int valheuri, Noeud origine) {
        coordonnee = coordo;
        Hcost = valheuri;
        nbMouve = nbDeplacement;
        this.origine = origine;

    }

    public Noeud(Coordonnee coordo, Coordonnee obj, int nbDeplacement, Noeud origine) {
        coordonnee = coordo;
        Hcost = creerHeuri(coordo, obj);
        nbMouve = nbDeplacement;
        this.origine = origine;

    }

    public void creerVoisin(Coordonnee objectif, boolean[][] mapMur) {
        ArrayList<Noeud> tabVoisin = new ArrayList<>();

        Coordonnee c1 = new Coordonnee(coordonnee.x + 1, coordonnee.y);
        Coordonnee c2 = new Coordonnee(coordonnee.x - 1, coordonnee.y);
        Coordonnee c3 = new Coordonnee(coordonnee.x, coordonnee.y + 1);
        Coordonnee c4 = new Coordonnee(coordonnee.x, coordonnee.y - 1);

        if (peutCreerNoeud(c1, mapMur)) {
            Noeud n1 = new Noeud(c1, nbMouve + 1, creerHeuri(c1, objectif), this);
            tabVoisin.add(n1);
        }
        if (peutCreerNoeud(c2, mapMur)) {
            Noeud n2 = new Noeud(c2, nbMouve + 1, creerHeuri(c2, objectif), this);
            tabVoisin.add(n2);
        }
        if (peutCreerNoeud(c3, mapMur)) {
            Noeud n3 = new Noeud(c3, nbMouve + 1, creerHeuri(c3, objectif), this);
            tabVoisin.add(n3);
        }
        if (peutCreerNoeud(c4, mapMur)) {
            Noeud n4 = new Noeud(c4, nbMouve + 1, creerHeuri(c4, objectif), this);
            tabVoisin.add(n4);
        }
        voisin = tabVoisin;
    }

    public boolean peutCreerNoeud(Coordonnee coordo, boolean[][] mapMur) {
        Noeud n = new Noeud();
        if (!estDejaVoisin(coordo)) {
            if (!mapMur[coordo.x][coordo.y]) {
                return true;
            }
        }
        return false;
    }

    public boolean estDejaVoisin(Coordonnee c) {
        if (origine != null) {
            if (origine.coordonnee == c) return true;
        }
        return false;
    }

    public int creerHeuri(Coordonnee c, Coordonnee o) {
        int X = o.x - c.x;
        int Y = o.y - c.y;
        X = Math.abs(X);
        Y = Math.abs(Y);
        return (X + Y);

    }

    public Noeud compareHeuri(Noeud n) {
        if (n.Hcost <= Hcost) {
            return n;
        }
        return this;
    }


    public Coordonnee getCoordonnee() {
        return coordonnee;
    }

    public void setCoordonnee(Coordonnee coordonnee) {
        this.coordonnee = coordonnee;
    }

    public int getNbMouve() {
        return nbMouve;
    }

    public void setNbMouve(int nbMouve) {
        this.nbMouve = nbMouve;
    }

    public int getHcost() {
        return Hcost;
    }

    public void setHcost(int hcost) {
        Hcost = hcost;
    }

    public Noeud getOrigine() {
        return origine;
    }
}
