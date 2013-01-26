package view;

import model.Simulator;


import javax.swing.JPanel;

public abstract class AbstractView extends JPanel {
	private static final long serialVersionUID = 6437976554496769048L;
	protected Simulator simulator;

	public AbstractView(Simulator simulator) {
		this.simulator = simulator;
		simulator.addView(this);
	}
	
	public Simulator getSimulator() {
		return simulator;
	}
	
	public void updateView() {
		repaint();
	}
}
