package com.saar.userservice.external.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.saar.userservice.entities.Hotel;

@FeignClient(name="HotelService")
public interface HotelService {
	
	@GetMapping("/api/hotels/get/{hotelId}")
	Hotel getHotel(@PathVariable("hotelId") String hotelId);
}
