package view;

import javax.swing.*;

public class TextView extends JFrame implements SimulatorView {
	
	
	
	public TextView(int height, int width) {
		
		JEditorPane textArea = new JEditorPane();
		JScrollPane scrollPane = new JScrollPane(textArea); 
		textArea.setEditable(false);
		
		
	}
	
}