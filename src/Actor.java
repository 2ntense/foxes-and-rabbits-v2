import java.util.List;

public interface Actor
{
	public void act(List<Actor> newHunters);
	void setLocation(Location newLocation);
}
