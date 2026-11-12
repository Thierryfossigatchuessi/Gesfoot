package com.example.gesfootWeb.controller;

import com.example.gesfootWeb.entity.Spectateur;
import com.example.gesfootWeb.repository.BilletRepository;
import com.example.gesfootWeb.repository.SpectateurRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.validation.Errors;

@Controller
public class SpectateurController {

    @Autowired
    private SpectateurRepository spectateurRepository;
    @Autowired
    private BilletRepository billetRepository;

    // Route pour afficher la liste des spectateurs
    @GetMapping("/spectateur")
    public String viewHomePage(Model model) {
        List<Spectateur> spectateurs = spectateurRepository.findAll(); 
        model.addAttribute("listSpectateur", spectateurs); // Affichage des spectateurs
        return "spectateur"; // Vue affichant la liste des spectateurs
    }
    
    // Afficher le formulaire de création d'un nouveau spectateur
    @GetMapping("/spectateur/new")
    public String showNewSpectateurForm(Model model) {
        Spectateur spectateur = new Spectateur(); // Créer un objet vide pour le formulaire
        model.addAttribute("spectateur", spectateur); // Passer cet objet au formulaire
        return "new_spectateur"; // Vue du formulaire
    }

    // Enregistrer un nouveau spectateur
    
    @PostMapping("/spectateur/saveNew")
public String saveNewSpectateur(@Valid Spectateur spectateur, Errors errors, Model model) {
    if (errors.hasErrors()) {
        return "new_spectateur"; // Si erreurs, revenir au formulaire
    }

    // Vérifier si un spectateur avec cet email existe déjà
    Optional<Spectateur> existingSpectateur = spectateurRepository.findByEmail(spectateur.getEmail());
    if (existingSpectateur.isPresent()) {
        // Ajouter un message d'erreur si un doublon est trouvé
        model.addAttribute("emailError", "Un spectateur avec cet email existe déjà.");
        return "new_spectateur"; // Retourner au formulaire
    }

    // Sauvegarder le nouveau spectateur dans la base de données
    spectateurRepository.save(spectateur);
    return "redirect:/spectateur"; // Redirection vers la liste des spectateurs
}

    /*@PostMapping("/spectateur/saveNew")
    public String saveNewSpectateur(@Valid Spectateur spectateur, Errors errors) {
        if (errors.hasErrors()) {
            return "new_spectateur"; // Si erreurs, revenir au formulaire
        }
        spectateurRepository.save(spectateur); // Sauvegarde dans la base de données
        return "redirect:/spectateur"; // Redirection vers la liste des spectateurs
    }*/
     // Mettre à jour un billet existant
    @PostMapping("/spectateur/saveUpdate")
public String saveUpdateSpectateur(@Valid Spectateur spectateur, Errors errors) {
    if (errors.hasErrors()) {
        return "update_spectateur"; // Si des erreurs de validation sont présentes, on renvoie vers le formulaire de mise à jour
    }
    spectateurRepository.save(spectateur); // Sauvegarde du spectateur mis à jour
    return "redirect:/spectateur"; // Redirige vers la liste des spectateurs après la mise à jour
}

    
    
   @GetMapping("/spectateur/update/{id}")
public String showUpdateSpectateurForm(@PathVariable(name="id") long id, Model model) {
    Optional<Spectateur> optionalSpectateur = spectateurRepository.findById(id);
    if (optionalSpectateur.isPresent()) {
        Spectateur spectateur = optionalSpectateur.get();
        model.addAttribute("spectateur", spectateur);
        return "update_spectateur"; // Retourne la vue de mise à jour
    } else {
        throw new RuntimeException("Spectateur non trouvé pour l'ID: " + id); // Gérer l'erreur si le spectateur n'est pas trouvé
    }
}


    // Supprimer un spectateur
    /*@GetMapping("/spectateur/delete")
    public String deleteSpectateur(@RequestParam(name = "id") long id) {
        Optional<Spectateur> optional = spectateurRepository.findById(id);
        if (optional.isPresent()) {
            spectateurRepository.deleteById(id); // Supprime le spectateur de la base de données
        } else {
            throw new RuntimeException("spectateur non trouvé pour l'ID: " + id); // Si le spectateurf n'est pas trouvé, une erreur est levée
        }
        return "redirect:/spectateur"; // Redirige vers la liste des billets après suppression
    }*/

// Supprimer un spectateur (via POST)
@PostMapping("/spectateur/delete")
public String deleteSpectateur(@RequestParam(name = "id") long id) {
    Optional<Spectateur> optional = spectateurRepository.findById(id);
    if (optional.isPresent()) {
        spectateurRepository.deleteById(id); // Supprime le spectateur de la base de données
    } else {
        throw new RuntimeException("Spectateur non trouvé pour l'ID: " + id); // Si le spectateur n'est pas trouvé, une erreur est levée
    }
    return "redirect:/spectateur"; // Redirige vers la liste des spectateurs après suppression
}

}

