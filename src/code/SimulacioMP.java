package code;

import java.util.Date;
import java.text.SimpleDateFormat;

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
		SimpleDateFormat dateFormat = new SimpleDateFormat(args[1]);
		long start = Long.parseLong(args[2]);

		double calc = simulation(type);

		long end = System.currentTimeMillis();

		long totalTime = end - start;

		String elapsedTime = (totalTime / 1000) + "_" + ((totalTime % 1000) / 10);
		String iniTime = dateFormat.format(new Date(start));
		String finTime = dateFormat.format(new Date(end));

		System.out.print(iniTime.substring(0, iniTime.length() - 1) + System.lineSeparator()
				+ finTime.substring(0, finTime.length() - 1) + System.lineSeparator() + elapsedTime
				+ System.lineSeparator() + calc);
	}
}
