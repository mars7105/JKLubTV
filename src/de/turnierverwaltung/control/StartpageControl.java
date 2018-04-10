package de.turnierverwaltung.control;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import de.turnierverwaltung.view.StartpageDBPanelView;
import de.turnierverwaltung.view.StartpageDWZPanelView;
import de.turnierverwaltung.view.StartpageDialog;
import de.turnierverwaltung.view.StartpageELOPanelView;
import de.turnierverwaltung.view.StartpagePanelView;
import de.turnierverwaltung.view.StartpageTounamentPanelView;
import de.turnierverwaltung.view.StartpageView;

public class StartpageControl {
	MainControl mainControl;
	StartpageView startpage;
	private StartpageDWZPanelView startpageDWZPanel;
	private StartpageELOPanelView startpageELOPanel;
	private StartpageDBPanelView startpageDBPanel;
	private StartpageDBPanelView startpagePlayerPanel;
	private StartpageTounamentPanelView startpageTounamentPanel;

	public StartpageControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
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

		startpagePlayerPanel = new StartpageDBPanelView();
		DialogListener playerListener = new DialogListener(startpagePlayerPanel);
		startpage.getPlayerButton().addActionListener(playerListener);

		startpageTounamentPanel = new StartpageTounamentPanelView();
		DialogListener tournamentListener = new DialogListener(startpageTounamentPanel);
		startpage.getTournamentButton().addActionListener(tournamentListener);

		StartpageActionListener actionListener = new StartpageActionListener(mainControl, this);
		actionListener.addActionListener();
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

	public StartpageDBPanelView getStartpagePlayerPanel() {
		return startpagePlayerPanel;
	}

	public void setStartpagePlayerPanel(StartpageDBPanelView startpagePlayerPanel) {
		this.startpagePlayerPanel = startpagePlayerPanel;
	}

	public StartpageTounamentPanelView getStartpageTounamentPanel() {
		return startpageTounamentPanel;
	}

	public void setStartpageTounamentPanel(StartpageTounamentPanelView startpageTounamentPanel) {
		this.startpageTounamentPanel = startpageTounamentPanel;
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
			final StartpageDialog dialog = new StartpageDialog(mainControl, "", Dialog.ModalityType.APPLICATION_MODAL);
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
