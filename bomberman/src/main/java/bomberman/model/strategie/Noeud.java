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
        } else System.out.println("Pas de Voisin c1");
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



/*
    // return true si la difference(en valeur obsolue) de chemin entre coordonne X et plus grande(ou égale) que la difference de coordonné Y
    public boolean difference_X_Y(){
        int diffX = objectif.x - coordonne.x;
        int diffY = objectif.y - coordonne.y;
        diffX=Math.abs(diffX);
        diffY=Math.abs(diffY);
        return (diffX>=diffY);

    }

    public int differenceX(){
        int diffX = objectif.x - coordonne.x;
        return diffX;

    }

    public int differenceY(){
        int diffY = objectif.y - coordonne.y;
        return diffY;

    }

    public Coordonne creerHeuri(int x,int y)

    public boolean searchChemin(){
        if (!arrivee) {
            if (difference_X_Y()){
                int diffX=differenceX();
                boolean cheminArrivee=false;
                while (((coordonne.x!=objectif.x)&&(difference_X_Y())) || (!cheminArrivee)){
                    if (diffX<0){
                        if(voisinIsArrived(-1,0)){

                        }
                    }
                }
            }
            else {
                int diffY=differenceY();

            }
        }
    }





    public Noeud creerVoisin(Coordonne objectif,Coordonne newPosition, boolean[][] mapMur,Noeud origineVoisin){

        if(!mapMur[newPosition.x][newPosition.y]){
            Noeud newNoeud = new Noeud(newPosition, origineVoisin.getNbMouve() + 1, creerHeuri(),objectif )
            if(objectif!=newPosition) {
               Noeud exixtant = noeudExiste(newPosition);
                if (exixtant!=null) {

                } else {
                    if(compareNbMove2noeud(exixtant,newNoeud)<0){
                        return exixtant;
                    }
                    else return newNoeud;
                }
            }
            else
        }
    }

    public int compareNbMove2noeud(Noeud n1, Noeud n2){
        if(n1.getNbMouve()<n2.getNbMouve())
            return 1;
        else {
            if (n1.getNbMouve()==n2.getNbMouve())
                return 0;
            else return -1;

        }
    }

    public int compareH2noeud(Noeud n1, Noeud n2){
        if(n1.getHcost()<n2.getHcost())
            return 1;
        else {
            if (n1.getHcost()==n2.getHcost())
                return 0;
            else return -1;

        }
    }



    public Noeud noeudExiste(Coordonne point){
        if (voisin!=null) {
            for (Noeud n : voisin) {
                if (n.coordonne == coordonne) return n;
                Noeud noeud= n.noeudExiste(point);
                if (noeud!=null) return noeud;

            }
        }
        return null;
    }
*/

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
