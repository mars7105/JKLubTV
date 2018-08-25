package de.turnierverwaltung.control.export;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.model.CopyToClipboard;
import de.turnierverwaltung.model.table.CrossTable;
import de.turnierverwaltung.view.export.HTMLToClipBoardDialogView;
import de.turnierverwaltung.view.export.HTMLToClipBoardView;

public class HTMLCopyToClipboardControl {

	private final MainControl mainControl;

	public HTMLCopyToClipboardControl(final MainControl mainControl) {
		super();
		this.mainControl = mainControl;

	}

	public void copyToClipboard() {
		final Boolean ready = mainControl.getPairingsControl().checkNewTurnier();
		if (ready) {
			final int anzahlGruppen = mainControl.getTournament().getAnzahlGruppen();
			final String cssTable = mainControl.getPropertiesControl().getCSSTable();

			final String webserverPath = mainControl.getPropertiesControl().getWebserverPath();
			final HTMLToClipBoardDialogView htmlToClipboardDialog = new HTMLToClipBoardDialogView();

			final ArrayList<HTMLToClipBoardView> htmlToClipboardArray = new ArrayList<HTMLToClipBoardView>();

			for (int i = 0; i < anzahlGruppen; i++) {

				String wfn = mainControl.getTournament().getTurnierName();
				wfn += Messages.getString("PDFSaveControler.2")
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".pdf";
				final String webfilename1 = wfn.replaceAll(" ", "");

				String wfn2 = mainControl.getTournament().getTurnierName();
				wfn2 += Messages.getString("PDFSaveControler.8")
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".pdf";
				final String webfilename2 = wfn2.replaceAll(" ", "");

				final String ical = mainControl.getTournament().getTurnierName()
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + ".ics";
				final String webfilename3 = ical.replaceAll(" ", "");

				final String crossLabel = mainControl.getTournament().getTurnierName() + " "
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + " "
						+ Messages.getString("PDFSaveControler.22");
				final String meetingLabel = mainControl.getTournament().getTurnierName() + " "
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + " "
						+ Messages.getString("PDFSaveControler.23");
				final String allLabel = mainControl.getTournament().getTurnierName() + " "
						+ mainControl.getTournament().getGruppe()[i].getGruppenName() + " "
						+ Messages.getString("PDFSaveControler.21");
				if (mainControl.getCrossTable()[i] == null) {
					mainControl.getCrossTableControl().makeSimpleTableView(i);

					mainControl.getMeetingTableControl().makeSimpleTableView(i);

				}

				final CrossTable turnierTabelle = mainControl.getCrossTable()[i];

				final int spalte = mainControl.getCrossTableView()[i].getTable().getModel().getColumnCount();
				final int zeile = mainControl.getCrossTableView()[i].getTable().getModel().getRowCount();
				for (int x = 0; x < spalte; x++) {
					for (int y = 0; y < zeile; y++) {

						turnierTabelle.getTabellenMatrix()[x][y + 1] = (String) mainControl.getCrossTableView()[i]
								.getTable().getValueAt(y, x);

					}
				}

				final Boolean ohneHeaderundFooter = mainControl.getPropertiesControl().getOnlyTables();
				final Boolean showLink = mainControl.getPropertiesControl().getPDFLinks();

				final HTMLToClipBoardView crosshtmlToClipboard = new HTMLToClipBoardView();
				crosshtmlToClipboard.getLabel().setText(crossLabel);
				final String crosstable = mainControl.getCrossTable()[i].getHTMLTable(ohneHeaderundFooter,
						webserverPath, webfilename1, showLink, cssTable);
				crosshtmlToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						final CopyToClipboard clipBoardcopy = new CopyToClipboard();
						clipBoardcopy.copy(crosstable);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + crossLabel);
					}

				});

				htmlToClipboardArray.add(crosshtmlToClipboard);

				final HTMLToClipBoardView meetinghtmlToClipboard = new HTMLToClipBoardView();
				meetinghtmlToClipboard.getLabel().setText(meetingLabel);

				final String meetingtable = mainControl.getMeetingTableControl().getTerminTabelle()[i].getHTMLTable(
						ohneHeaderundFooter, webserverPath, webfilename2, webfilename3, showLink, cssTable);
				meetinghtmlToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						final CopyToClipboard clipBoardcopy = new CopyToClipboard();
						clipBoardcopy.copy(meetingtable);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + meetingLabel);
					}

				});

				htmlToClipboardArray.add(meetinghtmlToClipboard);
				final HTMLToClipBoardView allToClipboard = new HTMLToClipBoardView();
				allToClipboard.getLabel().setText(allLabel);
				final String crosstableall = mainControl.getCrossTable()[i].getHTMLTableOnlyWithHeader(
						ohneHeaderundFooter, webserverPath, webfilename1, showLink, cssTable);
				final String meetingtableall = mainControl.getMeetingTableControl().getTerminTabelle()[i]
						.getHTMLTableOnlyWithFooter(ohneHeaderundFooter, webserverPath, webfilename2, webfilename3,
								showLink, cssTable);
				allToClipboard.getCopyToClipBoardButton().addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {

						final CopyToClipboard clipBoardcopy = new CopyToClipboard();
						clipBoardcopy.copy(crosstableall + "\n" + meetingtableall);
						htmlToClipboardDialog.getStatusLabel().getTitleLabel().setText("Clipboard: " + allLabel);
					}

				});

				htmlToClipboardArray.add(allToClipboard);
			}
			htmlToClipboardDialog.makeDialog(htmlToClipboardArray);

			htmlToClipboardDialog.getButtonPanel().getOkButton().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(final ActionEvent e) {
					htmlToClipboardDialog.dispose();

				}

			});
			htmlToClipboardDialog.showDialog();
		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("HTMLSaveControler.21") + Messages.getString("HTMLSaveControler.22")); //$NON-NLS-1$ //$NON-NLS-2$

		}
	}
}
