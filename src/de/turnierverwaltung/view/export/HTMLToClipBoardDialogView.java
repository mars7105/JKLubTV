package de.turnierverwaltung.view.export;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import de.turnierverwaltung.view.ButtonPanelView;
import de.turnierverwaltung.view.Messages;
import de.turnierverwaltung.view.TitleLabelView;

public class HTMLToClipBoardDialogView extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ButtonPanelView buttonPanel;
	private ArrayList<HTMLToClipBoardView> htmlToClipBoard;
	private TitleLabelView statusLabel;

	public HTMLToClipBoardDialogView() {
		super();
		buttonPanel = new ButtonPanelView();
		htmlToClipBoard = new ArrayList<HTMLToClipBoardView>();
		statusLabel = new TitleLabelView("Clipboard:");
		statusLabel.setFlowLayoutLeft();
	}

	public HTMLToClipBoardDialogView(ButtonPanelView buttonPanel, ArrayList<HTMLToClipBoardView> htmlToClipBoard) {
		super();
		this.buttonPanel = buttonPanel;
		this.htmlToClipBoard = htmlToClipBoard;
		statusLabel = new TitleLabelView("Clipboard:");

		makeDialog(htmlToClipBoard);
	}

	public ButtonPanelView getButtonPanel() {
		return buttonPanel;
	}

	public ArrayList<HTMLToClipBoardView> getHtmlToClipBoard() {
		return htmlToClipBoard;
	}

	public TitleLabelView getStatusLabel() {
		return statusLabel;
	}

	public void makeDialog(ArrayList<HTMLToClipBoardView> htmlToClipBoard) {
		TitleLabelView titleview = new TitleLabelView(Messages.getString("HTMLToClipBoardView.1"));
		titleview.setFlowLayoutLeft();
		setTitle(Messages.getString("HTMLToClipBoardView.2"));
		setLayout(new BorderLayout());
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		ListIterator<HTMLToClipBoardView> list = htmlToClipBoard.listIterator();
		
		while (list.hasNext()) {
			JSeparator separator = new JSeparator();

			separator.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			main.add(list.next());
			main.add(separator);
		}

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		add(titleview, BorderLayout.NORTH);

		mainPanel.add(main, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(mainPanel);
		add(scrollPane, BorderLayout.CENTER);
		buttonPanel.makeOKButton();
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.PAGE_AXIS));
		southPanel.add(new JSeparator());
		southPanel.add(statusLabel);
		southPanel.add(buttonPanel);

		add(southPanel, BorderLayout.SOUTH);
		statusLabel.setFlowLayoutLeft();

	}

	public void showDialog() {
		pack();

		setLocationRelativeTo(null);
		setEnabled(true);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setVisible(true);

	}

	public void setButtonPanel(ButtonPanelView buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public void setHtmlToClipBoard(ArrayList<HTMLToClipBoardView> htmlToClipBoard) {
		this.htmlToClipBoard = htmlToClipBoard;
	}

	public void setStatusLabel(TitleLabelView statusLabel) {
		this.statusLabel = statusLabel;
	}

}
