package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class StartpageDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private ButtonPanelView buttonPanel;

	public StartpageDialog(JPanel mainPanel) {
		super();

		getContentPane().setLayout(new BorderLayout());
		this.mainPanel = mainPanel;
		buttonPanel = new ButtonPanelView();
		buttonPanel.makeAllButtons();
		JScrollPane jsPane = new JScrollPane();
		jsPane.setViewportView(mainPanel);
		getContentPane().add(jsPane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	public StartpageDialog(Window arg0, String arg1, ModalityType arg2) {
		super(arg0, arg1, arg2);
		getContentPane().setLayout(new BorderLayout());
//		this.mainPanel = mainPanel;
		buttonPanel = new ButtonPanelView();
		buttonPanel.makeAllButtons();
		JScrollPane jsPane = new JScrollPane();
		jsPane.setViewportView(mainPanel);
		getContentPane().add(jsPane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public ButtonPanelView getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(ButtonPanelView buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public JButton getOkButton() {
		return buttonPanel.getOkButton();
	}

	public JButton getCancelButton() {
		return buttonPanel.getCancelButton();
	}

	public void enableDialog() {
		this.setLocationRelativeTo(null);
		// this.pack();
		this.setEnabled(true);
		this.setVisible(true);
	}
}
