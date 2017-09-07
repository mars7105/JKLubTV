package de.turnierverwaltung.control;

import javax.jnlp.FileContents;
import javax.jnlp.FileOpenService;
import javax.jnlp.FileSaveService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

public class JNLPControl {
	public void saveDialog(String fileName) {
		FileSaveService fss;
		FileOpenService fos;

		try {
			fos = (FileOpenService) ServiceManager.lookup("javax.jnlp.FileOpenService");
			fss = (FileSaveService) ServiceManager.lookup("javax.jnlp.FileSaveService");
		} catch (UnavailableServiceException e) {
			fss = null;
			fos = null;
		}

		if (fss != null && fos != null) {
			try {
				// get a file with FileOpenService
				FileContents fc = fos.openFileDialog(null, null);
				// one way to save a file
				FileContents newfc = fss.saveFileDialog(null, null, fc.getInputStream(), fileName);
				// another way to save a file
				FileContents newfc2 = fss.saveAsFileDialog(null, null, fc);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public FileContents openDialog(String fileName) {
		FileOpenService fos;
		FileContents fc = null;
		try {
			fos = (FileOpenService) ServiceManager.lookup("javax.jnlp.FileOpenService");
		} catch (UnavailableServiceException e) {
			fos = null;
		}

		if (fos != null) {
			try {
				// ask user to select a file through this service
				fc = fos.openFileDialog(null, null);
				// ask user to select multiple files through this service
				// FileContents[] fcs = fos.openMultiFileDialog(null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return fc;
	}

}
