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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.view.MenueView;

public class MenueControl implements ActionListener {
	private MainControl mainControl;
	private MenueView turnierMenue;
	private Turnier turnier;
	private Boolean warnHinweis;
	private String fileName = " "; //$NON-NLS-1$
	public MenueControl(MainControl mainControl) {
		warnHinweis = false;
		this.mainControl = mainControl;
		turnier = this.mainControl.getTurnier();
//		turnierMenue = this.mainControl.getMenueView();
		turnierMenue.setVisible(true);
		turnierMenue.getMntmLaden().addActionListener(this);
		turnierMenue.getMntmBeenden().addActionListener(this);
		turnierMenue.getMntmSpeichern().addActionListener(this);
		turnierMenue.getMntmSpeichern().setEnabled(false);
		turnierMenue.getMntmSpielerLaden().addActionListener(this);
		turnierMenue.getMntmDBNeu().addActionListener(this);
		turnierMenue.getMntmDBLaden().addActionListener(this);
		this.mainControl.getStandardView();
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == turnierMenue.getMntmSpielerLaden()) {
			int abfrage = warnHinweis(
					Messages.getString("MenueControl.1") + Messages.getString("MenueControl.2")); //$NON-NLS-1$ //$NON-NLS-2$
			if (abfrage == 0) {
				
				mainControl.resetApp();
				mainControl.setSpielerEditierenControl(new SpielerLadenControl(mainControl));
				mainControl.getSpielerEditierenControl().updateSpielerListe();
			}
		}

		if (arg0.getSource() == turnierMenue.getMntmSpeichern()) {

			@SuppressWarnings("unused")
			Boolean ok = this.mainControl.getSaveTurnierControl().saveChangedPartien();


		}
		if (arg0.getSource() == turnierMenue.getMntmLaden()) {
			int abfrage = warnHinweis(
					Messages.getString("MenueControl.3") + Messages.getString("MenueControl.4")); //$NON-NLS-1$ //$NON-NLS-2$
			if (abfrage == 0) {
				mainControl.resetApp();
				mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
				mainControl.getTurnierTableControl().loadTurnierListe();
				mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(this.mainControl));
				mainControl.getTurnierListeLadenControl().loadTurnierListe();

			}
		}
		if (arg0.getSource() == turnierMenue.getMntmBeenden()) {

			turnier = this.mainControl.getTurnier();
			if (turnier == null) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			} else {
				// Custom button text
				Object[] options = { Messages.getString("MenueControl.5"), Messages.getString("MenueControl.6") }; //$NON-NLS-1$ //$NON-NLS-2$
				int abfrage = JOptionPane.showOptionDialog(null,
						Messages.getString("MenueControl.7") + Messages.getString("MenueControl.8"), //$NON-NLS-1$ //$NON-NLS-2$
						Messages.getString("MenueControl.9"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, //$NON-NLS-1$
						options, options[1]);
				if (abfrage == 0) {
					System.exit(JFrame.EXIT_ON_CLOSE);

				}
			}
		}
		if (arg0.getSource() == turnierMenue.getMntmDBLaden()) {
			int abfrage = warnHinweis(
					Messages.getString("MenueControl.10") + Messages.getString("MenueControl.11")); //$NON-NLS-1$ //$NON-NLS-2$
			if (abfrage == 0) {
				mainControl.resetApp();
//				mainControl.datenbankMenueView(false);
				// Create a file chooser
				JFileChooser fc = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter(Messages.getString("MenueControl.12"), "ktv"); //$NON-NLS-1$ //$NON-NLS-2$
				fc.addChoosableFileFilter(filter);
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					fileName = file.getPath();
					// This is where a real application would open the file.
					SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
//					mainControl.datenbankMenueView(true);
					if (mainControl.getTurnierTableControl() == null) {
						mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
						mainControl.getTurnierTableControl().loadTurnierListe();
						mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(this.mainControl));
						mainControl.getTurnierListeLadenControl().loadTurnierListe();
					} else {
						mainControl.resetApp();
						mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
						mainControl.getTurnierTableControl().loadTurnierListe();
						mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(this.mainControl));
						mainControl.getTurnierListeLadenControl().loadTurnierListe();
					}

				} else {
					JOptionPane.showMessageDialog(null, Messages.getString("MenueControl.14")); //$NON-NLS-1$
				}

			}
		}
		if (arg0.getSource() == turnierMenue.getMntmDBNeu()) {
			int abfrage = warnHinweis(
					Messages.getString("MenueControl.15") + Messages.getString("MenueControl.16")); //$NON-NLS-1$ //$NON-NLS-2$
			if (abfrage == 0) {
				mainControl.resetApp();
//				mainControl.datenbankMenueView(false);
				String filename = JOptionPane.showInputDialog(null, Messages.getString("MenueControl.17"), Messages.getString("MenueControl.18"), //$NON-NLS-1$ //$NON-NLS-2$
						JOptionPane.PLAIN_MESSAGE);
				if (filename != null) {
					filename += ".ktv"; //$NON-NLS-1$
					fileName = filename;

					JFileChooser savefile = new JFileChooser();
					FileFilter filter = new FileNameExtensionFilter(Messages.getString("MenueControl.20"), "ktv"); //$NON-NLS-1$ //$NON-NLS-2$
					savefile.addChoosableFileFilter(filter);
					savefile.setFileFilter(filter);
					savefile.setDialogType(JFileChooser.SAVE_DIALOG);
					savefile.setSelectedFile(new File(filename));
					BufferedWriter writer;
					int sf = savefile.showSaveDialog(null);
					if (sf == JFileChooser.APPROVE_OPTION) {
						try {
							File file = savefile.getSelectedFile();
							writer = new BufferedWriter(new FileWriter(savefile.getSelectedFile()));
							writer.write(""); //$NON-NLS-1$
							writer.close();

							// true for rewrite, false for override
							SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
							SQLiteControl sqlC = new SQLiteControl();
							sqlC.createAllTables();
//							mainControl.datenbankMenueView(true);
							JOptionPane.showMessageDialog(null, Messages.getString("MenueControl.23"), "File Saved", //$NON-NLS-1$ //$NON-NLS-2$
									JOptionPane.INFORMATION_MESSAGE);

						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if (sf == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null, Messages.getString("MenueControl.25")); //$NON-NLS-1$
					}
				}
			}
		}

	}

	public void clickedMenueItemNeu() {
		turnier = this.mainControl.getTurnier();
		if (turnier == null) {
			mainControl.setTurnierControl(new TurnierControl(mainControl));
		} else {
			// Custom button text
			Object[] options = { Messages.getString("MenueControl.26"), Messages.getString("MenueControl.27") }; //$NON-NLS-1$ //$NON-NLS-2$
			int abfrage = JOptionPane.showOptionDialog(null,
					Messages.getString("MenueControl.28") + Messages.getString("MenueControl.29"), //$NON-NLS-1$ //$NON-NLS-2$
					Messages.getString("MenueControl.30"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, //$NON-NLS-1$
					options[1]);
			if (abfrage == 0) {
				mainControl.resetApp();
				mainControl.setTurnierControl(new TurnierControl(mainControl));
			}
		}

	}

	public Boolean getWarnHinweis() {
		return warnHinweis;
	}

	public void setDatenbankMenue(boolean enable) {
		turnierMenue.getMnEditMenu().setEnabled(enable);
	}

	public void setWarnHinweis(Boolean warnHinweis) {
		this.warnHinweis = warnHinweis;
	}

	private int warnHinweis(String hinweisText) {
		int abfrage = 0;
		if (warnHinweis) {
			abfrage = 1;
			// Custom button text
			Object[] options = { Messages.getString("MenueControl.31"), Messages.getString("MenueControl.32") }; //$NON-NLS-1$ //$NON-NLS-2$
			abfrage = JOptionPane.showOptionDialog(null, hinweisText, Messages.getString("MenueControl.33"), JOptionPane.YES_NO_CANCEL_OPTION, //$NON-NLS-1$
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		}

		return abfrage;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
