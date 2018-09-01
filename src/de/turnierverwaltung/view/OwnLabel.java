package de.turnierverwaltung.view;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import de.turnierverwaltung.model.TournamentConstants;

public class OwnLabel extends JLabel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final Dimension dimension;

	public OwnLabel() {
		super();
		final int width = TournamentConstants.TEXTFIELD_WIDTH;
		final int height = TournamentConstants.TEXTFIELD_HEIGHT;
		dimension = new Dimension(width, height);
		setPreferredSize(dimension);
	}

	public OwnLabel(final String text) {
		super(text);
		final int width = TournamentConstants.TEXTFIELD_WIDTH;
		final int height = TournamentConstants.TEXTFIELD_HEIGHT;
		dimension = new Dimension(width, height);
		setPreferredSize(dimension);
		// setText(text);
	}

	public OwnLabel(final ImageIcon germanFlag) {
		super(germanFlag);
		final int width = TournamentConstants.TEXTFIELD_WIDTH;
		final int height = TournamentConstants.TEXTFIELD_HEIGHT;
		dimension = new Dimension(width, height);
		setPreferredSize(dimension);
	}
}
