package com.saar.hotelservice.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saar.hotelservice.entities.Hotel;
import com.saar.hotelservice.exception.ResourceNotFoundException;
import com.saar.hotelservice.repositories.HotelRepository;
import com.saar.hotelservice.services.HotelService;

@Service
public class HotelServicesImpl implements HotelService {

	@Autowired
	private HotelRepository hotelRepository;
	
	@Override
	public Hotel addHotel(Hotel hotel) {
		// code to generate Random userId
				String uuid= UUID.randomUUID().toString();
				hotel.setHotelId(uuid);
				return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAllHotel() {
		return hotelRepository.findAll();
	}

	@Override
	public Hotel getHotelById(String hotelId) {
		return hotelRepository.findById(hotelId).orElseThrow(()->new ResourceNotFoundException("User is not found with id :"+ hotelId));
	}

	@Override
	public Hotel updateHotel(String hotelId, Hotel hotel) {
		Hotel hotel1=hotelRepository.findById(hotelId).orElseThrow(()->new ResourceNotFoundException("User is not found with id :"+ hotelId));
		hotel1.setAbout(hotel.getAbout());
		hotel1.setLocation(hotel.getLocation());
		hotel1.setName(hotel.getName());
		return hotelRepository.save(hotel1);
	}

	@Override
	public String deletedHotel(String hotelId) {
		Hotel hotel1=hotelRepository.findById(hotelId).orElseThrow(()->new ResourceNotFoundException("User is not found with id :"+ hotelId));
		hotelRepository.delete(hotel1);
		return "User is delete with id : "+hotelId;
	}

}
