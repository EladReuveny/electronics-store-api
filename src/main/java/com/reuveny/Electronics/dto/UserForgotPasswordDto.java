/**
 * @package Electronics
 * @author Elad Reuveny
 */
package com.reuveny.Electronics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserForgotPasswordDto {
    private String email;

    private String address;

    private String phone;
}
