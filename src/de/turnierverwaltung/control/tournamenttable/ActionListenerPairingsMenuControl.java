package de.turnierverwaltung.control.tournamenttable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.view.TabbedPaneView;
import de.turnierverwaltung.view.navigation.NaviView;

public class ActionListenerPairingsMenuControl implements ActionListener {
	private MainControl mainControl;
	private NaviView naviView;
	private boolean pairingIsActive;

	public ActionListenerPairingsMenuControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		naviView = mainControl.getNaviView();

		naviView.getPairingsSaveButton().addActionListener(this);
		naviView.getPairingsCancelButton().addActionListener(this);
		pairingIsActive = false;
	}

	private int abbrechenHinweis() {
		int abfrage = 0;
		String hinweisText = Messages.getString("NaviController.21") //$NON-NLS-1$
				+ Messages.getString("NaviController.22") //$NON-NLS-1$
				+ Messages.getString("NaviController.34"); //$NON-NLS-1$

		abfrage = 1;
		// Custom button text
		Object[] options = { Messages.getString("NaviController.35"), Messages.getString("NaviController.36") }; //$NON-NLS-1$ //$NON-NLS-2$
		abfrage = JOptionPane.showOptionDialog(mainControl, hinweisText, Messages.getString("NaviController.26"), //$NON-NLS-1$
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		return abfrage;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(naviView.getPairingsSaveButton())) {

			saveAndReloadTurnier();
			// try {
			// setTabsEnable(true);
			// } catch (SQLException e) {
			// ExceptionHandler eh = new ExceptionHandler(mainControl);
			// eh.fileSQLError(e.getMessage());
			// }
//			pairingIsActive = false;
			mainControl.setNewTournament(false);
			try {
				mainControl.getActionListenerTournamentItemsControl().loadTurnierListe();
				mainControl.getActionListenerTournamentItemsControl().reloadTurnier();
				mainControl.getActionListenerTournamentItemsControl().loadPairingsView();
			} catch (SQLException e) {
				ExceptionHandler eh = new ExceptionHandler(mainControl);
				eh.fileSQLError(e.getMessage());
			}
		}
		if (arg0.getSource().equals(naviView.getPairingsCancelButton())) {
			if (mainControl.getPairingsControl().getChangedPartien().size() > 0) {
				int abfrage = abbrechenHinweis();
				if (abfrage == 0) {
					saveAndReloadTurnier();
					try {
						setTabsEnable(true);
					} catch (SQLException e) {
						ExceptionHandler eh = new ExceptionHandler(mainControl);
						eh.fileSQLError(e.getMessage());
					}
					pairingIsActive = false;
				}
			} else {
				try {
					setTabsEnable(true);
				} catch (SQLException e) {
					ExceptionHandler eh = new ExceptionHandler(mainControl);
					eh.fileSQLError(e.getMessage());
				}
				pairingIsActive = false;
			}
		}
	}

	public boolean getPairingIsActive() {
		return pairingIsActive;
	}

	private Boolean saveAndReloadTurnier() {

		Boolean ok = true;
		try {
			ok = this.mainControl.getSaveTournamentControl().saveChangedPartien();
		} catch (SQLException e) {
			ok = false;
			ExceptionHandler eh = new ExceptionHandler(mainControl);
			eh.fileSQLError(e.getMessage());
		}

		return ok;
	}

	public void setPairingIsActive(boolean pairingIsActive) {
		this.pairingIsActive = pairingIsActive;
	}

	private void setTabsEnable(Boolean enable) throws SQLException {
		int gruppenAnzahl = mainControl.getTournament().getAnzahlGruppen();
		TabbedPaneView[] tabAnzeigeView2 = this.mainControl.getTabbedPaneViewArray();
		mainControl.getNaviView().getTabellenPanel().setVisible(enable);
		mainControl.getNaviView().getPairingsPanel().setVisible(!enable);
		for (int i = 0; i < gruppenAnzahl; i++) {
			tabAnzeigeView2[i].getTabbedPane().setEnabledAt(0, enable);
			tabAnzeigeView2[i].getTabbedPane().setEnabledAt(1, enable);
			tabAnzeigeView2[i].getTabbedPane().setEnabledAt(2, !enable);
		}
		mainControl.getActionListenerTournamentItemsControl().reloadTurnier();

	}
}
