package de.turnierverwaltung.model.table;

import javax.swing.table.DefaultTableModel;

public class PlayerListTableModel extends DefaultTableModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public PlayerListTableModel(final Object[][] data, final String[] columnNames) {
		super(data, columnNames);
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {

		boolean icE = false;
		if (columnIndex == 3) {
			icE = true;
		}
		if (columnIndex == 4) {
			icE = true;
		}
		return icE;
	}

}
