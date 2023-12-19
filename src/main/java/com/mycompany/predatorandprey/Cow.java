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
public class Cow extends Species {
    
    @Override
    public String GetPicture() {
        return "-";
    }
    
    @Override
    public String GetSpeciesName() {
        return "Cow";
    }

    @Override
    public int RandHealth() {
        return ThreadLocalRandom.current().nextInt(90, 150 + 1);
    }

    @Override
    public int RandSpeed() {
        return ThreadLocalRandom.current().nextInt(3, 8 + 1);
    }

    @Override
    public int RandStrength() {
        return ThreadLocalRandom.current().nextInt(20, 40 + 1);
    }

    @Override
    public int RandStomachSize() {
        return ThreadLocalRandom.current().nextInt(60, 90 + 1);
    }
    
}
