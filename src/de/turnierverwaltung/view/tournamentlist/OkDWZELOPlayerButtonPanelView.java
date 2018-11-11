package de.turnierverwaltung.view.tournamentlist;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import de.turnierverwaltung.view.ButtonPanelView;
import de.turnierverwaltung.view.Messages;

public class OkDWZELOPlayerButtonPanelView extends ButtonPanelView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ImageIcon userNew = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-new-3.png"))); //$NON-NLS-1$
	private final ImageIcon DEWISSearch = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/db.png"))); //$NON-NLS-1$
	private JButton spielerDEWISSearchButton;
	private JButton spielerAddButton;
	private JButton spielerELOSearchButton;

	public OkDWZELOPlayerButtonPanelView() {

	}

	public void makeAllButtons() {
		makeOKButton();
		makeCancelButton();
		makeELOButton();
		makeDWZButton();
		makeAddPlayerButton();
	}

	public void makeELOButton() {
		// ELO Abfrage
		spielerELOSearchButton = new JButton(Messages.getString("NaviView.34"), DEWISSearch); //$NON-NLS-1$
		add(spielerELOSearchButton);
	}

	public void makeDWZButton() {
		// DWZ Abfrage
		spielerDEWISSearchButton = new JButton(Messages.getString("NaviView.23"), DEWISSearch); //$NON-NLS-1$

		add(spielerDEWISSearchButton);
	}

	public void makeAddPlayerButton() {
		// neuer Spieler
		spielerAddButton = new JButton(Messages.getString("NaviView.19"), userNew); //$NON-NLS-1$

		add(spielerAddButton);
	}

	public JButton getSpielerDEWISSearchButton() {
		return spielerDEWISSearchButton;
	}

	public void setSpielerDEWISSearchButton(JButton spielerDEWISSearchButton) {
		this.spielerDEWISSearchButton = spielerDEWISSearchButton;
	}

	public JButton getSpielerAddButton() {
		return spielerAddButton;
	}

	public void setSpielerAddButton(JButton spielerAddButton) {
		this.spielerAddButton = spielerAddButton;
	}

	public JButton getSpielerELOSearchButton() {
		return spielerELOSearchButton;
	}

	public void setSpielerELOSearchButton(JButton spielerELOSearchButton) {
		this.spielerELOSearchButton = spielerELOSearchButton;
	}
	
}
