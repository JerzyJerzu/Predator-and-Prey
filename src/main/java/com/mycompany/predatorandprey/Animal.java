/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.predatorandprey;

import java.awt.Dimension;
import java.util.Random;
import javax.swing.JPanel;

/**
 * Abstract class which stores some parameters and methods that are common for all animals
 * @author User
 */
public abstract class Animal extends JPanel implements Runnable, Cloneable
{
    protected Animal instance;
    protected Field location;
    private final String SpeciesName;
    protected int health;
    private final int MaxHealth;
    protected final int speed;
    protected final int strength;
    private final String name;
    protected final World TheWorld;
    protected int stomachSize;
    private final Species TheSpecies;
    //private String spieceName;

    public Animal(World TheWorld, Species TheSpecies,Field location) {
        this.TheSpecies = TheSpecies;
        this.stomachSize = TheSpecies.RandStomachSize();
        this.SpeciesName = TheSpecies.GetSpeciesName();
        this.MaxHealth = TheSpecies.RandHealth();
        this.speed = TheSpecies.RandSpeed();
        this.strength = TheSpecies.RandStrength();
        this.health = MaxHealth;
        this.location = location;
        
        String[] NAMES = {"Bob","Tom","Rob","Max","Sam","Dax","Rey","Gus","Jay"};
        int index = (new Random()).nextInt(NAMES.length);
        this.name =  NAMES[index];
        
        this.setMaximumSize(new Dimension(20,20));
        this.TheWorld = TheWorld;
        this.location = location;
        instance = this;
        location.addAnimal(this);
    }

    /**
     * Returns list fields where animal (predator or prey) can go from a given position
     * @param position
     * @return
     */
    public abstract Field[] get_neighbouring_fields(int[] position);
    /**
     * moves animal to field given as destination
     * @param destination
     * @throws java.lang.InterruptedException
     */
    public void move(Field destination) throws InterruptedException
    {
        destination.addAnimal(this);
        location.removeAnimal(this);
        location = destination;
    }
    public boolean IsDead()
    {
        return this.health<=0;
    }
     /**
     * one just cannot simply die before cleaning up after himself,
     * removes animals coordinates from the board and delates animal
     * @throws java.lang.InterruptedException
     */ 
    public void die() throws InterruptedException
    {
        health = 0;
        location.removeAnimal(this);
    }

    public String getSpeciesName() {
        return SpeciesName;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return MaxHealth;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStrength() {
        return strength;
    }

    public String getAnimalName() {
        return name;
    }

    public int getStomachSize() {
        return stomachSize;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }

    public Species getTheSpecies() {
        return TheSpecies;
    }
}
