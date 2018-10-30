package de.turnierverwaltung.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class StartpageItemView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon checkImg = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/check.png")));
	private JLabel label;

	public StartpageItemView() {
		super();

		Color titleColor = new Color((SystemColor.text).getRGB());
		Color titleTextColor = new Color((SystemColor.textText).getRGB());
		setBackground(titleColor);
		setForeground(titleTextColor);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		setBorder(blackline);
		setPreferredSize(new Dimension(800, 150));
		label = new JLabel(checkImg);
	}

	public void addCheckItem() {

		if (checkComponent() == false) {

			this.add(label);
		}
	}

	public void removeCheckItem() {
		if (checkComponent() == true) {
			this.remove(label);

		}
	}

	private Boolean checkComponent() {
		Component comp[] = this.getComponents();
		for (int i = 0; i < comp.length; i++) {
			if (comp[i] == label) {
				return true;
			}
		}
		return false;
	}
}
