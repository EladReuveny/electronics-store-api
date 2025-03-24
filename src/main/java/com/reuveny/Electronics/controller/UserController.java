/**
 * @package Electronics
 * @author Elad Reuveny
 * @description
 */
package com.reuveny.Electronics.controller;

import com.reuveny.Electronics.dto.UserLoginDTO;
import com.reuveny.Electronics.dto.UserUpdateDTO;
import com.reuveny.Electronics.model.User;
import com.reuveny.Electronics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.loginUser(userLoginDTO);
    }

    @PutMapping("/{userId}/update")
    public User updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateUser(userId, userUpdateDTO);
    }

    @DeleteMapping("/{userId}/delete")
    void deleteUserById(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }
}

