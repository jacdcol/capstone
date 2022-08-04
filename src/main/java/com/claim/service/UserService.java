package com.claim.service;

import com.claim.entity.User;
import com.claim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    UserRepository userRepository;

    public void saveUser(User usr) {userRepository.save(usr);}
    public List<User> getAllUsers() {return userRepository.findAll();}
    public void deleteUserById(String email) {userRepository.deleteById(email);}
    public User loginUser(User user) {return userRepository.login(user.getUsername(), user.getPassword());}
    public Optional<User> findByUsername(String username) {return userRepository.findById(username);}
}