package view;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.Simulator;

import logic.Counter;
import main.Main;

/**
 * Represents a log with the amounts of animals on each step
 */
public class HistoryView extends JPanel
{
	//	text area
	private JTextArea textArea;
	private int step = -1;
	
	//	hashmap die hoeveelheid per kleur bij houdt
	private HashMap<Color, Counter> stats;
	
	public HistoryView(int height, int width)
	{
		textArea = new JTextArea(height, width);
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
	 * genereert string
	 * @param stats statistics per klasse
	 */
	public void history(boolean isReset)
	{	
        StringBuffer buffer = new StringBuffer();
        String nStep = new String();
        
        nStep =("Step: " + step + "  ");
        for(Color color : stats.keySet()){
            Counter info = stats.get(color);
            int stringLength = info.getName().length();
            buffer.append(info.getName().substring(6,stringLength));	//	show info
            buffer.append(": ");
            buffer.append(info.getCount());
            buffer.append(' ');
        }
        //	tijdelijk blokkeren
        if (!(nStep.equals("Step: " + -1 + "  "))){
        textArea.append(nStep + buffer.toString() + "\r\n");
        
        }
        step++;
	}
	
	/**
	 * getter voor step
	 * @param step
	 */ 
	public void setStep(int step)
	{
		this.step = step;
	}
	
	/**
	 * setter voor textArea
	 * @param textArea
	 */
	public void setTextArea(JTextArea textArea)
	{
		this.textArea = textArea;
	}
	
	/**
	 * getter voor textArea
	 * @return textArea
	 */
	public JTextArea getTextArea()
	{
		return textArea;
	}
}
