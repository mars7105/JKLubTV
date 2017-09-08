package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;

import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.view.NaviView;
import de.turnierverwaltung.view.NewPlayerView;

public class ActionListenerPlayerListControl implements ActionListener, FocusListener {
	private MainControl mainControl;
	private NewPlayerView spielerHinzufuegenView;
	private NaviView naviView;
	private DSBDWZControl dewisDialogControl;
	private Player neuerSpieler;

	public ActionListenerPlayerListControl(MainControl mainControl) {
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
			spielerHinzufuegenView.getTextFieldKuerzel().addFocusListener(this);
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
