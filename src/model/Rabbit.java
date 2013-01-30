package model;

import java.util.List;

import view.Field;

import logic.Location;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static int MAX_LITTER_SIZE = 4;
    
    
    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        setAge(0);
        if(randomAge) {
        	setAge(getRandom().nextInt(MAX_AGE));
        }
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Actor> newRabbits)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newRabbits);            
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
     * returns the maximum age of a rabbit can live
     * @return int maximum age of a rabbit can live
     */
    protected int getMaxAge()
    {
    	return MAX_AGE;
    }
    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    private void giveBirth(List<Actor> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Rabbit young = new Rabbit(false, field, loc);
            newRabbits.add(young);
        }
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }
    
    /**
     * Getter om BREEDING_AGE op te halen
     */
    protected int getBreedingAge()
    {
    	return BREEDING_AGE;
    }
    
    /**
     * Getter om MAX_LITTER_SIZE op te halen
     * @return MAX_LITTER_SIZE maximum litter
     */
    protected int getMaxLitterSize()
    {
    	return MAX_LITTER_SIZE;
    }
    
    /**
     * Getter om BREEDING_PROBABILITY op te halen
     * @return BREEDING_PROBABILITY breeding kansen
     */
    protected double getBreedingProbability()
    {
    	return BREEDING_PROBABILITY;
    }
}
