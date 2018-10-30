package de.turnierverwaltung.view.tournamentlist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import de.turnierverwaltung.view.Messages;

public class TournamentListItemView extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final JButton turnierLoeschenButton;
	private final JButton turnierBearbeitenButton;

	private final ImageIcon turnierDelete = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-close-4.png"))); //$NON-NLS-1$
	private final ImageIcon turnierProperties = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-edit.png"))); //$NON-NLS-1$
	private final ImageIcon turnierLaden = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-preview.png"))); //$NON-NLS-1$
	private final JButton turnierLadeButton;

	public TournamentListItemView(final String turnierName) {
		final Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		setLayout(new FlowLayout(FlowLayout.LEFT));

		final JPanel mainLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainLine.setBorder(raisedetched);
		final JPanel turnierLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
		turnierLine.setPreferredSize(new Dimension(350, 50));
		Color titleColor = new Color((SystemColor.text).getRGB());
		Color titleTextColor = new Color((SystemColor.textText).getRGB());
		turnierLine.setBackground(titleColor);
		turnierLine.setForeground(titleTextColor);
		final JPanel buttonLine = new JPanel(new FlowLayout(FlowLayout.LEFT));

		turnierLadeButton = new JButton(Messages.getString("TurnierListeLadenView.4"), turnierLaden);
		buttonLine.add(turnierLadeButton);
		turnierBearbeitenButton = new JButton(Messages.getString("TurnierListeLadenView.5"), turnierProperties);
		buttonLine.add(turnierBearbeitenButton);

		turnierLoeschenButton = new JButton(Messages.getString("TurnierListeLadenView.6"), turnierDelete);
		buttonLine.add(turnierLoeschenButton);
		final JLabel tName = new JLabel(Messages.getString("TurnierListeLadenView.7") + turnierName);
		turnierLine.add(tName);
		mainLine.add(turnierLine);
		mainLine.add(buttonLine);
		add(mainLine);
	}

	public JButton getTurnierBearbeitenButton() {
		return turnierBearbeitenButton;
	}

	public JButton getTurnierLadeButton() {
		return turnierLadeButton;
	}

	public JButton getTurnierLoeschenButton() {
		return turnierLoeschenButton;
	}

}
