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
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * its objects represent predators
 * @author User
 */
public class Predator extends Animal {
    private boolean relaxed;
    private int thirst;
    private int relaxedSpeeed;

    public Predator(World TheWorld, Field location) {
        super(TheWorld,SpeciesStrategies(), location);
        this.relaxed = true;
        thirst = stomachSize;
        relaxedSpeeed = 3;
        this.setBackground(Color.black);
        this.addMouseListener(new MouseAdapter()
        {
            private Color background;

                @Override
                public void mousePressed(MouseEvent e) {
                    background = getBackground();
                    setBackground(Color.BLUE);
                    repaint();
                    AnimalInformationWindow InfoWindow = new PredatorInformationWindow((Predator) instance);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBackground(background);
                }
        });
    }
    private static Species SpeciesStrategies()
    {
        int selector = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        Species TheSpecies = null;
        if(selector == 1)
        {
            TheSpecies = new Tiger();
        }
        if(selector == 2)
        {
            TheSpecies = new Panter();
        }
        return TheSpecies;
    }
    /**
     * If there is a prey near by, attacks it and changes mode to relaxation mode
     * returns false if no prey on field 
     * @throws java.lang.InterruptedException 
     */
    public void hunt() throws InterruptedException
    {
       //requires syncronization when attacking a prey (sem try accquire)
       location.lockField();
       if(location.NoPreysNearBy())
       {
          location.UnlockField();
          return; 
       }
       Prey Victim = location.GetPrey();
       //add syncronization trywait
       if(!Victim.IsDead())
       {
           Victim.recieve_damage(strength);
           location.UnlockField();
           if(Victim.IsDead())
           {
           this.relaxed = true;
           relaxedSpeeed = 3;
           System.out.println("GOT IT! AND KILLED IT!");
           }
           thirst = stomachSize;
           System.out.println("ATTACKED IT");
       }
       else
       {
           location.UnlockField();
           System.out.println("Almost GOT IT!");
       }
    }
        /**
    * returns collection of fields where predator can go from position
     * @param position - array with 2 records {x,y}
     * @return collection of objects type Field
    */
    @Override
    public Field[] get_neighbouring_fields(int[] position)
    {
        int[] NeighbourPosition;
        //Set<Field> neighbouringFields = new HashSet();
        //position = {y,x}
        Field[] neighbouringFields = new Field[4];
        int i = 0;
        if(position[0] > 0)
        {
            NeighbourPosition = Arrays.copyOf(position,2);
            --NeighbourPosition[0]; //This shit
            if(!(TheWorld.get_field(NeighbourPosition) instanceof Hideout))
            {
                neighbouringFields[i] = TheWorld.get_field(NeighbourPosition);
                i++;
            };
        }
        if(position[1] > 0)
        {
            NeighbourPosition = Arrays.copyOf(position,2);
            --NeighbourPosition[1];
            if(!(TheWorld.get_field(NeighbourPosition) instanceof Hideout))
            {
                neighbouringFields[i] = TheWorld.get_field(NeighbourPosition);
                i++;
            }
        }
        if(position[1] < TheWorld.getMapSize()-1)
        {
            NeighbourPosition = Arrays.copyOf(position,2);
            ++NeighbourPosition[1];
            if(!(TheWorld.get_field(NeighbourPosition) instanceof Hideout))
            {
                neighbouringFields[i] = TheWorld.get_field(NeighbourPosition);
                i++;
            }
        }
        if(position[0] < TheWorld.getMapSize()-1)
        {
            NeighbourPosition = Arrays.copyOf(position,2);
            ++NeighbourPosition[0];
            if(!(TheWorld.get_field(NeighbourPosition) instanceof Hideout))
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
     * changes predator position depending on his speed and if predator is in hunting mode calls hunt()
     * if predator is not in hunting mode, increments thirst counter
     */
    @Override
    public void run() {
        try
        {
            while(true)
            {
                Thread.sleep(10000/speed*relaxedSpeeed);
                Field neighbours[] = get_neighbouring_fields(location.getPosition());
                int index = (new Random()).nextInt(neighbours.length);
                Field destination = neighbours[index];
                this.move(destination);
                if(!relaxed)
                {
                   this.hunt();
                }
                thirst--;
                if(thirst <= 0)
                {
                    this.relaxed = false;
                    relaxedSpeeed = 1;
                }
                if(IsDead())
                {
                    break;
                }
            }
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(Predator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getThirst() {
        return thirst;
    }
}