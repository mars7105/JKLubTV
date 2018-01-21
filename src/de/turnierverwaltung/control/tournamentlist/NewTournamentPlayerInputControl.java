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
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.ZahlGroesserAlsN;
import de.turnierverwaltung.ZahlKleinerAlsN;
import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.TabbedPaneViewControl;
import de.turnierverwaltung.control.sqlite.SQLPlayerControl;
import de.turnierverwaltung.control.tournamenttable.PairingsControl;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.TabbedPaneView;
import de.turnierverwaltung.view.tournamentlist.NewTournamentPlayerInputView;

public class NewTournamentPlayerInputControl implements ActionListener, KeyListener {

	private MainControl mainControl;
	private NewTournamentPlayerInputView[] spielerEingabeView;
	// private NewTournamentPlayerCountlView[] spielerAnzahlView;
	private JButton[] okButton;
	private JButton[] cancelButton;
	private int[] spielerAnzahl;
	private int gruppenAnzahl;
	private TabbedPaneView tabAnzeigeView;
	private Tournament turnier;
	private Group[] gruppe;
	private Player[] spieler;
	private PairingsControl rundenEingabeFormularControl;
	private ArrayList<Player> alleSpieler;
	private Boolean[] readyToSave;
	private ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-calendar-month.png"))); //$NON-NLS-1$
	private JTabbedPane hauptPanel;

	public NewTournamentPlayerInputControl(MainControl mainControl) throws SQLException {
		int windowWidth = TournamentConstants.WINDOW_WIDTH - 25;
		int windowHeight = TournamentConstants.WINDOW_HEIGHT - 75;
		this.mainControl = mainControl;
		SQLPlayerControl spielerTableControl = new SQLPlayerControl(mainControl);
		alleSpieler = spielerTableControl.getAllSpieler();
		turnier = this.mainControl.getTournament();
		gruppe = turnier.getGruppe();
		hauptPanel = this.mainControl.getHauptPanel();
		this.mainControl.setTabbedPaneViewControl(new TabbedPaneViewControl(this.mainControl, "S"));
		this.mainControl
				.setTabbedPaneView(new TabbedPaneView(mainControl, Messages.getString("SpielerEingabeControl.10")));
		tabAnzeigeView = this.mainControl.getTabbedPaneView();
		tabAnzeigeView.setPreferredSize(new Dimension(windowWidth, windowHeight));
		hauptPanel.remove(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		hauptPanel.add(tabAnzeigeView, TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		hauptPanel.setTitleAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, turnier.getTurnierName());
		hauptPanel.setIconAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, gruppenIcon);
		hauptPanel.setSelectedIndex(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		gruppenAnzahl = this.mainControl.getTournament().getAnzahlGruppen();
		spielerAnzahl = new int[gruppenAnzahl];

		spielerEingabeView = new NewTournamentPlayerInputView[gruppenAnzahl];
		this.mainControl.setNewTournamentPlayerInputView(spielerEingabeView);

		okButton = new JButton[gruppenAnzahl];
		cancelButton = new JButton[gruppenAnzahl];
		rundenEingabeFormularControl = new PairingsControl(this.mainControl);
		this.mainControl.setPairingsControl(rundenEingabeFormularControl);

		readyToSave = new Boolean[gruppenAnzahl];
		for (int i = 0; i < gruppenAnzahl; i++) {
			readyToSave[i] = false;
			spielerAnzahl[i] = gruppe[i].getSpielerAnzahl();

		}
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		try {
			Player.cutFname = Integer.parseInt(this.mainControl.getPropertiesControl().getCutForename());
			Player.cutSname = Integer.parseInt(this.mainControl.getPropertiesControl().getCutSurname());
		} catch (NumberFormatException e) {
			Player.cutFname = 20;
			Player.cutSname = 20;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String foreName = ""; //$NON-NLS-1$
		String surName = "";
		String kuerzel = ""; //$NON-NLS-1$
		String dwz = ""; //$NON-NLS-1$
		int age = 0;
		int spielerID = 0;
		int sAnzahl = 0;

		for (int i = 0; i < gruppenAnzahl; i++) {
			if (arg0.getSource().equals(okButton[i])) {
				try {
					SQLPlayerControl stc = new SQLPlayerControl(mainControl);

					sAnzahl = gruppe[i].getSpielerAnzahl();
					gruppe[i].setRundenAnzahl(sAnzahl + ((sAnzahl % 2) - 1));
					readyToSave[i] = true;
					spieler = new Player[sAnzahl];
					Boolean allOk = true;
					if (testForDoubles(i) == true) {

						for (int y = 0; y < sAnzahl; y++) {
							if (spielerEingabeView[i].getSpielerSuche()[y].getSelectedIndex() >= 1) {
								foreName = spielerEingabeView[i].getForenameTextfield()[y].getText();
								surName = spielerEingabeView[i].getSurnameTextfield()[y].getText();
								kuerzel = spielerEingabeView[i].getKuerzelTextfield()[y].getText();
								dwz = spielerEingabeView[i].getDwzTextfield()[y].getText();
								spielerID = spielerEingabeView[i].getSpielerID()[y];
								age = spielerEingabeView[i].getTextComboBoxAge()[y].getSelectedIndex();
								spieler[y] = new Player();
								if (spielerID >= 0) {

									Player temp = null;

									ListIterator<Player> li = alleSpieler.listIterator();
									while (li.hasNext()) {
										temp = li.next();
										if (spielerID == temp.getSpielerId()) {
											spieler[y] = temp;

										}

									}
								} else {

									spieler[y].setName(surName + "," + foreName);
									spieler[y].setKuerzel(kuerzel);
									spieler[y].setDwz(dwz);
									spieler[y].setAge(age);

									spieler[y].setSpielerId(stc.insertOneSpieler(spieler[y]));
								}
							} else {
								allOk = false;
							}
						}
						if (allOk) {
							gruppe[i].setSpieler(spieler);
							spielerEingabeView[i].removeAll();

							rundenEingabeFormularControl.makeTerminTabelle(i);
						} else {
							JOptionPane.showMessageDialog(null, Messages.getString("SpielerEingabeControl.11")); //$NON-NLS-1$

						}
					} else {
						JOptionPane.showMessageDialog(null, Messages.getString("SpielerEingabeControl.9")); //$NON-NLS-1$
					}
				} catch (SQLException e) {
					ExceptionHandler eh = new ExceptionHandler(mainControl);
					eh.fileSQLError(e.getMessage());
				}
			}
			if (arg0.getSource().equals(cancelButton[i])) {
				// Custom button text
				Object[] options = { Messages.getString("SpielerEingabeControl.4"), //$NON-NLS-1$
						Messages.getString("SpielerEingabeControl.5") }; //$NON-NLS-1$
				int abfrage = JOptionPane.showOptionDialog(mainControl,
						Messages.getString("SpielerEingabeControl.6") + Messages.getString("SpielerEingabeControl.7"), //$NON-NLS-1$ //$NON-NLS-2$
						Messages.getString("SpielerEingabeControl.8"), JOptionPane.YES_NO_CANCEL_OPTION, //$NON-NLS-1$
						JOptionPane.WARNING_MESSAGE, null, options, options[1]);
				if (abfrage == 0) {
					this.mainControl.setNewTournamentControl(new NewTournamentControl(this.mainControl));
				}
			}
			for (int s = 0; s < spielerAnzahl[i]; s++) {
				if (arg0.getSource().equals(spielerEingabeView[i].getSpielerSuche()[s])) {

					@SuppressWarnings("unchecked")
					JComboBox<String> box = spielerEingabeView[i].getSpielerSuche()[s];
					if (box.getSelectedIndex() > 0) {
						Player player = alleSpieler.get(box.getSelectedIndex() - 1);

						foreName = player.getForename();
						surName = player.getSurname();
						dwz = player.getDwz();
						kuerzel = player.getKuerzel();
						spielerID = player.getSpielerId();
						age = player.getAge();

						spielerEingabeView[i].getForenameTextfield()[s].setText(foreName);
						spielerEingabeView[i].getSurnameTextfield()[s].setText(surName);
						spielerEingabeView[i].getKuerzelTextfield()[s].setText(kuerzel);
						spielerEingabeView[i].getDwzTextfield()[s].setText(dwz);
						spielerEingabeView[i].getSpielerID()[s] = spielerID;
						spielerEingabeView[i].getTextComboBoxAge()[s].setSelectedIndex(age);

					}
				}
			}
		}

	}

	public Boolean[] getReadyToSave() {
		return readyToSave;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < gruppenAnzahl; i++) {
			for (int s = 0; s < spielerAnzahl[i]; s++)
				if (e.getSource() == spielerEingabeView[i].getSurnameTextfield()[s]) {
					spielerEingabeView[i].getSpielerSuche()[s].removeActionListener(this);
					spielerEingabeView[i].getSurnameTextfield()[s].removeKeyListener(this);

					spielerEingabeView[i].getSpielerSuche()[s].removeAllItems();

					Player temp = null;

					ListIterator<Player> li = alleSpieler.listIterator();

					String textField = ""; //$NON-NLS-1$
					String labels = ""; //$NON-NLS-1$
					textField = spielerEingabeView[i].getSurnameTextfield()[s].getText() + e.getKeyChar();
					while (li.hasNext()) {
						temp = li.next();

						if (textField.regionMatches(true, 0, temp.getSurname(), 0, textField.length())) {
							labels = temp.getSurname();
							spielerEingabeView[i].getSpielerSuche()[s].addItem(labels);

						}

					}
					spielerEingabeView[i].getSpielerSuche()[s].addActionListener(this);
					spielerEingabeView[i].getSurnameTextfield()[s].addKeyListener(this);
					if (spielerEingabeView[i].getSurnameTextfield()[s].getText().length() == 0) {
						suchAnzeige2(i);
					}

				}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void makeTabbedPane(int index) throws NumberFormatException, ZahlKleinerAlsN, ZahlGroesserAlsN {
		this.mainControl.getHauptPanel();
		tabAnzeigeView.getTabbedPane().addTab(null, spielerEingabeView[index]);

		spielerEingabeView[index] = new NewTournamentPlayerInputView(spielerAnzahl[index]);
		okButton[index] = spielerEingabeView[index].getOkButton();
		okButton[index].addActionListener(this);
		cancelButton[index] = spielerEingabeView[index].getCancelButton();
		cancelButton[index].addActionListener(this);
		tabAnzeigeView.getTabbedPane().setComponentAt(index, spielerEingabeView[index]);
		tabAnzeigeView.getTabbedPane().setTitleAt(index, gruppe[index].getGruppenName());
		tabAnzeigeView.getTabbedPane().setIconAt(index, gruppenIcon);

		suchAnzeige(index);

	}

	public void setReadyToSave(Boolean[] readyToSave) {
		this.readyToSave = readyToSave;
	}

	@SuppressWarnings("unchecked")
	private void suchAnzeige(int index) {
		for (int i = 0; i < spielerAnzahl[index]; i++) {

			Player temp = null;
			ListIterator<Player> li = alleSpieler.listIterator();
			String labelName = "";
			while (li.hasNext()) {
				temp = li.next();
				labelName = temp.getName();
				spielerEingabeView[index].getSpielerSuche()[i].addItem(labelName);

			}
			spielerEingabeView[index].getSpielerSuche()[i].addActionListener(this);
			spielerEingabeView[index].getSurnameTextfield()[i].addKeyListener(this);

		}
	}

	@SuppressWarnings("unchecked")
	private void suchAnzeige2(int index) {
		for (int i = 0; i < spielerAnzahl[index]; i++) {
			spielerEingabeView[index].getSpielerSuche()[i].removeActionListener(this);
			spielerEingabeView[index].getSurnameTextfield()[i].removeKeyListener(this);
			Player temp = null;
			ListIterator<Player> li = alleSpieler.listIterator();
			String labelName = "";
			while (li.hasNext()) {
				temp = li.next();
				labelName = temp.getName();
				spielerEingabeView[index].getSpielerSuche()[i].addItem(labelName);

			}
			spielerEingabeView[index].getSpielerSuche()[i].addActionListener(this);
			spielerEingabeView[index].getSurnameTextfield()[i].addKeyListener(this);
		}
	}

	private boolean testForDoubles(int index) {
		int sAnzahl = gruppe[index].getSpielerAnzahl();
		Boolean testOK = true;
		for (int y = 0; y < sAnzahl - 1; y++) {

			int spielerYID = spielerEingabeView[index].getSpielerID()[y];

			for (int x = y + 1; x < sAnzahl; x++) {
				int spielerXID = spielerEingabeView[index].getSpielerID()[x];
				if (spielerYID == spielerXID && spielerYID >= 0) {
					testOK = false;
				}
			}
		}
		return testOK;
	}

}