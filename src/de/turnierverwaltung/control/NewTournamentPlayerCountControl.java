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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import de.turnierverwaltung.ZahlGroesserAlsN;
import de.turnierverwaltung.ZahlKleinerAlsN;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.NewTournamentPlayerCountlView;
import de.turnierverwaltung.view.TabbedPaneView;

public class NewTournamentPlayerCountControl implements ActionListener {
	private int gruppenAnzahl;
	private int spielerAnzahl[];
	private String[] title;
	private JButton[] okButton;
	private NewTournamentPlayerInputControl spielerEingabeControl;
	private MainControl mainControl;
	private NewTournamentPlayerCountlView[] spielerAnzahlView;
	private JComboBox<String>[] spielerAnzahlComboBox;
	private JTabbedPane hauptPanel;
	private TabbedPaneView tabbedPaneView;
	private Tournament turnier;
	private ArrayList<Group> gruppe;
	private ArrayList<ArrayList<Player>> spieler;
	private ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png"))); //$NON-NLS-1$

	@SuppressWarnings("unchecked")
	public NewTournamentPlayerCountControl(MainControl mainControl) {
		this.mainControl = mainControl;
		turnier = this.mainControl.getTurnier();
		gruppe = turnier.getGruppe();

		this.mainControl.setTabAnzeigeControl(new TabbedPaneViewControl(this.mainControl, "S"));
		this.mainControl.setTabAnzeigeView(new TabbedPaneView(mainControl, "S"));
		tabbedPaneView = this.mainControl.getTabAnzeigeView();

		hauptPanel = this.mainControl.getHauptPanel();
		gruppenAnzahl = this.mainControl.getTurnier().getAnzahlGruppen();
		this.spielerAnzahl = new int[gruppenAnzahl];
		title = mainControl.getGruppenView().getGruppenName();
		spielerAnzahlView = new NewTournamentPlayerCountlView[gruppenAnzahl];
		spielerAnzahlComboBox = new JComboBox[gruppenAnzahl];
		okButton = new JButton[gruppenAnzahl];
		spieler = new ArrayList<ArrayList<Player>>();
		for (int i = 0; i < gruppenAnzahl; i++) {
			spieler.add(new ArrayList<Player>());
			spielerAnzahlView[i] = new NewTournamentPlayerCountlView(title[i]);
			spielerAnzahlComboBox[i] = spielerAnzahlView[i].getSpielerAnzahlComboBox();
			okButton[i] = spielerAnzahlView[i].getOkButton();
			okButton[i].addActionListener(this);
			tabbedPaneView.getTabbedPane().addTab(title[i], spielerAnzahlView[i]);
			gruppe.get(i).setSpieler(spieler.get(i));
			spielerAnzahlComboBox[i].grabFocus();
		}
		// tabbedPaneView.updateUI();
		hauptPanel.remove(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		hauptPanel.add(tabbedPaneView, TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		hauptPanel.setTitleAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, turnier.getTurnierName());
		hauptPanel.setIconAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, gruppenIcon);
		hauptPanel.setSelectedIndex(TournamentConstants.TAB_ACTIVE_TOURNAMENT);

		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		tabbedPaneView.getTabbedPane().setSelectedIndex(0);
		// spielerAnzahlComboBox[0].setText("");
		spielerAnzahlComboBox[0].grabFocus();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int fehlerIndex = 0;
		try {
			if (mainControl.getSpielerEingabeControl() == null) {
				spielerEingabeControl = new NewTournamentPlayerInputControl(mainControl);
				mainControl.setSpielerEingabeControl(spielerEingabeControl);
			} else {
				spielerEingabeControl = mainControl.getSpielerEingabeControl();
			}

			for (int i = 0; i < gruppenAnzahl; i++) {
				if (arg0.getSource() == okButton[i]) {
					fehlerIndex = i;
					spielerAnzahl[i] = getSpielerAnzahl(i);
					spielerEingabeControl.makeTabbedPane(i);

				}
			}
		} catch (SQLException e) {
			mainControl.fileSQLError();
		} catch (NumberFormatException e) {
			spielerAnzahl[fehlerIndex] = 0;
			JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerAnzahlControl.1")); //$NON-NLS-1$
			// spielerAnzahlComboBox[fehlerIndex].setText(""); //$NON-NLS-1$
			spielerAnzahlComboBox[fehlerIndex].grabFocus();
		} catch (ZahlKleinerAlsN e) {
			spielerAnzahl[fehlerIndex] = 0;

			JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerAnzahlControl.3")); //$NON-NLS-1$
			spielerAnzahlComboBox[fehlerIndex].grabFocus();
		} catch (ZahlGroesserAlsN e) {
			spielerAnzahl[fehlerIndex] = 0;

			JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerAnzahlControl.5")); //$NON-NLS-1$
			spielerAnzahlComboBox[fehlerIndex].grabFocus();
		}

	}

	public int getSpielerAnzahl(int indexI) throws ZahlKleinerAlsN, NumberFormatException, ZahlGroesserAlsN {

		spielerAnzahl[indexI] = spielerAnzahlComboBox[indexI].getSelectedIndex() + 3;
		return spielerAnzahl[indexI];
	}

	public NewTournamentPlayerCountlView[] getSpielerAnzahlView() {
		return spielerAnzahlView;
	}

	public void makeNewTab(int indexI) {
		spieler.get(indexI).add(new Player());
		spielerAnzahlView[indexI] = new NewTournamentPlayerCountlView(title[indexI]);
		spielerAnzahlComboBox[indexI] = spielerAnzahlView[indexI].getSpielerAnzahlComboBox();
		okButton[indexI] = spielerAnzahlView[indexI].getOkButton();
		okButton[indexI].addActionListener(this);
		tabbedPaneView.getTabbedPane().setComponentAt(indexI, spielerAnzahlView[indexI]);
		spielerAnzahlView[indexI].getSpielerAnzahlComboBox().grabFocus();

	}

	public void setSpielerAnzahl(int[] spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
	}

	public void setSpielerAnzahlView(NewTournamentPlayerCountlView[] spielerAnzahlView) {
		this.spielerAnzahlView = spielerAnzahlView;
	}

	public JComboBox<String>[] getSpielerAnzahlComboBox() {
		return spielerAnzahlComboBox;
	}

	public void setSpielerAnzahlComboBox(JComboBox<String>[] spielerAnzahlComboBox) {
		this.spielerAnzahlComboBox = spielerAnzahlComboBox;
	}

}
