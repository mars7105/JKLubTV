package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import de.turnierverwaltung.control.playerlist.PlayerListControl;
import de.turnierverwaltung.control.tournamentlist.ActionListenerTournamentItemsControl;
import de.turnierverwaltung.model.rating.SQLitePlayerDWZList;
import de.turnierverwaltung.model.rating.SQLitePlayerELOList;
import de.turnierverwaltung.view.StartpageDBPanelView;
import de.turnierverwaltung.view.StartpageDWZPanelView;
import de.turnierverwaltung.view.StartpageDialog;
import de.turnierverwaltung.view.StartpageELOPanelView;
import de.turnierverwaltung.view.StartpagePanelView;
import de.turnierverwaltung.view.StartpagePlayerPanelView;
import de.turnierverwaltung.view.StartpageTournamentPanelView;
import de.turnierverwaltung.view.StartpageView;


public class StartpageControl {
	private MainControl mainControl;
	private StartpageView startpage;
	private StartpageDWZPanelView startpageDWZPanel;
	private StartpageELOPanelView startpageELOPanel;
	private StartpageDBPanelView startpageDBPanel;
	private StartpagePlayerPanelView startpagePlayerPanel;
	private StartpageTournamentPanelView startpageTounamentPanel;
	private JDialog dialog;

	public StartpageControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;

	}

	private void createDialog() {
		if (mainControl.getPropertiesControl().getPathToDatabase().length() > 0) {
			startpage.getDatabasePanel().addCheckItem();
//			startpage.getDatabaseButton().setEnabled(false);
		}
		PlayerListControl playerListControl = mainControl.getPlayerListControl();
		if (playerListControl != null) {
			if (playerListControl.getSpieler().size() > 0) {
				startpage.getPlayerPanel().addCheckItem();
//				startpage.getPlayerButton().setEnabled(false);
			}
		}
		ActionListenerTournamentItemsControl actionListenerTournamentItemsControl = mainControl
				.getActionListenerTournamentItemsControl();
		if (actionListenerTournamentItemsControl != null) {
			if (actionListenerTournamentItemsControl.getAnzahlTurniere() > 0) {
				startpage.getTournamentPanel().addCheckItem();
//				startpage.getTournamentButton().setEnabled(false);
			}
		}
		getStartpageDWZPanel().getOpenVereineCSVLabel()
				.setText(mainControl.getPropertiesControl().getPathToVereineCVS());

		getStartpageDWZPanel().getOpenPlayersCSVLabel()
				.setText(mainControl.getPropertiesControl().getPathToPlayersCSV());

		getStartpageELOPanel().getOpenPlayersELOLabel()
				.setText(mainControl.getPropertiesControl().getPathToPlayersELO());
		String filename = mainControl.getPropertiesControl().getPathToPlayersELO();
		int positionEXT = filename.lastIndexOf('.');

		if (positionEXT > 0) {
			String newFile = filename.substring(positionEXT);
			if (newFile.equals(".sqlite")) {
				SQLitePlayerELOList spelolist = new SQLitePlayerELOList();
				Boolean checkDB = spelolist.checkDatabase(filename);
				if (checkDB == true) {
					getStartpageELOPanel().getConvertELOToSQLITEButton().setEnabled(false);
					getStartpage().getEloPanel().addCheckItem();
//					startpage.getEloButton().setEnabled(false);
				} else {
					mainControl.getPropertiesControl().setPathToPlayersELO("");
				}

			} else {
				getStartpageELOPanel().getConvertELOToSQLITEButton().setEnabled(true);

			}
		} else {
			getStartpageELOPanel().getConvertELOToSQLITEButton().setEnabled(false);

		}

		filename = mainControl.getPropertiesControl().getPathToPlayersCSV();
		positionEXT = filename.lastIndexOf('.');
		if (positionEXT > 0) {
			String newFile = filename.substring(positionEXT);
			if (newFile.equals(".sqlite")) {

				getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(false);
				getStartpage().getDwzPanel().addCheckItem();
//				startpage.getDwzButton().setEnabled(false);
			} else {
				getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(true);

			}
		} else {
			getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(false);

		}
		StartpageActionListener actionListener = new StartpageActionListener(mainControl, this);
		actionListener.addActionListener();
		dialog = new JDialog();

		dialog.setTitle("Einrichtungsassistent");
		dialog.add(startpage);

		dialog.pack();
//		dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	public void createStartPanels() {
		startpage = new StartpageView();

		startpageDWZPanel = new StartpageDWZPanelView();
		DialogListener dwzListener = new DialogListener(startpageDWZPanel);
		startpage.getDwzButton().addActionListener(dwzListener);

		startpageELOPanel = new StartpageELOPanelView();
		DialogListener eloListener = new DialogListener(startpageELOPanel);
		startpage.getEloButton().addActionListener(eloListener);

		startpageDBPanel = new StartpageDBPanelView();
		DialogListener dbListener = new DialogListener(startpageDBPanel);
		startpage.getDatabaseButton().addActionListener(dbListener);

		startpagePlayerPanel = new StartpagePlayerPanelView();
		DialogListener playerListener = new DialogListener(startpagePlayerPanel);
		startpage.getPlayerButton().addActionListener(playerListener);

		startpageTounamentPanel = new StartpageTournamentPanelView();
		DialogListener tournamentListener = new DialogListener(startpageTounamentPanel);
		startpage.getTournamentButton().addActionListener(tournamentListener);
		createDialog();

	}

	public StartpageView getStartpage() {
		return startpage;
	}

	public void setStartpage(StartpageView startpage) {
		this.startpage = startpage;
	}

	public StartpageDWZPanelView getStartpageDWZPanel() {
		return startpageDWZPanel;
	}

	public void setStartpageDWZPanel(StartpageDWZPanelView startpageDWZPanel) {
		this.startpageDWZPanel = startpageDWZPanel;
	}

	public StartpageELOPanelView getStartpageELOPanel() {
		return startpageELOPanel;
	}

	public void setStartpageELOPanel(StartpageELOPanelView startpageELOPanel) {
		this.startpageELOPanel = startpageELOPanel;
	}

	public StartpageDBPanelView getStartpageDBPanel() {
		return startpageDBPanel;
	}

	public void setStartpageDBPanel(StartpageDBPanelView startpageDBPanel) {
		this.startpageDBPanel = startpageDBPanel;
	}

	public StartpagePlayerPanelView getStartpagePlayerPanel() {
		return startpagePlayerPanel;
	}

	public void setStartpagePlayerPanel(StartpagePlayerPanelView startpagePlayerPanel) {
		this.startpagePlayerPanel = startpagePlayerPanel;
	}

	public StartpageTournamentPanelView getStartpageTounamentPanel() {
		return startpageTounamentPanel;
	}

	public void setStartpageTounamentPanel(StartpageTournamentPanelView startpageTounamentPanel) {
		this.startpageTounamentPanel = startpageTounamentPanel;
	}

	public JDialog getDialog() {
		return dialog;
	}

	public void setDialog(JDialog dialog) {
		this.dialog = dialog;
	}

	class DialogListener implements ActionListener {
		private StartpagePanelView panel;

		public DialogListener(StartpagePanelView panel) {
			super();
			this.panel = panel;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// mainControl.setEnabled(false);
//			final StartpageDialog dialog = new StartpageDialog(mainControl, "", Dialog.ModalityType.APPLICATION_MODAL);
			final StartpageDialog dialog = new StartpageDialog();

			dialog.setMainPanel(panel);
			dialog.setLocationRelativeTo(null);
			dialog.pack();
			dialog.setBounds(mainControl.getPropertiesControl().getStartpageDialogX(),
					mainControl.getPropertiesControl().getStartpageDialogY(),
					mainControl.getPropertiesControl().getStartpageDialogWidth(),
					mainControl.getPropertiesControl().getStartpageDialogHeight());

			DialogWindowListener wlistener = new DialogWindowListener(dialog);
			dialog.addWindowListener(wlistener);
			dialog.getOkButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					mainControl.getPropertiesControl().writeStartpageDialogProperties(dialog.getBounds().x,
							dialog.getBounds().y, dialog.getBounds().width, dialog.getBounds().height);
					dialog.dispose();
					SQLitePlayerDWZList spdwzlist = new SQLitePlayerDWZList();
					Boolean checkDB = spdwzlist.checkDatabase(mainControl.getPropertiesControl().getPathToPlayersCSV());
					if (checkDB == true) {
						getStartpageDWZPanel().getConvertDWZToSQLITEButton().setEnabled(false);
						getStartpage().getDwzPanel().addCheckItem();
						getStartpage().getDwzPanel().updateUI();
					} else {
						getStartpage().getDwzPanel().removeCheckItem();
						getStartpage().getDwzPanel().updateUI();
					}
					SQLitePlayerELOList spelolist = new SQLitePlayerELOList();
					checkDB = spelolist.checkDatabase(mainControl.getPropertiesControl().getPathToPlayersELO());
					if (checkDB == true) {
						getStartpageELOPanel().getConvertELOToSQLITEButton().setEnabled(false);
						getStartpage().getEloPanel().addCheckItem();
						getStartpage().getEloPanel().updateUI();

					} else {
						getStartpage().getEloPanel().removeCheckItem();
						getStartpage().getEloPanel().updateUI();
					}
				}
			});

			dialog.showDialog();
		}
	}

	class DialogWindowListener implements WindowListener {
		private StartpageDialog dialog;

		public DialogWindowListener(StartpageDialog dialog) {
			super();
			this.dialog = dialog;
		}

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			mainControl.getPropertiesControl().writeStartpageDialogProperties(dialog.getBounds().x,
					dialog.getBounds().y, dialog.getBounds().width, dialog.getBounds().height);
			dialog.dispose();
			// mainControl.setEnabled(true);

		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}
