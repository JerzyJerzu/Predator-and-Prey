/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.predatorandprey;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Species of predator, all methods are used to set stats for predator
 * @author User
 */
public class Tiger extends Species {
        @Override
    public String GetPicture() {
        return "-";
    }
    
    @Override
    public String GetSpeciesName() {
        return "Tiger";
    }

    @Override
    public int RandHealth() {
        return 100;
    }

    @Override
    public int RandSpeed() {
        return ThreadLocalRandom.current().nextInt(30, 45 + 1);
    }

    @Override
    public int RandStrength() {
        return ThreadLocalRandom.current().nextInt(90, 150 + 1);
    }

    @Override
    public int RandStomachSize() {
        return ThreadLocalRandom.current().nextInt(50, 60 + 1);
    }
}
