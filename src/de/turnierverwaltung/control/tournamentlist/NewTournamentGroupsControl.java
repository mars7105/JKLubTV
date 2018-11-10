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
import de.turnierverwaltung.view.tournamenttable.ButtonTabComponent;

public class NewTournamentGroupsControl implements ActionListener {
	private static int pruefeObZahlKleinerDreiIst(final int zahl) throws ZahlKleinerAlsN, ZahlGroesserAlsN {
		if (zahl < 3) {
			throw new ZahlKleinerAlsN();
		}
		if (zahl > 20) {
			throw new ZahlGroesserAlsN();
		}
		return zahl;
	}

	private final MainControl mainControl;
	private NewTournamentGroupsView gruppenView;
	private JButton gruppenOKButton;
	private JButton gruppenCancelButton;
	private JTabbedPane hauptPanel;
	private int gruppenAnzahl;
	private Tournament turnier;
	private Group[] gruppe;
	private final ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png")));
	private Player[][] spieler;

	private int spielerAnzahl[];

	public NewTournamentGroupsControl(final MainControl mainControl) {

		this.mainControl = mainControl;
		init();
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

		if (arg0.getSource().equals(gruppenOKButton)) {
			makeGruppe();

			// mainControl.setSpielerAnzahlControl(new
			// NewTournamentPlayerCountControl(this.mainControl));
			runPlayerInput();
		}
		if (arg0.getSource().equals(gruppenCancelButton)) {
			mainControl.setNewTournamentControl(new NewTournamentControl(mainControl));
		}

	}

	public int getGruppenAnzahl() {
		return gruppenAnzahl;
	}

	public int getSpielerAnzahl(final int indexI) throws ZahlKleinerAlsN, NumberFormatException, ZahlGroesserAlsN {

		spielerAnzahl[indexI] = pruefeObZahlKleinerDreiIst(
				Integer.parseInt(gruppenView.getAnzahlSpielerSpinner()[indexI].getValue()));

		return spielerAnzahl[indexI];
	}

	public void init() {
		turnier = mainControl.getTournament();
		hauptPanel = mainControl.getHauptPanel();
		gruppenAnzahl = turnier.getAnzahlGruppen();
		mainControl.setNewTournamentGroupsView(new NewTournamentGroupsView());
		gruppenView = mainControl.getNewTournamentGroupsView();
		gruppenView.runView(gruppenAnzahl);
		gruppenOKButton = gruppenView.getOkButton();
		gruppenOKButton.addActionListener(this);
		gruppenCancelButton = gruppenView.getCancelButton();
		gruppenCancelButton.addActionListener(this);
		gruppenView.getGruppenNameTextField()[0].grabFocus();
		if (hauptPanel.getTabCount() > TournamentConstants.TAB_ACTIVE_TOURNAMENT) {
			hauptPanel.remove(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		}
		hauptPanel.add(gruppenView, TournamentConstants.TAB_ACTIVE_TOURNAMENT);

		final ButtonTabComponent buttonTabComponent = mainControl.getButtonTabComponent();

		hauptPanel.setTabComponentAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, buttonTabComponent);

		hauptPanel.setTitleAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, turnier.getTurnierName());
		hauptPanel.setIconAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, gruppenIcon);
		hauptPanel.setSelectedIndex(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		mainControl.getNaviView().getTabellenPanel().setVisible(false);
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
		final int fehlerIndex = 0;
		try {
			NewTournamentPlayerIncludeControl spielerEingabeControl;
			if (mainControl.getNewTournamentPlayerIncludeControl() == null) {
				spielerEingabeControl = new NewTournamentPlayerIncludeControl(mainControl);
				mainControl.setNewTournamentPlayerIncludeControl(spielerEingabeControl);
			} else {
				spielerEingabeControl = mainControl.getNewTournamentPlayerIncludeControl();
			}

			for (int i = 0; i < gruppenAnzahl; i++) {
				
				spielerEingabeControl.makeTabbedPane(i);

			}
		} catch (final SQLException e) {
			final ExceptionHandler eh = new ExceptionHandler(mainControl);
			eh.fileSQLError(e.getMessage());
		} catch (final NumberFormatException e) {
			spielerAnzahl[fehlerIndex] = 0;
			JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerAnzahlControl.1")); //$NON-NLS-1$
			// spielerAnzahlTextfield[fehlerIndex].setText(""); //$NON-NLS-1$
		} catch (final ZahlKleinerAlsN e) {
			spielerAnzahl[fehlerIndex] = 0;

			JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerAnzahlControl.3")); //$NON-NLS-1$
			// spielerAnzahlTextfield[fehlerIndex].setText(""); //$NON-NLS-1$
		} catch (final ZahlGroesserAlsN e) {
			spielerAnzahl[fehlerIndex] = 0;

			JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerAnzahlControl.5")); //$NON-NLS-1$
			// spielerAnzahlTextfield[fehlerIndex].setText(""); //$NON-NLS-1$
		}

	}

	public void setGruppenAnzahl(final int gruppenAnzahl) {
		this.gruppenAnzahl = gruppenAnzahl;
	}
}