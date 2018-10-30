package de.turnierverwaltung.model.rating;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ELOData {
	private int fideid;
	private String name;

	private String country;
	private String sex;
	private String title;
	private String w_title;
	private String o_title;
	private String foa_title;
	private int rating;
	private int games;
	private int k;
	private int birthday;
	private String flag;
	private int age;
	private int spielerId;

	public ELOData() {
		this.fideid = -1;
		this.name = "";
		this.country = "";
		this.sex = "";
		this.title = "";
		this.w_title = "";
		this.o_title = "";
		this.foa_title = "";
		this.rating = 0;
		this.games = 0;
		this.k = 0;
		this.birthday = 0;
		this.flag = "";

		spielerId = -1;
	}

	public ELOData(int fideid, String name, String country, String sex, String title, String w_title, String o_title,
			String foa_title, int rating, int games, int k, int birthday, String flag) {
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

	
	public int getAge() {
		String timeStamp = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());

		int year = Integer.parseInt(timeStamp) - birthday;
		if (year < 20) {
			age = 0;
		}
		if (year >= 20 && age <= 25) {
			age = 1;
		}
		if (year > 25) {
			age = 2;
		}

		return age;
	}

	public int getBirthday() {
		return birthday;
	}

	public String getCountry() {
		return country;
	}

	public int getFideid() {
		
		return fideid;
	}

	public String getFlag() {
		return flag;
	}

	public String getFoa_title() {
		return foa_title;
	}

	public int getGames() {
		return games;
	}

	public int getK() {
		return k;
	}

	public String getName() {
		return name;
	}

	public String getO_title() {
		return o_title;
	}

	public int getRating() {
		return rating;
	}

	public String getSex() {
		return sex;
	}

	public int getSpielerId() {
		return spielerId;
	}

	public String getTitle() {
		return title;
	}

	public String getW_title() {
		return w_title;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setBirthday(int birthday) {
		this.birthday = birthday;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setFideid(int fideid) {
		this.fideid = fideid;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setFoa_title(String foa_title) {
		this.foa_title = foa_title;
	}

	public void setGames(int games) {
		this.games = games;
	}

	public void setK(int k) {
		this.k = k;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setO_title(String o_title) {
		this.o_title = o_title;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setSpielerId(int spielerId) {
		this.spielerId = spielerId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setW_title(String w_title) {
		this.w_title = w_title;
	}

}
