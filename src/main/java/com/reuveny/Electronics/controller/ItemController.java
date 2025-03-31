/**
 * @package Electronics
 * @author Elad Reuveny
 *
 *
 */
package com.reuveny.Electronics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/item")
@CrossOrigin(origins = "http://localhost:5173")
public class ItemController {

}
