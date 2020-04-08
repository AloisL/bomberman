package ua.info.m1.bomberman.model.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private String dateDebut;

    @Column(name = "datefin")
    private String dateFin;

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

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateGame) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateGame) {
        this.dateFin = dateFin;
    }

}
