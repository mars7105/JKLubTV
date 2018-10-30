package de.turnierverwaltung.control.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.model.Info;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.sqlite.DAOFactory;
import de.turnierverwaltung.sqlite.InfoDAO;

public class SQLInfoControl {
	private DAOFactory daoFactory;
	private InfoDAO mySQLInfoDAO;

	public SQLInfoControl() {
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLInfoDAO = daoFactory.getInfoDAO();

	}

	public void deleteOneInfo(Info info) throws SQLException {
		mySQLInfoDAO.deleteInfo(info.getInfoId());
	}

	public ArrayList<Info> getAllInfos() throws SQLException {
		ArrayList<Info> infos;
		infos = mySQLInfoDAO.getAllInfos();

		return infos;

	}

	public int insertOneInfo(Info info) throws SQLException {

		int infoId = -1;

		infoId = mySQLInfoDAO.insertInfo(info);
		info.setInfoId(infoId);

		return infoId;
	}

	public void updateOneInfo(Info info) throws SQLException {

		mySQLInfoDAO.updateInfo(info);

	}

}
