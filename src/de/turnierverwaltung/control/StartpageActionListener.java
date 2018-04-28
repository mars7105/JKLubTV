package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.control.ratingdialog.DWZListToSQLITEControl;
import de.turnierverwaltung.control.ratingdialog.ELOListToSQLITEControl;
import de.turnierverwaltung.model.rating.SQLitePlayerDWZList;
import de.turnierverwaltung.model.rating.SQLitePlayerELOList;

public class StartpageActionListener {
	MainControl mainControl;
	StartpageControl startpageControl;

	public StartpageActionListener(MainControl mainControl, StartpageControl startpageControl) {
		super();
		this.mainControl = mainControl;
		this.startpageControl = startpageControl;
	}

	public void addActionListener() {
		startpageControl.getStartpageDWZPanel().getOpenVereineCSVLabel()
				.setText(mainControl.getPropertiesControl().getPathToVereineCVS());

		startpageControl.getStartpageDWZPanel().getOpenPlayersCSVLabel()
				.setText(mainControl.getPropertiesControl().getPathToPlayersCSV());

		startpageControl.getStartpageELOPanel().getOpenPlayersELOLabel()
				.setText(mainControl.getPropertiesControl().getPathToPlayersELO());
		String filename = mainControl.getPropertiesControl().getPathToPlayersELO();
		int positionEXT = filename.lastIndexOf('.');

		if (positionEXT > 0) {
			String newFile = filename.substring(positionEXT);
			if (newFile.equals(".sqlite")) {
				SQLitePlayerELOList spelolist = new SQLitePlayerELOList();
				Boolean checkDB = spelolist.checkDatabase(filename);
				if (checkDB == true) {
					startpageControl.getStartpageELOPanel().getConvertELOToSQLITEButton().setEnabled(false);
					startpageControl.getStartpage().getEloPanel().addCheckItem();

				} else {
					mainControl.getPropertiesControl().setPathToPlayersELO("");
					// mainControl.getPropertiesControl().writeProperties();
				}

			} else {
				startpageControl.getStartpageELOPanel().getConvertELOToSQLITEButton().setEnabled(true);

			}
		} else {
			startpageControl.getStartpageELOPanel().getConvertELOToSQLITEButton().setEnabled(false);

		}

		filename = mainControl.getPropertiesControl().getPathToPlayersCSV();
		positionEXT = filename.lastIndexOf('.');
		if (positionEXT > 0) {
			String newFile = filename.substring(positionEXT);
			if (newFile.equals(".sqlite")) {
				// SQLitePlayerDWZList spdwzlist = new SQLitePlayerDWZList();
				// Boolean checkDB = spdwzlist.checkDatabase(filename);
				// if (checkDB == true) {
				// startpageControl.getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(false);
				// startpageControl.getStartpage().getDwzPanel().checkItem();
				// } else {
				// mainControl.getPropertiesControl().setPathToPlayersCSV("");
				//// mainControl.getPropertiesControl().writeProperties();
				// }
				startpageControl.getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(false);
				startpageControl.getStartpage().getDwzPanel().addCheckItem();
			} else {
				startpageControl.getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(true);

			}
		} else {
			startpageControl.getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(false);

		}
		startpageControl.getStartpage().getButtonPane().getOkButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startpageControl.getDialog().dispose();
			}
		});
		startpageControl.getStartpageDWZPanel().getOpenVereineCSVButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// vereine.csv
				File path = new File(mainControl.getPropertiesControl().getDefaultPath());

				final JFileChooser fc = new JFileChooser(path);
				FileFilter filter = new FileNameExtensionFilter("CSV file", "csv", "CSV");

				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(startpageControl.getStartpageDWZPanel());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the
					// file.
					mainControl.getPropertiesControl().setPathToVereineCVS(file.getAbsolutePath());
					mainControl.getPropertiesControl().writeProperties();
					startpageControl.getStartpageDWZPanel().getOpenVereineCSVLabel()
							.setText(mainControl.getPropertiesControl().getPathToVereineCVS());

				}

			}
		});
		startpageControl.getStartpageDWZPanel().getConvertDWZToSQLITEButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SQLitePlayerDWZList spdwzlist = new SQLitePlayerDWZList();
				Boolean checkDB = spdwzlist.checkDatabase(mainControl.getPropertiesControl().getPathToPlayersCSV());
				if (checkDB == true) {
					DWZListToSQLITEControl dwzL = new DWZListToSQLITEControl(mainControl);
					dwzL.convertDWZListToSQLITE();

					startpageControl.getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(false);
					startpageControl.getStartpage().getDwzPanel().addCheckItem();
					startpageControl.getStartpageDWZPanel().getOpenPlayersCSVLabel()
							.setText(mainControl.getPropertiesControl().getPathToPlayersCSV());

					startpageControl.getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(false);
				}
			}
		});
		startpageControl.getStartpageDWZPanel().getOpenPlayersCSVButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// spieler.csv
				File path = new File(mainControl.getPropertiesControl().getDefaultPath());

				final JFileChooser fc = new JFileChooser(path);
				FileFilter filter = new FileNameExtensionFilter("CSV or SQLite file", "csv", "CSV", "sqlite", "SQLite");

				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(startpageControl.getStartpageDWZPanel());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the
					// file.

					mainControl.getPropertiesControl().setPathToPlayersCSV(file.getAbsolutePath());
					mainControl.getPropertiesControl().writeProperties();
					startpageControl.getStartpageDWZPanel().getOpenPlayersCSVLabel()
							.setText(mainControl.getPropertiesControl().getPathToPlayersCSV());

					String filename = file.getName();
					int positionEXT = filename.lastIndexOf('.');

					if (positionEXT > 0) {
						String newFile = filename.substring(positionEXT);
						if (newFile.equals(".sqlite")) {
							// SQLitePlayerDWZList spdwzlist = new SQLitePlayerDWZList();
							// Boolean checkDB = spdwzlist.checkDatabase(filename);
							// if (checkDB == true) {
							// startpageControl.getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(false);
							// startpageControl.getStartpage().getDwzPanel().checkItem();
							// } else {
							// mainControl.getPropertiesControl().setPathToPlayersCSV("");
							// mainControl.getPropertiesControl().writeProperties();
							// }
							startpageControl.getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(false);
							startpageControl.getStartpage().getDwzPanel().addCheckItem();
						} else {
							startpageControl.getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(true);

						}
					}
				}

			}
		});

		startpageControl.getStartpageELOPanel().getConvertELOToSQLITEButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				ELOListToSQLITEControl eloL = new ELOListToSQLITEControl(mainControl);
				eloL.convertELOListToSQLITE();
				startpageControl.getStartpageELOPanel().getOpenPlayersELOLabel()
						.setText(mainControl.getPropertiesControl().getPathToPlayersELO());

				startpageControl.getStartpageELOPanel().getConvertELOToSQLITEButton().setEnabled(false);
			}

		});
		startpageControl.getStartpageELOPanel().getOpenPlayersELOButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// spieler.csv
				File path = new File(mainControl.getPropertiesControl().getDefaultPath());

				final JFileChooser fc = new JFileChooser(path);
				FileFilter filter = new FileNameExtensionFilter("TXT or SQLite file", "txt", "TXT", "sqlite", "SQLite");

				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(startpageControl.getStartpageELOPanel());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the
					// file.
					mainControl.getPropertiesControl().setPathToPlayersELO(file.getAbsolutePath());
					mainControl.getPropertiesControl().writeProperties();
					startpageControl.getStartpageELOPanel().getOpenPlayersELOLabel()
							.setText(mainControl.getPropertiesControl().getPathToPlayersELO());

					String filename = file.getName();
					int positionEXT = filename.lastIndexOf('.');

					if (positionEXT > 0) {
						String newFile = filename.substring(positionEXT);
						if (newFile.equals(".sqlite")) {
							// SQLitePlayerELOList spelolist = new SQLitePlayerELOList();
							// Boolean checkDB = spelolist.checkDatabase(filename);
							// if (checkDB == true) {
							// startpageControl.getStartpageELOPanel().getConvertELOToSQLITEButton().setEnabled(false);
							// startpageControl.getStartpage().getEloPanel().checkItem();
							//
							// } else {
							// mainControl.getPropertiesControl().setPathToPlayersELO("");
							// }
							startpageControl.getStartpageELOPanel().getConvertELOToSQLITEButton().setEnabled(false);
							startpageControl.getStartpage().getEloPanel().addCheckItem();
						} else {
							startpageControl.getStartpageELOPanel().getConvertELOToSQLITEButton().setEnabled(true);

						}
					}
				}

			}
		});

	}
}
