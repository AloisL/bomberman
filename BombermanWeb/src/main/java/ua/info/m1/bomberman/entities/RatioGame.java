package ua.info.m1.bomberman.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class RatioGame  {

	@Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@NotNull
    @Column(unique = true)
    private Long IdUser;
	
	
    private Long nbvictoire;
	
	
    private Long nbdefaite;

	public Long getIdUser() {
		return IdUser;
	}

	public Long getNbvictoire() {
		return nbvictoire;
	}

	public void setNbvictoire(Long nbvictoire) {
		this.nbvictoire = nbvictoire;
	}

	public Long getNbdefaite() {
		return nbdefaite;
	}

	public void setNbdefaite(Long nbdefaite) {
		this.nbdefaite = nbdefaite;
	}


	
	
	
}
