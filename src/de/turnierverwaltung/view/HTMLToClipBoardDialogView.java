package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;

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

	public void makeDialog(ArrayList<HTMLToClipBoardView> htmlToClipBoard) {
		TitleLabelView titleview = new TitleLabelView(Messages.getString("HTMLToClipBoardView.1"));
		titleview.setFlowLayoutLeft();
		setTitle(Messages.getString("HTMLToClipBoardView.2"));
		setLayout(new BorderLayout());
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		ListIterator<HTMLToClipBoardView> list = htmlToClipBoard.listIterator();
		while (list.hasNext()) {

			main.add(list.next());
		}

		main.add(statusLabel);
		add(titleview, BorderLayout.NORTH);
		add(main, BorderLayout.CENTER);
		buttonPanel.makeOKButton();
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.PAGE_AXIS));
		southPanel.add(new JSeparator());

		southPanel.add(buttonPanel);

		add(southPanel, BorderLayout.SOUTH);

		pack();
		statusLabel.setFlowLayoutLeft();
		setEnabled(true);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public ButtonPanelView getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(ButtonPanelView buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public ArrayList<HTMLToClipBoardView> getHtmlToClipBoard() {
		return htmlToClipBoard;
	}

	public void setHtmlToClipBoard(ArrayList<HTMLToClipBoardView> htmlToClipBoard) {
		this.htmlToClipBoard = htmlToClipBoard;
	}

	public TitleLabelView getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(TitleLabelView statusLabel) {
		this.statusLabel = statusLabel;
	}

}
