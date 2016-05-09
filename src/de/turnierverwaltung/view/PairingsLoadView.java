package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PairingsLoadView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton loadPairingsButton;

	/**
	 * Create the panel.
	 */
	public PairingsLoadView() {
		setLayout(new BorderLayout());
		loadPairingsButton = new JButton(Messages.getString("PairingsLoadView.0")); //$NON-NLS-1$
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(loadPairingsButton);

		add(panel, BorderLayout.CENTER);
	}

	public JButton getLoadPairingsButton() {
		return loadPairingsButton;
	}

	public void setLoadPairingsButton(JButton loadPairingsButton) {
		this.loadPairingsButton = loadPairingsButton;
	}

}
