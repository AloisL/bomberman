package engine.subsystems;

import common.enums.ItemType;
import common.infotypes.InfoItem;
import engine.BombermanGame;
import engine.agents.AbstractAgent;
import engine.agents.BombermanAgent;

import java.util.ArrayList;

public class ItemSystem extends AbstractSystem {

    public ItemSystem(BombermanGame bombermanGame) {
        super(bombermanGame);
    }

    @Override
    public void run() {
        ArrayList<InfoItem> itemsToBeRemoved = new ArrayList<>();

        for (InfoItem item : items) {
            int posX = item.getX();
            int posY = item.getY();
            for (AbstractAgent agent : players) {
                BombermanAgent bombermanAgent = (BombermanAgent) agent;
                bombermanAgent.reduceInvinsibleTurn();
                bombermanAgent.reduceSickTurn();
                if (agent.getX() == posX && agent.getY() == posY) applyItem(bombermanAgent, item, itemsToBeRemoved);
            }
        }

        for (InfoItem item : itemsToBeRemoved) items.remove(item);
    }

    private void applyItem(BombermanAgent bombermanAgent, InfoItem item, ArrayList<InfoItem> itemsToBeRemoved) {
        ItemType itemType = item.getType();

        switch (itemType) {
            case FIRE_UP:
                bombermanAgent.addBombRange();
                itemsToBeRemoved.add(item);
                break;
            case FIRE_DOWN:
                bombermanAgent.reduceBombRange();
                itemsToBeRemoved.add(item);
                break;
            case BOMB_UP:
                bombermanAgent.addMaxBomb();
                itemsToBeRemoved.add(item);
                break;
            case BOMB_DOWN:
                bombermanAgent.reduceMaxBomb();
                itemsToBeRemoved.add(item);
                break;
            case FIRE_SUIT:
                bombermanAgent.makeInvinsible();
                itemsToBeRemoved.add(item);
                break;
            case SKULL:
                bombermanAgent.makeSick();
                break;
            default:
                log.error("Item inconnu : " + item);
                break;
        }
    }
}
