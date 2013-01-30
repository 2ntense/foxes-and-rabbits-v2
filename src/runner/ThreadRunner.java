package runner;

import controller.Simulator;
import main.Main;
/**
 * Allow the animation to be shown.
 */
public class ThreadRunner implements Runnable
{	
	
	private int numSteps = 0;
	private boolean infinite = false;
	private boolean threadRun;
	
	
	public ThreadRunner() 
	{
	
	}
	
    /**
     * Run the simulation from its current state for a reasonably long period
     */
    public void startRun(int numSteps)
    {
    	if (numSteps == 0)
    	{
    		this.numSteps = 1;
    		infinite = true;
    	}
    	else
    	{
//    		this.numSteps = numSteps;	//	voor niet stack gevallen
    		this.numSteps += numSteps;	//	voor als het wel moet stacken
    	}
    	
    	try{
    		if (!threadRun && Thread.currentThread().isAlive())
    		{
    			new Thread(this).start();
    		}
    	} 
    	catch (IllegalThreadStateException e)
    	{
    		infinite = false;
        	System.out.println("InterruptedException");
    	}
	}

    /**
     * Pauze de animatie
     */
	public void stop() 
	{
		numSteps = 0;
		threadRun = false;
		infinite = false;
	}
	
	/**
	 * Deze methode wordt alleen uitgevoerd als je de methode .start() gebruikt van de klasse Thread.
	 * Zonder de klasse (thread), wordt deze methode niet juist uitgevoerd.
	 * De methode zorgt ervoor dat de thread door het aantal numSteps heen loopt.
	 */
	@Override
	public void run() 
	{
		threadRun = true;
		Simulator simulator = Main.getSimulator();
		
		while(threadRun && numSteps > 0 && simulator.getSimulatorView().isViable(simulator.getField()))
		{
			Main.getSimulator().simulateOneStep();
			numSteps--;
			while(infinite && numSteps == 0)
			{
				numSteps++;
			}
			
			try {
				Thread.sleep(0);
			} 
			catch (Exception e) 
			{
            	System.out.println("InterruptedException");
			}
		}
		threadRun = false;
	}
}
