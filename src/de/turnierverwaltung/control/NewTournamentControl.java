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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.ZahlGroesserAlsN;
import de.turnierverwaltung.ZahlKleinerAlsN;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.ButtonTabComponent;
import de.turnierverwaltung.view.NewTournamentView;

public class NewTournamentControl implements ActionListener {

	private int selectIndex;
	private MainControl mainControl;
	private NewTournamentView turnierView;
	private JButton turnierOkButton;
	private JTabbedPane hauptPanel;
	private String turnierName;
	private String startDatum;
	private String endDatum;
	private int gruppenAnzahl;
	private ImageIcon turnierIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png"))); //$NON-NLS-1$

	private Tournament turnier;
	private ButtonTabComponent buttonTabComponent;

	public NewTournamentControl(MainControl mainControl) {
		this.mainControl = mainControl;
		this.mainControl.setGruppenControl(null);
//		this.mainControl.setSpielerAnzahlControl(null);
		this.mainControl.setSpielerEingabeControl(null);
		this.mainControl.setPairingsControl(null);
		this.mainControl.setTurnierTabelleControl(null);
		hauptPanel = this.mainControl.getHauptPanel();
		this.mainControl.setTurnierView(new NewTournamentView());
		this.turnierView = this.mainControl.getTurnierView();
		this.turnierView.setVisible(true);
		turnierOkButton = this.mainControl.getTurnierView().getOkButton();
		turnierOkButton.addActionListener(this);
		this.mainControl.setNeuesTurnier(true);
		buttonTabComponent = mainControl.getButtonTabComponent();
		if (mainControl.getTurnier() != null) {
			if (hauptPanel.getTabCount() - 1 == TournamentConstants.TAB_ACTIVE_TOURNAMENT) {
				hauptPanel.remove(TournamentConstants.TAB_ACTIVE_TOURNAMENT);

			}

		}

		hauptPanel.addTab(Messages.getString("TurnierControl.1"), turnierIcon, this.turnierView); //$NON-NLS-1$ \
		hauptPanel.setTabComponentAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, buttonTabComponent);
		selectIndex = hauptPanel.getTabCount() - 1;
		hauptPanel.setSelectedIndex(selectIndex);

		Boolean onlyTables = this.mainControl.getPropertiesControl().getOnlyTables();
		Boolean noDWZCalc = this.mainControl.getPropertiesControl().getNoDWZ();

		Boolean noFolgeDWZCalc = mainControl.getPropertiesControl().getNoFolgeDWZ();

		turnier = new Tournament(onlyTables, noDWZCalc, noFolgeDWZCalc);
		this.mainControl.setTurnier(turnier);
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
		this.mainControl.getPairingsMenuActionControl().setPairingIsActive(false);
		this.turnierView.getTurnierNameTextField().grabFocus();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == turnierOkButton) {
			turnierOkButton();

		}

	}

	private static int pruefeObZahlKleinerEinsIst(int zahl) throws ZahlKleinerAlsN, ZahlGroesserAlsN {
		if (zahl <= 0) {
			throw new ZahlKleinerAlsN();
		}
		if (zahl > 15) {
			throw new ZahlGroesserAlsN();
		}
		return zahl;
	}

	public String getEndDatum() {
		return endDatum;
	}

	public int getGruppenAnzahl() {
		return gruppenAnzahl;
	}

	public String getStartDatum() {
		return startDatum;
	}

	public Tournament getTurnier() {
		return turnier;
	}

	public String getTurnierName() {
		return turnierName;
	}

	public JButton getTurnierOkButton() {
		return turnierOkButton;
	}

	private void makeTurnier() {

		turnier.setTurnierName(turnierName);
		turnier.setStartDatum(startDatum);
		turnier.setEndDatum(endDatum);
		turnier.setAnzahlGruppen(gruppenAnzahl);
		this.mainControl.setTurnier(turnier);
		hauptPanel.setTitleAt(selectIndex, turnier.getTurnierName());
	}

	public void setEndDatum(String endDatum) {
		this.endDatum = endDatum;
	}

	public void setGruppenAnzahl(int gruppenAnzahl) {
		this.gruppenAnzahl = gruppenAnzahl;
	}

	public void setStartDatum(String startDatum) {
		this.startDatum = startDatum;
	}

	public void setTurnier(Tournament turnier) {
		this.turnier = turnier;
	}

	public void setTurnierName(String turnierName) {
		this.turnierName = turnierName;
	}

	public void setTurnierOkButton(JButton turnierOkButton) {
		this.turnierOkButton = turnierOkButton;
	}

	private void turnierOkButton() {
		gruppenAnzahl = 0;
		turnierName = turnierView.getTurnierNameTextField().getText();

		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

		try {
			startDatum = formatter.format(turnierView.getStartDatumTextField());
			endDatum = formatter.format(turnierView.getEndDatumTextField());
		} catch (NullPointerException e2) {
			startDatum = "";
			endDatum = "";
		}

		try {

			gruppenAnzahl = pruefeObZahlKleinerEinsIst(new Integer(turnierView.getGruppenAnzahlTextField().getValue()));

			if (turnierName.length() > 0 && startDatum.length() > 0 && endDatum.length() > 0 && gruppenAnzahl > 0) {
				makeTurnier();

				this.mainControl.setGruppenControl(new NewTournamentGroupsControl(this.mainControl));

			}

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("TurnierControl.2")); //$NON-NLS-1$
			turnierView.getGruppenAnzahlTextField().resetValue(); // $NON-NLS-1$
			turnierView.getGruppenAnzahlTextField().grabFocus();
		} catch (ZahlKleinerAlsN e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("TurnierControl.4")); //$NON-NLS-1$
			turnierView.getGruppenAnzahlTextField().resetValue(); // $NON-NLS-1$
			turnierView.getGruppenAnzahlTextField().grabFocus();
		} catch (ZahlGroesserAlsN e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("TurnierControl.6")); //$NON-NLS-1$
			turnierView.getGruppenAnzahlTextField().resetValue(); // $NON-NLS-1$
			turnierView.getGruppenAnzahlTextField().grabFocus();
		}

	}
}
