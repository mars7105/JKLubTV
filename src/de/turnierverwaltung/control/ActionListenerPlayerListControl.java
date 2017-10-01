package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.model.DSBDWZClub;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.view.NaviView;
import de.turnierverwaltung.view.NewPlayerView;
import de.turnierverwaltung.view.ProgressBarDWZUpdateView;

public class ActionListenerPlayerListControl implements ActionListener, FocusListener {
	private MainControl mainControl;
	private NewPlayerView spielerHinzufuegenView;
	private NaviView naviView;
	private DSBDWZControl dewisDialogControl;
	private Player neuerSpieler;
	private ProgressBarDWZUpdateView ladebalkenView;

	public ActionListenerPlayerListControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		naviView = mainControl.getNaviView();
		naviView.getSpielerAddButton().addActionListener(this);
		naviView.getSpielerExport().addActionListener(this);
		naviView.getSpielerImport().addActionListener(this);
		naviView.getSpielerDEWISSearchButton().addActionListener(this);
		naviView.getUpdateButton().addActionListener(this);
		naviView.getSpielerELOSearchButton().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (spielerHinzufuegenView != null) {
			if (arg0.getSource().equals(spielerHinzufuegenView.getOkButton())) {

				try {
					String forename = spielerHinzufuegenView.getTextFieldForeName().getText();
					String surname = spielerHinzufuegenView.getTextFieldSurName().getText();
					if (!surname.equals("Spielfrei")) {
						String kuerzel = spielerHinzufuegenView.getTextFieldKuerzel().getText();
						String dwz = spielerHinzufuegenView.getTextFieldDwz().getText();
						int age = spielerHinzufuegenView.getTextComboBoxAge().getSelectedIndex();
						neuerSpieler = new Player();
						neuerSpieler.setForename(forename);
						neuerSpieler.setSurname(surname);
						neuerSpieler.extractForenameAndSurenameToName();
						neuerSpieler.setKuerzel(kuerzel);
						neuerSpieler.setDwz(dwz);
						neuerSpieler.setAge(age);
						SQLPlayerControl stc = new SQLPlayerControl(mainControl);

						neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));

						this.mainControl.getSpielerLadenControl().getSpieler().add(neuerSpieler);
					}
					spielerHinzufuegenView.getTextFieldForeName().setEditable(false);
					spielerHinzufuegenView.getTextFieldSurName().setEditable(false);
					spielerHinzufuegenView.getTextFieldKuerzel().setEditable(false);
					spielerHinzufuegenView.getTextFieldDwz().setEditable(false);
					spielerHinzufuegenView.getTextComboBoxAge().setEnabled(false);
					spielerHinzufuegenView.spielerPanel();
					spielerHinzufuegenView.getTextFieldKuerzel().addFocusListener(this);
				} catch (SQLException e) {
					mainControl.fileSQLError();
				}

			}
			if (arg0.getSource().equals(spielerHinzufuegenView.getCancelButton())) {
				mainControl.setEnabled(true);
				try {
					this.mainControl.getSpielerLadenControl().updateSpielerListe();
				} catch (SQLException e) {
					mainControl.fileSQLError();
				}

				spielerHinzufuegenView.closeWindow();
			}
		}
		if (arg0.getSource().equals(naviView.getSpielerImport())) {
			SQLImportPlayerListControl spielerImport = new SQLImportPlayerListControl(mainControl);
			try {
				spielerImport.importSpielerTable();

				mainControl.getSpielerLadenControl().updateSpielerListe();
			} catch (SQLException e) {
				mainControl.fileSQLError();
			}
		}
		if (arg0.getSource().equals(naviView.getSpielerExport())) {
			SQLExportPlayerListControl spielerExport = new SQLExportPlayerListControl(this.mainControl);
			try {
				spielerExport.exportSpielerTable();
			} catch (SQLException e) {
				mainControl.fileSQLError();
			}
		}

		if (arg0.getSource().equals(naviView.getSpielerDEWISSearchButton())) {
			dewisDialogControl = new DSBDWZControl(mainControl);
			dewisDialogControl.makeDialog();
			dewisDialogControl.makePlayerSearchList();
		}
		if (arg0.getSource().equals(naviView.getSpielerELOSearchButton())) {
			ELOControl eloDialogControl = new ELOControl(mainControl);
			eloDialogControl.makeDialog();
			eloDialogControl.makePlayerSearchList();
		}
		if (arg0.getSource().equals(naviView.getSpielerAddButton())) {
			spielerHinzufuegenView = new NewPlayerView();

			spielerHinzufuegenView.getOkButton().addActionListener(this);
			spielerHinzufuegenView.getCancelButton().addActionListener(this);
			spielerHinzufuegenView.getTextFieldKuerzel().addFocusListener(this);
			mainControl.setEnabled(false);
		}
		if (arg0.getSource().equals(naviView.getUpdateButton())) {
			try {

				updateSpieler();

				mainControl.getSpielerLadenControl().updateSpielerListe();
			} catch (SQLException e) {
				mainControl.fileSQLError();
			}
		}
	}

	/**
	 * 
	 */
	public void neuerSpieler() {
		try {
			this.mainControl.getSpielerLadenControl().updateSpielerListe();
		} catch (SQLException e) {
			mainControl.fileSQLError();
		}
		spielerHinzufuegenView = new NewPlayerView();

		spielerHinzufuegenView.getOkButton().addActionListener(this);
		spielerHinzufuegenView.getCancelButton().addActionListener(this);
		mainControl.setEnabled(false);
	}

	/**
	 * Create the GUI and show it. As with all GUI code, this must run on the
	 * event-dispatching thread.
	 */
	private void createAndShowGUI(int anzahl) {

		ladebalkenView = new ProgressBarDWZUpdateView(anzahl);

		// Display the window.
		ladebalkenView.pack();

		ladebalkenView.setVisible(true);
	}

	public void updateSpieler() {
		DSBDWZClub verein = null;
		ArrayList<Player> spieler = null;
		SQLPlayerControl spielerTableControl = new SQLPlayerControl(this.mainControl);
		ArrayList<Player> spielerliste = null;
		try {
			spielerliste = spielerTableControl.getAllSpielerOrderByZPS();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		createAndShowGUI(spielerliste.size());
		for (Player player : spielerliste) {
			if (verein != null) {
				if (verein.getZps().equals(player.getDsbZPSNumber()) == false) {
					verein = new DSBDWZClub(player.getDsbZPSNumber());
					spieler = verein.getSpieler();
				}
			} else {
				verein = new DSBDWZClub(player.getDsbZPSNumber());
				spieler = verein.getSpieler();
			}

			if (spieler != null) {
				for (Player temp : spieler) {
					try {
						int tempMGL = Integer.parseInt(temp.getDsbMGLNumber());
						int playerMGL = Integer.parseInt(player.getDsbMGLNumber());
						if (tempMGL == playerMGL) {
							if (player.getDWZ() != temp.getDWZ() || player.getDwzindex() != temp.getDwzindex()) {
								player.setDwz(temp.getDWZ());
								player.setDwzindex(temp.getDwzindex());
								try {
									spielerTableControl.updateOneSpieler(player);
									// System.out.println(player.getName());
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					} catch (NumberFormatException e) {

					}

				}
			}
			ladebalkenView.iterate();
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		String forename = spielerHinzufuegenView.getTextFieldForeName().getText();
		String surname = spielerHinzufuegenView.getTextFieldSurName().getText();
		String kuerzel = forename.substring(0, 1) + surname.substring(0, 1);
		spielerHinzufuegenView.getTextFieldKuerzel().setText(kuerzel);

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

}
