package com.example.gesfootWeb.controller;
/*
import com.example.gesfootWeb.entity.Billet;
import com.example.gesfootWeb.repository.BilletRepository;
import jakarta.validation.Valid;
//import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;

/*@Controller
 public class BilletController {
     @Autowired
    private BilletRepository billetRepository;
     @GetMapping("/billet/new")
    public String showNewBilletForm(Model model)
    {
        //create model attribute to bind Form data
        Billet billet = new Billet();
        model.addAttribute("billet", billet);
        return "new_billet";
    }*/

/*@Controller
 public class BilletController {
     @Autowired
    private BilletRepository billetRepository;
 @GetMapping("/billet")  // c'est la handler method appelée pour cette url (racine de http://localhost:8080)
 public String viewHomePage(Model model)
 {
    // model.addAttribute("billet", billet);
     //model.addAttribute("listBillet", billetRepository.findAll());
 return "billet"; // retourne le nom de la vue, càd index.html
 }
 

   @PostMapping("/billet/saveNew")
    public String saveNewBillet(@Valid Billet billet, Errors errors)
    {
       
        if (errors.hasErrors())
        {         
            return "new_billet";
        }
        //save department to DB
        billetRepository.save(billet);
        return "redirect:/billet";
    }
    /*
    @GetMapping("/billet/update")
    public String showUpdateBilletForm(@RequestParam(name="id") long id, Model model)
    {
        Optional<Billet> optional = billetRepository.findById(id);
        Billet billet = null;
        if (optional.isPresent())
            billet = optional.get();
        else
            throw new RuntimeException ("Billet not found for id:: " + id);
        
        //set equipe as a model attribute to pre-populate the form
        model.addAttribute("billet", billet);
        return "update_billet";
    }
    
   @PostMapping("/billet/saveUpdate")
public String saveUpdateBillet(@Valid Billet billet, Errors errors) {
    if (errors.hasErrors()) {
        return "update_billet";
    }
    billetRepository.save(billet);
    return "redirect:/billet"; // Redirigez vers la liste des billets après la mise à jour
}

  @GetMapping("/billet/delete")
public String deleteBillet(@RequestParam(name = "id") long id, Model model) {
    Optional<Billet> optional = billetRepository.findById(id);
    if (optional.isPresent()) {
        billetRepository.deleteById(id);
    } else {
        throw new RuntimeException("Billet not found for id: " + id);
    }
    return "redirect:/billet"; // Rediriger vers la liste des billets après suppression
}*/

 
    


import com.example.gesfootWeb.entity.Billet;
import com.example.gesfootWeb.repository.BilletRepository;
import com.example.gesfootWeb.repository.RencontreRepository;
import com.example.gesfootWeb.repository.SpectateurRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@Controller
public class BilletController {

    @Autowired
    private BilletRepository billetRepository;

    @Autowired
    private SpectateurRepository spectateurRepository;

    @Autowired
    private RencontreRepository rencontreRepository;

    // Afficher la liste des billets
    @GetMapping("/billet")
    public String viewHomePage(Model model) {
        model.addAttribute("listBillet", billetRepository.findAll());
        return "billet"; // retourne le nom de la vue, billet.html
    }
    

    // Afficher le formulaire de création d'un nouveau billet
    @GetMapping("/billet/new")
    public String showNewBilletForm(Model model) {
        Billet billet = new Billet();
        // On récupère la liste des spectateurs et des rencontres
        model.addAttribute("billet", billet);
        model.addAttribute("spectateurs", spectateurRepository.findAll()); // Liste des spectateurs
        model.addAttribute("rencontres", rencontreRepository.findAll()); // Liste des rencontres
        return "new_billet"; // Affiche le formulaire de création de billet
    }

    // Enregistrer un nouveau billet
    @PostMapping("/billet/saveNew")
    public String saveNewBillet(@Valid Billet billet, Errors errors) {
        if (errors.hasErrors()) {
            return "new_billet"; // Si des erreurs de validation sont présentes, on renvoie vers le formulaire
        }
        billetRepository.save(billet); // Sauvegarde le billet dans la base de données
        return "redirect:/billet"; // Redirige vers la liste des billets
    }

    // Mettre à jour un billet existant
    @PostMapping("/billet/saveUpdate")
    public String saveUpdateBillet(@Valid Billet billet, Errors errors) {
        if (errors.hasErrors()) {
            return "update_billet"; // Si des erreurs de validation sont présentes, on renvoie vers le formulaire de mise à jour
        }
        billetRepository.save(billet); // Sauvegarde du billet mis à jour
        return "redirect:/billet"; // Redirige vers la liste des billets après la mise à jour
    }
    
    
    @GetMapping("/billet/update")
public String showUpdateBilletForm(@RequestParam(name="id") long id, Model model) {
    Optional<Billet> optionalBillet = billetRepository.findById(id);
    if (optionalBillet.isPresent()) {
        Billet billet = optionalBillet.get();
        model.addAttribute("billet", billet);
        model.addAttribute("spectateurs", spectateurRepository.findAll()); // Liste des spectateurs
        model.addAttribute("rencontres", rencontreRepository.findAll()); // Liste des rencontres
        return "update_billet"; // Retourne la vue de mise à jour
    } else {
        throw new RuntimeException("Billet non trouvé pour l'ID: " + id); // Gestion des erreurs si le billet n'est pas trouvé
    }
}


    // Supprimer un billet
    @GetMapping("/billet/delete")
    public String deleteBillet(@RequestParam(name = "id") long id) {
        Optional<Billet> optional = billetRepository.findById(id);
        if (optional.isPresent()) {
            billetRepository.deleteById(id); // Supprime le billet de la base de données
        } else {
            throw new RuntimeException("Billet non trouvé pour l'ID: " + id); // Si le billet n'est pas trouvé, une erreur est levée
        }
        return "redirect:/billet"; // Redirige vers la liste des billets après suppression
    }
}

    
  
    