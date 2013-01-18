
public class Main {
	private static Simulator simulator;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub							
		setSimulator(new Simulator());
		System.out.println("Simulator gestart");
	}
	
	public static Simulator getSimulator() {
		return simulator;
	}

	public static void setSimulator(Simulator simulator) {
		Main.simulator = simulator;
	}
	
}
