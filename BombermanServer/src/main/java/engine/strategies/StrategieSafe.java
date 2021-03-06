package engine.strategies;

import common.enums.AgentAction;
import common.infotypes.InfoBomb;
import engine.BombermanGame;
import engine.agents.AbstractAgent;
import engine.strategies.utils.Coordonnee;

public class StrategieSafe extends StrategieAgents {

    public StrategieSafe(BombermanGame bombermanGame, AbstractAgent agent) {
        super(bombermanGame, agent);
    }

    @Override
    public AgentAction doStrategie() {
        Coordonnee c = zoneSafe(checkSiBesoinSafe());
        if (c.x != 0) {
            return doMouvement(c);
        }
        return AgentAction.STOP;
    }

    //methode qui cherche une zone safe adjacente au rayon de la bombe
    public Coordonnee zoneSafe(InfoBomb b) {
        if (b != null) {
            int diffX = agentCalling.getX() - b.getX();
            int diffY = agentCalling.getY() - b.getY();
            if (diffX != 0) diffX = Math.abs(diffX) / diffX;
            if (diffY != 0) diffY = Math.abs(diffY) / diffY;
            Coordonnee c1 = new Coordonnee(agentCalling.getX() + 1, agentCalling.getY());
            Coordonnee c2 = new Coordonnee(agentCalling.getX() - 1, agentCalling.getY());
            Coordonnee c3 = new Coordonnee(agentCalling.getX(), agentCalling.getY() + 1);
            Coordonnee c4 = new Coordonnee(agentCalling.getX(), agentCalling.getY() - 1);
            if (isFree(c1) || isFree(c2) || isFree(c3) || isFree(c4)) {
                for (int i = 0; i <= b.getRange(); i++) {

                    if (diffX == 0 && diffY == 0) {
                        c1 = new Coordonnee(agentCalling.getX() + 1, agentCalling.getY() + 1);
                        c2 = new Coordonnee(agentCalling.getX() - 1, agentCalling.getY() + 1);
                        c3 = new Coordonnee(agentCalling.getX() + 1, agentCalling.getY() - 1);
                        c4 = new Coordonnee(agentCalling.getX() - 1, agentCalling.getY() - 1);
                    } else {
                        if (diffX == 0) {
                            c1 = new Coordonnee(agentCalling.getX() + 1, agentCalling.getY() + i);
                            c2 = new Coordonnee(agentCalling.getX() - 1, agentCalling.getY() + i);
                            c3 = new Coordonnee(agentCalling.getX() + 1, agentCalling.getY() - i);
                            c4 = new Coordonnee(agentCalling.getX() - 1, agentCalling.getY() - i);
                        } else if (diffY == 0) {
                            c1 = new Coordonnee(agentCalling.getX() + i, agentCalling.getY() + 1);
                            c2 = new Coordonnee(agentCalling.getX() + i, agentCalling.getY() - 1);
                            c3 = new Coordonnee(agentCalling.getX() - i, agentCalling.getY() + 1);
                            c4 = new Coordonnee(agentCalling.getX() - i, agentCalling.getY() - 1);
                        }
                    }

                    if (isFree(c1) && (!isInRange(b, c1))) {
                        if (doMouvement(c4) != AgentAction.STOP)
                            return c1;
                    }
                    if (isFree(c2) && (!isInRange(b, c2))) {
                        if (doMouvement(c4) != AgentAction.STOP)
                            return c2;
                    }
                    if (isFree(c3) && (!isInRange(b, c3))) {
                        if (doMouvement(c4) != AgentAction.STOP)
                            return c3;
                    }
                    if (isFree(c4) && (!isInRange(b, c4))) {
                        if (doMouvement(c4) != AgentAction.STOP)
                            return c4;
                    }
                }

                //la coordonnée de l'extrimité de la range
                Coordonnee c = new Coordonnee(b.getX() + (b.getRange() * diffX) + (diffY), b.getY() + (b.getRange() * diffY) + diffX);
                if (isFree(c) && (isInRange(b, c))) {
                    if (doMouvement(c4) != AgentAction.STOP)
                        return c;
                }
            }
        }

        Coordonnee c = new Coordonnee(0, 0);
        return c;
    }

    public boolean isInRange(InfoBomb b, Coordonnee newCoord) {
        if (((newCoord.x <= b.getX() + b.getRange()) && (newCoord.x >= b.getX() - b.getRange())) && (newCoord.y == b.getY())) {
            return true;
        }
        if (((newCoord.y <= b.getY() + b.getRange()) && (newCoord.y >= b.getY() - b.getRange())) && (newCoord.x == b.getX())) {
            return true;
        }
        return false;
    }

    public boolean isFree(Coordonnee c) {
        if (c.x > 0 && c.y > 0 && c.x < bombermanGame.getMap().getSizeX() && c.y < bombermanGame.getMap().getSizeY()) {
            if (bombermanGame.getBreakableWalls()[c.x][c.y] || bombermanGame.getMap().get_walls()[c.x][c.y]) {
                return false;
            }
            for (InfoBomb b : bombermanGame.getBombs()) {
                if (b.getX() == c.x && b.getY() == c.y) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
