/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Controller for managing user operations.
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.dto.UserForgotPasswordDTO;
import com.reuveny.Electronics.dto.UserLoginDTO;
import com.reuveny.Electronics.dto.UserUpdateDTO;
import com.reuveny.Electronics.model.User;
import com.reuveny.Electronics.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(
        name = "User Controller",
        description = "Handles all user-related endpoints"
)
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(
            summary = "Get user by ID",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user",
                            required = true
                    )
            }
    )
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    @Operation(summary = "Get all users")
    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Register a new user",
            parameters = {
                    @Parameter(
                            name = "user",
                            description = "The user data for registration",
                            required = true
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(e.getMessage());
        }
    }

    @Operation(
            summary = "Login a user",
            parameters = {
                    @Parameter(
                            name = "userLoginDTO",
                            description = "The login credentials for registered user",
                            required = true
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            User authinticatedUser = userService.loginUser(userLoginDTO);
            return ResponseEntity.ok(authinticatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(e.getMessage());
        }
    }

    @Operation(
            summary = "Update user details",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user to update",
                            required = true
                    ),
                    @Parameter(
                            name = "userUpdateDTO",
                            description = "The updated user information",
                            required = true
                    )
            }
    )
    @PutMapping("/{userId}/update")
    public ResponseEntity<?> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserUpdateDTO userUpdateDTO
    ) {
        try {
            User updatedUser = userService.updateUser(userId, userUpdateDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(e.getMessage());
        }
    }

    @Operation(
            summary = "Delete a user by ID",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "The ID of the user to delete",
                            required = true
                    )
            }
    )
    @DeleteMapping("/{userId}/delete")
    public void deleteUserById(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }

    @Operation(
            summary = "Forgot password",
            parameters = {
                    @Parameter(
                            name = "userForgotPasswordDTO",
                            description = "The user details for password recovery",
                            required = true
                    )
            }
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody UserForgotPasswordDTO userForgotPasswordDTO
    ) {
        try {
            User authenticatedUser = userService.forgotPassword(userForgotPasswordDTO);
            return ResponseEntity.ok(authenticatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(e.getMessage());
        }
    }
}