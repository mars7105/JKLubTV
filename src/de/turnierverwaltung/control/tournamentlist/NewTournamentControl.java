package de.turnierverwaltung.control.tournamentlist;

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
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.tournamentlist.NewTournamentView;
import de.turnierverwaltung.view.tournamenttable.ButtonTabComponent;

public class NewTournamentControl implements ActionListener {

	private static int pruefeObZahlKleinerEinsIst(final int zahl) throws ZahlKleinerAlsN, ZahlGroesserAlsN {
		if (zahl <= 0) {
			throw new ZahlKleinerAlsN();
		}
		if (zahl > maxGroups) {
			throw new ZahlGroesserAlsN();
		}
		return zahl;
	}

	private final int selectIndex;
	private final MainControl mainControl;
	private final NewTournamentView turnierView;
	private JButton turnierOkButton;
	private final JTabbedPane hauptPanel;
	private String turnierName;
	private String startDatum;
	private String endDatum;
	private int gruppenAnzahl;
	private final static int maxGroups = TournamentConstants.MAX_GROUPS;

	private final ImageIcon turnierIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png"))); //$NON-NLS-1$
	private Tournament turnier;

	private final ButtonTabComponent buttonTabComponent;

	public NewTournamentControl(final MainControl mainControl) {
		this.mainControl = mainControl;
		this.mainControl.setNewTournamentGroupsControl(null);
		// this.mainControl.setSpielerAnzahlControl(null);
		this.mainControl.setNewTournamentPlayerIncludeControl(null);
		this.mainControl.setPairingsControl(null);
		this.mainControl.setCrossTableControl(null);
		hauptPanel = this.mainControl.getHauptPanel();
		this.mainControl.setNewTournamentView(new NewTournamentView());
		turnierView = this.mainControl.getNewTournamentView();
		turnierView.setVisible(true);
		turnierOkButton = this.mainControl.getNewTournamentView().getOkButton();
		turnierOkButton.addActionListener(this);
		this.mainControl.setNewTournament(true);
		buttonTabComponent = mainControl.getButtonTabComponent();
		if (mainControl.getTournament() != null) {
			if (hauptPanel.getTabCount() - 1 == TournamentConstants.TAB_ACTIVE_TOURNAMENT) {
				hauptPanel.remove(TournamentConstants.TAB_ACTIVE_TOURNAMENT);

			}

		}

		hauptPanel.addTab(Messages.getString("TurnierControl.1"), turnierIcon, turnierView); //$NON-NLS-1$ \
		hauptPanel.setTabComponentAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, buttonTabComponent);
		selectIndex = hauptPanel.getTabCount() - 1;
		hauptPanel.setSelectedIndex(selectIndex);

		final Boolean onlyTables = this.mainControl.getPropertiesControl().getOnlyTables();
		final Boolean noDWZCalc = this.mainControl.getPropertiesControl().getNoDWZ();

		final Boolean noFolgeDWZCalc = mainControl.getPropertiesControl().getNoFolgeDWZ();
		final Boolean noELOCalc = this.mainControl.getPropertiesControl().getNoELO();

		final Boolean noFolgeELOCalc = mainControl.getPropertiesControl().getNoFolgeELO();
		turnier = new Tournament(onlyTables, noDWZCalc, noFolgeDWZCalc, noELOCalc, noFolgeELOCalc);
		this.mainControl.setTournament(turnier);
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
		this.mainControl.getActionListenerPairingsMenuControl().setPairingIsActive(false);
		turnierView.getTurnierNameTextField().grabFocus();

	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		if (arg0.getSource().equals(turnierOkButton)) {
			turnierOkButton();

		}

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
		mainControl.setTournament(turnier);
		hauptPanel.setTitleAt(selectIndex, turnier.getTurnierName());
	}

	public void setEndDatum(final String endDatum) {
		this.endDatum = endDatum;
	}

	public void setGruppenAnzahl(final int gruppenAnzahl) {
		this.gruppenAnzahl = gruppenAnzahl;
	}

	public void setStartDatum(final String startDatum) {
		this.startDatum = startDatum;
	}

	public void setTurnier(final Tournament turnier) {
		this.turnier = turnier;
	}

	public void setTurnierName(final String turnierName) {
		this.turnierName = turnierName;
	}

	public void setTurnierOkButton(final JButton turnierOkButton) {
		this.turnierOkButton = turnierOkButton;
	}

	private void turnierOkButton() {
		gruppenAnzahl = 0;
		turnierName = turnierView.getTurnierNameTextField().getText();

		final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

		try {
			startDatum = formatter.format(turnierView.getStartDatumTextField());
			endDatum = formatter.format(turnierView.getEndDatumTextField());
		} catch (final NullPointerException e2) {
			startDatum = "";
			endDatum = "";
		}

		try {

			gruppenAnzahl = pruefeObZahlKleinerEinsIst(
					Integer.parseInt(turnierView.getGruppenAnzahlTextField().getValue()));

			if (turnierName.length() > 0 && startDatum.length() > 0 && endDatum.length() > 0 && gruppenAnzahl > 0) {
				makeTurnier();

				mainControl.setNewTournamentGroupsControl(new NewTournamentGroupsControl(mainControl));

			}

		} catch (final NumberFormatException e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("TurnierControl.2")); //$NON-NLS-1$
			turnierView.getGruppenAnzahlTextField().resetValue(); // $NON-NLS-1$
			turnierView.getGruppenAnzahlTextField().grabFocus();
		} catch (final ZahlKleinerAlsN e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("TurnierControl.4")); //$NON-NLS-1$
			turnierView.getGruppenAnzahlTextField().resetValue(); // $NON-NLS-1$
			turnierView.getGruppenAnzahlTextField().grabFocus();
		} catch (final ZahlGroesserAlsN e) {
			JOptionPane.showMessageDialog(mainControl, Messages.getString("TurnierControl.6")); //$NON-NLS-1$
			turnierView.getGruppenAnzahlTextField().resetValue(); // $NON-NLS-1$
			turnierView.getGruppenAnzahlTextField().grabFocus();
		}

	}
}
