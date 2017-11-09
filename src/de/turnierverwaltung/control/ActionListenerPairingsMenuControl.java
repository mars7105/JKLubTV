package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import de.turnierverwaltung.view.NaviView;
import de.turnierverwaltung.view.TabbedPaneView;

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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() .equals(naviView.getPairingsSaveButton())) {

			saveAndReloadTurnier();
			try {
				setTabsEnable(true);
			} catch (SQLException e) {
				mainControl.fileSQLError(e.getMessage());
			}
			pairingIsActive = false;

		}
		if (arg0.getSource().equals(naviView.getPairingsCancelButton()) ){
			int abfrage = abbrechenHinweis();
			if (abfrage == 0) {
				PairingsControl pairingsControl = mainControl.getPairingsControl();
				pairingsControl.getChangedPartien().clear();
				try {
					setTabsEnable(true);
				} catch (SQLException e) {
					mainControl.fileSQLError(e.getMessage());
				}
				pairingIsActive = false;
			}
		}
	}

	private int abbrechenHinweis() {
		int abfrage = 0;
		String hinweisText = Messages.getString("NaviController.21") //$NON-NLS-1$
				+ Messages.getString("NaviController.22") //$NON-NLS-1$
				+ Messages.getString("NaviController.34"); //$NON-NLS-1$

		abfrage = 1;
		// Custom button text
		Object[] options = { Messages.getString("NaviController.24"), Messages.getString("NaviController.25") }; //$NON-NLS-1$ //$NON-NLS-2$
		abfrage = JOptionPane.showOptionDialog(mainControl, hinweisText, Messages.getString("NaviController.26"), //$NON-NLS-1$
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		return abfrage;
	}

	private Boolean saveAndReloadTurnier() {

		Boolean ok = true;
		try {
			ok = this.mainControl.getSaveTurnierControl().saveChangedPartien();
		} catch (SQLException e) {
			ok = false;
			e.printStackTrace();
		}

		return ok;
	}

	public boolean getPairingIsActive() {
		return pairingIsActive;
	}

	public void setPairingIsActive(boolean pairingIsActive) {
		this.pairingIsActive = pairingIsActive;
	}

	private void setTabsEnable(Boolean enable) throws SQLException {
		int gruppenAnzahl = mainControl.getTurnier().getAnzahlGruppen();
		TabbedPaneView[] tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		mainControl.getNaviView().getTabellenPanel().setVisible(enable);
		mainControl.getNaviView().getPairingsPanel().setVisible(!enable);
		for (int i = 0; i < gruppenAnzahl; i++) {
			tabAnzeigeView2[i].getTabbedPane().setEnabledAt(0, enable);
			tabAnzeigeView2[i].getTabbedPane().setEnabledAt(1, enable);
			tabAnzeigeView2[i].getTabbedPane().setEnabledAt(2, !enable);
		}
		mainControl.getTurnierListeLadenControl().reloadTurnier();

	}
}
