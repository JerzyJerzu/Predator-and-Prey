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
 * Water or a plant on map, the place where preys can eat or drink
 * @author User
 */
public class Resource extends Field {

    private final boolean water;  //True - water, False - food
    private final int PreyReplenishingSpeed;
    private final int MaxNumberOfAnimals;
    private Semaphore ResourceSemaphore;
    private String name;

    public Resource(String Thename,int[] position, int MaxNumberOfAnimals, int PreyReplenishingSpeed, boolean water) 
    {
        super(position);
        setFieldName(Thename);
        this.PreyReplenishingSpeed = PreyReplenishingSpeed;
        this.water = water;
        this.MaxNumberOfAnimals = MaxNumberOfAnimals;
        if(this.water == true)
        {
            this.setBackground(Color.blue);
            World.GetInstance().AddWATERSource(this);
        }
        else
        {
            this.setBackground(Color.orange);
            World.GetInstance().AddFOODSource(this);
        }
        ResourceSemaphore = new Semaphore(MaxNumberOfAnimals);
        this.addMouseListener(new MouseAdapter()
        {
            private Color background;

                @Override
                public void mousePressed(MouseEvent e) {
                    background = getBackground();
                    setBackground(Color.BLUE);
                    repaint();
                    FieldInformationWindow InfoWindow = new ResourceInformationWindow((Resource) instance);
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
                ResourceSemaphore.acquire();
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
            try {
                this.lockField();
                PREDATORS.add(AnimalToAdd);
                this.update();
                this.UnlockField();
            } catch (InterruptedException ex) {
                Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /*
    returns true if this resource is a water and false if it is food
    */
    public boolean isWater() {
        return water;
    }

    public int getPreyReplenishingSpeed() {
        return PreyReplenishingSpeed;
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
            ResourceSemaphore.release();
            //System.out.println("released");
        }
        else
        {
            System.out.println("No animal on field to remove");
        }
        update();
        this.UnlockField();
    }
    public int getMaxNumberOfAnimals()
    {
        return MaxNumberOfAnimals;
    }
    public int GetPreyReplenishingSpeed()
    {
        return PreyReplenishingSpeed;
    }
}
