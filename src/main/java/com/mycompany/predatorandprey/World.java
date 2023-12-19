/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.predatorandprey;

import java.awt.GridLayout;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * class that manages the whole board and interactions between classes
 * @author User
 */
public class World extends JFrame{
    /**
     * the smaller the TIMESTEP the faster the simulation.
     */
    public static final int TIMESTEP = 10000;
    private static World only_instance = null;
    private Vector<Hideout> HIDEOUTS;
    private Vector<Resource> WATER_SOURCES;
    private Vector<Resource> FOOD_SOURCES;
    private Field[][] BOARD;
    private JPanel window;
    private final int MapSize;
    /**
     * Semaphore - is being acquired every time when some animal changes its coordintaes and map needs to be reapinted.
     */
    public Semaphore UpadateSemaphore;
    private Field selected;
    /**
    * Constructor
    * displays the whole board in some window.
    */
    public World()
    {
        HIDEOUTS = new Vector();
        WATER_SOURCES = new Vector();
        FOOD_SOURCES = new Vector();        
        UpadateSemaphore = new Semaphore(1);
        this.MapSize = 12;
        this.setTitle("Predator and prey");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
    }
    /**
     * Here is where all the coordinates of fields are given and all the fields are declared.
     */
    public void InitializeBoard()
    {
        BOARD = new Field[MapSize][MapSize];
        window = new JPanel(new GridLayout(MapSize,MapSize));
        this.getContentPane().add(window);
        //window.pack();
        for(int i = 0; i < MapSize; i++)
        {
            for(int j = 0; j < MapSize; j++)
            {
                BOARD[i][j] = new Forest(new int[]{i,j});
            }
        }
        for(int i = 0; i < MapSize; i++)
        {
            BOARD[i][8] = new Path(new int[]{i,8});
        }
        for(int i = 0; i < 4; i++)
        {
            BOARD[i][11] = new Path(new int[]{i,11});
        }
        for(int i = 0; i < MapSize; i++)
        {
            BOARD[2][i] = new Path(new int[]{2,i});
        }
        for(int i = 0; i < 5; i++)
        {
            BOARD[8][i] = new Path(new int[]{8,i});
        }
        for(int i = 5; i < 11; i++)
        {
            BOARD[i][2] = new Path(new int[]{i,2});
        }
        for(int i = 5; i < 9; i++)
        {
            BOARD[9][i] = new Path(new int[]{9,i});
        }
        BOARD[10][5] = new Path(new int[]{10,5});
        BOARD[8][5] = new Path(new int[]{8,5});
        BOARD[7][4] = new Path(new int[]{7,4});
        BOARD[7][7] = new Path(new int[]{7,7});
        BOARD[7][9] = new Path(new int[]{7,9});
        BOARD[5][7] = new Path(new int[]{5,7});
        BOARD[5][9] = new Path(new int[]{5,9});
        BOARD[3][3] = new Path(new int[]{3,3});
        BOARD[3][5] = new Path(new int[]{3,5});
        
        BOARD[2][3] = new Intersection(new int[]{2,3});
        BOARD[2][8] = new Intersection(new int[]{2,8});
        BOARD[5][8] = new Intersection(new int[]{5,8});
        BOARD[7][8] = new Intersection(new int[]{7,8});
        BOARD[9][8] = new Intersection(new int[]{9,8});
        BOARD[8][2] = new Intersection(new int[]{8,2});
        BOARD[8][4] = new Intersection(new int[]{8,4});
        BOARD[9][5] = new Intersection(new int[]{9,5});

        BOARD[0][8] = new Hideout("cave",new int[]{0,8},3);
        BOARD[5][2] = new Hideout("cave",new int[]{5,2},4);
        BOARD[10][2] = new Hideout("cave",new int[]{10,2},2);
        BOARD[11][8] = new Hideout("cave",new int[]{11,8},5);
        BOARD[7][10] = new Hideout("cave",new int[]{7,10},2);
        
        BOARD[4][5] = new Resource("lake",new int[]{4,5},3,10,true);
        BOARD[4][11] = new Resource("lake",new int[]{4,11},3,10,true);
        BOARD[5][6] = new Resource("lake",new int[]{5,6},3,10,true);
        BOARD[7][6] = new Resource("lake",new int[]{7,6},3,10,true);
        BOARD[6][4] = new Resource("lake",new int[]{6,4},3,10,true);
        
        BOARD[2][0] = new Resource("plant",new int[]{2,0},3,10,false);
        BOARD[8][0] = new Resource("plant",new int[]{8,0},3,10,false);
        BOARD[4][3] = new Resource("plant",new int[]{4,3},3,10,false);
        BOARD[10][4] = new Resource("plant",new int[]{10,4},3,10,false);
        BOARD[5][10] = new Resource("plant",new int[]{5,10},3,10,false);
        BOARD[0][11] = new Resource("plant",new int[]{0,11},3,10,false);
        
        /*
            int cords[] = {0,0};
            BOARD[0][0] = new Forest(new int[]{0,0});
            ++cords[1];
            BOARD[0][1] = new Path(new int[]{0,1});
            ++cords[1];
            BOARD[0][2] = new Resource("lake",new int[]{0,2},3,10,true);
            //WATER_SOURCES.addElement((Resource) BOARD[0][2]);
            cords[1] = 0;
            ++cords[0];
            BOARD[1][0] = new Hideout("cave",new int[]{1,0},3);
            //HIDEOUTS.addElement((Hideout) BOARD[1][0]);
            ++cords[1];
            BOARD[1][1] = new Intersection(new int[]{1,1});
            ++cords[1];
            BOARD[1][2] = new Forest(new int[]{1,2});
            cords[1] = 0;
            ++cords[0];
            BOARD[2][0] = new Forest(new int[]{2,0});
            ++cords[1];
            BOARD[2][1] = new Path(new int[]{2,1});
            ++cords[1];
            BOARD[2][2] = new Resource("meat",new int[]{2,2},3,10,false);
            //FOOD_SOURCES.addElement((Resource) BOARD[2][2]);
        */
        for(int y = 0; y<MapSize; y++)
        {
            for(int x = 0; x<MapSize; x++)
            {
                window.add(BOARD[y][x]);
            }
        }
        this.getContentPane().add(window);
        this.setVisible(true);
    }
    public static World GetInstance()
    {
        if(only_instance == null)
            only_instance = new World();
        return only_instance;
    }
    /**
    * returns field which has the given coordinates
     * @param position
     * @return Field
    */
    public Field get_field(int[] position)
    {
        return BOARD[position[0]][position[1]];
    }

    public int getMapSize()
    {
        return MapSize;
    }
    /**
     * finds a random water source
     * @return 
     */
    public Field find_water()
    {
        int index = (new Random()).nextInt(WATER_SOURCES.size());
        return WATER_SOURCES.get(index);
    }
    /**
     * finds a random plant
     * @return 
     */
    public Field find_food()
    {
        int index = (new Random()).nextInt(FOOD_SOURCES.size());
        return FOOD_SOURCES.get(index);
    }
    /**
     * finds a random hideuot
     * @return 
    */ 
    public Field find_hideout()
    {
        int index = (new Random()).nextInt(HIDEOUTS.size());
        return HIDEOUTS.get(index);
    }
    public Field getSelected()
    {
        return selected;
    }
    public void setSelected(Field selected)
    {
        this.selected = selected;
    }
    /**
    * Adds Food source, to the set of food sources.
    */
    public void AddFOODSource(Resource food)
    {
        this.FOOD_SOURCES.addElement(food);
    }
    /**
    * Adds water source, to the set of water sources.
    */
    public void AddWATERSource(Resource water)
    {
        this.WATER_SOURCES.addElement(water);
    }
    /**
    * Adds HIDEOUT, to the set of HIDEOUTS.
    */
    public void AddHIDEOUT(Hideout hideout)
    {
        this.HIDEOUTS.addElement(hideout);
    }
    
}
