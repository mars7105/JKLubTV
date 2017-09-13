package de.turnierverwaltung.control;

import java.io.File;

//JKlubTV - Ein Programm zum verwalten von Schach Turnieren
//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import de.turnierverwaltung.model.Tournament;

public class PDFOutputControl {

	/**
	 * Creates a PDF with information about the movies
	 * 
	 * @param turnier
	 * 
	 * @param filename
	 *            the name of the PDF file that will be created.
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void createTurnierPdf(Tournament turnier, String titel, String absolutePath, String[][] tabellenMatrix) {
		int n = 0;
		File file = new File(absolutePath);
		if (file.exists()) {
			Object[] options = { Messages.getString("SaveDialog.2"), Messages.getString("SaveDialog.3") };
			n = JOptionPane.showOptionDialog(null,
					Messages.getString("SaveDialog.0") + file.getAbsolutePath() + Messages.getString("SaveDialog.1"),
					"Dateioperation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					options[1]);
		}
		if (n == 0) {
			// step 1
			Document document = new Document();
			// step 2
			try {
				PdfWriter.getInstance(document, new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// step 3
			document.open();
			// step 4
			try {
				document.add(new Paragraph(titel));
				document.add(new Paragraph(" ")); //$NON-NLS-1$
				document.add(createTurnierTabelle(turnier, tabellenMatrix));
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// step 5
			document.close();
		}
	}

	public void createTerminPdf(String titel, String absolutePath, String[][] tabellenMatrix) {
		int n = 0;
		
		File file = new File(absolutePath);
		if (file.exists()) {
			Object[] options = { Messages.getString("SaveDialog.2"), Messages.getString("SaveDialog.3") };
			n = JOptionPane.showOptionDialog(null,
					Messages.getString("SaveDialog.0") + file.getAbsolutePath() + Messages.getString("SaveDialog.1"),
					"Dateioperation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					options[1]);
		}
		if (n == 0) {
			// step 1
			Document document = new Document();
			// step 2
			try {
				PdfWriter.getInstance(document, new FileOutputStream(file));
			} catch (FileNotFoundException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// step 3
			document.open();
			// step 4
			try {
				document.add(new Paragraph(titel));
				document.add(new Paragraph(" ")); //$NON-NLS-1$
				document.add(createTerminTabelle(tabellenMatrix));
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// step 5
			document.close();
		}
	}

	public static PdfPTable createTurnierTabelle(Tournament turnier, String[][] stringTable) throws DocumentException {
		int spalten = stringTable[0].length;
		int zeilen = stringTable.length;
		for (int i = 0; i < zeilen; i++) {
			String replacedStr = stringTable[i][0].replaceAll("<br />", ""); //$NON-NLS-1$ //$NON-NLS-2$
			stringTable[i][0] = replacedStr;
		}

		PdfPTable table = new PdfPTable(zeilen);
		float[] fl = new float[zeilen];
		fl[0] = 5;
		if (turnier.getNoDWZCalc() == true) {
			fl[1] = 1;

		} else {
			fl[1] = 3;

		}
		if (turnier.getNoFolgeDWZCalc() == true) {

			fl[2] = 1;
		} else {
			fl[2] = 4;
		}

		for (int i = 0; i <= zeilen; i++) {
			if (i > 2 && i < zeilen - 3) {
				fl[i] = 1;
			}
			if (i >= zeilen - 2) {
				fl[i - 1] = 2;
			}
		}
		Font font = new Font(FontFamily.HELVETICA, 8);

		for (int x = 0; x < spalten; x++) {

			for (int y = 0; y < zeilen; y++) {

				Phrase ph = new Phrase(stringTable[y][x]);
				ph.setFont(font);
				PdfPCell cell = new PdfPCell(ph);

				table.addCell(cell);

			}

		}
		table.setWidths(fl);

		return table;

	}

	public static PdfPTable createTerminTabelle(String[][] stringTable) throws DocumentException {
		int spalten = stringTable[0].length;
		int zeilen = stringTable.length;
		for (int i = 0; i < zeilen; i++) {
			String replacedStr = stringTable[i][0].replaceAll("<br />", ""); //$NON-NLS-1$ //$NON-NLS-2$
			stringTable[i][0] = replacedStr;
		}

		PdfPTable table = new PdfPTable(zeilen);

		Font font = new Font(FontFamily.HELVETICA, 8);

		for (int x = 0; x < spalten; x++) {

			for (int y = 0; y < zeilen; y++) {

				Phrase ph = new Phrase(stringTable[y][x]);
				ph.setFont(font);
				PdfPCell cell = new PdfPCell(ph);

				table.addCell(cell);

			}

		}

		return table;

	}

}