package com.example.gesfootWeb.entity;

//import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "billet")
public class Billet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "prix")
   @NotNull(message = "Le  ne peut pas être nul")
   @Min(value = 40, message = "Le prix doit être supérieur ou égal à 40 euro")
    private float prix;
    @Column(name = "vendu")
    private boolean vendu;
    @Column(name = "date_achat")
@DateTimeFormat(pattern = "yyyy-MM-dd")
private LocalDate dateAchat;


     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spectateur_id", nullable = false)
    private Spectateur spectateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rencontre_id", nullable = false)
    private Rencontre rencontre;

    public Billet() {
        // La date d'achat est automatiquement la date actuelle à la création
        this.dateAchat = LocalDate.now(); // Définit la date à la date du jour
    }

    public Billet(float prix, boolean vendu, LocalDate dateAchat, Spectateur spectateur, Rencontre rencontre) {
        this.prix = prix;
        this.vendu = vendu;
       // this.dateAchat = dateAchat;
        this.dateAchat = LocalDate.now(); 
        this.spectateur = spectateur;
        this.rencontre = rencontre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public boolean isVendu() {
        return vendu;
    }

    public void setVendu(boolean vendu) {
        this.vendu = vendu;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    public Spectateur getSpectateur() {
        return spectateur;
    }

    public void setSpectateur(Spectateur spectateur) {
        this.spectateur = spectateur;
    }

    public Rencontre getRencontre() {
        return rencontre;
    }

    public void setRencontre(Rencontre rencontre) {
        this.rencontre = rencontre;
    }

 
}

