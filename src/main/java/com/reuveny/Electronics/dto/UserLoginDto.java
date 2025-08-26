/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Represents user login credentials with email and password.
 */
package com.reuveny.Electronics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginDto {
    private String email;

    private String password;
}
