package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import de.turnierverwaltung.view.FrontendTableTextView;

public class FrontendTableTextControl implements ActionListener {

	private JButton okButton;
	private JDialog dialog;

	public FrontendTableTextControl() {
		FrontendTableTextView fd = new FrontendTableTextView();
		dialog = fd.getJsonDialog();
		okButton = fd.getOkButton();
		okButton.addActionListener(this);
		fd.makeDialog();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.dispose();
	}

}
