package com.saar.hotelservice.services;

import java.util.List;
import com.saar.hotelservice.entities.Hotel;

public interface HotelService {

	// add User
	Hotel addHotel (Hotel hotel);
	
	// get all user
	List<Hotel> getAllHotel();
	
	// get a single user
	Hotel getHotelById(String hotelId);
	
	// update a user
	Hotel updateHotel(String hotelId, Hotel hotel);
	
	// Delete a user
	String deletedHotel(String hotelId);
	
}
