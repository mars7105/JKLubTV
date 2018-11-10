package de.turnierverwaltung.control.tournamentlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.sqlite.SaveTournamentControl;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.view.navigation.NaviView;

public class ActionListenerTournamentListControl implements ActionListener {
	private MainControl mainControl;
	private NaviView naviView;
	private AbstractButton newTurnierButton;

	public ActionListenerTournamentListControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		naviView = this.mainControl.getNaviView();
		newTurnierButton = naviView.getTurnierAddButton();
		newTurnierButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() .equals( newTurnierButton)) {
			mainControl.setNewTournamentPlayerIncludeControl(null);
			Tournament turnier = this.mainControl.getTournament();
			if (turnier == null) {
				mainControl.setNewTournamentControl(new NewTournamentControl(mainControl));
			} else {

				ArrayList<Game> changedPartien = this.mainControl.getChangedGames();
				if (changedPartien != null) {
					if (changedPartien.size() > 0) {
						// Custom button text
						Object[] options = { Messages.getString("TurnierListeLadenControl.10"), //$NON-NLS-1$
								Messages.getString("TurnierListeLadenControl.11") }; //$NON-NLS-1$
						int abfrage = JOptionPane.showOptionDialog(mainControl,
								Messages.getString("TurnierListeLadenControl.12") //$NON-NLS-1$
										+ Messages.getString("TurnierListeLadenControl.13"), //$NON-NLS-1$
								Messages.getString("TurnierListeLadenControl.14"), JOptionPane.YES_NO_CANCEL_OPTION, //$NON-NLS-1$
								JOptionPane.WARNING_MESSAGE, null, options, options[1]);
						if (abfrage == 0) {
							SaveTournamentControl saveGames = new SaveTournamentControl(mainControl);
							try {
								Boolean saved = saveGames.saveChangedPartien();
								if (saved == false) {
									changedPartien.clear();
								}

							} catch (SQLException e) {
								changedPartien.clear();
								ExceptionHandler eh = new ExceptionHandler(mainControl);
								eh.fileSQLError(e.getMessage());
							}
							mainControl.setNewTournamentControl(new NewTournamentControl(mainControl));
						}
					} else if (changedPartien.size() == 0) {
						mainControl.setNewTournamentControl(new NewTournamentControl(mainControl));
					}
				} else {
					mainControl.setNewTournamentControl(new NewTournamentControl(mainControl));
				}
			}
		}

	}

}
