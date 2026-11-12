package com.example.gesfootWeb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "joueur")
public class Joueur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom")
     @NotEmpty (message = "Le nom ne peut pas être vide")
    private String nom;
    @Column(name = "prenom")
     @NotEmpty (message = "Le prenom ne peut pas être vide")
    private String prenom;
    @Column(name = "date_naissance")

      @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;
    @Enumerated(EnumType.STRING)
    @Column(name = "poste")
     
    
    private Poste poste;
    @Column(name = "numero")
    @NotNull(message = "Le  ne peut pas être nul")
    @Min(value = 1, message = "Le numero doit être supérieur ou égal à 1")
    private int numero;

    public enum Poste {
        GARDIEN,
        DEFENSEUR,
        MILIEUX,
        ATTAQUANT
    }
    @ManyToOne(fetch = FetchType.LAZY)
    private Equipe equipe;

    public Joueur() {
    }

    public Joueur(String nom, String prenom, LocalDate dateNaissance, Poste poste, int numero, Equipe equipe) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.poste = poste;
        this.numero = numero;
        this.equipe = equipe;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
     // Méthode de validation de l'âge minimum
    //public boolean isAgeValid() {
      //  return Period.between(this.dateNaissance, LocalDate.now()).getYears() >= 16;
   // }
    public boolean isAgeValid() {
    if (this.dateNaissance == null) {
        return false; // Ou gérer autrement si la date de naissance est null
    }
    
    LocalDate today = LocalDate.now();
    int age = Period.between(this.dateNaissance, today).getYears();
    return age >= 16; // Assurez-vous que le joueur a au moins 16 ans
}


    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

}
