/**
 * @package Electronics
 * @author Elad Reuveny
 * @description
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

    @Override
    public User getUserById(Long userId) throws IllegalArgumentException {
        return userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User hasn't been found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

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

    @Override
    @Transactional
    public User updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    if (userUpdateDTO.getNewEmail() != null) {
                        if (userRepository.existsByEmail(userUpdateDTO.getNewEmail())) {
                            throw new IllegalArgumentException("Email is already taken. Please try a different email.");
                        }
                        existingUser.setEmail(userUpdateDTO.getNewEmail());
                    }

                    if (userUpdateDTO.getCurrentPassword() != null) {
                        if (!userUpdateDTO.getCurrentPassword().matches(existingUser.getPassword())) {
                            throw new IllegalArgumentException("Current password doesn't match. Please try again.");
                        }

                        if (userUpdateDTO.getNewPassword() != null) {
                            existingUser.setPassword(userUpdateDTO.getNewPassword());
                        } else {
                            throw new IllegalArgumentException("New password cannot be null.");
                        }
                    }

                    if (userUpdateDTO.getNewAddress() != null) {
                        existingUser.setAddress(userUpdateDTO.getNewAddress());
                    }

                    if (userUpdateDTO.getNewPhone() != null) {
                        existingUser.setPhone(userUpdateDTO.getNewPhone());
                    }

                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new IllegalArgumentException("User " + userId + " hasn't been found."));
    }


    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

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
