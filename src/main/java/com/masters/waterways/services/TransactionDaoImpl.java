package com.masters.waterways.services;
import com.masters.waterways.daos.*;
import com.masters.waterways.models.Transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDaoImpl implements TransactionDao {

	@Autowired
	JdbcTemplate jdbctemplate;
	
	@Override
	public int insert (Transaction Transaction) {
		// TODO Auto-generated method stub
		return jdbctemplate.update(
				"INSERT INTO Transaction (TransactionDate, Amount, UserId) VALUES (?, ?, ?)",
				Transaction.getTransactionDate(), Transaction.getAmount(), Transaction.getUserId()
		);
	}

	@Override
	public List<Transaction> getAll () {
		// TODO Auto-generated method stub
		return jdbctemplate.query(
				"SELECT * FROM Transaction", new BeanPropertyRowMapper<>(Transaction.class)
		);
	}

    @Override
    public List<Transaction> getAllByUserId(int userId) {
		return jdbctemplate.query(
				"SELECT * FROM Transaction WHERE UserId = ? ORDER BY TransactionDate desc",
				new BeanPropertyRowMapper<>(Transaction.class), userId
		);
    }

    @Override
	public Transaction getById (int id) {
		// TODO Auto-generated method stub
		return jdbctemplate.queryForObject(
				"SELECT * FROM Transaction WHERE TransactionId = ?",
				new BeanPropertyRowMapper<>(Transaction.class), id
		);
	}

}
