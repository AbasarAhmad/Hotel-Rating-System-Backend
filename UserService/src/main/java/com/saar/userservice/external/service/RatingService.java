package com.saar.userservice.external.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.saar.userservice.entities.Rating;

@FeignClient("RatingService")
public interface RatingService {
	
	@GetMapping("api/ratings/get/userId/{userId}")
	List<Rating> getRatingById(@PathVariable String userId);

}
