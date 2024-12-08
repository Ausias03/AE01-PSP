package code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimulacioMT implements Runnable {
	/**
	 * int The type of the protein
	 */
	private int type;

	/**
	 * File The file where the results will be written
	 */
	private File file;

	/**
	 * long The start time when the Thread was created
	 */
	private long startTime;

	/**
	 * SimpleDateFormat The format in which the date will be written on the file
	 */
	private SimpleDateFormat dateFormat;

	/**
	 * Constructor of the class
	 * 
	 * @param type       int The type of the protein
	 * @param file       File The file where the results will be written
	 * @param startTime  long The start time when the Thread was created
	 * @param dateFormat SimpleDateFormat The format in which the date will be
	 *                   written on the file
	 */
	public SimulacioMT(int type, File file, long startTime, String dateFormat) {
		this.type = type;
		this.file = file;
		this.startTime = startTime;
		this.dateFormat = new SimpleDateFormat(dateFormat);
	}

	/**
	 * Overwritten method from the runnable interface that describes what the Thread
	 * will do
	 */
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
		writeFile(content);
	}

	/**
	 * Method that writes a given String into a file
	 * 
	 * @param content String The content to be written into a file
	 */
	private void writeFile(String content) {
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

	/**
	 * Method that runs the simulation of the protein
	 * 
	 * @param type int The type of protein to run the simulation on
	 * @return double The result of the simulation
	 */
	private static double simulation(int type) {
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
