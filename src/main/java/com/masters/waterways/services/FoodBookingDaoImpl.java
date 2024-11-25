package com.masters.waterways.services;

import com.masters.waterways.daos.*;
import com.masters.waterways.models.FoodBooking;
import java.util.List;
import com.masters.waterways.models.FoodItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class FoodBookingDaoImpl implements FoodBookingDao {

	@Autowired
	JdbcTemplate jdbctemplate;

//	@Override
//	public void insert (FoodBooking foodBooking) {
//		// TODO Auto-generated method stub
//		jdbctemplate.update(
//				"INSERT INTO FoodBooking (FoodItemId, VoyageId, FoodItemCount, TransactionId) VALUES (?, ?, ?, ?)",
//				foodBooking.getFoodItemId(), foodBooking.getVoyageId(), foodBooking.getFoodItemCount(), foodBooking.getTransactionId()
//		);
//	}


	@Override
	public List<FoodBooking> getAll () {
		// TODO Auto-generated method stub
		return jdbctemplate.query(
				"SELECT * FROM FoodBooking",
				new BeanPropertyRowMapper<FoodBooking>(FoodBooking.class)
		);
	}

    @Override
    public List<FoodBooking> getAllByVoyageId(int voyageId) {
		return jdbctemplate.query(
				"SELECT * FROM FoodBooking WHERE VoyageId = ?",
				new BeanPropertyRowMapper<>(FoodBooking.class), voyageId
		);
    }

	@Override
	public List<FoodBooking> getAllByUserIdAndRoomIdAndVoyageId(int userId, int voyageId, int roomId) {
		return jdbctemplate.query(
				"SELECT * FROM FoodBooking WHERE VoyageId = ? AND RoomId = ? AND TransactionId IN (SELECT TransactionId FROM Transaction WHERE UserId = ?)",
				new BeanPropertyRowMapper<>(FoodBooking.class), voyageId, roomId, userId
		);
	}


	@Override
	public FoodBooking getById (int id) {
		// TODO Auto-generated method stub
		return jdbctemplate.queryForObject(
				"SELECT * FROM FoodBooking WHERE TransactionId = ?",
				new BeanPropertyRowMapper<FoodBooking>(FoodBooking.class), id
		);
	}

	@Override
	@Transactional
	public void bookFood(int userId, FoodBooking foodBooking) {

		System.out.println("HI");

		Integer foodCost = jdbctemplate.queryForObject(
				"SELECT FoodCost FROM FoodItem WHERE FoodItemId = ? AND VoyageId = ?",
				Integer.class, foodBooking.getFoodItemId(), foodBooking.getVoyageId()
		);

		if (foodCost != null) {
			// REAL TRANSACTION FUNCTION IS TO BE IMPLEMENT USING THE API CALLS AND BELOW CHANGE SHOULD BE ATOMIC W.R.T. TO TRANSACTION
			jdbctemplate.update(
					"INSERT INTO Transaction (TransactionDate, Amount, UserId) VALUES (NOW(), ?, ?)",
					foodBooking.getFoodItemCount() * foodCost, userId
			);
			jdbctemplate.update(
					"INSERT INTO FoodBooking (TransactionId, FoodItemId, VoyageId, RoomId, FoodItemCount) Values(" +
							"(SELECT LAST_INSERT_ID()), ?, ?, ?, ?)",
					foodBooking.getFoodItemId(), foodBooking.getVoyageId(), foodBooking.getRoomId(), foodBooking.getFoodItemCount()
			);
		}

//		jdbctemplate.update(
//				"INSERT INTO Transaction (TransactionDate, Amount, UserId) VALUES (NOW(), (?) * (SELECT FoodCost FROM FoodItem WHERE FoodItemId = ?), ?)",
//				foodBooking.getFoodItemCount(), foodBooking.getFoodItemId(), userId
//		);
//
//		jdbctemplate.update(
//				"INSERT INTO FoodBooking (TransactionId, FoodItemId, VoyageId, RoomId, FoodItemCount) Values(" +
//						"(SELECT LAST_INSERT_ID()), ?, ?, ?, ?)",
//				foodBooking.getFoodItemId(), foodBooking.getVoyageId(), foodBooking.getRoomId(), foodBooking.getFoodItemCount()
//		);
	}

}
