/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.predatorandprey;

/**
 * main class
 * @author User
 */
public class PredatorAndPrey {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello World!");
        World TheWorld = World.GetInstance();
        TheWorld.InitializeBoard();
        ControlPanel TheControlPanel = new ControlPanel();
    }
}
