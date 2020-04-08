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
    private Long id;
	
	@NotNull
    @Column(unique = true)
    private Long IdUser;
	
	private Long nbvictoire;
	
	private String dateGame;

	public Long getIdUser() {
		return IdUser;
	}

	public void setIdUser(Long idUser) {
		IdUser = idUser;
	}

	public Long getNbvictoire() {
		return nbvictoire;
	}

	public void setNbvictoire(Long nbvictoire) {
		this.nbvictoire = nbvictoire;
	}

	public String getDateGame() {
		return dateGame;
	}

	public void setDateGame(String dateGame) {
		this.dateGame = dateGame;
	}
	
	
}
