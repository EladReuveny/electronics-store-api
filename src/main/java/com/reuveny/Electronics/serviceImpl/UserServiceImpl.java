/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Implementation of the UserService interface to handle user-related operations.
 */
package com.reuveny.Electronics.serviceImpl;

import com.reuveny.Electronics.dto.UserForgotPasswordDto;
import com.reuveny.Electronics.dto.UserLoginDto;
import com.reuveny.Electronics.dto.UserUpdateDto;
import com.reuveny.Electronics.exception.ResourceAlreadyExistsException;
import com.reuveny.Electronics.exception.ResourceNotFoundException;
import com.reuveny.Electronics.model.Role;
import com.reuveny.Electronics.model.ShoppingCart;
import com.reuveny.Electronics.model.User;
import com.reuveny.Electronics.model.WishList;
import com.reuveny.Electronics.repository.UserRepository;
import com.reuveny.Electronics.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Value("${admin.email}")
    private String adminEmail;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new ResourceNotFoundException(
                                     "User " + userId + " hasn't been found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        if (user == null || user.getEmail() == null || user.getPassword() == null ||
            user.getAddress() == null || user.getPhone() == null) {
            throw new IllegalArgumentException("All user's fields are required.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("Email is already taken.");
        }
        if (user.getEmail()
                .contains(adminEmail)) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.SUBSCRIBED);
        }
        user.setShoppingCart(new ShoppingCart());
        user.setWishList(new WishList());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long userId, UserUpdateDto userUpdateDTO) {
        return userRepository.findById(userId)
                             .map(existingUser -> {
                                 if (userUpdateDTO.getNewEmail() != null &&
                                     !userUpdateDTO.getNewEmail()
                                                   .isEmpty()) {
                                     if (userRepository.existsByEmail(
                                             userUpdateDTO.getNewEmail())) {
                                         throw new ResourceAlreadyExistsException(
                                                 "Email is already taken. Please try a different email.");
                                     }
                                     existingUser.setEmail(userUpdateDTO.getNewEmail());
                                 }
                                 if (userUpdateDTO.getCurrentPassword() != null &&
                                     !userUpdateDTO.getCurrentPassword()
                                                   .isEmpty()) {
                                     if (!userUpdateDTO.getCurrentPassword()
                                                       .matches(existingUser.getPassword())) {
                                         throw new IllegalArgumentException(
                                                 "Current password doesn't match. Please try again.");
                                     }
                                     if (userUpdateDTO.getNewPassword() != null &&
                                         !userUpdateDTO.getNewPassword()
                                                       .isEmpty()) {
                                         existingUser.setPassword(userUpdateDTO.getNewPassword());
                                     } else {
                                         throw new IllegalArgumentException(
                                                 "New password cannot be null.");
                                     }
                                 }
                                 if (userUpdateDTO.getNewAddress() != null &&
                                     !userUpdateDTO.getNewAddress()
                                                   .isEmpty()) {
                                     existingUser.setAddress(userUpdateDTO.getNewAddress());
                                 }
                                 if (userUpdateDTO.getNewPhone() != null &&
                                     !userUpdateDTO.getNewPhone()
                                                   .isEmpty()) {
                                     existingUser.setPhone(userUpdateDTO.getNewPhone());
                                 }
                                 return userRepository.save(existingUser);
                             })
                             .orElseThrow(() -> new ResourceNotFoundException(
                                     "User " + userId + " hasn't been found."));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User loginUser(UserLoginDto userLoginDTO) {
        User user = userRepository.findByEmail(userLoginDTO.getEmail())
                                  .orElseThrow(() -> new IllegalArgumentException(
                                          "Email or password given are incorrect."));
        if (!user.getPassword()
                 .matches(userLoginDTO.getPassword())) {
            throw new IllegalArgumentException("Email or password given are incorrect.");
        }
        return user;
    }

    @Override
    public User forgotPassword(UserForgotPasswordDto userForgotPasswordDTO) {
        User registeredUser = userRepository.findByEmail(userForgotPasswordDTO.getEmail())
                                            .orElseThrow(() -> new ResourceNotFoundException(
                                                    "Email '" + userForgotPasswordDTO.getEmail() +
                                                    "' isn't registered."));
        if (userForgotPasswordDTO.getAddress() == null || userForgotPasswordDTO.getAddress()
                                                                               .isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty.");
        } else if (userForgotPasswordDTO.getPhone() == null || userForgotPasswordDTO.getPhone()
                                                                                    .isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be empty.");
        }
        if (!registeredUser.getAddress()
                           .matches(userForgotPasswordDTO.getAddress())) {
            throw new IllegalArgumentException("Wrong address.");
        } else if (!Objects.equals(registeredUser.getPhone(), userForgotPasswordDTO.getPhone())) {
            throw new IllegalArgumentException("Wrong phone.");
        }
        return registeredUser;
    }
}