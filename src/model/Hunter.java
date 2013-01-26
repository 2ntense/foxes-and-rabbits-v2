package model;

import java.util.List;
import java.util.Iterator;

/**
 * A simple model of an hunter.
 * Hunters moves and kills all animals.
 * 
 * @author Mike Por, Alexander Postma, Stefan Yip
 * @version 25.1.2013
 */
public class Hunter implements Actor
{
    // Whether the hunter is alive or not.
    //private boolean alive;
    // The hunter's field.
    private Field field;
    // The hunter's position in the field.
    private Location location;
	
	/**
     * Create an hunter. A hunter can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hunter(Field field, Location location)
    {
    	this.field = field;
    	setLocation(location);
    }
    
    /**
     * This is what the hunter does most of the time: it hunts for
     * animals.
     * @param field The field currently occupied.
     * @param newHunters A list to return newly created hunters.
     */
    public void act()
    {
      
            // Move towards an animal if found.
            Location newLocation = findTarget();
            if(newLocation == null) { 
                // No animal found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
        
    }

 
    /**
     * Look for animals adjacent to the current location.
     * Only the first live animal will be killed.
     * @return Where animal was found, or null if it wasn't.
     */
    private Location findTarget()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    return where;
                }
            }
            if(animal instanceof Fox) {
                Fox fox = (Fox) animal;
                if(fox.isAlive()) { 
                    fox.setDead();
                    return where;
                }
            }
            if(animal instanceof Bear) {
                Bear bear = (Bear) animal;
                if(bear.isAlive()) { 
                    bear.setDead();
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Returns the hunter's field.
     * @return the hunter's field
     */
    public Field getField() {
    	return field;
    }
    
    /**
     * Returns the hunter's location.
     * @return the hunter's location.
     */
    public Location getLocation() {
    	return location;    	
    }
    
    /**
     * Set a new location for hunter.
     * @param newLocation, the hunter's new location.
     */
    public void setLocation(Location newLocation) {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
}


