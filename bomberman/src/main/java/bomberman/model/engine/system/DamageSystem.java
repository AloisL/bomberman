package bomberman.model.engine.system;

import bomberman.model.BombermanGame;
import bomberman.model.agent.AbstractAgent;
import bomberman.model.agent.BombermanAgent;
import bomberman.model.engine.enums.ItemType;
import bomberman.model.engine.enums.StateBomb;
import bomberman.model.engine.info.InfoBomb;
import bomberman.model.engine.info.InfoItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.util.ArrayList;

public class DamageSystem extends AbstractSystem {

    final static Logger log = (Logger) LogManager.getLogger(DamageSystem.class);

    ArrayList<AbstractAgent> agentsToBeRemoved;

    public DamageSystem(BombermanGame bombermanGame) {
        super(bombermanGame);
    }

    /**
     * Méthode de gestion des bombes pour un tour de jeu
     */
    @Override
    public void run() {
        agentsToBeRemoved = new ArrayList<>();

        bombDamages();
        closeCombatDamages();

        for (AbstractAgent agent : agentsToBeRemoved) {
            agents.remove(agent);
            players.remove(agent);
            if (players.size() == 0) bombermanGame.gameOver();
        }
    }

    private void bombDamages() {
        ArrayList<InfoBomb> bombToBeRemoved = new ArrayList<>();

        for (InfoBomb bomb : bombs) {
            switch (bomb.getStateBomb()) {
                case Step1:
                    bomb.setStateBomb(StateBomb.Step2);
                    break;
                case Step2:
                    bomb.setStateBomb(StateBomb.Step3);
                    break;
                case Step3:
                    bomb.setStateBomb(StateBomb.Boom);
                    break;
                case Boom:
                    bombToBeRemoved.add(bomb);
                default:
                    bomb.setStateBomb(StateBomb.Step1);
                    break;
            }
        }

        for (InfoBomb bomb : bombToBeRemoved) {
            bomb.getOwner().freeBombSlot();
            bombs.remove(bomb);
        }

        for (InfoBomb bomb : bombs) if (bomb.getStateBomb() == StateBomb.Boom) bombHit(bomb);
    }

    private void closeCombatDamages() {
        for (AbstractAgent agent : agents) {
            for (AbstractAgent player : players) {
                BombermanAgent bombermanAgent = (BombermanAgent) player;
                if ((bombermanAgent != agent) && (agent.getX() == bombermanAgent.getX()) && (agent.getY() == bombermanAgent.getY())) {
                    bombermanAgent.removeLife();
                    if (bombermanAgent.isDead()) agentsToBeRemoved.add(bombermanAgent);
                }
            }
        }
    }

    public void bombHit(InfoBomb bomb) {
        int range = bomb.getRange();
        int posXbomb = bomb.getX();
        int posYbomb = bomb.getY();

        // Tue les agents dans la range de la bombe
        for (AbstractAgent agent : agents) {
            int posXagent = agent.getX();
            int posYagent = agent.getY();

            for (int i = 0; i <= range; i++) {
                if (posXagent == posXbomb) {
                    if ((posYagent == (posYbomb + i)) || (posYagent == (posYbomb - i))) {
                        if (agent.getClass() == BombermanAgent.class) {
                            BombermanAgent bombermanAgent = (BombermanAgent) agent;
                            if (!bombermanAgent.isInvincible()) bombermanAgent.removeLife();
                            if (bombermanAgent.isDead()) agentsToBeRemoved.add(bombermanAgent);
                        } else {
                            agentsToBeRemoved.add(agent);
                        }
                    }
                } else if (posYagent == posYbomb) {
                    if ((posXagent == (posXbomb + i)) || (posXagent == (posXbomb - i))) {
                        if (agent.getClass() == BombermanAgent.class) {
                            BombermanAgent bombermanAgent = (BombermanAgent) agent;
                            if (!bombermanAgent.isInvincible()) bombermanAgent.removeLife();
                            if (bombermanAgent.isDead()) agentsToBeRemoved.add(bombermanAgent);
                        } else {
                            agentsToBeRemoved.add(agent);
                        }
                    }
                }
            }
        }

        // Détruit les murs dans la range de la bombe
        for (int i = 0; i <= range; i++) {
            if (breakableWalls[posXbomb][posYbomb + i]) {
                breakableWalls[posXbomb][posYbomb + i] = false;
                int randomItem = (int) (Math.random() * 2);
                if (randomItem == 0) {
                    randomItem = (int) (Math.random() * 5);
                    items.add(new InfoItem(posXbomb, posYbomb + i, getInfoItemFromInt(randomItem)));
                }
            }
            if (breakableWalls[posXbomb][posYbomb - i]) {
                breakableWalls[posXbomb][posYbomb - i] = false;
                int randomItem = (int) (Math.random() * 2);
                if (randomItem == 0) {
                    randomItem = (int) (Math.random() * 5);
                    items.add(new InfoItem(posXbomb, posYbomb - i, getInfoItemFromInt(randomItem)));
                }
            }
        }
        for (int i = 0; i <= range; i++) {
            if (breakableWalls[posXbomb + i][posYbomb]) {
                breakableWalls[posXbomb + i][posYbomb] = false;
                int randomItem = (int) (Math.random() * 2);
                if (randomItem == 0) {
                    randomItem = (int) (Math.random() * 5);
                    items.add(new InfoItem(posXbomb + i, posYbomb, getInfoItemFromInt(randomItem)));
                }
            }
            if (breakableWalls[posXbomb - i][posYbomb]) {
                breakableWalls[posXbomb - i][posYbomb] = false;
                int randomItem = (int) (Math.random() * 2);
                if (randomItem == 0) {
                    randomItem = (int) (Math.random() * 5);
                    items.add(new InfoItem(posXbomb - i, posYbomb, getInfoItemFromInt(randomItem)));
                }
            }
        }
    }

    public ItemType getInfoItemFromInt(int i) {
        switch (i) {
            case 0:
                return ItemType.FIRE_UP;
            case 1:
                return ItemType.FIRE_DOWN;
            case 2:
                return ItemType.BOMB_UP;
            case 3:
                return ItemType.BOMB_DOWN;
            case 4:
                return ItemType.FIRE_SUIT;
            case 5:
                return ItemType.SKULL;
            default:
                log.error("Item inconnu ==> " + i);
                return null;
        }
    }

}
