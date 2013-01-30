package model;
import java.util.List;

import logic.Location;

/**
 * A class representing shared characteristics of living creature.
 */
public interface Actor 
{
    /**
     * Make this actor act - that is: make it do
     * whatever it wants/needs to do.
     * @param newActors A list to receive newly born animals.
     */
	void act(List<Actor> newActors);
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
	void setLocation(Location newLocation);
}
