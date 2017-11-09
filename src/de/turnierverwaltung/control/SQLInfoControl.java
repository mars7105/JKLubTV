package de.turnierverwaltung.control;

import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.model.Info;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.mysql.DAOFactory;
import de.turnierverwaltung.mysql.InfoDAO;

public class SQLInfoControl {
	private DAOFactory daoFactory;
	private InfoDAO mySQLInfoDAO;

	public SQLInfoControl() {
		daoFactory = DAOFactory.getDAOFactory(TournamentConstants.DATABASE_DRIVER);
		mySQLInfoDAO = daoFactory.getInfoDAO();

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

	public void deleteInfo(Info info) throws SQLException {
		mySQLInfoDAO.deleteInfo(info.getInfoId());
	}

}
