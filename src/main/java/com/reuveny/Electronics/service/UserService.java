/**
 * @package Electronics
 * @author Elad Reuveny
 * @description Service interface for managing user-related operations.
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
     */
    User registerUser(User user);

    /**
     * Updates an existing user's details.
     *
     * @param userId The ID of the user to update.
     * @param userUpdateDTO The updated user information.
     * @return The updated user details.
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
     */
    User loginUser(UserLoginDTO userLoginDTO);
}