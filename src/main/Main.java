package main;

import view.SplashScreen;
import view.Sound;
import controller.Simulator;

/**
 * Main class to operate first
 */ 
public class Main
{
    private static Simulator simulator;

    /**
     * Main methode
     * @throws Exception 
     */ 
    public static void main(String[] args) throws Exception {
    	
        // Throw a nice little title page up on the screen first
        SplashScreen splash = new SplashScreen(3000);
        Sound startupSound = new Sound("sounds/test.wav");

        // Normally, we'd call splash.showSplash() and get on 
        // with the program. But, since this is only a test...
        splash.showSplash();
    	
        setSimulator(new Simulator());
    }

    public static Simulator getSimulator() {
        return simulator;
    }

    public static void setSimulator(Simulator simulator) {
        Main.simulator = simulator;
    }
}