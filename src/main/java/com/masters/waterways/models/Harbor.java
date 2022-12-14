package com.masters.waterways.models;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Harbor {

	private int HarborId;
	private String Location;
	private Date ConstructionDate;
	private int ManagerId;
	private int HarborStatusCode;

	public int getHarborId() {
		return HarborId;
	}

	public void setHarborId(int harborId) {
		HarborId = harborId;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public Date getConstructionDate() {
		return ConstructionDate;
	}

	public void setConstructionDate(Date constructionDate) {
		ConstructionDate = constructionDate;
	}

	public int getManagerId() {
		return ManagerId;
	}

	public void setManagerId(int managerId) {
		ManagerId = managerId;
	}

	public int getHarborStatusCode() {
		return HarborStatusCode;
	}

	public void setHarborStatusCode(int harborStatusCode) {
		HarborStatusCode = harborStatusCode;
	}
}
