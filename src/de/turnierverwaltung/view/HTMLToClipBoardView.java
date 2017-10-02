package de.turnierverwaltung.view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HTMLToClipBoardView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton copyToClipBoardButton;

	public HTMLToClipBoardView() {
		super();
		JLabel label = new JLabel();
		setLayout(new FlowLayout(FlowLayout.LEFT));
		copyToClipBoardButton = new JButton();
		add(label);
		add(copyToClipBoardButton);

	}

	public JButton getCopyToClipBoardButton() {
		return copyToClipBoardButton;
	}

	public void setCopyToClipBoardButton(JButton copyToClipBoardButton) {
		this.copyToClipBoardButton = copyToClipBoardButton;
	}

}
