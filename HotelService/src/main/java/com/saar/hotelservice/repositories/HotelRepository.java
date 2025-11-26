package com.saar.hotelservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saar.hotelservice.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String> {

}
