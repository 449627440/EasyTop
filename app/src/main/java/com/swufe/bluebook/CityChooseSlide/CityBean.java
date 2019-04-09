package com.swufe.bluebook.CityChooseSlide;

public class CityBean {
	private String key;
	private String name;
	private String spell;
	private String id;
	private String districList;

	public String getDistricList() {
		return districList;
	}

	public void setDistricList(String districList) {
		this.districList = districList;
	}

	public CityBean(String id, String name, String spell) {
		super();
		this.id = id;
		this.name = name;
		this.spell = spell;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CityBean() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
