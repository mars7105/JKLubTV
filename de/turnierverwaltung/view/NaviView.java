package de.turnierverwaltung.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class NaviView extends JPanel {
	private JButton turnierListeButton;
	private ImageIcon spielerListeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/arrow-right-3.png")));
	private ImageIcon turnierListeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/arrow-right-3.png")));
	private JButton spielerListeButton;

	public NaviView() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.LIGHT_GRAY);
		EmptyBorder eBorder = new EmptyBorder(10, 10, 10, 10);
		this.setBorder(eBorder);
		turnierListeButton = new JButton("Turnierliste", turnierListeIcon);
		spielerListeButton = new JButton("Spielerliste", spielerListeIcon);
//		Dimension dim1 = turnierListeButton.getPreferredSize();
//		Dimension dim2 = spielerListeButton.getPreferredSize();
//		if (dim1.getWidth() >= dim2.getWidth()) {
//			turnierListeButton.setPreferredSize(dim1);
//			spielerListeButton.setPreferredSize(dim1);
//		} else {
//			turnierListeButton.setPreferredSize(dim2);
//			spielerListeButton.setPreferredSize(dim2);
//		}
		this.add(turnierListeButton);
		this.add(spielerListeButton);
	}

	public JButton getTurnierListeButton() {
		return turnierListeButton;
	}

	public void setTurnierListeButton(JButton turnierListeButton) {
		this.turnierListeButton = turnierListeButton;
	}

	public JButton getSpielerListeButton() {
		return spielerListeButton;
	}

	public void setSpielerListeButton(JButton spielerListeButton) {
		this.spielerListeButton = spielerListeButton;
	}

}
