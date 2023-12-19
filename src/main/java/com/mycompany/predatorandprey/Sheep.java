/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.predatorandprey;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Species of prey, all methods are used to set stats for prey
 * @author User
 */
public class Sheep extends Species {
    
    @Override
    public String GetPicture() {
        return "-";
    }
    
    @Override
    public String GetSpeciesName() {
        return "Sheep";
    }

    @Override
    public int RandHealth() {
        return ThreadLocalRandom.current().nextInt(80, 100 + 1);
    }

    @Override
    public int RandSpeed() {
        return ThreadLocalRandom.current().nextInt(10, 15 + 1);
    }

    @Override
    public int RandStrength() {
        return ThreadLocalRandom.current().nextInt(10, 20 + 1);
    }

    @Override
    public int RandStomachSize() {
        return ThreadLocalRandom.current().nextInt(40, 60 + 1);
    }
    
}
