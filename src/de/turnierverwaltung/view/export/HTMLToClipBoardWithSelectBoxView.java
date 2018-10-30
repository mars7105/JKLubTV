package de.turnierverwaltung.view.export;

import java.util.ArrayList;

import javax.swing.JComboBox;

public class HTMLToClipBoardWithSelectBoxView extends HTMLToClipBoardView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JComboBox<String> jComboBox;

	public HTMLToClipBoardWithSelectBoxView(ArrayList<String> selectItems) {
		super();
		jComboBox = new JComboBox<String>();
		for (String item : selectItems) {
			jComboBox.addItem(item);
		}
		add(jComboBox);

	}

	public JComboBox<String> getjComboBox() {
		return jComboBox;
	}

	public void setjComboBox(JComboBox<String> jComboBox) {
		this.jComboBox = jComboBox;
	}

}
