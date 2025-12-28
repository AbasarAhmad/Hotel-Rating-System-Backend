package com.saar.userservice.controller;

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

import com.saar.userservice.entities.User;
import com.saar.userservice.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Logger instance
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // Types of logs:
    // trace → very fine details
    // debug → debugging information
    // info  → normal information
    // warn  → something suspicious
    // error → something failed

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        logger.info("User added: {}", savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> ls = userService.getAllUser();
        logger.info("Fetched all users. Count = {}", ls.size());
        return ResponseEntity.ok(ls);
    }

    /*
    // Impemented Circuit Breaker
    ///
    @GetMapping("/get/{userId}")
    @CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallbackMethod")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        logger.info("Fetched user with id {}: "+ userId+"  And Name "+ user.getName());
        return ResponseEntity.ok(user);
    }
    */
    
    int retryCount=1;
    // Implemeted Retry
    @GetMapping("/get/{userId}")
//    @Retry(name="ratingHotelRetry",fallbackMethod = "ratingHotelFallbackMethod")
    @RateLimiter(name="userRateLimiter",fallbackMethod = "ratingHotelFallbackMethod" )
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
    	logger.info("Retry count is : {}", retryCount);
    	retryCount++;
    	User user = userService.getUserById(userId);
    	logger.info("Fetched user with id {}: "+ userId+"  And Name "+ user.getName());
    	return ResponseEntity.ok(user);
    }
    
    
    public ResponseEntity<User>ratingHotelFallbackMethod(String userId, Exception ex){
    	logger.info("Fallback is executed because service is down: "+ex.getMessage());
    	User user= User.builder()
    			.email("Dummy@gmail.com")
    			.name("Dummy")
    			.about("Service is down that's why Here is Dummy Data !!!")
    			.userId("123342")
    			.build();
    	return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable String userId) {
        User updatedUser = userService.updateUser(userId, user);
        logger.info("Updated user with id "+updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable String userId) {
        String msg = userService.deletedUser(userId);
        logger.info("Deleted user with id {}", userId);
        return ResponseEntity.ok(msg);
    }
}
