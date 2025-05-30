/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Represents user login credentials with email and password.
 */
package com.reuveny.Electronics.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
