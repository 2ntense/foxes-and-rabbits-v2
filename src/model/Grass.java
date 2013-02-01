package model;

import java.util.List;



import view.Field;
import logic.Location;

/**
 * A simple model of a grass.
 * Grass age, breed, and die.
 */
public class Grass extends Plant
{
    // Characteristics shared by all grass (class variables).

    // The age at which a grass can start to breed.
    private static int breeding_age = 3;
    // The age to which a grass can live.
    private static int max_age = 12;
    // The likelihood of a grass breeding.
    private static double breeding_probability = 0.5;
    // The maximum number of births.
    private static int max_litter_size = 6;
    

    
    /**
     * Create a new grass. A grass may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the grass will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Grass(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        setAge(0);
        if(randomAge) {
        	setAge(getRandom().nextInt(max_age));
        }
    }
    
    /**
     * This is what the grass does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newGrasss A list to return newly born grass.
     */
    public void act(List<Actor> newGrass)
    {
        incrementAge();
        if(isAlive()) {

        	
        	giveBirth(newGrass);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Check whether or not this grass is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newGrasss A list to return newly born grass.
     */
    private void giveBirth(List<Actor> newGrass)
    {
        // New grass are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Grass young = new Grass(false, field, loc);
            newGrass.add(young);
        }
    }

    /**
     * A grass can breed if it has reached the breeding age.
     * @return true if the grass can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }

    /**
     * setter voor breeding_age
     * @param breeding_age
     */
    public static void setBreedingAge(int breeding_age)
    {
    	if (breeding_age >= 0)
    		Grass.breeding_age = breeding_age;
    }
    
    
    /**
     * setter voor max_age
     * @param max_age
     */
    public static void setMaxAge(int max_age)
    {
    	if (max_age >= 1)
    		Grass.max_age = max_age;
    }
    
    /**
     * setter voor breeding_probability
     * @param breeding_probability
     */
    public static void setBreedingProbability(double breeding_probability)
    {
    	if (breeding_probability >= 0)
    		Grass.breeding_probability = breeding_probability;
    }
    
    /**
     * setter voor max_litter_size
     * @param max_litter_size
     */
    public static void setMaxLitterSize(int max_litter_size)
    {
    	if (max_litter_size >= 1)
    		Grass.max_litter_size = max_litter_size;
    }  
    
    /**
     * default settings
     */
    public static void setDefault()
    {
    	breeding_age = 1;
    	max_age = 100;
    	breeding_probability = 0.02;
    	max_litter_size = 100;
    }
    
    /**
     * Getter om breeding_age op te halen
     */
    protected int getBreedingAge()
    {
    	return breeding_age;
    }
    
    /**
     * returns the maximum age of a grass can live
     * @return int maximum age of a grass can live
     */
    protected int getMaxAge()
    {
    	return max_age;
    }
    
    /**
     * Getter om max_litter_size op te halen
     * @return max_litter_size maximum litter
     */
    protected int getMaxLitterSize()
    {
    	return max_litter_size;
    }
    
    /**
     * Getter om breeding_probability op te halen
     * @return breeding_probability breeding kansen
     */
    protected double getBreedingProbability()
    {
    	return breeding_probability;
    }	
}