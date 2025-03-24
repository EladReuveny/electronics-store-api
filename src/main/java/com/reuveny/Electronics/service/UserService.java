/**
 * @package Electronics
 * @author Elad Reuveny
 * @description
 */
package com.reuveny.Electronics.service;

import com.reuveny.Electronics.dto.UserLoginDTO;
import com.reuveny.Electronics.dto.UserUpdateDTO;
import com.reuveny.Electronics.model.User;

import java.util.List;

public interface UserService {
    User getUserById(Long userId);
    List<User> getAllUsers();
    User registerUser(User user);
    User updateUser(Long userId, UserUpdateDTO userUpdateDTO);
    void deleteUser(Long userId);
    User loginUser(UserLoginDTO userLoginDTO);
}
