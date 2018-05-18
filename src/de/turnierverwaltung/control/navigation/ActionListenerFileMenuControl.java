package de.turnierverwaltung.control.navigation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.control.StartpageControl;
import de.turnierverwaltung.control.playerlist.PlayerListControl;
import de.turnierverwaltung.control.sqlite.SQLControl;
import de.turnierverwaltung.control.sqlite.SQLTournamentControl;
import de.turnierverwaltung.control.sqlite.SaveTournamentControl;
import de.turnierverwaltung.control.tournamentlist.ActionListenerTournamentItemsControl;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.sqlite.SQLiteDAOFactory;
import de.turnierverwaltung.view.navigation.NaviView;

public class ActionListenerFileMenuControl implements ActionListener {
	private final MainControl mainControl;
	private JButton newdbButton;
	private JButton loaddbButton;
	private JButton exitButton;
	private JButton wizardButton;
	private final NaviView naviView;
	private ChangeListenerTabControl turnierAnsicht;

	public ActionListenerFileMenuControl(final MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		naviView = mainControl.getNaviView();
		newdbButton = naviView.getNewDatabseButton();
		loaddbButton = naviView.getLoadDatabaseButton();
		wizardButton = naviView.getWizardButton();
		wizardButton.addActionListener(this);
		exitButton = naviView.getExitButton();
		this.mainControl.getActionListenerPairingsMenuControl().getPairingIsActive();
		newdbButton = naviView.getNewDatabseButton();
		newdbButton.addActionListener(this);
		loaddbButton = naviView.getLoadDatabaseButton();
		loaddbButton.addActionListener(this);
		exitButton = naviView.getExitButton();
		exitButton.addActionListener(this);
		turnierAnsicht = new ChangeListenerTabControl(mainControl);

		mainControl.getHauptPanel().addChangeListener(turnierAnsicht);
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		final PropertiesControl prop = mainControl.getPropertiesControl();
		if (arg0.getSource().equals(newdbButton)) {

			final int abfrage = warnHinweis();
			if (abfrage == 0) {
				String filename = JOptionPane.showInputDialog(null, Messages.getString("NaviController.5"), //$NON-NLS-1$
						Messages.getString("NaviController.6"), //$NON-NLS-1$
						JOptionPane.PLAIN_MESSAGE);

				if (filename != null) {
					filename += ".sqlite";
					final File path = new File(mainControl.getPropertiesControl().getDefaultPath());

					final JFileChooser savefile = new JFileChooser(path);
					final FileFilter filter = new FileNameExtensionFilter(Messages.getString("NaviController.8"),
							"sqlite");
					savefile.addChoosableFileFilter(filter);
					savefile.setFileFilter(filter);
					savefile.setDialogType(JFileChooser.SAVE_DIALOG);
					savefile.setSelectedFile(new File(filename));
					BufferedWriter writer;
					final int sf = savefile.showSaveDialog(null);
					if (sf == JFileChooser.APPROVE_OPTION) {
						mainControl.resetApp();
						try {
							final File file = savefile.getSelectedFile();
							writer = new BufferedWriter(new FileWriter(savefile.getSelectedFile()));
							writer.write(""); //$NON-NLS-1$
							writer.close();

							// true for rewrite, false for override
							SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
							prop.setPathToDatabase(SQLiteDAOFactory.getDB_PATH());
							mainControl.setTitle(Messages.getString("MainControl.8") //$NON-NLS-1$
									+ SQLiteDAOFactory.getDB_PATH());
							prop.setDefaultPath(file.getParent());
							mainControl.getSettingsControl().getEigenschaftenView()
									.setOpenDefaultPathLabel(file.getParent());
							final SQLControl sqlC = new SQLControl();
							sqlC.createAllTables();
							JOptionPane.showMessageDialog(null, Messages.getString("NaviController.11"),
									// $NON-NLS-1$
									Messages.getString("NaviController.12"), //$NON-NLS-1$
									JOptionPane.INFORMATION_MESSAGE);
							mainControl.setNewTournament(false);
							mainControl.setSqlTournamentControl(new SQLTournamentControl(mainControl));

							mainControl.setPlayerListControl(new PlayerListControl(mainControl));
							mainControl.getPlayerListControl().updateSpielerListe();
							mainControl.setActionListenerTournamentItemsControl(
									new ActionListenerTournamentItemsControl(mainControl));
							mainControl.getActionListenerTournamentItemsControl().loadTurnierListe();

							prop.setPathToDatabase(SQLiteDAOFactory.getDB_PATH());
							turnierAnsicht = new ChangeListenerTabControl(mainControl);

							mainControl.getHauptPanel().addChangeListener(turnierAnsicht);

							naviView.updateUI();
							prop.setDatabaseUpdated(true);
							prop.writeProperties();
						} catch (final IOException e) {
							JOptionPane.showMessageDialog(null, Messages.getString("NaviController.13"));
						} catch (final SQLException e) {
							final ExceptionHandler eh = new ExceptionHandler(mainControl);
							eh.fileSQLError(e.getMessage());
						}
					} else if (sf == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null, Messages.getString("NaviController.14"));
					}
				}
			}
		}
		if (arg0.getSource().equals(loaddbButton))

		{

			final int abfrage = warnHinweis();
			if (abfrage == 0) {

				prop.setDatabaseUpdated(false);
				prop.writeProperties();

				// Create a file chooser
				final File path = new File(prop.getDefaultPath());

				final JFileChooser fc = new JFileChooser(path);
				final FileFilter filter = new FileNameExtensionFilter(Messages.getString("NaviController.15"), //$NON-NLS-1$
						Messages.getString("NaviController.16")); //$NON-NLS-1$
				fc.addChoosableFileFilter(filter);
				fc.setFileFilter(filter);
				final int returnVal = fc.showOpenDialog(null);
				try {
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						mainControl.resetApp();
						// mainControl.datenbankMenueView(false);
						final File file = fc.getSelectedFile();
						// This is where a real application would open the file.
						SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());

						mainControl.setNewTournament(false);
						// mainControl.getNaviView().getTabellenPanel().setVisible(false);
						mainControl.setSqlTournamentControl(new SQLTournamentControl(mainControl));
						// mainControl.getTurnierTableControl().loadTurnierListe();
						mainControl.setPlayerListControl(new PlayerListControl(mainControl));
						mainControl.getPlayerListControl().updateSpielerListe();
						mainControl.setActionListenerTournamentItemsControl(
								new ActionListenerTournamentItemsControl(mainControl));

						mainControl.getActionListenerTournamentItemsControl().loadTurnierListe();

						prop.setPathToDatabase(SQLiteDAOFactory.getDB_PATH());
						prop.setDefaultPath(file.getParent());
						mainControl.getSettingsControl().getEigenschaftenView()
								.setOpenDefaultPathLabel(file.getParent());
						prop.writeProperties();
						naviView.setPathToDatabase(new JLabel(SQLiteDAOFactory.getDB_PATH()));

						mainControl.setTitle(Messages.getString("MainControl.8") //$NON-NLS-1$
								+ SQLiteDAOFactory.getDB_PATH());
						turnierAnsicht = new ChangeListenerTabControl(mainControl);
						mainControl.getHauptPanel().addChangeListener(turnierAnsicht);
						for (int i = 0; i < mainControl.getHauptPanel().getTabCount(); i++) {
							if (mainControl.getHauptPanel().getTitleAt(i)
									.equals(Messages.getString("NaviController.17"))) { //$NON-NLS-1$
								mainControl.getHauptPanel().setSelectedIndex(i);
							}
						}

						naviView.updateUI();
						mainControl.getHauptPanel().updateUI();
					} else {
						JOptionPane.showMessageDialog(null, Messages.getString("NaviController.18")); //$NON-NLS-1$
					}
				} catch (final SQLException e) {

					final ExceptionHandler eh = new ExceptionHandler(mainControl);
					eh.fileSQLError(e.getMessage());
				}
			}

		}

		if (arg0.getSource().equals(wizardButton)) {
			StartpageControl startpageControl = new StartpageControl(mainControl);
			startpageControl.createStartPanels();
		}
		if (arg0.getSource().equals(exitButton)) {
			final int abfrage = beendenHinweis();
			if (abfrage > 0) {

				if (this.mainControl.getHauptPanel().getTabCount() == TournamentConstants.TAB_ACTIVE_TOURNAMENT + 1) {
					if (this.mainControl.getChangedGames().isEmpty() == false) {
						SaveTournamentControl saveTournament = new SaveTournamentControl(mainControl);
						try {
							saveTournament.saveChangedPartien();
						} catch (SQLException e1) {
							final ExceptionHandler eh = new ExceptionHandler(mainControl);
							eh.fileSQLError(e1.getMessage());
						}

					}
				}
			}
			this.mainControl.getPropertiesControl().setBounds();
			this.mainControl.getPropertiesControl().writeProperties();
			System.exit(0);

		}

	}

	private int beendenHinweis() {
		int abfrage = 0;
		if (this.mainControl.getHauptPanel().getTabCount() == TournamentConstants.TAB_ACTIVE_TOURNAMENT + 1) {
			if (this.mainControl.getChangedGames().isEmpty() == false) {

				final String hinweisText = Messages.getString("NaviController.21") //$NON-NLS-1$
						+ Messages.getString("NaviController.22") //$NON-NLS-1$
						+ Messages.getString("NaviController.33"); //$NON-NLS-1$

				abfrage = 1;
				// Custom button text
				final Object[] options = { Messages.getString("NaviController.24"), //$NON-NLS-1$
						Messages.getString("NaviController.25") }; //$NON-NLS-1$
				abfrage = JOptionPane.showOptionDialog(mainControl, hinweisText,
						Messages.getString("NaviController.26"), //$NON-NLS-1$
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
			}
		}
		return abfrage;
	}

	public ChangeListenerTabControl getTurnierAnsicht() {
		return turnierAnsicht;
	}

	public void setTurnierAnsicht(final ChangeListenerTabControl turnierAnsicht) {
		this.turnierAnsicht = turnierAnsicht;
	}

	private int warnHinweis() {
		int abfrage = 0;
		final String hinweisText = Messages.getString("NaviController.21") //$NON-NLS-1$
				+ Messages.getString("NaviController.22") //$NON-NLS-1$
				+ Messages.getString("NaviController.23"); //$NON-NLS-1$
		if (mainControl.getNaviView().getTabellenPanel().isVisible() == true) {
			if (mainControl.getChangedGames().size() > 0) {
				abfrage = 1;
				// Custom button text
				final Object[] options = { Messages.getString("NaviController.37"), //$NON-NLS-1$
						Messages.getString("NaviController.1") }; //$NON-NLS-1$
				abfrage = JOptionPane.showOptionDialog(mainControl, hinweisText,
						Messages.getString("NaviController.26"), //$NON-NLS-1$
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
				try {
					mainControl.getSaveTournamentControl().saveChangedPartien();
				} catch (SQLException e) {
					final ExceptionHandler eh = new ExceptionHandler(mainControl);
					eh.fileSQLError(e.getMessage());
				}
			}
		}
		if (abfrage == 0) {
			mainControl.setNewTournament(false);

		}
		return abfrage;
	}

}
