/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.predatorandprey;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A field with no limit of animals, where all animals can go
 * @author User
 */
public class Path extends Field {
    public Path(int[] position) {
        super(position);
        this.setBackground(Color.lightGray);
    }

    @Override
    public void addAnimal(Animal AnimalToAdd) {
        try {
            this.lockField();
        } catch (InterruptedException ex) {
            Logger.getLogger(Path.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(AnimalToAdd instanceof Predator)
        {
            PREDATORS.add(AnimalToAdd);
            try {
                this.update();
            } catch (InterruptedException ex) {
                Logger.getLogger(Path.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            PREYS.add(AnimalToAdd);
            try {
                this.update();
            } catch (InterruptedException ex) {
                Logger.getLogger(Path.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            this.UnlockField();
        } catch (InterruptedException ex) {
            Logger.getLogger(Path.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
