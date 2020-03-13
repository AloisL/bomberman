package bomberman.model.strategie;

import bomberman.model.agent.AbstractAgent;
import bomberman.model.engine.BombermanGame;
import bomberman.model.engine.enums.AgentAction;
import bomberman.model.engine.infotype.InfoBomb;
import bomberman.model.strategie.utils.AlgorithmeAEtoile;
import bomberman.model.strategie.utils.Coordonnee;
import bomberman.model.strategie.utils.Noeud;

import java.util.ArrayList;

public abstract class StrategieAgents {
    protected BombermanGame bombermanGame;
    protected ArrayList<ArrayList<Boolean>> mapChemin;
    protected int viewNbBlocks;
    protected AbstractAgent agentCalling;

    public StrategieAgents() {
    }

    public StrategieAgents(BombermanGame bombermanGame, AbstractAgent agent) {
        agentCalling = agent;
        this.bombermanGame = bombermanGame;
        //mapChemin = makeMapbinaire(bombermanGame.getMap())
    }

    public abstract AgentAction doStrategie();

    //utilisé pour eviter des bug qui non malheuresement pas reussi a etre corrigé a temps
    public AgentAction strategieAleatoire() {
        AgentAction resultat = AgentAction.MOVE_UP;
        int aleatoire = (int) (Math.random() * 4);
        switch (aleatoire) {
            case 0:
                resultat = AgentAction.MOVE_UP;
                break;
            case 1:
                resultat = AgentAction.MOVE_DOWN;
                break;
            case 2:
                resultat = AgentAction.MOVE_RIGHT;
                break;
            case 3:
                resultat = AgentAction.MOVE_LEFT;
                break;
            default:
                break;
        }
        return resultat;
    }

    public AgentAction doMouvement(Coordonnee objectif) {
        AgentAction resultat = AgentAction.STOP;
        objectif = chercherDirection(objectif);
        switch (donneDirection(objectif)) {
            case 0:
                resultat = AgentAction.MOVE_UP;
                break;
            case 1:
                resultat = AgentAction.MOVE_DOWN;
                break;
            case 2:
                resultat = AgentAction.MOVE_RIGHT;
                break;
            case 3:
                resultat = AgentAction.MOVE_LEFT;
                break;
            case 4:
                resultat = AgentAction.JUMP_DOWN;
                break;
            case 5:
                resultat = AgentAction.JUMP_LEFT;
                break;
            case 6:
                resultat = AgentAction.JUMP_RIGHT;
                break;
            case 7:
                resultat = AgentAction.JUMP_UP;
                break;
            case 8:
                resultat = AgentAction.STOP;
            default:
                break;
        }
        return resultat;
    }

    public int donneDirection(Coordonnee direction) {
        if (direction.x != 0) {
            int diffX = direction.x - agentCalling.getX();
            int diffY = direction.y - agentCalling.getY();
            if (diffY < 0) {
                return 0;
            } else if (diffY > 0) return 1;
            if (diffX > 0) return 2;
            else if (diffX < 0) return 3;
        }
        return 8;
    }

    public Coordonnee chercherDirection(Coordonnee objectif) {
        AlgorithmeAEtoile algorithmeAEtoile = new AlgorithmeAEtoile(bombermanGame, agentCalling, objectif);
        Coordonnee coordonnee = new Coordonnee(0, 0);
        Noeud noeud = algorithmeAEtoile.chemin(objectif, algorithmeAEtoile.creerOrigine());
        if (noeud != null) {
            coordonnee = noeud.getCoordonnee();
        }
               /*
        System.out.print(coordonnee.x+" : "+coordonnee.y);
                */
        return coordonnee;
    }

    public InfoBomb checkSiBesoinSafe() {
        for (InfoBomb b : bombermanGame.getBombs()) {
            boolean test = isInRange(b);
            if (isInRange(b)) {

                return b;  //zoneSafe(b);
            }
        }
        return null;
    }

    public Coordonnee checkEnnemie() {
        Coordonnee c = new Coordonnee();
        for (AbstractAgent agentP : bombermanGame.getPlayers()) {
            c.x = agentP.getX();
            c.y = agentP.getY();
        }
        return c;

    }

    public boolean isInRange(InfoBomb b) {
        if (((agentCalling.getX() <= (b.getX() + b.getRange())) && (agentCalling.getX() >= (b.getX() - b.getRange()))) && (agentCalling.getY() == b.getY())) {
            return true;
        }
        if (((agentCalling.getY() <= (b.getY() + b.getRange())) && (agentCalling.getY() >= (b.getY() - b.getRange()))) && (agentCalling.getX() == b.getX())) {

            return true;
        }
        return false;
    }

    public boolean isInRange(Coordonnee self, int range, Coordonnee ennemie) {

        if (((ennemie.x <= (self.x + range)) && (ennemie.x >= (self.x - range))) && (ennemie.y) == self.y) {
            return true;
        }
        if (((ennemie.y <= (self.y + range)) && (ennemie.y >= (self.y - range))) && ((ennemie.x) == self.x)) {
            return true;
        }
        return false;
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
