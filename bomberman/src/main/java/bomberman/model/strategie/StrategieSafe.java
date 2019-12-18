package bomberman.model.strategie;

import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;
import bomberman.model.engine.InfoBomb;
import bomberman.model.repo.AgentAction;

public class StrategieSafe extends StrategieAgents {

    public StrategieSafe() {
    }

    public StrategieSafe(BombermanGame bombermanGame, AbstractAgent agent) {
        super(bombermanGame, agent);
        doStrategie();
    }

    @Override
    public AgentAction doStrategie() {
        Coordonnee c = checkSiBesoinSafe();
        if (c.x != 0) {
            System.out.println("Pourquoi tu bouge pas!");
            return doMouvement(c);
        }
        return AgentAction.STOP;
    }

    //verifie si besoin de se mettre en zone de securité sinon renvoie (0,0)
    public Coordonnee checkSiBesoinSafe() {
        for (InfoBomb b : bombermanGame.getBombs()) {
            if (isInRange(b)) {
                return zoneSafe(b);
            }
        }
        Coordonnee c = new Coordonnee(0, 0);
        return c;
    }


    //methode qui cherche une zone safe adjacente au rayon de la bombe
    public Coordonnee zoneSafe(InfoBomb b) {
        int diffX = agentCalling.getX() - b.getX();
        int diffY = agentCalling.getY() - b.getY();
        if (diffX != 0) diffX = Math.abs(diffX) / diffX;
        if (diffY != 0) diffY = Math.abs(diffY) / diffY;

        for (int i = 1; i <= b.getRange(); i++) {
            //permet de virifier chaque case adjacente au rayon de la bombe pour trouvée une case "safe"
            Coordonnee c1 = new Coordonnee(b.getX() + (i * diffX) + (diffY), b.getY() + (i * diffY) + diffX);
            Coordonnee c2 = new Coordonnee(b.getX() + (i * diffX) - diffY, b.getY() + (i * diffY) - diffX);
            if (bombermanGame.isFree(c1)) return c1;
            if (bombermanGame.isFree(c2)) return c2;
        }
        //la coordonnée de l'extrimité de la range
        Coordonnee c = new Coordonnee(b.getX() + (b.getRange() * diffX) + (diffY),
                b.getY() + (b.getRange() * diffY) + diffX);
        if (bombermanGame.isFree(c)) {
            return c;
        }
        c.x = 0;
        c.y = 0;
        return c;
    }
//int x = 10, y = 20;
//int max = (x < y) ? y : x ; //Maintenant, max vaut 20
//$number, ': ', $number ? abs($number) / $number : 0

    public boolean isInRange(InfoBomb b) {
        if (((agentCalling.getX() < b.getX() + b.getRange()) && (agentCalling.getX() > b.getX() - b.getRange())) && (agentCalling.getY() == b.getY())) {
            return true;
        }
        if (((agentCalling.getY() < b.getY() + b.getRange()) && (agentCalling.getX() > b.getY() - b.getRange())) && (agentCalling.getX() == b.getX())) {
            return true;
        }
        return false;
    }


}
