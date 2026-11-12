/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.gesfootWeb.controller;

import com.example.gesfootWeb.entity.Equipe;
import com.example.gesfootWeb.entity.Joueur;
import com.example.gesfootWeb.entity.Rencontre;
//import com.example.gesfootWeb.entity.Rencontre;
import com.example.gesfootWeb.repository.EquipeRepository;
import com.example.gesfootWeb.repository.JoueurRepository;
import com.example.gesfootWeb.repository.RencontreRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
 public class EquipeController {
     @Autowired
    private EquipeRepository equipeRepository;
     
     
     @Autowired
    private RencontreRepository rencontreRepository;
     
   /* @GetMapping("/equipe")
public String viewHomePage(@RequestParam(name = "search", required = false) String keyword,
                           @RequestParam(name = "pays", required = false) String pays,
                           Model model) {
    List<Equipe> equipes;
    
    if (keyword != null && !keyword.isEmpty() && pays != null && !pays.isEmpty()) {
        // Si le nom et le pays sont fournis, effectuez la recherche avec les deux filtres
        equipes = equipeRepository.searchByKeywordAndPays(keyword, pays);
    } else if (keyword != null && !keyword.isEmpty()) {
        // Si seul le nom est fourni
        equipes = equipeRepository.searchByKeyword(keyword);
    } else if (pays != null && !pays.isEmpty()) {
        // Si seul le pays est fourni
        equipes = equipeRepository.searchByPays(pays);
    } else {
        // Sinon, récupérez toutes les équipes
        equipes = equipeRepository.findAll();
    }

    model.addAttribute("listEquipe", equipes);
    model.addAttribute("keyword", keyword);
    model.addAttribute("pays", pays); // Ajoute le paramètre de recherche pour le pays
    return "equipe";
}*/
     
     
     
     
     
     @GetMapping("/team/filter1")
public String filterEquipe(@RequestParam(name = "filter", required = false) String filter, Model model) {
    List<Equipe> equipes;

    if (filter != null && !filter.isEmpty()) {
        // Utilisez la méthode de recherche par mot-clé ou pays
        equipes = equipeRepository.searchByKeyword(filter);
    } else {
        // Si aucun filtre n'est fourni, afficher toutes les équipes
        equipes = equipeRepository.findAll();
    }

    model.addAttribute("listEquipe", equipes);
    model.addAttribute("filter", filter);  // Assurez-vous de transmettre le terme de recherche
    return "equipe";  // Retourner la vue "equipe"
}



     
   @GetMapping("/equipe")  // Afficher toutes les équipes ou les résultats de la recherche
   
   public String viewHomePage(@RequestParam(name = "search", required = false) String keyword, Model model) {
        List<Equipe> equipes;
        
        if (keyword != null && !keyword.isEmpty()) {
            // Si un terme de recherche est fourni, effectuez la recherche
            equipes = equipeRepository.searchByKeyword(keyword);
        } else {
            // Sinon, récupérez toutes les équipes
            equipes = equipeRepository.findAll();
        }

        model.addAttribute("listEquipe", equipes);
        model.addAttribute("keyword", keyword); // Ajouter le terme de recherche pour le pré-remplir dans la barre de recherche
        return "equipe"; // Retourner la vue "equipe"
    }
  
     
     
// @GetMapping("/equipe")  // c'est la handler method appelée pour cette url (racine de http://localhost:8080)
// public String viewHomePage(Model model)
// {
    // model.addAttribute("listEquipe", equipeRepository.findAll());
 //return "equipe"; // retourne le nom de la vue, càd index.html
 
 //}
/*@Autowired
private JoueurRepository joueurRepository;

@GetMapping("/equipe/new")
public String showNewEquipeForm(Model model) {
    Equipe equipe = new Equipe();
    List<Joueur> allJoueurs = joueurRepository.findAll(); // Récupérer tous les joueurs
    model.addAttribute("equipe", equipe);
    model.addAttribute("allJoueurs", allJoueurs); // Ajouter la liste des joueurs au modèle
    return "new_equipe";
}*/

 @GetMapping("/equipe/new")
    public String showNewEquipeForm(Model model)
    {
        //create model attribute to bind Form data
        Equipe equipe = new Equipe();
        model.addAttribute("equipe", equipe);
        return "new_equipe";
    }
   @PostMapping("/equipe/saveNew")
    public String saveNewEquipe(@Valid Equipe equipe, Errors errors)
    {
         
        if (equipeRepository.existsByNom(equipe.getNom())) {
        errors.rejectValue("nom", "nom.existe", "Une équipe avec ce nom existe déjà");
    }
    if (equipeRepository.existsByPays(equipe.getPays())) {
        errors.rejectValue("pays", "pays.existe", "Une équipe de ce pays existe déjà");
    }
    if (equipeRepository.existsByClassement(equipe.getClassement())) {
        errors.rejectValue("classement", "classement.existe", "Une équipe avec ce classement existe déjà");
    }
        if (errors.hasErrors())
        {         
            return "new_equipe";
        }
        //save department to DB
        equipeRepository.save(equipe);
        return "redirect:/equipe";
    }
    
  
    
    
     @GetMapping("/equipe/update")
    public String showUpdateEquipeForm(@RequestParam(name="id") long id, Model model)
    {
        Optional<Equipe> optional = equipeRepository.findById(id);
        Equipe equipe = null;
        if (optional.isPresent())
            equipe = optional.get();
        else
            throw new RuntimeException ("Equipe not found for id:: " + id);
        
        //set equipe as a model attribute to pre-populate the form
        model.addAttribute("equipe", equipe);
        return "update_equipe";
    }
    
    @PostMapping("/equipe/saveUpdate")
    public String saveUpdateEquipe(@Valid Equipe equipe, Errors errors)
    {
        
        // Vérifier si une équipe avec le même nom, pays ou classement existe déjà (en excluant l'équipe actuelle)
    if (equipeRepository.existsByNomAndIdNot(equipe.getNom(), equipe.getId())) {
        errors.rejectValue("nom", "nom.existe", "Une équipe avec ce nom existe déjà");
    }
    if (equipeRepository.existsByPaysAndIdNot(equipe.getPays(), equipe.getId())) {
        errors.rejectValue("pays", "pays.existe", "Une équipe de ce pays existe déjà");
    }
    if (equipeRepository.existsByClassementAndIdNot(equipe.getClassement(), equipe.getId())) {
        errors.rejectValue("classement", "classement.existe", "Une équipe avec ce classement existe déjà");
    }

        if (errors.hasErrors())
        {
            return "update_equipe";
        }
        //save department to DB
        equipeRepository.save(equipe);
        return "redirect:/equipe"; 
    }
   /*@GetMapping("/equipe/delete")
    public String deleteEquipe(@RequestParam(name="id") long id, Model model)
    {
        Optional<Equipe> optional = equipeRepository.findById(id);
        Equipe equipe = null;
        if (optional.isPresent())
        {
            equipe = optional.get();
            equipeRepository.deleteById(id);
        }
        else
            throw new RuntimeException ("Equipe not found for id: " + id);

        return "redirect:/equipe";
    }*/
    
   @GetMapping("/equipe/delete")
public String deleteEquipe(@RequestParam(name="id") long id, Model model) {
    try {
        Optional<Equipe> optional = equipeRepository.findById(id);
        Equipe equipe = null;
        if (optional.isPresent()) {
            equipe = optional.get();

            // Vérifier si l'équipe est utilisée dans des rencontres
            List<Rencontre> rencontres = rencontreRepository.findByEquipe2Id(id);
            if (!rencontres.isEmpty()) {
                // Si des rencontres existent, empêchez la suppression et affichez un message d'erreur
                model.addAttribute("error", "Impossible de supprimer  l'équipe   car elle est utilisée dans des rencontres.");
                return "errorPage";  // Affiche une page d'erreur ou redirige vers la vue d'équipe
            }

            // Si l'équipe n'est pas utilisée dans des rencontres, procédez à la suppression
            equipeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Equipe non trouvée pour l'id: " + id);
        }
    } catch (DataIntegrityViolationException e) {
        // Gestion d'une exception liée à une contrainte d'intégrité
        model.addAttribute("error", "Erreur d'intégrité des données : " + e.getMessage());
        return "errorPage";  // Rediriger vers une page d'erreur
    }

    return "redirect:/equipe";  // Rediriger vers la page d'affichage des équipes
}
 }
       
