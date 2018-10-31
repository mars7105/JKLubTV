package de.turnierverwaltung.control.playerlist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.sql.SQLException;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.ratingdialog.DSBDWZControl;
import de.turnierverwaltung.control.ratingdialog.ELOControl;
import de.turnierverwaltung.control.ratingdialog.UpdateRatingsControl;
import de.turnierverwaltung.control.sqlite.SQLExportPlayerListControl;
import de.turnierverwaltung.control.sqlite.SQLImportPlayerListControl;
import de.turnierverwaltung.control.sqlite.SQLPlayerControl;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.view.navigation.NaviView;
import de.turnierverwaltung.view.playerlist.NewPlayerView;

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

					String kuerzel = spielerHinzufuegenView.getTextFieldKuerzel().getText();
					String dwz = spielerHinzufuegenView.getTextFieldDwz().getText();
					int age = spielerHinzufuegenView.getTextComboBoxAge().getSelectedIndex();
					neuerSpieler = new Player();
					// neuerSpieler.setForename(forename);
					// neuerSpieler.setSurname(surname);
					neuerSpieler.setName(surname + "," + forename);
					neuerSpieler.setKuerzel(kuerzel);
					neuerSpieler.setDwz(dwz);
					neuerSpieler.setAge(age);
					SQLPlayerControl stc = new SQLPlayerControl(mainControl);

					neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));

					this.mainControl.getPlayerListControl().getSpieler().add(neuerSpieler);

					spielerHinzufuegenView.getTextFieldForeName().setEditable(false);
					spielerHinzufuegenView.getTextFieldSurName().setEditable(false);
					spielerHinzufuegenView.getTextFieldKuerzel().setEditable(false);
					spielerHinzufuegenView.getTextFieldDwz().setEditable(false);
					spielerHinzufuegenView.getTextComboBoxAge().setEnabled(false);
					spielerHinzufuegenView.spielerPanel();
					spielerHinzufuegenView.getTextFieldKuerzel().addFocusListener(this);
				} catch (SQLException e) {
					ExceptionHandler eh = new ExceptionHandler(mainControl);
					eh.fileSQLError(e.getMessage());
				}

			}
			if (arg0.getSource().equals(spielerHinzufuegenView.getCancelButton())) {
//				mainControl.setEnabled(true);
				try {
					this.mainControl.getPlayerListControl().updateSpielerListe();
				} catch (SQLException e) {
					ExceptionHandler eh = new ExceptionHandler(mainControl);
					eh.fileSQLError(e.getMessage());
				}

				spielerHinzufuegenView.closeWindow();
			}
		}
		if (arg0.getSource().equals(naviView.getSpielerImport())) {
			SQLImportPlayerListControl spielerImport = new SQLImportPlayerListControl(mainControl);
			try {
				spielerImport.importSpielerTable();

				mainControl.getPlayerListControl().updateSpielerListe();
			} catch (SQLException e) {
				ExceptionHandler eh = new ExceptionHandler(mainControl);
				eh.fileSQLError(e.getMessage());
			}
		}
		if (arg0.getSource().equals(naviView.getSpielerExport())) {
			SQLExportPlayerListControl spielerExport = new SQLExportPlayerListControl(this.mainControl);
			try {
				spielerExport.exportSpielerTable();
			} catch (SQLException e) {
				ExceptionHandler eh = new ExceptionHandler(mainControl);
				eh.fileSQLError(e.getMessage());
			}
		}

		if (arg0.getSource().equals(naviView.getSpielerDEWISSearchButton())) {

			dewisDialogControl = new DSBDWZControl(mainControl);
			try {
				dewisDialogControl.makeDialog();
				dewisDialogControl.makePlayerSearchList();
//				dewisDialogControl.getDialog.getVereinsAuswahl().addItemListener(dewisDialogActionListenerControl);
			} catch (ArrayIndexOutOfBoundsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (arg0.getSource().equals(naviView.getSpielerELOSearchButton())) {
			ELOControl eloDialogControl;
			try {
				eloDialogControl = new ELOControl(mainControl);
				eloDialogControl.makeDialog();
				eloDialogControl.makePlayerSearchList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (arg0.getSource().equals(naviView.getSpielerAddButton())) {
			spielerHinzufuegenView = new NewPlayerView();

			spielerHinzufuegenView.getOkButton().addActionListener(this);
			spielerHinzufuegenView.getCancelButton().addActionListener(this);
			spielerHinzufuegenView.getTextFieldKuerzel().addFocusListener(this);
			spielerHinzufuegenView.showDialog();
		}
		if (arg0.getSource().equals(naviView.getUpdateButton())) {
			try {

				UpdateRatingsControl updateRatings = new UpdateRatingsControl(mainControl);
				updateRatings.updateSpieler();
				mainControl.getPlayerListControl().updateSpielerListe();
			} catch (SQLException e) {
				ExceptionHandler eh = new ExceptionHandler(mainControl);
				eh.fileSQLError(e.getMessage());
			} catch (NullPointerException e) {
				ExceptionHandler eh = new ExceptionHandler(mainControl);
				eh.fileSQLError(e.getMessage());
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		String forename = spielerHinzufuegenView.getTextFieldForeName().getText();
		String surname = spielerHinzufuegenView.getTextFieldSurName().getText();
		String kuerzel = "";
		if (forename.length() > 0) {
			kuerzel = forename.substring(0, 1);
		}
		if (surname.length() > 0) {
			kuerzel += surname.substring(0, 1);
		}
//		String kuerzel = forename.substring(0, 1) + surname.substring(0, 1);
		spielerHinzufuegenView.getTextFieldKuerzel().setText(kuerzel);

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	public void neuerSpieler() {
		try {
			this.mainControl.getPlayerListControl().updateSpielerListe();
		} catch (SQLException e) {
			ExceptionHandler eh = new ExceptionHandler(mainControl);
			eh.fileSQLError(e.getMessage());
		}
		spielerHinzufuegenView = new NewPlayerView();

		spielerHinzufuegenView.getOkButton().addActionListener(this);
		spielerHinzufuegenView.getCancelButton().addActionListener(this);
//		mainControl.setEnabled(false);
	}

}
