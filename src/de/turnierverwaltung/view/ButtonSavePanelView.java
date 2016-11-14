package de.turnierverwaltung.view;

import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonSavePanelView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2508971975928437441L;

	private JButton saveButton;
	private JButton deleteButton;
	private JButton addButton;
	private ImageIcon saveIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-save-5.png"))); //$NON-NLS-1$
	private ImageIcon deleteIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-delete-2.png"))); //$NON-NLS-1$
	private ImageIcon addIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-add-2.png"))); //$NON-NLS-1$

	/**
	 * Create the panel.
	 */
	public ButtonSavePanelView() {

		setLayout(new FlowLayout(FlowLayout.LEFT));
		addButton = new JButton(Messages.getString("ButtonSavePanelView.0"), addIcon);
		addButton.setToolTipText(Messages.getString("ButtonSavePanelView.1"));
		add(addButton);

		saveButton = new JButton(Messages.getString("ButtonSavePanelView.0"), saveIcon);
		saveButton.setToolTipText(Messages.getString("ButtonSavePanelView.2"));
		add(saveButton);

		deleteButton = new JButton(Messages.getString("ButtonSavePanelView.0"), deleteIcon);
		deleteButton.setToolTipText(Messages.getString("ButtonSavePanelView.3"));
		add(deleteButton);

	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(JButton deleteButton) {
		this.deleteButton = deleteButton;
	}

	public JButton getAddButton() {
		return addButton;
	}

	public void setAddButton(JButton addButton) {
		this.addButton = addButton;
	}

}
