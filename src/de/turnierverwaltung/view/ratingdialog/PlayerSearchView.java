package de.turnierverwaltung.view.ratingdialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.turnierverwaltung.view.ButtonPanelView;
import de.turnierverwaltung.view.Messages;

public class PlayerSearchView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField searchField;
	private JPanel dsbPanel;
	private ButtonPanelView buttonPane;
	private JButton okButton;
	private JButton cancelButton;

	public PlayerSearchView(String title) {
		searchField = new JTextField(20);
		JLabel searchFieldLabel = new JLabel(Messages.getString("PlayerSearchView.0"));
		setLayout(new BorderLayout());
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		searchPanel.add(searchFieldLabel);
		searchPanel.add(searchField);
		add(searchPanel, BorderLayout.NORTH);
		this.dsbPanel = new DSBDWZPlayerView(title);
		add(this.dsbPanel, BorderLayout.CENTER);
		buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();
		okButton = buttonPane.getOkButton();
		cancelButton = buttonPane.getCancelButton();
		okButton.setText(Messages.getString("DEWISDialogView.6"));
		cancelButton.setText(Messages.getString("DEWISDialogView.7"));
		add(buttonPane, BorderLayout.SOUTH);
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JPanel getDsbPanel() {
		return dsbPanel;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JTextField getSearchField() {
		return searchField;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setDsbPanel(DSBDWZPlayerView dsbPanel) {
		remove(this.dsbPanel);
		this.dsbPanel = dsbPanel;
		add(dsbPanel, BorderLayout.CENTER);
	}

	public void setDsbPanel(ELOPlayerView spielerSearchPanelList) {
		remove(this.dsbPanel);
		this.dsbPanel = spielerSearchPanelList;
		add(spielerSearchPanelList, BorderLayout.CENTER);
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public void setSearchField(JTextField searchField) {
		this.searchField = searchField;
	}

}
