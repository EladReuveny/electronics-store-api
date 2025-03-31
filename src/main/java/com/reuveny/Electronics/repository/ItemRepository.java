/**
 * @package Electronics
 * @author Elad Reuveny
 *
 * Manages CRUD operations for individual items within orders or shopping carts.
 */
package com.reuveny.Electronics.repository;

import com.reuveny.Electronics.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
