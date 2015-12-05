package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class StandardView extends JPanel {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StandardView() {
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
//		panel.setBackground(new Color(249, 222, 112));
		this.add(panel, BorderLayout.CENTER);

	}

}
