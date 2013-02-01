package controller;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

import view.Field;
import view.SimulatorView;

import logic.Location;
import logic.Randomizer;
import model.Actor;
import model.Animal;
import model.Bear;
import model.Fox;
import model.Grass;
import model.Hunter;
import model.Plant;
import model.Rabbit;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a bear will be created in any given grid position.    
    private static double BEAR_CREATION_PROBABILITY = 0.01;
    // The probability that a fox will be created in any given grid position.
    private static double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static double RABBIT_CREATION_PROBABILITY = 0.08;    
    // The probability that a hunter will be created in any given grid position.
    private static double HUNTER_CREATION_PROBABILITY = 0.002;
    // The probability that grass will be created in any given grid position.
    private static double GRASS_CREATION_PROBABILITY = 1;
    
    // List of Actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step = 0;
    // A graphical view of the simulation.
    private SimulatorView view;

    
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
        view = new SimulatorView(depth, width);
        view.setColor(Rabbit.class, Color.ORANGE);
        view.setColor(Fox.class, Color.BLUE);
        view.setColor(Bear.class, Color.MAGENTA);
        view.setColor(Hunter.class, Color.BLACK);
        view.setColor(Grass.class, Color.GREEN);
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        step++;

        // Provide space for newborn actors.
        List<Actor> newActors = new ArrayList<Actor>();
        
				    // Let all actors act.
				    for(Iterator<Actor> it = actors.iterator(); it.hasNext();) {
				        Actor actor = it.next();
				        actor.act(newActors);
				        if (actor instanceof Animal)		//	check if actor is an animal
				        {
				        	Animal animal = (Animal) actor;
				        	if(! animal.isAlive()) {
				                it.remove();
				            }
				        }
				        else if (actor instanceof Plant)
				        {
				        	Plant plant = (Plant) actor;
				        	if(! plant.isAlive()) {
				                it.remove();
				            }
				        }
				    }
				    
        // Add the newly born foxes and rabbits to the main lists.
        actors.addAll(newActors);

        view.showStatus(step, field);
    }
    
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
		view.getThreadRunner().stop();			
        step = 0;
        actors.clear();
        populate();
        
        // Show the starting state in the view.
        view.showStatus(step, field);
    }
    
    /**
     * Getter voor view
     * @return view van het type SimulatorView
     */
    public SimulatorView getSimulatorView()
    {
    	return view;
    }
    
    /**
     * Getter voor field
     * @return field van het type Field
     */
    public Field getField()
    {
    	return field;
    }
    
    /**
     * getter voor step
     * @return steps aantal step
     */
    public int getStep()
    {
    	return step;
    }
    
    
    /**
     * Randomly populate the field with foxes and rabbits.
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
                else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(true, field, location);
                    actors.add(grass);
                }
                // else leave the location empty.
            }
        }
    }
    
    /**
     * setter voor BEAR_CREATION_PROBABILITY
     * @param BEAR_CREATION_PROBABILITY
     */
    public static void setBearCreationProbability(double BEAR_CREATION_PROBABILITY)
    {
    	if (BEAR_CREATION_PROBABILITY >= 0)
    		Simulator.BEAR_CREATION_PROBABILITY = BEAR_CREATION_PROBABILITY;
    }
    
    /**
     * setter voor FOX_CREATION_PROBABILITY
     * @param FOX_CREATION_PROBABILITY
     */
    public static void setFoxCreationProbability(double FOX_CREATION_PROBABILITY)
    {
    	if (FOX_CREATION_PROBABILITY >= 0)
    		Simulator.FOX_CREATION_PROBABILITY = FOX_CREATION_PROBABILITY;
    }
    
    /**
     * setter voor RABBIT_CREATION_PROBABILITY
     * @param RABBIT_CREATION_PROBABILITY
     */
    public static void setRabbitCreationProbability(double RABBIT_CREATION_PROBABILITY)
    {
    	if (RABBIT_CREATION_PROBABILITY >= 0)
    		Simulator.RABBIT_CREATION_PROBABILITY = RABBIT_CREATION_PROBABILITY;
    }
    
    /**
     * setter voor HUNTER_CREATION_PROBABILITY
     * @param HUNTER_CREATION_PROBABILITY
     */
    public static void setHunterCreationProbability(double HUNTER_CREATION_PROBABILITY)
    {
    	if (HUNTER_CREATION_PROBABILITY >= 0)
    		Simulator.HUNTER_CREATION_PROBABILITY = HUNTER_CREATION_PROBABILITY;
    }
    
    /**
     * setter voor GRASS_CREATION_PROBABILITY
     * @param GRASS_CREATION_PROBABILITY
     */
    public static void setGrassCreationProbability(double GRASS_CREATION_PROBABILITY)
    {
    	if (GRASS_CREATION_PROBABILITY >= 0)
    		Simulator.GRASS_CREATION_PROBABILITY = GRASS_CREATION_PROBABILITY;
    }    
}