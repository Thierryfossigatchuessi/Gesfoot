/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.gesfootWeb.controller;

import com.example.gesfootWeb.entity.Stade;
import com.example.gesfootWeb.repository.StadeRepository;
import jakarta.validation.Valid;
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
public class StadeController {

    @Autowired
    private StadeRepository stadeRepository;

    @GetMapping("/stade")
    public String viewStadePage(Model model, @RequestParam(name = "motCle", defaultValue = "") String mc) {
        model.addAttribute("listStade", stadeRepository.findByNomContaining(mc));
        return "stade"; // retourne la vue stade.html
    }

    @GetMapping("/stade/new")
    public String showNewStadeForm(Model model) {

        Stade stade = new Stade();
        model.addAttribute("stade", stade);
        return "new_stade";
    }

    @PostMapping("/stade/saveNew")
    public String saveNewStade(@Valid @ModelAttribute("stade") Stade stade, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "new_stade";
        }
        try {
            String nom = stade.getNom().trim();
            stade.setNom(nom);
            stadeRepository.save(stade);
        } catch (Exception e) {
            model.addAttribute("errorMemeStade", "le stade exsite déjà !!");
            return "new_stade";
        }
        return "redirect:/stade";
    }

    @GetMapping("/stade/update")
    public String showUpdateStadeForm(@RequestParam(name = "id") long id, Model model) {
        Optional<Stade> optional = stadeRepository.findById(id);
        Stade stade = null;
        if (optional.isPresent()) {
            stade = optional.get();
        } else {
            throw new RuntimeException("Stade not found for id:: " + id);
        }

        model.addAttribute("stade", stade);
        return "update_stade";
    }

    @PostMapping("/stade/saveUpdate")
    public String saveUpdateStade(@Valid @ModelAttribute("stade") Stade stade, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "update_stade";
        }
        stadeRepository.save(stade);
        return "redirect:/stade";
    }

    @GetMapping("/stade/delete")
    public String deleteStade(@RequestParam(name = "id") long id, Model model) {
        Optional<Stade> optional = stadeRepository.findById(id);
        Stade stade = null;
        if (optional.isPresent()) {
            stade = optional.get();
            stadeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Stade not found for id: " + id);
        }

        return "redirect:/stade";
    }
}
