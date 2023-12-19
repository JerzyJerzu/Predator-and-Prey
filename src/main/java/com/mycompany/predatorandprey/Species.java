/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.predatorandprey;

/**
 * Abstract class used as strategy to set animals statistics  
 * @author User
 */
public abstract class Species {
    public abstract String GetPicture();
    public abstract String GetSpeciesName();
    public abstract int RandHealth();
    public abstract int RandSpeed();
    public abstract int RandStrength();
    public abstract int RandStomachSize();
}
