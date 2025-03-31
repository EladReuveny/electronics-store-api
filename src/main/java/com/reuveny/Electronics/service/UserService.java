/**
 * @package Electronics
 * @author Elad Reuveny
 *
 *  Handles user-related functionality, including authentication, registration, and profile updates.
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.dto.UserLoginDTO;
import com.reuveny.Electronics.dto.UserUpdateDTO;
import com.reuveny.Electronics.model.User;

import java.util.List;

public interface UserService {
    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user with the specified ID.
     * @throws IllegalArgumentException if the user with the given ID does not exist
     */
    User getUserById(Long userId);

    /**
     * Retrieves all registered users.
     *
     * @return A list of all users.
     */
    List<User> getAllUsers();

    /**
     * Registers a new user in the system.
     *
     * @param user The user information for registration.
     * @return The registered user.
     * @throws IllegalArgumentException if any required field is missing or if the email is already in use
     */
    User registerUser(User user);

    /**
     * Updates an existing user's details.
     *
     * @param userId The ID of the user to update.
     * @param userUpdateDTO The updated user information.
     * @return The updated user details.
     * @throws IllegalArgumentException if the user does not exist or if email is already taken
     */
    User updateUser(Long userId, UserUpdateDTO userUpdateDTO);

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     */
    void deleteUser(Long userId);

    /**
     * Authenticates a user login.
     *
     * @param userLoginDTO The login credentials.
     * @return The authenticated user.
     * @throws IllegalArgumentException if the email or password is incorrect
     */
    User loginUser(UserLoginDTO userLoginDTO);
}