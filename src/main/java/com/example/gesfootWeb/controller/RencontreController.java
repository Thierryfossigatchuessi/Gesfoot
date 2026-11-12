/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.gesfootWeb.controller;

import com.example.gesfootWeb.entity.Equipe;
import com.example.gesfootWeb.entity.Rencontre;
import com.example.gesfootWeb.entity.Stade;
import com.example.gesfootWeb.repository.EquipeRepository;
import com.example.gesfootWeb.repository.RencontreRepository;
import com.example.gesfootWeb.repository.StadeRepository;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RencontreController {

    @Autowired
    private RencontreRepository rencontreRepository;

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private StadeRepository stadeRepository;

    @GetMapping("/rencontre")  // c'est la handler method appelée pour cette url (racine de http://localhost:8080)
    public String viewHomePage(Model model, @RequestParam(name = "motCle", defaultValue = "") String equipe) {
        model.addAttribute("listRencontre", rencontreRepository.findByEquipe1_NomContaining(equipe));
        return "rencontre"; // retourne le nom de la vue, càd index.html

    }

    @GetMapping("/fillter")
    public String fillterParDate(Model model, @RequestParam(name = "motCle", required = false) String motCle) {
        // Si aucun mot-clé n'est fourni, afficher toutes les rencontres
        if (motCle == null || motCle.isEmpty()) {
            model.addAttribute("listRencontre", rencontreRepository.findAll());
        } else {
            try {
                // Convertir le mot-clé (String) en LocalDate
                LocalDate date = LocalDate.parse(motCle);
                model.addAttribute("listRencontre", rencontreRepository.findByDate(date));
            } catch (Exception e) {
                // Si la conversion échoue, afficher un message d'erreur ou les données par défaut
                model.addAttribute("listRencontre", rencontreRepository.findAll());
                model.addAttribute("error", "Format de date invalide. Veuillez entrer une date valide (yyyy-MM-dd).");
            }
        }
        return "rencontre"; // Retourne la vue "rencontre.html"
    }

    @GetMapping("/rencontre/new")
    public String showNewRencontreForm(Model model) {

        Rencontre rencontre = new Rencontre();
        model.addAttribute("rencontre", rencontre);
        model.addAttribute("listEquipes", equipeRepository.findAll());
        model.addAttribute("listStades", stadeRepository.findAll());
        return "new_rencontre";
    }

    @PostMapping("/rencontre/saveNew")
    public String saveNewRencontre(@Valid @ModelAttribute("rencontre") Rencontre rencontre, Errors errors, Model model) {
        if (errors.hasErrors()) {
            // Charger les listes nécessaires pour afficher le formulaire
            model.addAttribute("listStades", stadeRepository.findAll());
            model.addAttribute("listEquipes", equipeRepository.findAll());
            return "new_rencontre";
        }

        // Initialiser le score à "0-0"
        rencontre.setScore("0-0");

        // Vérifier que les équipes sont différentes
        if (rencontre.getEquipe1().equals(rencontre.getEquipe2())|| memeDate(rencontre)) {
            if (rencontre.getEquipe1().equals(rencontre.getEquipe2())) {
               model.addAttribute("errorMemeEquipe", "Les équipes doivent être différentes");
            }else if (memeDate(rencontre)) {
                model.addAttribute("errorMemeEquipe", "une rencontre existe dans ce date");
            }
            model.addAttribute("listStades", stadeRepository.findAll());
            model.addAttribute("listEquipes", equipeRepository.findAll());
            return "new_rencontre";

        }
        try {
            // Tenter de sauvegarder la rencontre
            rencontreRepository.save(rencontre);
        } catch (Exception e) {
            // Gestion d'une violation de la contrainte d'unicité
            model.addAttribute("listStades", stadeRepository.findAll());
            model.addAttribute("listEquipes", equipeRepository.findAll());
            model.addAttribute("errorMemeRencontre", "Une rencontre avec les mêmes détails existe déjà !");
            return "new_rencontre";
        }

        // Redirection en cas de succès
        return "redirect:/rencontre";
    }

    @GetMapping("/rencontre/update")
    public String showUpdateRencontreForm(@RequestParam("id") Long id, Model model) {
        Optional<Rencontre> optionalRencontre = rencontreRepository.findById(id);

        // Vérification si la rencontre existe dans la base de données
        if (optionalRencontre.isPresent()) {
            // Si la rencontre existe, on la récupère
            Rencontre rencontre = optionalRencontre.get();
            // Ajout des attributs au modèle
            model.addAttribute("rencontre", rencontre);
            model.addAttribute("listEquipes", equipeRepository.findAll());
            model.addAttribute("listStades", stadeRepository.findAll());

            // Retourner la vue pour mettre à jour la rencontre
            return "update_rencontre";
        } else {
            // Si la rencontre n'existe pas, on lance une exception
            throw new RuntimeException("Rencontre non trouvée pour l'ID : " + id);
        }
    }

    @PostMapping("/rencontre/saveUpdate")
    public String saveUpdateRencontre(@Valid @ModelAttribute("rencontre") Rencontre rencontre,
            @RequestParam("equipe1") Long equipe1Id,
            @RequestParam("equipe2") Long equipe2Id,
            @RequestParam("stade") Long stadeId,
            Errors errors,
            Model model) {
        // Vérifier les erreurs de validation sur l'objet rencontre
        if (errors.hasErrors()) {
            return "update_rencontre";  // Si des erreurs sont présentes, retourner au formulaire de mise à jour
        }

        // Récupérer les entités liées en utilisant une logique conditionnelle if-else
        Equipe equipe1 = null;
        Equipe equipe2 = null;
        Stade stade = null;

        // Vérifier si l'équipe 1 existe
        Optional<Equipe> optionalEquipe1 = equipeRepository.findById(equipe1Id);
        if (optionalEquipe1.isPresent()) {
            equipe1 = optionalEquipe1.get();
        } else {
            throw new RuntimeException("Equipe 1 introuvable");
        }

        // Vérifier si l'équipe 2 existe
        Optional<Equipe> optionalEquipe2 = equipeRepository.findById(equipe2Id);
        if (optionalEquipe2.isPresent()) {
            equipe2 = optionalEquipe2.get();
        } else {
            throw new RuntimeException("Equipe 2 introuvable");
        }

        // Vérifier si le stade existe
        Optional<Stade> optionalStade = stadeRepository.findById(stadeId);
        if (optionalStade.isPresent()) {
            stade = optionalStade.get();
        } else {
            throw new RuntimeException("Stade introuvable");
        }

        // Associer les nouvelles valeurs de l'entité rencontre
        if (!(memeEquipe(rencontre.getEquipe1(), rencontre.getEquipe2()))) {
            rencontre.setEquipe1(equipe1);
            rencontre.setEquipe2(equipe2);
            rencontre.setStade(stade);
            rencontre.setScore(rencontre.getScore());
            // Sauvegarder les modifications de la rencontre
            rencontreRepository.save(rencontre);
            // Rediriger vers la page des rencontres après la sauvegarde
            return "redirect:/rencontre";
        } else {
            model.addAttribute("errorMemeEquipe", "les équipes doivent être différentes");
            model.addAttribute("rencontre", rencontre);
            model.addAttribute("listEquipes", equipeRepository.findAll());
            model.addAttribute("listStades", stadeRepository.findAll());
            return "update_rencontre";
        }

    }

    @GetMapping("/rencontre/delete")
    public String deleteRencontre(@RequestParam(name = "id") long id, Model model) {
        Optional<Rencontre> optional = rencontreRepository.findById(id);
        Rencontre rencontre = null;
        if (optional.isPresent()) {
            rencontre = optional.get();
            rencontreRepository.deleteById(id);
        } else {
            throw new RuntimeException("Rencontre not found for id: " + id);
        }

        return "redirect:/rencontre";
    }

    private boolean memeEquipe(Equipe equipe1, Equipe equipe2) {
        return equipe1.getId().equals(equipe2.getId()) && (equipe1.getNom().equals(equipe2.getNom()));
    }

    private boolean memeDate(Rencontre rencontreAjoute) {
        int monthAjoute = rencontreAjoute.getDateEtHeure().getMonthValue();
        int dayAjoute = rencontreAjoute.getDateEtHeure().getDayOfMonth();

        for (Rencontre rencontre : rencontreRepository.findAll()) {
            int day = rencontre.getDateEtHeure().getDayOfMonth();
            int month = rencontre.getDateEtHeure().getMonthValue();
            if (month == monthAjoute && day == dayAjoute) {
                return true; // Si une correspondance est trouvée, on retourne immédiatement true
            }
        }
        return false; // Si aucune correspondance n'est trouvée, on retourne false
    }

}
