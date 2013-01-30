package main;

import controller.Simulator;

/**
 * Main class to operate first
 */ 
public class Main
{
    private static Simulator simulator;

    /**
     * Main methode
     */ 
    public static void main(String[] args) {
        setSimulator(new Simulator());
    }

    public static Simulator getSimulator() {
        return simulator;
    }

    public static void setSimulator(Simulator simulator) {
        Main.simulator = simulator;
    }
}