package com.spring.cvgenerator.controllers;

import com.spring.cvgenerator.dto.filter.UserFilter;
import com.spring.cvgenerator.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(path = "/list", produces = {"application/json"})
    public ResponseEntity<?> findAll(UserFilter userFilter) {

        logger.info("findAll with param: userFilter = {}", userFilter);

        try {
            return ResponseEntity.ok(userService.findAll(userFilter));
        } catch (Exception e) {
            logger.error("Failed to findAll with error = {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Failed to findAll");
        }
    }

    @GetMapping(path = "byId")
    public ResponseEntity<?> findUserById(@RequestParam("userId") long userId) {
        logger.info("-----------Trying to findUserById with params: userId: {}", userId);

        try {
            return ResponseEntity.ok(this.userService.findById(userId));
        } catch (Exception e) {
            logger.error("Cannot find user with id: {}, exception: {}", userId, e);
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createAccount(@RequestBody AppUser appUser) {
        logger.info("-----------Trying to createAccount, user: {}", appUser);
        try {

            return ResponseEntity.ok(userService.saveUser(appUser));
        } catch (Exception e) {
            logger.error("-------------------Failed to save user: {}, exception: {}", appUser, e);
            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("message", "Failed to create account!");
            responseMap.put("error", e.getMessage());
            responseMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.internalServerError().body(responseMap);

        }
    }

    @DeleteMapping(path = "/removeAllInactive")
    public ResponseEntity<?> removeAllInactive() {
        this.userService.removeAllInactive();
        return ResponseEntity.ok("Success!");
    }


    @PutMapping(path = "/toggle-enabled")
    public ResponseEntity<?> toggleEnabled(@RequestParam("userId") long userId) {
        logger.info("-----------Trying to toggleEnabled with params: userId: {}", userId);

        try {
            AppUser updated = this.userService.toggleEnabled(userId);

            logger.info("--------------------Successfully toggleEnabled user: {}", updated);

            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            logger.error("----------------------Failed to toggleEnabled!");
            return ResponseEntity.internalServerError().body("Failed");
        }
    }

    @PutMapping(path = "updateUser")
    public ResponseEntity<?> updateUser(@RequestBody AppUser user) {
        logger.info("----------Trying to updateUser: user = {}", user);

        try {
            return ResponseEntity.ok(this.userService.editUser(user));
        } catch (Exception e) {
            logger.error("Failed to updateUser: {}. Error: {}", user, e.getMessage());

            return ResponseEntity.internalServerError().body("Failed!");
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteUserById(@RequestParam("userId") long userId) {
        logger.info("--------------------------Trying to deleteUserById with param: userId = {}", userId);
        try {
            this.userService.removeUser(userId);
            logger.info("--------------------------Successfully removed user with id: {}", userId);

            return ResponseEntity.ok(String.format("Successfully removed user with id: %d", userId));
        } catch (Exception e) {
            logger.error("Failed to deleteUserById with param: userId = {}", userId);

            Map<String, Object> respMap = new LinkedHashMap<>();
            respMap.put("message", String.format("Failed to deleteUserById with param %d", userId));
            respMap.put("errorMsg", e.getMessage());
            respMap.put("StatusCode", 500);
            return ResponseEntity.internalServerError().body(respMap);
        }
    }

}
