package com.claim.service;

import com.claim.entity.User;
import com.claim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
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
    public User loginUser(User user) {return userRepository.login(user.getEmail(), user.getPassword());}
    public Optional<User> findByEmail(String email) {return userRepository.findById(email);}
}