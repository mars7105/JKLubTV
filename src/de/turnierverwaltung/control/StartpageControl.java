package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import de.turnierverwaltung.view.StartpageDialog;
import de.turnierverwaltung.view.StartpageView;

public class StartpageControl {
	MainControl mainControl;
	StartpageView startpage;

	public StartpageControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		startpage = new StartpageView();
		DialogListener dwzListener = new DialogListener(new JPanel());
		startpage.getDwzButton().addActionListener(dwzListener);
		
		DialogListener eloListener = new DialogListener(new JPanel());
		startpage.getEloButton().addActionListener(eloListener);
		
		DialogListener dbListener = new DialogListener(new JPanel());
		startpage.getDatabaseButton().addActionListener(dbListener);
		
		DialogListener playerListener = new DialogListener(new JPanel());
		startpage.getPlayerButton().addActionListener(playerListener);
		
		DialogListener tournamentListener = new DialogListener(new JPanel());
		startpage.getTournamentButton().addActionListener(tournamentListener);
	}

	public StartpageView getStartpage() {
		return startpage;
	}

	public void setStartpage(StartpageView startpage) {
		this.startpage = startpage;
	}

	class DialogListener implements ActionListener {
		JPanel panel;

		public DialogListener(JPanel panel) {
			super();
			this.panel = panel;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			mainControl.setEnabled(false);
			StartpageDialog dialog = new StartpageDialog(panel);
			dialog.setBounds(100, 100, 500, 400);
			dialog.enableDialog();
			dialog.getOkButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					dialog.dispose();
					mainControl.setEnabled(true);
				}
			});
			dialog.getCancelButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					dialog.dispose();
					mainControl.setEnabled(true);
				}
			});
		}
	}
}
