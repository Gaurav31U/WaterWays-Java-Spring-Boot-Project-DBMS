package com.masters.waterways.daos;

import java.util.List;
import com.masters.waterways.models.*;


public interface RoomBookingDao {
    int insert (RoomBooking room_booking);
	int update (RoomBooking room_booking, int id);
	int delete (int id);
	List<RoomBooking> getAll ();
	RoomBooking getById (int id);
}
