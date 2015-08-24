package de.turnierverwaltung.view;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LadebalkenView extends JDialog {

	// JProgressBar-Objekt wird erzeugt

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private JPanel panel;
	private JLabel textLabel;
	int num = 0;

	public LadebalkenView(String text) {
		this.setAlwaysOnTop(true);
		setTitle("Speichervorgang");
		
		progressBar = new JProgressBar(0, 100);

		// Call setStringPainted now so that the progress bar height
		// stays the same whether or not the string is shown.
		progressBar.setOpaque(true);
		progressBar.setVisible(true);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		textLabel = new JLabel(text);
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		panel.add(textLabel);
		panel.add(progressBar);
		// panel.setOpaque(true);
		panel.setVisible(true);
		add(panel);

		// add(progressBar, BorderLayout.PAGE_START);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		// setContentPane(panel);
		setVisible(true);
		setLocationRelativeTo(null);

	}

	public JProgressBar getMeinLadebalken() {
		return progressBar;
	}

	public void iterate() {
		num += 20;
		progressBar.setValue(num);
		progressBar.paint(progressBar.getGraphics());
		textLabel.paint(textLabel.getGraphics());
		if (num >= 100) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException ex) {
			}
			this.dispose();
		}

	}

	public void setMeinLadebalken(JProgressBar meinLadebalken) {
		this.progressBar = meinLadebalken;
	}

	public void setValue(int value) {
		progressBar.setValue(value);
	}
}
