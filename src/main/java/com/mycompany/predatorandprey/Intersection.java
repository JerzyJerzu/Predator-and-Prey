/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.predatorandprey;

import java.awt.Color;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A field where can be only one prey at the moment and unlimited number of predators
 * @author User
 */
public class Intersection extends Field {
    private Semaphore IntersectionSemaphore;
    public Intersection(int[] position) {
        super(position);
        IntersectionSemaphore = new Semaphore(1);
        this.setBackground(Color.yellow);
    }
    
    @Override
    public void addAnimal(Animal AnimalToAdd)
    {
        try {
            if(AnimalToAdd instanceof Prey)
            {
                IntersectionSemaphore.acquire();
                this.lockField();
                //System.out.println("acquired");
                PREYS.add(AnimalToAdd);
                this.update();
                this.UnlockField();
            }
            else
            {
            this.lockField();
            PREDATORS.add(AnimalToAdd);
            this.update();
            this.UnlockField();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Intersection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
    * removes animal from the field
    */
    @Override
    public void removeAnimal(Animal AnimalToRemove) throws InterruptedException
    {
        this.lockField();
        if(PREDATORS.contains(AnimalToRemove))
        {
            PREDATORS.remove(AnimalToRemove);
        }
        else if(PREYS.contains(AnimalToRemove))
        {
            PREYS.remove(AnimalToRemove);
            IntersectionSemaphore.release();
            //System.out.println("released");
        }
        else
        {
            System.out.println("No animal on field to remove");
        }
        update();
        this.UnlockField();
    }
}
