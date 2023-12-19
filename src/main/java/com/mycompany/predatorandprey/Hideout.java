/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.predatorandprey;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A field which can be only visited by limited number of preys and no predators at the moment
 * @author User
 */
public class Hideout extends Field {
    private final int MaxNumberOfAnimals;
    private Semaphore HideoutSemaphore;
    public Hideout(String Thename,int[] position, int MaxNumberOfAnimals) 
    {
        super(position);
        setFieldName(Thename);
        this.MaxNumberOfAnimals = MaxNumberOfAnimals;
        this.setBackground(Color.pink);
        HideoutSemaphore = new Semaphore(MaxNumberOfAnimals);
        World.GetInstance().AddHIDEOUT(this);
        this.addMouseListener(new MouseAdapter()
        {
            private Color background;

                @Override
                public void mousePressed(MouseEvent e) {
                    background = getBackground();
                    setBackground(Color.BLUE);
                    repaint();
                    FieldInformationWindow InfoWindow = new HideoutInformationWindow((Hideout) instance);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBackground(background);
                }
        });
    }
    @Override
    public void addAnimal(Animal AnimalToAdd) {
        if(AnimalToAdd instanceof Prey)
        {
            try {
                HideoutSemaphore.acquire();
                this.lockField();
                PREYS.add(AnimalToAdd);
                try {
                    this.update();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Hideout.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.UnlockField();
                return;
            } catch (InterruptedException ex) {
                Logger.getLogger(Hideout.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.out.println("Predator cannot visit hideout!");
        }
    }
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
            HideoutSemaphore.release();
            //System.out.println("released");
        }
        else
        {
            System.out.println("No animal on field to remove");
        }
        update();
        this.UnlockField();
    }
    public int getMaxNumberOfAnimals() {
        return MaxNumberOfAnimals;
    }
}
