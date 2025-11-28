package com.saar.ratingservice.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saar.ratingservice.entities.Rating;
import com.saar.ratingservice.repositories.RatingRepository;
import com.saar.ratingservice.services.RatingService;
import com.saar.ratingservice.exception.ResourceNotFoundException;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private  RatingRepository ratingRepository;
	
	@Override
	public Rating saveRating(Rating rating) {
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getAllRating() {
		return ratingRepository.findAll();
	}

	@Override
	public Rating getRatingById(String ratingId) {
		return ratingRepository.findById(ratingId).orElseThrow(()->new ResourceNotFoundException("User is not found with id :"+ ratingId));		
	}

	@Override
	public Rating updateRating(String ratingId, Rating rating) {
		Rating rt=ratingRepository.findById(ratingId).orElseThrow(()->new ResourceNotFoundException("User is not found with id :"+ ratingId));
	rt.setHotelId(rating.getHotelId());
	rt.setFeedback(rating.getFeedback());
	rt.setRating(rating.getRating());
	rt.setUserId(rating.getUserId());
	return ratingRepository.save(rt);
	}

	@Override
	public String deletedRating(String ratingId) {
		Rating rating=ratingRepository.findById(ratingId).orElseThrow(()->new ResourceNotFoundException("User is not found with id :"+ ratingId));
		ratingRepository.delete(rating);
		return "Rating is deleted with id : "+ratingId;
	}
	
	@Override
	public List<Rating> getAllRatingByUserId(String userId) {
		return ratingRepository.findByUserId(userId);
	}

	@Override
	public List<Rating> getAllRatingByHotelId(String hotelId) {
		return ratingRepository.findByHotelId(hotelId);
	}

}
