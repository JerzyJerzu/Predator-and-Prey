/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.predatorandprey;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Field which can be only visited by predators
 * @author User
 */
public class Forest extends Field {

    public Forest(int[] position) {
        super(position);
        this.setBackground(Color.green);
    }

    @Override
    public void addAnimal(Animal AnimalToAdd) {
        try {
            this.lockField();
        } catch (InterruptedException ex) {
            Logger.getLogger(Forest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(AnimalToAdd instanceof Predator)
        {
            PREDATORS.add(AnimalToAdd);
            try {
                this.update();
            } catch (InterruptedException ex) {
                Logger.getLogger(Forest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.out.println("Prey cannot visit forests!");
        }
        try {
            this.UnlockField();
        } catch (InterruptedException ex) {
            Logger.getLogger(Forest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
