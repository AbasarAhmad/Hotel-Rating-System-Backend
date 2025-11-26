package com.saar.hotelservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saar.hotelservice.entities.Hotel;
import com.saar.hotelservice.services.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	@Autowired
	private HotelService hotelService;

	 @PostMapping("/add")
	    public ResponseEntity<Hotel> addHotel(@RequestBody Hotel hotel) {
		 Hotel savedHotel = hotelService.addHotel(hotel);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedHotel);
	    }

	    @GetMapping("/get")
	    public ResponseEntity<List<Hotel>> getAllHotels() {
	        List<Hotel> ls = hotelService.getAllHotel();
	        return ResponseEntity.ok(ls);
	    }

	    @GetMapping("/get/{hotelId}")
	    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId) {
	    	Hotel hotel = hotelService.getHotelById(hotelId);
	        return ResponseEntity.ok(hotel);
	    }

	    @PutMapping("/update/{hotelId}")
	    public ResponseEntity<Hotel> updateUserById(@RequestBody Hotel hotel, @PathVariable String hotelId) {
	    	Hotel updatedUser = hotelService.updateHotel(hotelId, hotel);
	        return ResponseEntity.ok(updatedUser);
	    }

	    @DeleteMapping("/delete/{hotelId}")
	    public ResponseEntity<String> deleteUserById(@PathVariable String hotelId) {
	        String msg = hotelService.deletedHotel(hotelId);
	        return ResponseEntity.ok(msg);
	    }
}
