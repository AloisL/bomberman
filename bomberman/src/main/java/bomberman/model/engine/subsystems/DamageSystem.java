package bomberman.model.engine.subsystems;

import bomberman.model.agent.AbstractAgent;
import bomberman.model.agent.BombermanAgent;
import bomberman.model.engine.BombermanGame;
import bomberman.model.engine.enums.ItemType;
import bomberman.model.engine.enums.StateBomb;
import bomberman.model.engine.infotype.InfoBomb;
import bomberman.model.engine.infotype.InfoItem;
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
        // Vidange de la liste des agents à supprimer
        agentsToBeRemoved = new ArrayList<>();

        // Explosion des bombes un tour sur deux
        if (bombermanGame.getCurrentTurn() % 2 == 0)
            explodeBombs();

        // Dégats au corps à corps
        closeCombatDamages();

        // Suppression des agents morts pendant le tour de jeu
        removeDeadAgents();
    }

    /**
     * Méthode de gestion de l'explosion des bombes
     */
    private void explodeBombs() {
        // Vidange de la liste des bombes à supprimer
        ArrayList<InfoBomb> bombsToExplode = new ArrayList<>();

        /* On fait avance la liste des états des bombes, on place les bombes dans la liste des
            bombes à exploser si elles atteingnent le StateBomb Boom */
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
                    bombsToExplode.add(bomb);
                default:
                    bomb.setStateBomb(StateBomb.Step1);
                    break;
            }
        }

        // Pour chacunes des bombes allant exploser, on redonne une bombe à son lanceur
        for (InfoBomb bomb : bombsToExplode) {
            bomb.getOwner().freeBombSlot();
            bombs.remove(bomb);
        }

        // On fait exploser toutes les bombes devant exploser à ce tour
        for (InfoBomb bomb : bombs) if (bomb.getStateBomb() == StateBomb.Boom) explode(bomb);
    }

    /**
     * Méthode de gestion des dommages corps à corps subis par les joueurs (BombermanAgents)
     */
    private void closeCombatDamages() {
        for (AbstractAgent agent : agents) {
            for (AbstractAgent player : players) {
                BombermanAgent bombermanAgent = (BombermanAgent) player;
                if ((bombermanAgent != agent) && (agent.getX() == bombermanAgent.getX()) && (agent.getY() == bombermanAgent.getY())) {
                    hit(bombermanAgent, false);
                }
            }
        }
    }

    /**
     * Méthode permettant de faire exploser une bombe
     *
     * @param bomb
     */
    private void explode(InfoBomb bomb) {
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
                        hit(agent, true);
                    }
                } else if (posYagent == posYbomb) {
                    if ((posXagent == (posXbomb + i)) || (posXagent == (posXbomb - i))) {
                        hit(agent, true);
                    }
                }
            }
        }

        // Détruit les murs dans la range de la bombe
        for (int i = 0; i <= range; i++) {
            if (posYbomb + i < breakableWalls[posXbomb].length && breakableWalls[posXbomb][posYbomb + i]) {
                breakableWalls[posXbomb][posYbomb + i] = false;
                int randomItem = (int) (Math.random() * 2);
                if (randomItem == 0) {
                    randomItem = (int) (Math.random() * 5);
                    items.add(new InfoItem(posXbomb, posYbomb + i, ItemType.values()[randomItem]));
                }
            }
            if (posYbomb - i > 0 && breakableWalls[posXbomb][posYbomb - i]) {
                breakableWalls[posXbomb][posYbomb - i] = false;
                int randomItem = (int) (Math.random() * 2);
                if (randomItem == 0) {
                    randomItem = (int) (Math.random() * 5);
                    items.add(new InfoItem(posXbomb, posYbomb - i, ItemType.values()[randomItem]));
                }
            }
        }
        for (int i = 0; i <= range; i++) {
            if (posXbomb + i < breakableWalls.length && breakableWalls[posXbomb + i][posYbomb]) {
                breakableWalls[posXbomb + i][posYbomb] = false;
                int randomItem = (int) (Math.random() * 2);
                if (randomItem == 0) {
                    randomItem = (int) (Math.random() * 5);
                    items.add(new InfoItem(posXbomb + i, posYbomb, ItemType.values()[randomItem]));
                }
            }
            if (posXbomb - i > 0 && breakableWalls[posXbomb - i][posYbomb]) {
                breakableWalls[posXbomb - i][posYbomb] = false;
                int randomItem = (int) (Math.random() * 2);
                if (randomItem == 0) {
                    randomItem = (int) (Math.random() * 5);
                    items.add(new InfoItem(posXbomb - i, posYbomb, ItemType.values()[randomItem]));
                }
            }
        }
    }

    /**
     * Méthode permettant d'infliger un dégat à un énnemi
     * Dans le cas d'un BombermanAgent, on enlève une vide avant de vérifier si l'agent est mort
     * On ajoute ensuite éventuellemnt l'Agent à la liste des Agents à tuer
     *
     * @param agent
     * @param bombHit
     */
    private void hit(AbstractAgent agent, boolean bombHit) {
        if (agent.getClass() == BombermanAgent.class) {
            BombermanAgent bombermanAgent = (BombermanAgent) agent;
            if (bombHit) {
                if (!bombermanAgent.isInvincible()) bombermanAgent.removeLife();
            } else bombermanAgent.removeLife();
            if (bombermanAgent.isDead()) agentsToBeRemoved.add(bombermanAgent);
        } else {
            agentsToBeRemoved.add(agent);
        }
    }

    /**
     * Tue les agents de la liste des agents à tuer.
     */
    private void removeDeadAgents() {
        for (AbstractAgent agent : agentsToBeRemoved) {
            agents.remove(agent);
            bombermanGame.getInfoAgents().remove(agent);
            if (agent.getClass() == BombermanAgent.class) players.remove(agent);
            else bombermanGame.getAgentsIa().remove(agent);
        }
    }

}
