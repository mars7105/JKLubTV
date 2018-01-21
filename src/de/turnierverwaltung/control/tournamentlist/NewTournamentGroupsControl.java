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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.ZahlGroesserAlsN;
import de.turnierverwaltung.ZahlKleinerAlsN;
import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.tournamentlist.NewTournamentGroupsView;

public class NewTournamentGroupsControl implements ActionListener {
	private static int pruefeObZahlKleinerDreiIst(int zahl) throws ZahlKleinerAlsN, ZahlGroesserAlsN {
		if (zahl < 3) {
			throw new ZahlKleinerAlsN();
		}
		if (zahl > 20) {
			throw new ZahlGroesserAlsN();
		}
		return zahl;
	}
	private MainControl mainControl;
	private NewTournamentGroupsView gruppenView;
	private JButton gruppenOKButton;
	private JButton gruppenCancelButton;
	private JTabbedPane hauptPanel;
	private int gruppenAnzahl;
	private Tournament turnier;
	private Group[] gruppe;
	private ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png")));
	private Player[][] spieler;

	private int spielerAnzahl[];

	public NewTournamentGroupsControl(MainControl mainControl) {

		this.mainControl = mainControl;
		init();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource().equals(gruppenOKButton)) {
			makeGruppe();

			// mainControl.setSpielerAnzahlControl(new
			// NewTournamentPlayerCountControl(this.mainControl));
			runPlayerInput();
		}
		if (arg0.getSource().equals(gruppenCancelButton)) {
			this.mainControl.setNewTournamentControl(new NewTournamentControl(this.mainControl));
		}

	}

	public int getGruppenAnzahl() {
		return gruppenAnzahl;
	}

	public int getSpielerAnzahl(int indexI) throws ZahlKleinerAlsN, NumberFormatException, ZahlGroesserAlsN {

		spielerAnzahl[indexI] = pruefeObZahlKleinerDreiIst(
				Integer.parseInt(gruppenView.getAnzahlSpielerSpinner()[indexI].getValue()));

		return spielerAnzahl[indexI];
	}

	public void init() {
		turnier = this.mainControl.getTournament();
		hauptPanel = this.mainControl.getHauptPanel();
		gruppenAnzahl = turnier.getAnzahlGruppen();
		this.mainControl.setNewTournamentGroupsView(new NewTournamentGroupsView());
		this.gruppenView = this.mainControl.getNewTournamentGroupsView();
		gruppenView.runView(gruppenAnzahl);
		gruppenOKButton = this.gruppenView.getOkButton();
		gruppenOKButton.addActionListener(this);
		gruppenCancelButton = this.gruppenView.getCancelButton();
		gruppenCancelButton.addActionListener(this);
		gruppenView.getGruppenNameTextField()[0].grabFocus();
		hauptPanel.remove(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		hauptPanel.add(this.gruppenView, TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		hauptPanel.setTitleAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, turnier.getTurnierName());
		hauptPanel.setIconAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, gruppenIcon);
		hauptPanel.setSelectedIndex(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
	}

	private void makeGruppe() {
		gruppe = new Group[gruppenAnzahl];
		spieler = new Player[gruppenAnzahl][];
		spielerAnzahl = new int[gruppenAnzahl];
		for (int i = 0; i < gruppenAnzahl; i++) {
			spielerAnzahl[i] = Integer.parseInt(gruppenView.getAnzahlSpielerSpinner()[i].getValue());
			gruppe[i] = new Group();
			gruppe[i].setGruppenName(gruppenView.getGruppenNameTextField()[i].getText());
			spieler[i] = new Player[spielerAnzahl[i]];

			gruppe[i].setSpieler(spieler[i]);
			gruppe[i].setSpielerAnzahl(spielerAnzahl[i]);

		}
		turnier.setGruppe(gruppe);

	}

	private void runPlayerInput() {
		int fehlerIndex = 0;
		try {
			NewTournamentPlayerInputControl spielerEingabeControl;
			if (mainControl.getNewTournamentPlayerInputControl() == null) {
				spielerEingabeControl = new NewTournamentPlayerInputControl(mainControl);
				mainControl.setNewTournamentPlayerInputControl(spielerEingabeControl);
			} else {
				spielerEingabeControl = mainControl.getNewTournamentPlayerInputControl();
			}

			for (int i = 0; i < gruppenAnzahl; i++) {
				//
				// fehlerIndex = i;
				// spielerAnzahl[i] = getSpielerAnzahl(i);
				// gruppe[i].setSpielerAnzahl(spielerAnzahl[i]);
				spielerEingabeControl.makeTabbedPane(i);

			}
		} catch (SQLException e) {
			ExceptionHandler eh = new ExceptionHandler(mainControl);
			eh.fileSQLError(e.getMessage());
		} catch (NumberFormatException e) {
			spielerAnzahl[fehlerIndex] = 0;
			JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerAnzahlControl.1")); //$NON-NLS-1$
			// spielerAnzahlTextfield[fehlerIndex].setText(""); //$NON-NLS-1$
		} catch (ZahlKleinerAlsN e) {
			spielerAnzahl[fehlerIndex] = 0;

			JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerAnzahlControl.3")); //$NON-NLS-1$
			// spielerAnzahlTextfield[fehlerIndex].setText(""); //$NON-NLS-1$
		} catch (ZahlGroesserAlsN e) {
			spielerAnzahl[fehlerIndex] = 0;

			JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerAnzahlControl.5")); //$NON-NLS-1$
			// spielerAnzahlTextfield[fehlerIndex].setText(""); //$NON-NLS-1$
		}

	}

	public void setGruppenAnzahl(int gruppenAnzahl) {
		this.gruppenAnzahl = gruppenAnzahl;
	}
}