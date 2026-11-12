/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.gesfootWeb.controller;

import com.example.gesfootWeb.entity.Equipe;
import com.example.gesfootWeb.entity.Joueur;
import com.example.gesfootWeb.repository.EquipeRepository;
import com.example.gesfootWeb.repository.JoueurRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
 public class JoueurController {
     @Autowired
    private JoueurRepository joueurRepository;
     
     @Autowired
private EquipeRepository equipeRepository; // Assurez-vous d'avoir ce repository
     
     
  @GetMapping("/joueur/search")
    public String searchJoueur(@RequestParam(name="search") String search, Model model) {
        // Recherche des joueurs dont le nom ou prénom commence par la chaîne donnée
        List<Joueur> listJoueur = joueurRepository.findByNomOrPrenomStartingWith(search);
        model.addAttribute("listJoueur", listJoueur);
        return "joueur"; // Retourne la page avec la liste des joueurs filtrée
    }    
     
     
 @GetMapping("/joueur")  // c'est la handler method appelée pour cette url (racine de http://localhost:8080)
 public String viewHomePage(Model model)
 {
     List<Joueur> listJoueur = joueurRepository.findAll();

    // Ajouter une vérification pour l'objet 'equipe' de chaque joueur
    for (Joueur joueur : listJoueur) {
        if (joueur.getEquipe() == null) {
            joueur.setEquipe(new Equipe()); // Ou une équipe par défaut si nécessaire
        }
    }
      model.addAttribute("listJoueur", joueurRepository.findAll());
     
 return "joueur"; // retourne le nom de la vue, càd index.html
 }
 @GetMapping("/joueur/new")
    public String showNewJoueurForm(Model model)
    {
        //create model attribute to bind Form data
        Joueur joueur = new Joueur();
        List<Equipe> equipes = equipeRepository.findAll(); // Assurez-vous d'injecter l'EQUIPE repository
    model.addAttribute("equipes", equipes);
        model.addAttribute("joueur", joueur);
        return "new_joueur";
    }
   /* @PostMapping("/joueur/saveNew")
    public String saveNewjoueur(@Valid Joueur joueur, Errors errors)
    {
        if (joueurRepository.existsByNumero(joueur.getNumero())) {
        errors.rejectValue("numero", "numero.existe", "Un joueur avec ce numero existe déjà");
    }
        // Vérification de l'âge du joueur (minimum 16 ans)
        if (!joueur.isAgeValid()) {
            errors.rejectValue("dateNaissance", "dateNaissance.invalide", "Le joueur doit avoir au moins 16 ans.");
        }
        if (errors.hasErrors())
        {         
            return "new_joueur";
        }
        //save joueur to DB
        joueurRepository.save(joueur);
        return "redirect:/joueur";
    }*/
    
    
    @PostMapping("/joueur/saveNew")
public String saveNewjoueur(@Valid Joueur joueur, Errors errors) {
    if (joueurRepository.existsByNumero(joueur.getNumero())) {
        errors.rejectValue("numero", "numero.existe", "Un joueur avec ce numéro existe déjà");
    }

    // Vérification de l'âge du joueur (minimum 16 ans)
    if (joueur.getDateNaissance() == null) {
        errors.rejectValue("dateNaissance", "dateNaissance.null", "La date de naissance ne peut pas être vide.");
    } else if (!joueur.isAgeValid()) {
        errors.rejectValue("dateNaissance", "dateNaissance.invalide", "Le joueur doit avoir au moins 16 ans.");
    }

    if (errors.hasErrors()) {
        return "new_joueur";
    }

    // Sauvegarder le joueur dans la base de données
    joueurRepository.save(joueur);
    return "redirect:/joueur";
}

    
   @GetMapping("/joueur/update")
    public String showUpdateJoueurForm(@RequestParam(name="id") long id, Model model)
    {
        
        Optional<Joueur> optional = joueurRepository.findById(id);
        Joueur joueur= null;
        if (optional.isPresent())
            joueur = optional.get();
        else
            throw new RuntimeException ("Joueur not found for id:: " + id);
        // Ajouter la liste des équipes disponibles au modèle
    List<Equipe> equipes = equipeRepository.findAll(); // Récupère toutes les équipes
    model.addAttribute("equipes", equipes); // Ajoute les équipes au modèle
        
        //set equipe as a model attribute to pre-populate the form
        model.addAttribute("joueur", joueur);
        return "update_joueur";
    }
   
    
    @PostMapping("/joueur/saveUpdate")
    public String saveUpdateJoueur(@Valid Joueur joueur, Errors errors)
    {
        
        if (errors.hasErrors())
        {
            return "update_joueur";
        }
        //save department to DB
        joueurRepository.save(joueur);
        return "redirect:/joueur"; 
    }
    
   /* @PostMapping("/joueur/saveUpdate")
public String saveUpdateJoueur(@Valid Joueur joueur, Errors errors) {
    // Vérification si un joueur avec le même numéro existe déjà, mais en excluant l'ID actuel
    Optional<Joueur> existingJoueur = joueurRepository.findByNumeroAndIdNot(joueur.getNumero(), joueur.getId());
    
    if (existingJoueur.isPresent()) {
        errors.rejectValue("numero", "numero.existe", "Un joueur avec ce numéro existe déjà.");
    }

    // Vérification de l'âge du joueur (minimum 16 ans)
    if (joueur.getDateNaissance() == null) {
        errors.rejectValue("dateNaissance", "dateNaissance.null", "La date de naissance ne peut pas être vide.");
    } else if (!joueur.isAgeValid()) {
        errors.rejectValue("dateNaissance", "dateNaissance.invalide", "Le joueur doit avoir au moins 16 ans.");
    }

    // Si des erreurs existent, retourner le formulaire de mise à jour
    if (errors.hasErrors()) {
        return "update_joueur";
    }

    // Sauvegarder les modifications du joueur dans la base de données
    joueurRepository.save(joueur);
    return "redirect:/joueur";
}*/

    
    @GetMapping("/joueur/delete")
    public String deleteJoueur(@RequestParam(name="id") long id, Model model)
    {
        Optional<Joueur> optional = joueurRepository.findById(id);
        Joueur joueur = null;
        if (optional.isPresent())
        {
            joueur = optional.get();
            joueurRepository.deleteById(id);
        }
        else
            throw new RuntimeException ("Joueur not found for id: " + id);

        return "redirect:/joueur";
    }
 }
