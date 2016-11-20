package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.turnierverwaltung.control.SidePanelControl;
import de.turnierverwaltung.model.Sidepanel;

public class FrontendSidePanelView {

	private JDialog jsonDialog;
	private JTextField headerTextField;
	private JTextArea bodyTextArea;
	private JPanel htmlAll;
	private ButtonPanelView buttonPane;
	// private JButton cancelButton;
	private JButton okButton;

	private ButtonSavePanelView buttonPanel;
	private JButton saveButton;
	private JButton addButton;
	private JButton deleteButton;
	private SidePanelControl dynTree;
	private ColorSelectorView colorSelectorPanel;

	public FrontendSidePanelView(ArrayList<Sidepanel> sidepanel, SidePanelControl dynTree) {
		this.dynTree = dynTree;
		// Erzeugung eines neuen Frames mit
		// dem Titel Beispiel JDialog
		jsonDialog = new JDialog();
		jsonDialog.setAlwaysOnTop(true);
		// Titel wird gesetzt
		jsonDialog.setTitle(Messages.getString("FrontendSidePanelView.0"));
		// Breite und Höhe des Fensters werden
		// auf 200 Pixel gesetzt
		jsonDialog.setSize(600, 500);
		// Dialog wird auf modal gesetzt
		jsonDialog.setModal(true);
		jsonDialog.setLayout(new BorderLayout());
		htmlAll = new JPanel();
		htmlAll.setLayout(new BorderLayout());
		Dimension dimTextField = new Dimension(350, 30);
		// Dimension dimTextArea = new Dimension(350, 350);
		int textFieldColumns = 18;
		int textFieldRows = 18;

		ContextMenuMouseListener cmmL = new ContextMenuMouseListener();

		headerTextField = new JTextField("", textFieldColumns);
		headerTextField.setPreferredSize(dimTextField);
		headerTextField.addMouseListener(cmmL);

		bodyTextArea = new JTextArea("", textFieldRows, textFieldColumns);
		// bodyTextArea.setPreferredSize(dimTextArea);
		bodyTextArea.addMouseListener(cmmL);

		buttonPane = new ButtonPanelView();
		buttonPane.makeOKButton();
		okButton = buttonPane.getOkButton();
		// cancelButton = buttonPane.getCancelButton();
		buttonPanel = new ButtonSavePanelView();
		addButton = buttonPanel.getAddButton();
		saveButton = buttonPanel.getSaveButton();
		deleteButton = buttonPanel.getDeleteButton();
		colorSelectorPanel = new ColorSelectorView();
	}

	public void makeDialog() {
		JPanel bothPanel = new JPanel();
		bothPanel.setLayout(new BorderLayout());
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Dimension dim = new Dimension(350, 30);
		// Dimension dimTextArea = new Dimension(350, 350);
		JLabel headerTextFieldLabel = new JLabel(Messages.getString("FrontendSidePanelView.1") + ":");
		// headerTextFieldLabel.setPreferredSize(dim);
		headerTextFieldLabel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel bodyTextAreaLabel = new JLabel(Messages.getString("FrontendSidePanelView.2") + ":");
		// bodyTextAreaLabel.setPreferredSize(dim);
		bodyTextAreaLabel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel colorSelectorLabel = new JLabel(Messages.getString("FrontendSidePanelView.3") + ":");
		// bodyTextAreaLabel.setPreferredSize(dim);
		bodyTextAreaLabel.setLayout(new FlowLayout(FlowLayout.LEFT));

		htmlPanel = new JPanel();
		// htmlPanel.setPreferredSize(dim);
		htmlPanel.setLayout(new BoxLayout(htmlPanel, BoxLayout.PAGE_AXIS));
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelPanel.add(headerTextFieldLabel);
		htmlPanel.add(labelPanel);
		htmlPanel.add(headerTextField);
		//leftPanel.add(htmlPanel, BorderLayout.NORTH);

//		htmlPanel = new JPanel();
//		htmlPanel.setLayout(new BoxLayout(htmlPanel, BoxLayout.PAGE_AXIS));
		labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelPanel.add(colorSelectorLabel);
		htmlPanel.add(labelPanel);
		htmlPanel.add(colorSelectorPanel);
		leftPanel.add(htmlPanel, BorderLayout.NORTH);
		
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new BoxLayout(htmlPanel, BoxLayout.PAGE_AXIS));
		// htmlPanel.setPreferredSize(dimTextArea);
		labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		labelPanel.add(bodyTextAreaLabel);
		htmlPanel.add(labelPanel);
		Dimension dimTextArea = new Dimension(350, 350);
		JScrollPane scrollPane = new JScrollPane(bodyTextArea);
		scrollPane.setPreferredSize(dimTextArea);
		htmlPanel.add(scrollPane);
		leftPanel.add(htmlPanel, BorderLayout.CENTER);

		

		bothPanel.add(leftPanel, BorderLayout.NORTH);
		htmlAll.add(bothPanel, BorderLayout.CENTER);
		// htmlAll.add(buttonPane, BorderLayout.SOUTH);

		Dimension dimdyn = new Dimension(200, 480);
		dynTree.setPreferredSize(dimdyn);
		JPanel left = new JPanel();
		JPanel right = new JPanel();

		left.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		right.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		right.add(htmlAll);
		left.add(dynTree);

		jsonDialog.add(right, BorderLayout.CENTER);

		jsonDialog.add(left, BorderLayout.WEST);
		buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		jsonDialog.add(buttonPane, BorderLayout.SOUTH);
		jsonDialog.pack();
		jsonDialog.setLocationRelativeTo(null);
		jsonDialog.setEnabled(true);
		jsonDialog.setVisible(true);
	}

	public JDialog getJsonDialog() {
		return jsonDialog;
	}

	public void setJsonDialog(JDialog jsonDialog) {
		this.jsonDialog = jsonDialog;
	}

	public JTextField getHeaderTextField() {
		return headerTextField;
	}

	public void setHeaderTextField(JTextField headerTextField) {
		this.headerTextField = headerTextField;
	}

	public JTextArea getBodyTextArea() {
		return bodyTextArea;
	}

	public void setBodyTextArea(JTextArea bodyTextArea) {
		this.bodyTextArea = bodyTextArea;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	public JButton getAddButton() {
		return addButton;
	}

	public void setAddButton(JButton addButton) {
		this.addButton = addButton;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(JButton deleteButton) {
		this.deleteButton = deleteButton;
	}

	public ColorSelectorView getColorSelectorPanel() {
		return colorSelectorPanel;
	}

	public void setColorSelectorPanel(ColorSelectorView colorSelectorPanel) {
		this.colorSelectorPanel = colorSelectorPanel;
	}

}
