package de.turnierverwaltung.control.settingsdialog;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.control.ratingdialog.DWZListToSQLITEControl;
import de.turnierverwaltung.control.ratingdialog.ELOListToSQLITEControl;
import de.turnierverwaltung.view.settingsdialog.SettingsView;
import say.swing.JFontChooser;

public class ActionListenerSettingsControl {
	private MainControl mainControl;
	private SettingsControl esControl;
	private JDialog dialog;

	public ActionListenerSettingsControl(MainControl mainControl, SettingsControl esControl) {
		super();
		this.mainControl = mainControl;
		this.esControl = esControl;
		addPropertiesActionListener();
	}

	public void addActionListeners() {
		esControl.getEigenschaftenView().getFontChooserButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFontChooser fontChooser = esControl.getEigenschaftenView().getFontChooser();
				int result = fontChooser.showDialog(esControl.getEigenschaftenView());
				if (result == JFontChooser.OK_OPTION) {
					Font selectedFont = fontChooser.getSelectedFont();
					
					
					MainControl.setUIFont(selectedFont);

				}
			}
		});
		esControl.getEigenschaftenView().getOkButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainControl.getPropertiesControl().writeSettingsDialogProperties(dialog.getBounds().x,
						dialog.getBounds().y, dialog.getBounds().width, dialog.getBounds().height);
				dialog.dispose();
				PropertiesControl ppC = mainControl.getPropertiesControl();
				SettingsView settingsView = esControl.getEigenschaftenView();
				ppC.setTableComumnBlack(settingsView.getBlackTextField().getText());
				ppC.setTableComumnWhite(settingsView.getWhiteTextField().getText());
				ppC.setTableComumnMeeting(settingsView.getMeetingTextField().getText());
				ppC.setTableComumnNewDWZ(settingsView.getNewDWZTextField().getText());
				ppC.setTableComumnOldDWZ(settingsView.getOldDWZTextField().getText());
				ppC.setTableComumnNewELO(settingsView.getNewELOTextField().getText());
				ppC.setTableComumnOldELO(settingsView.getOldELOTextField().getText());
				ppC.setTableComumnPlayer(settingsView.getPlayerTextField().getText());
				ppC.setTableComumnPoints(settingsView.getPointsTextField().getText());
				ppC.setTableComumnRanking(settingsView.getRankingTextField().getText());
				ppC.setTableComumnResult(settingsView.getResultTextField().getText());
				ppC.setTableComumnSonnebornBerger(settingsView.getSbbTextField().getText());
				ppC.setTableComumnRound(settingsView.getRoundTextField().getText());
				ppC.setCutForename(settingsView.getForenameLengthBox().getValue());
				ppC.setCutSurname(settingsView.getSurnameLengthBox().getValue());
				ppC.setWebserverPath(settingsView.getWebserverPathTextField().getText());
				ppC.checkCrossTableColumnForDoubles();
				ppC.checkMeetingTableColumnForDoubles();

				ppC.writeProperties();
				esControl.setTableColumns();

			}

		});
		esControl.getEigenschaftenView().getOpenVereineCSVButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// vereine.csv
				File path = new File(mainControl.getPropertiesControl().getDefaultPath());

				final JFileChooser fc = new JFileChooser(path);
				FileFilter filter = new FileNameExtensionFilter("CSV file", "csv", "CSV");

				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(esControl.getEigenschaftenView());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the
					// file.
					mainControl.getPropertiesControl().setPathToVereineCVS(file.getAbsolutePath());
					mainControl.getPropertiesControl().writeProperties();
					esControl.getEigenschaftenView()
							.setOpenVereineCSVLabel(mainControl.getPropertiesControl().getPathToVereineCVS());
				}

			}
		});
		esControl.getEigenschaftenView().getConvertDWZToSQLITEButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				DWZListToSQLITEControl dwzL = new DWZListToSQLITEControl(mainControl);
				dwzL.convertDWZListToSQLITE();
				esControl.getEigenschaftenView()
						.setOpenPlayersCSVLabel(mainControl.getPropertiesControl().getPathToPlayersCSV());
				esControl.getEigenschaftenView().getConvertDWZToSQLITEButton().setEnabled(false);
			}
		});
		esControl.getEigenschaftenView().getOpenPlayersCSVButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// spieler.csv
				File path = new File(mainControl.getPropertiesControl().getDefaultPath());

				final JFileChooser fc = new JFileChooser(path);
				FileFilter filter = new FileNameExtensionFilter("CSV or SQLite file", "csv", "CSV", "sqlite", "SQLite");

				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(esControl.getEigenschaftenView());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the
					// file.

					mainControl.getPropertiesControl().setPathToPlayersCSV(file.getAbsolutePath());
					mainControl.getPropertiesControl().writeProperties();
					esControl.getEigenschaftenView()
							.setOpenPlayersCSVLabel(mainControl.getPropertiesControl().getPathToPlayersCSV());

					String filename = file.getName();
					int positionEXT = filename.lastIndexOf('.');

					if (positionEXT > 0) {
						String newFile = filename.substring(positionEXT);
						if (newFile.equals(".sqlite")) {
							esControl.getEigenschaftenView().getConvertDWZToSQLITEButton().setEnabled(false);
						} else {
							esControl.getEigenschaftenView().getConvertDWZToSQLITEButton().setEnabled(true);

						}
					}
				}

			}
		});

		esControl.getEigenschaftenView().getConvertELOToSQLITEButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ELOListToSQLITEControl eloL = new ELOListToSQLITEControl(mainControl);
				eloL.convertELOListToSQLITE();
				esControl.getEigenschaftenView()
						.setOpenPlayersELOLabel(mainControl.getPropertiesControl().getPathToPlayersELO());
				esControl.getEigenschaftenView().getConvertELOToSQLITEButton().setEnabled(false);
			}
		});
		esControl.getEigenschaftenView().getOpenPlayersELOButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// spieler.csv
				File path = new File(mainControl.getPropertiesControl().getDefaultPath());

				final JFileChooser fc = new JFileChooser(path);
				FileFilter filter = new FileNameExtensionFilter("TXT or SQLite file", "txt", "TXT", "sqlite", "SQLite");

				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(esControl.getEigenschaftenView());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the
					// file.
					mainControl.getPropertiesControl().setPathToPlayersELO(file.getAbsolutePath());
					mainControl.getPropertiesControl().writeProperties();
					esControl.getEigenschaftenView()
							.setOpenPlayersELOLabel(mainControl.getPropertiesControl().getPathToPlayersELO());
					String filename = file.getName();
					int positionEXT = filename.lastIndexOf('.');

					if (positionEXT > 0) {
						String newFile = filename.substring(positionEXT);
						if (newFile.equals(".sqlite")) {
							esControl.getEigenschaftenView().getConvertELOToSQLITEButton().setEnabled(false);
						} else {
							esControl.getEigenschaftenView().getConvertELOToSQLITEButton().setEnabled(true);

						}
					}
				}

			}
		});

		esControl.getEigenschaftenView().getOpenDefaultPathButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File path = new File(mainControl.getPropertiesControl().getDefaultPath());
				final JFileChooser fc = new JFileChooser(path);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setMultiSelectionEnabled(false);

				int returnVal = fc.showOpenDialog(esControl.getEigenschaftenView());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					// This is where a real application would open the
					// file.
					mainControl.getPropertiesControl().setDefaultPath(file.getAbsolutePath());
					mainControl.getPropertiesControl().writeProperties();
					esControl.getEigenschaftenView()
							.setOpenDefaultPathLabel(mainControl.getPropertiesControl().getDefaultPath());
				}

			}
		});
		esControl.getEigenschaftenView()
				.setOpenVereineCSVLabel(mainControl.getPropertiesControl().getPathToVereineCVS());
		esControl.getEigenschaftenView()
				.setOpenPlayersCSVLabel(mainControl.getPropertiesControl().getPathToPlayersCSV());
		esControl.getEigenschaftenView()
				.setOpenPlayersELOLabel(mainControl.getPropertiesControl().getPathToPlayersELO());
		esControl.getEigenschaftenView().getGermanLanguageCheckBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainControl.getLanguagePropertiesControl().setLanguageToGerman();

				mainControl.getPropertiesControl().writeProperties();

			}
		});
		esControl.getEigenschaftenView().getEnglishLanguageCheckBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainControl.getLanguagePropertiesControl().setLanguageToEnglish();
				mainControl.getPropertiesControl().writeProperties();

			}
		});
		esControl.getEigenschaftenView().getSpielerListeAuswahlBox().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int anzahlProTab = esControl.getEigenschaftenView().getSpielerListeAuswahlBox().getSelectedIndex();
				mainControl.getPropertiesControl().setSpielerProTab(anzahlProTab);
				mainControl.getPropertiesControl().writeProperties();

			}
		});
		esControl.getEigenschaftenView().getTurnierListeAuswahlBox().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int anzahlProTab = esControl.getEigenschaftenView().getTurnierListeAuswahlBox().getSelectedIndex();
				mainControl.getPropertiesControl().setTurniereProTab(anzahlProTab);
				mainControl.getPropertiesControl().writeProperties();

			}
		});

		String filename = mainControl.getPropertiesControl().getPathToPlayersELO();
		int positionEXT = filename.lastIndexOf('.');

		if (positionEXT > 0) {
			String newFile = filename.substring(positionEXT);
			if (newFile.equals(".sqlite")) {
				esControl.getEigenschaftenView().getConvertELOToSQLITEButton().setEnabled(false);
			} else {
				esControl.getEigenschaftenView().getConvertELOToSQLITEButton().setEnabled(true);

			}
		} else {
			esControl.getEigenschaftenView().getConvertELOToSQLITEButton().setEnabled(false);

		}

		filename = mainControl.getPropertiesControl().getPathToPlayersCSV();
		positionEXT = filename.lastIndexOf('.');
		if (positionEXT > 0) {
			String newFile = filename.substring(positionEXT);
			if (newFile.equals(".sqlite")) {
				esControl.getEigenschaftenView().getConvertDWZToSQLITEButton().setEnabled(false);
			} else {
				esControl.getEigenschaftenView().getConvertDWZToSQLITEButton().setEnabled(true);

			}
		} else {
			esControl.getEigenschaftenView().getConvertDWZToSQLITEButton().setEnabled(false);

		}

	}

	private void addPropertiesActionListener() {
		mainControl.getNaviView().getPropertiesButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				mainControl.getSettingsControl().setEigenschaftenView(new SettingsView());
				SettingsView eigenschaftenView = mainControl.getSettingsControl().getEigenschaftenView();
				mainControl.getSettingsControl().setItemListenerControl(
						new ItemListenerSettingsControl(mainControl, mainControl.getSettingsControl()));
				mainControl.getSettingsControl().getItemListenerControl().addItemListeners();
				if (dialog == null) {
					dialog = new JDialog();
				} else {
					dialog.dispose();
					dialog = new JDialog();
				}
				dialog.setAlwaysOnTop(true);
				dialog.getContentPane().add(eigenschaftenView);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(mainControl.getPropertiesControl().getSettingsDialogX(),
						mainControl.getPropertiesControl().getSettingsDialogY(),
						mainControl.getPropertiesControl().getSettingsDialogWidth(),
						mainControl.getPropertiesControl().getSettingsDialogHeight());
				dialog.setEnabled(true);
				dialog.setVisible(true);
				if (mainControl.getPropertiesControl() == null) {

					mainControl.setPropertiesControl(new PropertiesControl(mainControl));

				}
				PropertiesControl ppC = mainControl.getPropertiesControl();
				ppC.readProperties();
				eigenschaftenView.getCheckBoxHeaderFooter().setSelected(ppC.getOnlyTables());
				eigenschaftenView.getCheckBoxohneDWZ().setSelected(ppC.getNoDWZ());
				eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(ppC.getNoFolgeDWZ());
				eigenschaftenView.getCheckBoxohneELO().setSelected(ppC.getNoELO());
				eigenschaftenView.getCheckBoxohneFolgeELO().setSelected(ppC.getNoFolgeELO());
				eigenschaftenView.getSpielerListeAuswahlBox().setSelectedIndex(ppC.getSpielerProTab());
				eigenschaftenView.getTurnierListeAuswahlBox().setSelectedIndex(ppC.getTurniereProTab());
				eigenschaftenView.getForenameLengthBox().setValue(ppC.getCutForename());
				eigenschaftenView.getSurnameLengthBox().setValue(ppC.getCutSurname());
				eigenschaftenView.getWebserverPathTextField().setText(ppC.getWebserverPath());
				// eigenschaftenView.getCheckBoxohneDWZ().setSelected(ppC.getNoDWZ());
				eigenschaftenView.getCheckBoxhtmlToClipboard().setSelected(ppC.gethtmlToClipboard());
				if (eigenschaftenView.getCheckBoxohneDWZ().isSelected() == true) {
					eigenschaftenView.getCheckBoxohneFolgeDWZ().setSelected(true);
					eigenschaftenView.getCheckBoxohneFolgeDWZ().setEnabled(false);
					ppC.setNoFolgeDWZ(true);
				}
				if (eigenschaftenView.getCheckBoxohneELO().isSelected() == true) {
					eigenschaftenView.getCheckBoxohneFolgeELO().setSelected(true);
					eigenschaftenView.getCheckBoxohneFolgeELO().setEnabled(false);
					ppC.setNoFolgeELO(true);
				}
				eigenschaftenView.getCheckBoxPDFLinks().setSelected(ppC.getPDFLinks());
				eigenschaftenView.getWebserverPathTextField().setEnabled(ppC.getPDFLinks());

				if (mainControl.getPropertiesControl().getLanguage().equals("german")) { //$NON-NLS-1$
					eigenschaftenView.getGermanLanguageCheckBox().setSelected(true);
					eigenschaftenView.getEnglishLanguageCheckBox().setSelected(false);
					mainControl.getLanguagePropertiesControl().setLanguageToGerman();
				} else if (mainControl.getPropertiesControl().getLanguage().equals("english")) { //$NON-NLS-1$
					eigenschaftenView.getGermanLanguageCheckBox().setSelected(false);
					eigenschaftenView.getEnglishLanguageCheckBox().setSelected(true);
					mainControl.getLanguagePropertiesControl().setLanguageToEnglish();
				}
				eigenschaftenView.setOpenDefaultPathLabel(ppC.getDefaultPath());

				esControl.setTableColumns();
				addActionListeners();
				esControl.getItemListenerControl().addItemListeners();

			}

		});
	}

}
