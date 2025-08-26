/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Controller for managing user operations.
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.dto.UserForgotPasswordDto;
import com.reuveny.Electronics.dto.UserLoginDto;
import com.reuveny.Electronics.dto.UserUpdateDto;
import com.reuveny.Electronics.model.User;
import com.reuveny.Electronics.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(
        name = "User Controller",
        description = "Handles all user-related endpoints"
)
public class UserController {
    private final UserService userService;

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
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all users")
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
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
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(registeredUser);
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
    public ResponseEntity<User> loginUser(@RequestBody UserLoginDto userLoginDTO) {
        User authinticatedUser = userService.loginUser(userLoginDTO);
        return ResponseEntity.ok(authinticatedUser);
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
    public ResponseEntity<User> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserUpdateDto userUpdateDTO
    ) {
        User updatedUser = userService.updateUser(userId, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
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
    public ResponseEntity<Void> deleteUserById(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent()
                             .build();
    }

    @Operation(
            summary = "Forgot/Retrieve password",
            parameters = {
                    @Parameter(
                            name = "userForgotPasswordDTO",
                            description = "The user details for password recovery",
                            required = true
                    )
            }
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<User> forgotPassword(
            @RequestBody UserForgotPasswordDto userForgotPasswordDTO
    ) {
        User authenticatedUser = userService.forgotPassword(userForgotPasswordDTO);
        return ResponseEntity.ok(authenticatedUser);
    }
}