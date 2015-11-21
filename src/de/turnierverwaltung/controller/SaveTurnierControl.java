package de.turnierverwaltung.controller;

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
import javax.swing.JOptionPane;

import de.turnierverwaltung.view.LadebalkenView;

public class SaveTurnierControl {
	private MainControl mainControl;
	private LadebalkenView ladebalkenView;;

	public SaveTurnierControl(MainControl mainControl) {
		this.mainControl = mainControl;

	}

	/**
	 * Create the GUI and show it. As with all GUI code, this must run on the
	 * event-dispatching thread.
	 */
	private void createAndShowGUI() {

		ladebalkenView = new LadebalkenView("Speicher " + this.mainControl.getTurnier().getTurnierName(),
				this.mainControl.getTurnier().getAnzahlGruppen());

		// Display the window.
		ladebalkenView.pack();

		ladebalkenView.setVisible(true);
	}

	public Boolean saveTurnier() {
		Boolean ready = true;
		if (mainControl.getSpielerEingabeControl() != null) {
			for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {
				if (mainControl.getSpielerEingabeControl().getReadyToSave()[i] == false) {
					ready = false;
				}
			}
		}
		if (ready) {
			for (int x = 0; x < mainControl.getTurnier().getAnzahlGruppen(); x++) {
				mainControl.getRundenEingabeFormularControl().changeWerte(x);
				mainControl.getRundenEingabeFormularControl().makeRundenEditView(x);
			}			
			createAndShowGUI();
			boolean saveOK1 = false;
			boolean saveOK2 = false;
			boolean saveOK4 = false;
			// ladebalkenView.iterate();

			this.mainControl.setTurnierTableControl(new TurnierTableControl(this.mainControl));

			this.mainControl.setGruppenTableControl(new GruppenTableControl(this.mainControl));

			this.mainControl.setSpielerTableControl(new SpielerTableControl(this.mainControl));

			this.mainControl.setPartienTableControl(new PartienTableControl(this.mainControl));

			this.mainControl.setTurnier_has_SpielerTableControl(new Turnier_has_SpielerTableControl(this.mainControl));

			for (int index = 0; index < this.mainControl.getTurnier().getAnzahlGruppen(); index++) {

				ladebalkenView.iterate();
				if (mainControl.getTurnier().getTurnierId() < 0) {
					saveOK1 = this.mainControl.getTurnierTableControl().insertTurnier();
				} else {
					this.mainControl.getTurnierTableControl().updateTurnier(this.mainControl.getTurnier());
					saveOK1 = true;
				}
				ladebalkenView.iterate();
				if (mainControl.getTurnier().getGruppe()[index].getGruppeId() < 0) {
					saveOK2 = this.mainControl.getGruppenTableControl().insertGruppe(index);
					ladebalkenView.iterate();
					ladebalkenView.iterate();
					saveOK4 = this.mainControl.getPartienTableControl().insertPartien(index);
					this.mainControl.getTurnier_has_SpielerTableControl().insertTurnier_has_Spieler(index);
				} else {
					saveOK2 = this.mainControl.getGruppenTableControl().updateGruppe(index);
					ladebalkenView.iterate();
					ladebalkenView.iterate();
					saveOK4 = this.mainControl.getPartienTableControl().updatePartien(index);

				}
				ladebalkenView.iterate();
				if (saveOK1 && saveOK2 && saveOK4) {

				} else {
					JOptionPane.showMessageDialog(null, "Fehler: Turnier "
							+ this.mainControl.getTurnier().getTurnierName() + " wurde nicht gespeichert!");
					return false;

				}
			}
			if (saveOK1 && saveOK2 && saveOK4) {
				JOptionPane.showMessageDialog(null,
						"Turnier " + this.mainControl.getTurnier().getTurnierName() + " wurde gespeichert! \n");
				return true;
			} else {

				JOptionPane.showMessageDialog(null, "Fehler: Turnier " + this.mainControl.getTurnier().getTurnierName()
						+ " wurde nicht gespeichert!");
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Erst nach der Eingabe aller Gruppen\n" + "kann gespeichert werden.");
			return false;
		}
	}

}
