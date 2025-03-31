/**
 * @package Electronics
 * @author Elad Reuveny
 * @description Implementation of the UserService interface to handle user-related operations.
 */
package com.reuveny.Electronics.serviceImpl;

import com.reuveny.Electronics.dto.UserLoginDTO;
import com.reuveny.Electronics.dto.UserUpdateDTO;
import com.reuveny.Electronics.model.Role;
import com.reuveny.Electronics.model.ShoppingCart;
import com.reuveny.Electronics.model.User;
import com.reuveny.Electronics.model.WishList;
import com.reuveny.Electronics.repository.UserRepository;
import com.reuveny.Electronics.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a user by their unique ID.
     *
     * @param userId the ID of the user to be retrieved
     * @return the user associated with the given userId
     * @throws IllegalArgumentException if the user with the given ID does not exist
     */
    @Override
    public User getUserById(Long userId) throws IllegalArgumentException {
        return userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User hasn't been found"));
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return a list of all users
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

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
    @Override
    @Transactional
    public User registerUser(User user) throws IllegalArgumentException {
        if (user == null || user.getEmail() == null
                || user.getPassword() == null
                || user.getAddress() == null
                || user.getPhone() == null) {
            throw new IllegalArgumentException("All user's fields are required.");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already taken.");
        }

        if(user.getEmail().contains("-admin@")) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.SUBSCRIBED);
        }

        user.setShoppingCart(new ShoppingCart());
        user.setWishList(new WishList());

        return userRepository.save(user);
    }

    /**
     * Updates the details of an existing user.
     * Allows changes to the email, password, address, and phone number.
     *
     * @param userId the ID of the user to be updated
     * @param userUpdateDTO DTO containing the new user details
     * @return the updated user
     * @throws IllegalArgumentException if the user does not exist or if email is already taken
     */
    @Override
    @Transactional
    public User updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    if (userUpdateDTO.getNewEmail() != null && !userUpdateDTO.getNewEmail().isEmpty()) {
                        if (userRepository.existsByEmail(userUpdateDTO.getNewEmail())) {
                            throw new IllegalArgumentException("Email is already taken. Please try a different email.");
                        }
                        existingUser.setEmail(userUpdateDTO.getNewEmail());
                    }

                    if (userUpdateDTO.getCurrentPassword() != null && !userUpdateDTO.getCurrentPassword().isEmpty()) {
                        if (!userUpdateDTO.getCurrentPassword().matches(existingUser.getPassword())) {
                            throw new IllegalArgumentException("Current password doesn't match. Please try again.");
                        }

                        if (userUpdateDTO.getNewPassword() != null && !userUpdateDTO.getNewPassword().isEmpty()) {
                            existingUser.setPassword(userUpdateDTO.getNewPassword());
                        } else {
                            throw new IllegalArgumentException("New password cannot be null.");
                        }
                    }

                    if (userUpdateDTO.getNewAddress() != null && !userUpdateDTO.getNewAddress().isEmpty()) {
                        existingUser.setAddress(userUpdateDTO.getNewAddress());
                    }

                    if (userUpdateDTO.getNewPhone() != null && !userUpdateDTO.getNewPhone().isEmpty()) {
                        existingUser.setPhone(userUpdateDTO.getNewPhone());
                    }

                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new IllegalArgumentException("User " + userId + " hasn't been found."));
    }


    /**
     * Deletes a user by their unique ID.
     *
     * @param userId the ID of the user to be deleted
     */
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Authenticates a user by their email and password.
     * Throws an exception if the email or password is incorrect.
     *
     * @param userLoginDTO DTO containing the email and password of the user
     * @return the authenticated user
     * @throws IllegalArgumentException if the email or password is incorrect
     */
    @Override
    public User loginUser(UserLoginDTO userLoginDTO) {
        User user = userRepository
                .findByEmail(userLoginDTO.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("Email or password given are incorrect."));

        if (!user.getPassword().matches(userLoginDTO.getPassword())) {
            throw new IllegalArgumentException("Incorrect password. Try again.");
        }
        return user;

    }
}