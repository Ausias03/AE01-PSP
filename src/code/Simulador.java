package code;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JSpinner;
import javax.swing.JButton;

import java.util.Date;

public class Simulador extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String dateFormat = "yyyyMMdd_HHmmss_SSS";
	private static final String filesRoute = "resources\\files";
	private JPanel contentPane;
	private JButton btnSimulate;
	private JLabel lblProcessTime;
	private JLabel lblThreadsTime;
	private JSpinner spiPrimary;
	private JSpinner spiSecondary;
	private JSpinner spiTertiary;
	private JSpinner spiQuaternary;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Simulador frame = new Simulador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void execute(int protein, int type, File file, long start) {
		String className = "code.SimulacioMP";
		String javaHome = System.getProperty("java.home");
		String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
		String classpath = System.getProperty("java.class.path");

		ArrayList<String> command = new ArrayList<>();
		command.add(javaBin);
		command.add("-cp");
		command.add(classpath);
		command.add(className);
		command.add(String.valueOf(type));
		command.add(dateFormat);
		command.add(String.valueOf(start));

		ProcessBuilder builder = new ProcessBuilder(command);
		builder.redirectOutput(file);

		try {
			Process p = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String buildFileTitle(int protein, int type, String sim, long startTime) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		String date = format.format(new Date(startTime));
		return "PROT_" + sim + "_" + type + "_n" + protein + "_" + date.substring(0, date.length() - 1) + ".sim";
	}

	private static boolean teContingut(File[] files) {
		boolean contingut = false;
		try {
			for (File arxiu : files) {
				FileReader fr = new FileReader(arxiu, StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(fr);
				String linea = br.readLine();
				contingut = (linea != null);
				br.close();
				fr.close();
				if (contingut == false)
					break;
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return contingut;
	}

	private void deleteFiles(File dir) {
		for (File file : dir.listFiles()) {
			file.delete();
		}
	}

	public void initEventHandlers() {
		btnSimulate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File fileDir = new File(filesRoute);
				if (fileDir.exists()) {
					deleteFiles(fileDir);
				} else {
					fileDir.mkdir();
				}

				JSpinner[] spinnerArray = new JSpinner[] { spiPrimary, spiSecondary, spiTertiary, spiQuaternary };

				// MP:
				long procStart = System.currentTimeMillis();
				for (int i = 1; i <= spinnerArray.length; i++) {
					for (int j = 1; j <= (int) spinnerArray[i - 1].getValue(); j++) {
						long start = System.currentTimeMillis();
						execute(j, i, new File(filesRoute + "\\" + buildFileTitle(j, i, "MP", start)), start);
					}
				}
				while (!teContingut(new File(filesRoute).listFiles())) {
				}
				long procEnd = System.currentTimeMillis();

				// MT:
				ArrayList<Thread> threads = new ArrayList<>();
				long thrStart = System.currentTimeMillis();
				for (int i = 1; i <= spinnerArray.length; i++) {
					for (int j = 1; j <= (int) spinnerArray[i - 1].getValue(); j++) {
						long start = System.currentTimeMillis();
						Thread thread = new Thread(new SimulacioMT(i,
								new File(filesRoute + "\\" + buildFileTitle(j, i, "MT", start)), start, dateFormat));
						thread.start();
						threads.add(thread);
					}
				}
				for (Thread thread : threads) {
					try {
						thread.join();
					} catch (Exception ex) {
						ex.getStackTrace();
					}
				}
				long thrEnd = System.currentTimeMillis();

				lblProcessTime.setText(String.format("%.2f", ((procEnd - procStart) / 1000.0d)) + " s");
				lblThreadsTime.setText(String.format("%.2f", ((thrEnd - thrStart) / 1000.0d)) + " s");
			}
		});
	}

	public Simulador() {
		setTitle("Alpha Fold Protein Simulator");
		initComponents();
		initEventHandlers();
	}

	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitle = new JLabel("Alpha Fold Java Protein Simulator");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitle.setBounds(105, 27, 320, 41);
		contentPane.add(lblTitle);

		JLabel lblPrimary = new JLabel("Primary structure (type 1):");
		lblPrimary.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPrimary.setBounds(23, 91, 160, 23);
		contentPane.add(lblPrimary);

		spiPrimary = new JSpinner();
		spiPrimary.setBounds(187, 93, 49, 20);
		contentPane.add(spiPrimary);

		JLabel lblSecundary = new JLabel("Secondary structure (type 2):");
		lblSecundary.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSecundary.setBounds(269, 91, 176, 23);
		contentPane.add(lblSecundary);

		spiSecondary = new JSpinner();
		spiSecondary.setBounds(449, 93, 49, 20);
		contentPane.add(spiSecondary);

		JLabel lblTertiary = new JLabel("Tertiary structure (type 3):");
		lblTertiary.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTertiary.setBounds(23, 140, 160, 23);
		contentPane.add(lblTertiary);

		spiTertiary = new JSpinner();
		spiTertiary.setBounds(187, 142, 49, 20);
		contentPane.add(spiTertiary);

		JLabel lblQuaternary = new JLabel("Quaternary structure (type 4):");
		lblQuaternary.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQuaternary.setBounds(269, 140, 176, 23);
		contentPane.add(lblQuaternary);

		spiQuaternary = new JSpinner();
		spiQuaternary.setBounds(449, 142, 49, 20);
		contentPane.add(spiQuaternary);

		btnSimulate = new JButton("Simulate");
		btnSimulate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSimulate.setBounds(204, 210, 100, 41);
		contentPane.add(btnSimulate);

		JLabel lblProcess = new JLabel("Multiprocess:");
		lblProcess.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblProcess.setBounds(105, 279, 100, 23);
		contentPane.add(lblProcess);

		JLabel lblThreads = new JLabel("Multithread:");
		lblThreads.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblThreads.setBounds(280, 279, 100, 23);
		contentPane.add(lblThreads);

		lblProcessTime = new JLabel("0.00 s");
		lblProcessTime.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblProcessTime.setBounds(205, 279, 65, 23);
		contentPane.add(lblProcessTime);

		lblThreadsTime = new JLabel("0.00 s");
		lblThreadsTime.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblThreadsTime.setBounds(375, 279, 65, 23);
		contentPane.add(lblThreadsTime);

		setVisible(true);
	}
}
