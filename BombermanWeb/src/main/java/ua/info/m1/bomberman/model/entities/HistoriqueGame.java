package ua.info.m1.bomberman.model.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class HistoriqueGame {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "iduser")
    private Long IdUser;

    @Column(name = "victoire")
    private String victoire;

    @Column(name = "datedebut")
    private Date dateDebut;

    @Column(name = "datedebut")
    private Date dateFin;

    public Long getIdUser() {
        return IdUser;
    }

    public void setIdUser(Long idUser) {
        IdUser = idUser;
    }

    public String getVictoire() {
        return victoire;
    }

    public void setVictoire(String victoire) {
        this.victoire = victoire;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateGame(String dateGame) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateGame) {
        this.dateFin = dateFin;
    }

}
