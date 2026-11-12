package com.example.gesfootWeb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "stade" ,uniqueConstraints = {@UniqueConstraint(columnNames = "nom")})
public class Stade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, unique = true)
    @NotEmpty(message = "Le nom du stade est obligatoire")
    //@Size(min = 3, max = 50, message = "Le nom doit contenir entre 3 et 50 caractères")
    private String nom;

    @Column(name = "capacite", nullable = false)
    @NotNull(message = "La capacité est obligatoire")
    @Min(value = 1000, message = "La capacité minimale est de 1000 places")
    @Max(value = 100000, message = "La capacité maximale est de 100000 places")
    private Integer capacite;

    @Column(name = "ville", nullable = false)
    @NotEmpty(message = "La ville est obligatoire")
    private String ville;

    @Column(name = "pays", nullable = false)
    @NotEmpty(message = "Le pays est obligatoire")
    private String pays;

    public Stade() {
    }

    public Stade(String nom, Integer capacite, String ville, String pays) {
        this.nom = nom;
        this.capacite = capacite;
        this.ville = ville;
        this.pays = pays;
    }

    // Getters et Setters
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

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
