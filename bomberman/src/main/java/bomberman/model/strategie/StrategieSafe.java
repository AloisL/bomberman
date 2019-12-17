package bomberman.model.strategie;

import bomberman.model.engine.InfoBomb;
import bomberman.model.repo.AgentAction;

public class StrategieSafe extends StrategieAgents {

    @Override
    public AgentAction doStrategie() {
        Coordonne c=checkSiBesoinSafe();
        if(c.x!=0){
           return doMouvement(c);
        }
        return AgentAction.STOP;
    }

    //verifie si besoin de se mettre en zone de securité sinon renvoie (0,0)
    public Coordonne checkSiBesoinSafe(){
        for (InfoBomb b: bombermanGame.getBombs()) {
            if (isInRange(b)){
                return zoneSafe(b);
            }
        }
        Coordonne c=new Coordonne(0,0);
        return c;
    }

    public Coordonne zoneSafe(InfoBomb b){
        int diffX=agentCalling.getX() - b.getX();
        int diffY=agentCalling.getY() - b.getY();
        if(diffX!=0) diffX=Math.abs(diffX)/diffX;
        if(diffY!=0) diffY=Math.abs(diffY)/diffY;

        for (int i=1; i<=b.getRange() ; i++ ){
            //permet de virifier chaque case adjacente au rayon de la bombe pour trouvée une case "safe"
            Coordonne c1=new Coordonne(b.getX()+(i*diffX)+(diffY),b.getY()+(i*diffY)+diffX);
            Coordonne c2=new Coordonne(b.getX()+(i*diffX)-diffY,b.getY()+(i*diffY)-diffX);
            if(bombermanGame.isFree(c1)) return c1;
            if(bombermanGame.isFree(c2)) return c2;
        }
        //la coordonnée de l'extrimité de la range
        Coordonne c=new Coordonne(b.getX()+(b.getRange()*diffX)+(diffY),b.getY()+(b.getRange()*diffY)+diffX);
        if (bombermanGame.isFree(c)) {
            return c;
        }
        c.x=0;
        c.y=0;
        return c;
    }
//int x = 10, y = 20;
//int max = (x < y) ? y : x ; //Maintenant, max vaut 20
//$number, ': ', $number ? abs($number) / $number : 0

    public boolean isInRange(InfoBomb b){
        if ( ( ( agentCalling.getX()<b.getX()+b.getRange() ) && ( agentCalling.getX()>b.getX()-b.getRange() ) ) && (agentCalling.getY()==b.getY()) ){
            return true;
        }
        if ( ( ( agentCalling.getY()<b.getY()+b.getRange() ) && ( agentCalling.getX()>b.getY()-b.getRange() ) ) && (agentCalling.getX()==b.getX()) ){
            return true;
        }
        return false;
    }


}
