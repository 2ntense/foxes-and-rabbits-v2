package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

import controller.Simulator;

import runner.ThreadRunner;

import logic.Counter;
import main.Main;
import model.Rabbit;

import logic.Counter;
import view.Field;
import view.FieldStats;
import main.Main;
import controller.Simulator;
import model.Bear;
import model.Fox;
import model.Rabbit;
import view.Sound;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Toolkit;

/**
 * A graphical view of the simulation grid. The view displays a colored
 * rectangle for each location representing its contents. It uses a default
 * background color. Colors for each type of species can be defined using the
 * setColor method.
 */
public class SimulatorView extends JFrame {
	// Colors used for empty locations.
	private static final Color EMPTY_COLOR = Color.white;

	// Color used for objects that have no defined color.
	private static final Color UNKNOWN_COLOR = Color.gray;

	private final String STEP_PREFIX = "Step: ";
	private final String POPULATION_PREFIX = "Population: ";
	private JLabel stepLabel, population;
	
	private FieldView fieldView;
	private PieChart pieChart;
	private Histogram histogram;
	private HistoryView historyView;
	
	private Rabbit rabbit;
	
	private ThreadRunner threadRunner;
	
	// A map for storing colors for participants in the simulation
	private Map<Class, Color> colors;
	
	// A statistics object computing and storing simulation information
	private FieldStats stats;
	private boolean isReset;
	private static final String VERSION = "versie 0.5";
	private int viewsToDisplay = 5;

	/**
	 * Create a view of the given width and height.
	 * 
	 * @param height, the simulation's height.
	 * @param width, the simulation's width.
	 */
	public SimulatorView(int height, int width) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		threadRunner = new ThreadRunner();
		stats = new FieldStats();
		colors = new LinkedHashMap<Class, Color>();

		setTitle("Fox and Rabbit Simulation");
		stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
		population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);

		fieldView = new FieldView(height, width);

		makePieChart(height, width);
		makeHistogram(height, width);
		makeHistoryView(height, width);
		makeMainFrame();
		makeMenuBar();
	}

	/**
	 * Maak main frame aan en al zijn componenten Main frame is de onderste
	 * laag, daarna komt tweede laag view panel (rechterkant) en toolbar panel
	 * (linkerkant). Aan de toolbar panel worden knoppen toegevoegd en aan de
	 * view panel worden meerdere views toegevoegd(piecharts etc.)
	 */
	public void makeMainFrame() {
		// maak main frame (onderste laag) aan, layout en border van main frame.
		JPanel mainFrame = new JPanel();
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.add(mainFrame);
			
		// maak view panel (tweede laag) aan, layout en border van view panel.
		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new GridLayout(2, 2));
		viewPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainFrame.add(viewPanel, BorderLayout.CENTER);

		// maak field panel (bovenste laag) aan, en border van field panel.
		JPanel field = new JPanel();
		field.setLayout(new BorderLayout());
		field.add(stepLabel, BorderLayout.NORTH);
		field.add(fieldView, BorderLayout.CENTER);
		field.add(population, BorderLayout.SOUTH);
		viewPanel.add(field); // field panel toevoegen aan view panel.

		// pieChart panel
		JPanel chart = new JPanel();
		chart.setLayout(new BorderLayout());
		chart.add(pieChart, BorderLayout.CENTER);
		viewPanel.add(chart); // chart panel toevoegen aan view panel

		// histogram panel
		JPanel diagram = new JPanel();
		diagram.setLayout(new BorderLayout());
		diagram.add(histogram, BorderLayout.CENTER);
		viewPanel.add(diagram); // diagram panel toevoegen aan view panel

		// textArea panel
		JTextArea textArea = new JTextArea(20, 20);
		historyView.setTextArea(textArea);
		textArea.setEditable(false);
		
		// scrollPane voor textArea
		JScrollPane scrollPane = new JScrollPane(textArea);		
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		viewPanel.add(scrollPane);

		// maak toolbar panel (tweede laag, linkerkant) aan, layout en border
		// van toolbar panel
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new GridLayout(20, 0)); 
		toolbar.setBorder(new EmptyBorder(20, 10, 20, 10));

		// labels en knoppen
		// step 1 knop
		JButton step1 = new JButton("Step 1");
		step1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {						
				threadRunner.startRun(1);
				try {
					Sound buttonPress = new Sound("sounds/button.wav");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		toolbar.add(step1); // toevoegen aan toolbar panel

		// step 100 knop
		JButton step100 = new JButton("Step 100");
		step100.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				threadRunner.startRun(100);
				try {
					Sound buttonPress = new Sound("sounds/button.wav");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		toolbar.add(step100);

		// start knop
		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				threadRunner.startRun(0);
				try {
					Sound buttonPress = new Sound("sounds/button.wav");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		toolbar.add(start); // toevoegen aan toolbar panel

		// stop knop
		JButton stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				threadRunner.stop();
				try {
					Sound buttonPress = new Sound("sounds/button.wav");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		toolbar.add(stop); // toevoegen aan toolbar panel

		// reset knop
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				historyView.setStep(0);
				Main.getSimulator().reset();
				try {
					Sound buttonPress = new Sound("sounds/button.wav");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		toolbar.add(reset); // toevoegen aan toolbar panel
		mainFrame.add(toolbar, BorderLayout.WEST); // toolbar panel toevoegen aan de main frame
		
		// maak een balk met versie nummer onderaan de frame en voeg toe aan de
		// main frame
		JLabel statusLabel = new JLabel(VERSION);
		mainFrame.add(statusLabel, BorderLayout.SOUTH);

		pack();
		//setSize(new Dimension(1024, 768));
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		setExtendedState(Frame.MAXIMIZED_BOTH);

	}
	
	/**
	 * Maak de hoofdmenu aan
	 */
	private void makeMenuBar() {
		// shorcuts kits
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

		// maak een menubalk aan.
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// maak de menu en menu items aan
		JMenu menu;
		JMenuItem item;

		menu = new JMenu("File"); // maak menu file aan
		menuBar.add(menu); // voeg toe aan de menu balk

		// maak menu item settings aan en ActionListener
		item = new JMenuItem("Settings");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeSettings();
			}
		});
		menu.add(item); // toevoegen aan de menu
		menu.addSeparator();

		// maak menu item quit aan
		item = new JMenuItem("Quit");
		
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});
		menu.add(item); // toevoegen aan de menu								
		
		// maak help menu aan
		menu = new JMenu("Help");
		menuBar.add(menu); // voeg toe aan de menu balk
		
		// maak menu item settings aan en ActionListener
		item = new JMenuItem("Help");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeHelp();
			}
		});
		menu.add(item); // toevoegen aan de menu
		menu.addSeparator();
		// maak menu item settings aan en ActionListener
		item = new JMenuItem("About");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeAbout();
			}
		});
		menu.add(item); // toevoegen aan de menu						
	}

	
	private void makeHelp() {
		JFrame helpFrame = new JFrame("Help");
		JPanel helpPanel = new JPanel();
		JTextArea helpTextArea = new JTextArea("TEST", 12, 20);
		helpFrame.add(helpPanel);
		helpPanel.add(helpTextArea);
		helpTextArea.setEditable(false);
		helpFrame.setSize(new Dimension(350, 240));
		helpFrame.setVisible(true);
		helpFrame.setResizable(false);
		helpFrame.setLocationRelativeTo(null);		

	}
	
	private void makeAbout() {		
		  JFrame aboutFrame = new JFrame("About");
		  JPanel aboutPanel = new JPanel();
		  JTextArea aboutTextArea = new JTextArea("               Vossen en Konijnen 2013 \n \n \n \n \n \n \n \n \n \n \n Mike Por, Alexander Postma en Stefan Yip", 12, 20);
		  aboutFrame.add(aboutPanel);
		  aboutPanel.add(aboutTextArea);
		  aboutTextArea.setEditable(false);
		  aboutFrame.setSize(350, 240);
		  aboutFrame.setVisible(true);
		  aboutFrame.setResizable(false);
		  aboutFrame.setLocationRelativeTo(null);
	}		
	
	
	/**
	 * maak een pop up settings window, wordt opgeroepen wanneer menu item
	 * settings wordt gebruikt. Settings wordt gebruikt om gegevens van een
	 * actoren te kunnen wijzigen
	 */
	private void makeSettings() {
		// maak settings frame aan
		JFrame settingsFrame = new JFrame();
		settingsFrame.setTitle("Settings");
		
		// maak settings frame's main tab aan, size, layout en border van
		// settings panel
		JTabbedPane mainTab = new JTabbedPane();
		mainTab.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainTab.setPreferredSize(new Dimension(100, 100));

		
		/*
		 * GENERAL TAB
		 */
		// maak general tab aan, layout en border
		JPanel generalTab = new JPanel();
		generalTab.setLayout(new GridLayout(8, 0));
		generalTab.setBorder(new EmptyBorder(10, 10, 10, 10));

		// voeg labels, tekstvelden en Actionlistener toe aan general tab
		generalTab.add(new JLabel("TEST"));
		final JTextField test = new JTextField();
		generalTab.add(test);
		
		// change setting button
		JButton change = new JButton("Apply");
		change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Simulator.setAnimationSpeed(stringToInt(animationSpeed));
			}
		});		
		generalTab.add(change);

		/*
		 *	RABBITS TAB
		 */
		// maak rabbits tab aan, layout en border
		JPanel rabbitTab = new JPanel();
		rabbitTab.setLayout(new GridLayout(8, 0));
		rabbitTab.setBorder(new EmptyBorder(10, 10, 10, 10));

		// voeg labels, tekstvelden en ActionListener toe aan rabbit tab
		rabbitTab.add(new JLabel("Breeding Age:"));
		final JTextField rabbitBreedingAge = new JTextField("5");
		rabbitTab.add(rabbitBreedingAge);

		rabbitTab.add(new JLabel("Max Age:"));
		final JTextField rabbitMaxAge = new JTextField("40");
		rabbitTab.add(rabbitMaxAge);

		rabbitTab.add(new JLabel("Breeding Probability:"));
		final JTextField rabbitBreedingProbability = new JTextField("0.12");
		rabbitTab.add(rabbitBreedingProbability);

		rabbitTab.add(new JLabel("Max Litter Size:"));
		final JTextField rabbitMaxLitterSize = new JTextField("4");
		rabbitTab.add(rabbitMaxLitterSize);

		// apply rabbits changes button
		JButton rabbitApply = new JButton("Apply");
		rabbitApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// get text van de text fields
				String stringBREEDING_AGE = rabbitBreedingAge.getText();
				String stringMAX_AGE = rabbitMaxAge.getText();
				String stringBREEDING_PROBABILITY = rabbitBreedingProbability.getText();
				String stringMAX_LITTER_SIZE = rabbitMaxLitterSize.getText();
								
				// converteer de strings naar ints en double
				int intBREEDING_AGE = Integer.parseInt(stringBREEDING_AGE);
				int intMAX_AGE = Integer.parseInt(stringMAX_AGE);
				double doubleBREEDING_PROBABILITY = Double.parseDouble(stringBREEDING_PROBABILITY);
				int intMAX_LITTER_SIZE = Integer.parseInt(stringMAX_LITTER_SIZE);
				
				// set de nieuwe waardes
				Rabbit.setBreedingAge(intBREEDING_AGE);
				Rabbit.setMaxAge(intMAX_AGE);
				Rabbit.setBreedingProbability(doubleBREEDING_PROBABILITY);
				Rabbit.setMaxLitterSize(intMAX_LITTER_SIZE);		
				
				System.out.println("Applied!");
				
			}
		});		
		rabbitTab.add(rabbitApply);
		
		
		
		
		/*
		 * FOXES TAB
		 */
		// maak rabbits tab aan, layout en border
		JPanel  foxTab = new JPanel();
		foxTab.setLayout(new GridLayout(8, 0));
		foxTab.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		foxTab.add(new JLabel("Breeding Age:"));
		final JTextField foxBreedingAge = new JTextField("15");
		foxTab.add(foxBreedingAge);
		
		foxTab.add(new JLabel("Max Age:"));
		final JTextField foxMaxAge = new JTextField("150");
		foxTab.add(foxMaxAge);
		
		foxTab.add(new JLabel("Breeding Probability:"));
		final JTextField foxBreedingProbability = new JTextField("0.08");
		foxTab.add(foxBreedingProbability);
		
		foxTab.add(new JLabel("Max Litter Size:"));
		final JTextField foxMaxLitterSize = new JTextField("4");
		foxTab.add(foxMaxLitterSize);
		
		JButton foxApply = new JButton("Apply");
		foxApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// get text van de text fields
				String stringBREEDING_AGE = foxBreedingAge.getText();
				String stringMAX_AGE = foxMaxAge.getText();
				String stringBREEDING_PROBABILITY = foxBreedingProbability.getText();
				String stringMAX_LITTER_SIZE = foxMaxLitterSize.getText();
								
				// converteer de strings naar ints en double
				int intBREEDING_AGE = Integer.parseInt(stringBREEDING_AGE);
				int intMAX_AGE = Integer.parseInt(stringMAX_AGE);
				double doubleBREEDING_PROBABILITY = Double.parseDouble(stringBREEDING_PROBABILITY);
				int intMAX_LITTER_SIZE = Integer.parseInt(stringMAX_LITTER_SIZE);
				
				// set de nieuwe waardes
				Fox.setBreedingAge(intBREEDING_AGE);
				Fox.setMaxAge(intMAX_AGE);
				Fox.setBreedingProbability(doubleBREEDING_PROBABILITY);
				Fox.setMaxLitterSize(intMAX_LITTER_SIZE);
				
				System.out.println("Applied!");
				
			}
		});		
		foxTab.add(foxApply);
		
		/*
		 * BEAR TAB
		 */
		// maak bear tab aan, layout en border
		JPanel  bearTab = new JPanel();
		bearTab.setLayout(new GridLayout(8, 0));
		bearTab.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		bearTab.add(new JLabel("Breeding Age:"));
		final JTextField bearBreedingAge = new JTextField("30");
		bearTab.add(bearBreedingAge);
		
		bearTab.add(new JLabel("Max Age:"));
		final JTextField bearMaxAge = new JTextField("190");
		bearTab.add(bearMaxAge);
		
		bearTab.add(new JLabel("Breeding Probability:"));
		final JTextField bearBreedingProbability = new JTextField("0.02");
		bearTab.add(bearBreedingProbability);
		
		bearTab.add(new JLabel("Max Litter Size:"));
		final JTextField bearMaxLitterSize = new JTextField("1");
		bearTab.add(bearMaxLitterSize);
		
		JButton bearApply = new JButton("Apply");
		bearApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// get text van de text fields
				String stringBREEDING_AGE = bearBreedingAge.getText();
				String stringMAX_AGE = bearMaxAge.getText();
				String stringBREEDING_PROBABILITY = bearBreedingProbability.getText();
				String stringMAX_LITTER_SIZE = bearMaxLitterSize.getText();
								
				// converteer de strings naar ints en double
				int intBREEDING_AGE = Integer.parseInt(stringBREEDING_AGE);
				int intMAX_AGE = Integer.parseInt(stringMAX_AGE);
				double doubleBREEDING_PROBABILITY = Double.parseDouble(stringBREEDING_PROBABILITY);
				int intMAX_LITTER_SIZE = Integer.parseInt(stringMAX_LITTER_SIZE);
				
				// set de nieuwe waardes
				Bear.setBreedingAge(intBREEDING_AGE);
				Bear.setMaxAge(intMAX_AGE);
				Bear.setBreedingProbability(doubleBREEDING_PROBABILITY);
				Bear.setMaxLitterSize(intMAX_LITTER_SIZE);
				
				System.out.println("Applied!");
				
			}
		});		
		bearTab.add(bearApply);							
		
		// alle tabs toevoegen aan maintab
		// main tab toevoegen aan de settings frame
		mainTab.addTab("General", generalTab);
		mainTab.addTab("Rabbit", rabbitTab);
		mainTab.addTab("Fox", foxTab);
		mainTab.addTab("Bear", bearTab);
		settingsFrame.add(mainTab);

		//pack();

		settingsFrame.setSize(new Dimension(320, 250));
		settingsFrame.setResizable(false);
		settingsFrame.setLocationRelativeTo(null);
		settingsFrame.setVisible(true);

	}

	/**
	 * Quit menu om het programma af te sluiten.
	 */
	private void quit() {
		System.exit(0);
	}

	/**
	 * maak pieChart aan
	 * 
	 * @param height, width hoogte en breedte van een pie chart
	 * 
	 */
	private void makePieChart(int height, int width) {
		pieChart = new PieChart();
		pieChart.setSize(height * 3, width * 2); // resize moet nog
		pieChart.stats(getPopulationDetails());
		pieChart.repaint();
	}

	/**
	 * maak histogram aan
	 * 
	 * @param height, width hoogte en breedte van een histogram
	 */
	private void makeHistogram(int height, int width) {
		histogram = new Histogram();
		histogram.setSize(height * 2, width * 2);
		histogram.stats(getPopulationDetails());
		histogram.repaint();
	}

	/**
	 * maak history view aan
	 * 
	 * @param height, width hoogte en breedte van een history view
	 */
	private void makeHistoryView(int height, int width) {
		historyView = new HistoryView(height, width);
		historyView.setSize(height, width);
		historyView.stats(getPopulationDetails());
		historyView.history(getIsReset());
	}

	/**
	 * Getter om threadRunner object op te halen
	 * 
	 * @return threadRunner object van type ThreadRunner
	 */
	public ThreadRunner getThreadRunner() {
		return threadRunner;
	}

	/**
	 * getter voor FieldStats stats
	 * 
	 * @param stats
	 */
	public FieldStats getStats() {
		return stats;
	}

	/**
	 * convert a string to int
	 * 
	 * @param number
	 */
	private int stringToInt(JTextField text) {
		int number = 0;
		try {
			number = Integer.parseInt(text.getText());
		} catch (NumberFormatException e) {
			historyView.getTextArea().append(
					"Alleen hele getallen zijn toegestaan" + "\r\n");
		}
		return number;
	}

	/**
	 * convert a string to double
	 * 
	 * @param number
	 */
	private double stringToDouble(String text) {
		double number = 0;
		try {
			number = Double.parseDouble(text);
		} catch (NumberFormatException e) {
			historyView.getTextArea().append(
					"Alleen cijfers zijn toegestaan" + "\r\n");
		}
		return number;
	}

	/**
	 * Define a color to be used for a given class of animal.
	 * 
	 * @param animalClass
	 *            The animal's Class object.
	 * @param color
	 *            The color to be used for the given class.
	 */
	public void setColor(Class animalClass, Color color) {
		colors.put(animalClass, color);
	}

	/**
	 * @return The color to be used for a given class of animal.
	 */
	private Color getColor(Class animalClass) {
		Color col = colors.get(animalClass);
		if (col == null) {
			// no color defined for this class
			return UNKNOWN_COLOR;
		} else {
			return col;
		}
	}

	/**
	 * Show the current status of the field.
	 * 
	 * @param step
	 *            Which iteration step it is.
	 * @param field
	 *            The field whose status is to be displayed.
	 */
	public void showStatus(int step, Field field) {
		if (!isVisible()) {
			setVisible(true);
		}

		stepLabel.setText(STEP_PREFIX + step);
		stats.reset();

		fieldView.preparePaint();

		for (int row = 0; row < field.getDepth(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {
				Object animal = field.getObjectAt(row, col);
				if (animal != null) {
					stats.incrementCount(animal.getClass());
					fieldView.drawMark(col, row, getColor(animal.getClass()));
				} else {
					fieldView.drawMark(col, row, EMPTY_COLOR);
				}
			}
		}
		stats.countFinished();

		population.setText(POPULATION_PREFIX
				+ stats.getPopulationDetails(field));
		fieldView.repaint();

		pieChart.stats(getPopulationDetails());
		pieChart.repaint();
		viewsToDisplay = 0;

		histogram.stats(getPopulationDetails());
		histogram.repaint();

		historyView.stats(getPopulationDetails());
		historyView.history(getIsReset());
	}

	/**
	 * retourneert de counter op voor ieder kleur
	 * 
	 * @return colorStats HashMap die kleur bij houdt en de hoeveelheid
	 */
	public HashMap<Color, Counter> getPopulationDetails() {
		HashMap<Class, Counter> classStats = stats.getPopulation();
		HashMap<Color, Counter> colorStats = new HashMap<Color, Counter>();

		for (Class c : classStats.keySet()) {
			colorStats.put(getColor(c), classStats.get(c));
		}
		return colorStats;
	}

	/**
	 * Determine whether the simulation should continue to run.
	 * 
	 * @return true If there is more than one species alive.
	 */
	public boolean isViable(Field field) {
		return stats.isViable(field);
	}

	/**
	 * Getter voor boolean isReset
	 * 
	 * @return isReset bepaald of step 0 is voor de historyview
	 */
	public boolean getIsReset() {
		return isReset;
	}

	// /**
	// * getter voor textarea
	// * @return textarea
	// */
	// public JTextArea getTextArea()
	// {
	// return history;
	// }

	/**
	 * Provide a graphical view of a rectangular field. This is a nested class
	 * (a class defined inside a class) which defines a custom component for the
	 * user interface. This component displays the field. This is rather
	 * advanced GUI stuff - you can ignore this for your project if you like.
	 */
	private class FieldView extends JPanel {
		private final int GRID_VIEW_SCALING_FACTOR = 6;

		private int gridWidth, gridHeight;
		private int xScale, yScale;
		Dimension size;
		private Graphics g;
		private Image fieldImage;

		/**
		 * Create a new FieldView component.
		 */
		public FieldView(int height, int width) {
			gridHeight = height;
			gridWidth = width;
			size = new Dimension(0, 0);
		}

		/**
		 * Tell the GUI manager how big we would like to be.
		 */
		public Dimension getPreferredSize() {
			return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
					gridHeight * GRID_VIEW_SCALING_FACTOR);
		}

		/**
		 * Prepare for a new round of painting. Since the component may be
		 * resized, compute the scaling factor again.
		 */
		public void preparePaint() {
			if (!size.equals(getSize())) { // if the size has changed...
				size = getSize();
				fieldImage = fieldView.createImage(size.width, size.height);
				g = fieldImage.getGraphics();

				xScale = size.width / gridWidth;
				if (xScale < 1) {
					xScale = GRID_VIEW_SCALING_FACTOR;
				}
				yScale = size.height / gridHeight;
				if (yScale < 1) {
					yScale = GRID_VIEW_SCALING_FACTOR;
				}
			}
		}

		/**
		 * Paint on grid location on this field in a given color.
		 */
		public void drawMark(int x, int y, Color color) {
			g.setColor(color);
			g.fillRect(x * xScale, y * yScale, xScale - 1, yScale - 1);
		}

		/**
		 * The field view component needs to be redisplayed. Copy the internal
		 * image to screen.
		 */
		public void paintComponent(Graphics g) {
			if (fieldImage != null) {
				Dimension currentSize = getSize();
				if (size.equals(currentSize)) {
					g.drawImage(fieldImage, 0, 0, null);
				} else {
					// Rescale the previous image.
					g.drawImage(fieldImage, 0, 0, currentSize.width,
							currentSize.height, null);
				}
			}
		}
	}
}
