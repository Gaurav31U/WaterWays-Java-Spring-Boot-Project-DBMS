package com.masters.waterways.services;
import com.masters.waterways.daos.*;
import com.masters.waterways.models.Ship;

import java.util.List;

import com.masters.waterways.models.ShipStatusProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShipDaoImpl implements ShipDao {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@Override
	public int insert (Ship ship) {
		// TODO Auto-generated method stub
		return jdbctemplate.update(
				"INSERT INTO Ship (ModelId, ShipStatusCode,MfDate) VALUES (?,?,?)",
				ship.getModelId(), ship.getShipStatusCode(),ship.getMfDate());
	}

	@Override
	public int delete (int id) {
		// TODO Auto-generated method stub
		return jdbctemplate.update("DELETE FROM Ship WHERE ShipSerialId=?",id);
	}

	@Override
	public List<Ship> getAll () {
		// TODO Auto-generated method stub
		return jdbctemplate.query("SELECT * FROM Ship", new BeanPropertyRowMapper<Ship>(Ship.class));
	}

	@Override
	public Ship getById (int id) {
		// TODO Auto-generated method stub
		return jdbctemplate.queryForObject("SELECT * FROM Ship WHERE ShipSerialId=?",
				new BeanPropertyRowMapper<Ship>(Ship.class), id);
	}

	@Override
	public void setActive(int id) {
		jdbctemplate.update(
				"UPDATE Ship SET ShipStatusCode = ? WHERE ShipSerialId = ?",
				ShipStatusProvider.getShipStatusCode.get("OPERATIONAL"), id
		);
	}

	@Override
	public void setSuspended(int id) {

	}

}
