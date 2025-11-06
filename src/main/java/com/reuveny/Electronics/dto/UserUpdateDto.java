/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Contains user update details, including email, password, address, and phone number.
 */
package com.reuveny.Electronics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDto {
    private String newEmail;

    private String currentPassword;

    private String newPassword;

    private String newAddress;

    private String newPhone;
}
