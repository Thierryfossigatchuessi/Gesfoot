package com.example.gesfootWeb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "rencontre", uniqueConstraints = @UniqueConstraint(
        columnNames = {"equipe1_id", "equipe2_id", "stade_id", "date_et_heure", "arbitre"}))

public class Rencontre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FutureOrPresent(message = "La date doit être future ou actuelle")
    @Column(name = "date_et_heure", nullable = false)
    @NotNull(message = "La date et l'heure de la rencontre sont obligatoires")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateEtHeure;

    @Column(name = "arbitre", nullable = false)
    @NotEmpty(message = "Le nom de l'arbitre ne peut pas être vide")
    // @Size(min = 3, max = 50, message = "Le nom de l'arbitre doit contenir entre 3 et 50 caractères")
    private String arbitre;

    @Column(name = "score")
    @Pattern(regexp = "\\d+-\\d+", message = "Le score doit être au format 'X-Y' (par exemple, 2-1)")
    private String score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stade_id", nullable = false)
    @NotNull(message = "Le stade est obligatoire")
    private Stade stade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe1_id", nullable = false)
    @NotNull(message = "L'équipe 1 est obligatoire")
    private Equipe equipe1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe2_id", nullable = false)
    @NotNull(message = "L'équipe 2 est obligatoire")
    private Equipe equipe2;

    // Constructeurs, Getters et Setters
    public Rencontre() {
    }

    public Rencontre(LocalDateTime dateEtHeure, String arbitre, String score, Stade stade, Equipe equipe1, Equipe equipe2) {
        this.dateEtHeure = dateEtHeure;
        this.arbitre = arbitre;
        this.score = score;
        this.stade = stade;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateEtHeure() {
        return dateEtHeure;
    }

    public void setDateEtHeure(LocalDateTime dateEtHeure) {
        this.dateEtHeure = dateEtHeure;
    }

    public String getArbitre() {
        return arbitre;
    }

    public void setArbitre(String arbitre) {
        this.arbitre = arbitre;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Stade getStade() {
        return stade;
    }

    public void setStade(Stade stade) {
        this.stade = stade;
    }

    public Equipe getEquipe1() {
        return equipe1;
    }

    public void setEquipe1(Equipe equipe1) {
        this.equipe1 = equipe1;
    }

    public Equipe getEquipe2() {
        return equipe2;
    }

    public void setEquipe2(Equipe equipe2) {
        this.equipe2 = equipe2;
    }
}
