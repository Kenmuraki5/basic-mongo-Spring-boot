package com.example.lab6.controller;
import com.example.lab6.pojo.Wizard;
import com.example.lab6.pojo.Wizards;
import com.example.lab6.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;
    private Wizards wizards = new Wizards();

    @GetMapping("/wizards")
    public List<Wizard> getWizards() {
        List<Wizard> wizardsList = wizardService.retrieveWizards();
//        this.wizards.getModel().clear(); // Clear the existing elements if needed
//        this.wizards.getModel().addAll(wizardsList);
        this.wizards.setModel((ArrayList<Wizard>) wizardsList);
        return this.wizards.getModel();
    }

    @GetMapping("/wizard/{name}")
    public ResponseEntity<Wizard> getWizardByName(@PathVariable("name") String name) {
        Wizard wizard = wizardService.retrieveWizardByName(name);
        return ResponseEntity.ok(wizard);
    }

    @PostMapping("/addWizard")
    public ResponseEntity<Wizard> createWizard(@RequestBody MultiValueMap<String, String> formdata) {
        Map<String, String> d = formdata.toSingleValueMap();
        int mon = Integer.parseInt(d.get("money"));
        Wizard wizard = wizardService.createWizard(
                new Wizard(null, d.get("name"), d.get("sex"), d.get("position"), mon, d.get("school"), d.get("house"))
        );
        return ResponseEntity.ok(wizard);
    }


    @PostMapping("/deleteWizard/{id}")
    public ResponseEntity<Boolean> deleteWizard(@PathVariable("id") String id) {
        Wizard wizard = wizardService.findByID(id);
        boolean status = wizardService.deleteWizard(wizard);
        if (status) {
            // Remove the deleted Wizard from the Wizards model
            wizards.getModel().remove(wizard);
        }
        return ResponseEntity.ok(status);
    }

    @GetMapping("/countWizards")
    public ResponseEntity<Integer> countWizards() {
        int count = wizardService.countWizards();
        return ResponseEntity.ok(count);
    }
    @PostMapping(value = "/updateWizard")
    public boolean updateBook(@RequestBody MultiValueMap<String, String> formdata) {
//        Wizard wizard = wizardService.retrieveWizardByName(nameOld);
//        if(wizard != null) {
//            int mon = Integer.parseInt(money);
//            wizardService.updateWizard(new Wizard(wizard.get_id(), nameNew, group, position, mon, school, house));
//            return true;
//        }else {
//            return false;
//        }
         Map<String, String> d = formdata.toSingleValueMap();
         int mon = Integer.parseInt(d.get("money"));
         wizardService.updateWizard(
                 new Wizard(d.get("id"), d.get("nameNew"), d.get("sex"), d.get("position")
                         , mon, d.get("school"), d.get("house"))
         );
         return true;
    }
}
