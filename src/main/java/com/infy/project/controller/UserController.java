package com.infy.project.controller;

import com.infy.project.Repository.UserRepository;
import com.infy.project.entity.User;
import com.infy.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }


    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName(); //this will give us the username
        User userInDb= userService.findByUserName(userName);

           userInDb.setUsername(user.getUsername());
           userInDb.setPassword(user.getPassword());
           userService.saveNewEntry(userInDb);

       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
