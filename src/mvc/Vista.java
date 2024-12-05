package mvc;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JButton;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSimulate;
	private JLabel lblProcessTime;
	private JLabel lblThreadsTime;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Vista() {
		setTitle("Alpha Fold Protein Simulator");
		initComponents();
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
		
		JSpinner spiPrimary = new JSpinner();
		spiPrimary.setBounds(187, 93, 49, 20);
		contentPane.add(spiPrimary);
		
		JLabel lblSecundary = new JLabel("Secondary structure (type 2):");
		lblSecundary.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSecundary.setBounds(269, 91, 176, 23);
		contentPane.add(lblSecundary);
		
		JSpinner spiSecondary = new JSpinner();
		spiSecondary.setBounds(449, 93, 49, 20);
		contentPane.add(spiSecondary);
		
		JLabel lblTertiary = new JLabel("Tertiary structure (type 3):");
		lblTertiary.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTertiary.setBounds(23, 140, 160, 23);
		contentPane.add(lblTertiary);
		
		JSpinner spiTertiary = new JSpinner();
		spiTertiary.setBounds(187, 142, 49, 20);
		contentPane.add(spiTertiary);
		
		JLabel lblQuaternary = new JLabel("Quaternary structure (type 4):");
		lblQuaternary.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQuaternary.setBounds(269, 140, 176, 23);
		contentPane.add(lblQuaternary);
		
		JSpinner spiQuaternary = new JSpinner();
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
	}


	public JButton getBtnSimulate() {
		return btnSimulate;
	}


	public JLabel getLblProcessTime() {
		return lblProcessTime;
	}


	public JLabel getLblThreadsTime() {
		return lblThreadsTime;
	}
}
