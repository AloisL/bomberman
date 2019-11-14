package bomberman.model.strategie;

import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;
import bomberman.model.repo.AgentAction;
import bomberman.model.repo.enumDirection;

import static com.sun.activation.registries.LogSupport.log;

public abstract class StrategieAgents {
    protected BombermanGame mapGameBombermanGame;
    protected int viewNbBlocks;
    protected AbstractAgent agentCalling;

    //public AgentAction mouvStrategie(InfoAgent infoAgent,boolean agentInSight, ArrayList<Boolean> rockMap); //effectue le mouvement choisie par la strategie
    public  StrategieAgents(){}
    public  StrategieAgents(BombermanGame bombermanGame, AbstractAgent agent){
        this.agentCalling = agent;
        this.mapGameBombermanGame = bombermanGame;
    }

    public abstract AgentAction doStrategie();

    public AgentAction strategieAleatoire(){
        AgentAction resultat = AgentAction.MOVE_UP;
        int aleatoire = (int)(Math.random()*4);
        switch (aleatoire){
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
            default:
                log("mouvement non reconnue");
                break;

        }



        return resultat;
    }


    public boolean checkEnnemie(){

        for (AbstractAgent agent : this.mapGameBombermanGame.getAgents()) {
            for (int i = 1; i<=this.viewNbBlocks; i++) {
                if(agent.getId()!=agentCalling.getId()){
                    if((agent.getX() == agentCalling.getX()+i) || (agent.getX() == agentCalling.getX()-i) || (agent.getY() == agentCalling.getY()+i) || (agent.getY() == agentCalling.getY()-i) )
                        return true;
                }
            }
        }


        return false;
    }

    // verifie sur l'agents passer en parametre se trouve sur une diagonale haute
    // x et y sont les coordonner de l'objectif, i est le nombre de cases de recherche depuis la position de l'agentCalling
    private enumDirection isDiagonalTop(int x,int y, int i) {
        if ((x == this.agentCalling.getX() - i) && (y == agentCalling.getY() + i)) return enumDirection.HG;
        else if((x == this.agentCalling.getX() + i) && (y == agentCalling.getY() + i)) return enumDirection.HD;
    }

    // verifie sur l'agents passer en parametre se trouve sur une diagonale basse
    // x et y sont les coordonner de l'objectif, i est le nombre de cases de recherche depuis la position de l'agentCalling
    private enumDirection isDiagonalBottom(int x,int y, int i){
        if ((x == this.agentCalling.getX() - i) && (y == agentCalling.getY() - i)) return enumDirection.BG;
        else if((x == this.agentCalling.getX() + i) && (y == agentCalling.getY() - i)) return enumDirection.BH;
        else return enumDirection.STOP;
    }

    private enumDirection agentIsDiagonal(AbstractAgent agentAnalise, int i){

        isDiagonalTop(agentAnalise,))
    }


    private boolean isAxeDown(int y, int i){
        return y == agentCalling.getY() - i;
    }
    private boolean isAxeUp(int y, int i){
        return y == agentCalling.getY() + i;
    }
    private boolean isAxeLeft(int x, int i){
        return x == agentCalling.getX() - i;
    }
    private boolean isAxeRight(int x, int i){
        return x == agentCalling.getX() + i;
    }

    public AbstractAgent agentToKill(){

        if(checkEnnemie()) {
            for (AbstractAgent agent : this.mapGameBombermanGame.getAgents()) {
                for (int i = 1; i <= this.viewNbBlocks; i++) {
                    if (agent.getId() != agentCalling.getId()) {
                        if ((agent.getX() == agentCalling.getX() + i) || (agent.getX() == agentCalling.getX() - i) || (agent.getY() == agentCalling.getY() + i) || (agent.getY() == agentCalling.getY() - i))
                            return agent;
                        if (((agent.getX() == agentCalling.getX() + i) && (agent.getY() == agentCalling.getY() + i) )  || (agent.getX() == agentCalling.getX() - i) || (agent.getY() == agentCalling.getY() + i) || (agent.getY() == agentCalling.getY() - i))
                    }
                }
            }
        }


        return null;
    }






}