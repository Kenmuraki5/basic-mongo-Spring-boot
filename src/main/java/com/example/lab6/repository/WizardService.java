package com.example.lab6.repository;

import com.example.lab6.pojo.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WizardService {
    @Autowired
    private WizardRepository repository;


    public WizardService(WizardRepository repository) {
        this.repository = repository;
    }

    public List<Wizard> retrieveWizards() {
        return repository.findAll();
    }

    public Wizard retrieveWizardByName(String name) {
        return repository.findByName(name);
    }

    public Wizard createWizard(Wizard wizard) {
        return repository.save(wizard);
    }

    public boolean deleteWizard(Wizard wizard) {
        try {
            repository.delete(wizard);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Wizard findByID(String id){
        return repository.findByID(id);
    }

    public int countWizards() {
        return (int) repository.count();
    }

    public Wizard updateWizard(Wizard wizard) {
        return repository.save(wizard);
    }
}

