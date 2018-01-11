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
	private JButton helpButton;
	private ImageIcon helpIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/help.png")));

	/**
	 * Create the panel.
	 */
	public ButtonPanelView() {
		setLayout(new FlowLayout(FlowLayout.LEFT));

	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JButton getHelpButton() {
		return helpButton;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void makeAllButtons() {
		makeOKButton();
		makeCancelButton();

	}

	public void makeCancelButton() {
		cancelButton = new JButton(Messages.getString("ButtonPanelView.1"), cancelIcon);
		cancelButton.setActionCommand(Messages.getString("ButtonPanelView.1")); //$NON-NLS-1$

		add(cancelButton);
	}

	public void makeHelpButton() {
		helpButton = new JButton(helpIcon);

		add(helpButton);
	}

	public void makeOKButton() {
		okButton = new JButton(Messages.getString("ButtonPanelView.0"), okIcon);
		okButton.setActionCommand(Messages.getString("ButtonPanelView.0")); //$NON-NLS-1$

		add(okButton);
		// makeHelpButton();
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setHelpButton(JButton helpButton) {
		this.helpButton = helpButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

}
