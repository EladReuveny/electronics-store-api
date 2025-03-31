/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Controller for managing user operations.
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.dto.UserLoginDTO;
import com.reuveny.Electronics.dto.UserUpdateDTO;
import com.reuveny.Electronics.model.User;
import com.reuveny.Electronics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the specified ID.
     */
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all registered users.
     */
    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Registers a new user.
     *
     * @param user The user data for registration.
     * @return The registered user or an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Authenticates a user login.
     *
     * @param userLoginDTO The login credentials.
     * @return The authenticated user or an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            User authinticatedUser = userService.loginUser(userLoginDTO);
            return ResponseEntity.ok(authinticatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Updates user details.
     *
     * @param userId The ID of the user to update.
     * @param userUpdateDTO The updated user information.
     * @return The updated user details or an error message.
     */
    @PutMapping("/{userId}/update")
    public ResponseEntity<?> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            User updatedUser = userService.updateUser(userId, userUpdateDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     */
    @DeleteMapping("/{userId}/delete")
    void deleteUserById(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}