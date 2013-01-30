package model;

import java.util.List;
import java.util.Iterator;

import view.Field;
//import java.util.Random;

import logic.Location;


/**
 * 
 * A class representing shared characteristics of hunters.
 * A simple model of a hunter
 * Hunters move and shoot.
 */
public class Hunter implements Actor
{
    // The hunter's field.
    private Field field;
    // The hunter's position in the field.
    private Location location;
    
    /**
     * Create a hunter. 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(Field field, Location location)
    {
        this.field = field;
        this.location = location;
    }

    /**
     * This is what the bear does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newbears A list to return newly born bears.
     */
    
    public void act(List<Actor> newHunters)
    {
    	// Move towards a source of food if found.
        Location newLocation = findTarget();
        if(newLocation == null) { 
            // No food found - try to move to a free location.
            newLocation = getField().freeAdjacentLocation(getLocation());
        }
        // See if it was possible to move.
        if(newLocation != null) {
            setLocation(newLocation);
        }

    }


    /**
     * Look for an animal adjacent to the current location.
     * Only the first live animal is shoot.
     * @return Where an animal is found, or null if it wasn't.
     */
    private Location findTarget()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) 
            {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) 
                { 
                    rabbit.setDead();
                    return where;
                }
            }
            else if (animal instanceof Fox)
            {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) 
                { 
                    fox.setDead();
                    return where;
                }
            	
            }
            else if (animal instanceof Bear)
            {
                Bear bear = (Bear) animal;
                if(bear.isAlive()) 
                { 
                    bear.setDead();
                    return where;
                }
            	
            }
        }
        return null;
    }
    
    /**
     * Return the hunter's location.
     * @return The hunter's location.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the hunter at the new location in the given field.
     * @param newLocation The hunter's new location.
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
     * Return the hunter's field.
     * @return Field the hunter's field.
     */
    public Field getField()
    {
        return field;
    }   
}