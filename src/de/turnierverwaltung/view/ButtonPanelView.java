package de.turnierverwaltung.view;

import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanelView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton okButton;
	private JButton cancelButton;
	private ImageIcon okIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/dialog-ok-3.png"))); //$NON-NLS-1$
	private ImageIcon cancelIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/dialog-cancel-3.png"))); //$NON-NLS-1$

	/**
	 * Create the panel.
	 */
	public ButtonPanelView() {
		setLayout(new FlowLayout(FlowLayout.LEFT));

	}

	public void makeAllButtons() {
		makeOKButton();
		makeCancelButton();
	}

	public void makeOKButton() {
		okButton = new JButton("Ok", okIcon);
		add(okButton);
	}

	public void makeCancelButton() {
		cancelButton = new JButton("Cancel", cancelIcon);
		add(cancelButton);
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

}
