package com.example.gesfootWeb.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipe")
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom")
    @NotEmpty (message = "Le nom ne peut pas être vide")
    private String nom;
    @Column(name = "pays")
    @NotEmpty (message = "Le nom du pays ne peut pas être vide")
    private String pays;
    @Column(name = "groupe")
    @NotEmpty (message = "Le groupe ne peut pas être vide")
    private String groupe;
    @Column(name = "classement")
    @NotNull(message = "Le classement ne peut pas être nul")
    @Min(value = 1, message = "Le classement doit être supérieur ou égal à 1")
    private int classement;
    @Column(name = "entraineur")
    @NotEmpty
    private String entraineur;
    @OneToMany(cascade = CascadeType.ALL, fetch
            = FetchType.LAZY, mappedBy = "equipe")
    private List<Joueur> joueur = new ArrayList<>();

    public Equipe() {
    }

    public Equipe(String nom, String pays, String groupe, int classement, String entraineur) {
        this.nom = nom;
        this.pays = pays;
        this.groupe = groupe;
        this.classement = classement;
        this.entraineur = entraineur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public int getClassement() {
        return classement;
    }

    public void setClassement(int classement) {
        this.classement = classement;
    }

    public String getEntraineur() {
        return entraineur;
    }

    public void setEntraineur(String entraineur) {
        this.entraineur = entraineur;
    }

    public List<Joueur> getJoueur() {
        return joueur;
    }

    public void setJoueur(List<Joueur> joueur) {
        this.joueur = joueur;
    }

}
