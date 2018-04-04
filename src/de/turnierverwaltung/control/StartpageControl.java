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
import de.turnierverwaltung.view.StartpagePayerPanelView;
import de.turnierverwaltung.view.StartpageTounamentPanelView;
import de.turnierverwaltung.view.StartpageView;

public class StartpageControl {
	MainControl mainControl;
	StartpageView startpage;

	public StartpageControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		startpage = new StartpageView();
		DialogListener dwzListener = new DialogListener(new StartpageDWZPanelView());
		startpage.getDwzButton().addActionListener(dwzListener);

		DialogListener eloListener = new DialogListener(new StartpageELOPanelView());
		startpage.getEloButton().addActionListener(eloListener);

		DialogListener dbListener = new DialogListener(new StartpageDBPanelView());
		startpage.getDatabaseButton().addActionListener(dbListener);

		DialogListener playerListener = new DialogListener(new StartpagePayerPanelView());
		startpage.getPlayerButton().addActionListener(playerListener);

		DialogListener tournamentListener = new DialogListener(new StartpageTounamentPanelView());
		startpage.getTournamentButton().addActionListener(tournamentListener);
	}

	public StartpageView getStartpage() {
		return startpage;
	}

	public void setStartpage(StartpageView startpage) {
		this.startpage = startpage;
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
			StartpageDialog dialog = new StartpageDialog(mainControl, "", Dialog.ModalityType.APPLICATION_MODAL);
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
			dialog.getCancelButton().addActionListener(new ActionListener() {

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
