package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JPanel;

import logic.Counter;

/**
 * maak een histogram aan
 */
public class Histogram extends JPanel
{
	//	hashmap die hoeveelheid per kleur bij houdt
	private HashMap<Color, Counter> stats;
	//	hoogte van de histogram
	private int height;
	//	breedte van de histogram
	private int width;
	//	afstand tussen ieder balk
	private final int SPACE = 40;
	
	/**
	 * leeg constructor
	 */
	public Histogram()
	{
		
	}
	
	/**
	 * stats wordt toegewezen
	 * @param stats populate stats
	 */
	public void stats(HashMap<Color, Counter> stats)
	{
		this.stats = stats;
	}
	
	/**
	 * bepaald de frame grote van de histogram
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	/**
	 * maak de histogram
	 * @param g Graphic component
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//	hoogte van de blok
		int height;
		//	breedte van de blok
		int width;
		
		if (!stats.isEmpty())
		{
			width = this.width / stats.size();
		}
		else
		{
			width = 4;
		}
		
		//	blok counter
		int blok = 0;
		
		//	teken de blok
		for (Color color : stats.keySet())
		{
			//	bepaald de hoogte van de blok;
			height = stats.get(color).getCount() / 5;
			
			//	kleurt de blok
			g.setColor(color);
			g.fillRect(width * blok + SPACE, this.height - height - SPACE, width - 1, height);
			
			//	paint de outline van de blok
			g.setColor(Color.BLACK);
			g.drawRect(width * blok + SPACE, this.height - height - SPACE, width - 1, height);
			
			blok++;
		}
		
//		//	maak assen aan.
//		//	teken de x as
//		g.drawLine(SPACE, this.height - SPACE, this.width, this.height - SPACE);
//		//	teken de y as
//		g.drawLine(SPACE, 0, SPACE, this.height -SPACE);
		
	}
}