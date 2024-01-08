package com.example.module11.controller;

import com.example.module11.entity.User;
import com.example.module11.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.findAll();
        logger.info("getting user list: {}", users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "User car details")
    public User getUserById(@PathVariable("id") long userId) {

        return service.findById(userId);
    }

    /**
     * @param user
     * @return
     */
    @PostMapping
    @Operation(summary = "Create user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        service.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/users/" + user.getId());
        return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
    }

    /**
     * @param user
     * @return
     */
    @PutMapping
    @Operation(summary = "Update a user")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        service.update(user);
        return ResponseEntity.ok().build();
    }

    /**
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a user by id")
    public ResponseEntity<User> deleteUser(@PathVariable long userId) {
        service.deleteById(userId);
        return ResponseEntity.ok().build();
    }

}