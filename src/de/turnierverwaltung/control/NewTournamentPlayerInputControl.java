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
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.NewTournamentPlayerCountlView;
import de.turnierverwaltung.view.NewTournamentPlayerInputView;
import de.turnierverwaltung.view.TabbedPaneView;

public class NewTournamentPlayerInputControl implements ActionListener, KeyListener {

	private MainControl mainControl;
	private NewTournamentPlayerInputView[] spielerEingabeView;
	private NewTournamentPlayerCountlView[] spielerAnzahlView;
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

	public NewTournamentPlayerInputControl(MainControl mainControl, int selectIndex) throws SQLException {
		int windowWidth = TournamentConstants.WINDOW_WIDTH - 25;
		int windowHeight = TournamentConstants.WINDOW_HEIGHT - 75;
		this.mainControl = mainControl;
		SQLPlayerControl spielerTableControl = new SQLPlayerControl(mainControl);
		alleSpieler = spielerTableControl.getAllSpieler();
		turnier = this.mainControl.getTurnier();
		gruppe = turnier.getGruppe();
		this.mainControl.getHauptPanel();
		tabAnzeigeView = this.mainControl.getTabAnzeigeView();
		tabAnzeigeView.setPreferredSize(new Dimension(windowWidth, windowHeight));
		spielerAnzahlView = this.mainControl.getSpielerAnzahlControl().getSpielerAnzahlView();
		gruppenAnzahl = this.mainControl.getTurnier().getAnzahlGruppen();
		spielerAnzahl = new int[gruppenAnzahl];

		spielerEingabeView = new NewTournamentPlayerInputView[gruppenAnzahl];
		this.mainControl.setSpielerEingabeView(spielerEingabeView);

		okButton = new JButton[gruppenAnzahl];
		cancelButton = new JButton[gruppenAnzahl];
		if (this.mainControl.getRundenEingabeFormularControl() == null) {
			rundenEingabeFormularControl = new PairingsControl(this.mainControl, selectIndex);
			this.mainControl.setRundenEingabeFormularControl(rundenEingabeFormularControl);
		} else {
			rundenEingabeFormularControl = this.mainControl.getRundenEingabeFormularControl();
		}
		rundenEingabeFormularControl = new PairingsControl(this.mainControl, selectIndex);
		this.mainControl.setRundenEingabeFormularControl(rundenEingabeFormularControl);
		readyToSave = new Boolean[gruppenAnzahl];
		for (int i = 0; i < gruppenAnzahl; i++) {
			readyToSave[i] = false;

		}
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String name = ""; //$NON-NLS-1$
		String kuerzel = ""; //$NON-NLS-1$
		String dwz = ""; //$NON-NLS-1$
		int age = 0;
		int spielerID = 0;
		int sAnzahl = 0;

		for (int i = 0; i < gruppenAnzahl; i++) {
			if (arg0.getSource() == okButton[i]) {
				SQLPlayerControl stc = new SQLPlayerControl(mainControl);

				sAnzahl = gruppe[i].getSpielerAnzahl();
				gruppe[i].setRundenAnzahl(sAnzahl + ((sAnzahl % 2) - 1));
				readyToSave[i] = true;
				spieler = new Player[sAnzahl];
				Boolean correctName = true;
				int counter = -1;

				if (testForDoubles(i) == true) {
					for (int y = 0; y < sAnzahl; y++) {
						if (spielerEingabeView[i].getSpielerTextfield()[y].getText().equals("Spielfrei")) {
							correctName = false;
						} else {
							correctName = true;
							counter++;
						}

						if (correctName == true) {
							name = spielerEingabeView[i].getSpielerTextfield()[y].getText();
							kuerzel = spielerEingabeView[i].getKuerzelTextfield()[y].getText();
							dwz = spielerEingabeView[i].getDwzTextfield()[y].getText();
							spielerID = spielerEingabeView[i].getSpielerID()[y];
							age = spielerEingabeView[i].getTextComboBoxAge()[y].getSelectedIndex();

							spieler[counter] = new Player();
							spieler[counter].setName(name);
							spieler[counter].setKuerzel(kuerzel);
							spieler[counter].setDwz(dwz);
							spieler[counter].setAge(age);
							if (spielerID >= 0) {
								spieler[counter].setSpielerId(spielerID);
								stc.updateOneSpieler(spieler[counter]);

							} else {
								spieler[counter].setSpielerId(stc.insertOneSpieler(spieler[counter]));
							}
						}
					}
					testPlayerListForDoubles();
					gruppe[i].setSpieler(spieler);
					Arrays.sort(spieler);
					spielerEingabeView[i].removeAll();
					// rundenEingabeFormularControl.makeRundenEditView(i);

					rundenEingabeFormularControl.makeTerminTabelle(i);
					// rundenEingabeFormularControl.saveTurnier(i);
				} else {
					JOptionPane.showMessageDialog(null, Messages.getString("SpielerEingabeControl.9")); //$NON-NLS-1$
				}
			}
			if (arg0.getSource() == cancelButton[i]) {
				// Custom button text
				Object[] options = { Messages.getString("SpielerEingabeControl.4"), //$NON-NLS-1$
						Messages.getString("SpielerEingabeControl.5") }; //$NON-NLS-1$
				int abfrage = JOptionPane.showOptionDialog(mainControl,
						Messages.getString("SpielerEingabeControl.6") + Messages.getString("SpielerEingabeControl.7"), //$NON-NLS-1$ //$NON-NLS-2$
						Messages.getString("SpielerEingabeControl.8"), JOptionPane.YES_NO_CANCEL_OPTION, //$NON-NLS-1$
						JOptionPane.WARNING_MESSAGE, null, options, options[1]);
				if (abfrage == 0) {
					this.mainControl.getSpielerAnzahlControl().makeNewTab(i);
				}
			}
			for (int s = 0; s < spielerAnzahl[i]; s++) {
				if (arg0.getSource() == spielerEingabeView[i].getSpielerSuche()[s]) {
					JTextField field = spielerEingabeView[i].getSpielerTextfield()[s];
					@SuppressWarnings("unchecked")
					JComboBox<String> box = spielerEingabeView[i].getSpielerSuche()[s];

					field.setText((String) box.getSelectedItem());

					Player temp = null;

					ListIterator<Player> li = alleSpieler.listIterator();

					String textField = ""; //$NON-NLS-1$
					textField = field.getText();
					while (li.hasNext()) {
						temp = li.next();
						if (textField.regionMatches(true, 0, temp.getName(), 0, textField.length())) {
							dwz = temp.getDwz();
							kuerzel = temp.getKuerzel();
							spielerID = temp.getSpielerId();
							age = temp.getAge();
							spielerEingabeView[i].getKuerzelTextfield()[s].setText(kuerzel);
							spielerEingabeView[i].getDwzTextfield()[s].setText(dwz);
							spielerEingabeView[i].getSpielerID()[s] = spielerID;
							spielerEingabeView[i].getTextComboBoxAge()[s].setSelectedIndex(age);
						}

					}
				}
			}
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

	@SuppressWarnings("unchecked")
	@Override
	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < gruppenAnzahl; i++) {
			for (int s = 0; s < spielerAnzahl[i]; s++)
				if (e.getSource() == spielerEingabeView[i].getSpielerTextfield()[s]) {
					spielerEingabeView[i].getSpielerSuche()[s].removeActionListener(this);
					spielerEingabeView[i].getSpielerTextfield()[s].removeKeyListener(this);

					spielerEingabeView[i].getSpielerSuche()[s].removeAllItems();

					Player temp = null;

					ListIterator<Player> li = alleSpieler.listIterator();

					String textField = ""; //$NON-NLS-1$
					String labels = ""; //$NON-NLS-1$
					textField = spielerEingabeView[i].getSpielerTextfield()[s].getText() + e.getKeyChar();
					while (li.hasNext()) {
						temp = li.next();

						if (textField.regionMatches(true, 0, temp.getName(), 0, textField.length())) {
							labels = temp.getName();
							spielerEingabeView[i].getSpielerSuche()[s].addItem(labels);

						}

					}
					spielerEingabeView[i].getSpielerSuche()[s].addActionListener(this);
					spielerEingabeView[i].getSpielerTextfield()[s].addKeyListener(this);
					if (spielerEingabeView[i].getSpielerTextfield()[s].getText().length() == 0) {
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

	public void makeTabbedPane(int index) {
		this.mainControl.getHauptPanel();

		if (spielerAnzahlView[index].getAnzahlSpielerTextField().getText().length() > 0) {
			spielerAnzahl[index] = this.mainControl.getSpielerAnzahlControl().getSpielerAnzahl(index);
			spielerEingabeView[index] = new NewTournamentPlayerInputView(spielerAnzahl[index]);
			okButton[index] = spielerEingabeView[index].getOkButton();
			okButton[index].addActionListener(this);
			cancelButton[index] = spielerEingabeView[index].getCancelButton();
			cancelButton[index].addActionListener(this);
			tabAnzeigeView.getTabbedPane().setComponentAt(index, spielerEingabeView[index]);
			tabAnzeigeView.getTabbedPane().setTitleAt(index, gruppe[index].getGruppenName());
			tabAnzeigeView.getTabbedPane().setIconAt(index, gruppenIcon);

			suchAnzeige(index);
			// hauptPanel.updateUI();
		}
	}

	private void testPlayerListForDoubles() {
		Boolean loop = false;
		SQLPlayerControl stc = new SQLPlayerControl(mainControl);
		do {
			loop = false;
			for (int i = 0; i < spieler.length; i++) {
				for (int y = 0; y < spieler.length; y++) {
					if (i != y) {
						if (spieler[i].getName().equals(spieler[y].getName())) {
							spieler[y].setName(spieler[y].getName() + "#");

							stc.updateOneSpieler(spieler[y]);
							loop = true;
						}
					}

				}
			}
		} while (loop == true);
	}

	@SuppressWarnings("unchecked")
	private void suchAnzeige(int index) {
		for (int i = 0; i < spielerAnzahl[index]; i++) {

			Player temp = null;
			ListIterator<Player> li = alleSpieler.listIterator();
			String labels = ""; //$NON-NLS-1$
			while (li.hasNext()) {
				temp = li.next();
				labels = temp.getName();
				spielerEingabeView[index].getSpielerSuche()[i].addItem(labels);
			}
			spielerEingabeView[index].getSpielerSuche()[i].addActionListener(this);
			spielerEingabeView[index].getSpielerTextfield()[i].addKeyListener(this);

		}
	}

	@SuppressWarnings("unchecked")
	private void suchAnzeige2(int index) {
		for (int i = 0; i < spielerAnzahl[index]; i++) {
			spielerEingabeView[index].getSpielerSuche()[i].removeActionListener(this);
			spielerEingabeView[index].getSpielerTextfield()[i].removeKeyListener(this);
			Player temp = null;
			ListIterator<Player> li = alleSpieler.listIterator();
			String labels = ""; //$NON-NLS-1$
			while (li.hasNext()) {
				temp = li.next();
				labels = temp.getName();
				spielerEingabeView[index].getSpielerSuche()[i].addItem(labels);
			}
			spielerEingabeView[index].getSpielerSuche()[i].addActionListener(this);
			spielerEingabeView[index].getSpielerTextfield()[i].addKeyListener(this);
		}
	}

	public Boolean[] getReadyToSave() {
		return readyToSave;
	}

	public void setReadyToSave(Boolean[] readyToSave) {
		this.readyToSave = readyToSave;
	}

}
