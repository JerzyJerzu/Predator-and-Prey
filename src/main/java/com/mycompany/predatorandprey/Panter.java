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
public class Panter extends Species {
    @Override
    public String GetPicture() {
        return "-";
    }
    
    @Override
    public String GetSpeciesName() {
        return "Panter";
    }

    @Override
    public int RandHealth() {
        return 100;
    }

    @Override
    public int RandSpeed() {
        return ThreadLocalRandom.current().nextInt(40, 50 + 1);
    }

    @Override
    public int RandStrength() {
        return ThreadLocalRandom.current().nextInt(70, 120 + 1);
    }

    @Override
    public int RandStomachSize() {
        return ThreadLocalRandom.current().nextInt(40, 50 + 1);
    }
}
