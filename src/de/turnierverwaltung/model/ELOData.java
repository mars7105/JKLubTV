package de.turnierverwaltung.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ELOData {
	private String fideid;
	private String name;

	private String country;
	private String sex;
	private String title;
	private String w_title;
	private String o_title;
	private String foa_title;
	private String rating;
	private String games;
	private String k;
	private String birthday;
	private String flag;
	private int age;
	private int spielerId;

	public ELOData(String fideid, String name, String country, String sex, String title, String w_title, String o_title,
			String foa_title, String rating, String games, String k, String birthday, String flag) {
		super();
		this.fideid = fideid;
		this.name = name;
		this.country = country;
		this.sex = sex;
		this.title = title;
		this.w_title = w_title;
		this.o_title = o_title;
		this.foa_title = foa_title;
		this.rating = rating;
		this.games = games;
		this.k = k;
		this.birthday = birthday;
		this.flag = flag;
		spielerId = -1;
	}

	public ELOData() {
		this.fideid = "";
		this.name = "";
		this.country = "";
		this.sex = "";
		this.title = "";
		this.w_title = "";
		this.o_title = "";
		this.foa_title = "";
		this.rating = "";
		this.games = "";
		this.k = "";
		this.birthday = "";
		this.flag = "";
		this.name = "";
		this.country = "";
		this.sex = "";
		this.title = "";
		this.w_title = "";
		this.o_title = "";
		this.foa_title = "";
		this.rating = "";
		this.games = "";
		this.k = "";
		this.birthday = "";
		this.flag = "";
		spielerId = -1;
	}

	// public String getFideid() {
	// return fideid;
	// }
	public int getFideid() {
		int fid = -1;
		try {
			fid = Integer.parseInt(fideid);
		} catch (NumberFormatException e) {
			fid = -1;
		}
		return fid;
	}

	public void setFideid(String fideid) {
		this.fideid = fideid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getW_title() {
		return w_title;
	}

	public void setW_title(String w_title) {
		this.w_title = w_title;
	}

	public String getO_title() {
		return o_title;
	}

	public void setO_title(String o_title) {
		this.o_title = o_title;
	}

	public String getFoa_title() {
		return foa_title;
	}

	public void setFoa_title(String foa_title) {
		this.foa_title = foa_title;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getGames() {
		return games;
	}

	public void setGames(String games) {
		this.games = games;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getAge() {
		String timeStamp = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		try {
			int year = Integer.parseInt(timeStamp) - Integer.parseInt(birthday);
			if (year < 20) {
				age = 0;
			}
			if (year >= 20 && age <= 25) {
				age = 1;
			}
			if (year > 25) {
				age = 2;
			}
		} catch (NumberFormatException e) {
			age = 2;
		}
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSpielerId() {
		return spielerId;
	}

	public void setSpielerId(int spielerId) {
		this.spielerId = spielerId;
	}

}
