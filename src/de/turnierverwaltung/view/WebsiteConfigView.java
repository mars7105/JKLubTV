package de.turnierverwaltung.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class WebsiteConfigView {

	private JDialog dialog;
	private JTabbedPane tabbedPane;
	private TitleLabelView titleView;
	private ButtonPanelView buttonPane;
	private JButton okButton;

	public WebsiteConfigView() {
		super();
		dialog = new JDialog();
		dialog.setAlwaysOnTop(true);
		dialog.setTitle(Messages.getString("FrontendTableTextView.0"));
		dialog.setModal(true);

		dialog.setLocationRelativeTo(null);
		dialog.setSize(600, 500);
		dialog.setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		titleView = new TitleLabelView("Website");
		buttonPane = new ButtonPanelView();
		buttonPane.makeOKButton();
		okButton = buttonPane.getOkButton();

	}

	public void makeDialog() {
		JScrollPane jsPane = new JScrollPane(tabbedPane);
		dialog.add(titleView, BorderLayout.NORTH);
		dialog.add(jsPane, BorderLayout.CENTER);
		dialog.add(buttonPane, BorderLayout.SOUTH);
		dialog.pack();
		dialog.setEnabled(true);
		dialog.setVisible(true);
	}

	public JDialog getDialog() {
		return dialog;
	}

	public void setDialog(JDialog dialog) {
		this.dialog = dialog;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public TitleLabelView getTitleView() {
		return titleView;
	}

	public void setTitleView(TitleLabelView titleView) {
		this.titleView = titleView;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

}
