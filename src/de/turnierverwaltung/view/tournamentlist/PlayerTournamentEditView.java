package de.turnierverwaltung.view.tournamentlist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.SystemColor;

import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import de.turnierverwaltung.model.table.PlayerListTableModel;
import de.turnierverwaltung.view.TitleLabelView;

public class PlayerTournamentEditView extends JDialog {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final PlayerListTableModel tableModel;
	private final JTable table;

	private OkDWZELOPlayerButtonPanelView buttonPanel;
	private int index;
	protected int lineIndex;

	public PlayerTournamentEditView(final PlayerListTableModel tableModel, final String groupName,
			final Action deleteAction) {
		super();
		index = 0;
		lineIndex = 0;
		setTitle("Playerlist");
		final TitleLabelView label = new TitleLabelView(groupName);
		this.tableModel = tableModel;

		setLayout(new BorderLayout());
		add(label, BorderLayout.NORTH);
		table = new JTable(this.tableModel);
		final Color titleColor = new Color((SystemColor.text).getRGB());
		final Color titleTextColor = new Color((SystemColor.textText).getRGB());
		setBackground(titleColor);
		setForeground(titleTextColor);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(40);

		@SuppressWarnings("unused")
		final ButtonColumn buttonColumn = new ButtonColumn(table, deleteAction, 3);
		setColumnWidth();

		final JScrollPane sPane = new JScrollPane();
		sPane.setViewportView(table);
		sPane.setBackground(Color.white);
		final JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(sPane, BorderLayout.CENTER);
		add(contentPanel, BorderLayout.CENTER);
		contentPanel.updateUI();
		buttonPanel = new OkDWZELOPlayerButtonPanelView();
		buttonPanel.makeOKButton();

		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void showDialog() {
		pack();

		setLocationRelativeTo(null);
		setEnabled(true);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setVisible(true);

	}

	private void setColumnWidth() {
		final int playerWidth = 200;
		final int medium = 75;
		final int high = 200;

		final int columnCount = table.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			final TableColumn c = table.getColumnModel().getColumn(i);

			if (i == 0) {
				c.setPreferredWidth(playerWidth);
			}

			if (i > 0 && i < 3) {
				c.setPreferredWidth(medium);
			}
			if (i == 3) {
				c.setPreferredWidth(high);
			}

		}

	}

	public OkDWZELOPlayerButtonPanelView getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(final OkDWZELOPlayerButtonPanelView buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setLineIndex(int lineIndex) {
		this.lineIndex = lineIndex;

	}

	public int getLineIndex() {
		return lineIndex;
	}

}
