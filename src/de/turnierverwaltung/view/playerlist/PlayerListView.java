package de.turnierverwaltung.view.playerlist;
//JKlubTV - Ein Programm zum verwalten von Schach Turnieren

import java.awt.BorderLayout;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import de.turnierverwaltung.model.table.PlayerListTableModel;

public class PlayerListView extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;
	private final PlayerListTableModel playerListModel;

	public PlayerListView(final PlayerListTableModel playerListModel, final Action editAction,
			final Action deleteAction) {

		this.playerListModel = playerListModel;
		setLayout(new BorderLayout());

		table = new JTable(this.playerListModel);

		setColumnWidth();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(30);
		@SuppressWarnings("unused")
		final ButtonColumn buttonColumn = new ButtonColumn(table, editAction, deleteAction, 3, 4);
		final JScrollPane sPane = new JScrollPane();
		sPane.setViewportView(table);

		add(sPane, BorderLayout.CENTER);

		setVisible(true);
		updateUI();
	}

	public JTable getTable() {
		return table;
	}

	private void setColumnWidth() {

		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		sorter.setRowFilter(RowFilter.regexFilter("[a-zA-Z]", 0));

		table.setRowSorter(sorter);

	}

	public void setTable(final JTable table) {
		this.table = table;
	}

}
