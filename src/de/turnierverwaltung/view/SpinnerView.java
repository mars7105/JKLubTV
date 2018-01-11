package de.turnierverwaltung.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

public class SpinnerView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSpinner spinner;
	private String[] model;

	public SpinnerView(String[] model, int setValue, String labelHeader) {
		this.model = model;
		setLayout(new FlowLayout(FlowLayout.LEFT));
		spinner = new JSpinner();
		spinner.setModel(new SpinnerListModel(model));
		spinner.setValue(model[setValue]);
		spinner.setPreferredSize(new Dimension(50, 30));
		add(spinner);
		add(new JLabel(labelHeader));
	}

	public String getValue() {
		return (String) spinner.getValue();
	}

	public void resetValue() {
		spinner.setValue(model[model.length - 1]);
	}

	public void setValue(String value) {
		spinner.setValue(value);
	}
}
