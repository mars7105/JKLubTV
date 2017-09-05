package de.turnierverwaltung.control;

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

import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.PartienDAO;
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
				Messages.getString("SaveTurnierControl.0") + this.mainControl.getTurnier().getTurnierName(), //$NON-NLS-1$
				this.mainControl.getTurnier().getAnzahlGruppen());

		// Display the window.
		ladebalkenView.pack();

		ladebalkenView.setVisible(true);
	}

	public Boolean saveChangedPartien() throws SQLException {
		Boolean ready = false;

		if (mainControl.getNeuesTurnier()) {
			ready = saveNewTurnier();
			if (ready) {
				mainControl.setNeuesTurnier(false);
				mainControl.getTurnierListeLadenControl().loadTurnierListe();
				mainControl.getTurnierListeLadenControl().reloadTurnier();
				mainControl.getTurnierListeLadenControl().loadPairingsView();
			}
			return ready;
		} else {
			ready = mainControl.getPairingsControl().checkNewTurnier();
			if (ready) {
				ArrayList<Game> changedPartien;
				if (this.mainControl.getChangedPartien() == null) {
					JOptionPane.showMessageDialog(mainControl,
							Messages.getString("SaveTurnierControl.1") //$NON-NLS-1$
									+ this.mainControl.getTurnier().getTurnierName()
									+ Messages.getString("SaveTurnierControl.2")); //$NON-NLS-1$
					return false;
				} else {
					changedPartien = this.mainControl.getChangedPartien();

				}

				this.mainControl.setPartienTableControl(new SQLGamesControl(this.mainControl));

				boolean saved = false;
				DAOFactory daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
				PartienDAO mySQLPartienDAO = daoFactory.getPartienDAO();

				saved = mySQLPartienDAO.updatePartien(changedPartien);
				changedPartien.clear();
				if (saved) {
//					JOptionPane.showMessageDialog(mainControl,
//							Messages.getString("SaveTurnierControl.3") + this.mainControl.getTurnier().getTurnierName() //$NON-NLS-1$
//									+ Messages.getString("SaveTurnierControl.4")); //$NON-NLS-1$
					return true;
				} else {

					JOptionPane.showMessageDialog(mainControl,
							Messages.getString("SaveTurnierControl.5") //$NON-NLS-1$
									+ this.mainControl.getTurnier().getTurnierName()
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

			this.mainControl.setTurnierTableControl(new SQLTournamentControl(this.mainControl));

			this.mainControl.setGruppenTableControl(new SQLGroupsControl(this.mainControl));

			this.mainControl.setSpielerTableControl(new SQLPlayerControl(this.mainControl));

			this.mainControl.setPartienTableControl(new SQLGamesControl(this.mainControl));

			this.mainControl.setTurnier_has_SpielerTableControl(new SQLTournament_has_PlayerControl(this.mainControl));

			for (int index = 0; index < this.mainControl.getTurnier().getAnzahlGruppen(); index++) {

				ladebalkenView.iterate();
				if (mainControl.getTurnier().getTurnierId() < 0) {
					turnierId = this.mainControl.getTurnierTableControl().insertTurnier();
					if (turnierId >= 0) {
						saveOK1 = true;
						mainControl.getTurnierListeLadenControl().setLoadedTurnierID(turnierId);
					}

				} else {
					this.mainControl.getTurnierTableControl().updateTurnier(this.mainControl.getTurnier());
					saveOK1 = true;
				}
				ladebalkenView.iterate();
				if (mainControl.getTurnier().getGruppe()[index].getGruppeId() < 0) {
					saveOK2 = this.mainControl.getGruppenTableControl().insertGruppe(index);
					ladebalkenView.iterate();
					ladebalkenView.iterate();
					saveOK4 = this.mainControl.getPartienTableControl().insertPartien(index);
					this.mainControl.getTurnier_has_SpielerTableControl().insertTurnier_has_Spieler(index);
				} else {
					saveOK2 = this.mainControl.getGruppenTableControl().updateGruppe(index);
					ladebalkenView.iterate();
					ladebalkenView.iterate();
					saveOK4 = this.mainControl.getPartienTableControl().updatePartien(index);

				}
				ladebalkenView.iterate();
				if (saveOK1 && saveOK2 && saveOK4) {

				} else {
					JOptionPane.showMessageDialog(mainControl,
							Messages.getString("SaveTurnierControl.9") //$NON-NLS-1$
									+ this.mainControl.getTurnier().getTurnierName()
									+ Messages.getString("SaveTurnierControl.10")); //$NON-NLS-1$
					return false;

				}
			}
			if (saveOK1 && saveOK2 && saveOK4) {
				JOptionPane.showMessageDialog(mainControl, Messages.getString("SaveTurnierControl.11") //$NON-NLS-1$
						+ this.mainControl.getTurnier().getTurnierName() + Messages.getString("SaveTurnierControl.12")); //$NON-NLS-1$
				try {
					mainControl.getTurnierListeLadenControl().loadTurnierListe();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mainControl.getSpielerLadenControl().updateSpielerListe();
				return true;
			} else {

				JOptionPane.showMessageDialog(mainControl,
						Messages.getString("SaveTurnierControl.13") + this.mainControl.getTurnier().getTurnierName() //$NON-NLS-1$
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
