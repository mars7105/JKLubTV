package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;

public class StartpageDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StartpagePanelView mainPanel;
	private ButtonPanelView buttonPanel;
	private JScrollPane jsPane;

	public StartpageDialog(StartpagePanelView mainPanel) {
		super();

		getContentPane().setLayout(new BorderLayout());
		this.mainPanel = mainPanel;
		buttonPanel = new ButtonPanelView();
		buttonPanel.makeAllButtons();
		JScrollPane jsPane = new JScrollPane();
		jsPane.setViewportView(this.mainPanel);
		getContentPane().add(jsPane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	public StartpageDialog(Window arg0, String arg1, ModalityType arg2) {
		super(arg0, arg1, arg2);
		getContentPane().setLayout(new BorderLayout());
		// this.mainPanel = mainPanel;
		buttonPanel = new ButtonPanelView();
		buttonPanel.makeOKButton();
		jsPane = new JScrollPane();
		jsPane.setViewportView(mainPanel);
		getContentPane().add(jsPane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	public StartpageDialog() {
		super();
		getContentPane().setLayout(new BorderLayout());
		// this.mainPanel = mainPanel;
		buttonPanel = new ButtonPanelView();
		buttonPanel.makeOKButton();
		jsPane = new JScrollPane();
		jsPane.setViewportView(mainPanel);
		getContentPane().add(jsPane, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	public StartpagePanelView getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(StartpagePanelView mainPanel) {
		this.mainPanel = mainPanel;
		jsPane.setViewportView(this.mainPanel);
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

	public void showDialog() {
		// this.setLocationRelativeTo(null);
		// this.pack();
		this.setEnabled(true);
		this.setVisible(true);
	}
}
