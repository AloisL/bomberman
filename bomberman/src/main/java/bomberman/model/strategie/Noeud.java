package bomberman.model.strategie;

import java.util.ArrayList;

public class Noeud {
    public ArrayList<Noeud> voisin;
    private Coordonne coordonne;
    private int nbMouve;
    private int Hcost;
    private Noeud origine;


    public Noeud(){}

    public Noeud(Coordonne coordo,int nbDeplacement, int valheuri,Noeud origine){
            this.coordonne = coordo;
            this.Hcost = valheuri;
            this.nbMouve = nbDeplacement;
            this.origine = origine;

    }

    public Noeud(Coordonne coordo,Coordonne obj,int nbDeplacement,Noeud origine){
        this.coordonne = coordo;
        this.Hcost = creerHeuri(coordo,obj);
        this.nbMouve = nbDeplacement;
        this.origine = origine;

    }

    public void creerVoisin(Coordonne objectif, boolean[][] mapMur){
        ArrayList<Noeud> tabVoisin=new ArrayList<>();
        Coordonne c1= new Coordonne(coordonne.x+1,coordonne.y);
        Coordonne c2= new Coordonne(coordonne.x-1,coordonne.y);
        Coordonne c3= new Coordonne(coordonne.x,coordonne.y+1);
        Coordonne c4= new Coordonne(coordonne.x,coordonne.y-1);


        if (peutCreerNoeud(c1,mapMur)){
            Noeud n1=new Noeud(c1,nbMouve+1,creerHeuri(c1,objectif),this);
            tabVoisin.add(n1);
        }
        if (peutCreerNoeud(c2,mapMur)){
            Noeud n2=new Noeud(c2,nbMouve+1,creerHeuri(c2,objectif),this);
            tabVoisin.add(n2);
        }
        if (peutCreerNoeud(c3,mapMur)){
            Noeud n3=new Noeud(c3,nbMouve+1,creerHeuri(c3,objectif),this);
            tabVoisin.add(n3);
        }
        if (peutCreerNoeud(c4,mapMur)){
            Noeud n4=new Noeud(c4,nbMouve+1,creerHeuri(c4,objectif),this);
            tabVoisin.add(n4);
        }
        voisin= tabVoisin;
    }

    public boolean peutCreerNoeud(Coordonne coordo,boolean[][] mapMur){
        Noeud n=new Noeud();
        if (!estDejaVoisin(coordo)){
            if (!mapMur[coordo.x][coordo.y]) {
                return true;
            }
        }
        return false;
    }

    public boolean estDejaVoisin(Coordonne c){
        if (this.origine!=null) {
            if (this.origine.coordonne==c) return true;}
        return false;
    }

    public int creerHeuri(Coordonne c,Coordonne o){
        int X = o.x - c.x;
        int Y = o.y - c.y;
        X=Math.abs(X);
        Y=Math.abs(Y);
        return (X+Y);

    }

    public Noeud compareHeuri(Noeud n){
        if(n.Hcost<=Hcost){
            return n;
        }
        return this;
    }




    public Coordonne getCoordonne() {
        return coordonne;
    }

    public void setCoordonne(Coordonne coordonne) {
        this.coordonne = coordonne;
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

    public Noeud getOrigine(){
        return origine;
    }
}
