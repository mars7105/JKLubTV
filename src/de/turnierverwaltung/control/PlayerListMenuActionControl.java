package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.view.NaviView;
import de.turnierverwaltung.view.NewPlayerView;

public class PlayerListMenuActionControl implements ActionListener {
	private MainControl mainControl;
	private NewPlayerView spielerHinzufuegenView;
	private NaviView naviView;
	private DSBDWZControl dewisDialogControl;

	public PlayerListMenuActionControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		naviView = mainControl.getNaviView();
		naviView.getSpielerAddButton().addActionListener(this);
		naviView.getSpielerExport().addActionListener(this);
		naviView.getSpielerImport().addActionListener(this);
		naviView.getSpielerDEWISSearchButton().addActionListener(this);
		dewisDialogControl = new DSBDWZControl(mainControl);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (spielerHinzufuegenView != null) {
			if (arg0.getSource() == spielerHinzufuegenView.getOkButton()) {
				try {
					String name = spielerHinzufuegenView.getTextFieldName().getText();
					if (!name.equals("Spielfrei")) {
						String kuerzel = spielerHinzufuegenView.getTextFieldKuerzel().getText();
						String dwz = spielerHinzufuegenView.getTextFieldDwz().getText();
						int age = spielerHinzufuegenView.getTextComboBoxAge().getSelectedIndex();
						Player neuerSpieler = new Player();
						neuerSpieler.setName(name);
						neuerSpieler.setKuerzel(kuerzel);
						neuerSpieler.setDwz(dwz);
						neuerSpieler.setAge(age);
						SQLPlayerControl stc = new SQLPlayerControl(mainControl);

						neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));

						this.mainControl.getSpielerLadenControl().getSpieler().add(neuerSpieler);
					}
					spielerHinzufuegenView.getTextFieldName().setEditable(false);
					spielerHinzufuegenView.getTextFieldKuerzel().setEditable(false);
					spielerHinzufuegenView.getTextFieldDwz().setEditable(false);
					spielerHinzufuegenView.getTextComboBoxAge().setEnabled(false);
					spielerHinzufuegenView.spielerPanel();
				} catch (SQLException e) {
					mainControl.fileSQLError();
				}

			}
			if (arg0.getSource() == spielerHinzufuegenView.getCancelButton()) {
				mainControl.setEnabled(true);
				try {
					this.mainControl.getSpielerLadenControl().updateSpielerListe();
				} catch (SQLException e) {
					mainControl.fileSQLError();
				}

				spielerHinzufuegenView.closeWindow();
			}
		}
		if (arg0.getSource() == naviView.getSpielerImport()) {
			SQLImportPlayerListControl spielerImport = new SQLImportPlayerListControl(mainControl);
			try {
				spielerImport.importSpielerTable();

				mainControl.getSpielerLadenControl().updateSpielerListe();
			} catch (SQLException e) {
				mainControl.fileSQLError();
			}
		}
		if (arg0.getSource() == naviView.getSpielerExport()) {
			SQLExportPlayerListControl spielerExport = new SQLExportPlayerListControl(this.mainControl);
			try {
				spielerExport.exportSpielerTable();
			} catch (SQLException e) {
				mainControl.fileSQLError();
			}
		}
		
		if (arg0.getSource() == naviView.getSpielerDEWISSearchButton()) {
			dewisDialogControl.makeDialog();
		}
		if (arg0.getSource() == naviView.getSpielerAddButton()) {
			spielerHinzufuegenView = new NewPlayerView();

			spielerHinzufuegenView.getOkButton().addActionListener(this);
			spielerHinzufuegenView.getCancelButton().addActionListener(this);
			mainControl.setEnabled(false);
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
}
