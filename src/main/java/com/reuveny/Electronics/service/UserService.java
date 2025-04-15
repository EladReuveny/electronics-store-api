/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Handles user-related functionality, including authentication, registration, and profile updates.
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.dto.UserForgotPasswordDTO;
import com.reuveny.Electronics.dto.UserLoginDTO;
import com.reuveny.Electronics.dto.UserUpdateDTO;
import com.reuveny.Electronics.model.User;

import java.util.List;

public interface UserService {
    /**
     * Retrieves a user by their unique ID.
     *
     * @param userId the ID of the user to be retrieved
     * @return the user associated with the given userId
     * @throws IllegalArgumentException if the user with the given ID does not exist
     */
    User getUserById(Long userId);

    /**
     * Retrieves all users from the repository.
     *
     * @return a list of all users
     */
    List<User> getAllUsers();

    /**
     * Registers a new user in the system.
     * If the email is already in use, it throws an exception.
     * Sets the user's role based on their email (admin if the email contains '-admin@').
     * Initializes a shopping cart and wish list for the new user.
     *
     * @param user the user object to be registered
     * @return the newly registered user
     * @throws IllegalArgumentException if any required field is missing or if the email is already in use
     */
    User registerUser(User user);

    /**
     * Updates the details of an existing user.
     * Allows changes to the email, password, address, and phone number.
     *
     * @param userId        the ID of the user to be updated
     * @param userUpdateDTO DTO containing the new user details
     * @return the updated user
     * @throws IllegalArgumentException if the user does not exist or if email is already taken
     */
    User updateUser(Long userId, UserUpdateDTO userUpdateDTO);

    /**
     * Deletes a user by their unique ID.
     *
     * @param userId the ID of the user to be deleted
     */
    void deleteUser(Long userId);

    /**
     * Authenticates a user by their email and password.
     * Throws an exception if the email or password is incorrect.
     *
     * @param userLoginDTO DTO containing the email and password of the user
     * @return the authenticated user
     * @throws IllegalArgumentException if the email or password is incorrect
     */
    User loginUser(UserLoginDTO userLoginDTO);

    /**
     * Finds and authenticates a user based on email, address, and phone number
     * for password recovery.
     *
     * @param userForgotPasswordDTO The DTO containing email, address, and phone number.
     * @return The authenticated user if details match.
     * @throws IllegalArgumentException If the email is not registered or
     *                                  if address or phone details are incorrect.
     */
    User forgotPassword(UserForgotPasswordDTO userForgotPasswordDTO);
}