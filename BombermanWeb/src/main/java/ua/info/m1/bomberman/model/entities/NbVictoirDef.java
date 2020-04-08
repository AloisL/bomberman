package ua.info.m1.bomberman.model.entities;

import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class NbVictoirDef {
	private int victoire;
	private int defaite;
    private String username;
    
    public NbVictoirDef() {
    	victoire =100;
    	defaite=0;
    	username="dieux";
    }
    
    public int getVictoire() {
		return victoire;
	}

	public void setVictoire(int victoire) {
		this.victoire = victoire;
	}

	public int getDefaite() {
		return defaite;
	}

	public void setDefaite(int defaite) {
		this.defaite = defaite;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public NbVictoirDef(List<HistoriqueGame> historiques,String username,Long id ) {
		this.username=username;
		victoire=0;
		defaite=0;
		if(historiques.size()!=0) {
			for (HistoriqueGame historiqueGame : historiques) {
				if(id==historiqueGame.getIdUser()) {
					if(historiqueGame.getVictoire()=="victoire") victoire+=1;
					else {
						defaite+=1;
					}
					break;
				}
			}
		}
	}
}
