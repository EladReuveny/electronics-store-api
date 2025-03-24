/**
* @package Electronics
 * 
 * @author Elad Reuveny
 * @description 
 */
package com.reuveny.Electronics.repository;

import com.reuveny.Electronics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
