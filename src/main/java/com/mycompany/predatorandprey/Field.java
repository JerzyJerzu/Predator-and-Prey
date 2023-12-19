/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.predatorandprey;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * a field on a board - stores information about animals inside
 * @author User
 */
public abstract class Field extends JPanel
{
    protected Set<Animal> PREDATORS;
    protected Set<Animal> PREYS;
    protected Field instance = this;
    private final int[] position;
    private final World TheWorld;
    private Semaphore Lock;
    private String name;
    /**
    * Constructor
     * @param position
    */
    public Field(int[] position)
    {
        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        //name = "bez tytulu";
        PREDATORS = new HashSet();
        PREYS = new HashSet();
        Lock = new Semaphore(1);
        this.TheWorld = World.GetInstance();
        this.position = position;
        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
    }
    /**
    * updates and displays the field panel
     * @throws java.lang.InterruptedException
    */
    public void update() throws InterruptedException
    {
        //Lock.acquire();
        TheWorld.UpadateSemaphore.acquire();
        //System.out.println("started updating: "+Arrays.toString(this.position));
        this.removeAll();
        for(Animal ThePrey : PREYS)
        {
            this.add(ThePrey);
        }
        for(Animal ThePredator : PREDATORS)
        {
            this.add(ThePredator);
        }
        //Thread.sleep(1000);
        
        TheWorld.revalidate();
        TheWorld.repaint();
        //System.out.println("ended updating: "+Arrays.toString(this.position));
        TheWorld.UpadateSemaphore.release();
        //Lock.release();
    }
    /**
    * removes animal from the field
     * @param AnimalToRemove
     * @throws java.lang.InterruptedException
    */
    public synchronized void removeAnimal(Animal AnimalToRemove) throws InterruptedException
    {
        Lock.acquire();
        if(PREDATORS.contains(AnimalToRemove))
        {
            PREDATORS.remove(AnimalToRemove);
        }
        else if(PREYS.contains(AnimalToRemove))
        {
            PREYS.remove(AnimalToRemove);
        }
        else
        {
            System.out.println("No animal on field to remove");
        }
        update();
        Lock.release();
    }
    /**
    * adds animal to the field
     * @param AnimalToAdd
    */
    public abstract void addAnimal(Animal AnimalToAdd);

    public int[] getPosition() {
        return position;
    }
    /**
    * returns true if there are preys on the field
     * @return 
    */
    public boolean NoPreysNearBy()
    {
        return PREYS.isEmpty();
    }
    /**
     * locks field, When field is locked no animal can be added or removed from it.
     * @throws InterruptedException 
     */
    public void lockField() throws InterruptedException
    {
        Lock.acquire();
    }
    /**
     * unlocks field, When field is locked no animal can be added or removed from it.
     * @throws InterruptedException 
     */
    public void UnlockField() throws InterruptedException
    {
        Lock.release();
    }
    /**
     * returns one prey from the field, so predator can eat it 
     * @return 
     */
    public Prey GetPrey()
    {
        for(Animal Someprey : PREYS)
        {
            return (Prey) Someprey;
        }
        System.out.println("No prey on field!");
        return null;
    }
    public String getFieldName() {
        return name;
    }
    public void setFieldName(String name) {
        this.name = name;
    }
}
