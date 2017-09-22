package de.turnierverwaltung.control;

import java.awt.Color;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import de.turnierverwaltung.ZahlKleinerAlsN;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.MeetingTable;
import de.turnierverwaltung.model.PairingsTables;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.view.DateChooserPanel;
import de.turnierverwaltung.view.PairingsView;
import de.turnierverwaltung.view.TabbedPaneView;
import de.turnierverwaltung.view.WaitForAllGroupsView;

public class PairingsControl implements ActionListener, PropertyChangeListener {

	private static int pruefeObZahlKleinerEinsIst(int zahl) throws ZahlKleinerAlsN {
		if (zahl <= 0) {
			throw new ZahlKleinerAlsN();
		}
		return zahl;
	}

	private MainControl mainControl;
	private Tournament turnier;
	private Group[] gruppe;
	private Game[] partien;
	private MeetingTable terminTabelle[];
	private int gruppenAnzahl;
	private PairingsView[] rundenEingabeFormularView;
	private JButton changeColor[][];
	private int[] spielerAnzahl;
	private PairingsTables[] paarungsTafeln;
	private TabbedPaneView[] tabAnzeigeView2;
	private Boolean[] neuesTurnier;
	private ArrayList<Game> changedPartien;
	private DateChooserPanel[][] datePicker;
	private JComboBox<String>[][] rundenNummer;
	private int[][] changedGroups;
	private ImageIcon paarungenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/media-playlist-shuffle-3.png"))); //$NON-NLS-1$

	public PairingsControl(MainControl mainControl) {
		this.mainControl = mainControl;

		turnier = this.mainControl.getTurnier();
		gruppe = turnier.getGruppe();
		gruppenAnzahl = turnier.getAnzahlGruppen();
		tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		init();
	}

	@SuppressWarnings("unchecked")
	public void init() {

		if (rundenEingabeFormularView == null) {
			if (mainControl.getTurnierTabelleControl() == null) {
				terminTabelle = new MeetingTable[gruppenAnzahl];
				this.mainControl.setTerminTabelle(terminTabelle);
			} else {
				terminTabelle = this.mainControl.getTerminTabelle();
			}
			rundenEingabeFormularView = new PairingsView[gruppenAnzahl];
			neuesTurnier = new Boolean[gruppenAnzahl];
			changedGroups = new int[gruppenAnzahl][3];
			for (int i = 0; i < gruppenAnzahl; i++) {
				rundenEingabeFormularView[i] = new PairingsView(gruppe[i].getSpielerAnzahl());
				neuesTurnier[i] = false;
				for (int x = 0; x < 3; x++) {

					if (checkNewTurnier() == true) {
						changedGroups[i][x] = 1;
					} else {
						changedGroups[i][x] = 0;
					}
				}
			}
			this.mainControl.setRundenEingabeFormularView(rundenEingabeFormularView);
			changeColor = new JButton[gruppenAnzahl][];
			datePicker = new DateChooserPanel[gruppenAnzahl][];
			rundenNummer = new JComboBox[gruppenAnzahl][];
			spielerAnzahl = new int[gruppenAnzahl];
			paarungsTafeln = new PairingsTables[gruppenAnzahl];
			if (this.mainControl.getChangedPartien() == null) {
				changedPartien = new ArrayList<Game>();
				this.mainControl.setChangedPartien(changedPartien);
			} else {
				changedPartien = this.mainControl.getChangedPartien();

			}
			for (int i = 0; i < gruppenAnzahl; i++) {
				rundenEingabeFormularView[i].getStatusLabel().setText(new Integer(changedPartien.size()).toString());
			}

		}

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		init();
		tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		int max;
		int anzahl;
		for (int index = 0; index < gruppenAnzahl; index++) {
			max = spielerAnzahl[index];
			anzahl = max * (max - 1) / 2;

			for (int i = 0; i < anzahl; i++) {

				if (arg0.getSource() == changeColor[index][i]) {
					changeColor(index, i);
					changedPartien.add(gruppe[index].getPartien()[i]);
					changedGroups[index][NaviControl.PAARUNGSTABELLE] = NaviControl.STANDARD;

					rundenEingabeFormularView[index].getWeissSpieler()[i].setText("");
					rundenEingabeFormularView[index].getWeissSpieler()[i]
							.setText(Messages.getString("RundenEingabeFormularControl.8")
									+ gruppe[index].getPartien()[i].getSpielerWeiss().getName() + " - ");
					rundenEingabeFormularView[index].getSchwarzSpieler()[i].setText("");
					rundenEingabeFormularView[index].getSchwarzSpieler()[i]
							.setText(Messages.getString("RundenEingabeFormularControl.9")
									+ gruppe[index].getPartien()[i].getSpielerSchwarz().getName());

				}

				if (arg0.getSource() == rundenNummer[index][i]) {
					changeWerte(index, i);
					changedPartien.add(gruppe[index].getPartien()[i]);
					changedGroups[index][NaviControl.PAARUNGSTABELLE] = NaviControl.SORTIEREN;

				}
				rundenEingabeFormularView[index].getStatusLabel()
						.setText(new Integer(changedPartien.size()).toString());

			}

		}

	}

	public void changeWerte(int index, int nummer) {

		String datum;
		partien = gruppe[index].getPartien();
		int runde;
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
		datum = "";
		Date date;
		try {

			try {
				date = rundenEingabeFormularView[index].getDatum()[nummer].getDate();
				datum = formatter.format(date);
			} catch (NullPointerException e2) {
				datum = "";
			}

			runde = pruefeObZahlKleinerEinsIst(Integer
					.parseInt((String) rundenEingabeFormularView[index].getRundenNummer()[nummer].getSelectedItem()));
			partien[nummer].setSpielDatum(datum);
			partien[nummer].setRunde(runde);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("RundenEingabeFormularControl.1")); //$NON-NLS-1$

		} catch (ZahlKleinerAlsN e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("RundenEingabeFormularControl.2")); //$NON-NLS-1$

		}

	}

	private void changeColor(int index, int nummer) {
		Player tauscheFarbe1;
		Player tauscheFarbe2;
		partien = gruppe[index].getPartien();
		tauscheFarbe1 = partien[nummer].getSpielerWeiss();
		tauscheFarbe2 = partien[nummer].getSpielerSchwarz();
		partien[nummer].setSpielerWeiss(tauscheFarbe2);
		partien[nummer].setSpielerSchwarz(tauscheFarbe1);
		String datum = "";
		int runde = 0;

		try {
			runde = pruefeObZahlKleinerEinsIst(Integer
					.parseInt((String) rundenEingabeFormularView[index].getRundenNummer()[nummer].getSelectedItem()));

			DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
			datum = "";
			Date date;

			date = rundenEingabeFormularView[index].getDatum()[nummer].getDate();
			datum = formatter.format(date);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("RundenEingabeFormularControl.3")); //$NON-NLS-1$

		} catch (ZahlKleinerAlsN e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("RundenEingabeFormularControl.4")); //$NON-NLS-1$

		} catch (NullPointerException e2) {
			datum = "";
		}
		partien[nummer].setSpielDatum(datum);
		partien[nummer].setRunde(runde);
	}

	@SuppressWarnings("unchecked")
	public void makeNewFormular(int index) {

		// PropertiesControl ppC = mainControl.getPropertiesControl();
		// String roundColumnName = ppC.getTableComumnRound();
		// String whiteColumnName = ppC.getTableComumnWhite();
		// String blackColumnName = ppC.getTableComumnBlack();
		// String resultColumnName = ppC.getTableComumnResult();
		// String meetingColumnName = ppC.getTableComumnMeeting();
		terminTabelle = mainControl.getTerminTabelle();

		// terminTabelle[index] = new MeetingTable(turnier, gruppe[index],
		// roundColumnName, whiteColumnName,
		// blackColumnName, resultColumnName, meetingColumnName);
		// gruppe[index].setTeminTabelle(terminTabelle[index]);
		// mainControl.setTerminTabelle(terminTabelle);
		String[][] terminMatrix = terminTabelle[index].getTabellenMatrix();
		// rundenEingabeFormularView[index] = new PairingsView(spielerAnzahl[index]);
		// this.mainControl.setRundenEingabeFormularView(rundenEingabeFormularView);

		rundenEingabeFormularView[index].makeZeilen(terminMatrix);

		changeColor[index] = rundenEingabeFormularView[index].getChangeColor();
		datePicker[index] = rundenEingabeFormularView[index].getDatum();
		rundenNummer[index] = rundenEingabeFormularView[index].getRundenNummer();
		for (int i = 0; i < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2); i++) {

			changeColor[index][i].addActionListener(this);
			datePicker[index][i].getJDateChooser().addPropertyChangeListener(this);
			rundenNummer[index][i].addActionListener(this);
		}

		if (tabAnzeigeView2[index].getTabbedPane().getComponentCount() == 2) {
			tabAnzeigeView2[index].getTabbedPane().insertTab(Messages.getString("RundenEingabeFormularControl.5"), //$NON-NLS-1$
					paarungenIcon, rundenEingabeFormularView[index], null, 2);
		} else {
			tabAnzeigeView2[index].getTabbedPane().setComponentAt(2, rundenEingabeFormularView[index]);
			tabAnzeigeView2[index].getTabbedPane().setIconAt(2, paarungenIcon);
		}
		if (this.mainControl.getChangedPartien() == null) {
			changedPartien = new ArrayList<Game>();
			this.mainControl.setChangedPartien(changedPartien);
		} else {
			changedPartien = this.mainControl.getChangedPartien();

		}

		rundenEingabeFormularView[index].getStatusLabel().setText(new Integer(changedPartien.size()).toString());

		rundenEingabeFormularView[index].updateUI();

	}

	public void makeRundenEditView(int index) {
		neuesTurnier[index] = true;
		partien = gruppe[index].getPartien();

		spielerAnzahl[index] = gruppe[index].getSpielerAnzahl();

		tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		if (tabAnzeigeView2 != null) {
			if (tabAnzeigeView2[index].getTabbedPane().getTabCount() < 3) {
				tabAnzeigeView2[index].getTabbedPane().insertTab(Messages.getString("RundenEingabeFormularControl.6"), //$NON-NLS-1$
						null, rundenEingabeFormularView[index], null, 2);
			} else {

				tabAnzeigeView2[index].getTabbedPane().setComponentAt(2, rundenEingabeFormularView[index]);
			}
		}
		makeNewFormular(index);
	}

	public void makeTerminTabelle(int index) {
		init();
		neuesTurnier[index] = true;
		paarungsTafeln[index] = new PairingsTables(gruppe[index]);
		gruppe[index] = paarungsTafeln[index].getGruppe();
		spielerAnzahl[index] = gruppe[index].getSpielerAnzahl();

		CrossTableControl turnierTabelleControl = new CrossTableControl(mainControl);
		MeetingTableControl terminTabelleControl = new MeetingTableControl(mainControl);
		mainControl.setTurnierTabelleControl(turnierTabelleControl);
		mainControl.setTerminTabelleControl(terminTabelleControl);
		turnierTabelleControl.makeSimpleTableView(index);

		terminTabelleControl.makeSimpleTableView(index);
		partien = gruppe[index].getPartien();

		spielerAnzahl[index] = gruppe[index].getSpielerAnzahl();

		tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();

		mainControl.getNaviView().setTabellenname(
				Messages.getString("RundenEingabeFormularControl.7") + mainControl.getTurnier().getTurnierName()); //$NON-NLS-1$

		Boolean readyToSave = checkIfReadyToSave();
		if (readyToSave == true) {
			saveAndReloadTurnier();

		} else {
			Boolean nextTab = true;
			for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {
				if (nextTab == true && mainControl.getSpielerEingabeControl().getReadyToSave()[i] == false) {
					nextTab = false;

					this.mainControl.getTabAnzeigeView().getTabbedPane().setSelectedIndex(i);
					tabAnzeigeView2[index].getTabbedPane().removeAll();
					tabAnzeigeView2[index].getTabbedPane().addTab("Info", new WaitForAllGroupsView());

					tabAnzeigeView2[index].getTabbedPane().updateUI();
				}
			}
		}

	}

	private Boolean checkIfReadyToSave() {
		Boolean readyToSave = false;
		for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {

			if (mainControl.getSpielerEingabeControl().getReadyToSave()[i] == true) {
				readyToSave = true;
			} else {
				return false;
			}
		}
		return readyToSave;
	}

	private Boolean saveAndReloadTurnier() {

		Boolean ok = true;
		try {
			ok = this.mainControl.getSaveTurnierControl().saveChangedPartien();
		} catch (SQLException e) {
			ok = false;
			e.printStackTrace();
		}
		return ok;
	}

	public Boolean[] getNeuesTurnier() {
		return neuesTurnier;
	}

	public void setNeuesTurnier(Boolean[] neuesTurnier) {
		this.neuesTurnier = neuesTurnier;
	}

	public int[][] getChangedGroups() {
		return changedGroups;
	}

	public void setChangedGroups(int[][] changedGroups) {
		this.changedGroups = changedGroups;
	}

	public ArrayList<Game> getChangedPartien() {
		return changedPartien;
	}

	public void setChangedPartien(ArrayList<Game> changedPartien) {
		this.changedPartien = changedPartien;
	}

	public Boolean checkNewTurnier() {
		Boolean ready = true;
		if (mainControl.getSpielerEingabeControl() != null) {
			for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {
				if (mainControl.getSpielerEingabeControl().getReadyToSave()[i] == false) {
					ready = false;
				}
			}
		}
		return ready;
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (arg0.getPropertyName().equals("date")) {

			init();
			tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
			int max;
			int anzahl;
			for (int index = 0; index < gruppenAnzahl; index++) {
				max = spielerAnzahl[index];
				anzahl = max * (max - 1) / 2;

				for (int i = 0; i < anzahl; i++) {
					if (arg0.getSource() == datePicker[index][i].getJDateChooser()) {

						changeWerte(index, i);
						changedPartien.add(gruppe[index].getPartien()[i]);
						changedGroups[index][NaviControl.PAARUNGSTABELLE] = NaviControl.STANDARD;
						for (int y = i; y < (rundenEingabeFormularView[index].getTabbedPane().getSelectedIndex() + 1)
								* max / 2; y++) {

							if ((y != i) && (rundenEingabeFormularView[index].getDatum()[y].getDate() == null)) {
								rundenEingabeFormularView[index].getDatum()[y]
										.setDate(rundenEingabeFormularView[index].getDatum()[i].getDate());
							}
						}
						rundenEingabeFormularView[index].getStatusLabel()
								.setText(new Integer(changedPartien.size()).toString());
						rundenEingabeFormularView[index].getStatusLabel().setBackground(Color.ORANGE);
					}
				}

			}
		}
	}

}
