package main;

import view.SplashScreen;
import view.Sound;
import controller.Simulator;

/**
 * Main class to operate first
 * 
 * @author Mike Por, Alexander Postma, Stefan YIp
 * @version 1.0
 */ 
public class Main
{
    private static Simulator simulator;

    /**
     * Main methode
     * @throws Exception 
     */ 
    public static void main(String[] args) throws Exception {
    	       
        SplashScreen splash = new SplashScreen(3000);
        Sound startupSound = new Sound("sounds/test.wav");
        splash.showSplash();
    	
        setSimulator(new Simulator());
    }

    /**
     * returns the simulator
     * @return simulator
     */
    public static Simulator getSimulator() {
        return simulator;
    }

    
    /**
     * sets a new simulator
     * @param simulator
     */
    public static void setSimulator(Simulator simulator) {
        Main.simulator = simulator;
    }
}