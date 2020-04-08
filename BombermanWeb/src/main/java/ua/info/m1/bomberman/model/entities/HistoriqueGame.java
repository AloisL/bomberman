package ua.info.m1.bomberman.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class HistoriqueGame {
	
	@Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
    private Long id;
	
	@NotNull
	@Column(name="iduser")
    private Long IdUser;
	
	private boolean victoire;
	
	private String dateGame;

	public Long getIdUser() {
		return IdUser;
	}

	public void setIdUser(Long idUser) {
		IdUser = idUser;
	}

	public boolean getVictoire() {
		return victoire;
	}

	public void setNbvictoire(boolean victoire) {
		this.victoire = victoire;
	}

	public String getDateGame() {
		return dateGame;
	}

	public void setDateGame(String dateGame) {
		this.dateGame = dateGame;
	}
	
	
}
