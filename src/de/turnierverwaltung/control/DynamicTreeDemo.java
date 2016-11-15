package de.turnierverwaltung.control;
/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

//package components;
/*
 * This code is based on an example provided by Richard Stanford, 
 * a tutorial reader.
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import de.turnierverwaltung.model.Sidepanel;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.SidepanelDAO;
import de.turnierverwaltung.view.ButtonSavePanelView;
import de.turnierverwaltung.view.DynamicTree;
import de.turnierverwaltung.view.FrontendSidePanelView;

public class DynamicTreeDemo extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3165185076576784076L;
	private static String ADD_COMMAND = "add";
	private static String SAVE_COMMAND = "save";
	private static String REMOVE_COMMAND = "remove";
	private ArrayList<Sidepanel> sidepanel;
	private DynamicTree treePanel;
	private MainControl mainControl;
	private SidepanelDAO sidepanelDAO;
	private FrontendSidePanelView sidePanelView;
	private int selectedRow;

	public DynamicTreeDemo(MainControl mainControl) {
		super(new BorderLayout());
		this.mainControl = mainControl;
		selectedRow = 0;
		DAOFactory daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		sidepanelDAO = daoFactory.getSidepanelDAO();

		try {
			sidepanel = sidepanelDAO.selectAllSidepanel(this.mainControl.getTurnier().getTurnierId());
		} catch (SQLException e1) {
			// TODO Automatisch generierter Erfassungsblock
			e1.printStackTrace();
		}
		sidePanelView = mainControl.getFrontendSidePanelView();
		// Create the components.
		treePanel = new DynamicTree();
		populateTree();
		treePanel.getTree().getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		ButtonSavePanelView buttonPanel = new ButtonSavePanelView();
		JButton addButton = buttonPanel.getAddButton();// new JButton("Add");
		addButton.setActionCommand(ADD_COMMAND);
		addButton.addActionListener(this);

		JButton saveButton = buttonPanel.getSaveButton();// new
															// JButton("Remove");
		saveButton.setActionCommand(SAVE_COMMAND);
		saveButton.addActionListener(this);

		JButton removeButton = buttonPanel.getDeleteButton();// new
																// JButton("Clear");
		removeButton.setActionCommand(REMOVE_COMMAND);
		removeButton.addActionListener(this);

		// Lay everything out.
		treePanel.setPreferredSize(new Dimension(300, 150));
		add(treePanel, BorderLayout.CENTER);

		add(buttonPanel, BorderLayout.SOUTH);

		treePanel.getTree().getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {

				// TreePath path = e.getNewLeadSelectionPath();
				// String lastpath = path.getLastPathComponent().toString();

				int[] row = treePanel.getTree().getSelectionRows();
				for (int i = 0; i < row.length; i++) {
					System.out.println(row[i]);
					if (row.length == 1) {
						selectedRow = row[i];
					}
				}
				if (selectedRow > 0) {
					mainControl.getFrontendSidePanelView().getHeaderTextField()
							.setText(sidepanel.get(selectedRow - 1).getHeader());
					mainControl.getFrontendSidePanelView().getBodyTextArea()
							.setText(sidepanel.get(selectedRow - 1).getBody());
				}
				if (selectedRow == 0) {
					mainControl.getFrontendSidePanelView().getHeaderTextField().setText("");
					mainControl.getFrontendSidePanelView().getBodyTextArea().setText("");
				}
			}

		});
	}

	public void populateTree() {

		for (Sidepanel sidepanelItem : sidepanel) {
			treePanel.addObject(null, sidepanelItem);
		}
		treePanel.getTree().expandRow(1);

		for (int i = 0; i < treePanel.getTree().getRowCount(); i++) {
			treePanel.getTree().expandRow(i);
		}
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (ADD_COMMAND.equals(command)) {
			// Add button clicked
			String header = "new item";
			String body = "";
			if (selectedRow == 0) {
				header = mainControl.getFrontendSidePanelView().getHeaderTextField().getText();
				body = mainControl.getFrontendSidePanelView().getBodyTextArea().getText();
				if (header.isEmpty()) {
					header = "new item";
					body = "";
				}
			}
			Sidepanel temp = new Sidepanel(header, body, 0);

			try {

				int id = sidepanelDAO.insertSidepanel(temp, mainControl.getTurnier().getTurnierId());
				temp.setIdSidepanel(id);
				sidepanel.add(temp);
				treePanel.addObject(null, temp);
			} catch (SQLException e1) {
				// TODO Automatisch generierter Erfassungsblock
				e1.printStackTrace();
			}
		} else if (SAVE_COMMAND.equals(command)) {
			// Save button clicked
			// treePanel.removeCurrentNode();
			String headerText = mainControl.getFrontendSidePanelView().getHeaderTextField().getText();
			String bodyText = mainControl.getFrontendSidePanelView().getBodyTextArea().getText();

			if (headerText != null && selectedRow > 0) {
				try {
					sidepanel.get(selectedRow - 1).setHeader(headerText);
					sidepanel.get(selectedRow - 1).setBody(bodyText);
					sidepanelDAO.updateSidepanel(sidepanel.get(selectedRow - 1));
					DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(headerText);
					treePanel.getTreeModel().insertNodeInto(childNode, treePanel.getRootNode(), selectedRow);
					treePanel.removeCurrentNode();

					treePanel.getTreeModel().reload();
					resetTreeSelect();
				} catch (SQLException e1) {
					// TODO Automatisch generierter Erfassungsblock
					e1.printStackTrace();
				} catch (IndexOutOfBoundsException e2) {

				}
				
			}

		} else if (REMOVE_COMMAND.equals(command)) {
			// Clear button clicked.
			try {
				sidepanelDAO.deleteSidepanel(sidepanel.get(selectedRow - 1).getIdSidepanel());
				treePanel.removeCurrentNode();
			} catch (SQLException e1) {
				// TODO Automatisch generierter Erfassungsblock
				e1.printStackTrace();
			}
			sidepanel = new ArrayList<Sidepanel>();

			try {
				sidepanel = sidepanelDAO.selectAllSidepanel(this.mainControl.getTurnier().getTurnierId());
			} catch (SQLException e1) {
				// TODO Automatisch generierter Erfassungsblock
				e1.printStackTrace();
			}
			resetTreeSelect();
		}
	}

	private void resetTreeSelect() {
		selectedRow = 0;
		mainControl.getFrontendSidePanelView().getHeaderTextField().setText("");
		mainControl.getFrontendSidePanelView().getBodyTextArea().setText("");
		treePanel.getTreeModel().reload();
	}

	public FrontendSidePanelView getSidePanelView() {
		return sidePanelView;
	}

	public void setSidePanelView(FrontendSidePanelView sidePanelView) {
		this.sidePanelView = sidePanelView;
	}

	public DynamicTree getTreePanel() {
		return treePanel;
	}

	public void setTreePanel(DynamicTree treePanel) {
		this.treePanel = treePanel;
	}

}