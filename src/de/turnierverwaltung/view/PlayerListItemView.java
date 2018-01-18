package de.turnierverwaltung.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import de.turnierverwaltung.model.Player;

public class PlayerListItemView extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final JButton spielerBearbeitenButton;
	private final JButton spielerLoeschenButton;
	private final ImageIcon userDelete = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-delete-2.png"))); //$NON-NLS-1$
	private final ImageIcon userProperties = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-properties.png"))); //$NON-NLS-1$

	public PlayerListItemView(final Player spieler) {
		final Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		// line = new JPanel();
		//
		// line.setLayout(new FlowLayout(FlowLayout.LEFT));
		final JPanel mainLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainLine.setBorder(raisedetched);
		final JPanel playerLine = new JPanel();
		playerLine.setLayout(new BoxLayout(playerLine, BoxLayout.PAGE_AXIS));
		playerLine.setPreferredSize(new Dimension(350, 50));
		playerLine.setBackground(Color.WHITE);

		final JPanel buttonLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
		final String lineText0 = spieler.getName();
		String lineText1 = "";

		if (spieler.getDWZ() > 0) {
			lineText1 += "DWZ: " + spieler.getDwz();
		}
		if (spieler.getDwzData().getCsvFIDE_Elo() > 0) {
			lineText1 += " ELO: " + spieler.getDwzData().getCsvFIDE_Elo();
		}

		String lineText2 = "";
		if (spieler.getDwzData().getCsvZPS().length() > 0) {
			lineText2 += "ZPS: " + spieler.getDwzData().getCsvZPS() + " - MGL: " + spieler.getDwzData().getCsvMgl_Nr();
		}
		final JLabel col0 = new JLabel(lineText0);
		playerLine.add(col0);
		final JLabel col1 = new JLabel(lineText1);
		playerLine.add(col1);
		final JLabel col2 = new JLabel(lineText2);
		playerLine.add(col2);

		mainLine.add(playerLine);

		spielerBearbeitenButton = new JButton(Messages.getString("SpielerLadenView.5"), userProperties); //$NON-NLS-1$

		buttonLine.add(spielerBearbeitenButton);
		spielerLoeschenButton = new JButton(Messages.getString("SpielerLadenView.6"), userDelete); //$NON-NLS-1$
		buttonLine.add(spielerLoeschenButton);
		mainLine.add(buttonLine);
		add(mainLine);
	}

	public JButton getSpielerBearbeitenButton() {
		return spielerBearbeitenButton;
	}

	public JButton getSpielerLoeschenButton() {
		return spielerLoeschenButton;
	}

}
