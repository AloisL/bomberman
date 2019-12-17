package bomberman.model.strategie;

import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;
import bomberman.model.repo.AgentAction;
import bomberman.model.repo.enumDirection;


import java.util.ArrayList;

import static com.sun.activation.registries.LogSupport.log;

public abstract class StrategieAgents {
    protected BombermanGame bombermanGame;
    protected ArrayList<ArrayList<Boolean>> mapChemin;
    protected int viewNbBlocks;
    protected AbstractAgent agentCalling;

    //public AgentAction mouvStrategie(InfoAgent infoAgent,boolean agentInSight, ArrayList<Boolean> rockMap); //effectue le mouvement choisie par la strategie
    public  StrategieAgents(){}

    public  StrategieAgents(BombermanGame bombermanGame, AbstractAgent agent){
        this.agentCalling = agent;
        this.bombermanGame = bombermanGame;
        //mapChemin = makeMapbinaire(bombermanGame.getMap())
    }

    public abstract AgentAction doStrategie();

    public AgentAction strategieAleatoire(Coordonne destination){
        AgentAction resultat = AgentAction.MOVE_UP;
        System.out.println(bombermanGame.getMap().getSizeX()+" "+bombermanGame.getMap().getSizeX());
        //int aleatoire = (int)(Math.random()*7);
       destination= afficherMur();
        switch (donneDirection(destination)){
            case 0:
                resultat= AgentAction.MOVE_UP;
                break;
            case 1:
                resultat= AgentAction.MOVE_DOWN;
                break;
            case 2:
                resultat= AgentAction.MOVE_RIGHT;
                break;
            case 3:
                resultat= AgentAction.MOVE_LEFT;
                break;
            case 4:
                resultat= AgentAction.JUMP_DOWN;
                break;
            case 5:
                resultat= AgentAction.JUMP_LEFT;
                break;
            case 6:
                resultat= AgentAction.JUMP_RIGHT;
                break;
            case 7:
                resultat= AgentAction.JUMP_UP;
                break;
            case 8:
                resultat= AgentAction.STOP;
            default:
                log("mouvement non reconnue");
                break;

        }



        return resultat;
    }

    public int donneDirection(Coordonne direction){
        int diffX = direction.x - agentCalling.getX();
        int diffY = direction.y - agentCalling.getY();
        if (diffY<0){
            return 0;
        } else if (diffY>0) return 1;
        if (diffX>0) return 2;
        else if (diffX<0) return 3;
        return 8;

    }



    public Coordonne afficherMur(){
        /*
        for (int y=0 ; y<bombermanGame.getMap().getSizeY();y++){
            System.out.println("");
            for (int x=0;x<bombermanGame.getMap().getSizeX();x++){
                System.out.print("| "+bombermanGame.getMap().get_walls()[x][y]);

            }
        }*/
        Coordonne objectif = new Coordonne(17,6);
        Astart astart = new Astart(bombermanGame,agentCalling,objectif);
        Coordonne coordonne = new Coordonne();
        Noeud noeud=astart.chemin(objectif,astart.creerOrigine());
               coordonne=noeud.getCoordonne();
        System.out.print(coordonne.x+" : "+coordonne.y);
        return coordonne;
    }


    public boolean checkEnnemie(){

        for (AbstractAgent agent : this.bombermanGame.getAgents()) {
            for (int i = 1; i<=this.viewNbBlocks; i++) {
                if(agent.getId()!=agentCalling.getId()){
                    if((agent.getX() == agentCalling.getX()+i) || (agent.getX() == agentCalling.getX()-i) || (agent.getY() == agentCalling.getY()+i) || (agent.getY() == agentCalling.getY()-i) )
                        return true;
                }
            }
        }


        return false;
    }




    /*
    // verifie sur l'agents passer en parametre se trouve sur une diagonale haute
    // x et y sont les coordonner de l'objectif, i est le nombre de cases de recherche depuis la position de l'agentCalling
    private enumDirection isDiagonalTop(int x,int y, int i,int z) {
        if ((x == this.agentCalling.getX() - i) && (y == agentCalling.getY() + z)) return enumDirection.HG;
        else if((x == this.agentCalling.getX() + i) && (y == agentCalling.getY() + z)) return enumDirection.HD;
        return enumDirection.STOP;
    }

    // verifie sur l'agents passer en parametre se trouve sur une diagonale basse
    // x et y sont les coordonner de l'objectif, i est le nombre de cases de recherche depuis la position de l'agentCalling
    private enumDirection isDiagonalBottom(int x,int y, int i,int z){
        if ((x == this.agentCalling.getX() - i) && (y == agentCalling.getY() - z)) return enumDirection.BG;
        else if((x == this.agentCalling.getX() + i) && (y == agentCalling.getY() - z)) return enumDirection.BH;
         return enumDirection.STOP;
    }





/*
    private enumDirection isAxeDown(int y, int i){
        if (y == agentCalling.getY() - i) return enumDirection.B;
        return enumDirection.STOP;
    }
    private enumDirection isAxeUp(int y, int i){
        if (y == agentCalling.getY() + i) return enumDirection.H;
        return enumDirection.STOP;
    }
    private enumDirection isAxeLeft(int x, int i){
        if ( x == agentCalling.getX() - i) return enumDirection.G;
        return  enumDirection.STOP;
    }
    private enumDirection isAxeRight(int x, int i){
        if (x == agentCalling.getX() + i) return enumDirection.D ;
        return enumDirection.STOP;
    }

    private enumDirection isDiagonal(int x,int y,int i,int z){
        enumDirection res=isDiagonalBottom(x, y, i, z);
        if (res!=enumDirection.STOP) return res;
        res = isDiagonalTop(x, y, i, z);
        return res;
    }

    private enumDirection isLigne(int x,int y,int i){
        if (enumDirection.STOP!=isAxeDown(y, i)) return isAxeDown(y, i);
        if (enumDirection.STOP!=isAxeLeft(x, i)) return isAxeLeft(x, i);
        if (enumDirection.STOP!=isAxeUp(y, i)) return isAxeUp(y, i);
        return (isAxeRight(x, i));
    }




    public enumDirection schearchObjectif(int x,int y){

        if(checkEnnemie()) {
            for (AbstractAgent agent : this.bombermanGame.getAgents()) {
                if(agent.getType() == 'B'){
                    for (int i = 0; i <= this.viewNbBlocks; i++) {
                        for (int z=0;z<=this.viewNbBlocks; z++ ) {
                            enumDirection direction = isLigne(x,y,i);
                            if (direction==enumDirection.STOP){
                                direction = isDiagonal(x,y,i,z);
                                if(direction!=enumDirection.STOP){
                                    return direction;
                                }
                            }
                            else return direction;
                        }
                    }
                }
            }
        }


        return enumDirection.STOP;
    }

 */








}