package model;

import java.util.List;
import java.util.Random;



import view.Field;
import logic.Location;
import logic.Randomizer;


/**
 * A class representing shared characteristics of plants.
 */
public abstract class Plant implements Actor
{
    // Whether the plant is alive or not.
    private boolean alive;
    // The plant's field.
    private Field field;
    // The plant's position in the field.
    private Location location;
    // An plant's age.
    private int age;
    // An plant's food level, which is increased by eating.
    private int foodLevel;

    // The food value of a single plant. In effect, this is the
    // number of steps an plant can go before it has to eat again.
    public static final int GRASS_FOOD_VALUE = 12;
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a new plant at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Plant(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this plant act - that is: make it do
     * whatever it wants/needs to do.
     * @param newPlants A list to receive newly born plants.
     */
    public abstract void act(List<Actor> newPlants);
    
    /**
     * Check whether the plant is alive or not.
     * @return true if the plant is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the plant is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Increase the age. This could result in the plant's death.
     */
    protected void incrementAge()
    {
        setAge(getAge() + 1);
        if(getAge() > getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * returns the maximum age of an plant type can live
     * @return agemaximum age of an plant type can live
     */
    protected abstract int getMaxAge();
    
    /**
     * Return the plant's location.
     * @return The plant's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the plant at the new location in the given field.
     * @param newLocation The plant's new location.
     */
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the plant's field.
     * @return The plant's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && getRandom().nextDouble() <= getBreedingProbability()) {
            births = getRandom().nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    /**
     * An plant can breed if it has reached the breeding age.
     * @return true if the plant can breed, false otherwise.
     */
    protected abstract boolean canBreed();
    
    /**
     * Returns an plants current age
     * @return age int the current age of an plant
     */
	protected int getAge() 
	{
		return age;
	}

    /**
     * set an plants current age
     * @param age int set the current age
     */
	protected void setAge(int age) 
	{
		this.age = age;
	}

    /**
     * Returns an plants current foodlevel
     * @return foodLevel int current foodlevel
     */
	protected int getFoodLevel() 
	{
		return foodLevel;
	}

    /**
     * set an plants current foodLevel
     * @param foodLevel int set the current foodLevel
     */
	protected void setFoodLevel(int foodLevel) 
	{
		this.foodLevel = foodLevel;
	}

	/**
	 * Getter om Rand op te halen
	 * @return rand random generator number
	 */
	protected Random getRandom()
	{
		return rand;
	}

	/**
	 * getter om infected op te halen
	 * @return infected boolean
	 */

    /**
     * Getter om MAX_LITTER_SIZE op te halen
     * @return MAX_LITTER_SIZE maximum litter
     */
    protected abstract int getMaxLitterSize();
    
    /**
     * Getter om BREEDING_PROBABILITY op te halen
     * @return BREEDING_PROBABILITY breeding kansen
     */
    protected abstract double getBreedingProbability();
}