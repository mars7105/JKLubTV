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

import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.roundrobin.MeetingTable;
import de.turnierverwaltung.model.roundrobin.MeetingTableModel;
import de.turnierverwaltung.view.MeetingTableView;
import de.turnierverwaltung.view.CrossTableView;
import de.turnierverwaltung.view.TabbedPaneView;

public class MeetingTableControl {

	private MainControl mainControl;
	private MeetingTable[] terminTabelle;
	private TabbedPaneView[] tabAnzeigeView2;
	private MeetingTableView[] simpleTableView;
	private CrossTableView[] simpleTurnierTabelleView;
	private JTabbedPane hauptPanel;
	private Tournament turnier;
	private MyTableModelListener tml[];
	private ArrayList<Game> changedPartien;
	private ImageIcon terminTabelleIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/accessories-date.png"))); //$NON-NLS-1$

	/**
	 * 
	 * @param mainControl
	 */
	public MeetingTableControl(MainControl mainControl) {
		this.mainControl = mainControl;
		this.turnier = mainControl.getTurnier();
		this.tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		int anzahlGruppen = mainControl.getTurnier().getAnzahlGruppen();
		simpleTableView = new MeetingTableView[anzahlGruppen];
		this.mainControl.setSimpleTerminTabelleView(simpleTableView);
		this.terminTabelle = new MeetingTable[anzahlGruppen];
		this.mainControl.setTerminTabelle(terminTabelle);
		hauptPanel = mainControl.getHauptPanel();
		tml = new MyTableModelListener[anzahlGruppen];
		if (this.mainControl.getChangedPartien() == null) {
			changedPartien = new ArrayList<Game>();
			this.mainControl.setChangedPartien(changedPartien);
		} else {
			changedPartien = this.mainControl.getChangedPartien();

		}
	}

	/**
	 * 
	 * @param gruppenNummer
	 */
	public void makeSimpleTableView(int gruppenNummer) {
		PropertiesControl ppC = mainControl.getPropertiesControl();
		String roundColumnName = ppC.getTableComumnRound();
		String whiteColumnName = ppC.getTableComumnWhite();
		String blackColumnName = ppC.getTableComumnBlack();
		String resultColumnName = ppC.getTableComumnResult();
		String meetingColumnName = ppC.getTableComumnMeeting();
		this.terminTabelle[gruppenNummer] = new MeetingTable(turnier,
				mainControl.getTurnier().getGruppe()[gruppenNummer], roundColumnName, whiteColumnName, blackColumnName,
				resultColumnName, meetingColumnName);
		this.mainControl.setTerminTabelle(terminTabelle);
		simpleTableView[gruppenNummer] = new MeetingTableView(new MeetingTableModel(this.terminTabelle[gruppenNummer]));
		simpleTurnierTabelleView = mainControl.getSimpleTableView();
		mainControl.getTurnierTabelle();
		if (tml[gruppenNummer] == null) {
			int abstand = mainControl.getPropertiesControl().getTabellenAbstand();
			tml[gruppenNummer] = new MyTableModelListener(gruppenNummer, abstand);
		}

		simpleTableView[gruppenNummer].getTable().getModel().addTableModelListener(tml[gruppenNummer]);

		if (tabAnzeigeView2[gruppenNummer].getTabbedPane().getTabCount() == 1) {
			tabAnzeigeView2[gruppenNummer].getTabbedPane().insertTab(Messages.getString("TerminTabelleControl.1"), //$NON-NLS-1$
					terminTabelleIcon, simpleTableView[gruppenNummer], null, 1);
		} else if (tabAnzeigeView2[gruppenNummer].getTabbedPane().getTabCount() > 1) {

			tabAnzeigeView2[gruppenNummer].getTabbedPane().setComponentAt(1, simpleTableView[gruppenNummer]);
		}

		hauptPanel.updateUI();

	}

	public MeetingTable[] getTerminTabelle() {
		return terminTabelle;
	}

	public void setTerminTabelle(MeetingTable[] terminTabelle) {
		this.terminTabelle = terminTabelle;
	}

	public void updateStatus() {
		int anzahlGruppen = mainControl.getTurnier().getAnzahlGruppen();
		for (int i = 0; i < anzahlGruppen; i++) {
			simpleTableView[i].getStatusLabel().setText(new Integer(changedPartien.size()).toString());
		}

	}

	/**
	 * 
	 * @author mars
	 *
	 */
	class MyTableModelListener implements TableModelListener {
		private int gruppenNummer;
		private int abstand;

		public MyTableModelListener(int gruppenNummer, int abstand) {
			this.gruppenNummer = gruppenNummer;
			this.abstand = abstand;
		}

		@Override
		public void tableChanged(TableModelEvent e) {
			// mainControl.getRundenEingabeFormularControl().getChangedGroups()[gruppenNummer][NaviController.TERMINTABELLE]
			// = NaviController.STANDARD;

			int row = e.getFirstRow();
			int col = e.getColumn();
			if (col == 3) {
				String ergebniss = (String) simpleTableView[gruppenNummer].getTable().getModel().getValueAt(row, col);
				String spielerWeiss = (String) simpleTableView[gruppenNummer].getTable().getModel().getValueAt(row, 1);
				String spielerSchwarz = (String) simpleTableView[gruppenNummer].getTable().getModel().getValueAt(row,
						2);

				String ergebnissSet = TournamentConstants.KEIN_ERGEBNIS;

				if (ergebniss == TournamentConstants.KEIN_ERGEBNIS) {
					ergebnissSet = TournamentConstants.KEIN_ERGEBNIS;
				}
				if (ergebniss == TournamentConstants.PARTIE_GEWINN_SCHWARZ) {
					ergebnissSet = TournamentConstants.VERLUST;
				}
				if (ergebniss == TournamentConstants.PARTIE_REMIS) {
					ergebnissSet = TournamentConstants.REMIS;
				}
				if (ergebniss == TournamentConstants.PARTIE_GEWINN_WEISS) {
					ergebnissSet = TournamentConstants.GEWINN;
				}
				if (ergebniss == TournamentConstants.PARTIE_GEWINN_KAMPFLOS_SCHWARZ) {
					ergebnissSet = TournamentConstants.VERLUST_KAMPFLOS;
				}
				if (ergebniss == TournamentConstants.PARTIE_GEWINN_KAMPFLOS_WEISS) {
					ergebnissSet = TournamentConstants.GEWINN_KAMPFLOS;
				}
				for (int i = 0; i < simpleTurnierTabelleView[gruppenNummer].getTable().getModel().getRowCount(); i++) {
					if (simpleTurnierTabelleView[gruppenNummer].getTable().getModel().getValueAt(i,
							0) == spielerWeiss) {
						for (int y = 0; y < simpleTurnierTabelleView[gruppenNummer].getTable().getModel()
								.getRowCount(); y++) {
							if (simpleTurnierTabelleView[gruppenNummer].getTable().getModel().getValueAt(y,
									0) == spielerSchwarz) {
								simpleTurnierTabelleView[gruppenNummer].getTable().getModel().setValueAt(ergebnissSet,
										i, y + abstand);
							}
						}

					}
				}
				mainControl.getTurnierTabelleControl().updateStatus();
				updateStatus();
			}
		}

	}

}
