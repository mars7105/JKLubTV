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

import de.turnierverwaltung.model.Gruppe;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.view.SpielerAnzahlView;
import de.turnierverwaltung.view.SpielerEingabeView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class SpielerEingabeControl implements ActionListener, KeyListener {

	private MainControl mainControl;
	private SpielerEingabeView[] spielerEingabeView;
	private SpielerAnzahlView[] spielerAnzahlView;
	private JButton[] okButton;
	private JButton[] cancelButton;
	private int[] spielerAnzahl;
	private int gruppenAnzahl;
	private TabAnzeigeView tabAnzeigeView;
	private Turnier turnier;
	private Gruppe[] gruppe;
	private Spieler[] spieler;
	private RundenEingabeFormularControl rundenEingabeFormularControl;
	private ArrayList<Spieler> alleSpieler;
	private Boolean[] readyToSave;
	private ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-calendar-month.png"))); //$NON-NLS-1$

	public SpielerEingabeControl(MainControl mainControl, int selectIndex) throws SQLException {
		int windowWidth = TurnierKonstanten.WINDOW_WIDTH - 25;
		int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 75;
		this.mainControl = mainControl;
		SpielerTableControl spielerTableControl = new SpielerTableControl(mainControl);
		alleSpieler = spielerTableControl.getAllSpieler();
		turnier = this.mainControl.getTurnier();
		gruppe = turnier.getGruppe();
		this.mainControl.getHauptPanel();
		tabAnzeigeView = this.mainControl.getTabAnzeigeView();
		tabAnzeigeView.setPreferredSize(new Dimension(windowWidth, windowHeight));
		spielerAnzahlView = this.mainControl.getSpielerAnzahlControl().getSpielerAnzahlView();
		gruppenAnzahl = this.mainControl.getTurnier().getAnzahlGruppen();
		spielerAnzahl = new int[gruppenAnzahl];

		spielerEingabeView = new SpielerEingabeView[gruppenAnzahl];
		this.mainControl.setSpielerEingabeView(spielerEingabeView);

		okButton = new JButton[gruppenAnzahl];
		cancelButton = new JButton[gruppenAnzahl];
		if (this.mainControl.getRundenEingabeFormularControl() == null) {
			rundenEingabeFormularControl = new RundenEingabeFormularControl(this.mainControl, selectIndex);
			this.mainControl.setRundenEingabeFormularControl(rundenEingabeFormularControl);
		} else {
			rundenEingabeFormularControl = this.mainControl.getRundenEingabeFormularControl();
		}
		rundenEingabeFormularControl = new RundenEingabeFormularControl(this.mainControl, selectIndex);
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
				SpielerTableControl stc = new SpielerTableControl(mainControl);

				sAnzahl = gruppe[i].getSpielerAnzahl();
				gruppe[i].setRundenAnzahl(sAnzahl + ((sAnzahl % 2) - 1));
				readyToSave[i] = true;
				spieler = new Spieler[sAnzahl];
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

							spieler[counter] = new Spieler();
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

					Spieler temp = null;

					ListIterator<Spieler> li = alleSpieler.listIterator();

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
		// hauptPanel.updateUI();

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
					spielerEingabeView[i].getSpielerSuche()[s].removeAllItems();

					Spieler temp = null;

					ListIterator<Spieler> li = alleSpieler.listIterator();

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
					if (spielerEingabeView[i].getSpielerTextfield()[s].getText().length() == 0) {
						suchAnzeige2(i);
					}
					spielerEingabeView[i].getSpielerSuche()[s].addActionListener(this);

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
			spielerEingabeView[index] = new SpielerEingabeView(spielerAnzahl[index]);
			okButton[index] = spielerEingabeView[index].getOkButton();
			okButton[index].addActionListener(this);
			cancelButton[index] = spielerEingabeView[index].getCancelButton();
			cancelButton[index].addActionListener(this);
			tabAnzeigeView.setComponentAt(index, spielerEingabeView[index]);
			tabAnzeigeView.setTitleAt(index, gruppe[index].getGruppenName());
			tabAnzeigeView.setIconAt(index, gruppenIcon);

			suchAnzeige(index);
			// hauptPanel.updateUI();
		}
	}

	@SuppressWarnings("unchecked")
	private void suchAnzeige(int index) {
		for (int i = 0; i < spielerAnzahl[index]; i++) {

			Spieler temp = null;
			ListIterator<Spieler> li = alleSpieler.listIterator();
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
			Spieler temp = null;
			ListIterator<Spieler> li = alleSpieler.listIterator();
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
