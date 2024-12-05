package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador {
	private Vista vista;
	private Model model;
	
	public Controlador(Vista vista, Model model) {
		this.vista = vista;
		this.model = model;
		
		initEventHandlers();
	}
	
	public void initEventHandlers() {
		vista.getBtnSimulate().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
	}
}
