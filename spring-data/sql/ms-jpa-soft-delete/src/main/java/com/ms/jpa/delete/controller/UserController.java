package com.ms.jpa.delete.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ms.jpa.delete.entity.User;
import com.ms.jpa.delete.repository.UserRepository;


@RestController
@RequestMapping("api/")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String createUser(@RequestBody User user){
        userRepository.save(user);
        return "SUCCESS";
    }
    
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String updateUser(@RequestBody User user){
        userRepository.save(user);
        return "SUCCESS";
    }
    
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public String deleteUserById(@PathVariable("userId") int userId){
        
        User user = userRepository.findOne(userId);
        if(null != user){
            userRepository.delete(user);
        }
        return null ==user ? "Not Found" : "SUCCESS";
    }
}
