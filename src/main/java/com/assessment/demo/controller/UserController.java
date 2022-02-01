package com.assessment.demo.controller;

import java.util.List;

import com.assessment.demo.model.UserInfo;
import com.assessment.demo.repository.UserRepository;
import com.assessment.demo.service.UserService;
import com.assessment.demo.vo.UserChangePasswordModel;
import com.assessment.demo.vo.UserDisplayInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "", tags = { "Available APIs" })
@SuppressWarnings({ "rawtypes" })
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    // create a new user
    @ApiOperation(value = "create new user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Failed due to bad input"),
            @ApiResponse(code = 201, message = "Successfully created the user") })
    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody UserInfo user) {
        return userService.createUser(user);
    }

    // list all users
    @ApiOperation(value = "List all users")
    @GetMapping("/users")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Failed due to bad input"),
            @ApiResponse(code = 200, message = "Fetched all users successfully") })
    public ResponseEntity<List<UserDisplayInfo>> getAllUsers() {
        return userService.getAllUsers();
    }

    // get user details using id
    @ApiOperation(value = "Get user details using id")
    @GetMapping("/users/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "No user found with the given id"),
            @ApiResponse(code = 200, message = "Fetched user details successfully") })
    public ResponseEntity<UserDisplayInfo> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // update user details
    @ApiOperation(value = "Update user details")
    @PutMapping("/users/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Unable to find user with the given id"),
            @ApiResponse(code = 204, message = "Updated Successfully"),
            @ApiResponse(code = 400, message = "Update failed due to bad input") })
    public ResponseEntity updateUserDetails(@PathVariable Long id,
            @RequestBody UserDisplayInfo userDisplayInfo) {
        return userService.updateUserDetails(id, userDisplayInfo);
    }

    // Change user password
    @ApiOperation(value = "Change password")
    @PatchMapping("/users/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Unable to find user with the given id"),
            @ApiResponse(code = 204, message = "Updated Password Successfully"),
            @ApiResponse(code = 400, message = "Update failed due to bad input") })
    public ResponseEntity changePassword(@PathVariable Long id, @RequestBody UserChangePasswordModel userNewPassword) {
        return userService.changePassword(id, userNewPassword);
    }

    // delete user
    @ApiOperation(value = "Delete a user")
    @DeleteMapping("/users/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Unable to find user with the given id"),
            @ApiResponse(code = 200, message = "Deleted the user successfully") })
    public ResponseEntity deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}
