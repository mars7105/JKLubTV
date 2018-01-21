package de.turnierverwaltung.control.sqlite;

//JKlubTV - Ein Programm zum verwalten von Schach Turnieren
//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

import java.sql.SQLException;
import java.util.ArrayList;

//JKlubTV - Ein Programm zum verwalten von Schach Turnieren
//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
import javax.swing.JOptionPane;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.PartienDAO;
import de.turnierverwaltung.view.ProgressBarView;

public class SaveTournamentControl {
	private MainControl mainControl;
	private ProgressBarView ladebalkenView;;

	public SaveTournamentControl(MainControl mainControl) {
		this.mainControl = mainControl;

	}

	/**
	 * Create the GUI and show it. As with all GUI code, this must run on the
	 * event-dispatching thread.
	 */
	private void createAndShowGUI() {

		ladebalkenView = new ProgressBarView(
				Messages.getString("SaveTurnierControl.0") + this.mainControl.getTournament().getTurnierName(), //$NON-NLS-1$
				this.mainControl.getTournament().getAnzahlGruppen());

		// Display the window.
		ladebalkenView.pack();

		ladebalkenView.setVisible(true);
	}

	public Boolean saveChangedPartien() throws SQLException {
		Boolean ready = false;

		if (mainControl.getNewTournament()) {
			ready = saveNewTurnier();
			if (ready) {
				mainControl.setNewTournament(false);
				mainControl.getActionListenerTournamentItemsControl().loadTurnierListe();
				mainControl.getActionListenerTournamentItemsControl().reloadTurnier();
				mainControl.getActionListenerTournamentItemsControl().loadPairingsView();
			}
			return ready;
		} else {
			ready = mainControl.getPairingsControl().checkNewTurnier();
			if (ready) {
				ArrayList<Game> changedPartien;
				if (this.mainControl.getChangedGames() == null) {
					JOptionPane.showMessageDialog(mainControl, Messages.getString("SaveTurnierControl.1") //$NON-NLS-1$
							+ this.mainControl.getTournament().getTurnierName()
							+ Messages.getString("SaveTurnierControl.2")); //$NON-NLS-1$
					return false;
				} else {
					changedPartien = this.mainControl.getChangedGames();

				}

				this.mainControl.setSqlGamesControl(new SQLGamesControl(this.mainControl));

				boolean saved = false;
				DAOFactory daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
				PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();

				saved = mySQLPartienDAO.updatePartien(changedPartien);
				changedPartien.clear();
				if (saved) {
					// JOptionPane.showMessageDialog(mainControl,
					// Messages.getString("SaveTurnierControl.3") +
					// this.mainControl.getTurnier().getTurnierName() //$NON-NLS-1$
					// + Messages.getString("SaveTurnierControl.4")); //$NON-NLS-1$
					return true;
				} else {

					JOptionPane.showMessageDialog(mainControl, Messages.getString("SaveTurnierControl.5") //$NON-NLS-1$
							+ this.mainControl.getTournament().getTurnierName()
							+ Messages.getString("SaveTurnierControl.6")); //$NON-NLS-1$
					return false;
				}
			} else {
				JOptionPane.showMessageDialog(mainControl,
						Messages.getString("SaveTurnierControl.7") + Messages.getString("SaveTurnierControl.8")); //$NON-NLS-1$ //$NON-NLS-2$
				return false;
			}
		}
	}

	public Boolean saveNewTurnier() throws SQLException {
		int turnierId = -1;
		Boolean ready = mainControl.getPairingsControl().checkNewTurnier();
		if (ready) {

			createAndShowGUI();
			boolean saveOK1 = false;
			boolean saveOK2 = false;
			boolean saveOK4 = false;

			// ladebalkenView.iterate();

			this.mainControl.setSqlTournamentControl(new SQLTournamentControl(this.mainControl));

			this.mainControl.setSqlGroupsControl(new SQLGroupsControl(this.mainControl));

			this.mainControl.setSqlPlayerControl(new SQLPlayerControl(this.mainControl));

			this.mainControl.setSqlGamesControl(new SQLGamesControl(this.mainControl));

			this.mainControl.setSqlTournament_has_PlayerControl(new SQLTournament_has_PlayerControl(this.mainControl));

			for (int index = 0; index < this.mainControl.getTournament().getAnzahlGruppen(); index++) {

				ladebalkenView.iterate();
				if (mainControl.getTournament().getTurnierId() < 0) {
					turnierId = this.mainControl.getSqlTournamentControl().insertTurnier();
					if (turnierId >= 0) {
						saveOK1 = true;
						mainControl.getActionListenerTournamentItemsControl().setLoadedTurnierID(turnierId);
					}

				} else {
					this.mainControl.getSqlTournamentControl().updateTurnier(this.mainControl.getTournament());
					saveOK1 = true;
				}
				ladebalkenView.iterate();
				if (mainControl.getTournament().getGruppe()[index].getGruppeId() < 0) {
					saveOK2 = this.mainControl.getSqlGroupsControl().insertGruppe(index);
					ladebalkenView.iterate();
					ladebalkenView.iterate();
					saveOK4 = this.mainControl.getSqlGamesControl().insertPartien(index);
					this.mainControl.getSqlTournament_has_PlayerControl().insertTurnier_has_Spieler(index);
				} else {
					saveOK2 = this.mainControl.getSqlGroupsControl().updateGruppe(index);
					ladebalkenView.iterate();
					ladebalkenView.iterate();
					saveOK4 = this.mainControl.getSqlGamesControl().updatePartien(index);

				}
				ladebalkenView.iterate();
				if (saveOK1 && saveOK2 && saveOK4) {

				} else {
					JOptionPane.showMessageDialog(mainControl, Messages.getString("SaveTurnierControl.9") //$NON-NLS-1$
							+ this.mainControl.getTournament().getTurnierName()
							+ Messages.getString("SaveTurnierControl.10")); //$NON-NLS-1$
					return false;

				}
			}
			if (saveOK1 && saveOK2 && saveOK4) {
				// JOptionPane.showMessageDialog(mainControl,
				// Messages.getString("SaveTurnierControl.11") //$NON-NLS-1$
				// + this.mainControl.getTurnier().getTurnierName() +
				// Messages.getString("SaveTurnierControl.12")); //$NON-NLS-1$
				try {
					mainControl.getActionListenerTournamentItemsControl().loadTurnierListe();
				} catch (SQLException e) {
					ExceptionHandler eh = new ExceptionHandler(mainControl);
					eh.fileSQLError(e.getMessage());
				}
				mainControl.getPlayerListControl().updateSpielerListe();
				return true;
			} else {

				JOptionPane.showMessageDialog(mainControl,
						Messages.getString("SaveTurnierControl.13") + this.mainControl.getTournament().getTurnierName() //$NON-NLS-1$
								+ Messages.getString("SaveTurnierControl.14")); //$NON-NLS-1$
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(mainControl,
					Messages.getString("SaveTurnierControl.15") + Messages.getString("SaveTurnierControl.16")); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
	}

}
