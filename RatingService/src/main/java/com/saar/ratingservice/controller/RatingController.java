package com.saar.ratingservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.saar.ratingservice.entities.Rating;
import com.saar.ratingservice.services.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

    @Autowired
    private RatingService ratingService;

    @PostMapping("/add")
    public ResponseEntity<Rating> addRating(@RequestBody Rating rating) {
        Rating savedRating = ratingService.saveRating(rating);
        logger.info("Added rating: {}", savedRating);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ls = ratingService.getAllRating();
        logger.info("Fetched all ratings, count={}", ls.size());
        return ResponseEntity.ok(ls);
    }

    @GetMapping("/get/{ratingId}")
    public ResponseEntity<Rating> getRatingById(@PathVariable String ratingId) {
        Rating rating = ratingService.getRatingById(ratingId);
        logger.info("Fetched rating with id={}: {}", ratingId, rating);
        return ResponseEntity.ok(rating);
    }

    @PutMapping("/update/{ratingId}")
    public ResponseEntity<Rating> updateRatingById(@RequestBody Rating rating, @PathVariable String ratingId) {
        Rating updatedRating = ratingService.updateRating(ratingId, rating);
        logger.info("Updated rating id={}: {}", ratingId, updatedRating);
        return ResponseEntity.ok(updatedRating);
    }

    @DeleteMapping("/delete/{ratingId}")
    public ResponseEntity<String> deleteRatingById(@PathVariable String ratingId) {
        String msg = ratingService.deletedRating(ratingId);
        logger.info("Deleted rating id={}", ratingId);
        return ResponseEntity.ok(msg);
    }
    
    @GetMapping("/get/userId/{userId}")
	public ResponseEntity<List<Rating>> getAllRatingByUserId(@PathVariable String userId)
	{
		return ResponseEntity.ok(ratingService.getAllRatingByUserId(userId));
	}
	
	
	@GetMapping("/get/hotelId/{hotelId}")
	public ResponseEntity<List<Rating>> getAllRatingByHotelId(@PathVariable String hotelId)
	{
		return ResponseEntity.ok(ratingService.getAllRatingByHotelId(hotelId));
	}
}
