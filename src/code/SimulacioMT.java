package code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimulacioMT implements Runnable {
	private int type;
	private File file;
	private long startTime;
	private SimpleDateFormat dateFormat;

	public SimulacioMT(int type, File file, long startTime, String dateFormat) {
		this.type = type;
		this.file = file;
		this.startTime = startTime;
		this.dateFormat = new SimpleDateFormat(dateFormat);
	}

	@Override
	public void run() {
		double calc = simulation(type);

		long end = System.currentTimeMillis();

		long totalTime = end - startTime;

		String elapsedTime = (totalTime / 1000) + "_" + ((totalTime % 1000) / 10);
		String iniTime = dateFormat.format(new Date(startTime));
		String finTime = dateFormat.format(new Date(end));

		String content = iniTime.substring(0, iniTime.length() - 1) + System.lineSeparator()
				+ finTime.substring(0, finTime.length() - 1) + System.lineSeparator() + elapsedTime
				+ System.lineSeparator() + calc;
		escriuFitxer(content);
	}

	private void escriuFitxer(String content) {
		try {
			FileWriter fw = new FileWriter(file, StandardCharsets.UTF_8);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
}
