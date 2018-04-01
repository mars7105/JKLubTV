package de.turnierverwaltung.control.tournamenttable;



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

import java.awt.Toolkit;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import de.turnierverwaltung.ZahlKleinerAlsN;
import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.navigation.NaviControl;
import de.turnierverwaltung.model.EventDate;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.table.MeetingTable;
import de.turnierverwaltung.model.table.PairingsTables;
import de.turnierverwaltung.view.DateChooserPanel;
import de.turnierverwaltung.view.TabbedPaneView;
import de.turnierverwaltung.view.tournamentlist.WaitForAllGroupsView;
import de.turnierverwaltung.view.tournamenttable.PairingsView;

public class PairingsControl implements ActionListener, PropertyChangeListener {

	private static int pruefeObZahlKleinerEinsIst(int zahl) throws ZahlKleinerAlsN {
		if (zahl <= 0) {
			throw new ZahlKleinerAlsN();
		}
		return zahl;
	}

	private MainControl mainControl;
	private Tournament tournament;
	private Group[] groups;
	private Game[] games;
	private MeetingTable meetingTable[];
	private int gruppenAnzahl;
	private PairingsView[] pairingsView;
	private JButton changeColorButton[][];
	private int[] spielerAnzahl;
	private PairingsTables[] pairingsTables;
	private TabbedPaneView[] tabAnzeigeView2;
	private Boolean[] neuesTurnier;
	private ArrayList<Game> changedGames;
	private DateChooserPanel[][] datePicker;
	private JComboBox<String>[][] roundNumbers;
	private int[][] changedGroups;
	private ImageIcon pairingsIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/media-playlist-shuffle-3.png"))); //$NON-NLS-1$

	public PairingsControl(MainControl mainControl) {
		this.mainControl = mainControl;

		tournament = this.mainControl.getTournament();
		groups = tournament.getGruppe();
		gruppenAnzahl = tournament.getAnzahlGruppen();
		tabAnzeigeView2 = this.mainControl.getTabbedPaneViewArray();
		init();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		init();
		tabAnzeigeView2 = this.mainControl.getTabbedPaneViewArray();
		int max;
		int anzahl;
		for (int index = 0; index < gruppenAnzahl; index++) {
			max = spielerAnzahl[index];
			anzahl = max * (max - 1) / 2;

			for (int i = 0; i < anzahl; i++) {

				if (arg0.getSource().equals(changeColorButton[index][i])) {
					changeColor(index, i);
					changedGames.add(groups[index].getPartien()[i]);
					changedGroups[index][NaviControl.PAARUNGSTABELLE] = NaviControl.STANDARD;

					pairingsView[index].getWeissSpieler()[i].setText("");
					pairingsView[index].getWeissSpieler()[i]
							.setText(Messages.getString("RundenEingabeFormularControl.8")
									+ groups[index].getPartien()[i].getSpielerWeiss().getName() + " - ");
					pairingsView[index].getSchwarzSpieler()[i].setText("");
					pairingsView[index].getSchwarzSpieler()[i]
							.setText(Messages.getString("RundenEingabeFormularControl.9")
									+ groups[index].getPartien()[i].getSpielerSchwarz().getName());

				}

				if (arg0.getSource().equals(roundNumbers[index][i])) {
					changeWerte(index, i);
					changedGames.add(groups[index].getPartien()[i]);
					changedGroups[index][NaviControl.PAARUNGSTABELLE] = NaviControl.SORTIEREN;

				}
				pairingsView[index].getStatusLabel().setText(new Integer(changedGames.size()).toString());

			}

		}

	}

	private void changeColor(int index, int nummer) {
		Player tauscheFarbe1;
		Player tauscheFarbe2;
		games = groups[index].getPartien();
		tauscheFarbe1 = games[nummer].getSpielerWeiss();
		tauscheFarbe2 = games[nummer].getSpielerSchwarz();
		games[nummer].setSpielerWeiss(tauscheFarbe2);
		games[nummer].setSpielerSchwarz(tauscheFarbe1);
		String datum = "";
		int runde = 0;

		try {
			runde = pruefeObZahlKleinerEinsIst(
					Integer.parseInt((String) pairingsView[index].getRundenNummer()[nummer].getSelectedItem()));

			// DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy",
			// Locale.getDefault());
			// datum = "";
			// Date date
			// if (Locale.getDefault().equals(Locale.US)) {
			// pairingsView[index].getDatum()[nummer].setDateFormatString("yyyy/mm/dd");
			// }
			// if (Locale.getDefault().equals(Locale.GERMANY)) {
			// pairingsView[index].getDatum()[nummer].setDateFormatString("dd.mm.yyyy");
			// }
			EventDate eventDate = new EventDate(pairingsView[index].getDatum()[nummer].getDate());

			// datum = eventDate.getDateString();
			// date = eventDate.getDateString();;
			datum = eventDate.getDateString();

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("RundenEingabeFormularControl.3")); //$NON-NLS-1$

		} catch (ZahlKleinerAlsN e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("RundenEingabeFormularControl.4")); //$NON-NLS-1$

		} catch (NullPointerException e2) {
			datum = "";
		}
		games[nummer].setSpielDatum(datum);
		games[nummer].setRunde(runde);
	}

	public void changeWerte(int index, int nummer) {

		String datum;
		games = groups[index].getPartien();
		int runde;
		datum = "";
		try {
			// if (Locale.getDefault().equals(Locale.US)) {
			// pairingsView[index].getDatum()[nummer].setDateFormatString("yyyy/mm/dd");
			// }
			// if (Locale.getDefault().equals(Locale.GERMANY)) {
			// pairingsView[index].getDatum()[nummer].setDateFormatString("dd.mm.yyyy");
			// }
			EventDate eventDate = new EventDate(pairingsView[index].getDatum()[nummer].getDate());

			datum = eventDate.getDateString();

			runde = pruefeObZahlKleinerEinsIst(
					Integer.parseInt((String) pairingsView[index].getRundenNummer()[nummer].getSelectedItem()));
			games[nummer].setSpielDatum(datum);
			games[nummer].setRunde(runde);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("RundenEingabeFormularControl.1")); //$NON-NLS-1$

		} catch (ZahlKleinerAlsN e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("RundenEingabeFormularControl.2")); //$NON-NLS-1$

		}

	}

	public void changeDate(int index, int nummer) {

		String datum;
		games = groups[index].getPartien();
		// int runde;
		datum = "";
		try {
			// if (Locale.getDefault().equals(Locale.US)) {
			// pairingsView[index].getDatum()[nummer].setDateFormatString("yyyy/mm/dd");
			// }
			// if (Locale.getDefault().equals(Locale.GERMANY)) {
			// pairingsView[index].getDatum()[nummer].setDateFormatString("dd.mm.yyyy");
			// }
			EventDate eventDate = new EventDate(pairingsView[index].getDatum()[nummer].getDate());

			datum = eventDate.getDateString();

			// runde = pruefeObZahlKleinerEinsIst(
			// Integer.parseInt((String)
			// pairingsView[index].getRundenNummer()[nummer].getSelectedItem()));
			games[nummer].setSpielDatum(datum);
			// games[nummer].setRunde(runde);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("RundenEingabeFormularControl.1")); //$NON-NLS-1$

		}

	}

	private Boolean checkIfReadyToSave() {
		Boolean readyToSave = false;
		for (int i = 0; i < mainControl.getTournament().getAnzahlGruppen(); i++) {

			if (mainControl.getNewTournamentPlayerInputControl().getReadyToSave()[i] == true) {
				readyToSave = true;
			} else {
				return false;
			}
		}
		return readyToSave;
	}

	public Boolean checkNewTurnier() {
		Boolean ready = true;
		if (mainControl.getNewTournamentPlayerInputControl() != null) {
			for (int i = 0; i < mainControl.getTournament().getAnzahlGruppen(); i++) {
				if (mainControl.getNewTournamentPlayerInputControl().getReadyToSave()[i] == false) {
					ready = false;
				}
			}
		}
		return ready;
	}

	public int[][] getChangedGroups() {
		return changedGroups;
	}

	public ArrayList<Game> getChangedPartien() {
		return changedGames;
	}

	public Boolean[] getNeuesTurnier() {
		return neuesTurnier;
	}

	@SuppressWarnings("unchecked")
	public void init() {

		if (pairingsView == null) {
			if (mainControl.getCrossTableControl() == null) {
				meetingTable = new MeetingTable[gruppenAnzahl];
				this.mainControl.setMeetingTable(meetingTable);
			} else {
				meetingTable = this.mainControl.getMeetingTable();
			}
			pairingsView = new PairingsView[gruppenAnzahl];
			neuesTurnier = new Boolean[gruppenAnzahl];
			changedGroups = new int[gruppenAnzahl][3];
			for (int i = 0; i < gruppenAnzahl; i++) {
				pairingsView[i] = new PairingsView(groups[i].getSpielerAnzahl());
				neuesTurnier[i] = false;
				for (int x = 0; x < 3; x++) {

					if (checkNewTurnier() == true) {
						changedGroups[i][x] = 1;
					} else {
						changedGroups[i][x] = 0;
					}
				}
			}
			this.mainControl.setPairingsView(pairingsView);
			changeColorButton = new JButton[gruppenAnzahl][];
			datePicker = new DateChooserPanel[gruppenAnzahl][];
			roundNumbers = new JComboBox[gruppenAnzahl][];
			spielerAnzahl = new int[gruppenAnzahl];
			pairingsTables = new PairingsTables[gruppenAnzahl];
			if (this.mainControl.getChangedGames() == null) {
				changedGames = new ArrayList<Game>();
				this.mainControl.setChangedGames(changedGames);
			} else {
				changedGames = this.mainControl.getChangedGames();

			}
			for (int i = 0; i < gruppenAnzahl; i++) {
				pairingsView[i].getStatusLabel().setText(new Integer(changedGames.size()).toString());
			}

		}

	}

	@SuppressWarnings("unchecked")
	public void makeNewFormular(int index) {

		meetingTable = mainControl.getMeetingTable();

		String[][] terminMatrix = meetingTable[index].getTabellenMatrix();

		pairingsView[index].makeZeilen(terminMatrix);

		changeColorButton[index] = pairingsView[index].getChangeColor();
		datePicker[index] = pairingsView[index].getDatum();
		roundNumbers[index] = pairingsView[index].getRundenNummer();
		for (int i = 0; i < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2); i++) {

			changeColorButton[index][i].addActionListener(this);
			datePicker[index][i].getJDateChooser().addPropertyChangeListener(this);
			roundNumbers[index][i].addActionListener(this);
		}

		if (tabAnzeigeView2[index].getTabbedPane().getComponentCount() == 2) {
			tabAnzeigeView2[index].getTabbedPane().insertTab(Messages.getString("RundenEingabeFormularControl.5"), //$NON-NLS-1$
					pairingsIcon, pairingsView[index], null, 2);
		} else {
			tabAnzeigeView2[index].getTabbedPane().setComponentAt(2, pairingsView[index]);
			tabAnzeigeView2[index].getTabbedPane().setIconAt(2, pairingsIcon);
		}
		if (this.mainControl.getChangedGames() == null) {
			changedGames = new ArrayList<Game>();
			this.mainControl.setChangedGames(changedGames);
		} else {
			changedGames = this.mainControl.getChangedGames();

		}

		pairingsView[index].getStatusLabel().setText(new Integer(changedGames.size()).toString());

		pairingsView[index].updateUI();

	}

	public void makeRundenEditView(int index) {
		neuesTurnier[index] = true;
		games = groups[index].getPartien();

		spielerAnzahl[index] = groups[index].getSpielerAnzahl();

		tabAnzeigeView2 = this.mainControl.getTabbedPaneViewArray();
		if (tabAnzeigeView2 != null) {
			if (tabAnzeigeView2[index].getTabbedPane().getTabCount() < 3) {
				tabAnzeigeView2[index].getTabbedPane().insertTab(Messages.getString("RundenEingabeFormularControl.6"), //$NON-NLS-1$
						null, pairingsView[index], null, 2);
			} else {

				tabAnzeigeView2[index].getTabbedPane().setComponentAt(2, pairingsView[index]);
			}
		}
		makeNewFormular(index);
	}

	public void makeTerminTabelle(int index) {
		init();
		neuesTurnier[index] = true;
		pairingsTables[index] = new PairingsTables(groups[index]);
		groups[index] = pairingsTables[index].getGruppe();
		spielerAnzahl[index] = groups[index].getSpielerAnzahl();

		CrossTableControl turnierTabelleControl = new CrossTableControl(mainControl);
		MeetingTableControl terminTabelleControl = new MeetingTableControl(mainControl);
		mainControl.setCrossTableControl(turnierTabelleControl);
		mainControl.setMeetingTableControl(terminTabelleControl);
		turnierTabelleControl.makeSimpleTableView(index);

		terminTabelleControl.makeSimpleTableView(index);
		games = groups[index].getPartien();

		spielerAnzahl[index] = groups[index].getSpielerAnzahl();

		tabAnzeigeView2 = this.mainControl.getTabbedPaneViewArray();

		mainControl.getNaviView().setTabellenname(
				Messages.getString("RundenEingabeFormularControl.7") + mainControl.getTournament().getTurnierName()); //$NON-NLS-1$

		Boolean readyToSave = checkIfReadyToSave();
		if (readyToSave == true) {
			saveAndReloadTurnier();

		} else {
			Boolean nextTab = true;
			for (int i = 0; i < mainControl.getTournament().getAnzahlGruppen(); i++) {
				if (nextTab == true && mainControl.getNewTournamentPlayerInputControl().getReadyToSave()[i] == false) {
					nextTab = false;

					this.mainControl.getTabbedPaneView().getTabbedPane().setSelectedIndex(i);
					tabAnzeigeView2[index].getTabbedPane().removeAll();
					tabAnzeigeView2[index].getTabbedPane().addTab("Info", new WaitForAllGroupsView());

					tabAnzeigeView2[index].getTabbedPane().updateUI();
				}
			}
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (arg0.getPropertyName().equals("date")) {

			init();
			tabAnzeigeView2 = this.mainControl.getTabbedPaneViewArray();
			int max;
			int anzahl;
			for (int index = 0; index < gruppenAnzahl; index++) {
				max = spielerAnzahl[index];
				anzahl = max * (max - 1) / 2;

				for (int i = 0; i < anzahl; i++) {
					if (arg0.getSource().equals(datePicker[index][i].getJDateChooser())) {

						changeDate(index, i);
						changedGames.add(groups[index].getPartien()[i]);
						changedGroups[index][NaviControl.PAARUNGSTABELLE] = NaviControl.STANDARD;
						for (int y = i; y < (pairingsView[index].getTabbedPane().getSelectedIndex() + 1) * max
								/ 2; y++) {

							if ((y != i) && (pairingsView[index].getDatum()[y].getDate() == null)) {
								pairingsView[index].getDatum()[y].setDate(pairingsView[index].getDatum()[i].getDate());
							}
						}
						pairingsView[index].getStatusLabel().setText(new Integer(changedGames.size()).toString());
//						pairingsView[index].getStatusLabel().setBackground(Color.ORANGE);
					}
				}

			}
		}
	}

	private Boolean saveAndReloadTurnier() {

		Boolean ok = true;
		try {
			ok = this.mainControl.getSaveTournamentControl().saveChangedPartien();
		} catch (SQLException e) {
			ok = false;
			ExceptionHandler eh = new ExceptionHandler(mainControl);
			eh.fileSQLError(e.getMessage());
		}
		return ok;
	}

	public void setChangedGroups(int[][] changedGroups) {
		this.changedGroups = changedGroups;
	}

	public void setChangedPartien(ArrayList<Game> changedPartien) {
		this.changedGames = changedPartien;
	}

	public void setNeuesTurnier(Boolean[] neuesTurnier) {
		this.neuesTurnier = neuesTurnier;
	}

}
