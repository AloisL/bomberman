package client;

import common.BombermanDTO;
import common.enums.AgentAction;
import common.enums.ItemType;
import common.enums.StateBomb;
import common.infotypes.InfoAgent;
import common.infotypes.InfoBomb;
import common.infotypes.InfoItem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe qui permet de charger d'afficher le panneau du jeu à partir d'une carte et de listes d'agents avec leurs
 * positions.
 * Inspiré du code de Kevin Balavoine et Victor Lelu--Ribaimont.
 */
public class PanelBomberman extends JPanel {

    private static final long serialVersionUID = 1L;
    protected Color wallColor = Color.GRAY;
    protected Color brokable_walls_Color = Color.lightGray;
    protected Color ground_Color = new Color(50, 175, 50);
    protected ArrayList<InfoAgent> listInfoAgents;
    protected ArrayList<InfoItem> listInfoItems;
    protected ArrayList<InfoBomb> listInfoBombs;
    float[] contraste = {0, 0, 0, 1.0f};
    float[] invincible = {200, 200, 200, 1.0f};
    float[] skull = {0.5f, 0.5f, 0.5f, 0.75f};
    int cpt;
    private int taille_x;
    private int taille_y;
    private BombermanDTO bombermanDTO;
    private boolean breakable_walls[][];

    public PanelBomberman(BombermanDTO bombermanDTO) {
        this.bombermanDTO = bombermanDTO;
        breakable_walls = bombermanDTO.getBreakableWalls();
        listInfoAgents = bombermanDTO.getInfoAgents();
        listInfoItems = new ArrayList<InfoItem>();
        listInfoBombs = new ArrayList<InfoBomb>();
    }

    @Override
    public void paint(Graphics g) {
        int fen_x = getSize().width;
        int fen_y = getSize().height;

        g.setColor(ground_Color);
        g.fillRect(0, 0, fen_x, fen_y);

        double stepx = fen_x / (double) taille_x;
        double stepy = fen_y / (double) taille_y;
        double position_x = 0;

        taille_x = bombermanDTO.getSizeX();
        taille_y = bombermanDTO.getSizeY();

        boolean[][] walls = bombermanDTO.get_walls();

        for (int x = 0; x < taille_x; x++) {
            double position_y = 0;


            for (int y = 0; y < taille_y; y++) {
                if (walls[x][y]) {
                    try {
                        Image img = ImageIO.read(getClass().getResourceAsStream("/image/wall.png"));
                        g.drawImage(img, (int) position_x, (int) position_y, (int) stepx, (int) stepy, this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (breakable_walls[x][y]) {

                    try {
                        Image img = ImageIO.read(getClass().getResource("/image/brique_2.png"));
                        g.drawImage(img, (int) position_x, (int) position_y, (int) stepx, (int) stepy, this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        Image img = ImageIO.read(getClass().getResource("/image/grass.png"));
                        g.drawImage(img, (int) position_x, (int) position_y, (int) stepx, (int) stepy, this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                position_y += stepy;
            }
            position_x += stepx;
        }


        for (int i = 0; i < listInfoItems.size(); i++) {
            dessine_Items(g, listInfoItems.get(i));
        }

        for (int j = 0; j < listInfoBombs.size(); j++) {
            dessine_Bomb(g, listInfoBombs.get(j));
        }

        for (int i = 0; i < listInfoAgents.size(); i++) {
            dessine_Agent(g, listInfoAgents.get(i));
        }

        cpt++;
    }

    public void dessine_Agent(Graphics g, InfoAgent infoAgent) {
        int fen_x = getSize().width;
        int fen_y = getSize().height;

        double stepx = fen_x / (double) taille_x;
        double stepy = fen_y / (double) taille_y;

        int px = infoAgent.getX();
        int py = infoAgent.getY();

        double pos_x = px * stepx;
        double pos_y = py * stepy;

        AgentAction agentAction = infoAgent.getAgentAction();

        int direction = 0;

        if (agentAction == AgentAction.MOVE_UP) {
            direction = 0;
        } else if (agentAction == AgentAction.MOVE_DOWN) {
            direction = 1;
        } else if (agentAction == AgentAction.MOVE_RIGHT) {
            direction = 2;
        } else if (agentAction == AgentAction.MOVE_LEFT) {
            direction = 3;
        } else {
            direction = 4;
        }

        BufferedImage img = null;

        try {
            if (infoAgent.getType() == 'R') {
                img = ImageIO.read(getClass().getResource("/image/" + infoAgent.getType() + direction + cpt % 2 + ".png"));
            } else {
                img = ImageIO.read(getClass().getResource("/image/" + infoAgent.getType() + direction + cpt % 3 + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        float[] scales = new float[]{1, 1, 1, 1.0f};

        if (infoAgent.getColor() != null) {
            switch (infoAgent.getColor()) {
                case ROUGE:
                    scales = new float[]{3, 0.75f, 0.75f, 1.0f};
                    break;
                case VERT:
                    scales = new float[]{0.75f, 3, 0.75f, 1.0f};
                    break;
                case BLEU:
                    scales = new float[]{0.75f, 0.75f, 3, 1.0f};
                    break;
                case JAUNE:
                    scales = new float[]{3, 3, 0.75f, 1.0f};
                    break;
                case BLANC:
                    scales = new float[]{2, 2, 2, 1.0f};
                    break;
                case DEFAULT:
                    scales = new float[]{1, 1, 1, 1.0f};
                    break;
            }
        }

        if (infoAgent.isInvincible() & cpt % 2 == 0)
            contraste = invincible;
        else contraste = new float[]{0, 0, 0, 1.0f};

        if (infoAgent.isSick() & cpt % 2 == 0)
            scales = skull;


        RescaleOp op = new RescaleOp(scales, contraste, null);
        img = op.filter(img, null);


        if (img != null) {
            g.drawImage(img, (int) pos_x, (int) pos_y, (int) stepx, (int) stepy, this);
        }

    }

    void dessine_Items(Graphics g, InfoItem item) {

        int fen_x = getSize().width;
        int fen_y = getSize().height;

        double stepx = fen_x / (double) taille_x;
        double stepy = fen_y / (double) taille_y;

        int px = item.getX();
        int py = item.getY();

        double pos_x = px * stepx;
        double pos_y = py * stepy;

        if (item.getType() == ItemType.FIRE_UP) {
            try {
                Image img = ImageIO.read(getClass().getResource("/image/Item_FireUp.png"));
                g.drawImage(img, (int) pos_x, (int) pos_y, (int) stepx, (int) stepy, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (item.getType() == ItemType.FIRE_DOWN) {
            try {
                Image img = ImageIO.read(getClass().getResource("/image/Item_FireDown.png"));
                g.drawImage(img, (int) pos_x, (int) pos_y, (int) stepx, (int) stepy, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (item.getType() == ItemType.BOMB_UP) {
            try {
                Image img = ImageIO.read(getClass().getResource("/image/Item_BombUp.png"));
                g.drawImage(img, (int) pos_x, (int) pos_y, (int) stepx, (int) stepy, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (item.getType() == ItemType.BOMB_DOWN) {
            try {
                Image img = ImageIO.read(getClass().getResource("/image/Item_BombDown.png"));
                g.drawImage(img, (int) pos_x, (int) pos_y, (int) stepx, (int) stepy, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (item.getType() == ItemType.FIRE_SUIT) {
            try {
                Image img = ImageIO.read(getClass().getResource("/image/Item_FireSuit.png"));
                g.drawImage(img, (int) pos_x, (int) pos_y, (int) stepx, (int) stepy, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (item.getType() == ItemType.SKULL) {
            try {
                Image img = ImageIO.read(getClass().getResource("/image/Item_Skull.png"));
                g.drawImage(img, (int) pos_x, (int) pos_y, (int) stepx, (int) stepy, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void dessine_Bomb(Graphics g, InfoBomb bomb) {
        int fen_x = getSize().width;
        int fen_y = getSize().height;

        double stepx = fen_x / (double) taille_x;
        double stepy = fen_y / (double) taille_y;

        int px = bomb.getX();
        int py = bomb.getY();

        double pos_x = px * stepx;
        double pos_y = py * stepy;

        if (bomb.getStateBomb() == StateBomb.Step1) {

            try {
                Image img = ImageIO.read(getClass().getResource("/image/Bomb_0.png"));
                g.drawImage(img, (int) pos_x, (int) pos_y, (int) stepx, (int) stepy, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (bomb.getStateBomb() == StateBomb.Step2) {

            try {
                Image img = ImageIO.read(getClass().getResource("/image/Bomb_1_jaune.png"));
                g.drawImage(img, (int) pos_x, (int) pos_y, (int) stepx, (int) stepy, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (bomb.getStateBomb() == StateBomb.Step3) {

            try {
                Image img = ImageIO.read(getClass().getResource("/image/Bomb_2_rouge.png"));
                g.drawImage(img, (int) pos_x, (int) pos_y, (int) stepx, (int) stepy, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (bomb.getStateBomb() == StateBomb.Boom) {

            try {
                Image img = ImageIO.read(getClass().getResource("/image/Range_CENTRE.png"));
                g.drawImage(img, (int) pos_x, (int) pos_y, (int) stepx, (int) stepy, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int range = bomb.getRange();

            for (int i = 1; i <= range; i++) {

                if (py + i < bombermanDTO.getSizeY()) {
                    if (i == range) {
                        try {
                            Image img = ImageIO.read(getClass().getResource("/image/Range_SOUTH_Fin.png"));
                            g.drawImage(img, (int) pos_x, (int) (pos_y + (stepy * i)), (int) stepx, (int) stepy, this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Image img = ImageIO.read(getClass().getResource("/image/Range_SOUTH.png"));
                            g.drawImage(img, (int) pos_x, (int) (pos_y + (stepy * i)), (int) stepx, (int) stepy, this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (py - i >= 0) {
                    if (i == range) {
                        try {
                            Image img = ImageIO.read(getClass().getResource("/image/Range_NORTH_Fin.png"));
                            g.drawImage(img, (int) pos_x, (int) (pos_y - (stepy * i)), (int) stepx, (int) stepy, this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Image img = ImageIO.read(getClass().getResource("/image/Range_NORTH.png"));
                            g.drawImage(img, (int) pos_x, (int) (pos_y - (stepy * i)), (int) stepx, (int) stepy, this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (px + i < bombermanDTO.getSizeX()) {
                    if (i == range) {
                        try {
                            Image img = ImageIO.read(getClass().getResource("/image/Range_EAST_Fin.png"));
                            g.drawImage(img, (int) (pos_x + (stepy * i)), (int) (pos_y), (int) stepx, (int) stepy,
                                    this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Image img = ImageIO.read(getClass().getResource("/image/Range_EAST.png"));
                            g.drawImage(img, (int) (pos_x + (stepy * i)), (int) (pos_y), (int) stepx, (int) stepy,
                                    this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (px - i >= 0) {
                    if (i == range) {
                        try {
                            Image img = ImageIO.read(getClass().getResource("/image/Range_WEST_Fin.png"));
                            g.drawImage(img, (int) (pos_x - (stepy * i)), (int) (pos_y), (int) stepx, (int) stepy,
                                    this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Image img = ImageIO.read(getClass().getResource("/image/Range_WEST.png"));
                            g.drawImage(img, (int) (pos_x - (stepy * i)), (int) (pos_y), (int) stepx, (int) stepy,
                                    this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }

        }

    }

    public void setInfoGame(boolean[][] breakable_walls, ArrayList<InfoAgent> listInfoAgents,
                            ArrayList<InfoItem> listInfoItems, ArrayList<InfoBomb> listInfoBombs) {
        this.listInfoAgents = listInfoAgents;
        this.listInfoItems = listInfoItems;
        this.listInfoBombs = listInfoBombs;
        this.breakable_walls = breakable_walls;
    }


}
