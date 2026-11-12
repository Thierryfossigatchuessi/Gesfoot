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
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "spectateur")
public class Spectateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom")
    @NotEmpty (message = "Le nom ne peut pas être vide")
    private String nom;
    @Column(name = "prenom")
    @NotEmpty (message = "Le penom ne peut pas être vide")
    private String prenom;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @OneToMany(mappedBy = "spectateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Billet> billets;

    public Spectateur() {
    }

    public Spectateur(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Billet> getBillets() {
        return billets;
    }

    public void setBillets(List<Billet> billets) {
        this.billets = billets;
    }

    
}
