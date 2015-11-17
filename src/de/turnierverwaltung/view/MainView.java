package de.turnierverwaltung.view;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton cancelButton;
	public MainView() {

		setLayout(new FlowLayout());
//		setBackground(new Color(249, 222, 112));
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(buttonPane);

		cancelButton = new JButton("Alles Verwerfen");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

	}
}
