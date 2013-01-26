package model;


import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

import model.*;
import view.*;
import logic.Randomizer;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.02;   
    // The probability that a rabbit will be created in any given grid position.
    private static final double BEAR_CREATION_PROBABILITY = 0.01;
    
    private static final double HUNTER_CREATION_PROBABILITY = 0.001;
    

    // List of actors in the field.
    private List<Actor> actors;
    
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private AnimatedView view;    
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        actors = new ArrayList<Actor>();
        
        field = new Field(depth, width);
        

        // Create a view of the state of each location in the field.
        view = new AnimatedView(depth, width);
        view.setColor(Rabbit.class, Color.ORANGE);
        view.setColor(Fox.class, Color.BLUE);
        view.setColor(Bear.class, Color.MAGENTA);
        view.setColor(Hunter.class, Color.BLACK);
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox, rabbit, bears and hunters.
     */
    public void simulateOneStep()
    {
        step++;
        
        // Provide space for newborn actors.
        List<Animal> newAnimals = new ArrayList<Animal>();     
        
        
        // Let all rabbits act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();           
            if(actor instanceof Animal) {
            	Animal animal = (Animal) actor;
            	animal.act(newAnimals);
	            if(! animal.isAlive()) {
	                it.remove();
	            }
            }
            else if(actor instanceof Hunter) {
            	Hunter hunter = (Hunter) actor;
            	hunter.act();
            }
        }
        
 
        

        // Add the newly born foxes, rabbits, bears and hunters to the main lists.
        actors.addAll(newAnimals);
        

        view.showStatus(step, field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        
        
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Randomly populate the field with foxes, rabbits, bears and hunters.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        
       
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, field, location);
                    actors.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, field, location);
                    actors.add(rabbit);
                }
                else if(rand.nextDouble() <= BEAR_CREATION_PROBABILITY) {
                	Location location = new Location(row, col);
                	Bear bear = new Bear(true, field, location);
                	actors.add(bear);
                }
                else if(rand.nextDouble() <= HUNTER_CREATION_PROBABILITY) {
                	Location location = new Location(row, col);
                	Hunter hunter = new Hunter(field, location);
                	actors.add(hunter);
                }
                
       
                // else leave the location empty.
            }

        }
    }
}
