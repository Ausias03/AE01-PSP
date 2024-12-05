package code;

import java.util.Locale;

public class SimulacioMP {
	public static double simulation(int type) {
		double calc = 0.0;
		double simulationTime = Math.pow(5, type);
		double startTime = System.currentTimeMillis();
		double endTime = startTime + simulationTime;
		while (System.currentTimeMillis() < endTime) {
			calc = Math.sin(Math.pow(Math.random(), 2));
		}
		return calc;
	}

	public static void main(String[] args) {
		int type = Integer.parseInt(args[0]);

		double calc = simulation(type);
		System.out.printf(Locale.ENGLISH, "%.2f", calc);
	}
}
