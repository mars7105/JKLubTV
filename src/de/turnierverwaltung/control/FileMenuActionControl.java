package de.turnierverwaltung.control;

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
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.SQLiteDAOFactory;
import de.turnierverwaltung.view.NaviView;

public class FileMenuActionControl implements ActionListener {
	private MainControl mainControl;
	private JButton newdbButton;
	private JButton loaddbButton;
	private JButton exitButton;
	private NaviView naviView;
	private TurnierAnsicht turnierAnsicht;
	private boolean pairingIsActive;

	public FileMenuActionControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		this.naviView = mainControl.getNaviView();
		this.newdbButton = this.naviView.getNewDatabseButton();
		this.loaddbButton = this.naviView.getLoadDatabaseButton();
		this.exitButton = this.naviView.getExitButton();
		this.pairingIsActive = this.mainControl.getPairingsMenuActionControl().getPairingIsActive();
		newdbButton = naviView.getNewDatabseButton();
		newdbButton.addActionListener(this);
		loaddbButton = naviView.getLoadDatabaseButton();
		loaddbButton.addActionListener(this);
		exitButton = naviView.getExitButton();
		exitButton.addActionListener(this);
		turnierAnsicht = new TurnierAnsicht(mainControl);

		mainControl.getHauptPanel().addChangeListener(turnierAnsicht);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == newdbButton) {

			int abfrage = warnHinweis();
			if (abfrage == 0) {
				// this.mainControl.getNaviView().getTabellenPanel().setVisible(false);

				String filename = JOptionPane.showInputDialog(null, Messages.getString("NaviController.5"), //$NON-NLS-1$
						Messages.getString("NaviController.6"), //$NON-NLS-1$
						JOptionPane.PLAIN_MESSAGE);

				if (filename != null) {
					filename += ".ktv"; //$NON-NLS-1$
					File path = new File(mainControl.getPropertiesControl().getDefaultPath());
					JFileChooser savefile = new JFileChooser(path);
					FileFilter filter = new FileNameExtensionFilter(Messages.getString("NaviController.8"), "ktv"); //$NON-NLS-1$ //$NON-NLS-2$
					savefile.addChoosableFileFilter(filter);
					savefile.setFileFilter(filter);
					savefile.setDialogType(JFileChooser.SAVE_DIALOG);
					savefile.setSelectedFile(new File(filename));
					BufferedWriter writer;
					int sf = savefile.showSaveDialog(null);
					if (sf == JFileChooser.APPROVE_OPTION) {
						mainControl.resetApp();
						// mainControl.datenbankMenueView(false);
						try {
							File file = savefile.getSelectedFile();
							writer = new BufferedWriter(new FileWriter(savefile.getSelectedFile()));
							writer.write(""); //$NON-NLS-1$
							writer.close();

							// true for rewrite, false for override
							SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());
							mainControl.getPropertiesControl().setPathToDatabase(SQLiteDAOFactory.getDB_PATH());
							mainControl.setTitle(Messages.getString("MainControl.8") //$NON-NLS-1$
									+ SQLiteDAOFactory.getDB_PATH());
							mainControl.getPropertiesControl().setDefaultPath(file.getParent());
							mainControl.getEigenschaftenControl().getEigenschaftenView()
									.setOpenDefaultPathLabel(file.getParent());
							SQLControl sqlC = new SQLControl();
							sqlC.createAllTables();
							// mainControl.datenbankMenueView(true);
							JOptionPane.showMessageDialog(null, Messages.getString("NaviController.11"), //$NON-NLS-1$
									Messages.getString("NaviController.12"), //$NON-NLS-1$
									JOptionPane.INFORMATION_MESSAGE);
							this.mainControl.setNeuesTurnier(false);
							mainControl.setTurnierTableControl(new SQLTournamentControl(mainControl));
							// mainControl.getTurnierTableControl()
							// .loadTurnierListe();
							mainControl.setSpielerEditierenControl(new PlayerListControl(mainControl));
							mainControl.getSpielerEditierenControl().updateSpielerListe();
							mainControl.setTurnierListeLadenControl(new TournamentListControl(this.mainControl));
							mainControl.getTurnierListeLadenControl().loadTurnierListe();

							mainControl.getPropertiesControl().setPathToDatabase(SQLiteDAOFactory.getDB_PATH());
							mainControl.getPropertiesControl().writeProperties();
							turnierAnsicht = new TurnierAnsicht(mainControl);

							mainControl.getHauptPanel().addChangeListener(turnierAnsicht);

							naviView.updateUI();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, Messages.getString("NaviController.13")); //$NON-NLS-1$
						} catch (SQLException e) {
							mainControl.fileSQLError();
						}
					} else if (sf == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null, Messages.getString("NaviController.14")); //$NON-NLS-1$
					}
				}
			}
		}
		if (arg0.getSource() == loaddbButton)

		{

			int abfrage = warnHinweis();
			if (abfrage == 0) {

				// Create a file chooser
				File path = new File(mainControl.getPropertiesControl().getDefaultPath());

				JFileChooser fc = new JFileChooser(path);
				FileFilter filter = new FileNameExtensionFilter(Messages.getString("NaviController.15"), //$NON-NLS-1$
						Messages.getString("NaviController.16")); //$NON-NLS-1$
				fc.addChoosableFileFilter(filter);
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(null);
				try {
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						mainControl.resetApp();
						// mainControl.datenbankMenueView(false);
						File file = fc.getSelectedFile();
						// This is where a real application would open the file.
						SQLiteDAOFactory.setDB_PATH(file.getAbsolutePath());

						mainControl.setNeuesTurnier(false);
						// mainControl.getNaviView().getTabellenPanel().setVisible(false);
						mainControl.setTurnierTableControl(new SQLTournamentControl(mainControl));
						// mainControl.getTurnierTableControl().loadTurnierListe();
						mainControl.setSpielerEditierenControl(new PlayerListControl(mainControl));
						mainControl.getSpielerEditierenControl().updateSpielerListe();
						mainControl.setTurnierListeLadenControl(new TournamentListControl(this.mainControl));

						mainControl.getTurnierListeLadenControl().loadTurnierListe();

						mainControl.getPropertiesControl().setPathToDatabase(SQLiteDAOFactory.getDB_PATH());
						mainControl.getPropertiesControl().setDefaultPath(file.getParent());
						mainControl.getEigenschaftenControl().getEigenschaftenView()
								.setOpenDefaultPathLabel(file.getParent());
						mainControl.getPropertiesControl().writeProperties();
						naviView.setPathToDatabase(new JLabel(SQLiteDAOFactory.getDB_PATH()));

						mainControl.setTitle(Messages.getString("MainControl.8") //$NON-NLS-1$
								+ SQLiteDAOFactory.getDB_PATH());
						turnierAnsicht = new TurnierAnsicht(mainControl);
						mainControl.getHauptPanel().addChangeListener(turnierAnsicht);
						for (int i = 0; i < mainControl.getHauptPanel().getTabCount(); i++) {
							if (mainControl.getHauptPanel().getTitleAt(i)
									.equals(Messages.getString("NaviController.17"))) { //$NON-NLS-1$
								mainControl.getHauptPanel().setSelectedIndex(i);
							}
						}

						naviView.updateUI();

					} else {
						JOptionPane.showMessageDialog(null, Messages.getString("NaviController.18")); //$NON-NLS-1$
					}
				} catch (SQLException e) {
					mainControl.fileSQLError();
				}
			}

		}
		if (arg0.getSource() == exitButton) {
			int abfrage = beendenHinweis();
			if (abfrage == 0) {
				System.exit(0);
			}

		}

	}

	private int warnHinweis() {
		int abfrage = 0;
		String hinweisText = Messages.getString("NaviController.21") //$NON-NLS-1$
				+ Messages.getString("NaviController.22") //$NON-NLS-1$
				+ Messages.getString("NaviController.23"); //$NON-NLS-1$
		if (this.mainControl.getNaviView().getTabellenPanel().isVisible() == true) {
			abfrage = 1;
			// Custom button text
			Object[] options = { Messages.getString("NaviController.24"), Messages.getString("NaviController.25") }; //$NON-NLS-1$ //$NON-NLS-2$
			abfrage = JOptionPane.showOptionDialog(mainControl, hinweisText, Messages.getString("NaviController.26"), //$NON-NLS-1$
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		}
		if (abfrage == 0) {
			mainControl.setNeuesTurnier(false);

		}
		return abfrage;
	}

	private int beendenHinweis() {
		int abfrage = 0;
		String hinweisText = Messages.getString("NaviController.21") //$NON-NLS-1$
				+ Messages.getString("NaviController.22") //$NON-NLS-1$
				+ Messages.getString("NaviController.33"); //$NON-NLS-1$

		abfrage = 1;
		// Custom button text
		Object[] options = { Messages.getString("NaviController.24"), Messages.getString("NaviController.25") }; //$NON-NLS-1$ //$NON-NLS-2$
		abfrage = JOptionPane.showOptionDialog(mainControl, hinweisText, Messages.getString("NaviController.26"), //$NON-NLS-1$
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		return abfrage;
	}

	public TurnierAnsicht getTurnierAnsicht() {
		return turnierAnsicht;
	}

	public void setTurnierAnsicht(TurnierAnsicht turnierAnsicht) {
		this.turnierAnsicht = turnierAnsicht;
	}

	/**
	 * 
	 * @author mars
	 *
	 */
	class TurnierAnsicht implements ChangeListener {

		private MainControl mainControl;

		/**
		 * 
		 * @param mainControl
		 */
		public TurnierAnsicht(MainControl mainControl) {
			super();
			this.mainControl = mainControl;
			int selectedTabIndex = this.mainControl.getHauptPanel().getSelectedIndex();
			if (TournamentConstants.TAB_PLAYER_LIST == selectedTabIndex) {
				this.mainControl.getNaviView().getSpielerListePanel().setVisible(true);
				this.mainControl.getNaviView().getTurnierListePanel().setVisible(false);
				this.mainControl.getNaviView().getTabellenPanel().setVisible(false);

			}
			if (TournamentConstants.TAB_TOURNAMENTS_LIST == selectedTabIndex) {
				this.mainControl.getNaviView().getTurnierListePanel().setVisible(true);
				this.mainControl.getNaviView().getSpielerListePanel().setVisible(false);
				this.mainControl.getNaviView().getTabellenPanel().setVisible(false);

			}
			if (TournamentConstants.TAB_ACTIVE_TOURNAMENT == selectedTabIndex) {
				this.mainControl.getNaviView().getTabellenPanel().setVisible(true);
				this.mainControl.getNaviView().getTurnierListePanel().setVisible(false);
				this.mainControl.getNaviView().getSpielerListePanel().setVisible(false);
			}
			if (this.mainControl.getNeuesTurnier() == true) {
				this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
				this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
			}
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			pairingIsActive = this.mainControl.getPairingsMenuActionControl().getPairingIsActive();
			if (e.getSource() instanceof JTabbedPane) {
				JTabbedPane pane = (JTabbedPane) e.getSource();
				int selectedIndex = pane.getSelectedIndex();
				String turnierName = Messages.getString("NaviController.27"); //$NON-NLS-1$
				if (this.mainControl.getTurnier() != null) {
					turnierName = this.mainControl.getTurnier().getTurnierName();
					if (pane.getTitleAt(selectedIndex).equals(turnierName)
							|| pane.getTitleAt(selectedIndex).equals(Messages.getString("NaviController.28"))) {
						if (this.mainControl.getNeuesTurnier() == false) {
							if (pairingIsActive == true) {
								this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
								this.mainControl.getNaviView().getPairingsPanel().setVisible(true);
							} else {
								this.mainControl.getNaviView().getTabellenPanel().setVisible(true);
								this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
							}

						} else {
							this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
							this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
						}

					} else {
						this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
						this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
					}

				}

				if (pane.getTitleAt(selectedIndex).equals(Messages.getString("NaviController.29"))) { //$NON-NLS-1$
					this.mainControl.getNaviView().getTurnierListePanel().setVisible(true);

				} else {
					this.mainControl.getNaviView().getTurnierListePanel().setVisible(false);
				}
				if (pane.getTitleAt(selectedIndex).equals(Messages.getString("NaviController.30"))) { //$NON-NLS-1$
					this.mainControl.getNaviView().getSpielerListePanel().setVisible(true);

				} else {
					this.mainControl.getNaviView().getSpielerListePanel().setVisible(false);
				}

			}
		}

	}
}
