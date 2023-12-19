/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.predatorandprey;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * its objects represent preys
 * @author User
 */
public class Prey extends Animal
{
    private Set<Field> VisitedWhileSearchingForRoute;
    private Field selectedDestination = null;
    private int food_lv;
    private int water_lv;
    private Stack<Field> Route;
    
    public Prey(World TheWorld, Species TheSpecies, Field location) {
        super(TheWorld,SpeciesStrategies(TheSpecies) , location);
        VisitedWhileSearchingForRoute = new HashSet();
        Route = new Stack<Field>();
        
        this.food_lv = stomachSize/2 - 1;
        this.water_lv = stomachSize/2 - 1;
        this.setBackground(Color.white);
        this.addMouseListener(new MouseAdapter()
        {
            private Color background;

                @Override
                public void mousePressed(MouseEvent e) {
                    background = getBackground();
                    setBackground(Color.BLUE);
                    repaint();
                    AnimalInformationWindow InfoWindow = new PreyInformationWindow((Prey) instance);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBackground(background);
                }
        });
        
    }
    private static Species SpeciesStrategies(Species TheSpecies)
    {
        if(TheSpecies == null)
        {
            int selector = ThreadLocalRandom.current().nextInt(1, 2 + 1);
            if(selector == 1)
            {
                TheSpecies = new Sheep();
            }
            if(selector == 2)
            {
                TheSpecies = new Cow();
            }
        }
            return TheSpecies;
    }     
    @Override
    public Field[] get_neighbouring_fields(int[] position)
    {
        int[] NeighbourPosition;
        Field[] neighbouringFields = new Field[4];
        int i = 0;
        if(position[0] > 0)
        {
            NeighbourPosition = Arrays.copyOf(position,2);
            --NeighbourPosition[0];
            if(!(TheWorld.get_field(NeighbourPosition) instanceof Forest))
            {
                neighbouringFields[i] = TheWorld.get_field(NeighbourPosition);
                i++;
            }
        }
        if(position[1] > 0)
        {
            NeighbourPosition = Arrays.copyOf(position,2);
            --NeighbourPosition[1];
            if(!(TheWorld.get_field(NeighbourPosition) instanceof Forest))
            {
                neighbouringFields[i] = TheWorld.get_field(NeighbourPosition);
                i++;
            }
        }
        if(position[1] < TheWorld.getMapSize()-1)
        {
            NeighbourPosition = Arrays.copyOf(position,2);
            ++NeighbourPosition[1];
            if(!(TheWorld.get_field(NeighbourPosition) instanceof Forest))
            {
                neighbouringFields[i] = TheWorld.get_field(NeighbourPosition);
                i++;
            }
        }
        if(position[0] < TheWorld.getMapSize()-1)
        {
            NeighbourPosition = Arrays.copyOf(position,2);
            ++NeighbourPosition[0];
            if(!(TheWorld.get_field(NeighbourPosition) instanceof Forest))
            {
                neighbouringFields[i] = TheWorld.get_field(NeighbourPosition);
                i++;
            }
        }
        Field result[] = new Field[i];
        System.arraycopy(neighbouringFields, 0, result, 0, i);
        return result;
    }
    /**
    * If prey is in hideout it regenerates preys halth to the maximum level
    * the time of regeneration is proportional to animal preys damage.
    */
    public void heal() throws InterruptedException
    {
        if(this.location instanceof Hideout)
        {
            while(this.getHealth()<this.getMaxHealth())
            {
                Thread.sleep(World.TIMESTEP/100);
                this.setHealth(this.getHealth()+1);
            }
        }
    }
    /**
     * DFS recursive method to find the route from start to destination.
     * After discovering destination Field it reiterates and sets route(stack of Fields) to that field as the followed route 
     * @param start 
     * @param destination
     * @return true if it has found the destination field and false, otherwise
     */
    public boolean set_rute(Field start,Field destination)
    {
        VisitedWhileSearchingForRoute.add(start);
        Field neighbours[] = get_neighbouring_fields(start.getPosition());
        for (int i = 0; i < neighbours.length; i++)  
        {
            Field current = neighbours[i];
            if(current == destination)
            {
                VisitedWhileSearchingForRoute.clear();
                Route.push(current);
                return true;
            }
            if(!VisitedWhileSearchingForRoute.contains(current))
            {
                if(set_rute(current,destination))
                {
                    Route.push(current);
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * creates new prey as a separate thread and runs it
     */
    private void reproduce() throws InterruptedException, CloneNotSupportedException
    {
        if(this.location instanceof Hideout)
        {
            Thread.sleep(World.TIMESTEP);
            for(int i = stomachSize/4; i > 0; i--)
            {
                useResources();
            }
            Animal child = new Prey(TheWorld, this.getTheSpecies(), this.location); //potential issues with copying objects
            Thread childThread = new Thread((Runnable) child);
            childThread.start();
        }
    }
    /*
    It prey is on Field with water it sets its water level to maximum and waits time proportional to 1/PreyReplenishingSpeed on that field
    */
    private void drink() throws InterruptedException
    {
        if(location instanceof Resource)
        {
            Resource waterSource = (Resource) location;
            if(waterSource.isWater())
            {
                Thread.sleep(World.TIMESTEP/waterSource.getPreyReplenishingSpeed());
                water_lv = stomachSize;
            }
        }
    }
    /*
    It prey is on Field with food it sets its food level to maximum and waits time proportional to 1/PreyReplenishingSpeed on that field
    */
    private void eat() throws InterruptedException
    {
        if(location instanceof Resource)
        {
            Resource waterSource = (Resource) location;
            if(!waterSource.isWater())
            {
                Thread.sleep(World.TIMESTEP/waterSource.getPreyReplenishingSpeed());
                food_lv = stomachSize;
            }
        }
    }
    /**
     * predator kills the prey or decreases its health by his Strength - preys strength
     * @param PredatorStrength
     */
    public synchronized void recieve_damage(int PredatorStrength)
    {
        this.health -= PredatorStrength - this.strength;
    }
    /**
     * decrements animal levels of food and water
     */
    public void useResources()
    {
        if(food_lv>0)
        {
            food_lv--;
        }
        else
        {
            recieve_damage(this.strength+1);
        }
        
        if(water_lv>0)
        {
            water_lv--;
        }
        else
        {
            this.setHealth(this.getHealth()-1);
        }
    }
    /**
     * moves along current route and changes coordinates or sets a new route.
     * Tryes to:
     * drink,
     * eat,
     * reproduce 
     * and die
     */
    @Override
    public void run()
    {
        set_rute(location,TheWorld.find_food());
        while(true)
        {
            try {
                while(!Route.empty())
                {
                    {
                        Thread.sleep(World.TIMESTEP/speed);
                        if(selectedDestination!=null)
                        {
                            Route.clear();
                            set_rute(location,selectedDestination);
                            selectedDestination = null;
                        }
                        this.move(Route.pop());
                        useResources();
                        if(IsDead())
                        {
                            System.out.println("DIED!");
                            this.die();
                            return;
                        }
                    }
                }
                heal();
                reproduce();
                drink();
                eat();
                if(water_lv<stomachSize/2)
                {
                    if(water_lv<food_lv)
                    {
                        set_rute(location,TheWorld.find_water());
                        System.out.println("going to water");
                    }
                    else
                    {
                        set_rute(location,TheWorld.find_food());
                        System.out.println("going to food");
                    }
                }
                else if(food_lv<stomachSize/2)
                {
                    set_rute(location,TheWorld.find_food());
                    System.out.println("going to food");
                }
                else if(!(location instanceof Hideout))
                {
                    set_rute(location,TheWorld.find_hideout());
                    System.out.println("going to Hideout");
                }
                if(selectedDestination!=null)
                {
                    set_rute(location,selectedDestination);
                    selectedDestination = null;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Prey.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Prey.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public int getFood_lv() {
        return food_lv;
    }

    public int getWater_lv() {
        return water_lv;
    }
    /**
     * sets field, that has beed earlier selected as preys destination
     * @param selectedDestination 
     */
    public void setSelectedDestination(Field selectedDestination) {
        this.selectedDestination = selectedDestination;
    }
}
